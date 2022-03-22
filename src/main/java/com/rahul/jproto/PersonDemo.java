package com.rahul.jproto;
import  com.rahul.*;
import com.rahul.model.Person;

public class PersonDemo {
    public static void main (String args[]){
        com.rahul.model.Person p= Person.newBuilder().setAge(10).setName("rahul").build();
        System.out.println("the person is  :"+p);
    }
}
