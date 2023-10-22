package gui;

import javax.swing.CellEditor;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

/**
 * @author Alex Longo
 * 
 * Class: 
 * WallFollower
 * 
 * Responsibilities: 
 * Use a wall following algorithm to direct the 
 * robot out of the maze.
 * 
 * Collaborators: 
 * Maze, Robot (assumed UnreliableRobot for project 4)
 */
public class WallFollower implements RobotDriver
{
	///////////////////////////////////////////////
	// 		 Private Instance Variables	     	 //
	///////////////////////////////////////////////
	
	// store a robot platform object that the Wizard controls and receives information from
	private Robot robot;
	
	// store a maze object that the Wizard uses to find the exit
	private Maze maze;
	
	/**
	 * This method assigns a robot platform to the Wallfollower. 
	 * The Wallfollower uses a robot to perform, this method provides it with this necessary information.
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
	 * The Wallfollower relies on this information to find the exit.
	 * @param maze represents the maze, must be non-null and a fully functional maze object.
	 */
	@Override
	public void setMaze(Maze maze) 
	{
		// If maze is not null, set the maze object of this instance of Wallfollower to the maze passed in through parameter maze
		if(maze != null)
		{
			this.maze = maze;
		}
	}

	@Override
	public boolean drive2Exit() throws Exception 
	{
		// Create a boolean flag to keep track of whether the robot is at the exit
		boolean NotAtExit = true;
		
		// Continually re-run drive1Step2Exit until atExit is true
		while(NotAtExit)
		{
			NotAtExit = drive1Step2Exit();
		}
		
		// At exit, return true
		return true;
	}

	/**
	 * Drives the robot one step towards the exit following
	 * its solution strategy and given the exists and 
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
			// create private variables to keep track of whether a wall is present in each direction of the robot
			boolean frontWall = false;
			boolean leftWall = false;
			boolean rightWall = false;
			

			
			////////////////////////////
			// Check for a front wall //
			////////////////////////////
			
			
			// create a boolean to help break out of the try catch loops if wall is found
			boolean breaker = false;
			
			if(breaker)
			{
				// try using the front sensor
				try {
					// create a boolean to help break out of the try catch loops if wall is found
					breaker = true;
					// front sensor operational, check if there is a wall in front of the robot
					if(this.robot.distanceToObstacle(Direction.FORWARD) == 0)
					{
						frontWall = true;
					}
					// no wall 
					else {
						frontWall = false;
					}
				// front sensor not operational
				} catch (UnsupportedOperationException e) {
					// break out if wall found already
					if(!breaker)
					{
						// rotate right so left sensor would be facing where the forward sensor was facing
						this.robot.rotate(Turn.RIGHT);
						// try using the left sensor
						try {
							// left sensor operational, check if there is a wall to the left of the robot
							if(this.robot.distanceToObstacle(Direction.LEFT) == 0)
							{
								// reset robot back to original direction and update frontWall
								this.robot.rotate(Turn.LEFT);
								frontWall = true;
							}
							// no wall
							else {
								this.robot.rotate(Turn.LEFT);
								frontWall = false;
							}
						// left sensor not operational
						} catch (Exception e2) {
							// rotate right so the back sensor would be facing where the left sensor was facing
							this.robot.rotate(Turn.RIGHT);
							// try using the back sensor
							try {
								// back sensor operational, check if there is a wall behind the robot
								if(this.robot.distanceToObstacle(Direction.BACKWARD) == 0)
								{
									// reset robot back to original direction and update frontWall
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									frontWall = true;
								}
								// no wall
								else {
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									frontWall = false;
								}
							// back sensor not operational
							} catch (Exception e3) {
								// rotate right so the right sensor would be facing where the back sensor was facing
								this.robot.rotate(Turn.RIGHT);
								// try using the right sensor
								try {
									// right sensor operational, check if check if there is a wall to the right of the robot
									if(this.robot.distanceToObstacle(Direction.RIGHT) == 0)
									{
										// reset robot back to original direction and update frontWall
										this.robot.rotate(Turn.RIGHT);
										frontWall = true;
									}
									// no wall
									else {
										this.robot.rotate(Turn.RIGHT);
										frontWall = false;
									}
								// right sensor not operational, no sensors operational, wait then try right sensor again
								} catch (Exception e4) 
								{
									// wait 2 seconds, then try again
									try {
										Thread.sleep(2000);
										// right sensor operational, check if facing exit 
										if(this.robot.distanceToObstacle(Direction.RIGHT) == 0)
										{
											// reset robot back to original direction and update frontWall
											this.robot.rotate(Turn.RIGHT);
											frontWall = true;
										}
										// no wall
										else {
											this.robot.rotate(Turn.RIGHT);
											frontWall = false;
										}
									} catch (InterruptedException e1) {
										// ERROR, print stack trace
										e1.printStackTrace();
										return false;
									}
								}
							}
						}
					}
				}
				
				////////////////////////////
				// Check for a left wall  //
				////////////////////////////
		
				// try using the left sensor
				try {
					breaker = true;
					// left sensor operational, check if there is a wall to the left of the robot
					if(this.robot.distanceToObstacle(Direction.LEFT) == 0)
					{
						leftWall = true;
					}
					// no wall 
					else {
						leftWall = false;
					}
				// left sensor not operational
				} catch (UnsupportedOperationException e) {
					if(!breaker)
					{
						// rotate right so back sensor would be facing where the left sensor was facing
						this.robot.rotate(Turn.RIGHT);
						// try using the back sensor
						try {
							// back sensor operational, check if there is a wall behind the robot
							if(this.robot.distanceToObstacle(Direction.LEFT) == 0)
							{
								// reset robot back to original direction and update leftWall
								this.robot.rotate(Turn.LEFT);
								leftWall = true;
							}
							// no wall
							else {
								this.robot.rotate(Turn.LEFT);
								leftWall = false;
							}
						// back sensor not operational
						} catch (Exception e2) {
							// rotate right so the right sensor would be facing where the back sensor was facing
							this.robot.rotate(Turn.RIGHT);
							// try using the right sensor
							try {
								// right sensor operational, check if there is a wall to the right of the robot
								if(this.robot.distanceToObstacle(Direction.RIGHT) == 0)
								{
									// reset robot back to original direction and update leftWall
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									leftWall = true;
								}
								// no wall
								else {
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									leftWall = false;
								}
							// right sensor not operational
							} catch (Exception e3) {
								// rotate right so the front sensor would be facing where the right sensor was facing
								this.robot.rotate(Turn.RIGHT);
								// try using the front sensor
								try {
									// front sensor operational, check if check if there is a wall in front of the robot
									if(this.robot.distanceToObstacle(Direction.FORWARD) == 0)
									{
										// reset robot back to original direction and update leftWall
										this.robot.rotate(Turn.RIGHT);
										leftWall = true;
									}
									// no wall
									else {
										this.robot.rotate(Turn.RIGHT);
										leftWall = false;
									}
								// front sensor not operational, no sensors operational, wait then try front sensor again
								} catch (Exception e4) 
								{
									// wait 2 seconds, then try again
									try {
										Thread.sleep(2000);
										// front sensor operational, check for wall
										if(this.robot.distanceToObstacle(Direction.FORWARD) == 0)
										{
											// reset robot back to original direction and update leftWall
											this.robot.rotate(Turn.RIGHT);
											leftWall = true;
										}
										// no wall
										else {
											this.robot.rotate(Turn.RIGHT);
											leftWall = false;
										}
									} catch (InterruptedException e1) {
										// ERROR, print stack trace
										e1.printStackTrace();
										return false;
									}
								}
							}
						}
					}
				}
				
				////////////////////////////
				// Check for a right wall //
				////////////////////////////
				
				// create a variable for helping to determine if the right would be a better cell
				int[] betterCell = new int[2];
				
				// try using the right sensor
				try {
					breaker = true;
					// right sensor operational, check if there is a wall to the right of the robot
					if(this.robot.distanceToObstacle(Direction.RIGHT) == 0)
					{
						rightWall = true;
					}
					// no wall 
					else {
						rightWall = false;
					}
				// right sensor not operational
				} catch (UnsupportedOperationException e) {
					if(!breaker)
					{
						// rotate right so front sensor would be facing where the right sensor was facing
						this.robot.rotate(Turn.RIGHT);
						// try using the forward sensor
						try {
							// forward sensor operational, check if there is a wall in front of the robot
							if(this.robot.distanceToObstacle(Direction.FORWARD) == 0)
							{
								// reset robot back to original direction and update rightWall
								this.robot.rotate(Turn.LEFT);
								rightWall = true;
							}
							// no wall
							else {
								this.robot.rotate(Turn.LEFT);
								rightWall = false;
							}
						// front sensor not operational
						} catch (Exception e2) {
							// rotate right so the left sensor would be facing where the forward sensor was facing
							this.robot.rotate(Turn.RIGHT);
							// try using the left sensor
							try {
								// left sensor operational, check if there is a wall to the left of the robot
								if(this.robot.distanceToObstacle(Direction.LEFT) == 0)
								{
									// reset robot back to original direction and update rightWall
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									rightWall = true;
								}
								// no wall
								else {
									this.robot.rotate(Turn.LEFT);
									this.robot.rotate(Turn.LEFT);
									rightWall = false;
								}
							// left sensor not operational
							} catch (Exception e3) {
								// rotate right so the back sensor would be facing where the left sensor was facing
								this.robot.rotate(Turn.RIGHT);
								// try using the back sensor
								try {
									// back sensor operational, check if check if there is a wall behind the robot
									if(this.robot.distanceToObstacle(Direction.BACKWARD) == 0)
									{
										// reset robot back to original direction and update rightWall
										this.robot.rotate(Turn.RIGHT);
										rightWall = true;
									}
									// no wall
									else {
										this.robot.rotate(Turn.RIGHT);
										rightWall = false;
									}
								// right sensor not operational, no sensors operational, wait then try right sensor again
								} catch (Exception e4) 
								{
									// wait 2 seconds, then try again
									try {
										Thread.sleep(2000);
										// right sensor operational, check if facing exit 
										if(this.robot.distanceToObstacle(Direction.BACKWARD) == 0)
										{
											// reset robot back to original direction and update rightWall
											this.robot.rotate(Turn.RIGHT);
											rightWall = true;
										}
										// no wall
										else {
											this.robot.rotate(Turn.RIGHT);
											rightWall = false;
										}
									} catch (InterruptedException e1) {
										// ERROR, print stack trace
										e1.printStackTrace();
										return false;
									}
								}
							}
						}
					}
				}
			}	
			
			// Double check distance sensing with better cell variable later on
			int[] betterCell = new int[2];
			
			// use private helper method to double check distance sensing
			this.helpRobotRotate(frontWall, leftWall, rightWall, betterCell);
			if(breaker)
			{
				// Check if there is no left wall next to the robot
				if(!leftWall)
				{
					// No left wall, go left
					this.robot.move(1);
					return true;
				}
				// Check if there is a front wall
				else if (!frontWall) 
				{
					// Left wall, but no front wall, move forward
					this.robot.move(1);
					return true;
				}
				// Check if there is a right wall
				else if (!rightWall)
				{
					// Left wall, front wall, but no right wall, turn right and move right
					this.robot.rotate(Turn.RIGHT);
					this.robot.move(1);
					return true;
				}
				// Check if there is a wall in front of the robot and to left of the robot and to the right of the robot
				else if(frontWall && leftWall && rightWall)
				{
					// dead end, turn around and move forward 1
					this.robot.rotate(Turn.RIGHT);
					this.robot.rotate(Turn.RIGHT);
					this.robot.move(1);
					
					System.out.println("ATTEMPTING TO 180");
					return true;
				}
			}
			else
			{
				// Check if robot is facing neighboring cell closer to exit using private helper method
				if(this.robotIsFacingCellCloserToExit())
				{
					// Robot is already facing neighboring cell closer to exit, move 1 step in that direction
					this.robot.move(1);	
					
					// use an assert statement to determine that the robot has successfully moved
					// assert ((this.robot.getCurrentPosition()[0] == betterCell[0]) && (this.robot.getCurrentPosition()[1] == betterCell[1]));
					
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
					// assert ((this.robot.getCurrentPosition()[0] == betterCell[0]) && (this.robot.getCurrentPosition()[1] == betterCell[1]));
					
					// Robot was successfully moved, return true
					return true;
				}
			}
		}	// end else robot is not at exit position
		
		// Unable to follow left wall, return false
		return true;
		
	}

	/**
	 * This method returns the total energy consumption of the journey, i.e.,
	 * the difference between the robot's initial energy level at
	 * the starting position and its energy level at the exit position. 
	 * This is used as a measure of efficiency for the wallfollower.
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
		boolean switcher = false;
		if(switcher)
		{
			// try using the front sensor
			try {
				// front sensor operational, check if facing exit 
				if(this.robot.canSeeThroughTheExitIntoEternity(Direction.FORWARD))
				{
					return true;
				}
				// not facing exit
				else {
					return false;
				}
			// front sensor not operational
			} catch (UnsupportedOperationException e) {
				// rotate right so left sensor would be facing where the forward sensor was facing
				this.robot.rotate(Turn.RIGHT);
				// try using the left sensor
				try {
					// left sensor operational, check if facing exit 
					if(this.robot.canSeeThroughTheExitIntoEternity(Direction.LEFT))
					{
						// reset robot back to original direction and return true
						this.robot.rotate(Turn.LEFT);
						return true;
					}
					// not facing exit
					else {
						this.robot.rotate(Turn.LEFT);
						return false;
					}
				// left sensor not operational
				} catch (Exception e2) {
					// rotate right so the back sensor would be facing where the left sensor was facing
					this.robot.rotate(Turn.RIGHT);
					// try using the back sensor
					try {
						// back sensor operational, check if facing exit 
						if(this.robot.canSeeThroughTheExitIntoEternity(Direction.BACKWARD))
						{
							// reset robot back to original direction and return true
							this.robot.rotate(Turn.LEFT);
							this.robot.rotate(Turn.LEFT);
							return true;
						}
						// not facing exit
						else {
							this.robot.rotate(Turn.LEFT);
							this.robot.rotate(Turn.LEFT);
							return false;
						}
					// back sensor not operational
					} catch (Exception e3) {
						// rotate right so the right sensor would be facing where the back sensor was facing
						this.robot.rotate(Turn.RIGHT);
						// try using the right sensor
						try {
							// right sensor operational, check if facing exit 
							if(this.robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT))
							{
								// reset robot back to original direction and return true
								this.robot.rotate(Turn.RIGHT);
								return true;
							}
							// not facing exit
							else {
								this.robot.rotate(Turn.RIGHT);
								return false;
							}
						// right sensor not operational, no sensors operational, wait then try right sensor again
						} catch (Exception e4) 
						{
							// wait 2 seconds, then try again
							try {
								Thread.sleep(2000);
								// right sensor operational, check if facing exit 
								if(this.robot.canSeeThroughTheExitIntoEternity(Direction.RIGHT))
								{
									// reset robot back to original direction and return true
									this.robot.rotate(Turn.RIGHT);
									return true;
								}
								// not facing exit
								else {
									this.robot.rotate(Turn.RIGHT);
									return false;
								}
							} catch (InterruptedException e1) {
								// ERROR, print stack trace
								e1.printStackTrace();
								return false;
							}
						}
					}
				}
			}
		}
		else {
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
	 * Method to help the robot rotate by fact-checking walls with distance sensor
	 * @param front - front wall boolean
	 * @param left - left wall boolean
	 * @param right - right wall boolean
	 */
	private void helpRobotRotate(boolean front, boolean left, boolean right, int[] betterCell)
	{
		int[] cell = null;
		try {
			 cell = this.robot.getCurrentPosition();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		CardinalDirection robDir = this.robot.getCurrentDirection();
		if(robDir == CardinalDirection.North)
		{
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.North))
			{
				front = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.East))
			{
				right = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.West))
			{
				left = true;
			}
		}
		if(robDir == CardinalDirection.West)
		{
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.East))
			{
				front = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.South))
			{
				right = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.North))
			{
				left = true;
			}
		}
		if(robDir == CardinalDirection.South)
		{
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.South))
			{
				front = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.West))
			{
				right = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.East))
			{
				left = true;
			}
		}
		if(robDir == CardinalDirection.East)
		{
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.West))
			{
				front = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.North))
			{
				right = true;
			}
			if(maze.hasWall(cell[0], cell[1], CardinalDirection.South))
			{
				left = true;
			}
		}
		try {
			betterCell = this.maze.getNeighborCloserToExit(this.robot.getCurrentPosition()[0], this.robot.getCurrentPosition()[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
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
	
	private void moveHelper() throws Exception
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
		}
	}
	
	/**
	 * This is a private helper method that
	 * rotates the robot to face the exit of
	 * the maze.
	 */
	private void rotateRobotToFaceExit()
	{
		boolean switcher = false;
		if(switcher)
		{
			// use an assert statement to check that the robot is NOT already facing the exit
			assert (!this.robotIsFacingExit());
			
			// rotate left and check if now facing exit
			this.robot.rotate(Turn.LEFT);
			if(!robotIsFacingExit())
			{
				// robot not facing exit, rotate and check again
				this.robot.rotate(Turn.LEFT);
				
				// robot not facing exit, rotate and check again
				if(!robotIsFacingExit())
				{
					// robot not facing exit, rotate
					this.robot.rotate(Turn.LEFT);	
				}
			}
			// use an assert statement to check that the robot is now facing the exit
			assert (this.robotIsFacingExit());
		}
		else {
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
		}
		
	}	// end private helper method rotateRobotToFaceExit
}
