package com.dhm.ce;

import java.util.ArrayList;
import java.util.List;

public class Demo02 {
    public static void main(String[] args) {

        String name="董浩淼(1)";
        List<String> list=new ArrayList<String>();
        list.add("aa");
        list.add("董浩淼(1)");
        list.add("bb");
        list.add("cc");
        list.add("dd");
        list.add("ee");
        list.add("ff");
        if(list.contains(name)){
            int i=0;
            ++i;
            list.add(name+"("+i+")");

        }
        System.out.println(list);
    }

}
