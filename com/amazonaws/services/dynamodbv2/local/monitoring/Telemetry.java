/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 *  software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
 *  software.amazon.awssdk.auth.credentials.AwsSessionCredentials
 *  software.amazon.awssdk.regions.Region
 *  software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient
 *  software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClientBuilder
 *  software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityRequest
 *  software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityResponse
 *  software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest
 *  software.amazon.awssdk.services.cognitoidentity.model.GetIdResponse
 *  software.amazon.awssdk.services.pinpoint.PinpointClient
 *  software.amazon.awssdk.services.pinpoint.PinpointClientBuilder
 *  software.amazon.awssdk.services.pinpoint.model.EndpointDemographic
 *  software.amazon.awssdk.services.pinpoint.model.EndpointUser
 *  software.amazon.awssdk.services.pinpoint.model.Event
 *  software.amazon.awssdk.services.pinpoint.model.EventsBatch
 *  software.amazon.awssdk.services.pinpoint.model.EventsRequest
 *  software.amazon.awssdk.services.pinpoint.model.PublicEndpoint
 *  software.amazon.awssdk.services.pinpoint.model.PutEventsRequest
 *  software.amazon.awssdk.services.pinpoint.model.Session
 *  software.amazon.awssdk.utils.ImmutableMap
 */
package com.amazonaws.services.dynamodbv2.local.monitoring;

import com.amazonaws.services.dynamodbv2.local.main.AppConfig;
import com.amazonaws.services.dynamodbv2.local.monitoring.TelemetryMetaData;
import com.amazonaws.services.dynamodbv2.local.monitoring.TelemetryUtil;
import com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteLibraryLoaderUtil;
import com.amazonaws.services.dynamodbv2.local.shared.logging.LogManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import java.io.File;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClientBuilder;
import software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityResponse;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdResponse;
import software.amazon.awssdk.services.pinpoint.PinpointClient;
import software.amazon.awssdk.services.pinpoint.PinpointClientBuilder;
import software.amazon.awssdk.services.pinpoint.model.EndpointDemographic;
import software.amazon.awssdk.services.pinpoint.model.EndpointUser;
import software.amazon.awssdk.services.pinpoint.model.Event;
import software.amazon.awssdk.services.pinpoint.model.EventsBatch;
import software.amazon.awssdk.services.pinpoint.model.EventsRequest;
import software.amazon.awssdk.services.pinpoint.model.PublicEndpoint;
import software.amazon.awssdk.services.pinpoint.model.PutEventsRequest;
import software.amazon.awssdk.services.pinpoint.model.Session;
import software.amazon.awssdk.utils.ImmutableMap;

public class Telemetry {
    private static final Region DEFAULT_REGION = Region.US_EAST_1;
    private static final Logger logger = LogManager.getLogger(Telemetry.class);
    private static Boolean metadataConfigured = false;
    private static TelemetryMetaData telemetryMetaData;
    private static volatile Telemetry telemetry;
    private PinpointClient pinpoint;
    private String setUpMode = "";
    private String setUpType = "";
    private Boolean enabled;

    private Telemetry() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!metadataConfigured.booleanValue()) {
            try {
                String metadataFilePath = Paths.get(System.getProperty("user.dir"), "dynamodb-local-metadata.json").toString();
                if (!metadataFilePath.isEmpty()) {
                    File metadataFile = new File(metadataFilePath);
                    metadataConfigured = this.configureMetaDataFile(metadataFile, objectMapper);
                }
            } catch (Exception e) {
                logger.debug("Failed to create metadata file", (Throwable)e);
            }
        }
        if (metadataConfigured.booleanValue()) {
            try {
                this.pinpoint = Telemetry.getPinpointClient();
            } catch (Exception e) {
                logger.debug("Failed to create Pinpoint client", (Throwable)e);
            }
        }
    }

    public static PinpointClient getPinpointClient() {
        CognitoIdentityClient cognitoClient = (CognitoIdentityClient)((CognitoIdentityClientBuilder)CognitoIdentityClient.builder().region(DEFAULT_REGION)).build();
        GetIdRequest idRequest = (GetIdRequest)GetIdRequest.builder().identityPoolId("us-east-1:a199bca5-0526-4f31-8e8f-2b4a3f4344ca").build();
        GetIdResponse idResponse = cognitoClient.getId(idRequest);
        GetCredentialsForIdentityRequest getCredentialsForIdentityRequest = (GetCredentialsForIdentityRequest)GetCredentialsForIdentityRequest.builder().identityId(idResponse.identityId()).build();
        GetCredentialsForIdentityResponse getCredentialsForIdentityResponse = cognitoClient.getCredentialsForIdentity(getCredentialsForIdentityRequest);
        AwsCredentialsProvider awsCredentialsProvider = () -> AwsSessionCredentials.create((String)getCredentialsForIdentityResponse.credentials().accessKeyId(), (String)getCredentialsForIdentityResponse.credentials().secretKey(), (String)getCredentialsForIdentityResponse.credentials().sessionToken());
        return (PinpointClient)((PinpointClientBuilder)((PinpointClientBuilder)PinpointClient.builder().region(DEFAULT_REGION)).credentialsProvider(awsCredentialsProvider)).build();
    }

    public static Optional<Telemetry> getTelemetry() {
        if (telemetry == null) {
            logger.debug("Telemetry has not been set up");
            return Optional.empty();
        }
        return Optional.ofNullable(telemetry);
    }

    public static void configureTelemetry(String setupType, String setupMode, boolean telemetryEnabled) {
        if (telemetry == null && telemetryEnabled) {
            telemetry = new Telemetry();
        }
        if (telemetry != null) {
            telemetry.setSetUpType(setupType);
            telemetry.setSetUpMode(setupMode);
            telemetry.setIsEnabled(telemetryEnabled);
        }
    }

    static boolean verifyMetaData(File metadataFile, ObjectMapper objectMapper) {
        try {
            telemetryMetaData = objectMapper.readValue(metadataFile, TelemetryMetaData.class);
            return true;
        } catch (Exception e) {
            logger.debug("Failed to verify format for metadata file", (Throwable)e);
            return false;
        }
    }

    static boolean writeMetadataFile(TelemetryMetaData metadata, File metadataFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(metadataFile, (Object)metadata);
            return true;
        } catch (Exception e) {
            logger.debug("Failed to write metadata file", (Throwable)e);
            return false;
        }
    }

    static boolean createMetadataFile(File metadataFile, ObjectMapper objectMapper) {
        try {
            if (metadataFile.createNewFile()) {
                String uuid = UUID.randomUUID().toString();
                String telemetryEnabled = "true";
                TelemetryMetaData telemetryMetaData = new TelemetryMetaData(uuid, telemetryEnabled);
                Telemetry.writeMetadataFile(telemetryMetaData, metadataFile);
            }
        } catch (Exception e) {
            logger.debug("Failed to create metadata file", (Throwable)e);
            return false;
        }
        return Telemetry.verifyMetaData(metadataFile, objectMapper);
    }

    static String translateOSName(String osName) {
        if (osName.contains("Windows")) {
            return OS_NAME.WINDOWS.name();
        }
        if (osName.contains("Mac") || osName.contains("Darwin")) {
            return OS_NAME.MAC.name();
        }
        if (osName.contains("Linux")) {
            return OS_NAME.LINUX.name();
        }
        if (osName.equals("")) {
            return OS_NAME.UNKNOWN.name();
        }
        return osName.replaceAll("\\W", "").toUpperCase();
    }

    static String getOSName() {
        return Telemetry.translateOSName(System.getProperty("os.name"));
    }

    static EndpointUser getEndpointUser(String installationId) {
        return (EndpointUser)EndpointUser.builder().userId(installationId).build();
    }

    static EndpointDemographic getEndpointDemographic(String platform, String appVersion, String make) {
        return (EndpointDemographic)EndpointDemographic.builder().platform(platform).appVersion(appVersion).make(make).build();
    }

    static PublicEndpoint getPublicEndpoint(EndpointUser endpointUser, EndpointDemographic endpointDemographic, Map customerAttributes) {
        return (PublicEndpoint)PublicEndpoint.builder().user(endpointUser).demographic(endpointDemographic).attributes(customerAttributes).build();
    }

    static Session getSession() {
        return (Session)Session.builder().id(UUID.randomUUID().toString()).startTimestamp(Instant.now().toString()).build();
    }

    void setIsEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    Boolean isEnabled() {
        return this.enabled;
    }

    String getSetUpMode() {
        return this.setUpMode;
    }

    void setSetUpMode(String setUpMode) {
        this.setUpMode = setUpMode;
    }

    String getSetUpType() {
        return this.setUpType;
    }

    void setSetUpType(String setUpType) {
        this.setUpType = setUpType;
    }

    boolean configureMetaDataFile(File metadataFile, ObjectMapper objectMapper) {
        if (metadataFile.exists()) {
            return Telemetry.verifyMetaData(metadataFile, objectMapper);
        }
        return Telemetry.createMetadataFile(metadataFile, objectMapper);
    }

    boolean canEmitEvent() {
        return AppConfig.TELEMETRY_FEATURE_ENABLED != false && metadataConfigured != false && this.pinpoint != null && !this.setUpType.isEmpty() && !this.setUpMode.isEmpty();
    }

    public void emitEvent(TelemetryUtil.EVENT_TYPE eventType) {
        if (this.canEmitEvent()) {
            SQLiteLibraryLoaderUtil sqLiteLibraryLoaderUtil = new SQLiteLibraryLoaderUtil();
            HashMap<String, ArrayList<String>> customerAttributes = new HashMap<String, ArrayList<String>>();
            customerAttributes.put("SETUP_TYPE", new ArrayList<String>(Collections.singleton(this.setUpType)));
            customerAttributes.put("SETUP_MODE", new ArrayList<String>(Collections.singleton(this.setUpMode)));
            customerAttributes.put("JAVA_VERSION", new ArrayList<String>(Collections.singleton(SQLiteLibraryLoaderUtil.getJavaVersion())));
            EndpointUser endpointUser = Telemetry.getEndpointUser(telemetryMetaData.getInstallationId());
            EndpointDemographic endpointDemographic = Telemetry.getEndpointDemographic(Telemetry.getOSName(), "2.4.0", SQLiteLibraryLoaderUtil.getArchName());
            PublicEndpoint publicEndpoint = Telemetry.getPublicEndpoint(endpointUser, endpointDemographic, customerAttributes);
            ImmutableMap event = ImmutableMap.of((Object)UUID.randomUUID().toString(), (Object)((Event)Event.builder().timestamp(String.valueOf(Instant.now())).session(Telemetry.getSession()).eventType(eventType.toString()).build()));
            EventsRequest eventsRequest = (EventsRequest)EventsRequest.builder().batchItem((Map)ImmutableMap.of((Object)telemetryMetaData.getInstallationId(), (Object)((EventsBatch)EventsBatch.builder().endpoint(publicEndpoint).events((Map)event).build()))).build();
            PutEventsRequest putEventsRequest = (PutEventsRequest)PutEventsRequest.builder().applicationId("5ebafb660937499a859886beacaaca6b").eventsRequest(eventsRequest).build();
            this.pinpoint.putEvents(putEventsRequest);
        }
    }

    @VisibleForTesting
    protected static enum OS_NAME {
        WINDOWS,
        MAC,
        LINUX,
        UNKNOWN;

    }
}

