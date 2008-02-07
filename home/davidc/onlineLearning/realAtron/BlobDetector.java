package onlineLearning.realAtron;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author m3rlinezatgmaildotcom
 */
public class BlobDetector {
	
	private static boolean isBlack(BufferedImage image,int posX,int posY){
	    int color = image.getRGB(posX,posY);
	    int brightness =
	            (color & 0xFF) +
	            ((color >> 2) & 0xFF) +
	            ((color >> 4) & 0xFF);
	    brightness /= 3;
	    return brightness < 128;
	}

	public static ArrayList<Blob> detectBlobs(BufferedImage bimg) {
        boolean[][] painted = new boolean[bimg.getHeight()][bimg.getWidth()];
        ArrayList<Blob> blobs = new ArrayList<Blob>();
        Blob currentBlob = new Blob();
        for(int i = 0 ; i < bimg.getHeight() ; i++){
            for(int j = 0 ; j < bimg.getWidth() ; j++) {
                if(isBlack(bimg,j,i) && !painted[i][j]){
                
                    Queue<Point> queue = new LinkedList<Point>();
                    queue.add(new Point(j,i));
                
                    int pixelCount = 0;
                    while(!queue.isEmpty()){
                        Point p = queue.remove();
                    
                        if((p.x >= 0) && (p.x < bimg.getWidth() && (p.y >= 0) && (p.y < bimg.getHeight()))){
                            if(!painted[p.y][p.x] && isBlack(bimg,p.x,p.y)){
                                painted[p.y][p.x] = true;
                                currentBlob.addPixel(p.x, p.y, bimg.getRGB(p.x,p.y));
                                pixelCount++;
                                queue.add(new Point(p.x + 1,p.y)); queue.add(new Point(p.x - 1,p.y));
                                queue.add(new Point(p.x,p.y + 1)); queue.add(new Point(p.x,p.y - 1));
                            }
                        }
                    }
                   // System.out.println("Blob detected : " + currentBlob.getPixelCount() + " pixels");
                    blobs.add(currentBlob);
                    currentBlob = new Blob(); 
                }
            }
        }
        return blobs;
	}
}