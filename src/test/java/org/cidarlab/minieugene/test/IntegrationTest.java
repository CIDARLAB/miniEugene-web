package org.cidarlab.minieugene.test;

import static org.junit.Assert.*;

import org.cidarlab.minieugene.MiniEugene;
import org.junit.Test;

/**
 * The IntegrationTest class provides unit-tests for 
 * the integration of miniEugene into the MiniEugeneServlet.
 * 
 * @author Ernst Oberortner
 */
public class IntegrationTest {

	@Test
	public void testBasic() {
    	MiniEugene me = new MiniEugene();
    	String script = "N=5. CONTAINS p. NOTCONTAINS d.";
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		// no exception allowed!
    		assertTrue(false);
    	}
	}
	
	@Test
	public void testContains() {
    	MiniEugene me = new MiniEugene();
		String script = "N=4. CONTAINS a. NOTCONTAINS b. NOT CONTAINS c. NOT NOTCONTAINS d. NOT CONTAINS b.";
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		// no exception allowed!
    		assertTrue(false);
    	}		
	}

	@Test
	public void testDrives() {
    	MiniEugene me = new MiniEugene();
		String script = "N=4. ALL_FORWARD.CONTAINS p.CONTAINS r.CONTAINS c.CONTAINS t.r BEFORE c.r NEXTTO c.p DRIVES c.";
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		// no exception allowed!
    		assertTrue(false);
    	}		
	}

	@Test
	public void testInteractions() {
    	MiniEugene me = new MiniEugene();
		String script = "N=6. ALL_FORWARD. CONTAINS p1. CONTAINS p2. CONTAINS p3. CONTAINS r. CONTAINS c. CONTAINS t." + 
				"r BEFORE c. r NEXTTO c. p1 BEFORE c. p2 BEFORE c. t AFTER c. p3 AFTER t."+
				"c REPRESSES p3. in1 INDUCES p1. in2 INDUCES p2.";
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		// no exception allowed!
    		assertTrue(false);
    	}		
	}

	@Test
	public void testNorGate() {
    	MiniEugene me = new MiniEugene();
		String script = "N=4. ALL_FORWARD.CONTAINS p.CONTAINS r.CONTAINS c.CONTAINS t.r BEFORE c.r NEXTTO c.p DRIVES c.";
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		// no exception allowed!
    		assertTrue(false);
    	}		
	}
	
	@Test
	public void testMoreThan() {
		
		String script = "N=5. a NOTMORETHAN 4. b MORETHAN 1. NOT c MORETHAN 3.NOT d NOTMORETHAN 4. e NOTMORETHAN 5.";
    	MiniEugene me = new MiniEugene();
    	try {
    		me.solve(script);
    	} catch(Exception e) {
    		e.printStackTrace();
    		// no exception allowed!
    		assertTrue(false);
    	}		
	}
}
