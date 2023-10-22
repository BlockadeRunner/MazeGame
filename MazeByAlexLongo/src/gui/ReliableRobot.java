package gui;

import java.awt.print.Printable;

import javax.naming.spi.DirStateFactory.Result;

import generation.CardinalDirection;
import gui.Constants.UserInput;

/**
 * @author Alex Longo
 * 
 * Class: 
 * ReliableRobot
 * 
 * Responsibilities: 
 * Transfer information from sensors to RobotDriver,
 * perform commands from RobotDriver by using 
 * actuators through Control. Keep track of battery
 * life, handle battery consumption and failure.
 * 
 * Collaborators: 
 * Wizard, WallFollower, ReliableSensor, UnreliableSensor, Control
 */
public class ReliableRobot implements Robot
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
	 * This method provides the ReliableRobot with a reference to the controller to cooperate with.
	 * The ReliableRobot memorizes the controller such that this method is most likely called only once
	 * and for initialization purposes. The controller serves as the main source of information
	 * for the ReliableRobot about the current position, the presence of walls, the reaching of an exit.
	 * The controller is assumed to be in the playing state.
	 * @param controller is the communication partner for ReliableRobot
	 * @throws IllegalArgumentException if controller is null, 
	 * or if controller is not in playing state, 
	 * or if controller does not have a maze
	 */
	@Override
	public void setController(Control controller) 
	{
		// Check if the controller is null, if so, throw IllegalArgumentException
		if(controller == null)
		{
			throw new IllegalArgumentException("Tried to set controller to null. Controller must not be null.");
		}
		
		// Check if the controller is not in playing state, if so, throw IllegalArgumentException
		if(controller.currentState instanceof StatePlaying == false)
		{
			throw new IllegalArgumentException("Tried to set a controller that was not in StatePlaying. Controller must be in state playing.");
		}
		
		// Check if the controller is does not have maze, if so, throw IllegalArgumentException
		if(controller.getMaze() == null)
		{
			throw new IllegalArgumentException("Tried to set controller that did not have a maze. Controller must have mazed.");
		}
		
		// Set the controller object in this instance of ReliableRobot to the controller passed in through parameter controller
		this.controller = controller;
	}	// end method setController

	/**
	 * This method adds a distance sensor to the ReliableRobot such that it measures in the given direction.
	 * This method is used when a ReliableRobot is initially configured to get ready for operation.
	 * @param sensor is the distance sensor to be added
	 * @param mountedDirection is the direction that it points to relative to the ReliableRobot's forward direction
	 */
	@Override
	public void addDistanceSensor(DistanceSensor sensor, Direction mountedDirection) 
	{
		// if mountedDirection == FORWARD, set the front distance sensor of this instance of ReliableRobot to sensor
		if(mountedDirection == Direction.FORWARD)
		{
			frontSensor = sensor;
			frontSensor.setSensorDirection(mountedDirection);
		}
		
		// if mountedDirection == BACKWARD, set the back distance sensor of this instance of ReliableRobot to sensor
		if(mountedDirection == Direction.BACKWARD)
		{
			backSensor = sensor;
			backSensor.setSensorDirection(mountedDirection);
		}
		
		// if mountedDirection == LEFT, set the left side distance sensor of this instance of ReliableRobot to sensor
		if(mountedDirection == Direction.LEFT)
		{
			leftSensor = sensor;
			leftSensor.setSensorDirection(mountedDirection);
		}
		
		// if mountedDirection == RIGHT, set the right side distance sensor of this instance of ReliableRobot to sensor
		if(mountedDirection == Direction.RIGHT)
		{
			rightSensor = sensor;
			rightSensor.setSensorDirection(mountedDirection);
		}
	}	// end method addDistanceSensor

	/**
	 * This method provides the current position as (x,y) coordinates for 
	 * the maze as an array of length 2 with [x,y].
	 * @return array of length 2, x = array[0], y = array[1]
	 * @throws Exception if position is outside of the maze
	 */
	@Override
	public int[] getCurrentPosition() throws Exception 
	{
		// get the current position of the robot by calling the getCurrentPosition() method on the controller object stored in this instance of ReliableRobot
		int[] currentPos = this.controller.getCurrentPosition();
		
		// check if the position is outside the maze
		if(((currentPos[0] < 0) || (currentPos[0] >= this.controller.getMaze().getWidth())) || ((currentPos[1] < 0) || (currentPos[1] >= this.controller.getMaze().getHeight())))
		{
			// Position outside maze, throw exception
			throw new Exception("Can't get current position. Position outside bounds of maze.");
		}
		// position inside maze
		else 
		{
			// return current position
			return currentPos;
		}
	}

	/**
	 * This method provides the ReliableRobot's current direction.
	 * @return cardinal direction is the ReliableRobot's current direction in absolute terms
	 */	
	@Override
	public CardinalDirection getCurrentDirection() 
	{
		// Call the getCurrentDirection() method on the controller object stored in this instance of ReliableRobot, return what it returns
		return this.controller.getCurrentDirection();
	}

	/**
	 * This method returns the current battery level.
	 * @return current battery level 
	 */
	@Override
	public float getBatteryLevel() 
	{
		// Check if batteryLevel is <= 0
		if(this.batteryLevel <= 0)
		{
			stopped = true; 
		}
		
		// return the value stored for batteryLevel float of this instance of ReliableRobot
		return this.batteryLevel;
	}

	/**
	 * This method sets the current battery level.
	 * @param level is the current battery level
	 * @throws IllegalArgumentException if level is negative 
	 */
	@Override
	public void setBatteryLevel(float level) 
	{
		// check if level is negative, if it is, then throw IllegalArgumentException
		if(level < 0)
		{
			throw new IllegalArgumentException("Attempted to set battery level to negative value. Battery level must be >= 0.");
		}
		// Battery level is >= 0
		else 
		{
			// Update value for battery level
			this.batteryLevel = level;
		}
	}

	/**
	 * This method gives the energy consumption for a full 360 degree rotation.
	 * @return energy for a full rotation
	 */
	@Override
	public float getEnergyForFullRotation() 
	{
		// Return 12 (3 per quarter turn)
		return 12f;
	}

	/**
	 * This method gives the energy consumption for moving forward for 
	 * a distance of 1 step.
	 * @return energy for a single step forward
	 */
	@Override
	public float getEnergyForStepForward() 
	{
		// Return 6
		return 6f;
	}

	/** 
	 * This method gets the distance traveled by the ReliableRobot.
	 * @return the distance traveled measured in single-cell steps forward
	 */
	@Override
	public int getOdometerReading() 
	{
		// Return the value stored for the odometer counter in this instance of ReliableRobot
		return odometer;
	}

	/** 
     * This method resets the odometer counter to zero.
     */
	@Override
	public void resetOdometer() 
	{
		// set value stored for the odometer counter in this instance of RelaibleRobot to 0
		odometer = 0;
	}

	/**
	 * This method turns ReliableRobot on the spot for 90 degrees in the direction 
	 * provided within the parameter turn. 
	 * @param turn is the direction to turn and relative to current forward direction. 
	 */
	@Override
	public void rotate(Turn turn) 
	{
		// Check if hasStopped() == true
		if(this.hasStopped() == true)
		{
			// Has stopped due to some malfunction, do nothing
			return;
		}
		
		// Check if there is enough battery to turn 90 degrees
		if(this.batteryLevel - 3 >= 0)
		{
			// Check if the direction of turn is right
			if(turn == Turn.RIGHT)
			{	
				// Use the controller to rotate the robot
				this.controller.currentState.handleUserInput(UserInput.RIGHT, 0);
				
				// Update battery
				this.batteryLevel = this.batteryLevel - 3;
			}
			// Check if the direction of turn is left
			else if(turn == Turn.LEFT)
			{	
				// Use the controller to rotate the robot
				this.controller.currentState.handleUserInput(UserInput.LEFT, 0);
				
				// Update battery
				this.batteryLevel = this.batteryLevel - 3;
			}
			// Check if the direction of turn is around
			else if(turn == Turn.AROUND)
			{
				// Check if there is enough battery for two turns
				if(this.batteryLevel - 6 >= 0)
				{
					// Use the controller to rotate the robot
					this.controller.currentState.handleUserInput(UserInput.RIGHT, 0);
					this.controller.currentState.handleUserInput(UserInput.RIGHT, 0);
					
					// Update battery
					this.batteryLevel = this.batteryLevel - 6;
				}
			}
		}
		// There is not enough battery to turn
		else 
		{	
			// Update battery
			this.batteryLevel = 0;
			
			// Update stopped boolean flag
			stopped = true;
		}
	}	// end method rotate

	/**
	 * This method moves ReliableRobot forward a given number of steps. A step matches a single cell.
	 * @param distance is the number of cells to move in the robot's current forward direction 
	 * @throws IllegalArgumentException if distance not positive
	 */
	@Override
	public void move(int distance) 
	{
		// Check if distance is positive, if not, throw IllegalArgumentException
		if(distance <= 0)
		{
			throw new IllegalArgumentException("Attempted to move a negative distance. Distance must be positive.");
		}
		
		// Check if hasStopped() == true
		if(this.hasStopped() == true)
		{
			// Has stopped due to some malfunction, do nothing
			return;
		}
		
		// Step one step at a time to try to reach distance
		for(int s = 0; s < distance; s++)
		{
			// Check that there is enough battery to move one step
			if(this.batteryLevel - 6 >= 0)
			{
				// Check that the robot won't run into a wall
				if(this.controller.getMaze().hasWall(this.controller.getCurrentPosition()[0], this.controller.getCurrentPosition()[1], this.controller.getCurrentDirection()))
				{
					// would run into wall, this is not supposed to happen, stop robot
					stopped = true;
					
					// Break out of loop
					break;
				}
				// Not going to run into wall
				else 
				{
					// Use the controller to move the robot one step forward
					this.controller.currentState.handleUserInput(UserInput.UP, 0);
					
					// Update Battery
					this.batteryLevel = this.batteryLevel - 6; 
					
					// increment odometer
					this.odometer++;
				}
			}
			// Not enough battery to move
			else 
			{
				// Update battery
				this.batteryLevel = 0;
				
				// Update stopped boolean flag
				stopped = true;
				
				// Break out of loop
				break;
			}
		}		
	}	// end move method

	/**
	 * This method makes ReliableRobot move in a forward direction 
	 * even if there is a wall in front of it. This makes ReliableRobot 
	 * jump over the wall if necessary. The distance is always 1 step 
	 * and the direction is always forward. If the ReliableRobot tries to jump 
	 * over an exterior wall and would land outside of the maze that way,  
	 * it remains at its current location and direction,
	 * hasStopped() == true as this is not supposed to happen.
	 */
	@Override
	public void jump() 
	{	
		// Check if hasStopped() == true
		if(this.hasStopped() == true)
		{
			// Has stopped due to some malfunction, do nothing
			return;
		}
		
		// Check that there is enough battery to move one step
		if(this.batteryLevel - 40 >= 0)
		{
			// Check that the robot won't jump over a Western exterior wall
			if((this.controller.getCurrentDirection() == CardinalDirection.West) && (this.controller.getCurrentPosition()[0] == (this.controller.getMaze().getWidth() - 1)))
			{
				// would jump over exterior wall, this is not supposed to happen, stop robot
				stopped = true;
			}
			// Check that the robot won't jump over an Eastern exterior wall
			else if((this.controller.getCurrentDirection() == CardinalDirection.East) && (this.controller.getCurrentPosition()[0] == 0))
			{
				// would jump over exterior wall, this is not supposed to happen, stop robot
				stopped = true;
			}
			// Check that the robot won't jump over a Northern exterior wall
			else if((this.controller.getCurrentDirection() == CardinalDirection.North) && (this.controller.getCurrentPosition()[1] == 0))
			{
				// would jump over exterior wall, this is not supposed to happen, stop robot
				stopped = true;
			}
			// Check that the robot won't jump over a Southern exterior wall
			else if((this.controller.getCurrentDirection() == CardinalDirection.South) && (this.controller.getCurrentPosition()[1] == (this.controller.getMaze().getHeight() - 1)))
			{
				// would jump over exterior wall, this is not supposed to happen, stop robot
				stopped = true;
			}
			// Not going to jump over an exterior wall
			else 
			{
				// Use the controller to move the robot one step forward
				this.controller.currentState.handleUserInput(UserInput.JUMP, 0);
				
				// Update Battery
				this.batteryLevel = this.batteryLevel - 40; 
			}
		}
		// Not enough battery to move
		else 
		{
			// Update battery
			this.batteryLevel = 0;
			
			// Update stopped boolean flag
			stopped = true;
		}
	}	// end method jump

	/**
	 * This method tells if the current position is right at the 
	 * exit but still inside the maze. 
	 * @return true if robot is at the exit, false otherwise
	 */
	@Override
	public boolean isAtExit() 
	{
		/* 
		 * Check the maze object stored in the control object, see if 
		 * ReliableRobot's coordinates match that maze's exit coordinates, 
		 * if so then return true, return false otherwise.
		 */
		if(this.controller.getCurrentPosition().equals(this.controller.getMaze().getExitPosition()))
		{
			return true;
		}
		
		// Not at exit, return false
		return false;
	}

	/**
	 * This method tells if current position is inside a room. 
	 * @return true if robot is inside a room, false otherwise
	 */	
	@Override
	public boolean isInsideRoom() 
	{
		/* 
		 * Check the maze object stored in the control object, call the method
		 * isInRoom() method on maze, if it returns true, return true, otherwise
		 * return false
		 */
		if(this.controller.getMaze().isInRoom(this.controller.getCurrentPosition()[0], this.controller.getCurrentPosition()[1]))
		{
			return true;
		}
		
		// Not in room, return false
		return false;
	}

	/**
	 * This method tells if ReliableRobot has stopped for reasons 
	 * like lack of energy, hitting an obstacle, etc.
	 * @return true if the robot has stopped, false otherwise
	 */
	@Override
	public boolean hasStopped() 
	{
		// Check if the value of the boolean flag 'stopped' for this instance of ReliableRobot is true, it so return true, otherwise return false
		if(this.stopped)
		{
			return true;
		}
		
		// Not stopped, return false
		return false;
	}

	/**
	 * This method tells the distance to an obstacle (a wall) 
	 * in the given direction. The direction is relative to the robot's 
	 * current forward direction.
	 * Distance is measured in the number of cells towards that obstacle
	 * The ReliableRobot uses its internal DistanceSensor objects for this and
	 * delegates the computation to the DistanceSensor in the correct direction
	 * @param direction specifies the direction of interest
	 * @return number of steps towards obstacle if obstacle is visible 
	 * in a straight line of sight, Integer.MAX_VALUE otherwise
	 * @throws UnsupportedOperationException if ReliableRobot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException 
	{		
		// Check if sensor to be used is front sensor
		if(direction == Direction.FORWARD)
		{
			frontSensor.setMaze(this.controller.getMaze());
			// Check that Robot has a front sensor
			if(frontSensor == null)
			{
				throw new UnsupportedOperationException("Attempted to sense distance without a front sensor. Front sensor required for distance sensing.");
			}
			
			// Convert batteryLevel to float[] powersupply for distance sensor
			float[] powersupply = new float[1];
			powersupply[0] = batteryLevel;
			
			// Use distance sensor to get the distance to the nearest obstacle
			try 
			{
				// return the distance found by distance sensor
				return frontSensor.distanceToObstacle(this.controller.getCurrentPosition(), this.controller.getCurrentDirection(), powersupply);
			} 
			catch (Exception e) 
			{
				// This error would come from distanceToObstacle method of frontSensor catching a powersupply error
				e.printStackTrace();
				return 0;
			}
		}
		
		// Check if sensor to be used is back sensor
		else if(direction == Direction.BACKWARD)
		{
			backSensor.setMaze(this.controller.getMaze());
			// Check that Robot has a back sensor
			if(backSensor == null)
			{
				throw new UnsupportedOperationException("Attempted to sense distance without a back sensor. Back sensor required for distance sensing.");
			}
			
			// Convert batteryLevel to float[] powersupply for distance sensor
			float[] powersupply = new float[1];
			powersupply[0] = batteryLevel;
			
			// Use distance sensor to get the distance to the nearest obstacle
			try 
			{
				// return the distance found by distance sensor
				return backSensor.distanceToObstacle(this.controller.getCurrentPosition(), this.controller.getCurrentDirection(), powersupply);
			} 
			catch (Exception e) 
			{
				// This error would come from distanceToObstacle method of frontSensor catching a powersupply error
				e.printStackTrace();
				return 0;
			}
		}

		// Check if sensor to be used is left sensor
		else if(direction == Direction.LEFT)
		{
			leftSensor.setMaze(this.controller.getMaze());
			// Check that Robot has a left sensor
			if(leftSensor == null)
			{
				throw new UnsupportedOperationException("Attempted to sense distance without a left sensor. Left sensor required for distance sensing.");
			}
			
			// Convert batteryLevel to float[] powersupply for distance sensor
			float[] powersupply = new float[1];
			powersupply[0] = batteryLevel;
			
			// Use distance sensor to get the distance to the nearest obstacle
			try 
			{
				// return the distance found by distance sensor
				return leftSensor.distanceToObstacle(this.controller.getCurrentPosition(), this.controller.getCurrentDirection(), powersupply);
			} 
			catch (Exception e) 
			{
				// This error would come from distanceToObstacle method of frontSensor catching a powersupply error
				e.printStackTrace();
				return 0;
			}
		}
		
		// sensor to be used is right sensor
		else 
		{
			rightSensor.setMaze(this.controller.getMaze());
			// Check that Robot has a right sensor
			if(rightSensor == null)
			{
				throw new UnsupportedOperationException("Attempted to sense distance without a right sensor. Right sensor required for distance sensing.");
			}
			
			// Convert batteryLevel to float[] powersupply for distance sensor
			float[] powersupply = new float[1];
			powersupply[0] = batteryLevel;
			
			// Use distance sensor to get the distance to the nearest obstacle
			try 
			{
				// return the distance found by distance sensor
				return leftSensor.distanceToObstacle(this.controller.getCurrentPosition(), this.controller.getCurrentDirection(), powersupply);
			} 
			catch (Exception e) 
			{
				// This error would come from distanceToObstacle method of frontSensor catching a powersupply error
				e.printStackTrace();
				return 0;
			}
		}
	}	// end method distanceToObstacle

	/**
	 * This method tells if a sensor can identify the exit in the given direction relative to 
	 * the ReliableRobot's current forward direction from the current position.
	 * @param direction is the direction of the sensor
	 * @return true if the exit of the maze is visible in a straight line of sight
	 * @throws UnsupportedOperationException if ReliableRobot has no sensor in this direction
	 * or the sensor exists but is currently not operational
	 */
	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException 
	{
		// Check if the result of distanceToObstacle is Integer.MAX_VALUE
		if(this.distanceToObstacle(direction) == Integer.MAX_VALUE)
		{
			// Sensing eternal distance, return true
			return true;
		}
		
		// Not sensing an eternal distance, return false
		return false;
	}
	
	/**
	 * Helper method that sets private boolean flag stopped
	 */
	public void setStopped(boolean stoppedInput)
	{
		this.stopped = stoppedInput;
	}

	/**
	 * PROJECT 4 METHOD
	 */
	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * PROJECT 4 METHOD
	 */
	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}

}
