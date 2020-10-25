package com.web.bobo.map;

import java.util.Objects;

/**
 * @author web
 * @version 1.0.0
 * @ClassName Student.java
 * @Description
 * @createTime 2020年10月14日 10:56:00
 */
public class Student {

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

