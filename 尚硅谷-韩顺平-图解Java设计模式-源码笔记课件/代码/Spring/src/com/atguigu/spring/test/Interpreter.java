package com.atguigu.spring.test;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class Interpreter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//创建一个 Parser 对象
		SpelExpressionParser parser = new SpelExpressionParser();
		//
		//通过 Parser 对象 获取到一个Expression对象
		//会根据不同的  Parser 对象 ，返回不同的 Expression对象
		Expression expression = parser.parseExpression("10 * (2 + 1) * 1 + 66"); //96
		int result = (Integer) expression.getValue();
		System.out.println(result);

	}

}
