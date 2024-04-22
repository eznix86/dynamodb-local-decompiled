/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.parser;

import com.amazonaws.services.dynamodbv2.datamodel.DocPath;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathListElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocPathMapElement;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentFactory;
import com.amazonaws.services.dynamodbv2.datamodel.DocumentNode;
import com.amazonaws.services.dynamodbv2.datamodel.ExpressionValidator;
import com.amazonaws.services.dynamodbv2.rr.ProjectionExpressionWrapper;
import ddb.partiql.shared.dbenv.PartiQLDbEnv;
import ddb.partiql.shared.exceptions.ExceptionMessageBuilder;
import ddb.partiql.shared.parser.ExpressionParser;
import ddb.partiql.shared.util.PartiQLToAttributeValueConverter;
import ddb.partiql.shared.util.PathTranslator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.partiql.lang.ast.ExprNode;
import org.partiql.lang.ast.Path;
import org.partiql.lang.ast.SelectListItem;
import org.partiql.lang.ast.SelectListItemExpr;
import org.partiql.lang.ast.SelectListItemProjectAll;
import org.partiql.lang.ast.SelectListItemStar;
import org.partiql.lang.ast.SelectProjection;
import org.partiql.lang.ast.SelectProjectionList;
import org.partiql.lang.ast.VariableReference;

public class ProjectionExpressionParser<E, R, V extends DocumentNode>
extends ExpressionParser<E, R, V> {
    public ProjectionExpressionParser(PartiQLToAttributeValueConverter<E, R, V> converter, PartiQLDbEnv dbEnv, ExpressionValidator validator, DocumentFactory documentFactory) {
        super(converter, dbEnv, validator, documentFactory);
    }

    public ProjectionExpressionWrapper getProjectionExpression(SelectProjection selectProjection, E opContext) {
        if (!(selectProjection instanceof SelectProjectionList)) {
            throw this.dbEnv.createValidationError("Select projection doesn't support value or pivot keywords");
        }
        SelectProjectionList projections = (SelectProjectionList)selectProjection;
        ArrayList<DocPath> docPaths = new ArrayList<DocPath>();
        for (SelectListItem listItem : projections.getItems()) {
            if (listItem instanceof SelectListItemStar) {
                return null;
            }
            if (listItem instanceof SelectListItemProjectAll) {
                throw this.dbEnv.createValidationError("Changing item's hierarchy while reading isn't supported");
            }
            if (listItem instanceof SelectListItemExpr) {
                SelectListItemExpr selectListItemExpr = (SelectListItemExpr)listItem;
                ExprNode exprNode = selectListItemExpr.getExpr();
                if (selectListItemExpr.getAsName() != null) {
                    throw this.dbEnv.createValidationError("Aliasing is not supported");
                }
                if (exprNode instanceof VariableReference) {
                    docPaths.add(PathTranslator.translateToDocPath((VariableReference)exprNode, this.dbEnv));
                    continue;
                }
                if (exprNode instanceof Path) {
                    docPaths.add(PathTranslator.translateToDocPath((Path)exprNode, this.dbEnv));
                    continue;
                }
                throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Unexpected path component", exprNode).build(new Object[0]));
            }
            throw this.dbEnv.createValidationError("Unexpected path component");
        }
        this.validateNoDuplicatePaths(docPaths);
        return new ProjectionExpressionWrapper(docPaths, this.validator);
    }

    private void validateNoDuplicatePaths(List<DocPath> docPaths) {
        HashSet<DocPathMapElement> docPathMapElements = new HashSet<DocPathMapElement>();
        for (DocPath docPath : docPaths) {
            DocPathMapElement docPathMapElement = this.collapseDocPath(docPath);
            if (docPathMapElements.add(this.collapseDocPath(docPath))) continue;
            throw this.dbEnv.createValidationError(new ExceptionMessageBuilder("Duplicate identifiers in select clause: %s").build(docPathMapElement.getFieldName()));
        }
    }

    private DocPathMapElement collapseDocPath(DocPath docPath) {
        List<DocPathElement> docPathElements = docPath.getElements();
        Object docPathString = "";
        for (DocPathElement element : docPathElements) {
            if (element instanceof DocPathMapElement) {
                docPathString = element.getFieldName();
                continue;
            }
            if (!(element instanceof DocPathListElement)) continue;
            docPathString = (String)docPathString + "[" + element.getListIndex() + "]";
        }
        return new DocPathMapElement((String)docPathString);
    }
}

