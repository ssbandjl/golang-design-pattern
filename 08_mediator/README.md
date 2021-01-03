# 中介者模式

中介者模式封装对象之间互交，使依赖变的简单，并且使复杂互交简单化，封装在中介者中。

例子中的中介者使用单例模式生成中介者。

中介者的change使用switch判断类型。

中介者实例, 串联起了各个"同事角色实例", 同事实例间的状态变化通过中介者的change方法来完成.


# 笔记
比如MVC模式中的中介者, view-controller-model, 由中介者controller来协调各模块间的关系.





在现实生活中，常常会出现好多对象之间存在复杂的交互关系，这种交互关系常常是“网状结构”，它要求每个对象都必须知道它需要交互的对象。例如，每个人必须记住他（她）所有朋友的电话；而且，朋友中如果有人的电话修改了，他（她）必须让其他所有的朋友一起修改，这叫作“牵一发而动全身”，非常复杂。

如果把这种“网状结构”改为“星形结构”的话，将大大降低它们之间的“耦合性”，这时只要找一个“中介者”就可以了。如前面所说的“每个人必须记住所有朋友电话”的问题，只要在网上建立一个每个朋友都可以访问的“通信录”就解决了。这样的例子还有很多，例如，你刚刚参加工作想租房，可以找“房屋中介”；或者，自己刚刚到一个陌生城市找工作，可以找“人才交流中心”帮忙。

在软件的开发过程中，这样的例子也很多，例如，在 MVC 框架中，控制器（C）就是模型（M）和视图（V）的中介者；还有大家常用的 QQ 聊天程序的“中介者”是 QQ 服务器。所有这些，都可以采用“中介者模式”来实现，它将大大降低对象之间的耦合性，提高系统的灵活性。

## 模式的定义与特点

中介者（Mediator）模式的定义：定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，且可以独立地改变它们之间的交互。中介者模式又叫调停模式，它是迪米特法则的典型应用。

中介者模式是一种对象行为型模式，其主要优点如下。

1. 类之间各司其职，符合迪米特法则。
2. 降低了对象之间的耦合性，使得对象易于独立地被复用。
3. 将对象间的一对多关联转变为一对一的关联，提高系统的灵活性，使得系统易于维护和扩展。


其主要缺点是：中介者模式将原本多个对象直接的相互依赖变成了中介者和多个同事类的依赖关系。当同事类越多时，中介者就会越臃肿，变得复杂且难以维护。

## 模式的结构与实现

中介者模式实现的关键是找出“中介者”，下面对它的结构和实现进行分析。

#### 1. 模式的结构

中介者模式包含以下主要角色。

1. 抽象中介者（Mediator）角色：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。
2. 具体中介者（Concrete Mediator）角色：实现中介者接口，定义一个 List 来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。
3. 抽象同事类（Colleague）角色：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。
4. 具体同事类（Concrete Colleague）角色：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。


中介者模式的结构图如图 1 所示。



![中介者模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161I532V0.gif)
图1 中介者模式的结构图

#### 2. 模式的实现

中介者模式的实现代码如下：

```
package net.biancheng.c.mediator;import java.util.*;public class MediatorPattern {    public static void main(String[] args) {        Mediator md = new ConcreteMediator();        Colleague c1, c2;        c1 = new ConcreteColleague1();        c2 = new ConcreteColleague2();        md.register(c1);        md.register(c2);        c1.send();        System.out.println("-------------");        c2.send();    }}//抽象中介者abstract class Mediator {    public abstract void register(Colleague colleague);    public abstract void relay(Colleague cl); //转发}//具体中介者class ConcreteMediator extends Mediator {    private List<Colleague> colleagues = new ArrayList<Colleague>();    public void register(Colleague colleague) {        if (!colleagues.contains(colleague)) {            colleagues.add(colleague);            colleague.setMedium(this);        }    }    public void relay(Colleague cl) {        for (Colleague ob : colleagues) {            if (!ob.equals(cl)) {                ((Colleague) ob).receive();            }        }    }}//抽象同事类abstract class Colleague {    protected Mediator mediator;    public void setMedium(Mediator mediator) {        this.mediator = mediator;    }    public abstract void receive();    public abstract void send();}//具体同事类class ConcreteColleague1 extends Colleague {    public void receive() {        System.out.println("具体同事类1收到请求。");    }    public void send() {        System.out.println("具体同事类1发出请求。");        mediator.relay(this); //请中介者转发    }}//具体同事类class ConcreteColleague2 extends Colleague {    public void receive() {        System.out.println("具体同事类2收到请求。");    }    public void send() {        System.out.println("具体同事类2发出请求。");        mediator.relay(this); //请中介者转发    }}
```

程序的运行结果如下：

```
具体同事类1发出请求。
具体同事类2收到请求。
-------------
具体同事类2发出请求。
具体同事类1收到请求。
```

## 模式的应用实例

【例1】用中介者模式编写一个“韶关房地产交流平台”程序。

说明：韶关房地产交流平台是“房地产中介公司”提供给“卖方客户”与“买方客户”进行信息交流的平台，比较适合用中介者模式来实现。

首先，定义一个中介公司（Medium）接口，它是抽象中介者，它包含了客户注册方法 register(Customer member) 和信息转发方法 relay(String from,String ad)；再定义一个韶关房地产中介（EstateMedium）公司，它是具体中介者类，它包含了保存客户信息的 List 对象，并实现了中介公司中的抽象方法。

然后，定义一个客户（Customer）类，它是抽象同事类，其中包含了中介者的对象，和发送信息的 send(String ad) 方法与接收信息的 receive(String from，String ad) 方法的接口，由于本程序是窗体程序，所以本类继承 JPmme 类，并实现动作事件的处理方法 actionPerformed(ActionEvent e)。

最后，定义卖方（Seller）类和买方（Buyer）类，它们是具体同事类，是客户（Customer）类的子类，它们实现了父类中的抽象方法，通过中介者类进行信息交流，其结构图如图 2 所示。



![韶关房地产交流平台的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161I559410.gif)
图2 韶关房地产交流平台的结构图


程序代码如下：

```
package net.biancheng.c.mediator;import javax.swing.*;import java.awt.*;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.util.ArrayList;import java.util.List;public class DatingPlatform {    public static void main(String[] args) {        Medium md = new EstateMedium();    //房产中介        Customer member1, member2;        member1 = new Seller("张三(卖方)");        member2 = new Buyer("李四(买方)");        md.register(member1); //客户注册        md.register(member2);    }}//抽象中介者：中介公司interface Medium {    void register(Customer member); //客户注册    void relay(String from, String ad); //转发}//具体中介者：房地产中介class EstateMedium implements Medium {    private List<Customer> members = new ArrayList<Customer>();    public void register(Customer member) {        if (!members.contains(member)) {            members.add(member);            member.setMedium(this);        }    }    public void relay(String from, String ad) {        for (Customer ob : members) {            String name = ob.getName();            if (!name.equals(from)) {                ((Customer) ob).receive(from, ad);            }        }    }}//抽象同事类：客户abstract class Customer extends JFrame implements ActionListener {    private static final long serialVersionUID = -7219939540794786080L;    protected Medium medium;    protected String name;    JTextField SentText;    JTextArea ReceiveArea;    public Customer(String name) {        super(name);        this.name = name;    }    void ClientWindow(int x, int y) {        Container cp;        JScrollPane sp;        JPanel p1, p2;        cp = this.getContentPane();        SentText = new JTextField(18);        ReceiveArea = new JTextArea(10, 18);        ReceiveArea.setEditable(false);        p1 = new JPanel();        p1.setBorder(BorderFactory.createTitledBorder("接收内容："));        p1.add(ReceiveArea);        sp = new JScrollPane(p1);        cp.add(sp, BorderLayout.NORTH);        p2 = new JPanel();        p2.setBorder(BorderFactory.createTitledBorder("发送内容："));        p2.add(SentText);        cp.add(p2, BorderLayout.SOUTH);        SentText.addActionListener(this);        this.setLocation(x, y);        this.setSize(250, 330);        this.setResizable(false); //窗口大小不可调整        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        this.setVisible(true);    }    public void actionPerformed(ActionEvent e) {        String tempInfo = SentText.getText().trim();        SentText.setText("");        this.send(tempInfo);    }    public String getName() {        return name;    }    public void setMedium(Medium medium) {        this.medium = medium;    }    public abstract void send(String ad);    public abstract void receive(String from, String ad);}//具体同事类：卖方class Seller extends Customer {    private static final long serialVersionUID = -1443076716629516027L;    public Seller(String name) {        super(name);        ClientWindow(50, 100);    }    public void send(String ad) {        ReceiveArea.append("我(卖方)说: " + ad + "\n");        //使滚动条滚动到最底端        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());        medium.relay(name, ad);    }    public void receive(String from, String ad) {        ReceiveArea.append(from + "说: " + ad + "\n");        //使滚动条滚动到最底端        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());    }}//具体同事类：买方class Buyer extends Customer {    private static final long serialVersionUID = -474879276076308825L;    public Buyer(String name) {        super(name);        ClientWindow(350, 100);    }    public void send(String ad) {        ReceiveArea.append("我(买方)说: " + ad + "\n");        //使滚动条滚动到最底端        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());        medium.relay(name, ad);    }    public void receive(String from, String ad) {        ReceiveArea.append(from + "说: " + ad + "\n");        //使滚动条滚动到最底端        ReceiveArea.setCaretPosition(ReceiveArea.getText().length());    }}
```

程序的运行结果如图 3 所示。



![韶关房地产交流平台的运行结果](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161I6313F.gif)
图3 韶关房地产交流平台的运行结果

## 模式的应用场景

前面分析了中介者模式的结构与特点，下面分析其以下应用场景。

- 当对象之间存在复杂的网状结构关系而导致依赖关系混乱且难以复用时。
- 当想创建一个运行于多个类之间的对象，又不想生成新的子类时。

## 模式的扩展

在实际开发中，通常采用以下两种方法来简化中介者模式，使开发变得更简单。

1. 不定义中介者接口，把具体中介者对象实现成为单例。
2. 同事对象不持有中介者，而是在需要的时候直接获取中介者对象并调用。


图 4 所示是简化中介者模式的结构图。



![简化中介者模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161IA5242.gif)
图4 简化中介者模式的结构图


程序代码如下：

```
package net.biancheng.c.mediator;import java.util.*;public class SimpleMediatorPattern {    public static void main(String[] args) {        SimpleColleague c1, c2;        c1 = new SimpleConcreteColleague1();        c2 = new SimpleConcreteColleague2();        c1.send();        System.out.println("-----------------");        c2.send();    }}//简单单例中介者class SimpleMediator {    private static SimpleMediator smd = new SimpleMediator();    private List<SimpleColleague> colleagues = new ArrayList<SimpleColleague>();    private SimpleMediator() {    }    public static SimpleMediator getMedium() {        return (smd);    }    public void register(SimpleColleague colleague) {        if (!colleagues.contains(colleague)) {            colleagues.add(colleague);        }    }    public void relay(SimpleColleague scl) {        for (SimpleColleague ob : colleagues) {            if (!ob.equals(scl)) {                ((SimpleColleague) ob).receive();            }        }    }}//抽象同事类interface SimpleColleague {    void receive();    void send();}//具体同事类class SimpleConcreteColleague1 implements SimpleColleague {    SimpleConcreteColleague1() {        SimpleMediator smd = SimpleMediator.getMedium();        smd.register(this);    }    public void receive() {        System.out.println("具体同事类1：收到请求。");    }    public void send() {        SimpleMediator smd = SimpleMediator.getMedium();        System.out.println("具体同事类1：发出请求...");        smd.relay(this); //请中介者转发    }}//具体同事类class SimpleConcreteColleague2 implements SimpleColleague {    SimpleConcreteColleague2() {        SimpleMediator smd = SimpleMediator.getMedium();        smd.register(this);    }    public void receive() {        System.out.println("具体同事类2：收到请求。");    }    public void send() {        SimpleMediator smd = SimpleMediator.getMedium();        System.out.println("具体同事类2：发出请求...");        smd.relay(this); //请中介者转发    }}
```

程序运行结果如下：

```
具体同事类1：发出请求...
具体同事类2：收到请求。
-----------------
具体同事类2：发出请求...
具体同事类1：收到请求。
```