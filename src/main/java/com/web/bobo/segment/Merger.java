package com.web.bobo.segment;

/**
 * @author web
 * @version 1.0.0
 * @ClassName Meger.java
 * @Description
 * @createTime 2020年09月17日 21:31:00
 */

@FunctionalInterface
public interface Merger<E> {
    E merger(E a, E b);
}
