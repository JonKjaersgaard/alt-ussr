package onlineLearning.realAtron;

import java.util.ArrayList;

public class Blob {
	ArrayList<Pixel> pixels;
	public Blob() {
		pixels = new ArrayList<Pixel>(); 
	}
	public void addPixel(int x, int y, int rgbColor) {
		pixels.add(new Pixel(x,y,rgbColor));
	}
	public int getPixelCount() {
		return pixels.size();
	}
	public Pixel getCenterOfMassPixel() {
		int x = 0;
		int y = 0;
		for(Pixel p: pixels) {
			x+=p.x;
			y+=p.y;
		}
		return new Pixel(x/getPixelCount(), y/getPixelCount(),0);
	}
}
