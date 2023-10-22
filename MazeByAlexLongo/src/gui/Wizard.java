package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Turn;

/**
 * @author Alex Longo
 * 
 * Class: 
 * Wizard
 * 
 * Responsibilities: 
 * Use information from the maze object to guide the Robot out
 * of the maze as quickly as possible.
 * 
 * Collaborators: 
 * Maze, ReliableRobot
 */
public class Wizard implements RobotDriver
{
	///////////////////////////////////////////////
	// 		 Private Instance Variables	     	 //
	///////////////////////////////////////////////
	
	// store a robot platform object that the Wizard controls and receives information from
	private Robot robot;
	
	// store a maze object that the Wizard uses to find the exit
	private Maze maze;
	
	/**
	 * This method assigns a robot platform to the Wizard. 
	 * The Wizard uses a robot to perform, this method provides it with this necessary information.
	 * @param r robot to operate
	 */
	@Override
	public void setRobot(Robot r) 
	{
		// set the robot platform object of this instance of Wizard to the robot passed in through parameter r
		this.robot = r; 
	}

	/**
	 * This method provides the Wizard with the maze information.
	 * The wizard relies on this information to find the exit.
	 * @param maze represents the maze, must be non-null and a fully functional maze object.
	 */
	@Override
	public void setMaze(Maze maze) 
	{		
		// If maze is not null, set the maze object of this instance of Wizard to the maze passed in through parameter maze
		if(maze != null)
		{
			this.maze = maze;
		}
	}

	/**
	 * Drives the robot towards the exit following
	 * its solution strategy and given the exit exists and  
	 * given the robot's energy supply lasts long enough. 
	 * When the robot reaches the exit position and its forward
	 * direction points to the exit the search terminates and 
	 * the method returns true.
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception.
	 * If the method determines that it is not capable of finding the
	 * exit it returns false.
	 * @return true if driver successfully reaches the exit, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive2Exit() throws Exception 
	{
		////////////////////////////////////////////////////////////////////////
		//     Check that the Wizard is capable of finding a solution,  	  //
		//   this should be possible as long as there are no enclosed areas	  //
		////////////////////////////////////////////////////////////////////////
		
		// Identify the exit location
		int[] exitLocation = this.maze.getExitPosition();
		
		// Loop through each cell of the Maze
		for (int i = 0; i < this.maze.getWidth(); i++)
		{
			for (int j = 0; j < this.maze.getHeight(); j++)
			{
				// Make sure the current cell position is not the exit
				if(i != exitLocation[0] || j != exitLocation[1])
				{
					// See if there is a neighbor to the current cell that is closer to the exit
					// If there is not a closer neighbor, than an enclosed area is found
					if(this.maze.getNeighborCloserToExit(i, j) == null)
					{
						// Wizard is unable to find an exit, return false
						return false;
					}
				}
			}
		}
		
		/////////////////////////////////////////
		// Wizard capable of finding solution, //
		//     Guide robot to solution   	   //
		/////////////////////////////////////////
		
		// Create a boolean flag to keep track of whether the robot is at the exit
		boolean NotAtExit = true;
		
		// Continually re-run drive1Step2Exit until atExit is true
		while(NotAtExit)
		{
			NotAtExit = drive1Step2Exit();
		}
		
		// At exit, return true
		return true;
	}	// end drive2Exit

	/**
	 * Drives the robot one step towards the exit following
	 * its solution strategy and given the exit exists and 
	 * given the robot's energy supply lasts long enough.
	 * It returns true if the driver successfully moved
	 * the robot from its current location to an adjacent
	 * location.
	 * At the exit position, it rotates the robot 
	 * such that if faces the exit in its forward direction
	 * and returns false. 
	 * If the robot failed due to lack of energy or crashed, the method
	 * throws an exception. 
	 * @return true if it moved the robot to an adjacent cell, false otherwise
	 * @throws Exception thrown if robot stopped due to some problem, e.g. lack of energy
	 */
	@Override
	public boolean drive1Step2Exit() throws Exception 
	{
		// Check if robot is at exit position
		if(this.maze.getExitPosition().equals(this.robot.getCurrentPosition()))
		{
			// Check if robot is facing the exit using private helper method
			if(this.robotIsFacingExit())
			{
				// Robot is already facing exit, return false
				return false;
			}
			// Robot is not facing the exit
			else 
			{
				// Rotate robot to face exit using private helper method
				this.rotateRobotToFaceExit();
				
				// Robot is now facing exit, return false
				return false;
			}
		}	// end if robot is at exit position
		
		// Robot is not at exit position
		else 
		{
			// Keep track of the cell the robot should move to
			int[] cellCloserToExit = this.maze.getNeighborCloserToExit(this.robot.getCurrentPosition()[0], this.robot.getCurrentPosition()[1]);
			
			// Check if robot is facing neighboring cell closer to exit using private helper method
			if(this.robotIsFacingCellCloserToExit())
			{
				// Robot is already facing neighboring cell closer to exit, move 1 step in that direction
				this.robot.move(1);	
				
				// use an assert statement to determine that the robot has successfully moved
				assert ((this.robot.getCurrentPosition()[0] == cellCloserToExit[0]) && (this.robot.getCurrentPosition()[1] == cellCloserToExit[1]));
				
				// Robot was successfully moved, return true
				return true;
			}
			// Robot is not facing neighboring cell closer to exit
			else 
			{
				// Rotate robot to face neighboring cell closer to exit using private helper method
				this.rotateRobotToFaceCellCloserToExit();
				
				// Move the robot 1 step forward
				this.robot.move(1);
				
				// use an assert statement to determine that the robot has successfully moved
				assert ((this.robot.getCurrentPosition()[0] == cellCloserToExit[0]) && (this.robot.getCurrentPosition()[1] == cellCloserToExit[1]));
				
				// Robot was successfully moved, return true
				return true;
			}
		}	// end else robot is not at exit position
		
		/*
		 * Exceptions will be thrown by corresponding helper methods and methods called on the robot object
		 */
		
	}	// end method drive1Step2Exit

	/**
	 * This method returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for the wizard.
	 * @return the total energy consumption of the journey
	 */
	@Override
	public float getEnergyConsumption() 
	{		
		// Return the difference of the initial and final energy levels
		return (3500 - this.robot.getBatteryLevel());
	}

	/**
	 * This method returns the total length of the journey in number of cells traversed. 
	 * Being at the initial position counts as 0. 
	 * This is used as a measure of efficiency for a robot driver.
	 * @return the total length of the journey in number of cells traversed
	 */
	@Override
	public int getPathLength() 
	{
		// Use getOdometerReading method of robot to get the number of cells traversed by the robot
		int pathLength = this.robot.getOdometerReading();
		
		// Return the path length
		return pathLength;
	}
	
	/**
	 * This is a private helper method that 
	 * determines whether the robot is facing 
	 * the exit of the maze or not. 
	 * @return - true if the robot object is facing the exit of the maze, false otherwise
	 */
	private boolean robotIsFacingExit()
	{
		// Use a private helper method to identify the exit direction
		CardinalDirection exitDirection = this.getMazeExitDirection();
		
		// Now check if robot is facing the exit 
		if(this.robot.getCurrentDirection() == exitDirection)
		{
			// Robot is facing exit, return true
			return true;
		}
		// Robot is not facing the exit
		else
		{
			// return false
			return false;
		}
	}	// end private method robotIsFacingExit
	
	/**
	 * This is a private helper method that
	 * identifies and returns the direction 
	 * of the exit in the maze object stored
	 * in this instance of class Wizard.
	 * @return - the cardinal direction of the maze exit
	 */
	private CardinalDirection getMazeExitDirection()
	{
		// create a variable to store the exit direction
		CardinalDirection exitDirection = null;
		
		// Check if exit cell is on western border of maze
		if(this.maze.getExitPosition()[0] == 0)
		{
			// Check if exit cell is North-Western corner of maze
			if(this.maze.getExitPosition()[1] == 0)
			{
				// Check Northern border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.North)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.North;
				}
				
				// Check Western border of cell for exit
				else if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.West)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.West;
				}
				
			}
			// Check if exit cell is South-Western corner of maze
			else if(this.maze.getExitPosition()[1] == (maze.getHeight() - 1))
			{
				// Check Southern border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.South)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.South;
				}
				
				// Check Western border of cell for exit
				else if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.West)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.West;
				}
			}
			// Cell is not a corner
			else 
			{
				// Check Western border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.West)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.West;
				}
			}
		}	// end if exit cell is on western border of maze
		
		// Check if exit cell is on Eastern border of maze
		else if(this.maze.getExitPosition()[0] == (this.maze.getWidth() - 1))
		{
			// Check if exit cell is North-Eastern corner of maze
			if(this.maze.getExitPosition()[1] == 0)
			{
				// Check Northern border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.North)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.North;
				}
				
				// Check Eastern border of cell for exit
				else if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.East)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.East;
				}
				
			}
			// Check if exit cell is South-Eastern corner of maze
			else if(this.maze.getExitPosition()[1] == (maze.getHeight() - 1))
			{
				// Check Southern border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.South)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.South;
				}
				
				// Check Eastern border of cell for exit
				else if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.East)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.East;
				}
			}
			// Cell is not a corner
			else 
			{
				// Check Eastern border of cell for exit
				if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.East)))
				{
					// Exit found, update exit direction
					exitDirection = CardinalDirection.East;
				}
			}
		}	// end if exit cell is on eastern border of maze
		
		// Check if exit cell is on Northern border of maze (excluding corners)
		else if(this.maze.getExitPosition()[1] == 0)
		{
			// Check Northern border of cell for exit
			if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.North)))
			{
				// Exit found, update exit direction
				exitDirection = CardinalDirection.North;
			}
		}
		
		// Check if exit cell is on Southern border of maze (excluding corners)
		else if(this.maze.getExitPosition()[1] == (this.maze.getHeight() - 1))
		{
			// Check Southern border of cell for exit
			if(!(this.maze.hasWall(this.maze.getExitPosition()[0], this.maze.getExitPosition()[1], CardinalDirection.South)))
			{
				// Exit found, update exit direction
				exitDirection = CardinalDirection.South;
			}
		}
		
		// Ensure that an exit has been found using an assert statement
		assert (exitDirection != null);
		
		// Return the exit direction
		return exitDirection;
		
	}	// end private method getMazeExitDirection
	
	/**
	 * This is a private helper method that
	 * rotates the robot to face the exit of
	 * the maze.
	 */
	private void rotateRobotToFaceExit()
	{
		// use an assert statement to check that the robot is NOT already facing the exit
		assert (!this.robotIsFacingExit());
		
		// use another private helper method to obtain the exit direction of the maze
		CardinalDirection exitDirection = this.getMazeExitDirection();
		
		// Check if the maze exit direction is East
		if(exitDirection == CardinalDirection.East)
		{
			// Check if the robot direction is North
			if(robot.getCurrentDirection() == CardinalDirection.North)
			{
				// Robot direction North, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Check if the robot direction is South
			else if(robot.getCurrentDirection() == CardinalDirection.South)
			{
				// Robot direction South, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Robot is facing West
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the maze exit direction is East
		
		// Check if the maze exit direction is West
		if(exitDirection == CardinalDirection.West)
		{
			// Check if the robot direction is North
			if(robot.getCurrentDirection() == CardinalDirection.North)
			{
				// Robot direction North, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Check if the robot direction is South
			else if(robot.getCurrentDirection() == CardinalDirection.South)
			{
				// Robot direction South, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Robot is facing East
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the maze exit direction is West
		
		// Check if the maze exit direction is North
		if(exitDirection == CardinalDirection.North)
		{
			// Check if the robot direction is East
			if(robot.getCurrentDirection() == CardinalDirection.East)
			{
				// Robot direction East, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Check if the robot direction is West
			else if(robot.getCurrentDirection() == CardinalDirection.West)
			{
				// Robot direction West, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Robot is facing South
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the maze exit direction is North
		
		// Check if the maze exit direction is South
		if(exitDirection == CardinalDirection.South)
		{
			// Check if the robot direction is East
			if(robot.getCurrentDirection() == CardinalDirection.East)
			{
				// Robot direction East, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Check if the robot direction is West
			else if(robot.getCurrentDirection() == CardinalDirection.West)
			{
				// Robot direction West, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Robot is facing North
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the maze exit direction is South
		
		// use an assert statement to check that the robot is now facing the exit
		assert (this.robotIsFacingExit());
		
	}	// end private helper method rotateRobotToFaceExit
	
	/**
	 * This is a private helper method that 
	 * determines whether the robot is facing 
	 * its neighboring cell that is closer to 
	 * the exit of the maze or not. 
	 * @return - true if the robot object is facing its neighboring cell that is closer to 
	 * the exit of the maze, false otherwise
	 * @throws Exception if robot's current position is outside the maze
	 */
	private boolean robotIsFacingCellCloserToExit() throws Exception
	{
		// Use a private helper method to identify the direction of the closer cell
		CardinalDirection directionOfCloserCell = this.getCellCloserToExitDirection();
		
		// Now check if robot is facing directionOfCloserCell 
		if(this.robot.getCurrentDirection() == directionOfCloserCell)
		{
			// Robot is facing directionOfCloserCell, return true
			return true;
		}
		// Robot is not facing directionOfCloserCell
		else
		{
			// return false
			return false;
		}
	}	// end private method robotIsFacingCellCloserToExit
	
	/**
	 * This is a private helper method that
	 * identifies and returns the direction 
	 * of the neighboring cell closer to the 
	 * exit of the maze, in relation to the cell
	 * the robot is currently standing on.
	 * @return - the cardinal direction of the neighboring cell closer to the exit.
	 * @throws Exception if robot's current position is outside the maze
	 */
	private CardinalDirection getCellCloserToExitDirection() throws Exception
	{	
		// create a CardinalDirection to keep track of the direction of the cell closer to exit
		CardinalDirection cellCloserToExitDirection = null;
		
		// create an int[] to store the coordinates of the cell closer to the exit
		int[] neighborCloserToExit = this.maze.getNeighborCloserToExit(this.robot.getCurrentPosition()[0], this.robot.getCurrentPosition()[1]);
		
		// create an int[] to store the coordinates of the cell the robot is standing on
		int[] robotLocation = this.robot.getCurrentPosition();
		
		// Check if neighborCloserToExit is North of robotLocation
		if((neighborCloserToExit[0] == robotLocation[0]) && (neighborCloserToExit[1] == (robotLocation[1] - 1)))
		{
			// Neighbor is to the North, update cellCloserToExitDirection
			cellCloserToExitDirection = CardinalDirection.North;
		}
		
		// Check if neighborCloserToExit is South of robotLocation
		if((neighborCloserToExit[0] == robotLocation[0]) && (neighborCloserToExit[1] == (robotLocation[1] + 1)))
		{
			// Neighbor is to the South, update cellCloserToExitDirection
			cellCloserToExitDirection = CardinalDirection.South;
		}
		
		// Check if neighborCloserToExit is East of robotLocation
		if((neighborCloserToExit[0] == (robotLocation[0] + 1)) && (neighborCloserToExit[1] == robotLocation[1]))
		{
			// Neighbor is to the East, update cellCloserToExitDirection
			cellCloserToExitDirection = CardinalDirection.East;
		}
		
		// Check if neighborCloserToExit is West of robotLocation
		if((neighborCloserToExit[0] == (robotLocation[0] - 1)) && (neighborCloserToExit[1] == robotLocation[1]))
		{
			// Neighbor is to the West, update cellCloserToExitDirection
			cellCloserToExitDirection = CardinalDirection.West;
		}
		
		// use an assert statement to check that the direction of the neighboring cell closer to exit was found
		assert (cellCloserToExitDirection != null);
		
		return cellCloserToExitDirection;
	}	// end private method getCellCloserToExitDirection()
	
	/**
	 * This is a private helper method that
	 * rotates the robot to face the neighboring
	 * cell that is closer to the exit of the maze.
	 * @throws Exception if robot's current position is outside the maze
	 */
	private void rotateRobotToFaceCellCloserToExit() throws Exception
	{
		// use an assert statement to check that the robot is NOT already facing the neighboring cell that is closer to the exit
		//assert (!this.robotIsFacingCellCloserToExit());
		
		// use another private helper method to obtain the direction of the neighboring cell that is closer to the exit
		CardinalDirection cellCloserToExitDirection = this.getCellCloserToExitDirection();
		
		// Check if the cellCloserToExitDirection is East
		if(cellCloserToExitDirection == CardinalDirection.East)
		{
			// Check if the robot direction is North
			if(robot.getCurrentDirection() == CardinalDirection.North)
			{
				// Robot direction North, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Check if the robot direction is South
			else if(robot.getCurrentDirection() == CardinalDirection.South)
			{
				// Robot direction South, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Robot is facing West
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the cellCloserToExitDirection is East
		
		// Check if the cellCloserToExitDirection is West
		else if(cellCloserToExitDirection == CardinalDirection.West)
		{
			// Check if the robot direction is North
			if(robot.getCurrentDirection() == CardinalDirection.North)
			{
				// Robot direction North, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Check if the robot direction is South
			else if(robot.getCurrentDirection() == CardinalDirection.South)
			{
				// Robot direction South, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Robot is facing East
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the cellCloserToExitDirection is West
		
		// Check if the cellCloserToExitDirection is North
		else if(cellCloserToExitDirection == CardinalDirection.North)
		{
			// Check if the robot direction is East
			if(robot.getCurrentDirection() == CardinalDirection.East)
			{
				// Robot direction East, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Check if the robot direction is West
			else if(robot.getCurrentDirection() == CardinalDirection.West)
			{
				// Robot direction West, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Robot is facing South
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the cellCloserToExitDirection is North
		
		// Check if the cellCloserToExitDirection is South
		else if(cellCloserToExitDirection == CardinalDirection.South)
		{
			// Check if the robot direction is East
			if(robot.getCurrentDirection() == CardinalDirection.East)
			{
				// Robot direction East, rotate to the left
				robot.rotate(Turn.LEFT);
			}
			// Check if the robot direction is West
			else if(robot.getCurrentDirection() == CardinalDirection.West)
			{
				// Robot direction West, rotate to the right
				robot.rotate(Turn.RIGHT);
			}
			// Robot is facing North
			else 
			{
				// Rotate to the right twice
				robot.rotate(Turn.RIGHT);
				robot.rotate(Turn.RIGHT);
			}
		}	// end if the cellCloserToExitDirection is South
		
		// use an assert statement to check that the robot is now facing the neighboring cell that is closer to the exit
		assert (this.robotIsFacingCellCloserToExit());
	
	}	// end private helper method rotateRobotToFaceCellCloserToExit

}	// end class Wizard 