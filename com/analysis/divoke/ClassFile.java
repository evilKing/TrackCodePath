package com.analysis.divoke;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class ClassFile {
	private String classname;
	private String path;
	private File file;
	private Class cls;
	private Map<String,String> methodMap;
	public Map<String, String> getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(Map<String, String> methodMap) {
		this.methodMap = methodMap;
	}

	private Method[] methods;
	
	public ClassFile(){
		
	}
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public Class getCls() {
		return cls;
	}
	public void setCls(Class cls) {
		this.cls = cls;
	}

	public Method[] getMethods() {
		return methods;
	}

	public void setMethods(Method[] methods) {
		this.methods = methods;
	}
	
}
