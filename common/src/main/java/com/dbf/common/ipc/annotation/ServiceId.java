package com.dbf.common.ipc.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//保留到运行时，反射需要用到
@Target(ElementType.TYPE)//目标
public @interface ServiceId {
    String value();
}
