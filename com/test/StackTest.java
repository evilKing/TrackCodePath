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
			System.out.println("��");
		}
		
	}

	private static void printStack(Stack<Integer> stack) {
		if (stack.empty())
			System.out.println("��ջ�ǿյģ�û��Ԫ��");
		else {
			System.out.print("��ջ�е�Ԫ�أ�");
			Enumeration items = stack.elements(); // �õ� stack �е�ö�ٶ���
			while (items.hasMoreElements()) // ��ʾö�٣�stack �� �е�����Ԫ��
				System.out.print(items.nextElement() + " ");
		}
		System.out.println(); // ����
	}
}
