package dip

type Botton interface {
	TurnOn()
	TurnOff()
}

// UI struct UI 需要被注入(inject)一个 Botton interface 才能逻辑完整。所以，DIP 经常换一个名字出现，叫做依赖注入(Dependency Injection)
type UI struct {
	botton Botton
}

func NewUI(b Botton) *UI {
	return &UI{botton: b}
}

// 打开关闭灯
func (u *UI) Poll() {
	u.botton.TurnOn()
	u.botton.TurnOff()
	u.botton.TurnOn()
}
