package com.atguigu.proxy.dynamic;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建目标对象
		ITeacherDao target = new TeacherDao();
		
		//给目标对象，创建代理对象, 可以转成 ITeacherDao
		ITeacherDao proxyInstance = (ITeacherDao)new ProxyFactory(target).getProxyInstance();
	
		// proxyInstance=class com.sun.proxy.$Proxy0 内存中动态生成了代理对象
		System.out.println("proxyInstance=" + proxyInstance.getClass());
		
		//通过代理对象，调用目标对象的方法
		//proxyInstance.teach();
		
		proxyInstance.sayHello(" tom ");
	}

}
