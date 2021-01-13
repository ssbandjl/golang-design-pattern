package javaimpl

import "fmt"

type Lamp struct {
}

func NewLamp() *Lamp {
	return &Lamp{}
}

func (*Lamp) TurnOn() {
	fmt.Println("turn on java lamp")
}

func (*Lamp) TurnOff() {
	fmt.Println("turn off java lamp")
}
