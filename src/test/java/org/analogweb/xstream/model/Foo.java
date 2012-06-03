package org.analogweb.xstream.model;

import java.util.Date;

/**
 * @author snowgoose
 */
public class Foo {

    private String name = "foo";
    private int age = 34;
    private Date birthDay;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public Date getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
