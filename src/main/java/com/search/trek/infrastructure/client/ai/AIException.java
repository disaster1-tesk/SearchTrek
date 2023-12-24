package com.search.trek.infrastructure.client.ai;

import lombok.Getter;

@Getter
public class AIException extends RuntimeException{
    private final String msg;
    private final String code;

    public AIException(IError error) {
        super(error.msg());
        this.code = error.code();
        this.msg = error.msg();
    }

    public AIException(String msg) {
        super(msg);
        this.code = CommonError.SYS_ERROR.code();
        this.msg = msg;
    }

    public AIException() {
        super(CommonError.SYS_ERROR.msg());
        this.code = CommonError.SYS_ERROR.code();
        this.msg = CommonError.SYS_ERROR.msg();
    }
}
