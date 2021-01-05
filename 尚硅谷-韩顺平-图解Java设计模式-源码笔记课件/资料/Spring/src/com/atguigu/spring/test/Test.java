package com.atguigu.spring.test;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Test {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// System.out.println("new~~");
		// ApplicationContext applicationContext = new
		// ClassPathXmlApplicationContext("beans.xml");
		// //获取monster[通过id获取monster]
		// Object bean = applicationContext.getBean("id01");
		// System.out.println("bean" + bean);

		SpelExpressionParser parser = new SpelExpressionParser();

		Expression expression = parser.parseExpression("100 * (2 + 400) * 1 + 66");

		int result = (Integer) expression.getValue();

		System.out.println(result);

	}

}
