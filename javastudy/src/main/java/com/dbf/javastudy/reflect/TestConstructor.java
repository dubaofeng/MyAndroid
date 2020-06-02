package com.dbf.javastudy.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class TestConstructor {
    public void main() {
        Student student = new Student();
        student.setName("xiaoming");

        try {

            Class studentClass = Class.forName("com.dbf.javastudy.reflect.Student");
            Class studentClass1 = Student.class;
            Class studentClass2 = student.getClass();

            Student classStudent = (Student) studentClass.newInstance();
            classStudent.setName("xiaoqiang");
            System.out.println("\nreflect get Student name =" + classStudent.getName() + "\n\n\n");


            String className = "com.dbf.javastudy.reflect.Student";
            Class<Student> clazz = (Class<Student>) Class.forName(className);

            System.out.println("get All Constructor object:");
            Constructor<Student>[] constructors = (Constructor<Student>[]) clazz.getConstructors();
            for (Constructor<Student> constructor : constructors) {
                System.out.println(constructor);
            }
            System.out.println("\nget no arguments constructor");
            Constructor<Student> constructor = clazz.getConstructor();
            System.out.println(constructor);
            System.out.println("\nget arguments constructor");
            Constructor<Student> argumentsConstructor = clazz.getConstructor(String.class, int.class, boolean.class);
            System.out.println(argumentsConstructor);
            System.out.println("\nnewInstance");
            Student zhangsan = argumentsConstructor.newInstance("zhangsan", 18, true);
            System.out.println(zhangsan.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
