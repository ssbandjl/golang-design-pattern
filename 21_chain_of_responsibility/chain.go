package chain

import "fmt"

type Manager interface {
	HaveRight(money int) bool
	HandleFeeRequest(name string, money int) bool
}

type RequestChain struct {
	Manager
	successor *RequestChain
}

func (r *RequestChain) SetSuccessor(m *RequestChain) {
	r.successor = m
}

func (r *RequestChain) HandleFeeRequest(name string, money int) bool {
	if r.Manager.HaveRight(money) {
		return r.Manager.HandleFeeRequest(name, money)
	}
	if r.successor != nil {
		return r.successor.HandleFeeRequest(name, money)
	}
	return false
}

func (r *RequestChain) HaveRight(money int) bool {
	return true
}

type ProjectManager struct{}

func NewProjectManagerChain() *RequestChain {
	return &RequestChain{
		Manager: &ProjectManager{},
	}
}

func (*ProjectManager) HaveRight(money int) bool {
	return money < 500
}

func (*ProjectManager) HandleFeeRequest(name string, money int) bool {
	if name == "bob" {
		fmt.Printf("Project manager permit %s %d fee request\n", name, money)
		return true
	}
	fmt.Printf("Project manager don't permit %s %d fee request\n", name, money)
	return false
}

type DepManager struct{}

func NewDepManagerChain() *RequestChain {
	return &RequestChain{
		Manager: &DepManager{},
	}
}

func (*DepManager) HaveRight(money int) bool {
	return money < 5000
}

func (*DepManager) HandleFeeRequest(name string, money int) bool {
	if name == "tom" {
		fmt.Printf("Dep manager permit %s %d fee request\n", name, money)
		return true
	}
	fmt.Printf("Dep manager don't permit %s %d fee request\n", name, money)
	return false
}

type GeneralManager struct{}

func NewGeneralManagerChain() *RequestChain {
	return &RequestChain{
		Manager: &GeneralManager{},
	}
}

func (*GeneralManager) HaveRight(money int) bool {
	return true
}

func (*GeneralManager) HandleFeeRequest(name string, money int) bool {
	if name == "ada" {
		fmt.Printf("General manager permit %s %d fee request\n", name, money)
		return true
	}
	fmt.Printf("General manager don't permit %s %d fee request\n", name, money)
	return false
}
