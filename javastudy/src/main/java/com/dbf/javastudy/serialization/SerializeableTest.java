package com.dbf.javastudy.serialization;

import com.dbf.javastudy.reflect.Person;
import com.dbf.studyandtest.proto.PersonInjava;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * Created by dbf on 2021/10/24
 * describe:
 */
public class SerializeableTest {
    public static void main(String[] args) throws  Exception{
        PersonInjava person = PersonInjava.newBuilder().setName("张三").setAge(18).build();
        System.out.println("protobuf="+person.getName()+"--"+person.getAge());
        //序列化
        byte[] bs=person.toByteArray();
        System.out.println("protobuf序列化后="+Arrays.toString(bs));
        PersonInjava person2=PersonInjava.parseFrom(bs);
        System.out.println("protobuf反序列化="+person2.getName()+"--"+person2.getAge());
        System.out.println("\n----------------分割线-------------\n");
        SPerson sPerson1 =new SPerson();
        sPerson1.setName("张三");
        sPerson1.setAge(18);
        System.out.println("serializable="+ sPerson1.getName()+"--"+ sPerson1.getAge());
        try {
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(sPerson1);
            byte[] bs2=  baos.toByteArray();
            System.out.println("serializable序列化后="+Arrays.toString(bs2));
            ObjectInputStream ojis=new ObjectInputStream(new ByteArrayInputStream(bs2));
            SPerson sPerson2 = (SPerson) ojis.readObject();
            System.out.println("serializable反序列化="+ sPerson2.getName()+"--"+ sPerson2.getAge());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
