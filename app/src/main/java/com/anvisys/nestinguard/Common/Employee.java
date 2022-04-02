package com.anvisys.nestinguard.Common;

 public class Employee {
     private String id;
     private String name;

     public String getId() {
         return id;
     }

     public String getName() {
         return name;
     }

     public int getImage() {
         return image;
     }

     private int image;
//constucter
     public Employee(String id, String name, int image) {
         this.id = id;
         this.name = name;
         this.image = image;
     }
 }
