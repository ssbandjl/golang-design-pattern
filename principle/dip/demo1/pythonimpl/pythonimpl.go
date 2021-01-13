package pythonimpl

import "fmt"

// Lamp 灯
type Lamp struct {
}

func NewLamp() *Lamp {
	return &Lamp{}
}

func (*Lamp) TurnOn() {
	fmt.Println("turn on python lamp")
}

func (*Lamp) TurnOff() {
	fmt.Println("turn off python lamp")
}
