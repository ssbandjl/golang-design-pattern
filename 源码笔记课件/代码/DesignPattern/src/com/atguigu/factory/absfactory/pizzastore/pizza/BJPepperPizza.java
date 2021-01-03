package com.atguigu.factory.absfactory.pizzastore.pizza;

public class BJPepperPizza extends Pizza {
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		setName("北京的胡椒pizza");
		System.out.println(" 北京的胡椒pizza 准备原材料");
	}
}
