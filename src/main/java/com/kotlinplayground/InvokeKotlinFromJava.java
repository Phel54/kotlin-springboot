package com.kotlinplayground;

import com.kotlinspring.entity.Course;
import com.kotlinspring.entity.Instructor;

import java.util.ArrayList;

public class InvokeKotlinFromJava {
    public static void main(String[] args) {
        Course course = new Course(1,"Build RestFul APis using SpringBoot and Kotlin", "Devlopment",
                new Instructor(1,"Phelim",new ArrayList<>()));
    }
}
