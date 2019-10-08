package com.web.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author web
 * @title: User
 * @projectName javacode
 * @description: TODO
 * @date 19-9-11下午9:42
 */
@Setter
@Getter
@AllArgsConstructor
public class User implements Comparable<User>, Serializable {

    private String name;
    private Integer age;


    @Override
    public int compareTo(User o) {
        if (this.age > o.age){
            return 1;
        }else if (this.age < o.age){
            return -1;
        }
        return age;
    }
}
