/**
 * 
 */
package gui;

import generation.CardinalDirection;

/**
 * @author Alex Longo
 * 
 * Class: 
 * UnreliableRobot
 * 
 * Responsibilities: 
 * Transfer information from unreliable sensors to RobotDriver,
 * perform commands from RobotDriver by using 
 * actuators through Control. Keep track of battery
 * life, handle battery consumption and failure.
 * 
 * Collaborators: 
 * RobotDriver (WallFollower), UnreliableSensor, Control
 */
public class UnreliableRobot extends ReliableRobot implements Robot 
{
	///////////////////////////////////////////////
	// 		 Private Instance Variables	     	 //
	///////////////////////////////////////////////
	
	// store a controller object used for information about the the maze
	Control controller;
	
	// store a distance sensor for the front of the ReliableRobot
	public DistanceSensor frontSensor;
	
	// store a distance sensor for the back of the ReliableRobot
	public DistanceSensor backSensor;
	
	// store a distance sensor for the left side of the ReliableRobot
	public DistanceSensor leftSensor;
	
	// store a distance sensor for the right side of the ReliableRobot
	public DistanceSensor rightSensor;
	
	// store a float counter 'batteryLevel' that contains the charge of the battery
	float batteryLevel;
	
	// store an int counter 'odometer' to keep track of the distance traversed
	int odometer;
	
	// store a boolean flag 'stopped' to keep track of if the robot has stopped or not
	private boolean stopped = false;
	
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
	 * This an optional operation. If not implemented, the method
	 * throws an UnsupportedOperationException.
	 * 
	 * @param direction the direction the sensor is mounted on the robot
	 * @param meanTimeBetweenFailures is the mean time in seconds, must be greater than zero
	 * @param meanTimeToRepair is the mean time in seconds, must be greater than zero
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException 
	{
		// Check direction, then update the sensor in that direction
		if(direction == Direction.FORWARD)
		{
			frontSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
		}
		if(direction == Direction.LEFT)
		{
			leftSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
		}
		if(direction == Direction.BACKWARD)
		{
			backSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
		}
		if(direction == Direction.RIGHT)
		{
			rightSensor.startFailureAndRepairProcess(meanTimeBetweenFailures, meanTimeToRepair);
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
	 * This an optional operation. If not implemented, the method
	 * throws an UnsupportedOperationException.
	 * 
	 * @param direction the direction the sensor is mounted on the robot
	 * @throws UnsupportedOperationException if method not supported
	 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {

		// Check direction, then update the sensor in that direction
		if(direction == Direction.FORWARD)
		{
			frontSensor.stopFailureAndRepairProcess();
		}
		if(direction == Direction.LEFT)
		{
			leftSensor.stopFailureAndRepairProcess();
		}
		if(direction == Direction.BACKWARD)
		{
			backSensor.stopFailureAndRepairProcess();
		}
		if(direction == Direction.RIGHT)
		{
			rightSensor.stopFailureAndRepairProcess();
		}

	}

}
