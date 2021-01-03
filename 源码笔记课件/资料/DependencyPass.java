package com.atguigu.principle.dependencyreverse.improve;

public class DependencyPass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

// 方式1： 通过接口传递实现依赖
// 开关的接口
// interface IOpenAndClose {
// public void open(ITV tv); //抽象方法,接收接口
// }
//
// interface ITV { //ITV接口
// public void play();
// }
//// 实现接口
// class OpenAndClose implements IOpenAndClose{
// public void open(ITV tv){
// tv.play();
// }
// }

// 方式2: 通过构造方法依赖传递
// interface IOpenAndClose {
// public void open(); //抽象方法
// }
// interface ITV { //ITV接口
// public void play();
// }
// class OpenAndClose implements IOpenAndClose{
// public ITV tv;
// public OpenAndClose(ITV tv){
// this.tv = tv;
// }
// public void open(){
// this.tv.play();
// }
// }

// 方式3 , 通过setter方法传递
interface IOpenAndClose {
	public void open(); // 抽象方法

	public void setTv(ITV tv);
}

interface ITV { // ITV接口
	public void play();
}

class OpenAndClose implements IOpenAndClose {
	private ITV tv;

	public void setTv(ITV tv) {
		this.tv = tv;
	}

	public void open() {
		this.tv.play();
	}
}