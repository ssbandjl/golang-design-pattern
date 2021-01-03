# 享元模式

享元模式的本质是缓存共享对象，降低内存消耗

享元模式从对象中剥离出不发生改变且多个实例需要的重复数据，独立出一个享元，使多个对象共享，从而节省内存以及减少对象数量。

运用共享技术有效地支持大量细粒度的对象。

享元（Flyweight）的核心思想很简单：如果一个对象实例一经创建就不可变，那么反复创建相同的实例就没有必要，直接向调用方返回一个共享的实例就行，这样即节省内存，又可以减少创建对象的过程，提高运行速度。

享元模式在Java标准库中有很多应用。我们知道，包装类型如Byte、Integer都是不变类，因此，反复创建同一个值相同的包装类型是没有必要的。以Integer为例，如果我们通过Integer.valueOf()这个静态工厂方法创建Integer实例，当传入的int范围在-128~+127之间时，会直接返回缓存的Integer实例：
因此，享元模式就是通过工厂方法创建对象，在工厂方法内部，很可能返回缓存的实例，而不是新创建实例，从而实现不可变实例的复用。  总是使用工厂方法而不是new操作符创建实例，可获得享元模式的好处。


小结
享元模式的设计思想是尽量复用已创建的对象，常用于工厂方法内部的优化。

# 案例
- string
- 池





#### 享元

阅读: 44118

------

> 运用共享技术有效地支持大量细粒度的对象。

享元（Flyweight）的核心思想很简单：如果一个对象实例一经创建就不可变，那么反复创建相同的实例就没有必要，直接向调用方返回一个共享的实例就行，这样即节省内存，又可以减少创建对象的过程，提高运行速度。

享元模式在Java标准库中有很多应用。我们知道，包装类型如`Byte`、`Integer`都是不变类，因此，反复创建同一个值相同的包装类型是没有必要的。以`Integer`为例，如果我们通过`Integer.valueOf()`这个静态工厂方法创建`Integer`实例，当传入的`int`范围在`-128`~`+127`之间时，会直接返回缓存的`Integer`实例：

`// 享元模式 ` Run

对于`Byte`来说，因为它一共只有256个状态，所以，通过`Byte.valueOf()`创建的`Byte`实例，全部都是缓存对象。

因此，享元模式就是通过工厂方法创建对象，在工厂方法内部，很可能返回缓存的实例，而不是新创建实例，从而实现不可变实例的复用。

 总是使用工厂方法而不是new操作符创建实例，可获得享元模式的好处。

在实际应用中，享元模式主要应用于缓存，即客户端如果重复请求某些对象，不必每次查询数据库或者读取文件，而是直接返回内存中缓存的数据。

我们以`Student`为例，设计一个静态工厂方法，它在内部可以返回缓存的对象：

```
public class Student {
    // 持有缓存:
    private static final Map<String, Student> cache = new HashMap<>();

    // 静态工厂方法:
    public static Student create(int id, String name) {
        String key = id + "\n" + name;
        // 先查找缓存:
        Student std = cache.get(key);
        if (std == null) {
            // 未找到,创建新对象:
            System.out.println(String.format("create new Student(%s, %s)", id, name));
            std = new Student(id, name);
            // 放入缓存:
            cache.put(key, std);
        } else {
            // 缓存中存在:
            System.out.println(String.format("return cached Student(%s, %s)", std.id, std.name));
        }
        return std;
    }

    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

在实际应用中，我们经常使用成熟的缓存库，例如[Guava](https://github.com/google/guava)的[Cache](https://github.com/google/guava/blob/master/guava/src/com/google/common/cache/Cache.java)，因为它提供了最大缓存数量限制、定时过期等实用功能。

### 练习

从[![img](data:image/png;base64,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)](https://gitee.com/)下载练习：[使用享元模式实现缓存](https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java教程/190.设计模式.1264742167474528/20.结构型模式.1281319233388578/60.享元.1281319417937953/pattern-flyweight.zip?utm_source=blog_lxf) （推荐使用[IDE练习插件](https://www.liaoxuefeng.com/wiki/1252599548343744/1266092093733664)快速下载）

### 小结

享元模式的设计思想是尽量复用已创建的对象，常用于工厂方法内部的优化。





在面向对象程序设计过程中，有时会面临要创建大量相同或相似对象实例的问题。创建那么多的对象将会耗费很多的系统资源，它是系统性能提高的一个瓶颈。

例如，围棋和五子棋中的黑白棋子，图像中的坐标点或颜色，局域网中的路由器、交换机和集线器，教室里的桌子和凳子等。这些对象有很多相似的地方，如果能把它们相同的部分提取出来共享，则能节省大量的系统资源，这就是享元模式的产生背景。

## 享元模式的定义与特点

享元（Flyweight）模式的定义：运用共享技术来有效地支持大量细粒度对象的复用。它通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似类的开销，从而提高系统资源的利用率。

享元模式的主要优点是：相同对象只要保存一份，这降低了系统中对象的数量，从而降低了系统中细粒度对象给内存带来的压力。

其主要缺点是：

1. 为了使对象可以共享，需要将一些不能共享的状态外部化，这将增加程序的复杂性。
2. 读取享元模式的外部状态会使得运行时间稍微变长。

## 享元模式的结构与实现

享元模式的定义提出了两个要求，细粒度和共享对象。因为要求细粒度，所以不可避免地会使对象数量多且性质相近，此时我们就将这些对象的信息分为两个部分：内部状态和外部状态。

- 内部状态指对象共享出来的信息，存储在享元信息内部，并且不回随环境的改变而改变；
- 外部状态指对象得以依赖的一个标记，随环境的改变而改变，不可共享。


比如，连接池中的连接对象，保存在连接对象中的用户名、密码、连接URL等信息，在创建对象的时候就设置好了，不会随环境的改变而改变，这些为内部状态。而当每个连接要被回收利用时，我们需要将它标记为可用状态，这些为外部状态。

享元模式的本质是缓存共享对象，降低内存消耗。

#### 1. 模式的结构

享元模式的主要角色有如下。

1. 抽象享元角色（Flyweight）：是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入。
2. 具体享元（Concrete Flyweight）角色：实现抽象享元角色中所规定的接口。
3. 非享元（Unsharable Flyweight)角色：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
4. 享元工厂（Flyweight Factory）角色：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。


图 1 是享元模式的结构图，其中：

- UnsharedConcreteFlyweight 是非享元角色，里面包含了非共享的外部状态信息 info；
- Flyweight 是抽象享元角色，里面包含了享元方法 operation(UnsharedConcreteFlyweight state)，非享元的外部状态以参数的形式通过该方法传入；
- ConcreteFlyweight 是具体享元角色，包含了关键字 key，它实现了抽象享元接口；
- FlyweightFactory 是享元工厂角色，它是关键字 key 来管理具体享元；
- 客户角色通过享元工厂获取具体享元，并访问具体享元的相关方法。



![享元模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115161342242.gif)
图1 享元模式的结构图

#### 2. 模式的实现

享元模式的实现代码如下：

```
public class FlyweightPattern {    public static void main(String[] args) {        FlyweightFactory factory = new FlyweightFactory();        Flyweight f01 = factory.getFlyweight("a");        Flyweight f02 = factory.getFlyweight("a");        Flyweight f03 = factory.getFlyweight("a");        Flyweight f11 = factory.getFlyweight("b");        Flyweight f12 = factory.getFlyweight("b");        f01.operation(new UnsharedConcreteFlyweight("第1次调用a。"));        f02.operation(new UnsharedConcreteFlyweight("第2次调用a。"));        f03.operation(new UnsharedConcreteFlyweight("第3次调用a。"));        f11.operation(new UnsharedConcreteFlyweight("第1次调用b。"));        f12.operation(new UnsharedConcreteFlyweight("第2次调用b。"));    }}//非享元角色class UnsharedConcreteFlyweight {    private String info;    UnsharedConcreteFlyweight(String info) {        this.info = info;    }    public String getInfo() {        return info;    }    public void setInfo(String info) {        this.info = info;    }}//抽象享元角色interface Flyweight {    public void operation(UnsharedConcreteFlyweight state);}//具体享元角色class ConcreteFlyweight implements Flyweight {    private String key;    ConcreteFlyweight(String key) {        this.key = key;        System.out.println("具体享元" + key + "被创建！");    }    public void operation(UnsharedConcreteFlyweight outState) {        System.out.print("具体享元" + key + "被调用，");        System.out.println("非享元信息是:" + outState.getInfo());    }}//享元工厂角色class FlyweightFactory {    private HashMap<String, Flyweight> flyweights = new HashMap<String, Flyweight>();    public Flyweight getFlyweight(String key) {        Flyweight flyweight = (Flyweight) flyweights.get(key);        if (flyweight != null) {            System.out.println("具体享元" + key + "已经存在，被成功获取！");        } else {            flyweight = new ConcreteFlyweight(key);            flyweights.put(key, flyweight);        }        return flyweight;    }}
```

程序运行结果如下：

```
具体享元a被创建！
具体享元a已经存在，被成功获取！
具体享元a已经存在，被成功获取！
具体享元b被创建！
具体享元b已经存在，被成功获取！
具体享元a被调用，非享元信息是:第1次调用a。
具体享元a被调用，非享元信息是:第2次调用a。
具体享元a被调用，非享元信息是:第3次调用a。
具体享元b被调用，非享元信息是:第1次调用b。
具体享元b被调用，非享元信息是:第2次调用b。
```

## 享元模式的应用实例

【例1】享元模式在五子棋游戏中的应用。

分析：五子棋同围棋一样，包含多个“黑”或“白”颜色的棋子，所以用享元模式比较好。

本实例中:

- 棋子（ChessPieces）类是抽象享元角色，它包含了一个落子的 DownPieces(Graphics g,Point pt) 方法；
- 白子（WhitePieces）和黑子（BlackPieces）类是具体享元角色，它实现了落子方法；
- Point 是非享元角色，它指定了落子的位置；
- WeiqiFactory 是享元工厂角色，它通过 ArrayList 来管理棋子，并且提供了获取白子或者黑子的 getChessPieces(String type) 方法；
- 客户类（Chessboard）利用 Graphics 组件在框架窗体中绘制一个棋盘，并实现 mouseClicked(MouseEvent e) 事件处理方法，该方法根据用户的选择从享元工厂中获取白子或者黑子并落在棋盘上。


图 2 所示是其结构图。



![五子棋游戏的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q11516141M29.gif)
图2 五子棋游戏的结构图


程序代码如下：

```
import javax.swing.*;import java.awt.*;import java.awt.event.MouseAdapter;import java.awt.event.MouseEvent;import java.util.ArrayList;public class WzqGame {    public static void main(String[] args) {        new Chessboard();    }}//棋盘class Chessboard extends MouseAdapter {    WeiqiFactory wf;    JFrame f;    Graphics g;    JRadioButton wz;    JRadioButton bz;    private final int x = 50;    private final int y = 50;    private final int w = 40;    //小方格宽度和高度    private final int rw = 400;    //棋盘宽度和高度    Chessboard() {        wf = new WeiqiFactory();        f = new JFrame("享元模式在五子棋游戏中的应用");        f.setBounds(100, 100, 500, 550);        f.setVisible(true);        f.setResizable(false);        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        JPanel SouthJP = new JPanel();        f.add("South", SouthJP);        wz = new JRadioButton("白子");        bz = new JRadioButton("黑子", true);        ButtonGroup group = new ButtonGroup();        group.add(wz);        group.add(bz);        SouthJP.add(wz);        SouthJP.add(bz);        JPanel CenterJP = new JPanel();        CenterJP.setLayout(null);        CenterJP.setSize(500, 500);        CenterJP.addMouseListener(this);        f.add("Center", CenterJP);        try {            Thread.sleep(500);        } catch (InterruptedException e) {            e.printStackTrace();        }        g = CenterJP.getGraphics();        g.setColor(Color.BLUE);        g.drawRect(x, y, rw, rw);        for (int i = 1; i < 10; i++) {            //绘制第i条竖直线            g.drawLine(x + (i * w), y, x + (i * w), y + rw);            //绘制第i条水平线            g.drawLine(x, y + (i * w), x + rw, y + (i * w));        }    }    public void mouseClicked(MouseEvent e) {        Point pt = new Point(e.getX() - 15, e.getY() - 15);        if (wz.isSelected()) {            ChessPieces c1 = wf.getChessPieces("w");            c1.DownPieces(g, pt);        } else if (bz.isSelected()) {            ChessPieces c2 = wf.getChessPieces("b");            c2.DownPieces(g, pt);        }    }}//抽象享元角色：棋子interface ChessPieces {    public void DownPieces(Graphics g, Point pt);    //下子}//具体享元角色：白子class WhitePieces implements ChessPieces {    public void DownPieces(Graphics g, Point pt) {        g.setColor(Color.WHITE);        g.fillOval(pt.x, pt.y, 30, 30);    }}//具体享元角色：黑子class BlackPieces implements ChessPieces {    public void DownPieces(Graphics g, Point pt) {        g.setColor(Color.BLACK);        g.fillOval(pt.x, pt.y, 30, 30);    }}//享元工厂角色class WeiqiFactory {    private ArrayList<ChessPieces> qz;    public WeiqiFactory() {        qz = new ArrayList<ChessPieces>();        ChessPieces w = new WhitePieces();        qz.add(w);        ChessPieces b = new BlackPieces();        qz.add(b);    }    public ChessPieces getChessPieces(String type) {        if (type.equalsIgnoreCase("w")) {            return (ChessPieces) qz.get(0);        } else if (type.equalsIgnoreCase("b")) {            return (ChessPieces) qz.get(1);        } else {            return null;        }    }}
```

程序运行结果如图 3 所示。

![五子棋游戏的运行结果](http://c.biancheng.net/uploads/allimg/181115/3-1Q115162I4425.gif)
图3 五子棋游戏的运行结果

## 享元模式的应用场景

当系统中多处需要同一组信息时，可以把这些信息封装到一个对象中，然后对该对象进行缓存，这样，一个对象就可以提供给多出需要使用的地方，避免大量同一对象的多次创建，降低大量内存空间的消耗。

享元模式其实是[工厂方法模式](http://c.biancheng.net/view/1348.html)的一个改进机制，享元模式同样要求创建一个或一组对象，并且就是通过工厂方法模式生成对象的，只不过享元模式为工厂方法模式增加了缓存这一功能。

前面分析了享元模式的结构与特点，下面分析它适用的应用场景。享元模式是通过减少内存中对象的数量来节省内存空间的，所以以下几种情形适合采用享元模式。

1. 系统中存在大量相同或相似的对象，这些对象耗费大量的内存资源。
2. 大部分的对象可以按照内部状态进行分组，且可将不同部分外部化，这样每一个组只需保存一个内部状态。
3. 由于享元模式需要额外维护一个保存享元的[数据结构](http://c.biancheng.net/data_structure/)，所以应当在有足够多的享元实例时才值得使用享元模式。

## 享元模式的扩展

在前面介绍的享元模式中，其结构图通常包含可以共享的部分和不可以共享的部分。在实际使用过程中，有时候会稍加改变，即存在两种特殊的享元模式：单纯享元模式和复合享元模式，下面分别对它们进行简单介绍。

(1) 单纯享元模式，这种享元模式中的所有的具体享元类都是可以共享的，不存在非共享的具体享元类，其结构图如图 4 所示。



![单纯享元模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115161549429.gif)
图4 单纯享元模式的结构图


(2) 复合享元模式，这种享元模式中的有些享元对象是由一些单纯享元对象组合而成的，它们就是复合享元对象。虽然复合享元对象本身不能共享，但它们可以分解成单纯享元对象再被共享，其结构图如图 5 所示。



![复合享元模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q11516162C42.gif)
图5 复合享元模式的结构图