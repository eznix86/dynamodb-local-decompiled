/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ddb.partiql.shared.exceptions;

import java.util.Optional;
import org.partiql.lang.ast.HasMetas;

public class ExceptionMessageBuilder {
    private String exceptionMessage;
    private String sourceLocation;

    public ExceptionMessageBuilder(String exceptionMessage) {
        this(exceptionMessage, null);
    }

    public ExceptionMessageBuilder(String exceptionMessage, HasMetas component) {
        this.exceptionMessage = exceptionMessage;
        this.sourceLocation = this.getSourceLocation(component);
    }

    public String build(Object ... params) {
        Object message = String.format(this.exceptionMessage, params);
        if (this.sourceLocation != null) {
            message = (String)message + " at " + this.sourceLocation;
        }
        return message;
    }

    private String getSourceLocation(HasMetas component) {
        String source = Optional.ofNullable(component).map(HasMetas::getMetas).map(e -> e.find("$source_location")).map(Object::toString).orElse(null);
        return source;
    }
}

