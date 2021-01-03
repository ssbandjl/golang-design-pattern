package com.atguigu.facade;

public class Stereo {

	private static Stereo instance = new Stereo();
	
	public static Stereo getInstance() {
		return instance;
	}
	
	public void on() {
		System.out.println(" Stereo on ");
	}
	
	public void off() {
		System.out.println(" Screen off ");
	}
	
	public void up() {
		System.out.println(" Screen up.. ");
	}
	
	//...
}
