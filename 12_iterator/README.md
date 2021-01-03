# 迭代器模式

迭代器模式用于使用相同方式迭代不同类型集合或者隐藏集合类型的具体实现。

可以使用迭代器模式使遍历同时应用迭代策略，如请求新对象、过滤、处理对象等。





在现实生活以及程序设计中，经常要访问一个聚合对象中的各个元素，如“[数据结构](http://c.biancheng.net/data_structure/)”中的链表遍历，通常的做法是将链表的创建和遍历都放在同一个类中，但这种方式不利于程序的扩展，如果要更换遍历方法就必须修改程序源代码，这违背了 “开闭原则”。

既然将遍历方法封装在聚合类中不可取，那么聚合类中不提供遍历方法，将遍历方法由用户自己实现是否可行呢？答案是同样不可取，因为这种方式会存在两个缺点：

1. 暴露了聚合类的内部表示，使其数据不安全；
2. 增加了客户的负担。


“迭代器模式”能较好地克服以上缺点，它在客户访问类与聚合类之间插入一个迭代器，这分离了聚合对象与其遍历行为，对客户也隐藏了其内部细节，且满足“单一职责原则”和“开闭原则”，如 [Java](http://c.biancheng.net/java/) 中的 Collection、List、Set、Map 等都包含了迭代器。

迭代器模式在生活中应用的比较广泛，比如：物流系统中的传送带，不管传送的是什么物品，都会被打包成一个个箱子，并且有一个统一的二维码。这样我们不需要关心箱子里是什么，在分发时只需要一个个检查发送的目的地即可。再比如，我们平时乘坐交通工具，都是统一刷卡或者刷脸进站，而不需要关心是男性还是女性、是残疾人还是正常人等信息。

## 模式的定义与特点

迭代器（Iterator）模式的定义：提供一个对象来顺序访问聚合对象中的一系列数据，而不暴露聚合对象的内部表示。迭代器模式是一种对象行为型模式，其主要优点如下。

1. 访问一个聚合对象的内容而无须暴露它的内部表示。
2. 遍历任务交由迭代器完成，这简化了聚合类。
3. 它支持以不同方式遍历一个聚合，甚至可以自定义迭代器的子类以支持新的遍历。
4. 增加新的聚合类和迭代器类都很方便，无须修改原有代码。
5. 封装性良好，为遍历不同的聚合结构提供一个统一的接口。


其主要缺点是：增加了类的个数，这在一定程度上增加了系统的复杂性。

在日常开发中，我们几乎不会自己写迭代器。除非需要定制一个自己实现的数据结构对应的迭代器，否则，开源框架提供的 API 完全够用。

## 模式的结构与实现

迭代器模式是通过将聚合对象的遍历行为分离出来，抽象成迭代器类来实现的，其目的是在不暴露聚合对象的内部结构的情况下，让外部代码透明地访问聚合的内部数据。现在我们来分析其基本结构与实现方法。

#### 1. 模式的结构

迭代器模式主要包含以下角色。

1. 抽象聚合（Aggregate）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
2. 具体聚合（ConcreteAggregate）角色：实现抽象聚合类，返回一个具体迭代器的实例。
3. 抽象迭代器（Iterator）角色：定义访问和遍历聚合元素的接口，通常包含 hasNext()、first()、next() 等方法。
4. 具体迭代器（Concretelterator）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。


其结构图如图 1 所示。



![迭代器模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161PU9528.gif)
图1 迭代器模式的结构图

#### 2. 模式的实现

迭代器模式的实现代码如下：

```
package net.biancheng.c.iterator;import java.util.*;public class IteratorPattern {    public static void main(String[] args) {        Aggregate ag = new ConcreteAggregate();        ag.add("中山大学");        ag.add("华南理工");        ag.add("韶关学院");        System.out.print("聚合的内容有：");        Iterator it = ag.getIterator();        while (it.hasNext()) {            Object ob = it.next();            System.out.print(ob.toString() + "\t");        }        Object ob = it.first();        System.out.println("\nFirst：" + ob.toString());    }}//抽象聚合interface Aggregate {    public void add(Object obj);    public void remove(Object obj);    public Iterator getIterator();}//具体聚合class ConcreteAggregate implements Aggregate {    private List<Object> list = new ArrayList<Object>();    public void add(Object obj) {        list.add(obj);    }    public void remove(Object obj) {        list.remove(obj);    }    public Iterator getIterator() {        return (new ConcreteIterator(list));    }}//抽象迭代器interface Iterator {    Object first();    Object next();    boolean hasNext();}//具体迭代器class ConcreteIterator implements Iterator {    private List<Object> list = null;    private int index = -1;    public ConcreteIterator(List<Object> list) {        this.list = list;    }    public boolean hasNext() {        if (index < list.size() - 1) {            return true;        } else {            return false;        }    }    public Object first() {        index = 0;        Object obj = list.get(index);        ;        return obj;    }    public Object next() {        Object obj = null;        if (this.hasNext()) {            obj = list.get(++index);        }        return obj;    }}
```

程序运行结果如下：

```
聚合的内容有：中山大学    华南理工    韶关学院   
First：中山大学
```

## 模式的应用实例

【例1】用迭代器模式编写一个浏览婺源旅游风景图的程序。

分析：婺源的名胜古迹较多，要设计一个查看相关景点图片（[点此下载本实例所要显示的景点图片](http://c.biancheng.net/uploads/soft/181113/3-1Q1161Q640.zip)）和简介的程序，用“迭代器模式”设计比较合适。

首先，设计一个婺源景点（WyViewSpot）类来保存每张图片的名称与简介；再设计一个景点集（ViewSpotSet）接口，它是抽象聚合类，提供了增加和删除婺源景点的方法，以及获取迭代器的方法。

然后，定义一个婺源景点集（WyViewSpotSet）类，它是具体聚合类，用 ArrayList 来保存所有景点信息，并实现父类中的抽象方法；再定义婺源景点的抽象迭代器（ViewSpotltemtor）接口，其中包含了查看景点信息的相关方法。

最后，定义婺源景点的具体迭代器（WyViewSpotlterator）类，它实现了父类的抽象方法；客户端程序设计成窗口程序，它初始化婺源景点集（ViewSpotSet）中的数据，并实现 ActionListener 接口，它通过婺源景点迭代器（ViewSpotlterator）来査看婺源景点（WyViewSpot）的信息。图 2 所示是其结构图。



![婺源旅游风景图浏览程序的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161P9263W.gif)
图2 婺源旅游风景图浏览程序的结构图（[点此查看原图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161P940959.gif)）


程序代码如下：

```
package net.biancheng.c.iterator;import javax.swing.*;import java.awt.*;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.util.ArrayList;public class PictureIterator {    public static void main(String[] args) {        new PictureFrame();    }}//相框类class PictureFrame extends JFrame implements ActionListener {    private static final long serialVersionUID = 1L;    ViewSpotSet ag; //婺源景点集接口    ViewSpotIterator it; //婺源景点迭代器接口    WyViewSpot ob;    //婺源景点类    PictureFrame() {        super("中国最美乡村“婺源”的部分风景图");        this.setResizable(false);        ag = new WyViewSpotSet();        ag.add(new WyViewSpot("江湾", "江湾景区是婺源的一个国家5A级旅游景区，景区内有萧江宗祠、永思街、滕家老屋、婺源人家、乡贤园、百工坊等一大批古建筑，精美绝伦，做工精细。"));        ag.add(new WyViewSpot("李坑", "李坑村是一个以李姓聚居为主的古村落，是国家4A级旅游景区，其建筑风格独特，是著名的徽派建筑，给人一种安静、祥和的感觉。"));        ag.add(new WyViewSpot("思溪延村", "思溪延村位于婺源县思口镇境内，始建于南宋庆元五年（1199年），当时建村者俞氏以（鱼）思清溪水而名。"));        ag.add(new WyViewSpot("晓起村", "晓起有“中国茶文化第一村”与“国家级生态示范村”之美誉，村屋多为清代建筑，风格各具特色，村中小巷均铺青石，曲曲折折，回环如棋局。"));        ag.add(new WyViewSpot("菊径村", "菊径村形状为山环水绕型，小河成大半圆型，绕村庄将近一周，四周为高山环绕，符合中国的八卦“后山前水”设计，当地人称“脸盆村”。"));        ag.add(new WyViewSpot("篁岭", "篁岭是著名的“晒秋”文化起源地，也是一座距今近六百历史的徽州古村；篁岭属典型山居村落，民居围绕水口呈扇形梯状错落排布。"));        ag.add(new WyViewSpot("彩虹桥", "彩虹桥是婺源颇有特色的带顶的桥——廊桥，其不仅造型优美，而且它可在雨天里供行人歇脚，其名取自唐诗“两水夹明镜，双桥落彩虹”。"));        ag.add(new WyViewSpot("卧龙谷", "卧龙谷是国家4A级旅游区，这里飞泉瀑流泄银吐玉、彩池幽潭碧绿清新、山峰岩石挺拔奇巧，活脱脱一幅天然泼墨山水画。"));        it = ag.getIterator(); //获取婺源景点迭代器        ob = it.first();        this.showPicture(ob.getName(), ob.getIntroduce());    }    //显示图片    void showPicture(String Name, String Introduce) {        Container cp = this.getContentPane();        JPanel picturePanel = new JPanel();        JPanel controlPanel = new JPanel();        String FileName = "src/iterator/Picture/" + Name + ".jpg";        JLabel lb = new JLabel(Name, new ImageIcon(FileName), JLabel.CENTER);        JTextArea ta = new JTextArea(Introduce);        lb.setHorizontalTextPosition(JLabel.CENTER);        lb.setVerticalTextPosition(JLabel.TOP);        lb.setFont(new Font("宋体", Font.BOLD, 20));        ta.setLineWrap(true);        ta.setEditable(false);        //ta.setBackground(Color.orange);        picturePanel.setLayout(new BorderLayout(5, 5));        picturePanel.add("Center", lb);        picturePanel.add("South", ta);        JButton first, last, next, previous;        first = new JButton("第一张");        next = new JButton("下一张");        previous = new JButton("上一张");        last = new JButton("最末张");        first.addActionListener(this);        next.addActionListener(this);        previous.addActionListener(this);        last.addActionListener(this);        controlPanel.add(first);        controlPanel.add(next);        controlPanel.add(previous);        controlPanel.add(last);        cp.add("Center", picturePanel);        cp.add("South", controlPanel);        this.setSize(630, 550);        this.setVisible(true);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    @Override    public void actionPerformed(ActionEvent arg0) {        String command = arg0.getActionCommand();        if (command.equals("第一张")) {            ob = it.first();            this.showPicture(ob.getName(), ob.getIntroduce());        } else if (command.equals("下一张")) {            ob = it.next();            this.showPicture(ob.getName(), ob.getIntroduce());        } else if (command.equals("上一张")) {            ob = it.previous();            this.showPicture(ob.getName(), ob.getIntroduce());        } else if (command.equals("最末张")) {            ob = it.last();            this.showPicture(ob.getName(), ob.getIntroduce());        }    }}//婺源景点类class WyViewSpot {    private String Name;    private String Introduce;    WyViewSpot(String Name, String Introduce) {        this.Name = Name;        this.Introduce = Introduce;    }    public String getName() {        return Name;    }    public String getIntroduce() {        return Introduce;    }}//抽象聚合：婺源景点集接口interface ViewSpotSet {    void add(WyViewSpot obj);    void remove(WyViewSpot obj);    ViewSpotIterator getIterator();}//具体聚合：婺源景点集class WyViewSpotSet implements ViewSpotSet {    private ArrayList<WyViewSpot> list = new ArrayList<WyViewSpot>();    public void add(WyViewSpot obj) {        list.add(obj);    }    public void remove(WyViewSpot obj) {        list.remove(obj);    }    public ViewSpotIterator getIterator() {        return (new WyViewSpotIterator(list));    }}//抽象迭代器：婺源景点迭代器接口interface ViewSpotIterator {    boolean hasNext();    WyViewSpot first();    WyViewSpot next();    WyViewSpot previous();    WyViewSpot last();}//具体迭代器：婺源景点迭代器class WyViewSpotIterator implements ViewSpotIterator {    private ArrayList<WyViewSpot> list = null;    private int index = -1;    WyViewSpot obj = null;    public WyViewSpotIterator(ArrayList<WyViewSpot> list) {        this.list = list;    }    public boolean hasNext() {        if (index < list.size() - 1) {            return true;        } else {            return false;        }    }    public WyViewSpot first() {        index = 0;        obj = list.get(index);        return obj;    }    public WyViewSpot next() {        if (this.hasNext()) {            obj = list.get(++index);        }        return obj;    }    public WyViewSpot previous() {        if (index > 0) {            obj = list.get(--index);        }        return obj;    }    public WyViewSpot last() {        index = list.size() - 1;        obj = list.get(index);        return obj;    }}
```

程序运行结果如图 3 所示。



![婺源旅游风景图浏览程序的运行结果](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161Q029442.jpg)
图3 婺源旅游风景图浏览程序的运行结果（[点此查看原图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161Q123121.gif)）

## 模式的应用场景

前面介绍了关于迭代器模式的结构与特点，下面介绍其应用场景，迭代器模式通常在以下几种情况使用。

1. 当需要为聚合对象提供多种遍历方式时。
2. 当需要为遍历不同的聚合结构提供一个统一的接口时。
3. 当访问一个聚合对象的内容而无须暴露其内部细节的表示时。


由于聚合与迭代器的关系非常密切，所以大多数语言在实现聚合类时都提供了迭代器类，因此大数情况下使用语言中已有的聚合类的迭代器就已经够了。

## 模式的扩展

迭代器模式常常与[组合模式](http://c.biancheng.net/view/1373.html)结合起来使用，在对组合模式中的容器构件进行访问时，经常将迭代器潜藏在组合模式的容器构成类中。当然，也可以构造一个外部迭代器来对容器构件进行访问，其结构图如图 4 所示。



![组合迭代器模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161Q152626.gif)
图4 组合迭代器模式的结构图