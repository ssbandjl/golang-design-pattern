package com.atguigu.observer.improve;

//接口, 让WeatherData 来实现 
public interface Subject {
	
	public void registerObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObservers();
}
