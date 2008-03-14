package onlineLearning.realAtron.tracking;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jme.math.Vector2f;

public class ATRONTracker {
	BufferedImage background = null; 
	public void setBackgroundImage(BufferedImage bImage) {
		background = bImage;
		System.out.println("Background image set "+bImage);
	}
	public Blob findATRON(BufferedImage bImage) {
		  threasHold(bImage);
		  ArrayList<Blob> blobs = BlobDetector.detectBlobs(bImage);
		  int index = findATRONBlob(bImage,blobs);
		  if(index==-1) return null;
		  else return blobs.get(index);
	}
	public Vector2f findATRONPos(BufferedImage bImage) {
		Blob blob = findATRON(bImage);
		if(blob!=null) {
			Pixel p = blob.getCenterOfMassPixel();
			return new Vector2f(p.x,p.y);
		}
		else {
			return null;
		}
		
	}
	private void threasHold(BufferedImage bImage) {
		  for(int x=0;x<bImage.getWidth();x++) {
			  for(int y=0;y<bImage.getHeight();y++) {
				  //simpelThreshold(x,y,bImage);
				  if(background!=null) {
					  backgroundSubstraction(x, y,bImage);
					  //simpelThreshold(x,y,bImage);
				  }
				  else {
					  System.out.println("Background image not set!!!");
				  }
			}
		}
	}
	private void backgroundSubstraction(int x, int y,BufferedImage bImage) {
		int pixel = bImage.getRGB(x, y);
		int backPixel = background.getRGB(x, y);
		
		int red = Math.abs(getRed(pixel)-getRed(backPixel));
		int green = Math.abs(getGreen(pixel)-getGreen(backPixel));
		int blue = Math.abs(getBlue(pixel)-getBlue(backPixel));
		
		double brightness = Math.sqrt(red*red+green*green+blue*blue);
		if(brightness>50) {
			bImage.setRGB(x, y, 0);
		}
		else {
			bImage.setRGB(x, y, 0x0fffffff);
		}
	}
	private void simpelThreshold(int x, int y,BufferedImage bImage) {
		int pixel = bImage.getRGB(x, y);
		int red = getRed(pixel);
		int green = getGreen(pixel);
		int blue = getBlue(pixel);
		double brightness = Math.sqrt(red*red+green*green+blue*blue);
		if(brightness<200)bImage.setRGB(x, y, 0x0fffffff);
		else bImage.setRGB(x, y, 0);
	}
	private int pixelOf(int red, int green, int blue) {
		return (red<<16)+(green<<8)+(blue);
	}
	private int getBlue(int pixel) {
		return pixel & 0xff;
	}
	private int getRed(int pixel) {
		return (pixel >> 16) & 0xff;
	}
	private int getGreen(int pixel) {
		return (pixel >> 8) & 0xff;
	}
	private int findATRONBlob(BufferedImage bImage, ArrayList<Blob> blobs) {
		  int biggestBlobIndex = 0;
		  int biggestBlobSize = 0;
		  for(int i=0;i<blobs.size();i++) {
			  if(blobs.get(i).getPixelCount()>biggestBlobSize) {
				  biggestBlobIndex=i;
				  biggestBlobSize=blobs.get(i).getPixelCount();
			  }
		  }
		  if(biggestBlobSize<100||biggestBlobSize>1000)  {
			  //System.out.println("ATRON blob has strange size "+biggestBlobSize);
			  return -1;
		  }
		  Pixel cm = blobs.get(biggestBlobIndex).getCenterOfMassPixel();
		  if(cm.x<10||cm.x>=310||cm.y<10||cm.y>=210) return -1;
		  return biggestBlobIndex;
	  
	  }
}
