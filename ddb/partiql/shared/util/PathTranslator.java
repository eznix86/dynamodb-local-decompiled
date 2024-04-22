/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazon.ion.IntegerSize;
import com.amazon.ion.IonInt;
import com.amazon.ion.IonString;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import java.util.ArrayList;
import java.util.Collections;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Literal;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.PathComponent;
import org.partiql.lang.ast.PathComponentExpr;
import org.partiql.lang.ast.PathComponentUnpivot;
import org.partiql.lang.ast.PathComponentWildcard;
import org.partiql.lang.ast.Seq;
import org.partiql.lang.ast.VariableReference;

public final class PathTranslator {
    private PathTranslator() {
    }

    public static DocPath translateToDocPath(VariableReference variableReference, PartiQLDbEnv dbEnv) {
        String id = variableReference.getId();
        PathTranslator.checkForEmptyPathString(id, dbEnv);
        return new DocPath(Collections.singletonList(new DocPathMapElement(id)));
    }

    public static DocPath translateToDocPath(Path partiQLPath, PartiQLDbEnv dbEnv) {
        ExprNode rootExprNode = partiQLPath.getRoot();
        if (rootExprNode instanceof Seq) {
            throw dbEnv.createValidationError(new ExceptionMessageBuilder("A path cannot start with a list index", rootExprNode).build(new Object[0]));
        }
        if (!(rootExprNode instanceof VariableReference)) {
            throw dbEnv.createValidationError(new ExceptionMessageBuilder("Unexpected path component", rootExprNode).build(new Object[0]));
        }
        ArrayList<DocPathElement> pathElements = new ArrayList<DocPathElement>(1 + partiQLPath.getComponents().size());
        pathElements.add(PathTranslator.translatePathComponentToDocPathElement(rootExprNode, dbEnv));
        for (PathComponent component : partiQLPath.getComponents()) {
            if (component instanceof PathComponentUnpivot) {
                throw dbEnv.createValidationError(new ExceptionMessageBuilder("Unpivot path component like a.*.b are not supported", (PathComponentUnpivot)component).build(new Object[0]));
            }
            if (component instanceof PathComponentWildcard) {
                throw dbEnv.createValidationError(new ExceptionMessageBuilder("Wildcard path component like a[*].b are not supported", (PathComponentWildcard)component).build(new Object[0]));
            }
            if (component instanceof PathComponentExpr) {
                pathElements.add(PathTranslator.translatePathComponentToDocPathElement(((PathComponentExpr)component).getExpr(), dbEnv));
                continue;
            }
            throw dbEnv.createValidationError("Unexpected path component");
        }
        return new DocPath(pathElements);
    }

    private static DocPathElement translatePathComponentToDocPathElement(ExprNode pathComponentAsExprNode, PartiQLDbEnv dbEnv) {
        if (pathComponentAsExprNode instanceof VariableReference) {
            String id = ((VariableReference)pathComponentAsExprNode).getId();
            PathTranslator.checkForEmptyPathString(id, dbEnv);
            return new DocPathMapElement(id);
        }
        if (pathComponentAsExprNode instanceof Literal) {
            Literal literal = (Literal)pathComponentAsExprNode;
            IonValue componentAsIon = literal.getIonValue();
            if (IonType.STRING == componentAsIon.getType()) {
                String id = ((IonString)componentAsIon).stringValue();
                PathTranslator.checkForEmptyPathString(id, dbEnv);
                return new DocPathMapElement(id);
            }
            if (IonType.INT == componentAsIon.getType()) {
                IonInt componentAsInt = (IonInt)componentAsIon;
                IntegerSize componentAsIntSize = componentAsInt.getIntegerSize();
                if (componentAsIntSize != IntegerSize.INT || componentAsInt.intValue() < 0) {
                    throw dbEnv.createValidationError(new ExceptionMessageBuilder("List index is not within the allowable range; index: [%s]", pathComponentAsExprNode).build(componentAsInt.bigIntegerValue().toString()));
                }
                return new DocPathListElement(componentAsInt.intValue());
            }
            throw dbEnv.createValidationError(new ExceptionMessageBuilder("Unexpected path component", pathComponentAsExprNode).build(new Object[0]));
        }
        throw dbEnv.createValidationError(new ExceptionMessageBuilder("Unexpected path component", pathComponentAsExprNode).build(new Object[0]));
    }

    public static void checkForEmptyPathString(String id, PartiQLDbEnv dbEnv) {
        if ("".equals(id)) {
            throw dbEnv.createValidationError("Path component cannot be an empty string");
        }
    }
}

