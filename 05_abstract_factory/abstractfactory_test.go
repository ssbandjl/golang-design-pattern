package abstractfactory

import (
	"log"
	"runtime"
)

// 获取正在运行的函数名
func runFuncName() string {
	pc := make([]uintptr, 1)
	runtime.Callers(2, pc)        //将当前运行的方法信息填充到pc切片
	f := runtime.FuncForPC(pc[0]) //获取当前运行的方法信息
	return f.Name()
}

func getMainAndDetail(factory DAOFactory) {
	log.Printf("函数名:%s", runFuncName())
	factory.CreateOrderMainDAO().SaveOrderMain()
	factory.CreateOrderDetailDAO().SaveOrderDetail()
}

//首先执行, 大写方法
func ExampleRdbFactory() {
	log.Printf("函数名:%s", runFuncName())
	var factory DAOFactory
	factory = &RDBDAOFactory{}
	getMainAndDetail(factory)
	// Output:
	// rdb main save
	// rdb detail save
}

func ExampleXmlFactory() {
	log.Printf("函数名:%s", runFuncName())
	var factory DAOFactory
	factory = &XMLDAOFactory{}
	getMainAndDetail(factory)
	// Output:
	// xml main save
	// xml detail save
}
