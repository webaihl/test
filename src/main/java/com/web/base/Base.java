package com.web.base;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * @author web
 * @title: Base
 * @projectName javacode
 * @description: TODO
 * @date 19-9-11下午9:10
 */
public class Base {

    public static void main(String[] args) {
        Integer num1 = new Integer(121800);
        Integer num2 = new Integer(121800);
        System.out.println(num1.equals(num2));
        System.out.println(Objects.equals(num1, num2));

        TreeMap<User, String> treeMap = new TreeMap<>();
        treeMap.put(new User("web", 12), "web");
        treeMap.put(new User("tank", 10), "tank");
        treeMap.put(new User("alice", 100), "alice");
        treeMap.forEach((k,v)->System.out.println(v));

        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("1","2","3","2"));
        System.out.println(arrayList);

        //arrayList.removeIf(item -> item.indexOf() != )
        Stream.iterate(0, i->i+1).limit(arrayList.size()).forEach(i->{
            arrayList.removeIf(item -> item.indexOf(item) != i);
        });

        //new ArrayList<>(new TreeSet<>(arrayList));

    }


}
