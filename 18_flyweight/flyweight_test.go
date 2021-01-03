package flyweight

import "testing"

func ExampleFlyweight() {
	viewer := NewImageViewer("image1.png")
	viewer.Display()
	// Output:
	// Display: image data image1.png
}

func TestFlyweight(t *testing.T) {
	viewer1 := NewImageViewer("image1.png")
	viewer2 := NewImageViewer("image1.png")

	if viewer1.ImageFlyweight != viewer2.ImageFlyweight {
		t.Fail()
	}
}
