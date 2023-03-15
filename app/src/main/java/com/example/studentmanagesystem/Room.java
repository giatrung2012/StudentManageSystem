package com.example.studentmanagesystem;

import java.io.Serializable;

public class Room implements Serializable {
    String id_class;
    String code_class;
    String name_class;
    String class_number;

    public Room(String id_class, String code_class, String name_class, String class_number) {
        this.id_class = id_class;
        this.code_class = code_class;
        this.name_class = name_class;
        this.class_number = class_number;
    }

    public Room(String code_class, String name_class, String class_number) {
        this.code_class = code_class;
        this.name_class = name_class;
        this.class_number = class_number;
    }

    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    public String getCode_class() {
        return code_class;
    }

    public void setCode_class(String code_class) {
        this.code_class = code_class;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getClass_number() {
        return class_number;
    }

    public void setClass_number(String class_number) {
        this.class_number = class_number;
    }

    @Override
    public String toString() {
        return code_class;
    }
}
