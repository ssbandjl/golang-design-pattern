# 工厂方法模式

工厂方法模式使用子类的方式延迟生成对象到子类中实现。

Go中不存在继承 所以使用匿名组合来实现

是简单工厂模式的升级版本, 解决简单工厂模式的缺点



在现实生活中社会分工越来越细，越来越专业化。各种产品有专门的工厂生产，彻底告别了自给自足的小农经济时代，这大大缩短了产品的生产周期，提高了生产效率。同样，在软件开发中能否做到软件对象的生产和使用相分离呢？能否在满足“开闭原则”的前提下，客户随意增删或改变对软件相关对象的使用呢？这就是本节要讨论的问题。

在《[简单工厂模式](http://c.biancheng.net/view/8387.html)》一节我们介绍了简单工厂模式，提到了简单工厂模式违背了开闭原则，而“工厂方法模式”是对简单工厂模式的进一步抽象化，其好处是可以使系统在不修改原来代码的情况下引进新的产品，即满足开闭原则。

#### 优点：

- 用户只需要知道具体工厂的名称就可得到所要的产品，无须知道产品的具体创建过程。
- 灵活性增强，对于新产品的创建，只需多写一个相应的工厂类。
- 典型的解耦框架。高层模块只需要知道产品的抽象类，无须关心其他实现类，满足迪米特法则、依赖倒置原则和里氏替换原则。

#### 缺点：

- 类的个数容易过多，增加复杂度
- 增加了系统的抽象性和理解难度
- 抽象产品只能生产一种产品，此弊端可使用[抽象工厂模式](http://c.biancheng.net/view/1351.html)解决。

#### 应用场景：

- 客户只知道创建产品的工厂名，而不知道具体的产品名。如 TCL 电视工厂、海信电视工厂等。
- 创建对象的任务由多个具体子工厂中的某一个完成，而抽象工厂只提供创建产品的接口。
- 客户不关心创建产品的细节，只关心产品的品牌

## 模式的结构与实现

工厂方法模式由抽象工厂、具体工厂、抽象产品和具体产品等4个要素构成。本节来分析其基本结构和实现方法。

#### 1. 模式的结构

工厂方法模式的主要角色如下。

1. 抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法 newProduct() 来创建产品。
2. 具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
3. 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
4. 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。


其结构图如图 1 所示。



![工厂方法模式的结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q114135A2M3.gif)
图1 工厂方法模式的结构图

#### 2. 模式的实现

根据图 1 写出该模式的代码如下：

```
package FactoryMethod;public class AbstractFactoryTest {    public static void main(String[] args) {        try {            Product a;            AbstractFactory af;            af = (AbstractFactory) ReadXML1.getObject();            a = af.newProduct();            a.show();        } catch (Exception e) {            System.out.println(e.getMessage());        }    }}//抽象产品：提供了产品的接口interface Product {    public void show();}//具体产品1：实现抽象产品中的抽象方法class ConcreteProduct1 implements Product {    public void show() {        System.out.println("具体产品1显示...");    }}//具体产品2：实现抽象产品中的抽象方法class ConcreteProduct2 implements Product {    public void show() {        System.out.println("具体产品2显示...");    }}//抽象工厂：提供了厂品的生成方法interface AbstractFactory {    public Product newProduct();}//具体工厂1：实现了厂品的生成方法class ConcreteFactory1 implements AbstractFactory {    public Product newProduct() {        System.out.println("具体工厂1生成-->具体产品1...");        return new ConcreteProduct1();    }}//具体工厂2：实现了厂品的生成方法class ConcreteFactory2 implements AbstractFactory {    public Product newProduct() {        System.out.println("具体工厂2生成-->具体产品2...");        return new ConcreteProduct2();    }}
package FactoryMethod;import javax.xml.parsers.*;import org.w3c.dom.*;import java.io.*;class ReadXML1 {    //该方法用于从XML配置文件中提取具体类类名，并返回一个实例对象    public static Object getObject() {        try {            //创建文档对象            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();            DocumentBuilder builder = dFactory.newDocumentBuilder();            Document doc;            doc = builder.parse(new File("src/FactoryMethod/config1.xml"));            //获取包含类名的文本节点            NodeList nl = doc.getElementsByTagName("className");            Node classNode = nl.item(0).getFirstChild();            String cName = "FactoryMethod." + classNode.getNodeValue();            //System.out.println("新类名："+cName);            //通过类名生成实例对象并将其返回            Class<?> c = Class.forName(cName);            Object obj = c.newInstance();            return obj;        } catch (Exception e) {            e.printStackTrace();            return null;        }    }}
```

注意：该程序中用到了 XML 文件，如果想要获取该文件，请点击“[下载](http://c.biancheng.net/uploads/soft/181113/3-1Q114140222.zip)”，就可以对其进行下载。

程序运行结果如下：

```
具体工厂1生成-->具体产品1...
具体产品1显示...
```


如果将 XML 配置文件中的 ConcreteFactory1 改为 ConcreteFactory2，则程序运行结果如下：

```
具体工厂2生成-->具体产品2...
具体产品2显示...
```

## 模式的应用实例

【例1】用工厂方法模式设计畜牧场。

分析：有很多种类的畜牧场，如养马场用于养马，养牛场用于养牛，所以该实例用工厂方法模式比较适合。

对养马场和养牛场等具体工厂类，只要定义一个生成动物的方法 newAnimal() 即可。由于要显示马类和牛类等具体产品类的图像，所以它们的构造函数中用到了 JPanel、JLabd 和 ImageIcon 等组件，并定义一个 show() 方法来显示它们。

客户端程序通过对象生成器类 ReadXML2 读取 XML 配置文件中的数据来决定养马还是养牛。其结构图如图 2 所示。



![畜牧场结构图](http://c.biancheng.net/uploads/allimg/181114/3-1Q11413554DT.gif)
图2 畜牧场结构图


注意：该程序中用到了 XML 文件，并且要显示马类和牛类等具体产品类的图像，如果想要获取 HTML 文件和图片，请点击“[下载](http://c.biancheng.net/uploads/soft/181113/3-1Q114140526.zip)”，就可以对其进行下载。

程序代码如下：

```
package FactoryMethod;import java.awt.*;import javax.swing.*;public class AnimalFarmTest {    public static void main(String[] args) {        try {            Animal a;            AnimalFarm af;            af = (AnimalFarm) ReadXML2.getObject();            a = af.newAnimal();            a.show();        } catch (Exception e) {            System.out.println(e.getMessage());        }    }}//抽象产品：动物类interface Animal {    public void show();}//具体产品：马类class Horse implements Animal {    JScrollPane sp;    JFrame jf = new JFrame("工厂方法模式测试");    public Horse() {        Container contentPane = jf.getContentPane();        JPanel p1 = new JPanel();        p1.setLayout(new GridLayout(1, 1));        p1.setBorder(BorderFactory.createTitledBorder("动物：马"));        sp = new JScrollPane(p1);        contentPane.add(sp, BorderLayout.CENTER);        JLabel l1 = new JLabel(new ImageIcon("src/A_Horse.jpg"));        p1.add(l1);        jf.pack();        jf.setVisible(false);        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭    }    public void show() {        jf.setVisible(true);    }}//具体产品：牛类class Cattle implements Animal {    JScrollPane sp;    JFrame jf = new JFrame("工厂方法模式测试");    public Cattle() {        Container contentPane = jf.getContentPane();        JPanel p1 = new JPanel();        p1.setLayout(new GridLayout(1, 1));        p1.setBorder(BorderFactory.createTitledBorder("动物：牛"));        sp = new JScrollPane(p1);        contentPane.add(sp, BorderLayout.CENTER);        JLabel l1 = new JLabel(new ImageIcon("src/A_Cattle.jpg"));        p1.add(l1);        jf.pack();        jf.setVisible(false);        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //用户点击窗口关闭    }    public void show() {        jf.setVisible(true);    }}//抽象工厂：畜牧场interface AnimalFarm {    public Animal newAnimal();}//具体工厂：养马场class HorseFarm implements AnimalFarm {    public Animal newAnimal() {        System.out.println("新马出生！");        return new Horse();    }}//具体工厂：养牛场class CattleFarm implements AnimalFarm {    public Animal newAnimal() {        System.out.println("新牛出生！");        return new Cattle();    }}
package FactoryMethod;import javax.xml.parsers.*;import org.w3c.dom.*;import java.io.*;class ReadXML2 {    public static Object getObject() {        try {            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();            DocumentBuilder builder = dFactory.newDocumentBuilder();            Document doc;            doc = builder.parse(new File("src/FactoryMethod/config2.xml"));            NodeList nl = doc.getElementsByTagName("className");            Node classNode = nl.item(0).getFirstChild();            String cName = "FactoryMethod." + classNode.getNodeValue();            System.out.println("新类名：" + cName);            Class<?> c = Class.forName(cName);            Object obj = c.newInstance();            return obj;        } catch (Exception e) {            e.printStackTrace();            return null;        }    }}
```

程序的运行结果如图 3 所示。



![畜牧场养殖的运行结果](http://c.biancheng.net/uploads/allimg/181114/3-1Q114135422T9.gif)
图3 畜牧场养殖的运行结果


注意：当需要生成的产品不多且不会增加，一个具体工厂类就可以完成任务时，可删除抽象工厂类。这时工厂方法模式将退化到简单工厂模式。

