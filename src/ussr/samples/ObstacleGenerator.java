/**
 * 
 */
package ussr.samples;

import ussr.robotbuildingblocks.BoxDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * Utility class for inserting obstacles into a WorldDescription object
 * 
 * @author ups
 */
public class ObstacleGenerator {
    public static enum ObstacleType { NONE, LINE, CIRCLE }

    private int numberOfCircleObstacles = 64;
    private int numberOfCircleLayers = 1;
    private float circleObstacleRadius = 0.5f;
    private float obstacleY = -0.4f;
    private float obstacleIncY = 0.15f;
    private float obstacleSize = 0.02f;
    private int numberOfLineObstacles = 20;
    private float lineObstacleDistance = 0.2f;
    private float lineObstacleCenter = 0;
    
    public void obstacalize(ObstacleType type, WorldDescription world) {
        if(type==ObstacleType.LINE) {
            System.out.println("Generating line obstacles");
            VectorDescription[] obstacles = new VectorDescription[numberOfLineObstacles];
            for(int i=0; i<numberOfLineObstacles; i++) {
                obstacles[i] = new VectorDescription(1.0f, obstacleIncY, lineObstacleCenter-(numberOfLineObstacles/2*lineObstacleDistance)+i*lineObstacleDistance);
            }
            world.setObstacles(obstacles);
        } else if(type==ObstacleType.CIRCLE) {
            System.out.println("Generating circle obstacles");
            BoxDescription[] obstacles = new BoxDescription[numberOfCircleObstacles*numberOfCircleLayers];
            int index = 0;
            float y = obstacleY;
            VectorDescription size = new VectorDescription(obstacleSize,obstacleSize,obstacleSize);
            for(int layers=0; layers<numberOfCircleLayers; layers++) {
                for(int i=0; i<numberOfCircleObstacles; i++) {
                    VectorDescription position = new VectorDescription(
                            ((float)(circleObstacleRadius*Math.cos(((double)i)/numberOfCircleObstacles*Math.PI*2+y))),
                            y,
                            ((float)(circleObstacleRadius*Math.sin(((double)i)/numberOfCircleObstacles*Math.PI*2+y)))
                    );
                    obstacles[index++] = new BoxDescription(position,size,new RotationDescription(),100f); 
                }
                y+=obstacleIncY;
            }
            world.setBigObstacles(obstacles);
        }
    }

    /**
     * @param numberOfCircleObstacles the numberOfCircleObstacles to set
     */
    public void setNumberOfCircleObstacles(int numberOfCircleObstacles) {
        this.numberOfCircleObstacles = numberOfCircleObstacles;
    }

    /**
     * @param numberOfCircleLayers the numberOfCircleLayers to set
     */
    public void setNumberOfCircleLayers(int numberOfCircleLayers) {
        this.numberOfCircleLayers = numberOfCircleLayers;
    }

    /**
     * @param circleObstacleRadius the circleObstacleRadius to set
     */
    public void setCircleObstacleRadius(float circleObstacleRadius) {
        this.circleObstacleRadius = circleObstacleRadius;
    }

    /**
     * @param obstacleY the obstacleY to set
     */
    public void setObstacleY(float obstacleY) {
        this.obstacleY = obstacleY;
    }

    /**
     * @param obstacleIncY the obstacleIncY to set
     */
    public void setObstacleIncY(float obstacleIncY) {
        this.obstacleIncY = obstacleIncY;
    }

    /**
     * @param obstacleSize the obstacleSize to set
     */
    public void setObstacleSize(float obstacleSize) {
        this.obstacleSize = obstacleSize;
    }

    /**
     * @param numberOfLineObstacles the numberOfLineObstacles to set
     */
    public void setNumberOfLineObstacles(int numberOfLineObstacles) {
        this.numberOfLineObstacles = numberOfLineObstacles;
    }

    /**
     * @param lineObstacleDistance the lineObstacleDistance to set
     */
    public void setLineObstacleDistance(float lineObstacleDistance) {
        this.lineObstacleDistance = lineObstacleDistance;
    }

    /**
     * @param lineObstacleCenter the lineObstacleCenter to set
     */
    public void setLineObstacleCenter(float lineObstacleCenter) {
        this.lineObstacleCenter = lineObstacleCenter;
    }

}
