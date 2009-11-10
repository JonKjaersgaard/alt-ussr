package ussr.physics.jme;

import java.io.IOException;

import com.jme.bounding.BoundingVolume;
import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;

public class DummyCamera implements Camera {

    @Override
    public void apply() {
    }

    @Override
    public FrustumIntersect contains(BoundingVolume bound) {
        return null;
    }

    @Override
    public Vector3f getDirection() {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumBottom() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumFar() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumLeft() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumNear() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumRight() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getFrustumTop() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getLeft() {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getLocation() {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public int getPlaneState() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getScreenCoordinates(Vector3f worldPosition) {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getScreenCoordinates(Vector3f worldPosition, Vector3f store) {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getUp() {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getViewPortBottom() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getViewPortLeft() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getViewPortRight() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public float getViewPortTop() {
        // TODO Auto-generated method stub
        return 0;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getWorldCoordinates(Vector2f screenPosition, float zPos) {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public Vector3f getWorldCoordinates(Vector2f screenPosition, float zPos,
            Vector3f store) {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public boolean isParallelProjection() {
        // TODO Auto-generated method stub
        return false;
        //throw new Error("Method not implemented");
    }

    @Override
    public void lookAt(Vector3f pos, Vector3f worldUpVector) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void normalize() {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void onFrameChange() {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void onFrustumChange() {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void onViewPortChange() {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setAxes(Quaternion axes) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setAxes(Vector3f left, Vector3f up, Vector3f direction) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setDirection(Vector3f direction) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrame(Vector3f location, Quaternion axes) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrame(Vector3f location, Vector3f left, Vector3f up,
            Vector3f direction) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustum(float near, float far, float left, float right,
            float top, float bottom) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumBottom(float frustumBottom) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumFar(float frustumFar) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumLeft(float frustumLeft) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumNear(float frustumNear) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumPerspective(float fovY, float aspect, float near,
            float far) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumRight(float frustumRight) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setFrustumTop(float frustumTop) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setLeft(Vector3f left) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setLocation(Vector3f location) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setParallelProjection(boolean value) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setPlaneState(int planeState) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setUp(Vector3f up) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setViewPort(float left, float right, float bottom, float top) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setViewPortBottom(float bottom) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setViewPortLeft(float left) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setViewPortRight(float right) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void setViewPortTop(float top) {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public Class getClassTag() {
        // TODO Auto-generated method stub
        return null;
        //throw new Error("Method not implemented");
    }

    @Override
    public void read(JMEImporter im) throws IOException {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

    @Override
    public void write(JMEExporter ex) throws IOException {
        // TODO Auto-generated method stub
        // 
        //throw new Error("Method not implemented");
    }

}
