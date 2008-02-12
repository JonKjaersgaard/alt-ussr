package onlineLearning.realAtron.tracking;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jme.math.Vector2f;

public class ATRONTracker {
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
				  int pixel = bImage.getRGB(x, y);
				  int red = (pixel >> 16) & 0xff;
				  int green = (pixel >> 8) & 0xff;
				  int blue = pixel & 0xff;
				  double brightness = Math.sqrt(red*red+green*green+blue*blue);
				  //if(blue<180) bImage.setRGB(x, y, 0x0fffffff);
				  //System.out.println(brightness);
				  if(brightness<200)bImage.setRGB(x, y, 0x0fffffff);
				  else {
					  //System.out.println(brightness);
					  bImage.setRGB(x, y, 0);
				  }
			}
		}
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
