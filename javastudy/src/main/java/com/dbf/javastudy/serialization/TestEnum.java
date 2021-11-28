package com.dbf.javastudy.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by dbf on 2021/10/24
 * describe:
 */
public enum TestEnum {
    TWO,ONE,THREE;
    public void printValues() {
        System.out.println(ONE + " ONE.ordinal " + ONE.ordinal());
        System.out.println(TWO + " TWO.ordinal " + TWO.ordinal());
        System.out.println(THREE + " THREE.ordinal " + THREE.ordinal());
    }
    public static void testSerializable() throws Exception {
        TestEnum.ONE.printValues();
        File file = new File("p.dat");
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
//        oos.writeObject(TestEnum.ONE);
//        oos.close();

        System.out.println("=========反序列化后=======");

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        TestEnum s1 = (TestEnum) ois.readObject();
        s1.printValues();
        ois.close();
    }
}
