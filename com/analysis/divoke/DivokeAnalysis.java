package com.analysis.divoke;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DivokeAnalysis {

	private static List<ClassFile> classFileList = new ArrayList<ClassFile>();
	
	public static void main(String[] args) throws Exception {
		//查找所有的类，以及类的方法
		File directory = new File("D:\\360Downloads\\darts-java\\src");
		searchAllClassFile(directory);
		
		//从入口方法开始遍历所有调用的方法
		String intoClass = "com.unknown.test.TrieLinkedMain";
		String intoMethodPattern = ".*public\\s+static\\s+void\\s+main.*";
		
		//寻找入口类对应的ClassFile对象
		ClassFile intoClf = null;
		for(ClassFile clf : classFileList){
//			System.out.println(clf.getClassname());
			if(clf.getClassname().contains(intoClass)){
				intoClf = clf;
				break;
			}
		}
		if(intoClf == null){
			System.out.println("========找不到入口类======退出!");
		}else{
			System.out.println("===找到入口类，开始分析");
		}
		
		//加载入口类文件成 随机访问文件
		List<String> sentenceList = new ArrayList<String>();
//		System.out.println(intoClf.getPath());
		BufferedReader reader = new BufferedReader(new FileReader(intoClf.getFile()));
        String line;
        StringBuffer sb = new StringBuffer();
		Stack stack = new Stack();
		boolean flag = false;
//		boolean tmpFlag = false;
        while ((line = reader.readLine()) != null){
        	if(line.matches(intoMethodPattern)){
        		flag = true;
        		if(!line.contains("{")){
        			stack.push('#');
        		}
        	}
        	if(flag){
        		sb.append(line + "\n");
        		sentenceList.add(line);
        		
        		char[] chars = line.toCharArray();
        		
        		for(char c: chars){
        			if(c == '{'){
        				stack.push(c);
        			}else if(c == '}'){
        				stack.pop();
        			}
        		}
        	}
        	if(flag && stack.isEmpty()){
//        		System.out.println(sb.toString());
        		flag = false;
        		break;
        	}
        }
        reader.close();
		
        //查找所有调用到的对象和方法
        Queue<String> queue = new LinkedList<String>();
    	List<ObjectStruct> objectList = new ArrayList<ObjectStruct>();

        for(String sentence: sentenceList){
        	//1.查找本类中的方法
        	Method[] methods = intoClf.getMethods();
        	for(Method method: methods){
        		//排除当前方法
        		if(!method.getName().equals("main") && sentence.contains(method.getName()+"(")){
        			//递归调用本类方法查找
        			
        		}
        	}
//        	System.out.println("====" + sentence);
        	//2.查找静态类或对象
        	
        	for(ClassFile clsf: classFileList){
        		if(clsf == intoClf){
        			continue;
        		}
        		
        		String clsname = clsf.getClassname().substring(clsf.getClassname().lastIndexOf('.')+1);
//        		System.out.println(clsname);
//        		if(sentence.contains("DoubleLinkedTrie")){
//        			System.out.println();
//        		}
        		//静态方法
        		if(sentence.contains( clsname + ".(")){
        			System.out.println("static method: " + sentence);
        			//提取静态方法,加入到队列
        			
        			
        			
        		}else if(sentence.contains(clsname + "(")){		//生成对象
//        			System.out.println(clsname + "   object method: " + sentence);
        			
        			//提取对象名称
        			sentence = sentence.replaceAll("\\s+=\\s+new\\s+" + clsname + ".*", "");
        			String[] tmp = sentence.split("\\s+");
//        			System.out.println("object method: " + tmp[tmp.length - 1]);
        			
        			ObjectStruct objs = new ObjectStruct();
        			objs.setObjectname(tmp[tmp.length - 1]);
        			objs.setClassname(clsname);
        			objs.setClsf(clsf);
        			
        			objectList.add(objs);
        		}
        	}
        }
       //处理对象调用方法
    	if(objectList.size() == 0){
    		System.out.println("objectList size is null");
    	}else{
    		for(String sentence: sentenceList){
	    		for(ObjectStruct os: objectList){
	    			if(sentence.contains(os.getObjectname() + ".")){
	    				System.out.println(sentence);
	    				
	    				Pattern p=Pattern.compile(os.getObjectname()+".(.+)\\((.*)\\)"); 
	    				Matcher m=p.matcher(sentence);

	    				System.out.println(m.groupCount());
	    				
	    				int i = 1;
						while (m.find()) { 
							System.out.println(m.group(i++));
						}
	    			}
	    		}
    		}
    	}
        
	}
	
	public static void searchAllClassFile(File file){
		if(file.isDirectory()){
	        File[] files = file.listFiles();
	        for(File tmpFile: files){
	        	searchAllClassFile(tmpFile);
	        }
	        return;
		}
		
		ClassFile clsfile = new ClassFile();
		
		String path = file.getPath().replace(".java", "");
		String classname = path.substring(path.lastIndexOf("java") + 5).replaceAll("\\\\", ".");
		listClassMethods(classname, clsfile);
		
		clsfile.setPath(file.getPath());
		clsfile.setFile(file);
//		clsfile.setClassname(classname.substring(classname.lastIndexOf('.')));
		clsfile.setClassname(classname);
		
		classFileList.add(clsfile);
	}
	
	private static String rootDir = "D:\\360Downloads\\darts-java\\target\\classes";
	
	public static void listClassMethods(String classname,ClassFile classfile){
		try {
			
//			Class reflectClass = Class.forName(classname);
			DefineClassLoader loader = new DefineClassLoader(rootDir);
			Class reflectClass = loader.loadClass(classname);

			Method[] methods = reflectClass.getDeclaredMethods();
			
			Map<String,String> methodMap = new HashMap<String,String>();
			for(Method method: methods){
				String methodReturnType = method.getReturnType().toString();
				methodReturnType = methodReturnType.substring(methodReturnType.lastIndexOf('.')+1);
				
				String paramsstr = "";
				Class[] clss = method.getParameterTypes();
				Parameter[] parms = method.getParameters();
				if(parms == null || parms.length == 0){
					
				}else if(parms.length == 1){

					String typestr = clss[0].getName().substring(clss[0].getName().lastIndexOf('.') + 1);
					typestr = (typestr.lastIndexOf(';') < 0 ) ? typestr + ".*" : typestr.substring(0, typestr.lastIndexOf(';')) + ".*";
//					System.out.println(typestr + " *" + parms[0].getName());
					
					paramsstr = typestr + "\\s+" + parms[0].getName();
				}else{
					StringBuffer sb = new StringBuffer();
					String typestr = null;
					for(int i = 0;i < clss.length; i++){
						typestr = clss[i].getName().substring(clss[i].getName().lastIndexOf('.') + 1);
						typestr = (typestr.lastIndexOf(';') < 0 ) ? typestr + ".*" : typestr.substring(0, typestr.lastIndexOf(';')) + ".*";

						sb.append(typestr + "\\s+" + parms[i].getName() + "\\s*,\\s*");
					}
					int index = sb.toString().lastIndexOf(',');
					paramsstr = sb.substring(0,index);
				}
				
//				System.out.println(".*" + methodReturnType + "\\s+" + method.getName() + "\\s*\\(\\s*"+ paramsstr +"\\s*\\).*");
				
				methodMap.put(method.getName(), ".*" + methodReturnType + "\\s+" + method.getName() + "\\s*\\(\\s*"+ paramsstr +"\\s*\\).*");
			}
			
			classfile.setMethods(methods);
			classfile.setCls(reflectClass);
			
			classfile.setMethodMap(methodMap);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
