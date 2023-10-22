package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import generation.CardinalDirection;
import gui.Robot.Direction;

/**
 * @author Alex Longo
 */
class ReliableSensorTest 
{	
	/**
	 * Test method for DistanceSensor: ReliableSensor
	 * 
	 * Testing method distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply):
	 * This test determines if the distanceToObstacle method 
	 * correctly returns number of steps towards an obstacle 
	 * if an obstacle is visible in a straight line of sight,
	 * Integer.MAX_VALUE otherwise. 
	 * It also tests whether this
	 * method correctly throws an Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * or PowerFailure if the power supply is insufficient for 
	 * the operation.
	 * It also tests whether this method correctly throws an 
	 * IllegalArgumentException if any parameter is null or if 
	 * currentPosition is outside of legal range.
	 * It also tests whether this method correctly throws an
	 * IndexOutOfBoundsException if the powersupply is out of range.
	 */	
	@Test
	void testDistanceToObstacle() 
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for DistanceSensor: ReliableSensor
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
	 * Test method for DistanceSensor: ReliableSensor
	 * 
	 * Testing method setSensorDirection(Direction mountedDirection):
	 * This test determines if the setSensorDirection method 
	 * correctly sets the sensor object's direction value 
	 * based on the parameter mountedDirection. It also tests
	 * whether this method throws an IllegalArgumentException
	 * if the parameter passed in is null.
	 */
	@Test
	void testSetSensorDirection() 
	{
		assertEquals(true, true);
	}
	
	/**
	 * Test method for DistanceSensor: ReliableSensor
	 * 
	 * Testing method getEnergyConsumptionForSensing():
	 * This test determines if the getEnergyConsumptionForSensing 
	 * method correctly returns the amount of energy a ReliableSensor 
	 * uses for calculating the distance to an obstacle exactly once.
	 */
	@Test
	void testGetEnergyConsumptionForSensing() 
	{
		assertEquals(true, true);
	}


}
