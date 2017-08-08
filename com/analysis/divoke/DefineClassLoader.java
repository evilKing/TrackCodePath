package com.analysis.divoke;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DefineClassLoader extends ClassLoader{

	private String rootDir;
	
	public DefineClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(name);
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);  // 获取类的字节数组  
        if (classData == null) {  
            throw new ClassNotFoundException();  
        } else {  
            return defineClass(name, classData, 0, classData.length);  
        }  
	}
	
	private byte[] getClassData(String className) {  
        // 读取类文件的字节  
        String path = classNameToPath(className);
//        System.out.println(path);
        try {  
            InputStream ins = new FileInputStream(path);  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            int bufferSize = 4096;  
            byte[] buffer = new byte[bufferSize];  
            int bytesNumRead = 0;  
            // 读取类文件的字节码  
            while ((bytesNumRead = ins.read(buffer)) != -1) {  
                baos.write(buffer, 0, bytesNumRead);  
            }
            byte[] ans = baos.toByteArray();
            baos.close();
            ins.close();
            return ans;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    private String classNameToPath(String className) {  
        // 得到类文件的完全路径  
        return rootDir + File.separatorChar  
                + className.replace('.', File.separatorChar) + ".class";  
    }  
	
}
