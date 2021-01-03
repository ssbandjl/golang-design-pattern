package proxy

import "testing"

func TestProxy(t *testing.T) {
	var sub Subject //抽象主题角色
	sub = &Proxy{}  //代理主题角色

	res := sub.Do()

	if res != "pre:real:after" {
		t.Fail()
	}
}
