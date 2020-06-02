package com.dbf.studyandtest.javastudy.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public class TestConstructor {
    public void main() {
        Student student = new Student();
        student.setName("xiaoming");

        try {

            Class studentClass = Class.forName("com.dbf.studyandtest.javastudy.reflect.Student");
            Class studentClass1 = Student.class;
            Class studentClass2 = student.getClass();

            Student classStudent = (Student) studentClass.newInstance();
            classStudent.setName("小强");
            System.out.println("反射实例设置的名字:" + classStudent.getName());


            String className = "com.dbf.studyandtest.javastudy.reflect.Student";
            Class<Student> clazz = (Class<Student>) Class.forName(className);

            System.out.println("获取所有构造器:");
            Constructor<Student>[] constructors = (Constructor<Student>[]) clazz.getConstructors();
            for (Constructor<Student> constructor : constructors) {
                System.out.println(constructor);
            }
            System.out.println("获取无参构造器:");
            Constructor<Student> constructor = clazz.getConstructor();
            System.out.println(constructor);
            System.out.println("获取有参构造器:");
            Constructor<Student> argumentsConstructor = clazz.getConstructor(String.class, int.class, boolean.class);
            System.out.println(argumentsConstructor);
            System.out.println("使用构造器实例化:");
            Student zhangsan = argumentsConstructor.newInstance("张三", 18, true);
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
