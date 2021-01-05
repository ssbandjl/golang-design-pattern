package com.atguigu.jdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<String> a = new ArrayList<>();
		a.add("jack");// ..
		// 获取到迭代器
		Iterator Itr = a.iterator();
		while (Itr.hasNext()) {
			System.out.println(Itr.next());
		}
		
	

	}

}
