// IMyAidlInterface.aidl
package com.dbf.studyandtest.myaidl;

// Declare any non-default types here with import statements
import com.dbf.studyandtest.myaidl.Person;
interface IMyAidlInterface {
void addPerson(inout Person person);
List<Person> getPersonList();
}
