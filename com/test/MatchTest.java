package com.test;

public class MatchTest {

	public static void main(String[] args) {
		
		 String likeType = "int\\s+CopyToTailArray\\s*\\(\\s*String\\s+arg0\\s*,\\s*int\\s+arg1\\s*\\)";
		  String pattern = "[a-zA-Z0-9]*[" + likeType + "]{1}[a-zA-Z0-9]*";
		  String sourceStr = "int    CopyToTailArray  (String     arg0     ,int  arg1  	 )";
		     
		  System.out.println(sourceStr.matches(likeType)); 
		
		
		
	}

}
