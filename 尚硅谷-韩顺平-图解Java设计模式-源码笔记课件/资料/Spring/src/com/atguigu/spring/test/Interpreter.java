package com.atguigu.spring.test;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Interpreter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpelExpressionParser parser = new SpelExpressionParser();

		Expression expression = parser.parseExpression("100 * (2 + 400) * 1 + 66");

		int result = (Integer) expression.getValue();

		System.out.println(result);
	}

}
