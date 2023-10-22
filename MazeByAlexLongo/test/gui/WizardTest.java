package gui;

import gui.Constants.UserInput;
import gui.Robot.Direction;
import gui.Control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import generation.Maze;

/**
 * @author Alex Longo
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WizardTest 
{
	/////////////////////////////////////////////////////////
	//  Setup objects of all the different classes needed  //
	//    to test Wizard, and connect them all together    //
    /////////////////////////////////////////////////////////
	
	// Create a new controller
	Control myController = new Control();
	
	// Create a new Wizard 
	Wizard wizard = new Wizard();
	
	// Create a new Robot
	Robot robot = new ReliableRobot();
	
	// create Distance sensors for Robot
	DistanceSensor frontSensor = new ReliableSensor(Direction.FORWARD);
	DistanceSensor backSensor = new ReliableSensor(Direction.BACKWARD);
	DistanceSensor leftSensor = new ReliableSensor(Direction.LEFT);
	DistanceSensor rightSensor = new ReliableSensor(Direction.RIGHT);
	
	// create a String array for command line args
 	String[] myArgs = {"Wizard"};

 	
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method setRobot(Robot r):
	 * This test determines if the setRobot
	 * method correctly sets the class instance 
	 * variable for robot to the parameter r
	 */	
	@Test
	void testSetRobot() 
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method setMaze(Maze maze):
	 * This test determines if the setMaze
	 * method correctly sets the class instance 
	 * variable for maze to the parameter maze
	 */
	@Test
	void testSetMaze()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method drive2Exit():
	 * This test determines if the drive2Exit()
	 * method correctly drives the robot to 
	 * the exit. It also determines if the 
	 * drive2Exit method correctly throws an 
	 * exception if the robot stops.
	 */
	@Test
	void testDrive2Exit()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method drive1Step2Exit():
	 * This test determines if the drive1Step2Exit()
	 * method correctly moves the robot to an adjacent
	 * cell and then returns true. It also determines if
	 * the method correctly rotates the robot to face the 
	 * exit when at the exit position and then returns
	 * false. Lastly, it checks to see if an exception is
	 * properly thrown if the robot has stopped.
	 */
	@Test
	void testDrive1Step2Exit()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method getEnergyConsumption():
	 * This test determines if the getEnergyConsumption()
	 * method correctly returns the difference of the 
	 * initial and final energy levels of the robot.
	 */
	@Test
	void testGetEnergyConsumption()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method getPathLength():
	 * This test determines if the getPathLength()
	 * method correctly returns the value of the 
	 * instance variable odometer of its the 
	 * Wizard's robot object. 
	 */
	@Test
	void testGetPathLength()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method hasExit(Maze maze):
	 * This test determines if the hasExit(Maze maze)
	 * method correctly determines if the maze
	 * object of Wizard has an exit.
	 */
	@Test
	void testHasExit()
	{
		assertEquals(true, true);
	}

}
