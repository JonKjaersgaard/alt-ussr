package meta2d.statements;

import java.util.Arrays;

import meta2d.Meta2DSimulation;
import meta2d.MetaController;
import meta2d.Statement;

public class RemapRoles extends Statement {
    private static final int northRole = 0, southRole = 1, eastRole = 2, westRole = 3;
    private int[] map = new int[4];
    
    public RemapRoles(int north, int south, int east, int west) {
        super(Meta2DSimulation.MetaPosition_NORTH|Meta2DSimulation.MetaPosition_SOUTH|Meta2DSimulation.MetaPosition_EAST|Meta2DSimulation.MetaPosition_WEST);
        this.map[northRole] = north; this.map[southRole] = south; this.map[eastRole] = east; this.map[westRole] = west;
    }

    private int meta2index(int metaRole) {
        switch(metaRole) {
        case Meta2DSimulation.MetaPosition_NORTH: return northRole;
        case Meta2DSimulation.MetaPosition_SOUTH: return southRole;
        case Meta2DSimulation.MetaPosition_EAST: return eastRole;
        case Meta2DSimulation.MetaPosition_WEST: return westRole;
        default: throw new Error("Undefined meta position");
        }
    }
    
    @Override
    protected boolean evaluateImplementation(MetaController metaController) {
        metaController.setMetaPosition(map[meta2index(metaController.getMetaPosition())]);
        return true;
    }

    private void assignField(RemapRoles other, int metaRole) {
        int target = map[meta2index(metaRole)];
        other.map[meta2index(target)] = metaRole;
    }
    
    @Override
    public Statement reverseStatement() {
        RemapRoles inverse = new RemapRoles(Meta2DSimulation.MetaPosition_UNKNOWN,Meta2DSimulation.MetaPosition_UNKNOWN,Meta2DSimulation.MetaPosition_UNKNOWN,Meta2DSimulation.MetaPosition_UNKNOWN);
        assignField(inverse,Meta2DSimulation.MetaPosition_NORTH);
        assignField(inverse,Meta2DSimulation.MetaPosition_SOUTH);
        assignField(inverse,Meta2DSimulation.MetaPosition_EAST);
        assignField(inverse,Meta2DSimulation.MetaPosition_WEST);
/*        if(northRole==Meta2DSimulation.MetaPosition_NORTH) inverse.northRole = Meta2DSimulation.MetaPosition_NORTH;
        else if(northRole==Meta2DSimulation.MetaPosition_SOUTH) inverse.southRole = Meta2DSimulation.MetaPosition_NORTH;
        else if(northRole==Meta2DSimulation.MetaPosition_EAST) inverse.eastRole = Meta2DSimulation.MetaPosition_NORTH;
        else if(northRole==Meta2DSimulation.MetaPosition_WEST) inverse.westRole = Meta2DSimulation.MetaPosition_NORTH;
        else throw new Error("Irreversible statement");*/
        return inverse;
    }
    
    public boolean isGroupStatement() { return true; }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(map);
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RemapRoles other = (RemapRoles) obj;
        if (!Arrays.equals(map, other.map))
            return false;
        return true;
    }

}
