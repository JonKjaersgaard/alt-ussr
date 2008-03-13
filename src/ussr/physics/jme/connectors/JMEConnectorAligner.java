package ussr.physics.jme.connectors;

import java.awt.Color;
import java.util.ArrayList;

import ussr.description.geometry.BoxShape;
import ussr.description.geometry.CylinderShape;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.geometry.VectorDescription;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;

public class JMEConnectorAligner {
	ArrayList<AlignmentPoint> aPoints;
	JMEConnector connector;
	JMESimulation world;
	JMEModuleComponent component;
	public JMEConnectorAligner(JMEConnector connector, JMESimulation world, JMEModuleComponent component) {
		aPoints = new ArrayList<AlignmentPoint>();
		this.connector = connector;
		this.world = world;
		this.component = component;
	}
	public void addAlignmentPoint(Vector3f pos, int type, int sex, float maxForce, float maxDist, float epsilonDist) {
		AlignmentPoint aPoint = new AlignmentPoint(pos, type, sex, maxForce, maxDist, epsilonDist, connector.getNode(),this); 
		aPoints.add(aPoint);
		//addGeometry(pos, type, sex);
	}
	private void addGeometry(Vector3f pos, int type, int sex) {
		GeometryDescription[] shapes = new GeometryDescription[]{new SphereShape(0.001f), new BoxShape(0.001f),new CylinderShape(0.001f,0.001f)};
		Color[] colors = new Color[]{Color.green, Color.red, Color.blue};
		TriMesh mesh = JMEGeometryHelper.createShape(connector.getNode(), "Attraction Point Mesh", shapes[type]);
        mesh.getLocalTranslation().set( mesh.getLocalTranslation().add(new Vector3f(pos)) );
        connector.getNode().attachChild( mesh );
        world.associateGeometry(connector.getNode(), mesh);
        world.getHelper().setColor(mesh, colors[sex]);
	}
	public ArrayList<AlignmentPoint> getAlignmentPoints() {
		return aPoints;
	}
	public boolean canAlign(JMEConnector otherconnector) {
		JMEConnectorAligner aligner = otherconnector.getConnectorAligner();
		for(AlignmentPoint p1 : aligner.getAlignmentPoints()) {
			AlignmentPoint p2 = getNearestCompatible(p1);
			if(p2==null) return false;
			if(!p1.canAlign(p2)) return false;
		}
		return true;
	}
	public boolean isAligned(JMEConnector otherconnector) {
		JMEConnectorAligner aligner = otherconnector.getConnectorAligner();
		for(AlignmentPoint p1 : aligner.getAlignmentPoints()) {
			AlignmentPoint p2 = getNearestCompatible(p1);
			if(p2==null) return false;
			if(!p1.isAligned(p2)) return false;
		}
		return true;
	}
	public void align(JMEConnector otherconnector) {
		//System.out.println(connector.getPos().distance(c.getPos())+", ");
		alignWithForce(otherconnector);
		//alignWithMagic(otherconnector);
		//System.out.println("Distance after = "+connector.getPos().distance(c.getPos()));
	}
	private void alignWithForce(JMEConnector c) {
		JMEConnectorAligner aligner = c.getConnectorAligner();
		for(AlignmentPoint p1 : aligner.getAlignmentPoints()) {
			AlignmentPoint p2 = getNearestCompatible(p1);
			//System.out.println(p1.distance(p2)+" Aligning "+p1+" toward "+p2);
			Vector3f direction = p2.getPos().subtract(p1.getPos()).normalize();
			connector.getNode().addForce(direction.mult(-p2.getMaxForce()), p2.getPosRel());
			c.getNode().addForce(direction.mult(p1.getMaxForce()), p1.getPosRel());
			
			//connector.getNode().addForce(direction.mult(-p2.getMaxForce()));
			//c.getNode().addForce(direction.mult(p1.getMaxForce()));
		}
	}
	
	private void alignWithMagic(JMEConnector c) {
		JMEConnectorAligner aligner = c.getConnectorAligner();
		boolean first = true;
		AlignmentPoint ref = null;
		for(AlignmentPoint p1 : aligner.getAlignmentPoints()) {
			AlignmentPoint p2 = getNearestCompatible(p1);
			if(p2!=null) {
				if(first) {
					alignMove(p1,p2);
					ref = p1;
					first=false;
				}
				else {
					float bf = p1.distance(p2);
					//alignRotate(p1,p2,ref);
					alignMove(p1,p2);
					float af = p1.distance(p2);
					//System.out.println("Improvement = "+(bf-af)+" ground trouth "+af);
				}
			}
		}
	}
	
	private void alignMove(AlignmentPoint p1, AlignmentPoint p2) {
		Vector3f offset = p2.getPos().subtract(p1.getPos()).mult(0.5f);
		//Vector3f newPosP1 = connector.getNode().getLocalTranslation().subtract(offset);
		move(p1,offset);
		move(p2,offset.mult(-1));
	}
	private void move(AlignmentPoint p, Vector3f offset) {
		Vector3f newPos = p.getAligner().connector.getNode().getLocalTranslation().add(offset);
		p.getAligner().component.setPosition(new VectorDescription(newPos.x,newPos.y,newPos.z));
	}
	private void alignRotate(AlignmentPoint p1, AlignmentPoint p2, AlignmentPoint ref) {
		Vector3f p1ref = ref.getPos().subtract(p1.getPos()).normalize();
		Vector3f p2ref = ref.getPos().subtract(p2.getPos()).normalize();
		float angle = -p2ref.angleBetween(p1ref); 
		Vector3f normal = p1ref.cross(p2ref).normalize();
		Quaternion q = new Quaternion().fromAngleAxis(angle, normal);
		Quaternion newRot = connector.getNode().getLocalRotation().mult(q);
		//System.out.println("Before rot aligbment "+p1.getPos().distance(p2.getPos()));
		component.setRotation(new RotationDescription(newRot));
		//System.out.println("after rot aligbment "+p1.getPos().distance(p2.getPos()));
		alignMove(p1,p2);
		//System.out.println("After final aligbment "+p1.getPos().distance(p2.getPos()));
	}
	private AlignmentPoint getNearestCompatible(AlignmentPoint p1) {
		float minDistance = Float.MAX_VALUE;
		AlignmentPoint nearest = null;
		for(AlignmentPoint p2 : aPoints) {
			if(p2.compatible(p1)) {
				if(p2.distance(p1)<minDistance){
					minDistance = p2.distance(p1);
					nearest = p2;
				}
			}
		}
		return nearest;
	}
	private class AlignmentPoint {
		int type, sex;
		float maxForce;
		float maxDist;
		float epsilonDist;
		Vector3f position;
		DynamicPhysicsNode moduleNode;
		JMEConnectorAligner aligner;
		public AlignmentPoint(Vector3f position, int type, int sex, float maxForce, float maxDist, float epsilonDist, DynamicPhysicsNode moduleNode, JMEConnectorAligner aligner) {
			this.position = position;
			this.type = type;
			this.sex = sex;
			this.maxForce = maxForce;
			this.maxDist = maxDist;
			this.epsilonDist = epsilonDist;
			this.moduleNode = moduleNode;
			this.aligner = aligner;
		}
		
		public boolean isAligned(AlignmentPoint aPoint) {
			float dist = distance(aPoint);
			if(dist<epsilonDist&&dist<aPoint.getEpsilonDist()) return true;
			return false; 
		}
		public float distance(AlignmentPoint aPoint) {
			return getPos().distance(aPoint.getPos());
		}
		public boolean compatible(AlignmentPoint aPoint) {
			boolean sameType = aPoint.getType()==type;
			boolean compatibleSex = aPoint.getSex()!=sex || (sex==0&& aPoint.getSex() ==0);  
			return  sameType && compatibleSex;   
		}
		public boolean canAlign(AlignmentPoint aPoint) {
			float dist = distance(aPoint);
			if(dist<maxDist&&dist<aPoint.getMaxDist()) return true;
			return false;   
		}
		public Vector3f getPos() {
			return moduleNode.getLocalRotation().mult(position).add(moduleNode.getLocalTranslation());
		}
		public Vector3f getPosRel() {return position;}
		public int getType() {return type;}
		public int getSex() {return sex;}
		public JMEConnectorAligner getAligner() {return aligner;}
		public float getMaxForce() {return maxForce;}
		public float getMaxDist() {return maxDist;}
		public float getEpsilonDist() {return epsilonDist;}
		public String toString() { return "AP("+getPos().x+", "+getPos().y+", "+getPos().z+")";}
	}
}
