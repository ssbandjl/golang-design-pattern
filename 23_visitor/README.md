# 访问者模式

访问者模式可以给一系列对象透明的添加功能，并且把相关代码封装到一个类中。

对象只要预留访问者接口`Accept`则后期为对象添加功能的时候就不需要改动对象。



#### 访问者

阅读: 35077

------

> 表示一个作用于某对象结构中的各元素的操作。它使你可以在不改变各元素的类的前提下定义作用于这些元素的新操作。

访问者模式（Visitor）是一种操作一组对象的操作，它的目的是不改变对象的定义，但允许新增不同的访问者，来定义新的操作。

访问者模式的设计比较复杂，如果我们查看GoF原始的访问者模式，它是这么设计的：

```ascii
   ┌─────────┐       ┌───────────────────────┐
   │ Client  │─ ─ ─ >│        Visitor        │
   └─────────┘       ├───────────────────────┤
        │            │visitElementA(ElementA)│
                     │visitElementB(ElementB)│
        │            └───────────────────────┘
                                 ▲
        │                ┌───────┴───────┐
                         │               │
        │         ┌─────────────┐ ┌─────────────┐
                  │  VisitorA   │ │  VisitorB   │
        │         └─────────────┘ └─────────────┘
        ▼
┌───────────────┐        ┌───────────────┐
│ObjectStructure│─ ─ ─ ─>│    Element    │
├───────────────┤        ├───────────────┤
│handle(Visitor)│        │accept(Visitor)│
└───────────────┘        └───────────────┘
                                 ▲
                        ┌────────┴────────┐
                        │                 │
                ┌───────────────┐ ┌───────────────┐
                │   ElementA    │ │   ElementB    │
                ├───────────────┤ ├───────────────┤
                │accept(Visitor)│ │accept(Visitor)│
                │doA()          │ │doB()          │
                └───────────────┘ └───────────────┘
```

上述模式的复杂之处在于上述访问者模式为了实现所谓的“双重分派”，设计了一个回调再回调的机制。因为Java只支持基于多态的单分派模式，这里强行模拟出“双重分派”反而加大了代码的复杂性。

这里我们只介绍简化的访问者模式。假设我们要递归遍历某个文件夹的所有子文件夹和文件，然后找出`.java`文件，正常的做法是写个递归：

```
void scan(File dir, List<File> collector) {
    for (File file : dir.listFiles()) {
        if (file.isFile() && file.getName().endsWith(".java")) {
            collector.add(file);
        } else if (file.isDir()) {
            // 递归调用:
            scan(file, collector);
        }
    }
}
```

上述代码的问题在于，扫描目录的逻辑和处理.java文件的逻辑混在了一起。如果下次需要增加一个清理`.class`文件的功能，就必须再重复写扫描逻辑。

因此，访问者模式先把数据结构（这里是文件夹和文件构成的树型结构）和对其的操作（查找文件）分离开，以后如果要新增操作（例如清理`.class`文件），只需要新增访问者，不需要改变现有逻辑。

用访问者模式改写上述代码步骤如下：

首先，我们需要定义访问者接口，即该访问者能够干的事情：

```
public interface Visitor {
    // 访问文件夹:
    void visitDir(File dir);
    // 访问文件:
    void visitFile(File file);
}
```

紧接着，我们要定义能持有文件夹和文件的数据结构`FileStructure`：

```
public class FileStructure {
    // 根目录:
    private File path;
    public FileStructure(File path) {
        this.path = path;
    }
}
```

然后，我们给`FileStructure`增加一个`handle()`方法，传入一个访问者：

```
public class FileStructure {
    ...

    public void handle(Visitor visitor) {
		scan(this.path, visitor);
	}

	private void scan(File file, Visitor visitor) {
		if (file.isDirectory()) {
            // 让访问者处理文件夹:
			visitor.visitDir(file);
			for (File sub : file.listFiles()) {
                // 递归处理子文件夹:
				scan(sub, visitor);
			}
		} else if (file.isFile()) {
            // 让访问者处理文件:
			visitor.visitFile(file);
		}
	}
}
```

这样，我们就把访问者的行为抽象出来了。如果我们要实现一种操作，例如，查找`.java`文件，就传入`JavaFileVisitor`：

```
FileStructure fs = new FileStructure(new File("."));
fs.handle(new JavaFileVisitor());
```

这个`JavaFileVisitor`实现如下：

```
public class JavaFileVisitor implements Visitor {
    public void visitDir(File dir) {
        System.out.println("Visit dir: " + dir);
    }

    public void visitFile(File file) {
        if (file.getName().endsWith(".java")) {
            System.out.println("Found java file: " + file);
        }
    }
}
```

类似的，如果要清理`.class`文件，可以再写一个`ClassFileClearnerVisitor`：

```
public class ClassFileCleanerVisitor implements Visitor {
	public void visitDir(File dir) {
	}

	public void visitFile(File file) {
		if (file.getName().endsWith(".class")) {
			System.out.println("Will clean class file: " + file);
		}
	}
}
```

可见，访问者模式的核心思想是为了访问比较复杂的数据结构，不去改变数据结构，而是把对数据的操作抽象出来，在“访问”的过程中以回调形式在访问者中处理操作逻辑。如果要新增一组操作，那么只需要增加一个新的访问者。

实际上，Java标准库提供的`Files.walkFileTree()`已经实现了一个访问者模式：

`import java.io.*; import java.nio.file.*; import java.nio.file.attribute.*; ` Run

`Files.walkFileTree()`允许访问者返回`FileVisitResult.CONTINUE`以便继续访问，或者返回`FileVisitResult.TERMINATE`停止访问。

类似的，对XML的SAX处理也是一个访问者模式，我们需要提供一个SAX Handler作为访问者处理XML的各个节点。

### 练习

从[![img](data:image/png;base64,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)](https://gitee.com/)下载练习：[使用访问者模式递归遍历文件夹](https://gitee.com/liaoxuefeng/learn-java/blob/master/practices/Java教程/190.设计模式.1264742167474528/30.行为型模式.1281319453589538/110.访问者.1281319659110433/pattern-visitor.zip?utm_source=blog_lxf) （推荐使用[IDE练习插件](https://www.liaoxuefeng.com/wiki/1252599548343744/1266092093733664)快速下载）

### 小结

访问者模式是为了抽象出作用于一组复杂对象的操作，并且后续可以新增操作而不必对现有的对象结构做任何改动。



在现实生活中，有些集合对象存在多种不同的元素，且每种元素也存在多种不同的访问者和处理方式。例如，公园中存在多个景点，也存在多个游客，不同的游客对同一个景点的评价可能不同；医院医生开的处方单中包含多种药元素，査看它的划价员和药房工作人员对它的处理方式也不同，划价员根据处方单上面的药品名和数量进行划价，药房工作人员根据处方单的内容进行抓药。

这样的例子还有很多，例如，电影或电视剧中的人物角色，不同的观众对他们的评价也不同；还有顾客在商场购物时放在“购物车”中的商品，顾客主要关心所选商品的性价比，而收银员关心的是商品的价格和数量。

这些被处理的数据元素相对稳定而访问方式多种多样的[数据结构](http://c.biancheng.net/data_structure/)，如果用“访问者模式”来处理比较方便。访问者模式能把处理方法从数据结构中分离出来，并可以根据需要增加新的处理方法，且不用修改原来的程序代码与数据结构，这提高了程序的扩展性和灵活性。

## 模式的定义与特点

访问者（Visitor）模式的定义：将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，为数据结构中的每个元素提供多种访问方式。它将对数据的操作与数据结构进行分离，是行为类模式中最复杂的一种模式。

访问者（Visitor）模式是一种对象行为型模式，其主要优点如下。

1. 扩展性好。能够在不修改对象结构中的元素的情况下，为对象结构中的元素添加新的功能。
2. 复用性好。可以通过访问者来定义整个对象结构通用的功能，从而提高系统的复用程度。
3. 灵活性好。访问者模式将数据结构与作用于结构上的操作解耦，使得操作集合可相对自由地演化而不影响系统的数据结构。
4. 符合单一职责原则。访问者模式把相关的行为封装在一起，构成一个访问者，使每一个访问者的功能都比较单一。


访问者（Visitor）模式的主要缺点如下。

1. 增加新的元素类很困难。在访问者模式中，每增加一个新的元素类，都要在每一个具体访问者类中增加相应的具体操作，这违背了“开闭原则”。
2. 破坏封装。访问者模式中具体元素对访问者公布细节，这破坏了对象的封装性。
3. 违反了依赖倒置原则。访问者模式依赖了具体类，而没有依赖抽象类。

## 模式的结构与实现

访问者（Visitor）模式实现的关键是如何将作用于元素的操作分离出来封装成独立的类，其基本结构与实现方法如下。

#### 1. 模式的结构

访问者模式包含以下主要角色。

1. 抽象访问者（Visitor）角色：定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作 visit() ，该操作中的参数类型标识了被访问的具体元素。
2. 具体访问者（ConcreteVisitor）角色：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
3. 抽象元素（Element）角色：声明一个包含接受操作 accept() 的接口，被接受的访问者对象作为 accept() 方法的参数。
4. 具体元素（ConcreteElement）角色：实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visit(this) ，另外具体元素中可能还包含本身业务逻辑的相关操作。
5. 对象结构（Object Structure）角色：是一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常由 List、Set、Map 等聚合类实现。


其结构图如图 1 所示。



[![访问者（Visitor）模式的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q11910135Y25.gif)](http://c.biancheng.net/uploads/allimg/181119/3-1Q119101429D6.gif)
图1 访问者（Visitor）模式的结构图（[点此查看原图](http://c.biancheng.net/uploads/allimg/181119/3-1Q119101429D6.gif)）

#### 2. 模式的实现

访问者模式的实现代码如下：

```
package net.biancheng.c.visitor;import java.util.*;public class VisitorPattern {    public static void main(String[] args) {        ObjectStructure os = new ObjectStructure();        os.add(new ConcreteElementA());        os.add(new ConcreteElementB());        Visitor visitor = new ConcreteVisitorA();        os.accept(visitor);        System.out.println("------------------------");        visitor = new ConcreteVisitorB();        os.accept(visitor);    }}//抽象访问者interface Visitor {    void visit(ConcreteElementA element);    void visit(ConcreteElementB element);}//具体访问者A类class ConcreteVisitorA implements Visitor {    public void visit(ConcreteElementA element) {        System.out.println("具体访问者A访问-->" + element.operationA());    }    public void visit(ConcreteElementB element) {        System.out.println("具体访问者A访问-->" + element.operationB());    }}//具体访问者B类class ConcreteVisitorB implements Visitor {    public void visit(ConcreteElementA element) {        System.out.println("具体访问者B访问-->" + element.operationA());    }    public void visit(ConcreteElementB element) {        System.out.println("具体访问者B访问-->" + element.operationB());    }}//抽象元素类interface Element {    void accept(Visitor visitor);}//具体元素A类class ConcreteElementA implements Element {    public void accept(Visitor visitor) {        visitor.visit(this);    }    public String operationA() {        return "具体元素A的操作。";    }}//具体元素B类class ConcreteElementB implements Element {    public void accept(Visitor visitor) {        visitor.visit(this);    }    public String operationB() {        return "具体元素B的操作。";    }}//对象结构角色class ObjectStructure {    private List<Element> list = new ArrayList<Element>();    public void accept(Visitor visitor) {        Iterator<Element> i = list.iterator();        while (i.hasNext()) {            ((Element) i.next()).accept(visitor);        }    }    public void add(Element element) {        list.add(element);    }    public void remove(Element element) {        list.remove(element);    }}
```

程序的运行结果如下：

```
具体访问者A访问-->具体元素A的操作。
具体访问者A访问-->具体元素B的操作。
------------------------
具体访问者B访问-->具体元素A的操作。
具体访问者B访问-->具体元素B的操作。
```

## 模式的应用实例

【例1】利用“访问者（Visitor）模式”模拟艺术公司与造币公司的功能。

分析：艺术公司利用“铜”可以设计出铜像，利用“纸”可以画出图画；造币公司利用“铜”可以印出铜币，利用“纸”可以印出纸币（[点此下载运行该程序后所要显示的图片](http://c.biancheng.net/uploads/soft/181113/3-1Q119103045.zip)）。对“铜”和“纸”这两种元素，两个公司的处理方法不同，所以该实例用访问者模式来实现比较适合。

首先，定义一个公司（Company）接口，它是抽象访问者，提供了两个根据纸（Paper）或铜（Cuprum）这两种元素创建作品的方法；再定义艺术公司（ArtCompany）类和造币公司（Mint）类，它们是具体访问者，实现了父接口的方法。

然后，定义一个材料（Material）接口，它是抽象元素，提供了 accept（Company visitor）方法来接受访问者（Company）对象访问；再定义纸（Paper）类和铜（Cuprum）类，它们是具体元素类，实现了父接口中的方法。

最后，定义一个材料集（SetMaterial）类，它是对象结构角色，拥有保存所有元素的容器 List，并提供让访问者对象遍历容器中的所有元素的 accept（Company visitor）方法；客户类设计成窗体程序，它提供材料集（SetMaterial）对象供访问者（Company）对象访问，实现了 ItemListener 接口，处理用户的事件请求。图 2 所示是其结构图。



![艺术公司与造币公司的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q119101J2P8.gif)
图2 艺术公司与造币公司的结构图


程序代码如下：

```
package net.biancheng.c.visitor;import javax.swing.*;import java.awt.event.ItemEvent;import java.awt.event.ItemListener;import java.util.ArrayList;import java.util.Iterator;import java.util.List;public class VisitorProducer {    public static void main(String[] args) {        new MaterialWin();    }}//窗体类class MaterialWin extends JFrame implements ItemListener {    private static final long serialVersionUID = 1L;    JPanel CenterJP;    SetMaterial os;    //材料集对象    Company visitor1, visitor2;    //访问者对象    String[] select;    MaterialWin() {        super("利用访问者模式设计艺术公司和造币公司");        JRadioButton Art;        JRadioButton mint;        os = new SetMaterial();        os.add(new Cuprum());        os.add(new Paper());        visitor1 = new ArtCompany();//艺术公司        visitor2 = new Mint(); //造币公司        this.setBounds(10, 10, 750, 350);        this.setResizable(false);        CenterJP = new JPanel();        this.add("Center", CenterJP);        JPanel SouthJP = new JPanel();        JLabel yl = new JLabel("原材料有：铜和纸，请选择生产公司：");        Art = new JRadioButton("艺术公司", true);        mint = new JRadioButton("造币公司");        Art.addItemListener(this);        mint.addItemListener(this);        ButtonGroup group = new ButtonGroup();        group.add(Art);        group.add(mint);        SouthJP.add(yl);        SouthJP.add(Art);        SouthJP.add(mint);        this.add("South", SouthJP);        select = (os.accept(visitor1)).split(" ");    //获取产品名        showPicture(select[0], select[1]);    //显示产品    }    //显示图片    void showPicture(String Cuprum, String paper) {        CenterJP.removeAll();    //清除面板内容        CenterJP.repaint();    //刷新屏幕        String FileName1 = "src/visitor/Picture/" + Cuprum + ".jpg";        String FileName2 = "src/visitor/Picture/" + paper + ".jpg";        JLabel lb = new JLabel(new ImageIcon(FileName1), JLabel.CENTER);        JLabel rb = new JLabel(new ImageIcon(FileName2), JLabel.CENTER);        CenterJP.add(lb);        CenterJP.add(rb);        this.setVisible(true);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    @Override    public void itemStateChanged(ItemEvent arg0) {        JRadioButton jc = (JRadioButton) arg0.getSource();        if (jc.isSelected()) {            if (jc.getText() == "造币公司") {                select = (os.accept(visitor2)).split(" ");            } else {                select = (os.accept(visitor1)).split(" ");            }            showPicture(select[0], select[1]);    //显示选择的产品        }    }}//抽象访问者:公司interface Company {    String create(Paper element);    String create(Cuprum element);}//具体访问者：艺术公司class ArtCompany implements Company {    public String create(Paper element) {        return "讲学图";    }    public String create(Cuprum element) {        return "朱熹铜像";    }}//具体访问者：造币公司class Mint implements Company {    public String create(Paper element) {        return "纸币";    }    public String create(Cuprum element) {        return "铜币";    }}//抽象元素：材料interface Material {    String accept(Company visitor);}//具体元素：纸class Paper implements Material {    public String accept(Company visitor) {        return (visitor.create(this));    }}//具体元素：铜class Cuprum implements Material {    public String accept(Company visitor) {        return (visitor.create(this));    }}//对象结构角色:材料集class SetMaterial {    private List<Material> list = new ArrayList<Material>();    public String accept(Company visitor) {        Iterator<Material> i = list.iterator();        String tmp = "";        while (i.hasNext()) {            tmp += ((Material) i.next()).accept(visitor) + " ";        }        return tmp; //返回某公司的作品集    }    public void add(Material element) {        list.add(element);    }    public void remove(Material element) {        list.remove(element);    }}
```

程序运行结果如图 3 所示。



![艺术公司设计的产品](http://c.biancheng.net/uploads/allimg/181119/3-1Q119101U2436.jpg)
(a)艺术公司设计的产品



![造币公司生产的货币](http://c.biancheng.net/uploads/allimg/181119/3-1Q119101921H6.jpg)
(b)造币公司生产的货币



图3 艺术公司与造币公司的运行结果

## 模式的应用场景

当系统中存在类型数量稳定（固定）的一类数据结构时，可以使用访问者模式方便地实现对该类型所有数据结构的不同操作，而又不会对数据产生任何副作用（脏数据）。

简而言之，就是当对集合中的不同类型数据（类型数量稳定）进行多种操作时，使用访问者模式。

通常在以下情况可以考虑使用访问者（Visitor）模式。

1. 对象结构相对稳定，但其操作算法经常变化的程序。
2. 对象结构中的对象需要提供多种不同且不相关的操作，而且要避免让这些操作的变化影响对象的结构。
3. 对象结构包含很多类型的对象，希望对这些对象实施一些依赖于其具体类型的操作。

## 模式的扩展

访问者（Visitor）模式是使用频率较高的一种[设计模式](http://c.biancheng.net/design_pattern/)，它常常同以下两种设计模式联用。

(1)与“[迭代器模式](http://c.biancheng.net/view/1395.html)”联用。因为访问者模式中的“对象结构”是一个包含元素角色的容器，当访问者遍历容器中的所有元素时，常常要用迭代器。如【例1】中的对象结构是用 List 实现的，它通过 List 对象的 Iterator() 方法获取迭代器。如果对象结构中的聚合类没有提供迭代器，也可以用迭代器模式自定义一个。

(2)访问者（Visitor）模式同“[组合模式](http://c.biancheng.net/view/1373.html)”联用。因为访问者（Visitor）模式中的“元素对象”可能是叶子对象或者是容器对象，如果元素对象包含容器对象，就必须用到[组合模式](http://c.biancheng.net/view/1373.html)，其结构图如图 4 所示。



![包含组合模式的访问者模式的结构图](http://c.biancheng.net/uploads/allimg/181119/3-1Q11910210Jc.gif)
图4 包含组合模式的访问者模式的结构图