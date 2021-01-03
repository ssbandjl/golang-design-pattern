# 原型模式

原型模式使对象能复制自身，并且暴露到接口中，使客户端面向接口编程时，不知道接口实际对象的情况下生成新的对象。

原型模式配合原型管理器使用，使得客户端在不知道具体类的情况下，通过接口管理器得到新的实例，并且包含部分预设定配置。





在有些系统中，存在大量相同或相似对象的创建问题，如果用传统的构造函数来创建对象，会比较复杂且耗时耗资源，用原型模式生成对象就很高效，就像孙悟空拔下猴毛轻轻一吹就变出很多孙悟空一样简单。

## 原型模式的定义与特点

原型（Prototype）模式的定义如下：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。在这里，原型实例指定了要创建的对象的种类。用这种方式创建对象非常高效，根本无须知道对象创建的细节。例如，Windows 操作系统的安装通常较耗时，如果复制就快了很多。在生活中复制的例子非常多，这里不一一列举了。

#### 原型模式的优点：

- [Java](http://c.biancheng.net/java/) 自带的原型模式基于内存二进制流的复制，在性能上比直接 new 一个对象更加优良。
- 可以使用深克隆方式保存对象的状态，使用原型模式将对象复制一份，并将其状态保存起来，简化了创建对象的过程，以便在需要的时候使用（例如恢复到历史某一状态），可辅助实现撤销操作。

#### 原型模式的缺点：

- 需要为每一个类都配置一个 clone 方法
- clone 方法位于类的内部，当对已有类进行改造的时候，需要修改代码，违背了开闭原则。
- 当实现深克隆时，需要编写较为复杂的代码，而且当对象之间存在多重嵌套引用时，为了实现深克隆，每一层对象对应的类都必须支持深克隆，实现起来会比较麻烦。因此，深克隆、浅克隆需要运用得当。

## 原型模式的结构与实现

由于 Java 提供了对象的 clone() 方法，所以用 Java 实现原型模式很简单。

#### 1. 模式的结构

原型模式包含以下主要角色。

1. 抽象原型类：规定了具体原型对象必须实现的接口。
2. 具体原型类：实现抽象原型类的 clone() 方法，它是可被复制的对象。
3. 访问类：使用具体原型类中的 clone() 方法来复制新的对象。


其结构图如图 1 所示。



![原型模式的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q114101Fa22.gif)
图1 原型模式的结构图

#### 2. 模式的实现

原型模式的克隆分为浅克隆和深克隆。

- 浅克隆：创建一个新对象，新对象的属性和原来对象完全相同，对于非基本类型属性，仍指向原有属性所指向的对象的内存地址。
- 深克隆：创建一个新对象，属性中引用的其他对象也会被克隆，不再指向原有对象地址。


Java 中的 Object 类提供了浅克隆的 clone() 方法，具体原型类只要实现 Cloneable 接口就可实现对象的浅克隆，这里的 Cloneable 接口就是抽象原型类。其代码如下：

```
//具体原型类class Realizetype implements Cloneable {    Realizetype() {        System.out.println("具体原型创建成功！");    }    public Object clone() throws CloneNotSupportedException {        System.out.println("具体原型复制成功！");        return (Realizetype) super.clone();    }}//原型模式的测试类public class PrototypeTest {    public static void main(String[] args) throws CloneNotSupportedException {        Realizetype obj1 = new Realizetype();        Realizetype obj2 = (Realizetype) obj1.clone();        System.out.println("obj1==obj2?" + (obj1 == obj2));    }}
```


程序的运行结果如下：

```
具体原型创建成功！
具体原型复制成功！
obj1==obj2?false
```

## 原型模式的应用实例

【例1】用原型模式模拟“孙悟空”复制自己。

分析：孙悟空拔下猴毛轻轻一吹就变出很多孙悟空，这实际上是用到了原型模式。这里的孙悟空类 SunWukong 是具体原型类，而 Java 中的 Cloneable 接口是抽象原型类。

同前面介绍的猪八戒实例一样，由于要显示孙悟空的图像（[点击此处下载该程序所要显示的孙悟空的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q114103933.zip)），所以将孙悟空类定义成面板 JPanel 的子类，里面包含了标签，用于保存孙悟空的图像。

另外，重写了 Cloneable 接口的 clone() 方法，用于复制新的孙悟空。访问类可以通过调用孙悟空的 clone() 方法复制多个孙悟空，并在框架窗体 JFrame 中显示。图 2 所示是其结构图。



![孙悟空生成器的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q114101K4L9.gif)
图2 孙悟空生成器的结构图


程序代码如下：

```
import java.awt.*;import javax.swing.*;class SunWukong extends JPanel implements Cloneable {    private static final long serialVersionUID = 5543049531872119328L;    public SunWukong() {        JLabel l1 = new JLabel(new ImageIcon("src/Wukong.jpg"));        this.add(l1);    }    public Object clone() {        SunWukong w = null;        try {            w = (SunWukong) super.clone();        } catch (CloneNotSupportedException e) {            System.out.println("拷贝悟空失败!");        }        return w;    }}public class ProtoTypeWukong {    public static void main(String[] args) {        JFrame jf = new JFrame("原型模式测试");        jf.setLayout(new GridLayout(1, 2));        Container contentPane = jf.getContentPane();        SunWukong obj1 = new SunWukong();        contentPane.add(obj1);        SunWukong obj2 = (SunWukong) obj1.clone();        contentPane.add(obj2);        jf.pack();        jf.setVisible(true);        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }}
```

程序的运行结果如图 3 所示。



![孙悟空克隆器的运行结果](http://c.biancheng.net/uploads/allimg/181114/3-1Q114102002601.gif)
图3 孙悟空克隆器的运行结果


用原型模式除了可以生成相同的对象，还可以生成相似的对象，请看以下实例。

【例2】用原型模式生成“三好学生”奖状。

分析：同一学校的“三好学生”奖状除了获奖人姓名不同，其他都相同，属于相似对象的复制，同样可以用原型模式创建，然后再做简单修改就可以了。图 4 所示是三好学生奖状生成器的结构图。



![奖状生成器的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q114101SUJ.gif)
图4 奖状生成器的结构图


程序代码如下：

```
public class ProtoTypeCitation {    public static void main(String[] args) throws CloneNotSupportedException {        citation obj1 = new citation("张三", "同学：在2016学年第一学期中表现优秀，被评为三好学生。", "韶关学院");        obj1.display();        citation obj2 = (citation) obj1.clone();        obj2.setName("李四");        obj2.display();    }}//奖状类class citation implements Cloneable {    String name;    String info;    String college;    citation(String name, String info, String college) {        this.name = name;        this.info = info;        this.college = college;        System.out.println("奖状创建成功！");    }    void setName(String name) {        this.name = name;    }    String getName() {        return (this.name);    }    void display() {        System.out.println(name + info + college);    }    public Object clone() throws CloneNotSupportedException {        System.out.println("奖状拷贝成功！");        return (citation) super.clone();    }}
```


程序运行结果如下：

```
奖状创建成功！
张三同学：在2016学年第一学期中表现优秀，被评为三好学生。韶关学院
奖状拷贝成功！
李四同学：在2016学年第一学期中表现优秀，被评为三好学生。韶关学院
```

## 原型模式的应用场景

原型模式通常适用于以下场景。

- 对象之间相同或相似，即只是个别的几个属性不同的时候。
- 创建对象成本较大，例如初始化时间长，占用CPU太多，或者占用网络资源太多等，需要优化资源。
- 创建一个对象需要繁琐的数据准备或访问权限等，需要提高性能或者提高安全性。
- 系统中大量使用该类对象，且各个调用者都需要给它的属性重新赋值。


在 [Spring](http://c.biancheng.net/spring/) 中，原型模式应用的非常广泛，例如 scope='prototype'、JSON.parseObject() 等都是原型模式的具体应用。

## 原型模式的扩展

原型模式可扩展为带原型管理器的原型模式，它在原型模式的基础上增加了一个原型管理器 PrototypeManager 类。该类用 HashMap 保存多个复制的原型，Client 类可以通过管理器的 get(String id) 方法从中获取复制的原型。其结构图如图 5 所示。



![带原型管理器的原型模式的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q114102049214.gif)
图5 带原型管理器的原型模式的结构图


【例3】用带原型管理器的原型模式来生成包含“圆”和“正方形”等图形的原型，并计算其面积。分析：本实例中由于存在不同的图形类，例如，“圆”和“正方形”，它们计算面积的方法不一样，所以需要用一个原型管理器来管理它们，图 6 所示是其结构图。



![图形生成器的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q11410212a20.gif)
图6 图形生成器的结构图


程序代码如下：

```
import java.util.*;interface Shape extends Cloneable {    public Object clone();    //拷贝    public void countArea();    //计算面积}class Circle implements Shape {    public Object clone() {        Circle w = null;        try {            w = (Circle) super.clone();        } catch (CloneNotSupportedException e) {            System.out.println("拷贝圆失败!");        }        return w;    }    public void countArea() {        int r = 0;        System.out.print("这是一个圆，请输入圆的半径：");        Scanner input = new Scanner(System.in);        r = input.nextInt();        System.out.println("该圆的面积=" + 3.1415 * r * r + "\n");    }}class Square implements Shape {    public Object clone() {        Square b = null;        try {            b = (Square) super.clone();        } catch (CloneNotSupportedException e) {            System.out.println("拷贝正方形失败!");        }        return b;    }    public void countArea() {        int a = 0;        System.out.print("这是一个正方形，请输入它的边长：");        Scanner input = new Scanner(System.in);        a = input.nextInt();        System.out.println("该正方形的面积=" + a * a + "\n");    }}class ProtoTypeManager {    private HashMap<String, Shape> ht = new HashMap<String, Shape>();    public ProtoTypeManager() {        ht.put("Circle", new Circle());        ht.put("Square", new Square());    }    public void addshape(String key, Shape obj) {        ht.put(key, obj);    }    public Shape getShape(String key) {        Shape temp = ht.get(key);        return (Shape) temp.clone();    }}public class ProtoTypeShape {    public static void main(String[] args) {        ProtoTypeManager pm = new ProtoTypeManager();        Shape obj1 = (Circle) pm.getShape("Circle");        obj1.countArea();        Shape obj2 = (Shape) pm.getShape("Square");        obj2.countArea();    }}
```


运行结果如下所示：

```
这是一个圆，请输入圆的半径：3
该圆的面积=28.2735

这是一个正方形，请输入它的边长：3
该正方形的面积=9
```