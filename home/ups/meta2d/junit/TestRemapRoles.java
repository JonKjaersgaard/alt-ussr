package meta2d.junit;

import static org.junit.Assert.*;

import meta2d.Meta2DSimulation;
import meta2d.Statement;
import meta2d.statements.RemapRoles;

import org.junit.Before;
import org.junit.Test;

public class TestRemapRoles {

    RemapRoles identity, swap2, shiftAllForwards, shiftAllBackwards;
    
    @Before
    public void setUp() throws Exception {
        identity = new RemapRoles(Meta2DSimulation.MetaPosition_NORTH, Meta2DSimulation.MetaPosition_SOUTH, Meta2DSimulation.MetaPosition_EAST, Meta2DSimulation.MetaPosition_WEST);
        swap2 = new RemapRoles(Meta2DSimulation.MetaPosition_SOUTH, Meta2DSimulation.MetaPosition_NORTH, Meta2DSimulation.MetaPosition_WEST, Meta2DSimulation.MetaPosition_EAST);
        shiftAllForwards = new RemapRoles(Meta2DSimulation.MetaPosition_SOUTH, Meta2DSimulation.MetaPosition_EAST, Meta2DSimulation.MetaPosition_WEST,Meta2DSimulation.MetaPosition_NORTH);
        shiftAllBackwards = new RemapRoles(Meta2DSimulation.MetaPosition_WEST, Meta2DSimulation.MetaPosition_NORTH, Meta2DSimulation.MetaPosition_SOUTH, Meta2DSimulation.MetaPosition_EAST);
    }

    @Test
    public void testReverseStatementIdentity() {
        Statement inv = identity.reverseStatement();
        assertEquals(identity, inv);
    }

    @Test
    public void testReverseStatementSwap() {
        Statement inv = swap2.reverseStatement();
        assertEquals(swap2, inv);
    }

    @Test
    public void testReverseStatementShift() {
        Statement inv = shiftAllForwards.reverseStatement();
        assertEquals(shiftAllBackwards, inv);
    }

}
