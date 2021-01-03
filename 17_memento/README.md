# 备忘录模式

备忘录模式用于保存程序内部状态到外部，又不希望暴露内部状态的情形。

程序内部状态使用窄接口船体给外部进行存储，从而不暴露程序实现细节。

备忘录模式同时可以离线保存内部状态，如保存到数据库，文件等。



每个人都有犯错误的时候，都希望有种“后悔药”能弥补自己的过失，让自己重新开始，但现实是残酷的。在计算机应用中，客户同样会常常犯错误，能否提供“后悔药”给他们呢？当然是可以的，而且是有必要的。这个功能由“备忘录模式”来实现。

其实很多应用软件都提供了这项功能，如 Word、记事本、Photoshop、Eclipse 等软件在编辑时按 Ctrl+Z 组合键时能撤销当前操作，使文档恢复到之前的状态；还有在 IE 中的后退键、数据库事务管理中的回滚操作、玩游戏时的中间结果存档功能、数据库与操作系统的备份操作、棋类游戏中的悔棋功能等都属于这类。

备忘录模式能记录一个对象的内部状态，当用户后悔时能撤销当前操作，使数据恢复到它原先的状态。

## 模式的定义与特点

备忘录（Memento）模式的定义：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，以便以后当需要时能将该对象恢复到原先保存的状态。该模式又叫快照模式。

备忘录模式是一种对象行为型模式，其主要优点如下。

- 提供了一种可以恢复状态的机制。当用户需要时能够比较方便地将数据恢复到某个历史的状态。
- 实现了内部状态的封装。除了创建它的发起人之外，其他对象都不能够访问这些状态信息。
- 简化了发起人类。发起人不需要管理和保存其内部状态的各个备份，所有状态信息都保存在备忘录中，并由管理者进行管理，这符合单一职责原则。


其主要缺点是：资源消耗大。如果要保存的内部状态信息过多或者特别频繁，将会占用比较大的内存资源。

## 模式的结构与实现

备忘录模式的核心是设计备忘录类以及用于管理备忘录的管理者类，现在我们来学习其结构与实现。

#### 1. 模式的结构

备忘录模式的主要角色如下。

1. 发起人（Originator）角色：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，它可以访问备忘录里的所有信息。
2. 备忘录（Memento）角色：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。
3. 管理者（Caretaker）角色：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。


备忘录模式的结构图如图 1 所示。



![备忘录模式的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q119130413927.gif)
图1 备忘录模式的结构图

#### 2. 模式的实现

备忘录模式的实现代码如下：

```
package net.biancheng.c.memento;public class MementoPattern {    public static void main(String[] args) {        Originator or = new Originator();        Caretaker cr = new Caretaker();        or.setState("S0");        System.out.println("初始状态:" + or.getState());        cr.setMemento(or.createMemento()); //保存状态        or.setState("S1");        System.out.println("新的状态:" + or.getState());        or.restoreMemento(cr.getMemento()); //恢复状态        System.out.println("恢复状态:" + or.getState());    }}//备忘录class Memento {    private String state;    public Memento(String state) {        this.state = state;    }    public void setState(String state) {        this.state = state;    }    public String getState() {        return state;    }}//发起人class Originator {    private String state;    public void setState(String state) {        this.state = state;    }    public String getState() {        return state;    }    public Memento createMemento() {        return new Memento(state);    }    public void restoreMemento(Memento m) {        this.setState(m.getState());    }}//管理者class Caretaker {    private Memento memento;    public void setMemento(Memento m) {        memento = m;    }    public Memento getMemento() {        return memento;    }}
```

程序运行的结果如下：

```
初始状态:S0
新的状态:S1
恢复状态:S0
```

## 模式的应用实例

【例1】利用备忘录模式设计相亲游戏。

分析：假如有西施、王昭君、貂蝉、杨玉环四大美女同你相亲，你可以选择其中一位作为你的爱人；当然，如果你对前面的选择不满意，还可以重新选择，但希望你不要太花心；这个游戏提供后悔功能，用“备忘录模式”设计比较合适（[点此下载所要显示的四大美女的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q119131144.zip)）。

首先，先设计一个美女（Girl）类，它是备忘录角色，提供了获取和存储美女信息的功能；然后，设计一个相亲者（You）类，它是发起人角色，它记录当前时刻的内部状态信息（临时妻子的姓名），并提供创建备忘录和恢复备忘录数据的功能；最后，定义一个美女栈（GirlStack）类，它是管理者角色，负责对备忘录进行管理，用于保存相亲者（You）前面选过的美女信息，不过最多只能保存 4 个，提供后悔功能。

客户类设计成窗体程序，它包含美女栈（GirlStack）对象和相亲者（You）对象，它实现了 ActionListener 接口的事件处理方法 actionPerformed(ActionEvent e)，并将 4 大美女图像和相亲者（You）选择的美女图像在窗体中显示出来。图 2 所示是其结构图。



![相亲游戏的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q119130439230.gif)
图2 相亲游戏的结构图


程序代码如下：

```
package net.biancheng.c.memento;import javax.swing.*;import java.awt.*;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;public class DatingGame {    public static void main(String[] args) {        new DatingGameWin();    }}//客户窗体类class DatingGameWin extends JFrame implements ActionListener {    private static final long serialVersionUID = 1L;    JPanel CenterJP, EastJP;    JRadioButton girl1, girl2, girl3, girl4;    JButton button1, button2;    String FileName;    JLabel g;    You you;    GirlStack girls;    DatingGameWin() {        super("利用备忘录模式设计相亲游戏");        you = new You();        girls = new GirlStack();        this.setBounds(0, 0, 900, 380);        this.setResizable(false);        FileName = "src/memento/Photo/四大美女.jpg";        g = new JLabel(new ImageIcon(FileName), JLabel.CENTER);        CenterJP = new JPanel();        CenterJP.setLayout(new GridLayout(1, 4));        CenterJP.setBorder(BorderFactory.createTitledBorder("四大美女如下："));        CenterJP.add(g);        this.add("Center", CenterJP);        EastJP = new JPanel();        EastJP.setLayout(new GridLayout(1, 1));        EastJP.setBorder(BorderFactory.createTitledBorder("您选择的爱人是："));        this.add("East", EastJP);        JPanel SouthJP = new JPanel();        JLabel info = new JLabel("四大美女有“沉鱼落雁之容、闭月羞花之貌”，您选择谁？");        girl1 = new JRadioButton("西施", true);        girl2 = new JRadioButton("貂蝉");        girl3 = new JRadioButton("王昭君");        girl4 = new JRadioButton("杨玉环");        button1 = new JButton("确定");        button2 = new JButton("返回");        ButtonGroup group = new ButtonGroup();        group.add(girl1);        group.add(girl2);        group.add(girl3);        group.add(girl4);        SouthJP.add(info);        SouthJP.add(girl1);        SouthJP.add(girl2);        SouthJP.add(girl3);        SouthJP.add(girl4);        SouthJP.add(button1);        SouthJP.add(button2);        button1.addActionListener(this);        button2.addActionListener(this);        this.add("South", SouthJP);        showPicture("空白");        you.setWife("空白");        girls.push(you.createMemento());    //保存状态    }    //显示图片    void showPicture(String name) {        EastJP.removeAll(); //清除面板内容        EastJP.repaint(); //刷新屏幕        you.setWife(name);        FileName = "src/memento/Photo/" + name + ".jpg";        g = new JLabel(new ImageIcon(FileName), JLabel.CENTER);        EastJP.add(g);        this.setVisible(true);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    @Override    public void actionPerformed(ActionEvent e) {        boolean ok = false;        if (e.getSource() == button1) {            ok = girls.push(you.createMemento());    //保存状态            if (ok && girl1.isSelected()) {                showPicture("西施");            } else if (ok && girl2.isSelected()) {                showPicture("貂蝉");            } else if (ok && girl3.isSelected()) {                showPicture("王昭君");            } else if (ok && girl4.isSelected()) {                showPicture("杨玉环");            }        } else if (e.getSource() == button2) {            you.restoreMemento(girls.pop()); //恢复状态            showPicture(you.getWife());        }    }}//备忘录：美女class Girl {    private String name;    public Girl(String name) {        this.name = name;    }    public void setName(String name) {        this.name = name;    }    public String getName() {        return name;    }}//发起人：您class You {    private String wifeName;    //妻子    public void setWife(String name) {        wifeName = name;    }    public String getWife() {        return wifeName;    }    public Girl createMemento() {        return new Girl(wifeName);    }    public void restoreMemento(Girl p) {        setWife(p.getName());    }}//管理者：美女栈class GirlStack {    private Girl girl[];    private int top;    GirlStack() {        girl = new Girl[5];        top = -1;    }    public boolean push(Girl p) {        if (top >= 4) {            System.out.println("你太花心了，变来变去的！");            return false;        } else {            girl[++top] = p;            return true;        }    }    public Girl pop() {        if (top <= 0) {            System.out.println("美女栈空了！");            return girl[0];        } else return girl[top--];    }}
```

程序运行结果如图 3 所示。



[![相亲游戏的运行结果](http://c.biancheng.net/uploads/allimg/181119/3-1Q119130526391.jpg)](http://c.biancheng.net/uploads/allimg/181119/3-1Q1191305555M.jpg)
图3 相亲游戏的运行结果（[点此查看原图](http://c.biancheng.net/uploads/allimg/181119/3-1Q1191305555M.jpg)）

## 模式的应用场景

前面学习了备忘录模式的定义与特点、结构与实现，现在来看该模式的以下应用场景。

1. 需要保存与恢复数据的场景，如玩游戏时的中间结果的存档功能。
2. 需要提供一个可回滚操作的场景，如 Word、记事本、Photoshop，Eclipse 等软件在编辑时按 Ctrl+Z 组合键，还有数据库中事务操作。

## 模式的扩展

在前面介绍的备忘录模式中，有单状态备份的例子，也有多状态备份的例子。下面介绍备忘录模式如何同[原型模式](http://c.biancheng.net/view/1343.html)混合使用。在备忘录模式中，通过定义“备忘录”来备份“发起人”的信息，而原型模式的 clone() 方法具有自备份功能，所以，如果让发起人实现 Cloneable 接口就有备份自己的功能，这时可以删除备忘录类，其结构图如图 4 所示。



![带原型的备忘录模式的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q119130HW56.gif)
图4 带原型的备忘录模式的结构图


实现代码如下：

```
package net.biancheng.c.memento;public class PrototypeMemento {    public static void main(String[] args) {        OriginatorPrototype or = new OriginatorPrototype();        PrototypeCaretaker cr = new PrototypeCaretaker();        or.setState("S0");        System.out.println("初始状态:" + or.getState());        cr.setMemento(or.createMemento()); //保存状态        or.setState("S1");        System.out.println("新的状态:" + or.getState());        or.restoreMemento(cr.getMemento()); //恢复状态        System.out.println("恢复状态:" + or.getState());    }}//发起人原型class OriginatorPrototype implements Cloneable {    private String state;    public void setState(String state) {        this.state = state;    }    public String getState() {        return state;    }    public OriginatorPrototype createMemento() {        return this.clone();    }    public void restoreMemento(OriginatorPrototype opt) {        this.setState(opt.getState());    }    public OriginatorPrototype clone() {        try {            return (OriginatorPrototype) super.clone();        } catch (CloneNotSupportedException e) {            e.printStackTrace();        }        return null;    }}//原型管理者class PrototypeCaretaker {    private OriginatorPrototype opt;    public void setMemento(OriginatorPrototype opt) {        this.opt = opt;    }    public OriginatorPrototype getMemento() {        return opt;    }}
```


程序的运行结果如下：

```
初始状态:S0
新的状态:S1
恢复状态:S0
```

#### 拓展

由于 JDK、[Spring](http://c.biancheng.net/spring/)、[Mybatis](http://c.biancheng.net/mybatis/) 中很少有备忘录模式，所以该[设计模式](http://c.biancheng.net/design_pattern/)不做典型应用源码分析。

Spring Webflow 中 DefaultMessageContext 类实现了 StateManageableMessageContext 接口，查看其源码可以发现其主要逻辑就相当于给 Message 备份。感兴趣的小伙伴可以去阅读学习其源码。