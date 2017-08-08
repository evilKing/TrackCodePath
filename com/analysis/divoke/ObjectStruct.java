package com.analysis.divoke;

public class ObjectStruct {
	private String objectname;
	
	private String methodName;
	
	private String classname;	//单纯的类名
	
	private ClassFile clsf;
	
	public String getObjectname() {
		return objectname;
	}
	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public ClassFile getClsf() {
		return clsf;
	}
	public void setClsf(ClassFile clsf) {
		this.clsf = clsf;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	
}
