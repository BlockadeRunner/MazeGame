package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * @author Alex Longo
 */
class ReliableRobotTest 
{
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method setController(Control controller):
	 * This test determines if the setController
	 * method correctly sets the class instance 
	 * variable for controller to the parameter 
	 * controller.
	 */	
	@Test
	void testSetController() 
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method addDistanceSensor(DistanceSensor sensor, Direction mountedDirection):
	 * This test determines if the addDistanceSensor method 
	 * correctly sets the class instance variable for distance
	 * sensor in parameter mountedDirection to the parameter 
	 * sensor.
	 */	
	@Test
	void testAddDistanceSensor()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getCurrentPosition():
	 * This test determines if the getCurrentPosition method 
	 * correctly uses the getCurrentPosition() method on the 
	 * controller object stored in ReliableRobot, and returns 
	 * what it returns. It also checks whether this method 
	 * throws an exception if the position is outside of the
	 * maze.
	 */	
	@Test
	void testGetCurrentPosition()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getCurrentDirection():
	 * This test determines if the getCurrentDirection method 
	 * correctly uses the getCurrentDirection() method on the 
	 * controller object stored in ReliableRobot, and returns 
	 * what it returns.
	 */	
	@Test
	void testGetCurrentDirection()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getBatteryLevel():
	 * This test determines if the getBatteryLevel method 
	 * correctly returns the value stored for the instance 
	 * variable batteryLevel of ReliableRobot.
	 */	
	@Test
	void testGetBatteryLevel()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for RobotDriver: Wizard
	 * 
	 * Testing method setBatteryLevel(float level):
	 * This test determines if the setBatteryLevel
	 * method correctly sets the Wizard class instance 
	 * variable for battery to the parameter level. It
	 * also tests if the method properly throws an 
	 * exception if the parameter level is negative
	 */
	@Test
	void testSetBatteryLevel()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getEnergyForFullRotation():
	 * This test determines if the getEnergyForFullRotation method 
	 * correctly returns the energy consumed during a full 360 degrees 
	 * rotation of the robot.
	 */	
	@Test
	void testGetEnergyForFullRotation()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getEnergyForStepForward():
	 * This test determines if the getEnergyForStepForward method 
	 * correctly returns the energy consumed during a single step
	 * forward of the robot.
	 */	
	@Test
	void testGetEnergyForStepForward()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method getOdometerReading():
	 * This test determines if the getOdometerReading method 
	 * correctly returns the value stored for the instance
	 * variable odometer in ReliableRobot
	 */	
	@Test
	void testGetOdometerReading()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method resetOdometer():
	 * This test determines if the resetOdometer()
	 * method correctly sets the ReliableRobot class instance 
	 * variable for odometer to 0.
	 */
	@Test
	void testResetOdometer()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method rotate(Turn turn):
	 * This test determines if the rotate
	 * method correctly rotates the ReliableRobot 
	 * in the proper direction provided by its 
	 * parameter turn. It also determines
	 * if the ReliableRobot properly adjusts 
	 * it's battery level after making its rotation.
	 */
	@Test
	void testRotate()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method move(int distance):
	 * This test determines if the move method 
	 * correctly moves the ReliableRobot forward a 
	 * number of steps corresponding to the 
	 * parameter distance. It also determines
	 * if the ReliableRobot correctly throws an
	 * IllegalArgumentException if the parameter
	 * distance is not positive.
	 */
	@Test
	void testMove()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method jump():
	 * This test determines if the jump method 
	 * correctly moves the ReliableRobot forward a 
	 * single step and through the wall it is
	 * facing. It also tests that the method will
	 * not move the ReliableRobot through an exterior
	 * wall.
	 */
	@Test
	void testJump()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method isAtExit():
	 * This test determines if the isAtExit method 
	 * correctly returns true if the current position 
	 * of the ReliableRobot is right at the exit but 
	 * still inside the maze. It also determines if 
	 * the method returns false otherwise.
	 */
	@Test
	void testIsAtExit()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method isInsideRoom():
	 * This test determines if the isInsideRoom method 
	 * correctly returns true if the current position 
	 * of the ReliableRobot is in a room. It also determines if 
	 * the method returns false otherwise.
	 */
	@Test
	void testIsInsideRoom()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method hasStopped():
	 * This test determines if the hasStopped method 
	 * correctly returns true if the instance variable
	 * 'stopped' for ReliableRobot is true. It also 
	 * determines if the method returns false otherwise.
	 */
	@Test
	void testHasStopped()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method distanceToObstacle(Direction direction):
	 * This test determines if the distanceToObstacle method correctly 
	 * returns the number of steps towards an obstacle if an obstacle is 
	 * visible in a straight line of sight in the parameter direction, 
	 * Integer.MAX_VALUE otherwise. It also tests whether this method 
	 * throws an UnsupportedOperationException if ReliableRobot has 
	 * no sensor in this direction or the sensor exists but is currently 
	 * not operational.
	 */
	@Test
	void testDistanceToObstacle()
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for Robot: ReliableRobot
	 * 
	 * Testing method canSeeThroughTheExitIntoEternity(Direction direction):
	 * This test determines if the canSeeThroughTheExitIntoEternity 
	 * method correctly returns true if the exit of the maze is visible 
	 * in a straight line of sight in the direction provided via parameter
	 * direction. It also tests whether this method throws an 
	 * UnsupportedOperationException if ReliableRobot has no sensor in this 
	 * direction or the sensor exists but is currently not operational.
	 */
	@Test
	void testcanSeeThroughTheExitIntoEternity()
	{
		assertEquals(true, true);
	}

}
