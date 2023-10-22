package gui;

import javax.sound.midi.VoiceStatus;

import org.junit.jupiter.params.provider.NullEnum;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;


/**
 * @author Alex Longo
 * 
 * Class: 
 * ReliableSensor
 * 
 * Responsibilities: 
 * Uses the maze and its floorplan to measure
 * the distance between the robot and obstacles
 * (wallboards). Provides that information to 
 * ReliableRobot. 
 * 
 * Collaborators: 
 * Maze, ReliableRobot
 */
public class ReliableSensor implements DistanceSensor
{
	///////////////////////////////////////////////
	// 		 Private Instance Variables	     	 //
	///////////////////////////////////////////////
	
	// store a maze object used for information about the cells within it
	private Maze maze;
	
	// store the direction this sensor is mounted in
	private Direction direction;
	
	/**
	 * Constructor for a Reliable Sensor, sets sensor direction
	 * @param direction - the direction on the robot that the sensor is mounted
	 */
	protected ReliableSensor(Direction direction)
	{
		this.direction = direction;
	}	
	
	/**
	 * This method tells the distance to an obstacle (a wallboard) that the sensor
	 * measures.
	 * Distance is measured in the number of cells towards that obstacle.
	 * @param currentPosition is the current location as (x,y) coordinates
	 * @param currentDirection specifies the direction of the robot
	 * @param powersupply is an array of length 1, whose content is modified 
	 * to account for the power consumption for sensing
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise.
	 * @throws Exception with message 
	 * SensorFailure if the sensor is currently not operational
	 * PowerFailure if the power supply is insufficient for the operation
	 * @throws IllegalArgumentException if any parameter is null
	 * or if currentPosition is outside of legal range
	 * ({@code currentPosition[0] < 0 || currentPosition[0] >= width})
	 * ({@code currentPosition[1] < 0 || currentPosition[1] >= height}) 
	 * @throws IndexOutOfBoundsException if the powersupply is out of range
	 * ({@code powersupply < 0}) 
	 */
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply) throws Exception 
	{
		// If any of the parameters are null, throw an exception
		if(currentPosition == null || currentDirection == null || powersupply == null)
		{
			throw new Exception();
		}
		// make an incrementer to keep track of the distance to wall
		int distanceToWall = 0;
		int[] cellCoords = currentPosition;
		while(!this.maze.hasWall(cellCoords[0], cellCoords[1], currentDirection))
		{
			cellCoords = checkCell(cellCoords, currentDirection);
			distanceToWall++;
		}
		return distanceToWall;

	}	// end method: distanceToObstacle
	
	/**
	 * Private helper method to move over to the next cell via incrementation
	 * @param cellCoords - coords of the current cell
	 * @param currentDirection - direction to move in
	 * @return the next cell in that direction
	 */
	private int[] checkCell(int[] cellCoords, CardinalDirection currentDirection)
	{
		int cellXcoord = cellCoords[0];
		int cellYcoord = cellCoords[1];
		if(currentDirection == CardinalDirection.North)
		{
			cellYcoord--;
		}
		else if(currentDirection == CardinalDirection.East)
		{
			cellXcoord++;
		}
		else if(currentDirection == CardinalDirection.South)
		{
			cellYcoord++;
		}
		else if(currentDirection == CardinalDirection.West)
		{
			cellXcoord--;
		}
		int[] nextCellToCheck = new int[2];
		nextCellToCheck[0] = cellXcoord;
		nextCellToCheck[1] = cellYcoord;
		return nextCellToCheck;
	}

	/**
	 * This method provides the maze information that is necessary to make
	 * a ReliableSensor able to calculate distances.
	 * @param maze the maze for this game
	 * @throws IllegalArgumentException if parameter is null
	 * or if it does not contain a floor plan
	 */
	@Override
	public void setMaze(Maze maze) 
	{
		// If maze null throw exception
		if(maze == null)
		{
			throw new IllegalArgumentException("Tried to set a maze equal to null. Maze must not be null!");
		}
		
		// If maze does not have a floorplan, throw exception
		if(maze.getFloorplan() == null)
		{
			throw new IllegalArgumentException("Tried to set a maze without a floorplan. Maze must have floorplan!");
		}
		
		// set maze for this sensor to parameter maze
		this.maze = maze;
	}

	/**
	 * This method takes a provided angle, or the relative direction at 
	 * which this ReliableSensor is mounted on the robot.
	 * If the direction is left, then the ReliableSensor is pointing
	 * towards the left hand side of the robot at a 90 degree
	 * angle from the forward direction. This method sets the sensor
	 * object's direction value.
	 * @param mountedDirection is the ReliableSensor's relative direction
	 * @throws IllegalArgumentException if parameter is null
	 */
	@Override
	public void setSensorDirection(Direction mountedDirection) 
	{
		// Check if parameter is null, if so, throw exception
		if(mountedDirection == null)
		{
			throw new IllegalArgumentException("Attempted to set Sensor direction to null. Sensor direction must not be null.");
		}
		
		// set this sensor's direction to the parameter mountedDirection
		this.direction = mountedDirection;
	}

	/**
	 * This method returns the amount of energy this ReliableSensor 
	 * uses for calculating the distance to an obstacle exactly once.
	 * @return the amount of energy used for using the sensor once
	 */
	@Override
	public float getEnergyConsumptionForSensing() 
	{
		// Return 1
		return 1f;
	}

	/**
	 * PROJECT 4 METHOD 
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException 
	{
		// Not applicable for reliable sensor, throw exception
		throw new UnsupportedOperationException("The failure operation unavailable.");
	}

	/**
	 * PROJECT 4 METHOD
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException 
	{
		// Not applicable for reliable sensor, throw exception
		throw new UnsupportedOperationException("The failure operation unavailable.");
	}

}
