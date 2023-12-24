package com.search.trek.domain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class ResultCodeBuilder {
    private static String application;
    private static Environment environment;

    @Autowired
    public void ResultCodeBuilder(Environment environment) {
        this.environment = environment;
        this.application = environment.getProperty("spring.application.name");
    }

    public static Result of(String applicationCode, String domainCode, String sceneCode, String errorCode, String msg) {
        String strCode = applicationCode +  domainCode +  sceneCode + errorCode;
        return new Result(Integer.valueOf(strCode), msg);
    }

    public static Result of(String domainCode, String sceneCode, String errorCode, String msg) {
        return of(ApplicationEnum.getCode(application), domainCode, sceneCode, errorCode, msg);
    }

    public static Result of(String errorCode, String msg) {
        return of("000", "000", errorCode, msg);
    }

    public static int toOut(int innerCode) {
        return innerCode % 10000;
    }

    public static void main(String[] args) {
        System.out.println(innerToOut("010122101"));
    }

    public static int innerToOut(String innerErrorCode) {
        String strInnerCode = innerErrorCode + "";
        if (strInnerCode.length()<4) throw new IllegalArgumentException("innerErrorCode length is at least 4");
        return Integer.parseInt(strInnerCode.substring(strInnerCode.length() - 4));
    }

    @Getter
    public enum ApplicationEnum {
        APP_FINANCE_FRONT_LIGHT_SERVICE("AppFinanceFrontLightService", "101"),
        DEFAULT("default", "100");
        private String application;
        private String code;

        ApplicationEnum(String application) {
            this.application = application;
        }

        ApplicationEnum(String application, String code) {
            this.application = application;
            this.code = code;
        }

        public static String getCode(String application) {
            return Arrays.stream(ApplicationEnum.values())
                    .filter(applicationEnum -> applicationEnum.getApplication().equals(application))
                    .findFirst()
                    .orElse(ApplicationEnum.DEFAULT)
                    .getCode();
        }
    }
}
