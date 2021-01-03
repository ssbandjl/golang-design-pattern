package com.atguigu.builder;

public abstract class AbstractHouse {
	
	//´òµØ»ù
	public abstract void buildBasic();
	//ÆöÇ½
	public abstract void buildWalls();
	//·â¶¥
	public abstract void roofed();
	
	public void build() {
		buildBasic();
		buildWalls();
		roofed();
	}
	
}
