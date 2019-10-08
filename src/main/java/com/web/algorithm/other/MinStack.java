package com.web.algorithm.other;

import java.util.Stack;

/**
 * 获取栈中最小值复杂度为为0(1)
 */
public class MinStack {
    private Stack<Integer> mainStack = new Stack<>();
    private Stack<Integer> minStack = new Stack<>();

    public void push(int e) {
        mainStack.push(e);
        //栈为空，或者小于最小值时
        if (minStack.isEmpty() || e <= minStack.peek()) {
            minStack.push(e);
        }
    }

    public Integer pop() {
        //两个栈顶元素相等时，一同出栈，保证元素的一致
        if (mainStack.peek().equals(minStack.peek())) {
            minStack.pop();
        }
        return mainStack.pop();
    }

    public Integer getMin() throws Exception {
        if (mainStack.isEmpty()) {
            throw new Exception("stack is empty");
        }
        return minStack.peek();
    }

    public static void main(String[] args) throws Exception {
        MinStack stack = new MinStack();
        stack.push(4);
        stack.push(9);
        stack.push(7);
        stack.push(3);
        stack.push(8);
        stack.push(5);

        System.out.println(stack.getMin());
        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println(stack.getMin());
    }
}


