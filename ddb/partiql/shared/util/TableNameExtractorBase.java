/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.util;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.util.PathTranslator;
import java.util.ArrayList;
import java.util.List;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.FromSource;
import org.partiql.lang.ast.FromSourceExpr;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.VariableReference;

public abstract class TableNameExtractorBase<E> {
    private static final String TABLE_NAME_PATTERN = "[a-zA-Z0-9_.-]+";
    private final double tableNameMinLength;
    private final double tableNameMaxLength;
    private final PartiQLDbEnv dbEnv;

    public TableNameExtractorBase(double tableNameMinLength, double tableNameMaxLength, PartiQLDbEnv dbEnv) {
        this.tableNameMinLength = tableNameMinLength;
        this.tableNameMaxLength = tableNameMaxLength;
        this.dbEnv = dbEnv;
    }

    protected abstract void addUnsupportedSyntaxCount(E var1, int var2);

    public String getTableName(FromSource fromSource, E opContext) {
        List<String> fromSourceComponents = this.getFromSourceComponents(fromSource, opContext);
        if (fromSourceComponents.size() > 1) {
            throw this.dbEnv.createValidationError("This operation is not supported on an index");
        }
        return fromSourceComponents.get(0);
    }

    public List<String> getFromSourceComponents(FromSource fromSource, E opContext) {
        DocPath docPath;
        if (!(fromSource instanceof FromSourceExpr)) {
            this.addUnsupportedSyntaxCount(opContext, 1);
            throw this.dbEnv.createValidationError("Only select from a single table or index is supported.");
        }
        FromSourceExpr fromSourceExpr = (FromSourceExpr)fromSource;
        if (fromSourceExpr.getVariables().isAnySpecified()) {
            this.addUnsupportedSyntaxCount(opContext, 1);
            throw this.dbEnv.createValidationError("Aliasing is not supported");
        }
        ExprNode exprNode = fromSourceExpr.getExpr();
        if (exprNode instanceof VariableReference) {
            docPath = PathTranslator.translateToDocPath((VariableReference)exprNode, this.dbEnv);
        } else if (exprNode instanceof Path) {
            docPath = PathTranslator.translateToDocPath((Path)exprNode, this.dbEnv);
        } else {
            this.addUnsupportedSyntaxCount(opContext, 1);
            throw this.dbEnv.createValidationError("Unexpected from source");
        }
        List<DocPathElement> docPathElements = docPath.getElements();
        if (docPathElements.size() > 2) {
            throw this.dbEnv.createValidationError("A path may contain at most 2 components in the FROM clause");
        }
        ArrayList<String> fromSourceComponents = new ArrayList<String>(docPathElements.size());
        for (DocPathElement docPathElement : docPathElements) {
            if (!(docPathElement instanceof DocPathMapElement)) {
                throw this.dbEnv.createValidationError("Invalid list expression in source.");
            }
            String tableName = docPathElement.getFieldName();
            if (!this.isValidTableName(tableName)) {
                throw this.dbEnv.createValidationError("Table name should be within the length [3, 255] and only contain 'a-z', 'A-Z', '0-9', '-', '_', '.'.");
            }
            fromSourceComponents.add(tableName);
        }
        return fromSourceComponents;
    }

    public String getTableName(ExprNode exprNode) {
        if (!(exprNode instanceof VariableReference)) {
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("FROM clause may only contain a single table name in data manipulation statements", exprNode).build(new Object[0]));
        }
        return ((VariableReference)exprNode).getId();
    }

    private boolean isValidTableName(String tableName) {
        return (double)tableName.length() >= this.tableNameMinLength && (double)tableName.length() <= this.tableNameMaxLength && tableName.matches(TABLE_NAME_PATTERN);
    }
}

