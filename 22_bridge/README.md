# 桥接模式

桥接模式分离抽象部分和实现部分。使得两部分独立扩展。

桥接模式类似于策略模式，区别在于策略模式封装一系列算法使得算法可以互相替换。

策略模式使抽象部分和实现部分分离，可以独立变化。





#### 桥接

阅读: 77174

------

> 将抽象部分与它的实现部分分离，使它们都可以独立地变化。

桥接模式的定义非常玄乎，直接理解不太容易，所以我们还是举例子。

假设某个汽车厂商生产三种品牌的汽车：Big、Tiny和Boss，每种品牌又可以选择燃油、纯电和混合动力。如果用传统的继承来表示各个最终车型，一共有3个抽象类加9个最终子类：

```ascii
                   ┌───────┐
                   │  Car  │
                   └───────┘
                       ▲
    ┌──────────────────┼───────────────────┐
    │                  │                   │
┌───────┐          ┌───────┐          ┌───────┐
│BigCar │          │TinyCar│          │BossCar│
└───────┘          └───────┘          └───────┘
    ▲                  ▲                  ▲
    │                  │                  │
    │ ┌───────────────┐│ ┌───────────────┐│ ┌───────────────┐
    ├─│  BigFuelCar   │├─│  TinyFuelCar  │├─│  BossFuelCar  │
    │ └───────────────┘│ └───────────────┘│ └───────────────┘
    │ ┌───────────────┐│ ┌───────────────┐│ ┌───────────────┐
    ├─│BigElectricCar │├─│TinyElectricCar│├─│BossElectricCar│
    │ └───────────────┘│ └───────────────┘│ └───────────────┘
    │ ┌───────────────┐│ ┌───────────────┐│ ┌───────────────┐
    └─│ BigHybridCar  │└─│ TinyHybridCar │└─│ BossHybridCar │
      └───────────────┘  └───────────────┘  └───────────────┘
```

如果要新增一个品牌，或者加一个新的引擎（比如核动力），那么子类的数量增长更快。

所以，桥接模式就是为了避免直接继承带来的子类爆炸。

我们来看看桥接模式如何解决上述问题。

在桥接模式中，首先把`Car`按品牌进行子类化，但是，每个品牌选择什么发动机，不再使用子类扩充，而是通过一个抽象的“修正”类，以组合的形式引入。我们来看看具体的实现。

首先定义抽象类`Car`，它引用一个`Engine`：

```
public abstract class Car {
    // 引用Engine:
    protected Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public abstract void drive();
}
```

`Engine`的定义如下：

```
public interface Engine {
    void start();
}
```

紧接着，在一个“修正”的抽象类`RefinedCar`中定义一些额外操作：

```
public abstract class RefinedCar extends Car {
    public RefinedCar(Engine engine) {
        super(engine);
    }

    public void drive() {
        this.engine.start();
        System.out.println("Drive " + getBrand() + " car...");
    }

    public abstract String getBrand();
}
```

这样一来，最终的不同品牌继承自`RefinedCar`，例如`BossCar`：

```
public class BossCar extends RefinedCar {
    public BossCar(Engine engine) {
        super(engine);
    }

    public String getBrand() {
        return "Boss";
    }
}
```

而针对每一种引擎，继承自`Engine`，例如`HybridEngine`：

```
public class HybridEngine implements Engine {
    public void start() {
        System.out.println("Start Hybrid Engine...");
    }
}
```

客户端通过自己选择一个品牌，再配合一种引擎，得到最终的Car：

```
RefinedCar car = new BossCar(new HybridEngine());
car.drive();
```

使用桥接模式的好处在于，如果要增加一种引擎，只需要针对`Engine`派生一个新的子类，如果要增加一个品牌，只需要针对`RefinedCar`派生一个子类，任何`RefinedCar`的子类都可以和任何一种`Engine`自由组合，即一辆汽车的两个维度：品牌和引擎都可以独立地变化。

```ascii
       ┌───────────┐
       │    Car    │
       └───────────┘
             ▲
             │
       ┌───────────┐       ┌─────────┐
       │RefinedCar │ ─ ─ ─>│ Engine  │
       └───────────┘       └─────────┘
             ▲                  ▲
    ┌────────┼────────┐         │ ┌──────────────┐
    │        │        │         ├─│  FuelEngine  │
┌───────┐┌───────┐┌───────┐     │ └──────────────┘
│BigCar ││TinyCar││BossCar│     │ ┌──────────────┐
└───────┘└───────┘└───────┘     ├─│ElectricEngine│
                                │ └──────────────┘
                                │ ┌──────────────┐
                                └─│ HybridEngine │
                                  └──────────────┘
```

桥接模式实现比较复杂，实际应用也非常少，但它提供的设计思想值得借鉴，即不要过度使用继承，而是优先拆分某些部件，使用组合的方式来扩展功能。

### 练习

使用桥接模式扩展一种新的品牌和新的核动力引擎。

从[![img](data:image/png;base64,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)](https://gitee.com/)下载练习：[桥接模式练习](https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java教程/190.设计模式.1264742167474528/20.结构型模式.1281319233388578/20.桥接.1281319266943009/pattern-bridge.zip?utm_source=blog_lxf) （推荐使用[IDE练习插件](https://www.liaoxuefeng.com/wiki/1252599548343744/1266092093733664)快速下载）

### 小结

桥接模式通过分离一个抽象接口和它的实现部分，使得设计可以按两个维度独立扩展。





在现实生活中，某些类具有两个或多个维度的变化，如图形既可按形状分，又可按颜色分。如何设计类似于 Photoshop 这样的软件，能画不同形状和不同颜色的图形呢？如果用继承方式，m 种形状和 n 种颜色的图形就有 m×n 种，不但对应的子类很多，而且扩展困难。

当然，这样的例子还有很多，如不同颜色和字体的文字、不同品牌和功率的汽车、不同性别和职业的男女、支持不同平台和不同文件格式的媒体播放器等。如果用桥接模式就能很好地解决这些问题。

## 桥接模式的定义与特点

桥接（Bridge）模式的定义如下：将抽象与实现分离，使它们可以独立变化。它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。

通过上面的讲解，我们能很好的感觉到桥接模式遵循了里氏替换原则和依赖倒置原则，最终实现了开闭原则，对修改关闭，对扩展开放。这里将桥接模式的优缺点总结如下。

桥接（Bridge）模式的优点是：

- 抽象与实现分离，扩展能力强
- 符合开闭原则
- 符合合成复用原则
- 其实现细节对客户透明


缺点是：由于聚合关系建立在抽象层，要求开发者针对抽象化进行设计与编程，能正确地识别出系统中两个独立变化的维度，这增加了系统的理解与设计难度。

## 桥接模式的结构与实现

可以将抽象化部分与实现化部分分开，取消二者的继承关系，改用组合关系。

#### 1. 模式的结构

桥接（Bridge）模式包含以下主要角色。

1. 抽象化（Abstraction）角色：定义抽象类，并包含一个对实现化对象的引用。
2. 扩展抽象化（Refined Abstraction）角色：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
3. 实现化（Implementor）角色：定义实现化角色的接口，供扩展抽象化角色调用。
4. 具体实现化（Concrete Implementor）角色：给出实现化角色接口的具体实现。


其结构图如图 1 所示。



![桥接模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115125253H1.gif)
图1 桥接模式的结构图

#### 2. 模式的实现

桥接模式的代码如下：

```
package bridge;public class BridgeTest {    public static void main(String[] args) {        Implementor imple = new ConcreteImplementorA();        Abstraction abs = new RefinedAbstraction(imple);        abs.Operation();    }}//实现化角色interface Implementor {    public void OperationImpl();}//具体实现化角色class ConcreteImplementorA implements Implementor {    public void OperationImpl() {        System.out.println("具体实现化(Concrete Implementor)角色被访问");    }}//抽象化角色abstract class Abstraction {    protected Implementor imple;    protected Abstraction(Implementor imple) {        this.imple = imple;    }    public abstract void Operation();}//扩展抽象化角色class RefinedAbstraction extends Abstraction {    protected RefinedAbstraction(Implementor imple) {        super(imple);    }    public void Operation() {        System.out.println("扩展抽象化(Refined Abstraction)角色被访问");        imple.OperationImpl();    }}
```


程序的运行结果如下：

```
扩展抽象化(Refined Abstraction)角色被访问
具体实现化(Concrete Implementor)角色被访问
```

## 桥接模式的应用实例

【例1】用桥接（Bridge）模式模拟女士皮包的选购。

分析：女士皮包有很多种，可以按用途分、按皮质分、按品牌分、按颜色分、按大小分等，存在多个维度的变化，所以采用桥接模式来实现女士皮包的选购比较合适。

本实例按用途分可选钱包（Wallet）和挎包（HandBag），按颜色分可选黄色（Yellow）和红色（Red）。可以按两个维度定义为颜色类和包类。（[点此下载本实例所要显示的包的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q115125U5.zip)）。

颜色类（Color）是一个维度，定义为实现化角色，它有两个具体实现化角色：黄色和红色，通过 getColor() 方法可以选择颜色；包类（Bag）是另一个维度，定义为抽象化角色，它有两个扩展抽象化角色：挎包和钱包，它包含了颜色类对象，通过 getName() 方法可以选择相关颜色的挎包和钱包。

客户类通过 ReadXML 类从 XML 配置文件中获取包信息（[点此下载 XML 配置文件](http://c.biancheng.net/uploads/soft/181113/3-1Q115130045.zip)），并把选到的产品通过窗体显示出现，图 2 所示是其结构图。



![女士皮包选购的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q11512532X54.gif)
图2 女士皮包选购的结构图


程序代码如下：

```
package bridge;import org.w3c.dom.NodeList;import javax.swing.*;import javax.xml.parsers.DocumentBuilder;import javax.xml.parsers.DocumentBuilderFactory;import java.awt.*;public class BagManage {    public static void main(String[] args) {        Color color;        Bag bag;        color = (Color) ReadXML.getObject("color");        bag = (Bag) ReadXML.getObject("bag");        bag.setColor(color);        String name = bag.getName();        show(name);    }    public static void show(String name) {        JFrame jf = new JFrame("桥接模式测试");        Container contentPane = jf.getContentPane();        JPanel p = new JPanel();        JLabel l = new JLabel(new ImageIcon("src/bridge/" + name + ".jpg"));        p.setLayout(new GridLayout(1, 1));        p.setBorder(BorderFactory.createTitledBorder("女士皮包"));        p.add(l);        contentPane.add(p, BorderLayout.CENTER);        jf.pack();        jf.setVisible(true);        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }}//实现化角色：颜色interface Color {    String getColor();}//具体实现化角色：黄色class Yellow implements Color {    public String getColor() {        return "yellow";    }}//具体实现化角色：红色class Red implements Color {    public String getColor() {        return "red";    }}//抽象化角色：包abstract class Bag {    protected Color color;    public void setColor(Color color) {        this.color = color;    }    public abstract String getName();}//扩展抽象化角色：挎包class HandBag extends Bag {    public String getName() {        return color.getColor() + "HandBag";    }}//扩展抽象化角色：钱包class Wallet extends Bag {    public String getName() {        return color.getColor() + "Wallet";    }}package bridge;        import javax.xml.parsers.*;        import org.w3c.dom.*;        import java.io.*;class ReadXML {    public static Object getObject(String args) {        try {            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();            DocumentBuilder builder = dFactory.newDocumentBuilder();            Document doc;            doc = builder.parse(new File("src/bridge/config.xml"));            NodeList nl = doc.getElementsByTagName("className");            Node classNode = null;            if (args.equals("color")) {                classNode = nl.item(0).getFirstChild();            } else if (args.equals("bag")) {                classNode = nl.item(1).getFirstChild();            }            String cName = "bridge." + classNode.getNodeValue();            Class<?> c = Class.forName(cName);            Object obj = c.newInstance();            return obj;        } catch (Exception e) {            e.printStackTrace();            return null;        }    }}
```


程序的运行结果如图 3 所示。



![女士皮包选购的运行结果1](http://c.biancheng.net/uploads/allimg/181115/3-1Q115125404105.jpg)
图3 女士皮包选购的运行结果1


如果将 XML 配置文件按如下修改：

```
<?xml version="1.0" encoding="UTF-8"?><config>    <className>Red</className>    <className>Wallet</className></config>
```


则程序的运行结果如图 4 所示。



![女士皮包选购的运行结果2](http://c.biancheng.net/uploads/allimg/181115/3-1Q11512542a20.jpg)
图4 女士皮包选购的运行结果2

## 桥接模式的应用场景

当一个类内部具备两种或多种变化维度时，使用桥接模式可以解耦这些变化的维度，使高层代码架构稳定。

桥接模式通常适用于以下场景。

1. 当一个类存在两个独立变化的维度，且这两个维度都需要进行扩展时。
2. 当一个系统不希望使用继承或因为多层次继承导致系统类的个数急剧增加时。
3. 当一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性时。


桥接模式的一个常见使用场景就是替换继承。我们知道，继承拥有很多优点，比如，抽象、封装、多态等，父类封装共性，子类实现特性。继承可以很好的实现代码复用（封装）的功能，但这也是继承的一大缺点。

因为父类拥有的方法，子类也会继承得到，无论子类需不需要，这说明继承具备强侵入性（父类代码侵入子类），同时会导致子类臃肿。因此，在[设计模式](http://c.biancheng.net/design_pattern/)中，有一个原则为优先使用组合/聚合，而不是继承。



![img](http://c.biancheng.net/uploads/allimg/200929/5-2009291J41K93.png)


很多时候，我们分不清该使用继承还是组合/聚合或其他方式等，其实可以从现实语义进行思考。因为软件最终还是提供给现实生活中的人使用的，是服务于人类社会的，软件是具备现实场景的。当我们从纯代码角度无法看清问题时，现实角度可能会提供更加开阔的思路。

## 桥接模式模式的扩展

在软件开发中，有时桥接（Bridge）模式可与[适配器模式](http://c.biancheng.net/view/1361.html)联合使用。当桥接（Bridge）模式的实现化角色的接口与现有类的接口不一致时，可以在二者中间定义一个适配器将二者连接起来，其具体结构图如图 5 所示。



![桥接模式与适配器模式联用的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q1151255004c.gif)
图5 桥接模式与适配器模式联用的结构图