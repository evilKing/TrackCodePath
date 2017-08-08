package com.test;

import java.util.Enumeration;
import java.util.Stack;

public class StackTest {

	public static void main(String[] args) {
		Stack stack = new Stack();

		String line = "public void main(){ jjjaljd;ljljljl}aaaa";

		char[] chars = line.toCharArray();
		
		for(char c: chars){
			if(c == '{'){
				stack.push(c);
			}else if(c == '}'){
				stack.pop();
			}

		}
		
		if(stack.isEmpty()){
			System.out.println("完");
		}
		
	}

	private static void printStack(Stack<Integer> stack) {
		if (stack.empty())
			System.out.println("堆栈是空的，没有元素");
		else {
			System.out.print("堆栈中的元素：");
			Enumeration items = stack.elements(); // 得到 stack 中的枚举对象
			while (items.hasMoreElements()) // 显示枚举（stack ） 中的所有元素
				System.out.print(items.nextElement() + " ");
		}
		System.out.println(); // 换行
	}
}
