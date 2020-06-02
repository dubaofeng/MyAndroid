package com.dbf.javastudy.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMethod {
    public void main() {
        try {
            Class clazz = Class.forName("com.dbf.javastudy.reflect.Student");
            System.out.println("获取clazz对应类中所有的公有方法,包括从父类继承来的公有方法");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                System.out.println("" + method);
            }
            System.out.println("----------------------------------");
            System.out.println("获取当前类的所有方法，包括私有方法，且" +
                    "只能获取当前类的方法");
            Method[] declaredmethods = clazz.getDeclaredMethods();
            for (Method method : declaredmethods) {
                System.out.println("" + method);
            }
            System.out.println("获取指定方法，需要参数名称和参数列表，无参则不需要传");
            Method method = clazz.getMethod("setName", String.class);
            System.out.println("" + method);
            System.out.println("执行取得的方法");
            Object object = clazz.newInstance();
            method.invoke(object, "lisi");
            System.out.println("method.invoke=" + ((Student) object).getName());
            System.out.println("执行私有方法");
            Method privateMethod = clazz.getDeclaredMethod("privateMethod", String.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(object, "I ma privateMethod");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
