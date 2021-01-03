# 外观模式(门面模式)

API 为facade 模块的外观接口，大部分代码使用此接口简化对facade类的访问。

facade模块同时暴露了a和b 两个Module 的NewXXX和interface，其它代码如果需要使用细节功能时可以直接调用。



在现实生活中，常常存在办事较复杂的例子，如办房产证或注册一家公司，有时要同多个部门联系，这时要是有一个综合部门能解决一切手续问题就好了。

软件设计也是这样，当一个系统的功能越来越强，子系统会越来越多，客户对系统的访问也变得越来越复杂。这时如果系统内部发生改变，客户端也要跟着改变，这违背了“开闭原则”，也违背了“迪米特法则”，所以有必要为多个子系统提供一个统一的接口，从而降低系统的耦合度，这就是外观模式的目标。

图 1 给出了客户去当地房产局办理房产证过户要遇到的相关部门。



![办理房产证过户的相关部门](http://c.biancheng.net/uploads/allimg/181115/3-1Q11515205E60.gif)
图1 办理房产证过户的相关部门

## 外观模式的定义与特点

外观（Facade）模式又叫作门面模式，是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式。该模式对外有一个统一接口，外部应用程序不用关心内部子系统的具体细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。

在日常编码工作中，我们都在有意无意的大量使用外观模式。只要是高层模块需要调度多个子系统（2个以上的类对象），我们都会自觉地创建一个新的类封装这些子系统，提供精简的接口，让高层模块可以更加容易地间接调用这些子系统的功能。尤其是现阶段各种第三方SDK、开源类库，很大概率都会使用外观模式。

外观（Facade）模式是“迪米特法则”的典型应用，它有以下主要优点。

1. 降低了子系统与客户端之间的耦合度，使得子系统的变化不会影响调用它的客户类。
2. 对客户屏蔽了子系统组件，减少了客户处理的对象数目，并使得子系统使用起来更加容易。
3. 降低了大型软件系统中的编译依赖性，简化了系统在不同平台之间的移植过程，因为编译一个子系统不会影响其他的子系统，也不会影响外观对象。


外观（Facade）模式的主要缺点如下。

1. 不能很好地限制客户使用子系统类，很容易带来未知风险。
2. 增加新的子系统可能需要修改外观类或客户端的源代码，违背了“开闭原则”。

## 外观模式的结构与实现

外观（Facade）模式的结构比较简单，主要是定义了一个高层接口。它包含了对各个子系统的引用，客户端可以通过它访问各个子系统的功能。现在来分析其基本结构和实现方法。

#### 1. 模式的结构

外观（Facade）模式包含以下主要角色。

1. 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
2. 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。
3. 客户（Client）角色：通过一个外观角色访问各个子系统的功能。


其结构图如图 2 所示。



![外观模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115152143509.gif)
图2 外观（Facade）模式的结构图

#### 2. 模式的实现

外观模式的实现代码如下：

```java
package facade;
public class FacadePattern {
    public static void main(String[] args) {
        Facade f = new Facade();
        f.method();
    }
}
//外观角色
class Facade {
    private SubSystem01 obj1 = new SubSystem01();
    private SubSystem02 obj2 = new SubSystem02();
    private SubSystem03 obj3 = new SubSystem03();
    public void method() {
        obj1.method1();
        obj2.method2();
        obj3.method3();
    }
}
//子系统角色
class SubSystem01 {
    public void method1() {
        System.out.println("子系统01的method1()被调用！");
    }
}
//子系统角色
class SubSystem02 {
    public void method2() {
        System.out.println("子系统02的method2()被调用！");
    }
}
//子系统角色
class SubSystem03 {
    public void method3() {
        System.out.println("子系统03的method3()被调用！");
    }
}
```



程序运行结果如下：

```java
子系统01的method1()被调用！
子系统02的method2()被调用！
子系统03的method3()被调用！
```

## 外观模式的应用实例

【例1】用“外观模式”设计一个婺源特产的选购界面。

分析：本实例的外观角色 WySpecialty 是 JPanel 的子类，它拥有 8 个子系统角色 Specialty1~Specialty8，它们是图标类（ImageIcon）的子类对象，用来保存该婺源特产的图标（[点此下载要显示的婺源特产的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q115152634.zip)）。

外观类（WySpecialty）用 JTree 组件来管理婺源特产的名称，并定义一个事件处理方法 valueClianged(TreeSelectionEvent e)，当用户从树中选择特产时，该特产的图标对象保存在标签（JLabd）对象中。

客户窗体对象用分割面板来实现，左边放外观角色的目录树，右边放显示所选特产图像的标签。其结构图如图 3 所示。



![婺源特产管理界面的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q115152223406.gif)
图3 婺源特产管理界面的结构图


程序代码如下：

```java
package facade;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
public class WySpecialtyFacade {
    public static void main(String[] args) {
        JFrame f = new JFrame("外观模式: 婺源特产选择测试");
        Container cp = f.getContentPane();
        WySpecialty wys = new WySpecialty();
        JScrollPane treeView = new JScrollPane(wys.tree);
        JScrollPane scrollpane = new JScrollPane(wys.label);
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, treeView, scrollpane); //分割面版
        splitpane.setDividerLocation(230);     //设置splitpane的分隔线位置
        splitpane.setOneTouchExpandable(true); //设置splitpane可以展开或收起                      
        cp.add(splitpane);
        f.setSize(650, 350);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class WySpecialty extends JPanel implements TreeSelectionListener {
    private static final long serialVersionUID = 1L;
    final JTree tree;
    JLabel label;
    private Specialty1 s1 = new Specialty1();
    private Specialty2 s2 = new Specialty2();
    private Specialty3 s3 = new Specialty3();
    private Specialty4 s4 = new Specialty4();
    private Specialty5 s5 = new Specialty5();
    private Specialty6 s6 = new Specialty6();
    private Specialty7 s7 = new Specialty7();
    private Specialty8 s8 = new Specialty8();
    WySpecialty() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("婺源特产");
        DefaultMutableTreeNode node1 = null, node2 = null, tempNode = null;
        node1 = new DefaultMutableTreeNode("婺源四大特产（红、绿、黑、白）");
        tempNode = new DefaultMutableTreeNode("婺源荷包红鲤鱼");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源绿茶");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源龙尾砚");
        node1.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源江湾雪梨");
        node1.add(tempNode);
        top.add(node1);
        node2 = new DefaultMutableTreeNode("婺源其它土特产");
        tempNode = new DefaultMutableTreeNode("婺源酒糟鱼");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源糟米子糕");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源清明果");
        node2.add(tempNode);
        tempNode = new DefaultMutableTreeNode("婺源油煎灯");
        node2.add(tempNode);
        top.add(node2);
        tree = new JTree(top);
        tree.addTreeSelectionListener(this);
        label = new JLabel();
    }
    public void valueChanged(TreeSelectionEvent e) {
        if (e.getSource() == tree) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) return;
            if (node.isLeaf()) {
                Object object = node.getUserObject();
                String sele = object.toString();
                label.setText(sele);
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                sele = sele.substring(2, 4);
                if (sele.equalsIgnoreCase("荷包")) label.setIcon(s1);
                else if (sele.equalsIgnoreCase("绿茶")) label.setIcon(s2);
                else if (sele.equalsIgnoreCase("龙尾")) label.setIcon(s3);
                else if (sele.equalsIgnoreCase("江湾")) label.setIcon(s4);
                else if (sele.equalsIgnoreCase("酒糟")) label.setIcon(s5);
                else if (sele.equalsIgnoreCase("糟米")) label.setIcon(s6);
                else if (sele.equalsIgnoreCase("清明")) label.setIcon(s7);
                else if (sele.equalsIgnoreCase("油煎")) label.setIcon(s8);
                label.setHorizontalAlignment(JLabel.CENTER);
            }
        }
    }
}
class Specialty1 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty1() {
        super("src/facade/WyImage/Specialty11.jpg");
    }
}
class Specialty2 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty2() {
        super("src/facade/WyImage/Specialty12.jpg");
    }
}
class Specialty3 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty3() {
        super("src/facade/WyImage/Specialty13.jpg");
    }
}
class Specialty4 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty4() {
        super("src/facade/WyImage/Specialty14.jpg");
    }
}
class Specialty5 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty5() {
        super("src/facade/WyImage/Specialty21.jpg");
    }
}
class Specialty6 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty6() {
        super("src/facade/WyImage/Specialty22.jpg");
    }
}
class Specialty7 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty7() {
        super("src/facade/WyImage/Specialty23.jpg");
    }
}
class Specialty8 extends ImageIcon {
    private static final long serialVersionUID = 1L;
    Specialty8() {
        super("src/facade/WyImage/Specialty24.jpg");
    }
}
```

## 外观模式的应用场景

通常在以下情况下可以考虑使用外观模式。

1. 对分层结构系统构建时，使用外观模式定义子系统中每层的入口点可以简化子系统之间的依赖关系。
2. 当一个复杂系统的子系统很多时，外观模式可以为系统设计一个简单的接口供外界访问。
3. 当客户端与多个子系统之间存在很大的联系时，引入外观模式可将它们分离，从而提高子系统的独立性和可移植性。

## 外观模式的扩展

在外观模式中，当增加或移除子系统时需要修改外观类，这违背了“开闭原则”。如果引入抽象外观类，则在一定程度上解决了该问题，其结构图如图 5 所示。



![引入抽象外观类的外观模式的结构图](http://c.biancheng.net/uploads/allimg/181115/3-1Q1151524262a.gif)
图5 引入抽象外观类的外观模式的结构图