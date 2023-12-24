package com.search.trek.domain;

public class Results {
    /**
     * 使用ok()代替
     */
    @Deprecated
    public static final Result OK = new Result(0, "ok");

    public static <T> Result<T> ok() {
        return new Result<>(0, "ok");
    }

    public static <R, T extends R> Result<R> ok(T data) {
        return new Result<>(0, null, data);
    }

    /**
     * 使用build代替
     */
    @Deprecated
    public static <T> Result<T> ok(int code, String msg) {
        return new Result<>(code, msg);
    }


    /**
     * 建议替换为build
     */
    @Deprecated
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 不建议使用
     */
    @Deprecated
    public static <T> Result<T> fail(int code, String msg, Object... formatParams) {
        return new Result<>(code, String.format(msg, formatParams));
    }

    /**
     * 不建议使用
     */
    @Deprecated
    public static <T> Result<T> fail(int code, T data) {
        return new Result<>(code, data);
    }

    public static <T> Result<T> build(int code, String msg) {
        return new Result<>(code, msg);
    }
    public static <T> Result<T> build(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }
}
