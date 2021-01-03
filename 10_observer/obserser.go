package observer

import "fmt"

type Subject struct {
	observers []Observer //观察者
	context   string
}

func NewSubject() *Subject {
	return &Subject{
		observers: make([]Observer, 0),
	}
}

//主题将观察者加入自己的字段数组中
func (s *Subject) Attach(o Observer) {
	s.observers = append(s.observers, o)
}

func (s *Subject) notify() {
	for _, o := range s.observers {
		o.Update(s)
	}
}

//主题更新
func (s *Subject) UpdateContext(context string) {
	s.context = context
	s.notify()
}

type Observer interface {
	Update(*Subject)
}

//读者实现了观察者(接口)
type Reader struct {
	name string
}

func NewReader(name string) *Reader {
	return &Reader{
		name: name,
	}
}

func (r *Reader) Update(s *Subject) {
	fmt.Printf("%s receive %s\n", r.name, s.context)
}
