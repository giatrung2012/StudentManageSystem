package com.example.studentmanagesystem;

import java.io.Serializable;

public class Student implements Serializable {
    String id_class;
    String name_class;
    String id_student;
    String code_student;
    String name_student;
    String gender_student;
    String birthday_student;
    String address_student;

    public Student(String id_class, String name_class, String id_student, String code_student, String name_student, String gender_student, String birthday_student, String address_student) {
        this.id_class = id_class;
        this.name_class = name_class;
        this.id_student = id_student;
        this.code_student = code_student;
        this.name_student = name_student;
        this.gender_student = gender_student;
        this.birthday_student = birthday_student;
        this.address_student = address_student;
    }

    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getId_student() {
        return id_student;
    }

    public void setId_student(String id_student) {
        this.id_student = id_student;
    }

    public String getCode_student() {
        return code_student;
    }

    public void setCode_student(String code_student) {
        this.code_student = code_student;
    }

    public String getName_student() {
        return name_student;
    }

    public void setName_student(String name_student) {
        this.name_student = name_student;
    }

    public String getGender_student() {
        return gender_student;
    }

    public void setGender_student(String gender_student) {
        this.gender_student = gender_student;
    }

    public String getBirthday_student() {
        return birthday_student;
    }

    public void setBirthday_student(String birthday_student) {
        this.birthday_student = birthday_student;
    }

    public String getAddress_student() {
        return address_student;
    }

    public void setAddress_student(String address_student) {
        this.address_student = address_student;
    }
}
