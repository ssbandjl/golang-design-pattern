package mediator

import "testing"

func TestMediator(t *testing.T) {
	mediator := GetMediatorInstance()
	mediator.CD = &CDDriver{}
	mediator.CPU = &CPU{}
	mediator.Video = &VideoCard{}
	mediator.Sound = &SoundCard{}

	//Tiggle
	mediator.CD.ReadData()

	if mediator.CD.Data != "music,image" {
		t.Fatalf("CD unexpect data %s", mediator.CD.Data)
	}

	if mediator.CPU.Sound != "music" {
		t.Fatalf("CPU unexpect sound data %s", mediator.CPU.Sound)
	}

	if mediator.CPU.Video != "image" {
		t.Fatalf("CPU unexpect video data %s", mediator.CPU.Video)
	}

	if mediator.Video.Data != "image" {
		t.Fatalf("VidoeCard unexpect data %s", mediator.Video.Data)
	}

	if mediator.Sound.Data != "music" {
		t.Fatalf("SoundCard unexpect data %s", mediator.Sound.Data)
	}
}
