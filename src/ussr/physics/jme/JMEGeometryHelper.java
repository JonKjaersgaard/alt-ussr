/**
 * 
 */
package ussr.physics.jme;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ussr.comm.IRReceiver;
import ussr.comm.IRTransmitter;
import ussr.comm.RadioReceiver;
import ussr.comm.RadioTransmitter;
import ussr.comm.Receiver;
import ussr.comm.TransmissionType;
import ussr.comm.Transmitter;
import ussr.comm.WiredReceiver;
import ussr.comm.WiredTransmitter;
import ussr.description.AtronShape;
import ussr.description.BoxShape;
import ussr.description.ConeShape;
import ussr.description.CylinderShape;
import ussr.description.GeometryDescription;
import ussr.description.ReceivingDevice;
import ussr.description.RotationDescription;
import ussr.description.SphereShape;
import ussr.description.TransmissionDevice;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.description.WorldDescription.TextureDescription;
import ussr.model.Connector;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsQuaternionHolder;
import ussr.physics.PhysicsSimulationHelper;
import ussr.physics.PhysicsVectorHolder;
import ussr.physics.jme.connectors.JMEMechanicalConnector;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.image.Texture;
import com.jme.math.Quaternion;
import com.jme.math.Triangle;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Cone;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.geom.BufferUtils;
import com.jmex.model.XMLparser.Converters.MaxToJme;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.material.Material;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.MidPointHeightMap;
import com.jmex.terrain.util.ProceduralTextureGenerator;

/**
 * Helper class used by various JME classes to perform geometry-related operations.
 * 
 * @author Modular Robots @ MMMI
 */
public class JMEGeometryHelper implements PhysicsSimulationHelper {

    /**
     * The ATRON CAD model
     */
    private static TriMesh atronModel;
    private JMESimulation simulation;
    private int obstacleCounter = 0;
    public JMEGeometryHelper(JMESimulation simulation) {
        this.simulation = simulation;
    }

    public static TriMesh createShape(DynamicPhysicsNode moduleNode, String name, GeometryDescription element) {
    	TriMesh shape = null;
    	int resolutionfactor = PhysicsParameters.get().getResolutionFactor();
        if(element instanceof SphereShape) {
            try {
                shape = new Sphere( name, resolutionfactor*9, resolutionfactor*9, ((SphereShape)element).getRadius());
            } catch(OutOfMemoryError error) {
                throw new Error("Out of memory while creating sphere, adjust resolution factor in PhysicsParameters");
            }
        	shape.setModelBound( new BoundingSphere() );
        }
        else if(element instanceof AtronShape) {
        	AtronShape half = (AtronShape) element;
        	shape = constructAtronModel(name, half);
        	shape.setModelBound(new BoundingSphere());
    		shape.getBatch(0).setIsCollidable(true);
    		shape.setIsCollidable(true);
    		
        	//shape.setIsCollidable(false);      	
        //	shape = moduleNode.createBox(name);
        	/*shape = new Sphere( name, 9, 9, 0.025f); 
        	if(((AtronShape) element).isNorth()) shape.setLocalTranslation(new Vector3f(0.0f,0,0.1f));
        	shape.setModelBound( new BoundingSphere() );*/
        }
        else if(element instanceof ConeShape) {
        	shape = new Cone(name, resolutionfactor*8, resolutionfactor*8, ((ConeShape)element).getRadius(),((ConeShape)element).getHeight(),true);
        	shape.setModelBound( new BoundingSphere() );
        }
        else if(element instanceof CylinderShape) {
        	shape = new Cylinder(name, resolutionfactor*8, resolutionfactor*8, ((CylinderShape)element).getRadius(),((CylinderShape)element).getHeight(),true); 
        	shape.setModelBound(new BoundingBox()); //BoundingBox makes the simulation "unstable?"
        	//TODO can not control cap color of cylinder??? 
        }
        else if(element instanceof BoxShape) {
        	BoxShape box = (BoxShape) element;
        	shape = new Box(name,new Vector3f(),box.getExtend().getX(),box.getExtend().getY(),box.getExtend().getZ());//name, 8, 8, ((CylinderShape)element).getRadius(),((CylinderShape)element).getHeight(),true); 
        	shape.setModelBound(new BoundingBox()); //BoundingBox makes the simulation "unstable?"
        }
        else throw new Error("Only sphere and atron geometries supported for now");
        
        VectorDescription translation = element.getTranslation();
    	shape.getLocalTranslation().set( translation.getX(), translation.getY(), translation.getZ() );
    	
    	RotationDescription rotation = element.getRotation();
    	//shape.getLocalRotation().set(rotation.getRotation());
    	shape.setLocalRotation(new Quaternion(rotation.getRotation()));
        shape.updateModelBound();
    	moduleNode.attachChild(shape);
        return shape;
    }
    
    public static Transmitter createTransmitter(Module module, Entity hardware, TransmissionDevice transmitter) {
    	if(transmitter.getType()==TransmissionType.RADIO) {
    		return new RadioTransmitter(module,hardware,transmitter.getType(),transmitter.getRange());
    	}
    	else if(transmitter.getType()==TransmissionType.IR) {
    		return new IRTransmitter(module,hardware,transmitter.getType(),transmitter.getRange());
    	}
    	else if(transmitter.getType()==TransmissionType.WIRE_UNISEX||transmitter.getType()==TransmissionType.WIRE_MALE||transmitter.getType()==TransmissionType.WIRE_FEMALE) {
    		return new WiredTransmitter(module,hardware,transmitter.getType(),transmitter.getRange());
    	}
    	else {
    		throw new RuntimeException("Transmission device type not recognized "+transmitter.getType()); 
    	}
        //return new GenericTransmitter(module, transmitter.getType(),transmitter.getRange());
    }

    public static Receiver createReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
    	if(receiver.getType()==TransmissionType.RADIO) {
    		return new RadioReceiver(module,hardware,receiver);
    	}
    	else if(receiver.getType()==TransmissionType.IR) {
    		return new IRReceiver(module,hardware,receiver);
    	}
    	else if(receiver.getType()==TransmissionType.WIRE_UNISEX||receiver.getType()==TransmissionType.WIRE_MALE||receiver.getType()==TransmissionType.WIRE_FEMALE) {
    		return new WiredReceiver(module,hardware,receiver);
    	}
    	else {
    		throw new RuntimeException("Receiver device type not recognized "+receiver.getType()); 
    	}
    }
    
    public void setColor(Object object, Color color) {
        this.setColor((SceneElement)object, color);
    }

    public void setColor(SceneElement object, Color color) {
        if(color==null) return;
        object.setRenderState(simulation.color2jme(color));
        
        object.updateRenderState();
    }
    public Color getColor(Object element) {
        ColorRGBA color = ((MaterialState)((SceneElement)element).getRenderState(RenderState.RS_MATERIAL)).getDiffuse();
        return new Color(color.r,color.g,color.b);
    }

    public static Spatial cloneElement(TriMesh element) {
        if(!(element instanceof Sphere)) throw new Error("not supported");
        Sphere oldSphere = (Sphere)element;
        Sphere meshSphere = new Sphere( oldSphere.getName(), 9, 9, oldSphere.radius);
        meshSphere.setLocalTranslation(oldSphere.getLocalTranslation());
        meshSphere.setLocalRotation(oldSphere.getLocalRotation());
        RenderState state = oldSphere.getRenderState(RenderState.RS_MATERIAL);
        if(state!=null) meshSphere.setRenderState(state);
        meshSphere.setModelBound( new BoundingSphere() );
        meshSphere.updateModelBound();
       return meshSphere;
    }
	
	private synchronized static TriMesh constructAtronModel(String name, AtronShape half) {
		if(atronModel==null) loadAtronModel(half.getRadius());
		//SharedMesh atronMesh = new SharedMesh(name,atronModel,true);
		TriMesh atronMesh = new TriMesh(atronModel.getName(),atronModel.getVertexBuffer(0),atronModel.getNormalBuffer(0),atronModel.getColorBuffer(0),atronModel.getTextureBuffer(0,0),atronModel.getIndexBuffer(0));
		atronMesh.setLocalScale(half.getRadius()*atronModel.getLocalScale().x);
		atronMesh.setName("ATRON Mesh");
		atronMesh.setIsCollidable(true);
		//atronMesh.getBatch(0).setIsCollidable(true);
		
        // UPS: hack to make conveyor belt work
        if(name.startsWith("--")) atronMesh.setLocalScale(0.95f*atronMesh.getLocalScale().x);
		return atronMesh;
	}
    public static void loadAtronModel(float radius) {
		try {
			MaxToJme C1 = new MaxToJme();
			ByteArrayOutputStream BO = new ByteArrayOutputStream();
			String[] fileNames = {"ATRON.3DS","simpleATRON.3ds","mediumATRON.3ds","goodATRON.3ds"};
			InputStream maxStream = new FileInputStream(JMEBasicGraphicalSimulation.setupPath("resources/"+fileNames[2]));
			C1.convert(new BufferedInputStream(maxStream),BO);
			Node atronNode = (Node)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
			System.out.println("ATRON CAD Model loaded - nTriangles = "+atronNode.getVertexCount());
			atronModel = (TriMesh) getTriMesh(atronNode);
			Triangle[] ts = atronModel.getMeshAsTriangles(0, null);
			Vector3f[] normals = new Vector3f[ts.length];
			for(int i=0;i<ts.length;i++) {
				ts[i].calculateNormal();
				normals[i] = ts[i].getNormal();
			}
			FloatBuffer ns = BufferUtils.createFloatBuffer(normals);
			atronModel.getBatch(0).setNormalBuffer(ns);
			atronModel.setLocalScale(0.012f*radius);
			//atronModel.setLocalScale(0.092f*radius); //for "ATRON.3DS"
			atronModel.setName("ATRON CAD Model");
			/*Quaternion rotation =new Quaternion(new float[]{0f,0f,(float)(Math.PI/4)});
			atronModel.setLocalRotation(rotation);
			atronModel.updateModelBound();*/
	        
			
		} catch (IOException e) {
			e.printStackTrace(); 
		}
    }

    private static TriMesh getTriMesh(Spatial spatial) {
    	//System.out.println("N children "+((Node)spatial).getChildren().size());
    	if(spatial instanceof Node) {
    		for(Spatial s: ((Node)spatial).getChildren()) {
        		if(s instanceof TriMesh) {
        			return (TriMesh) s;
        		}
        		else {
        			TriMesh mesh = getTriMesh(s);
        			if(mesh!=null) return mesh;
        		}
        	}
    	}
    	else if(spatial instanceof TriMesh) return (TriMesh)spatial;
    	return null;
//		return (((Node)atronNode.getChild(0)).getChild(0));;
	}

	public ArrayList<Vector3f> connectorPos(Module m) {
    	Vector3f mPos = (Vector3f)getModulePos(m).get();
    	//System.out.println("Module "+m.getID()+" at ("+mPos.x+", "+mPos.y+", "+mPos.z+") has the following connectors");
    	ArrayList<Vector3f> cPos = new ArrayList<Vector3f>();
    	int index =0;
    	for(Connector c: m.getConnectors()) {
    		Vector3f absCpos = new Vector3f();
    		Vector3f cpos = ((JMEMechanicalConnector)c.getPhysics().get(0)).getPos();
    		//absCpos = absCpos.add(mPos);
    		absCpos = absCpos.add(cpos);
    		//System.out.println(" "+index+" at ("+absCpos.x+", "+absCpos.y+", "+absCpos.z+")");
    		cPos.add(absCpos);
    		index++;
    	}
    	return cPos;
    }

    public PhysicsVectorHolder getModulePos(Module m) {
    	Vector3f mPos = ((JMEModuleComponent)m.getPhysics().get(0)).getModuleNode().getLocalTranslation();
    	return new PhysicsVectorHolder(mPos);
    }

    public PhysicsQuaternionHolder getModuleOri(Module m) {
    	Quaternion mOri= ((JMEModuleComponent)m.getPhysics().get(0)).getModuleNode().getLocalRotation();
    	return new PhysicsQuaternionHolder(mOri);
    }

    public /*synchronized*/ PhysicsNode createBox(float xs, float ys, float zs, float mass, boolean isStatic) {
        Box meshBox = new Box( "obstacle#"+(obstacleCounter++), new Vector3f(), xs, ys, zs );
        meshBox.setModelBound( new BoundingBox() );
        meshBox.updateModelBound();
        this.setColor(meshBox, simulation.obstacleColors[obstacleCounter%simulation.obstacleColors.length]);
        final PhysicsNode boxNode;
        if(isStatic) {
        	boxNode = simulation.getPhysicsSpace().createStaticNode();
        }
        else {
        	boxNode = simulation.getPhysicsSpace().createDynamicNode();
        }
        boxNode.attachChild( meshBox );
        boxNode.generatePhysicsGeometry();
        simulation.rootNode.attachChild( boxNode );
        if(!isStatic) {
            if(mass!=0f) {
            	((DynamicPhysicsNode)boxNode).setMass(mass);
            }
            else {
            	((DynamicPhysicsNode)boxNode).computeMass();
            }
        }
        boxNode.setMaterial(Material.WOOD);
        return boxNode;
    }

    public StaticPhysicsNode createPlane(int size, WorldDescription.TextureDescription texture) {
        final StaticPhysicsNode planeNode = simulation.getPhysicsSpace().createStaticNode();
        TriMesh planeBox = new Box( "plane", new Vector3f(), size, 0.5f, size );
        planeNode.attachChild( planeBox );
        planeBox.setModelBound( new BoundingBox() );
        planeBox.updateModelBound();
        planeNode.getLocalTranslation().set( 0, -1f, 0 );
        simulation.rootNode.attachChild( planeNode );
        planeNode.generatePhysicsGeometry();
        planeNode.setMaterial(description2material(PhysicsParameters.get().getPlaneMaterial()));
        Texture tex = TextureManager.loadTexture(JMEBasicGraphicalSimulation.setupPath(texture.getFileName()),Texture.MM_LINEAR_LINEAR,Texture.FM_LINEAR);
        tex.setWrap(Texture.WM_WRAP_S_WRAP_T);
        VectorDescription texScale = texture.getScale(size);
        tex.setScale(new Vector3f(texScale.getX(),texScale.getY(),texScale.getZ()));
        tex.setApply(Texture.AM_REPLACE);
        TextureState ts = simulation.getDisplay().getRenderer().createTextureState();
        ts.setTexture(tex, 0);

        planeNode.setRenderState(ts);
       
        return planeNode;
    }

    private Material description2material(PhysicsParameters.Material planeMaterial) {
        if(planeMaterial==PhysicsParameters.Material.RUBBER) return Material.RUBBER;
        if(planeMaterial==PhysicsParameters.Material.ICE) return Material.ICE;
        if(planeMaterial==PhysicsParameters.Material.WOOD) return Material.WOOD;
        if(planeMaterial==PhysicsParameters.Material.CONCRETE) return Material.CONCRETE;
        if(planeMaterial==PhysicsParameters.Material.GLASS) return Material.GLASS;
        if(planeMaterial==PhysicsParameters.Material.IRON) return Material.IRON;
        if(planeMaterial==PhysicsParameters.Material.GRANITE) return Material.GRANITE;
        if(planeMaterial==PhysicsParameters.Material.DEFAULT) return Material.DEFAULT;
        else throw new Error("Unknown material "+planeMaterial+", please add more cases");
    }

    /**
    	 * build the height map and terrain block.
     * @param textureDescription 
    	 */
    	public StaticPhysicsNode createTerrain(int size, TextureDescription textureDescription) { //change to hillhweightmap - looks better
    		// Generate a random terrain data
    //		 Generate a random terrain data
    		int powerSize = 64;
    		if(powerSize!=size) System.out.println("Requested terrain place size "+size+" converted to "+powerSize);
    		MidPointHeightMap heightMap = new MidPointHeightMap(powerSize, 0.01f);
    		// Scale the data
    		Vector3f terrainScale = new Vector3f(1, 0.003f, 1);
    		// create a terrainblock
    		simulation.tb = new TerrainBlock("Terrain", heightMap.getSize(), terrainScale,
    				heightMap.getHeightMap(), new Vector3f(-32, -1, -32), false);
    		simulation.tb.setModelBound(new BoundingBox());
    		simulation.tb.updateModelBound();
     
    		
    		// assign the texture to the terrain
    		TextureState ts = simulation.getDisplay().getRenderer().createTextureState();
    		ts.setEnabled(true);
    		if(false) {
    			// generate a terrain texture with 3 textures
        		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(
        				heightMap);
        		pt.addTexture(new ImageIcon("resources/grassb.png"), -255, 0, 255);
        		pt.addTexture(new ImageIcon("resources/dirt.jpg"), 0, 128, 255);
        		pt.addTexture(new ImageIcon("resources/highest.jpg"), 128, 255,
        				384);
        		pt.createTexture(32);
	    		Texture t1 = TextureManager.loadTexture(pt.getImageIcon().getImage(),
	    				Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
	    		ts.setTexture(t1, 0);
    		}
    		else {
    			Texture tex = TextureManager.loadTexture(textureDescription.getFileName(),Texture.MM_LINEAR_LINEAR,Texture.FM_LINEAR);
            	tex.setWrap(Texture.WM_WRAP_S_WRAP_T);
                VectorDescription texScale = textureDescription.getScale(size);
                tex.setScale(new Vector3f(texScale.getX(),texScale.getY(),texScale.getZ()));
                //tex.setScale(new Vector3f(50f*powerSize/4,50f*powerSize/4,0f));
                tex.setApply(Texture.AM_REPLACE);
                ts.setTexture(tex, 0);
           }
                		
    		simulation.tb.setRenderState(ts);
    		
    		final StaticPhysicsNode terrainNode = simulation.getPhysicsSpace().createStaticNode();
    		//terrainNode.attachChild(tb);
    		//rootNode.attachChild(tb);
    		
    		terrainNode.attachChild( simulation.tb );
    		simulation.tb.setModelBound( new BoundingBox() );
    		simulation.tb.updateModelBound();
            //terrainNode.getLocalTranslation().set( 0, -100, 0 );
            simulation.rootNode.attachChild( terrainNode );
            terrainNode.generatePhysicsGeometry();
            return terrainNode;
            
    		//return tb;
    	}

     int findBestConnection(Module m1, Module m2) {
    	ArrayList<Vector3f> m1Cpos = connectorPos(m1);
    	ArrayList<Vector3f> m2Cpos = connectorPos(m2);
    	int connector = -1;
    	for(int i=0;i<m1Cpos.size();i++) {
    		for(int j=0;j<m2Cpos.size();j++) {
        		float d = m1Cpos.get(i).distance(m2Cpos.get(j));
        		if(d<0.005) {
        			//System.out.println("dist from "+i+" to "+j+" is "+d);
        			connector = i;
        		}
        		
        	}
    	}
    	return connector;
    }
     
}
