package com.eg.egsc.scp.reflect;

import java.lang.reflect.InvocationTargetException;

public class User {
	String userName;
	String userPassword;
	String Key;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public static void main(String[] args) {
    	User user = new User();
    	user.setUserName("玩无可");
    	user.setUserPassword("1596666");
    	Object object = user;
    	
    	java.lang.reflect.Field[] fields = object.getClass().getDeclaredFields();
    	for (java.lang.reflect.Field f : fields) {
    	    //System.out.println(f.getName());
    	}
    	
    	java.lang.reflect.Method[] method = object.getClass().getDeclaredMethods();//获取所有方法
    	for(java.lang.reflect.Method m:method) {
//    		System.out.println(m.getName());
    	    if (m.getName().startsWith("get")) {
    	        Object o = null;
    	        try {
    	            o = m.invoke(object);
    	        } catch (IllegalAccessException | InvocationTargetException e) {
    	            e.printStackTrace();
    	        }
    	        if (o != null && !"".equals(o.toString())) {
    	            System.out.println(m.getName() + ":" + o.toString());
    	        }
    	    }
    	}
    	
    	
    	
    	java.lang.reflect.Field fi = null;//获取属性
    	try {
    	    fi = object.getClass().getDeclaredField("userName");
    	} catch (NoSuchFieldException e) {
    	    e.printStackTrace();
    	}
    	fi.setAccessible(true);//设置当前对象对model私有属性的访问权限
    	try {
    	    System.out.println(fi.get(object).toString());
    	} catch (IllegalAccessException e) {
    	    e.printStackTrace();
    	}
	}
}
