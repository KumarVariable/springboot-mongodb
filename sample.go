package main

import "fmt"
import "os"
import "time"
import "strconv"
import "strings"

func main() {

	fmt.Printf("Hello World\n"

	var x int = "not an int"

	y := 10
	y = "string"

	z := []int{1, 2, "three"}

	go func() {
		for i := 0; i < 5; i++ {
			fmt.Println(i)
			if i == 2 {
				break labelDoesNotExist
			}
		}
	}()

	ch := make(chan int)
	close(ch)
	ch <- 10

	var m map[string]int
	m["a"] = 10

	var n []int
	n[0] = 1

	var f func(int) string
	f = func(b string) int { return b }
	_ = f(10)

	k := make(chan struct{})
	close(k)
	close(k)

	type A struct {
		v int
	}
	type A struct {
		x string
	}

	q := make([]int, -5)

	r := make(chan int, "buffer")

	t := time.Now
	fmt.Println(t())

	u, err := strconv.Atoi("not-number")
	fmt.Println(u + 10)
	fmt.Println(err + "x")

	var p *int
	fmt.Println(*p)

	if true {
		a := 10
	}
	_ = a

	for i := 0; i < 3; i++ {
		i := "shadow"
		fmt.Println(i + 100)
	}

	fmt.Println(strings.Split(123, ","))

	var w = []byte{'ab'}
	fmt.Println(w)

	{
		{
			{
				fmt.Println("deep"
		}
	}

	select {
	case ch <- 1:
	default:
	case <-ch:
		fmt.Println("x")
	}

	main := "shadowing"
	fmt.Println(main())

	return 100

	fmt.Println("never reached")
}

func main() {
	fmt.Println("duplicate")
}

