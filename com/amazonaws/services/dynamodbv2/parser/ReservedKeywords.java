/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.amazonaws.services.dynamodbv2.parser;

import com.amazonaws.services.dynamodbv2.dbenv.DbEnv;
import com.amazonaws.services.dynamodbv2.dbenv.DbValidationError;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReservedKeywords {
    public static final Set<String> TOKENIZED_KEYWORDS = new HashSet<String>(Arrays.asList("add", "and", "between", "delete", "in", "not", "or", "remove", "set"));
    public static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList("abort", "absolute", "action", "add", "after", "agent", "aggregate", "all", "allocate", "alter", "analyze", "and", "any", "archive", "are", "array", "as", "asc", "ascii", "asensitive", "assertion", "asymmetric", "at", "atomic", "attach", "attribute", "auth", "authorization", "authorize", "auto", "avg", "back", "backup", "base", "batch", "before", "begin", "between", "bigint", "binary", "bit", "blob", "block", "boolean", "both", "breadth", "bucket", "bulk", "by", "byte", "call", "called", "calling", "capacity", "cascade", "cascaded", "case", "cast", "catalog", "char", "character", "check", "class", "clob", "close", "cluster", "clustered", "clustering", "clusters", "coalesce", "collate", "collation", "collection", "column", "columns", "combine", "comment", "commit", "compact", "compile", "compress", "condition", "conflict", "connect", "connection", "consistency", "consistent", "constraint", "constraints", "constructor", "consumed", "continue", "copy", "corresponding", "count", "counter", "create", "cross", "cube", "current", "cursor", "cycle", "data", "database", "date", "datetime", "day", "deallocate", "dec", "decimal", "declare", "default", "deferrable", "deferred", "define", "defined", "definition", "delete", "delimited", "depth", "deref", "desc", "describe", "descriptor", "detach", "deterministic", "diagnostics", "directories", "disable", "disconnect", "distinct", "distribute", "do", "domain", "double", "drop", "dump", "duration", "dynamic", "each", "element", "else", "elseif", "empty", "enable", "end", "equal", "equals", "error", "escape", "escaped", "eval", "evaluate", "exceeded", "except", "exception", "exceptions", "exclusive", "exec", "execute", "exists", "exit", "explain", "explode", "export", "expression", "extended", "external", "extract", "fail", "false", "family", "fetch", "fields", "file", "filter", "filtering", "final", "finish", "first", "fixed", "flattern", "float", "for", "for", "force", "foreign", "format", "forward", "found", "free", "from", "full", "function", "functions", "general", "generate", "get", "glob", "global", "go", "goto", "grant", "greater", "group", "grouping", "handler", "hash", "have", "having", "heap", "hidden", "hold", "hour", "identified", "identity", "if", "ignore", "immediate", "import", "in", "including", "inclusive", "increment", "incremental", "index", "indexed", "indexes", "indicator", "infinite", "initially", "inline", "inner", "innter", "inout", "input", "insensitive", "insert", "instead", "int", "integer", "intersect", "interval", "into", "invalidate", "is", "isolation", "item", "items", "iterate", "join", "key", "keys", "lag", "language", "large", "last", "lateral", "lead", "leading", "leave", "left", "length", "less", "level", "like", "limit", "limited", "lines", "list", "load", "local", "localtime", "localtimestamp", "location", "locator", "lock", "locks", "log", "loged", "long", "loop", "lower", "map", "match", "materialized", "max", "maxlen", "member", "merge", "method", "metrics", "min", "minus", "minute", "missing", "mod", "mode", "modifies", "modify", "module", "month", "multi", "multiset", "name", "name", "names", "national", "natural", "nchar", "nclob", "new", "next", "no", "none", "not", "null", "nullif", "number", "numeric", "object", "of", "offline", "offset", "old", "on", "online", "only", "opaque", "open", "operator", "option", "or", "order", "order", "ordinality", "other", "others", "out", "outer", "output", "over", "overlaps", "override", "owner", "pad", "parallel", "parameter", "parameters", "partial", "partition", "partitioned", "partitions", "path", "percent", "percentile", "permission", "permissions", "pipe", "pipelined", "plan", "pool", "position", "precision", "prepare", "preserve", "primary", "prior", "private", "privileges", "procedure", "processed", "project", "projection", "property", "provisioning", "public", "put", "query", "quit", "quorum", "raise", "random", "range", "rank", "raw", "read", "reads", "real", "rebuild", "record", "recursive", "reduce", "ref", "reference", "references", "referencing", "regexp", "region", "reindex", "relative", "release", "remainder", "remove", "rename", "rename", "repeat", "replace", "request", "reset", "resignal", "resource", "response", "restore", "restrict", "result", "return", "returning", "returns", "reverse", "revoke", "right", "role", "roles", "rollback", "rollup", "routine", "row", "rows", "rule", "rules", "sample", "satisfies", "save", "savepoint", "scan", "schema", "scope", "scroll", "search", "second", "section", "segment", "segments", "select", "self", "semi", "sensitive", "separate", "sequence", "serializable", "session", "set", "sets", "shard", "share", "shared", "short", "show", "signal", "similar", "skewed", "smallint", "snapshot", "some", "source", "space", "spaces", "sparse", "specific", "specifictype", "split", "sql", "sqlcode", "sqlerror", "sqlexception", "sqlstate", "sqlwarning", "start", "state", "static", "status", "storage", "store", "stored", "stream", "string", "struct", "style", "sub", "submultiset", "subpartition", "substring", "subtype", "sum", "super", "symmetric", "synonym", "system", "table", "tablesample", "temp", "temporary", "terminated", "text", "than", "then", "throughput", "time", "timestamp", "timezone", "tinyint", "to", "token", "total", "touch", "trailing", "transaction", "transform", "translate", "translation", "treat", "trigger", "trim", "true", "truncate", "ttl", "tuple", "type", "under", "undo", "union", "unique", "unit", "unknown", "unlogged", "unnest", "unprocessed", "unsigned", "until", "update", "upper", "url", "usage", "use", "user", "users", "using", "uuid", "vacuum", "value", "valued", "values", "varchar", "variable", "variance", "varint", "varying", "view", "views", "virtual", "void", "wait", "when", "whenever", "where", "while", "window", "with", "within", "without", "work", "wrapped", "write", "year", "zone"));

    public static void validateId(String id, DbEnv dbEnv) {
        if (ReservedKeywords.isKeyword(id)) {
            dbEnv.throwValidationError(DbValidationError.RESERVED_KEYWORD_ATTRIBUTE_NAME, "reserved keyword", id);
        }
    }

    public static boolean isKeyword(String id) {
        return KEYWORDS.contains(id.toLowerCase());
    }
}

