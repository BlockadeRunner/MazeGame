package gui;

import gui.Robot.Direction;

/**
 * @author Alex Longo
 * 
 * Class: 
 * UnreliableSensor
 * 
 * Responsibilities: 
 * Uses the maze and its floorplan to measure
 * the distance between the robot and obstacles
 * (wallboards). Handles causing and resolving
 * failure events. Provides all information to 
 * Robot. 
 * 
 * Collaborators: 
 * Maze, Robot (assumed an UnreliableRobot for project 4)
 */
public class UnreliableSensor extends ReliableSensor
{
	// Private variable to keep track of whether is the sensor is broken or working
	boolean isOperational = true;
	
	// Protected thread for the failure and repair cycle of the sensor
	protected Thread failureAndRepairThread;
	
	// Call the superclass constructor: reliable sensor, with parameter input specifying the mounted direction
	public UnreliableSensor(Direction direction) 
	{
		super(direction);
	}
	
	/**
	 * Method starts a concurrent, independent failure and repair
	 * process that makes the sensor fail and repair itself.
	 * This creates alternating time periods of up time and down time.
	 * Up time: The duration of a time period when the sensor is in 
	 * operational is characterized by a distribution
	 * whose mean value is given by parameter meanTimeBetweenFailures.
	 * Down time: The duration of a time period when the sensor is in repair
	 * and not operational is characterized by a distribution
	 * whose mean value is given by parameter meanTimeToRepair.
	 * 
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair) throws UnsupportedOperationException 
	{
		// Attempt to run the failure and repair process thread for this sensor
		try 
		{
			// start the failure and repair cycle thread
			SensorFailureAndRepairProcess sensorCycle = new SensorFailureAndRepairProcess(this, meanTimeBetweenFailures, meanTimeToRepair);
			failureAndRepairThread = new Thread(sensorCycle);
			failureAndRepairThread.start();
		} 
		// Thread ran into an error, catch so code does not crash
		catch (Exception e) 
		{
			// set the thread to null
			failureAndRepairThread = null;
		}
		
	}

	/**
	 * This method stops a failure and repair process and
	 * leaves the sensor in an operational state.
	 * 
	 * It is complementary to starting a 
	 * failure and repair process. 
	 * 
	 * Intended use: If called after starting a process, this method
	 * will stop the process as soon as the sensor is operational.
	 * 
	 * If called with no running failure and repair process, 
	 * the method will return an UnsupportedOperationException.
	 * 
	 * @throws UnsupportedOperationException if called with no running failure and repair proces
	 */
	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException 
	{
		// Check if a thread is is actually running
		if (!(failureAndRepairThread == null) && failureAndRepairThread.isAlive()) 
		{
			// thread running, terminate the failure and repair cycle thread
			failureAndRepairThread.interrupt();
			
			// set the sensor to operational
			this.setWorking();
			
			// reset thread to null
			failureAndRepairThread = null;
		} 
		// Thread is not running, throw error
		else 
		{
			throw new UnsupportedOperationException("Can't stop a thread that is not running");
		}
	}
	
	/**
	 * This method sets the sensor to an unoperational state via the internal private variable isOperational
	 */
	public void setBroken()
	{
		this.isOperational = false;
	}
	
	/**
	 * This method sets the sensor to an operational state via the internal private variable isOperational
	 */
	public void setWorking()
	{
		this.isOperational = true;
	}
	
	/**
	 * This method returns whether the sensor is operational or not
	 * @return - True if the sensor is operational, false otherwise
	 */
	public boolean isOperational()
	{
		if(this.isOperational)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
