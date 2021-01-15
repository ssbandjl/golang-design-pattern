# Golang的23种设计模式之代码示例+图解+设计模式/原则资料

[![Build Status](https://travis-ci.org/senghoo/golang-design-pattern.svg?branch=master)](https://travis-ci.org/senghoo/golang-design-pattern)

## 什么是设计模式?

设计模式是一套理论, 由软件界先辈们总结出的一套可以反复使用的经验, 可以提高代码可重用性, 增强系统可维护性, 以及巧妙解决一系列逻辑复杂的问题(运用套路).

1995 年，艾瑞克·伽马（ErichGamma）、理査德·海尔姆（Richard Helm）、拉尔夫·约翰森（Ralph Johnson）、约翰·威利斯迪斯（John Vlissides）等 4 位作者合作出版了《设计模式：可复用面向对象软件的基础》（Design Patterns: Elements of Reusable Object-Oriented Software）一书，在本教程中收录了 23 个设计模式，这是设计模式领域里程碑的事件，导致了软件设计模式的突破。这 4 位作者在软件开发领域里也以他们的“四人组”（Gang of Four，GoF）匿名著称.



## 项目简介

Golang的23种设计模式之代码示例+图解+设计模式/原则等资料

项目地址: https://github.com/ssbandjl/golang-design-pattern

## 云原生

更多云原生相关技术干货, 欢迎大家关注我的微信公众号:**云原生云**

![云原生云二维码](img/云原生云二维码大.gif)

## 参考文档: 

- [廖雪峰](https://www.liaoxuefeng.com/wiki/1252599548343744/1281319417937953)
- [图解设计模式](http://c.biancheng.net/view/1397.html)
- [golang-design-patttern](https://github.com/senghoo/golang-design-pattern)
- 设计
  + [DDD领域驱动设计在互联网业务开发中的实践](https://tech.meituan.com/2017/12/22/ddd-in-practice.html)



## 原则

- [依赖倒置原则](./ref)
  + [程序员进阶指南-文档团队Golang最佳实践和CR案例集分享](./ref/程序员进阶指南-文档团队Golang最佳实践和CR案例集分享.md)
    * [示例代码](./principle/dip/)



## Golang的23种设计模式

### 创建型模式

* [简单工厂模式（Simple Factory）](./00_simple_factory) 

  ![image-20210103164219481](./img/简单工厂模式.png)

* [工厂方法模式（Factory Method）](./04_factory_method)

  ![image-20210103164256869](./img/工厂方法模式.png)

* [抽象工厂模式（Abstract Factory）](./05_abstract_factory)

  ![image-20210103164329402](./img/抽象工厂模式.png)

* [创建者模式（Builder）](./06_builder)

  ![image-20210103164416271](./img/创建者模式.png)

* [原型模式（Prototype）](./07_prototype)

  ![image-20210103164457142](./img/原型模式.png)

* [单例模式（Singleton）](./03_singleton)

  ![image-20210103164524348](./img/单例模式.png)

### 结构型模式

* [外观模式（Facade）](./01_facade) 

  ![image-20210103164552758](./img/外观模式.png)

* [适配器模式（Adapter）](./02_adapter)

  ![image-20210103164615892](./img/适配器模式.png)

* [代理模式（Proxy）](./09_proxy) 

  ![image-20210103164633592](./img/代理模式.png)

* [组合模式（Composite）](./13_composite)

  ![image-20210103164703131](./img/组合模式.png)![image-20210103164728438](./img/组合模式2.png)

* [享元模式（Flyweight）](./18_flyweight)

  ![image-20210103164758921](./img/享元模式.png)

* [装饰模式（Decorator）](./20_decorator)

  ![image-20210103164826679](./img/装饰模式.png)

* [桥接模式（Bridge）](./22_bridge)

  ![image-20210103164857565](./img/桥接模式.png)

### 行为型模式

* [中介者模式（Mediator）](./08_mediator)

  ![image-20210103165035523](./img/中介者模式.png)

* [观察者模式（Observer）](./10_observer)

  ![image-20210103165108726](./img/观察者模式.png)

* [命令模式（Command）](./11_command)

  ![image-20210103165125851](./img/命令模式.png)

* [迭代器模式（Iterator）](./12_iterator)

  ![image-20210103165157356](./img/迭代器模式.png)

* [模板方法模式（Template Method）](./14_template_method)

  ![image-20210103165219538](./img/模板方法模式.png)

* [策略模式（Strategy）](./15_strategy)

  ![image-20210103165236575](./img/策略模式.png)

* [状态模式（State）](./16_state)

  ![image-20210103165303649](./img/状态模式.png)

* [备忘录模式（Memento）](./17_memento)

  ![image-20210103165318038](./img/备忘录模式.png)

* [解释器模式（Interpreter）](./19_interpreter)

  ![image-20210103165338073](./img/解释器模式.png)

* [职责链模式（Chain of Responsibility）](./21_chain_of_responsibility)

  ![image-20210103165403720](./img/责任链模式.png)

* [访问者模式（Visitor）](./23_visitor)

  ![image-20210103165421365](./img/访问者模式.png)

