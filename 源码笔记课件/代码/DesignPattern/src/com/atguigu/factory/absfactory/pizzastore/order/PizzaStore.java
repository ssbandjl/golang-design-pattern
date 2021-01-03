package com.atguigu.factory.absfactory.pizzastore.order;

public class PizzaStore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new OrderPizza(new BJFactory());
		new OrderPizza(new LDFactory());
	}

}
