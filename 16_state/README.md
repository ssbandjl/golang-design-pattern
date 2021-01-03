# 状态模式

状态模式用于分离状态和行为。





在软件开发过程中，应用程序中的部分对象可能会根据不同的情况做出不同的行为，我们把这种对象称为有状态的对象，而把影响对象行为的一个或多个动态变化的属性称为状态。当有状态的对象与外部事件产生互动时，其内部状态就会发生改变，从而使其行为也发生改变。如人都有高兴和伤心的时候，不同的情绪有不同的行为，当然外界也会影响其情绪变化。

对这种有状态的对象编程，传统的解决方案是：将这些所有可能发生的情况全都考虑到，然后使用 if-else 或 switch-case 语句来做状态判断，再进行不同情况的处理。但是显然这种做法对复杂的状态判断存在天然弊端，条件判断语句会过于臃肿，可读性差，且不具备扩展性，维护难度也大。且增加新的状态时要添加新的 if-else 语句，这违背了“开闭原则”，不利于程序的扩展。

以上问题如果采用“状态模式”就能很好地得到解决。状态模式的解决思想是：当控制一个对象状态转换的条件表达式过于复杂时，把相关“判断逻辑”提取出来，用各个不同的类进行表示，系统处于哪种情况，直接使用相应的状态类对象进行处理，这样能把原来复杂的逻辑判断简单化，消除了 if-else、switch-case 等冗余语句，代码更有层次性，并且具备良好的扩展力。

## 状态模式的定义与特点

状态（State）模式的定义：对有状态的对象，把复杂的“判断逻辑”提取到不同的状态对象中，允许状态对象在其内部状态发生改变时改变其行为。

状态模式是一种对象行为型模式，其主要优点如下。

1. 结构清晰，状态模式将与特定状态相关的行为局部化到一个状态中，并且将不同状态的行为分割开来，满足“单一职责原则”。
2. 将状态转换显示化，减少对象间的相互依赖。将不同的状态引入独立的对象中会使得状态转换变得更加明确，且减少对象间的相互依赖。
3. 状态类职责明确，有利于程序的扩展。通过定义新的子类很容易地增加新的状态和转换。


状态模式的主要缺点如下。

1. 状态模式的使用必然会增加系统的类与对象的个数。
2. 状态模式的结构与实现都较为复杂，如果使用不当会导致程序结构和代码的混乱。
3. 状态模式对开闭原则的支持并不太好，对于可以切换状态的状态模式，增加新的状态类需要修改那些负责状态转换的源码，否则无法切换到新增状态，而且修改某个状态类的行为也需要修改对应类的源码。

## 状态模式的结构与实现

状态模式把受环境改变的对象行为包装在不同的状态对象里，其意图是让一个对象在其内部状态改变的时候，其行为也随之改变。现在我们来分析其基本结构和实现方法。

#### 1. 模式的结构

状态模式包含以下主要角色。

1. 环境类（Context）角色：也称为上下文，它定义了客户端需要的接口，内部维护一个当前状态，并负责具体状态的切换。
2. 抽象状态（State）角色：定义一个接口，用以封装环境对象中的特定状态所对应的行为，可以有一个或多个行为。
3. 具体状态（Concrete State）角色：实现抽象状态所对应的行为，并且在需要的情况下进行状态切换。


其结构图如图 1 所示。

![状态模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q11615412U55.gif)
图1 状态模式的结构图

#### 2. 模式的实现

状态模式的实现代码如下：

```
public class StatePatternClient {    public static void main(String[] args) {        Context context = new Context();    //创建环境              context.Handle();    //处理请求        context.Handle();        context.Handle();        context.Handle();    }}//环境类class Context {    private State state;    //定义环境类的初始状态    public Context() {        this.state = new ConcreteStateA();    }    //设置新状态    public void setState(State state) {        this.state = state;    }    //读取状态    public State getState() {        return (state);    }    //对请求做处理    public void Handle() {        state.Handle(this);    }}//抽象状态类abstract class State {    public abstract void Handle(Context context);}//具体状态A类class ConcreteStateA extends State {    public void Handle(Context context) {        System.out.println("当前状态是 A.");        context.setState(new ConcreteStateB());    }}//具体状态B类class ConcreteStateB extends State {    public void Handle(Context context) {        System.out.println("当前状态是 B.");        context.setState(new ConcreteStateA());    }}
```


程序运行结果如下：

```
当前状态是 A.
当前状态是 B.
当前状态是 A.
当前状态是 B.
```

## 状态模式的应用实例

【例1】用“状态模式”设计一个学生成绩的状态转换程序。

分析：本实例包含了“不及格”“中等”和“优秀” 3 种状态，当学生的分数小于 60 分时为“不及格”状态，当分数大于等于 60 分且小于 90 分时为“中等”状态，当分数大于等于 90 分时为“优秀”状态，我们用状态模式来实现这个程序。

首先，定义一个抽象状态类（AbstractState），其中包含了环境属性、状态名属性和当前分数属性，以及加减分方法 addScore(intx) 和检查当前状态的抽象方法 checkState()。

然后，定义“不及格”状态类 LowState、“中等”状态类 MiddleState 和“优秀”状态类 HighState，它们是具体状态类，实现 checkState() 方法，负责检査自己的状态，并根据情况转换。

最后，定义环境类（ScoreContext），其中包含了当前状态对象和加减分的方法 add(int score)，客户类通过该方法来改变成绩状态。图 2 所示是其结构图。



![学生成绩的状态转换程序的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q11615425V39.gif)
图2 学生成绩的状态转换程序的结构图


程序代码如下：

```
public class ScoreStateTest {    public static void main(String[] args) {        ScoreContext account = new ScoreContext();        System.out.println("学生成绩状态测试：");        account.add(30);        account.add(40);        account.add(25);        account.add(-15);        account.add(-25);    }}//环境类class ScoreContext {    private AbstractState state;    ScoreContext() {        state = new LowState(this);    }    public void setState(AbstractState state) {        this.state = state;    }    public AbstractState getState() {        return state;    }    public void add(int score) {        state.addScore(score);    }}//抽象状态类abstract class AbstractState {    protected ScoreContext hj;  //环境    protected String stateName; //状态名    protected int score; //分数    public abstract void checkState(); //检查当前状态    public void addScore(int x) {        score += x;        System.out.print("加上：" + x + "分，\t当前分数：" + score);        checkState();        System.out.println("分，\t当前状态：" + hj.getState().stateName);    }}//具体状态类：不及格class LowState extends AbstractState {    public LowState(ScoreContext h) {        hj = h;        stateName = "不及格";        score = 0;    }    public LowState(AbstractState state) {        hj = state.hj;        stateName = "不及格";        score = state.score;    }    public void checkState() {        if (score >= 90) {            hj.setState(new HighState(this));        } else if (score >= 60) {            hj.setState(new MiddleState(this));        }    }}//具体状态类：中等class MiddleState extends AbstractState {    public MiddleState(AbstractState state) {        hj = state.hj;        stateName = "中等";        score = state.score;    }    public void checkState() {        if (score < 60) {            hj.setState(new LowState(this));        } else if (score >= 90) {            hj.setState(new HighState(this));        }    }}//具体状态类：优秀class HighState extends AbstractState {    public HighState(AbstractState state) {        hj = state.hj;        stateName = "优秀";        score = state.score;    }    public void checkState() {        if (score < 60) {            hj.setState(new LowState(this));        } else if (score < 90) {            hj.setState(new MiddleState(this));        }    }}
```

程序运行结果如下：

```
学生成绩状态测试：
加上：30分，    当前分数：30分，    当前状态：不及格
加上：40分，    当前分数：70分，    当前状态：中等
加上：25分，    当前分数：95分，    当前状态：优秀
加上：-15分，    当前分数：80分，    当前状态：中等
加上：-25分，    当前分数：55分，    当前状态：不及格
```

【例2】用“状态模式”设计一个多线程的状态转换程序。

分析：多线程存在 5 种状态，分别为新建状态、就绪状态、运行状态、阻塞状态和死亡状态，各个状态当遇到相关方法调用或事件触发时会转换到其他状态，其状态转换规律如图 3 所示。



![线程状态转换图](http://c.biancheng.net/uploads/allimg/181116/3-1Q116154332451.gif)
图3 线程状态转换图


现在先定义一个抽象状态类（TheadState），然后为图 3 所示的每个状态设计一个具体状态类，它们是新建状态（New）、就绪状态（Runnable ）、运行状态（Running）、阻塞状态（Blocked）和死亡状态（Dead），每个状态中有触发它们转变状态的方法，环境类（ThreadContext）中先生成一个初始状态（New），并提供相关触发方法，图 4 所示是线程状态转换程序的结构图。



![线程状态转换程序的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q116154432352.gif)
图4 线程状态转换程序的结构图


程序代码如下：

```
public class ScoreStateTest {    public static void main(String[] args) {        ThreadContext context = new ThreadContext();        context.start();        context.getCPU();        context.suspend();        context.resume();        context.getCPU();        context.stop();    }}//环境类class ThreadContext {    private ThreadState state;    ThreadContext() {        state = new New();    }    public void setState(ThreadState state) {        this.state = state;    }    public ThreadState getState() {        return state;    }    public void start() {        ((New) state).start(this);    }    public void getCPU() {        ((Runnable) state).getCPU(this);    }    public void suspend() {        ((Running) state).suspend(this);    }    public void stop() {        ((Running) state).stop(this);    }    public void resume() {        ((Blocked) state).resume(this);    }}//抽象状态类：线程状态abstract class ThreadState {    protected String stateName; //状态名}//具体状态类：新建状态class New extends ThreadState {    public New() {        stateName = "新建状态";        System.out.println("当前线程处于：新建状态.");    }    public void start(ThreadContext hj) {        System.out.print("调用start()方法-->");        if (stateName.equals("新建状态")) {            hj.setState(new Runnable());        } else {            System.out.println("当前线程不是新建状态，不能调用start()方法.");        }    }}//具体状态类：就绪状态class Runnable extends ThreadState {    public Runnable() {        stateName = "就绪状态";        System.out.println("当前线程处于：就绪状态.");    }    public void getCPU(ThreadContext hj) {        System.out.print("获得CPU时间-->");        if (stateName.equals("就绪状态")) {            hj.setState(new Running());        } else {            System.out.println("当前线程不是就绪状态，不能获取CPU.");        }    }}//具体状态类：运行状态class Running extends ThreadState {    public Running() {        stateName = "运行状态";        System.out.println("当前线程处于：运行状态.");    }    public void suspend(ThreadContext hj) {        System.out.print("调用suspend()方法-->");        if (stateName.equals("运行状态")) {            hj.setState(new Blocked());        } else {            System.out.println("当前线程不是运行状态，不能调用suspend()方法.");        }    }    public void stop(ThreadContext hj) {        System.out.print("调用stop()方法-->");        if (stateName.equals("运行状态")) {            hj.setState(new Dead());        } else {            System.out.println("当前线程不是运行状态，不能调用stop()方法.");        }    }}//具体状态类：阻塞状态class Blocked extends ThreadState {    public Blocked() {        stateName = "阻塞状态";        System.out.println("当前线程处于：阻塞状态.");    }    public void resume(ThreadContext hj) {        System.out.print("调用resume()方法-->");        if (stateName.equals("阻塞状态")) {            hj.setState(new Runnable());        } else {            System.out.println("当前线程不是阻塞状态，不能调用resume()方法.");        }    }}//具体状态类：死亡状态class Dead extends ThreadState {    public Dead() {        stateName = "死亡状态";        System.out.println("当前线程处于：死亡状态.");    }}
```


程序运行结果如下：

```
当前线程处于：新建状态.
调用start()方法-->当前线程处于：就绪状态.
获得CPU时间-->当前线程处于：运行状态.
调用suspend()方法-->当前线程处于：阻塞状态.
调用resume()方法-->当前线程处于：就绪状态.
获得CPU时间-->当前线程处于：运行状态.
调用stop()方法-->当前线程处于：死亡状态.
```

## 状态模式的应用场景

通常在以下情况下可以考虑使用状态模式。

- 当一个对象的行为取决于它的状态，并且它必须在运行时根据状态改变它的行为时，就可以考虑使用状态模式。
- 一个操作中含有庞大的分支结构，并且这些分支决定于对象的状态时。

## 状态模式的扩展

在有些情况下，可能有多个环境对象需要共享一组状态，这时需要引入享元模式，将这些具体状态对象放在集合中供程序共享，其结构图如图 5 所示。



![共享状态模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q116154539147.gif)
图5 共享状态模式的结构图


分析：共享状态模式的不同之处是在环境类中增加了一个 HashMap 来保存相关状态，当需要某种状态时可以从中获取，其程序代码如下：

```
package state;import java.util.HashMap;public class FlyweightStatePattern {    public static void main(String[] args) {        ShareContext context = new ShareContext(); //创建环境              context.Handle(); //处理请求        context.Handle();        context.Handle();        context.Handle();    }}//环境类class ShareContext {    private ShareState state;    private HashMap<String, ShareState> stateSet = new HashMap<String, ShareState>();    public ShareContext() {        state = new ConcreteState1();        stateSet.put("1", state);        state = new ConcreteState2();        stateSet.put("2", state);        state = getState("1");    }    //设置新状态    public void setState(ShareState state) {        this.state = state;    }    //读取状态    public ShareState getState(String key) {        ShareState s = (ShareState) stateSet.get(key);        return s;    }    //对请求做处理    public void Handle() {        state.Handle(this);    }}//抽象状态类abstract class ShareState {    public abstract void Handle(ShareContext context);}//具体状态1类class ConcreteState1 extends ShareState {    public void Handle(ShareContext context) {        System.out.println("当前状态是： 状态1");        context.setState(context.getState("2"));    }}//具体状态2类class ConcreteState2 extends ShareState {    public void Handle(ShareContext context) {        System.out.println("当前状态是： 状态2");        context.setState(context.getState("1"));    }}
```

程序运行结果如下：

```
当前状态是： 状态1
当前状态是： 状态2
当前状态是： 状态1
当前状态是： 状态2
```

## 拓展

#### 状态模式与责任链模式的区别

状态模式和责任链模式都能消除 if-else 分支过多的问题。但在某些情况下，状态模式中的状态可以理解为责任，那么在这种情况下，两种模式都可以使用。

从定义来看，状态模式强调的是一个对象内在状态的改变，而责任链模式强调的是外部节点对象间的改变。

从代码实现上来看，两者最大的区别就是状态模式的各个状态对象知道自己要进入的下一个状态对象，而责任链模式并不清楚其下一个节点处理对象，因为链式组装由客户端负责。

#### 状态模式与策略模式的区别

状态模式和策略模式的 UML 类图架构几乎完全一样，但两者的应用场景是不一样的。策略模式的多种算法行为择其一都能满足，彼此之间是独立的，用户可自行更换策略算法，而状态模式的各个状态间存在相互关系，彼此之间在一定条件下存在自动切换状态的效果，并且用户无法指定状态，只能设置初始状态。