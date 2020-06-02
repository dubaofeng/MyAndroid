package com.dbf.studyandtest.javastudy.reflect;

import java.lang.reflect.Field;

public class TestField {
    public void main() {
        try {
            Class clazz = Class.forName("com.dbf.studyandtest.javastudy.reflect.Student");
            Student student = (Student) clazz.newInstance();
            student.setName("my name is TestField");
            System.out.println("获取当前类所有公有字段，包括继承来的公有字段");
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                System.out.println(field);
            }
            System.out.println("获取当前类所有字段，包括私有字段，不能获取父类字段");
            Field[] declaredfields = clazz.getDeclaredFields();
            for (Field field : declaredfields) {
                System.out.println(field);
            }

            System.out.println("获取指定公有字段");
            Field hobbyfield = clazz.getDeclaredField("hobby");
            System.out.println("hobbyfield=" + hobbyfield.getName());
            System.out.println("hobbyfield=" + hobbyfield.get(student));


            System.out.println("获取指定私有字段");
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            System.out.println("field=" + field.get(student));
            field.set(student, "my name is TestField2");
            System.out.println("field=" + field.get(student));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }
}
