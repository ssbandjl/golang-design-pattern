package main

import (
	"golang-design-patern/principle/dip/demo1/dip"
	"golang-design-patern/principle/dip/demo1/javaimpl"
	"golang-design-patern/principle/dip/demo1/pythonimpl"
)

// runPoll 执行灯开关测试, main pkg 里的 runPoll 函数仅仅面向 Botton interface 编码, main pkg 不再关心 Botton interface 里定义的 TurnOn、TurnOff 等实现细节。实现了解耦
func runPoll(b dip.Botton) {
	ui := dip.NewUI(b)
	ui.Poll()
}

// 依赖倒置原则(DIP) 代码示例
// cd golang-design-pattern/principle/dip/demo1/cmd && go build main.go && ./main
func main() {
	runPoll(pythonimpl.NewLamp())
	runPoll(javaimpl.NewLamp())
}
