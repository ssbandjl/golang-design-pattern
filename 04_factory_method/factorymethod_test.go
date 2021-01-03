package factorymethod

import "testing"

func compute(factory OperatorFactory, a, b int) int {
	op := factory.Create()
	op.SetA(a)
	op.SetB(b)
	return op.Result()
}

func TestOperator(t *testing.T) {
	var (
		factory OperatorFactory //工厂接口
	)
	//加法, PlusOperatorFactory 是 PlusOperator 的工厂类
	factory = PlusOperatorFactory{}
	if compute(factory, 1, 2) != 3 {
		t.Fatal("error with factory method pattern")
	}

	factory = MinusOperatorFactory{} //减法
	if compute(factory, 4, 2) != 2 {
		t.Fatal("error with factory method pattern")
	}
}
