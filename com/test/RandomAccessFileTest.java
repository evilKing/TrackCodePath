package com.test;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class RandomAccessFileTest {

	public static void main(String[] args) throws Exception {
		RandomAccessFile access = new RandomAccessFile("C:\\a.txt","rw");
		
		access.writeBytes("Hello World!!!");
		
		access.writeBytes("aaaaa\n");
		
		access.writeUTF("he he");
		
		access.close();
		
		
		access = new RandomAccessFile("C:\\a.txt", "rw");
		String content = access.readLine();
		access.seek(content.length() + 2);
		
		content = access.readLine();
		
		access.close();
		
		System.out.println(content);
		
	}

}
