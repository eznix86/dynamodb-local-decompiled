/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.local.shared.access.api.dp;

import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.datamodel.ParameterMap;
import com.amazonaws.services.dynamodbv2.exceptions.AWSExceptionFactory;
import com.amazonaws.services.dynamodbv2.exceptions.AmazonServiceExceptionType;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBAccess;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBInputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.LocalDBOutputConverter;
import com.amazonaws.services.dynamodbv2.local.shared.access.api.dp.WriteDataPlaneFunction;
import com.amazonaws.services.dynamodbv2.local.shared.env.LocalPartiQLDbEnv;
import com.amazonaws.services.dynamodbv2.local.shared.helpers.TransactionsEnabledMode;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.model.PartiQLToAttributeValueConverter;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.DeleteStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.InsertStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.SelectStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.StatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.processor.UpdateStatementProcessor;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.CheckStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.DeleteStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.InsertStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.SelectStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.StatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.translator.UpdateStatementTranslator;
import com.amazonaws.services.dynamodbv2.local.shared.partiql.util.IonSerializer;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.partiql.lang.ast.AssignmentOp;
import org.partiql.lang.ast.DeleteOp;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.InsertValueOp;
import org.partiql.lang.ast.NAry;
import org.partiql.lang.ast.RemoveOp;
import org.partiql.lang.ast.Select;
import org.partiql.lang.syntax.SqlParser;
import org.partiql.lang.syntax.SyntaxException;

public abstract class PartiQLStatementFunction<I, O>
extends WriteDataPlaneFunction<I, O> {
    public static final SqlParser ION_SQL_PARSER = new SqlParser(IonSerializer.ION_SYSTEM);
    protected static final int MAX_STATEMENT_SIZE_BYTES = 8192;
    public final Map<Class, StatementProcessor> statementProcessors;
    public Map<Class, StatementTranslator> statementTranslators = this.populateStatementTranslators();

    PartiQLStatementFunction(LocalDBAccess dbAccess, PartiQLDbEnv partiQLDbEnv, LocalDBInputConverter inputConverter, LocalDBOutputConverter localDBOutputConverter, AWSExceptionFactory awsExceptionFactory, DocumentFactory documentFactory, TransactionsEnabledMode transactionsEnabledMode) {
        super(dbAccess, partiQLDbEnv, inputConverter, localDBOutputConverter, awsExceptionFactory, documentFactory, transactionsEnabledMode);
        this.statementProcessors = this.populateStatementProcessors(this.statementTranslators);
    }

    private Map<Class, StatementTranslator> populateStatementTranslators() {
        PartiQLToAttributeValueConverter converter = new PartiQLToAttributeValueConverter((PartiQLDbEnv)this.localDBEnv, this.documentFactory);
        ExpressionValidator validator = new ExpressionValidator(this.localDBEnv, new ParameterMap(Collections.emptyMap(), this.documentFactory));
        InsertStatementTranslator insertStatementTranslator = new InsertStatementTranslator(this.dbAccess, converter, (LocalPartiQLDbEnv)this.localDBEnv, validator, this.documentFactory);
        UpdateStatementTranslator updateStatementTranslator = new UpdateStatementTranslator(this.dbAccess, converter, (LocalPartiQLDbEnv)this.localDBEnv, validator, this.documentFactory);
        DeleteStatementTranslator deleteStatementTranslator = new DeleteStatementTranslator(this.dbAccess, converter, (LocalPartiQLDbEnv)this.localDBEnv, validator, this.documentFactory);
        SelectStatementTranslator selectStatementTranslator = new SelectStatementTranslator(this.dbAccess, converter, (LocalPartiQLDbEnv)this.localDBEnv, validator, this.documentFactory);
        CheckStatementTranslator checkStatementTranslator = new CheckStatementTranslator(this.dbAccess, converter, (LocalPartiQLDbEnv)this.localDBEnv, validator, this.documentFactory);
        HashMap<Class, StatementTranslator> statementTranslators = new HashMap<Class, StatementTranslator>();
        statementTranslators.put(DeleteOp.class, deleteStatementTranslator);
        statementTranslators.put(InsertValueOp.class, insertStatementTranslator);
        statementTranslators.put(Select.class, selectStatementTranslator);
        statementTranslators.put(AssignmentOp.class, updateStatementTranslator);
        statementTranslators.put(RemoveOp.class, updateStatementTranslator);
        statementTranslators.put(NAry.class, checkStatementTranslator);
        return statementTranslators;
    }

    private Map<Class, StatementProcessor> populateStatementProcessors(Map<Class, StatementTranslator> statementTranslators) {
        HashMap<Class, StatementProcessor> statementProcessors = new HashMap<Class, StatementProcessor>();
        InsertStatementProcessor insertStatementProcessor = new InsertStatementProcessor((InsertStatementTranslator)statementTranslators.get(InsertValueOp.class), this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        UpdateStatementProcessor updateStatementProcessor = new UpdateStatementProcessor((UpdateStatementTranslator)statementTranslators.get(AssignmentOp.class), this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        DeleteStatementProcessor deleteStatementProcessor = new DeleteStatementProcessor((DeleteStatementTranslator)statementTranslators.get(DeleteOp.class), this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        SelectStatementProcessor selectStatementProcessor = new SelectStatementProcessor((SelectStatementTranslator)statementTranslators.get(Select.class), this.dbAccess, (LocalPartiQLDbEnv)this.localDBEnv, this, this.documentFactory);
        statementProcessors.put(DeleteOp.class, deleteStatementProcessor);
        statementProcessors.put(InsertValueOp.class, insertStatementProcessor);
        statementProcessors.put(Select.class, selectStatementProcessor);
        statementProcessors.put(AssignmentOp.class, updateStatementProcessor);
        statementProcessors.put(RemoveOp.class, updateStatementProcessor);
        return statementProcessors;
    }

    protected ExprNode parseStatement(String statement) {
        try {
            return ION_SQL_PARSER.parseExprNode(statement);
        } catch (SyntaxException e) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("Statement wasn't well formed, can't be processed: %s", statement));
        }
    }

    protected void verifySupportedOperation(Class opClass) {
        if (!this.statementProcessors.containsKey(opClass)) {
            throw AWSExceptionFactory.buildAWSException(AmazonServiceExceptionType.VALIDATION_EXCEPTION, String.format("Unsupported operation: %s", opClass.getSimpleName()));
        }
    }
}

