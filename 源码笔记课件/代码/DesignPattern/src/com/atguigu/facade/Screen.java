package com.atguigu.facade;

public class Screen {

	private static Screen instance = new Screen();
	
	public static Screen getInstance() {
		return instance;
	}
	
	
	
	
	public void up() {
		System.out.println(" Screen up ");
	}
	
	public void down() {
		System.out.println(" Screen down ");
	}
	

}
