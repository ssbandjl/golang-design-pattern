# 装饰模式

装饰模式使用对象组合的方式动态改变或增加对象行为。

Go语言借助于匿名组合和非入侵式接口可以很方便实现装饰模式。

使用匿名组合，在装饰器中不必显式定义转调原对象方法。





#### 装饰器

阅读: 77843

------

> 动态地给一个对象添加一些额外的职责。就增加功能来说，相比生成子类更为灵活。

装饰器（Decorator）模式，是一种在运行期动态给某个对象的实例增加功能的方法。

我们在IO的[Filter模式](https://www.liaoxuefeng.com/wiki/1252599548343744/1298364142452770)一节中其实已经讲过装饰器模式了。在Java标准库中，`InputStream`是抽象类，`FileInputStream`、`ServletInputStream`、`Socket.getInputStream()`这些`InputStream`都是最终数据源。

现在，如果要给不同的最终数据源增加缓冲功能、计算签名功能、加密解密功能，那么，3个最终数据源、3种功能一共需要9个子类。如果继续增加最终数据源，或者增加新功能，子类会爆炸式增长，这种设计方式显然是不可取的。

Decorator模式的目的就是把一个一个的附加功能，用Decorator的方式给一层一层地累加到原始数据源上，最终，通过组合获得我们想要的功能。

例如：给`FileInputStream`增加缓冲和解压缩功能，用Decorator模式写出来如下：

```
// 创建原始的数据源:
InputStream fis = new FileInputStream("test.gz");
// 增加缓冲功能:
InputStream bis = new BufferedInputStream(fis);
// 增加解压缩功能:
InputStream gis = new GZIPInputStream(bis);
```

或者一次性写成这样：

```
InputStream input = new GZIPInputStream( // 第二层装饰
                        new BufferedInputStream( // 第一层装饰
                            new FileInputStream("test.gz") // 核心功能
                        ));
```

观察`BufferedInputStream`和`GZIPInputStream`，它们实际上都是从`FilterInputStream`继承的，这个`FilterInputStream`就是一个抽象的Decorator。我们用图把Decorator模式画出来如下：

```ascii
             ┌───────────┐
             │ Component │
             └───────────┘
                   ▲
      ┌────────────┼─────────────────┐
      │            │                 │
┌───────────┐┌───────────┐     ┌───────────┐
│ComponentA ││ComponentB │...  │ Decorator │
└───────────┘└───────────┘     └───────────┘
                                     ▲
                              ┌──────┴──────┐
                              │             │
                        ┌───────────┐ ┌───────────┐
                        │DecoratorA │ │DecoratorB │...
                        └───────────┘ └───────────┘
```

最顶层的Component是接口，对应到IO的就是`InputStream`这个抽象类。ComponentA、ComponentB是实际的子类，对应到IO的就是`FileInputStream`、`ServletInputStream`这些数据源。Decorator是用于实现各个附加功能的抽象装饰器，对应到IO的就是`FilterInputStream`。而从Decorator派生的就是一个一个的装饰器，它们每个都有独立的功能，对应到IO的就是`BufferedInputStream`、`GZIPInputStream`等。

Decorator模式有什么好处？它实际上把核心功能和附加功能给分开了。核心功能指`FileInputStream`这些真正读数据的源头，附加功能指加缓冲、压缩、解密这些功能。如果我们要新增核心功能，就增加Component的子类，例如`ByteInputStream`。如果我们要增加附加功能，就增加Decorator的子类，例如`CipherInputStream`。两部分都可以独立地扩展，而具体如何附加功能，由调用方自由组合，从而极大地增强了灵活性。

如果我们要自己设计完整的Decorator模式，应该如何设计？

我们还是举个栗子：假设我们需要渲染一个HTML的文本，但是文本还可以附加一些效果，比如加粗、变斜体、加下划线等。为了实现动态附加效果，可以采用Decorator模式。

首先，仍然需要定义顶层接口`TextNode`：

```
public interface TextNode {
    // 设置text:
    void setText(String text);
    // 获取text:
    String getText();
}
```

对于核心节点，例如`<span>`，它需要从`TextNode`直接继承：

```
public class SpanNode implements TextNode {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return "<span>" + text + "</span>";
    }
}
```

紧接着，为了实现Decorator模式，需要有一个抽象的Decorator类：

```
public abstract class NodeDecorator implements TextNode {
    protected final TextNode target;

    protected NodeDecorator(TextNode target) {
        this.target = target;
    }

    public void setText(String text) {
        this.target.setText(text);
    }
}
```

这个`NodeDecorator`类的核心是持有一个`TextNode`，即将要把功能附加到的`TextNode`实例。接下来就可以写一个加粗功能：

```
public class BoldDecorator extends NodeDecorator {
    public BoldDecorator(TextNode target) {
        super(target);
    }

    public String getText() {
        return "<b>" + target.getText() + "</b>";
    }
}
```

类似的，可以继续加`ItalicDecorator`、`UnderlineDecorator`等。客户端可以自由组合这些Decorator：

```
TextNode n1 = new SpanNode();
TextNode n2 = new BoldDecorator(new UnderlineDecorator(new SpanNode()));
TextNode n3 = new ItalicDecorator(new BoldDecorator(new SpanNode()));
n1.setText("Hello");
n2.setText("Decorated");
n3.setText("World");
System.out.println(n1.getText());
// 输出<span>Hello</span>

System.out.println(n2.getText());
// 输出<b><u><span>Decorated</span></u></b>

System.out.println(n3.getText());
// 输出<i><b><span>World</span></b></i>
```

### 练习

使用Decorator添加一个`<del>`标签表示删除。

从[![img](data:image/png;base64,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)](https://gitee.com/)下载练习：[Decorator练习](https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java教程/190.设计模式.1264742167474528/20.结构型模式.1281319233388578/40.装饰器.1281319302594594/pattern-decorator.zip?utm_source=blog_lxf) （推荐使用[IDE练习插件](https://www.liaoxuefeng.com/wiki/1252599548343744/1266092093733664)快速下载）

### 小结

使用Decorator模式，可以独立增加核心功能，也可以独立增加附加功能，二者互不影响；

可以在运行期动态地给核心功能增加任意个附加功能。





上班族大多都有睡懒觉的习惯，每天早上上班时间都很紧张，于是很多人为了多睡一会，就会用方便的方式解决早餐问题。有些人早餐可能会吃煎饼，煎饼中可以加鸡蛋，也可以加香肠，但是不管怎么“加码”，都还是一个煎饼。在现实生活中，常常需要对现有产品增加新的功能或美化其外观，如房子装修、相片加相框等，都是装饰器模式。

在软件开发过程中，有时想用一些现存的组件。这些组件可能只是完成了一些核心功能。但在不改变其结构的情况下，可以动态地扩展其功能。所有这些都可以釆用装饰模式来实现。

## 装饰模式的定义与特点

装饰（Decorator）模式的定义：指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式，它属于对象结构型模式。

装饰（Decorator）模式的主要优点有：

- 装饰器是继承的有力补充，比继承灵活，在不改变原有对象的情况下，动态的给一个对象扩展功能，即插即用
- 通过使用不用装饰类及这些装饰类的排列组合，可以实现不同效果
- 装饰器模式完全遵守开闭原则


其主要缺点是：装饰模式会增加许多子类，过度使用会增加程序得复杂性。

## 装饰模式的结构与实现

通常情况下，扩展一个类的功能会使用继承方式来实现。但继承具有静态特征，耦合度高，并且随着扩展功能的增多，子类会很膨胀。如果使用组合关系来创建一个包装对象（即装饰对象）来包裹真实对象，并在保持真实对象的类结构不变的前提下，为其提供额外的功能，这就是装饰模式的目标。下面来分析其基本结构和实现方法。

#### 1. 模式的结构

装饰模式主要包含以下角色。

1. 抽象构件（Component）角色：定义一个抽象接口以规范准备接收附加责任的对象。
2. 具体构件（ConcreteComponent）角色：实现抽象构件，通过装饰角色为其添加一些职责。
3. 抽象装饰（Decorator）角色：继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
4. 具体装饰（ConcreteDecorator）角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。


装饰模式的结构图如图 1 所示。



![装饰模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115142115M2.gif)
图1 装饰模式的结构图

#### 2. 模式的实现

装饰模式的实现代码如下：

```
package decorator;public class DecoratorPattern {    public static void main(String[] args) {        Component p = new ConcreteComponent();        p.operation();        System.out.println("---------------------------------");        Component d = new ConcreteDecorator(p);        d.operation();    }}//抽象构件角色interface Component {    public void operation();}//具体构件角色class ConcreteComponent implements Component {    public ConcreteComponent() {        System.out.println("创建具体构件角色");    }    public void operation() {        System.out.println("调用具体构件角色的方法operation()");    }}//抽象装饰角色class Decorator implements Component {    private Component component;    public Decorator(Component component) {        this.component = component;    }    public void operation() {        component.operation();    }}//具体装饰角色class ConcreteDecorator extends Decorator {    public ConcreteDecorator(Component component) {        super(component);    }    public void operation() {        super.operation();        addedFunction();    }    public void addedFunction() {        System.out.println("为具体构件角色增加额外的功能addedFunction()");    }}
```


程序运行结果如下：

```
创建具体构件角色
调用具体构件角色的方法operation()
---------------------------------
调用具体构件角色的方法operation()
为具体构件角色增加额外的功能addedFunction()
```

## 装饰模式的应用实例

【例1】用装饰模式实现游戏角色“莫莉卡·安斯兰”的变身。

分析：在《恶魔战士》中，游戏角色“莫莉卡·安斯兰”的原身是一个可爱少女，但当她变身时，会变成头顶及背部延伸出蝙蝠状飞翼的女妖，当然她还可以变为穿着漂亮外衣的少女。这些都可用装饰模式来实现，在本实例中的“莫莉卡”原身有 setImage(String t) 方法决定其显示方式，而其 变身“蝙蝠状女妖”和“着装少女”可以用 setChanger() 方法来改变其外观，原身与变身后的效果用 display() 方法来显示（[点此下载其原身和变身后的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q115142F6.zip)），图 2 所示是其结构图。



![游戏角色“莫莉卡·安斯兰”的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115142204235.gif)
图2 游戏角色“莫莉卡·安斯兰”的结构图


程序代码如下：

```
package decorator;import java.awt.*;import javax.swing.*;public class MorriganAensland {    public static void main(String[] args) {        Morrigan m0 = new original();        m0.display();        Morrigan m1 = new Succubus(m0);        m1.display();        Morrigan m2 = new Girl(m0);        m2.display();    }}//抽象构件角色：莫莉卡interface Morrigan {    public void display();}//具体构件角色：原身class original extends JFrame implements Morrigan {    private static final long serialVersionUID = 1L;    private String t = "Morrigan0.jpg";    public original() {        super("《恶魔战士》中的莫莉卡·安斯兰");    }    public void setImage(String t) {        this.t = t;    }    public void display() {        this.setLayout(new FlowLayout());        JLabel l1 = new JLabel(new ImageIcon("src/decorator/" + t));        this.add(l1);        this.pack();        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        this.setVisible(true);    }}//抽象装饰角色：变形class Changer implements Morrigan {    Morrigan m;    public Changer(Morrigan m) {        this.m = m;    }    public void display() {        m.display();    }}//具体装饰角色：女妖class Succubus extends Changer {    public Succubus(Morrigan m) {        super(m);    }    public void display() {        setChanger();        super.display();    }    public void setChanger() {        ((original) super.m).setImage("Morrigan1.jpg");    }}//具体装饰角色：少女class Girl extends Changer {    public Girl(Morrigan m) {        super(m);    }    public void display() {        setChanger();        super.display();    }    public void setChanger() {        ((original) super.m).setImage("Morrigan2.jpg");    }}
```


程序运行结果如图 3 所示。



![游戏角色“莫莉卡·安斯兰”的变身](http://c.biancheng.net/uploads/allimg/181115/3-1Q115142234201.gif)
图3 游戏角色“莫莉卡·安斯兰”的变身

## 装饰模式的应用场景

前面讲解了关于装饰模式的结构与特点，下面介绍其适用的应用场景，装饰模式通常在以下几种情况使用。

- 当需要给一个现有类添加附加职责，而又不能采用生成子类的方法进行扩充时。例如，该类被隐藏或者该类是终极类或者采用继承方式会产生大量的子类。
- 当需要通过对现有的一组基本功能进行排列组合而产生非常多的功能时，采用继承关系很难实现，而采用装饰模式却很好实现。
- 当对象的功能要求可以动态地添加，也可以再动态地撤销时。


装饰模式在 [Java](http://c.biancheng.net/java/) 语言中的最著名的应用莫过于 Java I/O 标准库的设计了。例如，InputStream 的子类 FilterInputStream，OutputStream 的子类 FilterOutputStream，Reader 的子类 BufferedReader 以及 FilterReader，还有 Writer 的子类 BufferedWriter、FilterWriter 以及 PrintWriter 等，它们都是抽象装饰类。

下面代码是为 FileReader 增加缓冲区而采用的装饰类 BufferedReader 的例子：

```
BufferedReader in = new BufferedReader(new FileReader("filename.txt"));String s = in.readLine();
```

## 装饰模式的扩展

装饰模式所包含的 4 个角色不是任何时候都要存在的，在有些应用环境下模式是可以简化的，如以下两种情况。

(1) 如果只有一个具体构件而没有抽象构件时，可以让抽象装饰继承具体构件，其结构图如图 4 所示。



![只有一个具体构件的装饰模式](http://c.biancheng.net/uploads/allimg/181115/3-1Q11514230H05.gif)
图4 只有一个具体构件的装饰模式


(2) 如果只有一个具体装饰时，可以将抽象装饰和具体装饰合并，其结构图如图 5 所示。



![只有一个具体装饰的装饰模式](http://c.biancheng.net/uploads/allimg/181115/3-1Q115142333D3.gif)
图5 只有一个具体装饰的装饰模式