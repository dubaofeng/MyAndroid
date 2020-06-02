package com.dbf.studyandtest.javastudy.myannotation;

import java.lang.annotation.Target;

public @interface Test {
    String name() default "test";
}
