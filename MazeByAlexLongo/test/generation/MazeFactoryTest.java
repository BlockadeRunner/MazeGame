package generation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import generation.MazeFactory;
import generation.Order.Builder;
import junit.extensions.TestSetup;
import generation.DefaultOrder;
import generation.Maze;

/**
 * A set of basic Junit 5 tests for the MazeFactory class.
 * 
 * @author Alex Longo
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MazeFactoryTest 
{
	/**
	 * Private class variables that will be setup to determine what mazes will be built for testing
	 * 
	 * Skill levels that will be tested:
	 * 0  -  smallest possible maze
	 * 1  -  smallest possible maze with rooms
	 * 3  -  smallest possible maze that is not a square (width != height)
	 * 7  -  scaled up maze, demonstrates that tests still passed when size is scaled up
	 * 
	 * For each skill level tested, perfect and not perfect mazes will be tested
	 * 0  -  no rooms should be possible even if set to not perfect
	 * 1  -  should have rooms for not perfect, no rooms for perfect
	 * 3  -  should have rooms for not perfect, no rooms for perfect
	 * 7  -  should have rooms for not perfect, no rooms for perfect
	 */
	
	// Create a randomized seed
	Random myRnd = new Random();
	private int mySeed = myRnd.nextInt();
	
	//////////////////////////////////////////////////////////////////////////////
	////////////////// Setup Mazes with DFS Builder Algorithm //////////////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	// Setup a test maze: skillLVL=0, Algorithm=DFS, Perfect
	protected Maze testMaze0Perfect = this.completeMazeSetup(0, Builder.DFS, true);
	
	// Setup a test maze: skillLVL=0, Algorithm=DFS, NotPerfect
	protected Maze testMaze0NotPerfect = this.completeMazeSetup(0, Builder.DFS, false);
	
	// Setup a test maze: skillLVL=1, Algorithm=DFS, Perfect
	protected Maze testMaze1Perfect = this.completeMazeSetup(1, Builder.DFS, true);
	
	// Setup a test maze: skillLVL=1, Algorithm=DFS, NotPerfect
	protected Maze testMaze1NotPerfect = this.completeMazeSetup(1, Builder.DFS, false);
	
	// Setup a test maze: skillLVL=3, Algorithm=DFS, Perfect
	protected Maze testMaze3Perfect = this.completeMazeSetup(3, Builder.DFS, true);
	
	// Setup a test maze: skillLVL=3, Algorithm=DFS, NotPerfect
	protected Maze testMaze3NotPerfect = this.completeMazeSetup(3, Builder.DFS, false);
	
	// Setup a test maze: skillLVL=7, Algorithm=DFS, Perfect
	protected Maze testMaze7Perfect = this.completeMazeSetup(7, Builder.DFS, true);
	
	// Setup a test maze: skillLVL=7, Algorithm=DFS, NotPerfect
	protected Maze testMaze7NotPerfect = this.completeMazeSetup(7, Builder.DFS, false);
	
	
	//////////////////////////////////////////////////////////////////////////////
	////////////// Private Helper Methods For Test Maze Setup //////////////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Private helper method to instantiate a new MazeFactory Object for test methods.
	 */
	private MazeFactory createMazeFactory() 
	{
		// return new MazeFactory
		return new MazeFactory();
	}
	
	/**
	 * Private helper method that instantiates a new DefaultOrder Object with the provided parameters for use by test methods.
	 */	
	private DefaultOrder createOrder(int skillLevel, Builder builder, boolean perfect, int seed)
	{
		// return new DefaultOrder
		return new DefaultOrder(skillLevel, builder, perfect, seed);
	}
	
	/**
	 * Private helper method that returns the maze created when the given DefaultOrder is passed to the given MazeFactory, for use in test methods
	 */	
	private Maze createMaze(MazeFactory mazeFactory, DefaultOrder order)
	{
		// Pass the provided DefaultOrder into the provided MazeFactory so that a maze is created based on that order
		mazeFactory.order(order);	
		
		// Wait until the actual maze is delivered before returning the delivered maze
		mazeFactory.waitTillDelivered();
		
		// return new Maze object
		return order.getMaze();
	}
	
	/**
	 * Private helper method that uses the previous helper methods to do a complete maze setup for use in test methods
	 */	
	protected Maze completeMazeSetup(int skillLVL, Builder alg, boolean isPerfect)
	{
		// Use previous helper methods to create a Factory and an Order
		MazeFactory myMazeFactory = this.createMazeFactory();
		DefaultOrder myDefaultOrder = this.createOrder(skillLVL, alg, isPerfect, this.mySeed);
		
		// create and return the test maze
		return this.createMaze(myMazeFactory, myDefaultOrder);
	}
	
	//////////////////////////////////////////////////////////////////////////////
	///////////// Additional Helper Methods for the Main Test Methods //////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Private helper method that returns the number of exits in a provided maze
	 */
	private int numExits(Maze testMaze)
	{
		// Create a Variable to keep track of the number of exits found
		int exitsFound = 0;
		
		// iterate through the top row and bottom row cells of the grid and update the number of exits found
		for (int i = 0; i < testMaze.getWidth(); i++)
		{
			// Check the top row
			if(!testMaze.hasWall(i, 0, CardinalDirection.North))
			{
				exitsFound++;
			}
			
			// Check the bottom row
			if(!testMaze.hasWall(i, testMaze.getHeight()-1, CardinalDirection.South))
			{
				exitsFound++;
			}
			
		}
		
		// iterate through the left-most column and right-most column of cells on the grid and update the number of exits found
		for (int i = 0; i < testMaze.getHeight(); i++)
		{
			// Check the left-most column
			if(!testMaze.hasWall(0, i, CardinalDirection.West))
			{
				exitsFound++;
			}
			
			// Check the right-most column
			if(!testMaze.hasWall(testMaze.getWidth()-1, i, CardinalDirection.East))
			{
				exitsFound++;
			}
			
		}
		
		// return the number of exits found
		return exitsFound;
	}
	
	/**
	 * Private helper method that checks if a solution
	 * can be reached from every position within a 
	 * give maze.
	 */
	private boolean noEnclosedAreas(Maze testMaze)
	{
		// Identify the exit location
		int[] exitPosition = testMaze.getExitPosition();
		
		// Loop through each cell of the Maze
		for (int i = 0; i < testMaze.getWidth(); i++)
		{
			for (int j = 0; j < testMaze.getHeight(); j++)
			{
				// Make sure the current cell position is not the exit
				if(i != exitPosition[0] || j != exitPosition[1])
				{
					// See if there is a neighbor to the current cell that is closer to the exit
					// If there is not a closer neighbor, than an enclosed area is found
					if(testMaze.getNeighborCloserToExit(i, j) == null)
					{
						return false;
					}
				}
			}
		}
		// No enclosed areas found, return true
		return true;
	}
	
	/**
	 * Private helper method that returns the number of
	 * internal walls that are present in a given perfect 
	 * maze.
	 */
	private int numInternalWalls(Maze testMaze)
	{
		// Variable to keep track of the number of internal walls
		int internalWalls = 0;
		
		// Loop through each cell of the Maze
		// For consistency, only check the bottom
		// and right walls of each cell
		for (int j = 0; j < testMaze.getHeight(); j++)
		{
			for (int i = 0; i < testMaze.getWidth(); i++)
			{
				// Special Case: Cell in Bottom/Right Corner
				if((i == testMaze.getWidth()-1) && (j == testMaze.getHeight()-1))
				{
					// Don't check for walls at all
				}
				
				// Special Case: Right-most column of cells
				else if(i == testMaze.getWidth()-1)
				{
					// Only check the bottom side of the cell for a wall
					if(testMaze.hasWall(i, j, CardinalDirection.South))
					{
						internalWalls++;
					}
				}
				
				// Special Case: Bottom row of cells
				else if(j == testMaze.getHeight()-1)
				{
					// Only check the right side of the cell for a wall
					if(testMaze.hasWall(i, j, CardinalDirection.East))
					{
						internalWalls++;
					}
				}	
				
				// Regular Case
				else
				{
					// Check the right side of the cell for a wall
					if(testMaze.hasWall(i, j, CardinalDirection.East))
					{
						internalWalls++;
					}
					// Check the bottom side of the cell for a wall
					if(testMaze.hasWall(i, j, CardinalDirection.South))
					{
						internalWalls++;
					}
				}
			}
		}
		return internalWalls;
	}
	
	/**
	 * Private helper method that checks if 
	 * a given maze has no rooms (i.e. is a
	 * Perfect maze).
	 */
	private boolean noRooms(Maze testMaze)
	{
		// Loop through each cell of the Maze
		for (int i = 0; i < testMaze.getWidth(); i++)
		{
			for (int j = 0; j < testMaze.getHeight(); j++)
			{
				// Check if the current cell is part of a room
				if(testMaze.isInRoom(i, j))
				{
					// Room found, return false
					return false;
				}
			}
		}
		// No rooms found, return true
		return true;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	/////// Actual Test Methods to Test the Correctness of Generated Mazes /////// 
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Test method for a maze produced by MazeFactory
	 * 
	 * Testing for maze correctness:
	 * A correct maze needs to have only one exit
	 * 
	 * This test determines if there is only
	 * one exit to the maze
	 */	
	@Test
	void testOnlyOneExit() 
	{
		// Call helper method to identify the number of exits in testMaze0Perfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze0Perfect));
		
		// Call helper method to identify the number of exits in testMaze0NotPerfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze0NotPerfect));
		
		// Call helper method to identify the number of exits in testMaze1Perfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze1Perfect));

		// Call helper method to identify the number of exits in testMaze1NotPerfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze1NotPerfect));
		
		// Call helper method to identify the number of exits in testMaze3Perfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze3Perfect));
		
		// Call helper method to identify the number of exits in testMaze3NotPerfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze3NotPerfect));
		
		// Call helper method to identify the number of exits in testMaze7Perfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze7Perfect));

		// Call helper method to identify the number of exits in testMaze7NotPerfect
		// Check that there is only one exit		
		assertEquals(1, this.numExits(testMaze7NotPerfect));	
	}
	
	/**
	 * Test method for a maze produced by MazeFactory
	 * 
	 * Testing for maze correctness:
	 * A correct maze needs to have an exit that 
	 * can be reached from anywhere in the maze.
	 * 
	 * This test determines if the maze exit is 
	 * reachable from every position within the
	 * maze.
	 */	
	@Test
	void testExitReachableFromAnywhere() 
	{
		// Call helper method to identify if enclosed areas exist in testMaze0Perfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze0Perfect));
		
		// Call helper method to identify if enclosed areas exist in testMaze0NotPerfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze0NotPerfect));
		
		// Call helper method to identify if enclosed areas exist in testMaze1Perfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze1Perfect));

		// Call helper method to identify if enclosed areas exist in testMaze1NotPerfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze1NotPerfect));
		
		// Call helper method to identify if enclosed areas exist in testMaze3Perfect
		// Check that no enclosed areas exist	
		assertEquals(true, this.noEnclosedAreas(testMaze3Perfect));
		
		// Call helper method to identify if enclosed areas exist in testMaze3NotPerfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze3NotPerfect));
		
		// Call helper method to identify if enclosed areas exist in testMaze7Perfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze7Perfect));

		// Call helper method to identify if enclosed areas exist in testMaze7NotPerfect
		// Check that no enclosed areas exist		
		assertEquals(true, this.noEnclosedAreas(testMaze7NotPerfect));
	}
	
	/**
	 * Test method for a maze produced by MazeFactory
	 * 
	 * Testing for maze correctness:
	 * The number of internal walls 'i' in a correct,
	 * perfect maze, in relation to the number of cells 
	 * 'c' is:
	 * i = [floored] (sqrt(c)-1)^2
	 * 
	 * This test determines if the maze has the correct
	 * number of internal walls.
	 */	
	@Test
	void testNumInternalWalls() 
	{
		// Call helper method to identify the number of internal walls in testMaze0Perfect
		// Check that actual number of internals walls matches the expected result	
		assertEquals(Math.floor(Math.pow((Math.sqrt((testMaze0Perfect.getWidth() * testMaze0Perfect.getHeight())) - 1), 2)), this.numInternalWalls(testMaze0Perfect));
		
		// Call helper method to identify the number of internal walls in testMaze1Perfect
		// Check that actual number of internals walls matches the expected result	
		assertEquals(Math.floor(Math.pow((Math.sqrt((testMaze1Perfect.getWidth() * testMaze1Perfect.getHeight())) - 1), 2)), this.numInternalWalls(testMaze1Perfect));
		
		// Call helper method to identify the number of internal walls in testMaze3Perfect
		// Check that actual number of internals walls matches the expected result	
		assertEquals(Math.floor(Math.pow((Math.sqrt((testMaze3Perfect.getWidth() * testMaze3Perfect.getHeight())) - 1), 2)), this.numInternalWalls(testMaze3Perfect));
		
		// Call helper method to identify the number of internal walls in testMaze7Perfect
		// Check that actual number of internals walls matches the expected result	
		assertEquals(Math.floor(Math.pow((Math.sqrt((testMaze7Perfect.getWidth() * testMaze7Perfect.getHeight())) - 1), 2)), this.numInternalWalls(testMaze7Perfect));
	}
	
	/**
	 * Test method for a maze produced by MazeFactory
	 * 
	 * Testing for maze correctness:
	 * A perfect maze has no rooms.
	 * 
	 * This test determines if a maze that is supposed
	 * to be perfect actually has no rooms.
	 */	
	@Test
	void testPerfectMazeNoRooms() 
	{
		// Call helper method to identify if there are any rooms present in testMaze0Perfect
		// Check that there are no rooms
		assertEquals(true, this.noRooms(testMaze0Perfect));
		
		// Call helper method to identify if there are any rooms present in testMaze1Perfect
		// Check that there are no rooms
		assertEquals(true, this.noRooms(testMaze1Perfect));

		// Call helper method to identify if there are any rooms present in testMaze3Perfect
		// Check that there are no rooms
		assertEquals(true, this.noRooms(testMaze3Perfect));

		// Call helper method to identify if there are any rooms present in testMaze7Perfect
		// Check that there are no rooms
		assertEquals(true, this.noRooms(testMaze7Perfect));
	}
	
	/**
	 * Test method for a maze produced by MazeFactory
	 * 
	 * Testing for maze correctness:
	 * A not-perfect maze has rooms.
	 * 
	 * This test determines if a maze that is supposed
	 * to be not-perfect actually has rooms.
	 */	
	@Test
	void testNotPerfectMazeHasRooms() 
	{
		// Call helper method to identify if there are any rooms present in testMaze0NotPerfect
		// Check that there are no rooms because a maze with a skill level of 0 
		// is a special case that has no rooms no matter what
		assertEquals(true, this.noRooms(testMaze0NotPerfect));
		
		// Call helper method to identify if there are any rooms present in testMaze1NotPerfect
		// Check that there are rooms
		assertEquals(false, this.noRooms(testMaze1NotPerfect));

		// Call helper method to identify if there are any rooms present in testMaze3NotPerfect
		// Check that there are rooms
		assertEquals(false, this.noRooms(testMaze3NotPerfect));

		// Call helper method to identify if there are any rooms present in testMaze7NotPerfect
		// Check that there are rooms
		assertEquals(false, this.noRooms(testMaze7NotPerfect));
	}
}
