
模版方法模式使用继承机制，把通用步骤和通用方法放到父类中，把具体实现延迟到子类中实现。使得实现符合开闭原则。

如实例代码中通用步骤在父类中实现（`准备`、`下载`、`保存`、`收尾`）下载和保存的具体实现留到子类中，并且提供 `保存`方法的默认实现。

因为Golang不提供继承机制，需要使用匿名组合模拟实现继承。

此处需要注意：因为父类需要调用子类方法，所以子类需要匿名组合父类的同时，父类需要持有子类的引用。



在面向对象程序设计过程中，程序员常常会遇到这种情况：设计一个系统时知道了算法所需的关键步骤，而且确定了这些步骤的执行顺序，但某些步骤的具体实现还未知，或者说某些步骤的实现与具体的环境相关。

例如，去银行办理业务一般要经过以下4个流程：取号、排队、办理具体业务、对银行工作人员进行评分等，其中取号、排队和对银行工作人员进行评分的业务对每个客户是一样的，可以在父类中实现，但是办理具体业务却因人而异，它可能是存款、取款或者转账等，可以延迟到子类中实现。

这样的例子在生活中还有很多，例如，一个人每天会起床、吃饭、做事、睡觉等，其中“做事”的内容每天可能不同。我们把这些规定了流程或格式的实例定义成模板，允许使用者根据自己的需求去更新它，例如，简历模板、论文模板、Word 中模板文件等。

以下介绍的模板方法模式将解决以上类似的问题。

## 模式的定义与特点

模板方法（Template Method）模式的定义如下：定义一个操作中的算法骨架，而将算法的一些步骤延迟到子类中，使得子类可以不改变该算法结构的情况下重定义该算法的某些特定步骤。它是一种类行为型模式。

该模式的主要优点如下。

1. 它封装了不变部分，扩展可变部分。它把认为是不变部分的算法封装到父类中实现，而把可变部分算法由子类继承实现，便于子类继续扩展。
2. 它在父类中提取了公共的部分代码，便于代码复用。
3. 部分方法是由子类实现的，因此子类可以通过扩展方式增加相应的功能，符合开闭原则。


该模式的主要缺点如下。

1. 对每个不同的实现都需要定义一个子类，这会导致类的个数增加，系统更加庞大，设计也更加抽象，间接地增加了系统实现的复杂度。
2. 父类中的抽象方法由子类实现，子类执行的结果会影响父类的结果，这导致一种反向的控制结构，它提高了代码阅读的难度。
3. 由于继承关系自身的缺点，如果父类添加新的抽象方法，则所有子类都要改一遍。

## 模式的结构与实现

模板方法模式需要注意抽象类与具体子类之间的协作。它用到了虚函数的多态性技术以及“不用调用我，让我来调用你”的反向控制技术。现在来介绍它们的基本结构。

### 1. 模式的结构

模板方法模式包含以下主要角色。

#### 1）抽象类/抽象模板（Abstract Class）

抽象模板类，负责给出一个算法的轮廓和骨架。它由一个模板方法和若干个基本方法构成。这些方法的定义如下。

① 模板方法：定义了算法的骨架，按某种顺序调用其包含的基本方法。

② 基本方法：是整个算法中的一个步骤，包含以下几种类型。

- 抽象方法：在抽象类中声明，由具体子类实现。
- 具体方法：在抽象类中已经实现，在具体子类中可以继承或重写它。
- 钩子方法：在抽象类中已经实现，包括用于判断的逻辑方法和需要子类重写的空方法两种。

#### 2）具体子类/具体实现（Concrete Class）

具体实现类，实现抽象类中所定义的抽象方法和钩子方法，它们是一个顶级逻辑的一个组成步骤。

模板方法模式的结构图如图 1 所示。



![模板方法模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q116095405308.gif)
图1 模板方法模式的结构图

### 2. 模式的实现

模板方法模式的代码如下：

```
public class TemplateMethodPattern {    public static void main(String[] args) {        AbstractClass tm = new ConcreteClass();        tm.TemplateMethod();    }}//抽象类abstract class AbstractClass {    //模板方法    public void TemplateMethod() {        SpecificMethod();        abstractMethod1();        abstractMethod2();    }    //具体方法    public void SpecificMethod() {        System.out.println("抽象类中的具体方法被调用...");    }    //抽象方法1    public abstract void abstractMethod1();    //抽象方法2    public abstract void abstractMethod2();}//具体子类class ConcreteClass extends AbstractClass {    public void abstractMethod1() {        System.out.println("抽象方法1的实现被调用...");    }    public void abstractMethod2() {        System.out.println("抽象方法2的实现被调用...");    }}
```

程序的运行结果如下：

```
抽象类中的具体方法被调用...
抽象方法1的实现被调用...
抽象方法2的实现被调用...
```

## 模式的应用实例

【例1】用模板方法模式实现出国留学手续设计程序。

分析：出国留学手续一般经过以下流程：索取学校资料，提出入学申请，办理因私出国护照、出境卡和公证，申请签证，体检、订机票、准备行装，抵达目标学校等，其中有些业务对各个学校是一样的，但有些业务因学校不同而不同，所以比较适合用模板方法模式来实现。

在本实例中，我们先定义一个出国留学的抽象类 StudyAbroad，里面包含了一个模板方法 TemplateMethod()，该方法中包含了办理出国留学手续流程中的各个基本方法，其中有些方法的处理由于各国都一样，所以在抽象类中就可以实现，但有些方法的处理各国是不同的，必须在其具体子类（如美国留学类 StudyInAmerica）中实现。如果再增加一个国家，只要增加一个子类就可以了，图 2 所示是其结构图。



![出国留学手续设计程序的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q11609544UV.gif)
图2 出国留学手续设计程序的结构图


程序代码如下：

```
public class StudyAbroadProcess {    public static void main(String[] args) {        StudyAbroad tm = new StudyInAmerica();        tm.TemplateMethod();    }}//抽象类: 出国留学abstract class StudyAbroad {    public void TemplateMethod() //模板方法    {        LookingForSchool(); //索取学校资料        ApplyForEnrol();    //入学申请        ApplyForPassport(); //办理因私出国护照、出境卡和公证        ApplyForVisa();     //申请签证        ReadyGoAbroad();    //体检、订机票、准备行装        Arriving();         //抵达    }    public void ApplyForPassport() {        System.out.println("三.办理因私出国护照、出境卡和公证：");        System.out.println("  1）持录取通知书、本人户口簿或身份证向户口所在地公安机关申请办理因私出国护照和出境卡。");        System.out.println("  2）办理出生公证书，学历、学位和成绩公证，经历证书，亲属关系公证，经济担保公证。");    }    public void ApplyForVisa() {        System.out.println("四.申请签证：");        System.out.println("  1）准备申请国外境签证所需的各种资料，包括个人学历、成绩单、工作经历的证明；个人及家庭收入、资金和财产证明；家庭成员的关系证明等；");        System.out.println("  2）向拟留学国家驻华使(领)馆申请入境签证。申请时需按要求填写有关表格，递交必需的证明材料，缴纳签证。有的国家(比如美国、英国、加拿大等)在申请签证时会要求申请人前往使(领)馆进行面试。");    }    public void ReadyGoAbroad() {        System.out.println("五.体检、订机票、准备行装：");        System.out.println("  1）进行身体检查、免疫检查和接种传染病疫苗；");        System.out.println("  2）确定机票时间、航班和转机地点。");    }    public abstract void LookingForSchool();//索取学校资料    public abstract void ApplyForEnrol();   //入学申请    public abstract void Arriving();        //抵达}//具体子类: 美国留学class StudyInAmerica extends StudyAbroad {    @Override    public void LookingForSchool() {        System.out.println("一.索取学校以下资料：");        System.out.println("  1）对留学意向国家的政治、经济、文化背景和教育体制、学术水平进行较为全面的了解；");        System.out.println("  2）全面了解和掌握国外学校的情况，包括历史、学费、学制、专业、师资配备、教学设施、学术地位、学生人数等；");        System.out.println("  3）了解该学校的住宿、交通、医疗保险情况如何；");        System.out.println("  4）该学校在中国是否有授权代理招生的留学中介公司？");        System.out.println("  5）掌握留学签证情况；");        System.out.println("  6）该国政府是否允许留学生合法打工？");        System.out.println("  8）毕业之后可否移民？");        System.out.println("  9）文凭是否受到我国认可？");    }    @Override    public void ApplyForEnrol() {        System.out.println("二.入学申请：");        System.out.println("  1）填写报名表；");        System.out.println("  2）将报名表、个人学历证明、最近的学习成绩单、推荐信、个人简历、托福或雅思语言考试成绩单等资料寄往所申请的学校；");        System.out.println("  3）为了给签证办理留有充裕的时间，建议越早申请越好，一般提前1年就比较从容。");    }    @Override    public void Arriving() {        System.out.println("六.抵达目标学校：");        System.out.println("  1）安排住宿；");        System.out.println("  2）了解校园及周边环境。");    }}
```

程序的运行结果如下：

```
一.索取学校以下资料：
  1）对留学意向国家的政治、经济、文化背景和教育体制、学术水平进行较为全面的了解；
  2）全面了解和掌握国外学校的情况，包括历史、学费、学制、专业、师资配备、教学设施、学术地位、学生人数等；
  3）了解该学校的住宿、交通、医疗保险情况如何；
  4）该学校在中国是否有授权代理招生的留学中介公司？
  5）掌握留学签证情况；
  6）该国政府是否允许留学生合法打工？
  8）毕业之后可否移民？
  9）文凭是否受到我国认可？
二.入学申请：
  1）填写报名表；
  2）将报名表、个人学历证明、最近的学习成绩单、推荐信、个人简历、托福或雅思语言考试成绩单等资料寄往所申请的学校；
  3）为了给签证办理留有充裕的时间，建议越早申请越好，一般提前1年就比较从容。
三.办理因私出国护照、出境卡和公证：
  1）持录取通知书、本人户口簿或身份证向户口所在地公安机关申请办理因私出国护照和出境卡。
  2）办理出生公证书，学历、学位和成绩公证，经历证书，亲属关系公证，经济担保公证。
四.申请签证：
  1）准备申请国外境签证所需的各种资料，包括个人学历、成绩单、工作经历的证明；个人及家庭收入、资金和财产证明；家庭成员的关系证明等；
  2）向拟留学国家驻华使(领)馆申请入境签证。申请时需按要求填写有关表格，递交必需的证明材料，缴纳签证。有的国家(比如美国、英国、加拿大等)在申请签证时会要求申请人前往使(领)馆进行面试。
五.体检、订机票、准备行装：
  1）进行身体检查、免疫检查和接种传染病疫苗；
  2）确定机票时间、航班和转机地点。
六.抵达目标学校：
  1）安排住宿；
  2）了解校园及周边环境。
```

## 模式的应用场景

模板方法模式通常适用于以下场景。

1. 算法的整体步骤很固定，但其中个别部分易变时，这时候可以使用模板方法模式，将容易变的部分抽象出来，供子类实现。
2. 当多个子类存在公共的行为时，可以将其提取出来并集中到一个公共父类中以避免代码重复。首先，要识别现有代码中的不同之处，并且将不同之处分离为新的操作。最后，用一个调用这些新的操作的模板方法来替换这些不同的代码。
3. 当需要控制子类的扩展时，模板方法只在特定点调用钩子操作，这样就只允许在这些点进行扩展。

## 模式的扩展

在模板方法模式中，基本方法包含：抽象方法、具体方法和钩子方法，正确使用“钩子方法”可以使得子类控制父类的行为。如下面例子中，可以通过在具体子类中重写钩子方法 HookMethod1() 和 HookMethod2() 来改变抽象父类中的运行结果，其结构图如图 3 所示。



![含钩子方法的模板方法模式的结构图](http://c.biancheng.net/uploads/allimg/181116/3-1Q116095550123.gif)
图3 含钩子方法的模板方法模式的结构图


程序代码如下：

```
public class HookTemplateMethod {    public static void main(String[] args) {        HookAbstractClass tm = new HookConcreteClass();        tm.TemplateMethod();    }}//含钩子方法的抽象类abstract class HookAbstractClass {    //模板方法    public void TemplateMethod() {        abstractMethod1();        HookMethod1();        if (HookMethod2()) {            SpecificMethod();        }        abstractMethod2();    }    //具体方法    public void SpecificMethod() {        System.out.println("抽象类中的具体方法被调用...");    }    //钩子方法1    public void HookMethod1() {    }    //钩子方法2    public boolean HookMethod2() {        return true;    }    //抽象方法1    public abstract void abstractMethod1();    //抽象方法2    public abstract void abstractMethod2();}//含钩子方法的具体子类class HookConcreteClass extends HookAbstractClass {    public void abstractMethod1() {        System.out.println("抽象方法1的实现被调用...");    }    public void abstractMethod2() {        System.out.println("抽象方法2的实现被调用...");    }    public void HookMethod1() {        System.out.println("钩子方法1被重写...");    }    public boolean HookMethod2() {        return false;    }}
```

程序的运行结果如下：

```
抽象方法1的实现被调用...
钩子方法1被重写...
抽象方法2的实现被调用...
```

如果钩子方法 HookMethod1() 和钩子方法 HookMethod2() 的代码改变，则程序的运行结果也会改变。