package singleton

import (
	"log"
	"sync"
	"testing"
)

const parCount = 100

func TestSingleton(t *testing.T) {
	ins1 := GetInstance()
	ins2 := GetInstance()
	if ins1 != ins2 {
		t.Fatal("instance is not equal")
	}
}

//并发测试单例模式
func TestParallelSingleton(t *testing.T) {
	start := make(chan struct{})
	wg := sync.WaitGroup{}
	wg.Add(parCount)                    //预计使用100个协程并发,计数器为100
	instances := [parCount]*Singleton{} //初始化100个单例数组
	for i := 0; i < parCount; i++ {
		go func(index int) {
			//协程阻塞，等待channel被关闭才能继续运行
			<-start
			instances[index] = GetInstance()
			wg.Done() //计数-1
		}(i)
	}
	//关闭channel，所有协程同时开始运行，实现并行(parallel)
	close(start)
	wg.Wait() //阻塞等待, 直到WaitGroup,计数器为0, 即等待上面的100个协程执行完毕后
	//time.Sleep(time.Second * 3)
	for i := 1; i < parCount; i++ {
		log.Printf("i=%d", i)
		if instances[i] != instances[i-1] { //对比相邻的两个单例是否相等
			t.Fatal("instance is not equal")
		}
	}
	log.Printf("执行完毕")
}
