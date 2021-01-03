# 命令模式

命令模式本质是把某个对象的方法调用封装到对象中，方便传递、存储、调用。

示例中把主板单中的启动(start)方法和重启(reboot)方法封装为命令对象，再传递到主机(box)对象中。于两个按钮进行绑定：

* 第一个机箱(box1)设置按钮1(button1) 为开机按钮2(button2)为重启。
* 第二个机箱(box1)设置按钮2(button2) 为开机按钮1(button1)为重启。

从而得到配置灵活性。


除了配置灵活外，使用命令模式还可以用作：

* 批处理
* 任务队列
* undo, redo

等把具体命令封装到对象中使用的场合



在软件开发系统中，“方法的请求者”与“方法的实现者”之间经常存在紧密的耦合关系，这不利于软件功能的扩展与维护。例如，想对方法进行“撤销、重做、记录”等处理都很不方便，因此“如何将方法的请求者与实现者解耦？”变得很重要，命令模式就能很好地解决这个问题。

在现实生活中，命令模式的例子也很多。比如看电视时，我们只需要轻轻一按遥控器就能完成频道的切换，这就是命令模式，将换台请求和换台处理完全解耦了。电视机遥控器（命令发送者）通过按钮（具体命令）来遥控电视机（命令接收者）。

再比如，我们去餐厅吃饭，菜单不是等到客人来了之后才定制的，而是已经预先配置好的。这样，客人来了就只需要点菜，而不是任由客人临时定制。餐厅提供的菜单就相当于把请求和处理进行了解耦，这就是命令模式的体现。

## 命令模式的定义与特点

命令（Command）模式的定义如下：将一个请求封装为一个对象，使发出请求的责任和执行请求的责任分割开。这样两者之间通过命令对象进行沟通，这样方便将命令对象进行储存、传递、调用、增加与管理。

命令模式的主要优点如下。

1. 通过引入中间件（抽象接口）降低系统的耦合度。
2. 扩展性良好，增加或删除命令非常方便。采用命令模式增加与删除命令不会影响其他类，且满足“开闭原则”。
3. 可以实现宏命令。命令模式可以与[组合模式](http://c.biancheng.net/view/1373.html)结合，将多个命令装配成一个组合命令，即宏命令。
4. 方便实现 Undo 和 Redo 操作。命令模式可以与后面介绍的[备忘录模式](http://c.biancheng.net/view/1400.html)结合，实现命令的撤销与恢复。
5. 可以在现有命令的基础上，增加额外功能。比如日志记录，结合装饰器模式会更加灵活。


其缺点是：

1. 可能产生大量具体的命令类。因为每一个具体操作都需要设计一个具体命令类，这会增加系统的复杂性。
2. 命令模式的结果其实就是接收方的执行结果，但是为了以命令的形式进行架构、解耦请求与实现，引入了额外类型结构（引入了请求方与抽象命令接口），增加了理解上的困难。不过这也是[设计模式](http://c.biancheng.net/design_pattern/)的通病，抽象必然会额外增加类的数量，代码抽离肯定比代码聚合更加难理解。

## 命令模式的结构与实现

可以将系统中的相关操作抽象成命令，使调用者与实现者相关分离，其结构如下。

#### 1. 模式的结构

命令模式包含以下主要角色。

1. 抽象命令类（Command）角色：声明执行命令的接口，拥有执行命令的抽象方法 execute()。
2. 具体命令类（Concrete Command）角色：是抽象命令类的具体实现类，它拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
3. 实现者/接收者（Receiver）角色：执行命令功能的相关操作，是具体命令对象业务的真正实现者。
4. 调用者/请求者（Invoker）角色：是请求的发送者，它通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，它不直接访问接收者。


其结构图如图 1 所示。



![命令模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q11611335E44.gif)
图1 命令模式的结构图

#### 2. 模式的实现

命令模式的代码如下：

```
package command;public class CommandPattern {    public static void main(String[] args) {        Command cmd = new ConcreteCommand();        Invoker ir = new Invoker(cmd);        System.out.println("客户访问调用者的call()方法...");        ir.call();    }}//调用者class Invoker {    private Command command;    public Invoker(Command command) {        this.command = command;    }    public void setCommand(Command command) {        this.command = command;    }    public void call() {        System.out.println("调用者执行命令command...");        command.execute();    }}//抽象命令interface Command {    public abstract void execute();}//具体命令class ConcreteCommand implements Command {    private Receiver receiver;    ConcreteCommand() {        receiver = new Receiver();    }    public void execute() {        receiver.action();    }}//接收者class Receiver {    public void action() {        System.out.println("接收者的action()方法被调用...");    }}
```

程序的运行结果如下：

```
客户访问调用者的call()方法...
调用者执行命令command...
接收者的action()方法被调用...
```

## 命令模式的应用实例

【例1】用命令模式实现客户去餐馆吃早餐的实例。

分析：客户去餐馆可选择的早餐有肠粉、河粉和馄饨等，客户可向服务员选择以上早餐中的若干种，服务员将客户的请求交给相关的厨师去做。这里的点早餐相当于“命令”，服务员相当于“调用者”，厨师相当于“接收者”，所以用命令模式实现比较合适。

- 首先，定义一个早餐类（Breakfast），它是抽象命令类，有抽象方法 cooking()，说明要做什么；
- 再定义其子类肠粉类（ChangFen）、馄饨类（HunTun）和河粉类（HeFen），它们是具体命令类，实现早餐类的 cooking() 方法，但它们不会具体做，而是交给具体的厨师去做；
- 具体厨师类有肠粉厨师（ChangFenChef）、馄饨厨师（HunTunChef）和河粉厨师（HeFenChef），他们是命令的接收者。


由于本实例要显示厨师做菜的效果图（[点此下载要显示的效果图](http://c.biancheng.net/uploads/soft/181113/3-1Q116125200.zip)），所以把每个厨师类定义为 JFrame 的子类；最后，定义服务员类（Waiter），它接收客户的做菜请求，并发出做菜的命令。客户类是通过服务员类来点菜的，图 2 所示是其结构图。



![客户在餐馆吃早餐的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161134341E.gif)
图2 客户在餐馆吃早餐的结构图


程序代码如下：

```
package command;import javax.swing.*;public class CookingCommand {    public static void main(String[] args) {        Breakfast food1 = new ChangFen();        Breakfast food2 = new HunTun();        Breakfast food3 = new HeFen();        Waiter fwy = new Waiter();        fwy.setChangFen(food1);//设置肠粉菜单        fwy.setHunTun(food2);  //设置河粉菜单        fwy.setHeFen(food3);   //设置馄饨菜单        fwy.chooseChangFen();  //选择肠粉        fwy.chooseHeFen();     //选择河粉        fwy.chooseHunTun();    //选择馄饨    }}//调用者：服务员class Waiter {    private Breakfast changFen, hunTun, heFen;    public void setChangFen(Breakfast f) {        changFen = f;    }    public void setHunTun(Breakfast f) {        hunTun = f;    }    public void setHeFen(Breakfast f) {        heFen = f;    }    public void chooseChangFen() {        changFen.cooking();    }    public void chooseHunTun() {        hunTun.cooking();    }    public void chooseHeFen() {        heFen.cooking();    }}//抽象命令：早餐interface Breakfast {    public abstract void cooking();}//具体命令：肠粉class ChangFen implements Breakfast {    private ChangFenChef receiver;    ChangFen() {        receiver = new ChangFenChef();    }    public void cooking() {        receiver.cooking();    }}//具体命令：馄饨class HunTun implements Breakfast {    private HunTunChef receiver;    HunTun() {        receiver = new HunTunChef();    }    public void cooking() {        receiver.cooking();    }}//具体命令：河粉class HeFen implements Breakfast {    private HeFenChef receiver;    HeFen() {        receiver = new HeFenChef();    }    public void cooking() {        receiver.cooking();    }}//接收者：肠粉厨师class ChangFenChef extends JFrame {    private static final long serialVersionUID = 1L;    JLabel l = new JLabel();    ChangFenChef() {        super("煮肠粉");        l.setIcon(new ImageIcon("src/command/ChangFen.jpg"));        this.add(l);        this.setLocation(30, 30);        this.pack();        this.setResizable(false);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    public void cooking() {        this.setVisible(true);    }}//接收者：馄饨厨师class HunTunChef extends JFrame {    private static final long serialVersionUID = 1L;    JLabel l = new JLabel();    HunTunChef() {        super("煮馄饨");        l.setIcon(new ImageIcon("src/command/HunTun.jpg"));        this.add(l);        this.setLocation(350, 50);        this.pack();        this.setResizable(false);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    public void cooking() {        this.setVisible(true);    }}//接收者：河粉厨师class HeFenChef extends JFrame {    private static final long serialVersionUID = 1L;    JLabel l = new JLabel();    HeFenChef() {        super("煮河粉");        l.setIcon(new ImageIcon("src/command/HeFen.jpg"));        this.add(l);        this.setLocation(200, 280);        this.pack();        this.setResizable(false);        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    }    public void cooking() {        this.setVisible(true);    }}
```

程序的运行结果如图 2 所示。



![客户在餐馆吃早餐的运行结果](http://c.biancheng.net/uploads/allimg/181116/3-1Q11611350Q06.jpg)
图2 客户在餐馆吃早餐的运行结果

## 命令模式的应用场景

当系统的某项操作具备命令语义，且命令实现不稳定（变化）时，可以通过命令模式解耦请求与实现。使用抽象命令接口使请求方的代码架构稳定，封装接收方具体命令的实现细节。接收方与抽象命令呈现弱耦合（内部方法无需一致），具备良好的扩展性。

命令模式通常适用于以下场景。

1. 请求调用者需要与请求接收者解耦时，命令模式可以使调用者和接收者不直接交互。
2. 系统随机请求命令或经常增加、删除命令时，命令模式可以方便地实现这些功能。
3. 当系统需要执行一组操作时，命令模式可以定义宏命令来实现该功能。
4. 当系统需要支持命令的撤销（Undo）操作和恢复（Redo）操作时，可以将命令对象存储起来，采用备忘录模式来实现。

## 命令模式的扩展

在软件开发中，有时将命令模式与前面学的组合模式联合使用，这就构成了宏命令模式，也叫组合命令模式。宏命令包含了一组命令，它充当了具体命令与调用者的双重角色，执行它时将递归调用它所包含的所有命令，其具体结构图如图 3 所示。



![组合命令模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q1161135322R.gif)
图3 组合命令模式的结构图


程序代码如下：

```
package command;import java.util.ArrayList;public class CompositeCommandPattern {    public static void main(String[] args) {        AbstractCommand cmd1 = new ConcreteCommand1();        AbstractCommand cmd2 = new ConcreteCommand2();        CompositeInvoker ir = new CompositeInvoker();        ir.add(cmd1);        ir.add(cmd2);        System.out.println("客户访问调用者的execute()方法...");        ir.execute();    }}//抽象命令interface AbstractCommand {    public abstract void execute();}//树叶构件: 具体命令1class ConcreteCommand1 implements AbstractCommand {    private CompositeReceiver receiver;    ConcreteCommand1() {        receiver = new CompositeReceiver();    }    public void execute() {        receiver.action1();    }}//树叶构件: 具体命令2class ConcreteCommand2 implements AbstractCommand {    private CompositeReceiver receiver;    ConcreteCommand2() {        receiver = new CompositeReceiver();    }    public void execute() {        receiver.action2();    }}//树枝构件: 调用者class CompositeInvoker implements AbstractCommand {    private ArrayList<AbstractCommand> children = new ArrayList<AbstractCommand>();    public void add(AbstractCommand c) {        children.add(c);    }    public void remove(AbstractCommand c) {        children.remove(c);    }    public AbstractCommand getChild(int i) {        return children.get(i);    }    public void execute() {        for (Object obj : children) {            ((AbstractCommand) obj).execute();        }    }}//接收者class CompositeReceiver {    public void action1() {        System.out.println("接收者的action1()方法被调用...");    }    public void action2() {        System.out.println("接收者的action2()方法被调用...");    }}
```

程序的运行结果如下：

```
客户访问调用者的execute()方法...
接收者的action1()方法被调用...
接收者的action2()方法被调用...
```