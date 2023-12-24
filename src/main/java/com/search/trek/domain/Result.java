package com.search.trek.domain;

import lombok.Data;

@Data
public class Result<T> {
    public int code;

    public String msg;

    public T data;

    public Result() {

    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
