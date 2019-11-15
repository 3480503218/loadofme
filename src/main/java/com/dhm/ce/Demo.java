package com.dhm.ce;

import java.io.File;
import java.util.UUID;

public class Demo {
    public static void main(String[] args) {

//        String string="2556240b-e531-4947-9fc1-45f59808be70_613127884.docx";
//        int i = string.hashCode();
//        System.out.println(i);
//        int a= i & 0xff;
//        System.out.println(a);
//        System.out.println(i & 0xff0);
//        int d2 = (i & 0xff0) >> 4;
        //System.out.println(d2);String string="2556240b-e531-4947-9fc1-45f59808be70_613127884.docx";
        //        int i = string.hashCode();
//        for (int j = 0; j < 10; j++) {
//            String string="2556240b-e531-4947-9fc1-45f59808be70_613127884.docx";
//            int i = string.hashCode();
//            System.out.println(i);
//            System.out.println(UUID.randomUUID().toString());
//        }

        //System.out.println(UUID.randomUUID().toString());

//        String name="redis教案-郭永峰.docx";
//        String substring = name.substring(0,name.lastIndexOf("."));
      //  System.out.println(substring);

        File file=new File("/Users/dhm/000/000/");
//        System.out.println(file.getParentFile());
       // System.out.println(file.listFiles().toString());
        String[] list = file.list();
        for(String str:list){
            System.out.println(str);
        }
//        System.out.println(file.list());
//        System.out.println(file.exists());
//        System.out.println(file.getName());


    }
}
