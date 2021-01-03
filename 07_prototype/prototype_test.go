package prototype

import (
	"log"
	"testing"
)

var manager *PrototypeManager

type Type1 struct {
	name string
}

func (t *Type1) Clone() Cloneable {
	tc := *t //创建新变量, 将t的指针指向的数据赋值给该对象, 两个对象数据独立
	//log.Printf("t2的地址:%+v", &tc)
	return &tc
}

type Type2 struct {
	name string
}

func (t *Type2) Clone() Cloneable {
	tc := *t
	return &tc
}

func TestClone(t *testing.T) {
	t1 := manager.Get("t1")

	t2 := t1.Clone()

	log.Printf("t1内存地址:%+v", &t1)
	log.Printf("t2内存地址:%+v", &t2)
	if t1 == t2 { //t1与t1克隆出来的t2不是同一个对象
		t.Fatal("error! get clone not working")
	}
	//a := t1.(*Type1)
	//a.name="change"
	//b := t2.(*Type1)
	//log.Printf("a:%+v", *a)
	//log.Printf("b:%+v", *b)
}

func TestCloneFromManager(t *testing.T) {
	c := manager.Get("t1").Clone()

	t1 := c.(*Type1)
	if t1.name != "type1" { //t1与t1克隆出来的对象类型一致, 内容一致, 但是内存地址不同
		t.Fatal("error")
	}
}

func init() {
	manager = NewPrototypeManager()

	t1 := &Type1{
		name: "type1",
	}
	//log.Printf("t1的地址:%+v", *t1)
	manager.Set("t1", t1)
}
