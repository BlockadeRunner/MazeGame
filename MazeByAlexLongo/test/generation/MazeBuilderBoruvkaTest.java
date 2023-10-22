package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import generation.Order.Builder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MazeBuilderBoruvkaTest extends MazeFactoryTest
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
	
	//////////////////////////////////////////////////////////////////////////////
	//////////////// Setup Mazes with Boruvka Builder Algorithm ////////////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	// Setup a test maze: skillLVL=0, Algorithm=DFS, Perfect
	private Maze testMaze0PerfectBoruvka = this.completeMazeSetup(0, Builder.Boruvka, true);
	
	// Setup a test maze: skillLVL=0, Algorithm=DFS, NotPerfect
	private Maze testMaze0NotPerfectBoruvka = this.completeMazeSetup(0, Builder.Boruvka, false);
	
	// Setup a test maze: skillLVL=1, Algorithm=DFS, Perfect
	private Maze testMaze1PerfectBoruvka = this.completeMazeSetup(1, Builder.Boruvka, true);
	
	// Setup a test maze: skillLVL=1, Algorithm=DFS, NotPerfect
	private Maze testMaze1NotPerfectBoruvka = this.completeMazeSetup(1, Builder.Boruvka, false);
	
	// Setup a test maze: skillLVL=3, Algorithm=DFS, Perfect
	private Maze testMaze3PerfectBoruvka = this.completeMazeSetup(3, Builder.Boruvka, true);
	
	// Setup a test maze: skillLVL=3, Algorithm=DFS, NotPerfect
	private Maze testMaze3NotPerfectBoruvka = this.completeMazeSetup(3, Builder.Boruvka, false);
	
	// Setup a test maze: skillLVL=7, Algorithm=DFS, Perfect
	private Maze testMaze7PerfectBoruvka = this.completeMazeSetup(7, Builder.Boruvka, true);
	
	// Setup a test maze: skillLVL=7, Algorithm=DFS, NotPerfect
	private Maze testMaze7NotPerfectBoruvka = this.completeMazeSetup(7, Builder.Boruvka, false);
	
	
	//////////////////////////////////////////////////////////////////////////////
	/////////////////// Private Helper Methods For Tests ///////////////////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Private helper method that checks if
	 * a given int item is in a given integer
	 * array.
	 * @param array - Array that the item could be in.
	 * @param item - Integer item you are looking for in the specified array.
	 * @return - Returns true if item found in specified array, false otherwise.
	 */
	private boolean contains(int[] array, int item)
	{
		// Loop through the entire array
		for(int k = 0; k < array.length; k++)
		{
			// Check if each item in the array is equal to the item in question
			if(array[k] == item)
			{
				// found equal items, return true
				return true;
			}
		}
		// item not found in array, return false
		return false;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	/////// Call the Superclass's Blackbox Tests for the Correctness of any Generated Maze /////// 
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	void testOnlyOneExit() 
	{
		// Update all test mazes to ones build by Boruvka's algorithm
		testMaze0Perfect = testMaze0PerfectBoruvka;
		testMaze0NotPerfect = testMaze0NotPerfectBoruvka;
		testMaze1Perfect = testMaze1PerfectBoruvka;
		testMaze1NotPerfect = testMaze1NotPerfectBoruvka;
		testMaze3Perfect = testMaze3PerfectBoruvka;
		testMaze3NotPerfect = testMaze3NotPerfectBoruvka;
		testMaze7Perfect = testMaze7PerfectBoruvka;
		testMaze7NotPerfect = testMaze7NotPerfectBoruvka;
		super.testOnlyOneExit();
	}
	
	@Test
	void testExitReachableFromAnywhere() 
	{
		// Update all test mazes to ones build by Boruvka's algorithm
		testMaze0Perfect = testMaze0PerfectBoruvka;
		testMaze0NotPerfect = testMaze0NotPerfectBoruvka;
		testMaze1Perfect = testMaze1PerfectBoruvka;
		testMaze1NotPerfect = testMaze1NotPerfectBoruvka;
		testMaze3Perfect = testMaze3PerfectBoruvka;
		testMaze3NotPerfect = testMaze3NotPerfectBoruvka;
		testMaze7Perfect = testMaze7PerfectBoruvka;
		testMaze7NotPerfect = testMaze7NotPerfectBoruvka;
		super.testExitReachableFromAnywhere();
	}
	
	@Test
	void testNumInternalWalls() 
	{
		// Update all test mazes to ones build by Boruvka's algorithm
		testMaze0Perfect = testMaze0PerfectBoruvka;
		testMaze0NotPerfect = testMaze0NotPerfectBoruvka;
		testMaze1Perfect = testMaze1PerfectBoruvka;
		testMaze1NotPerfect = testMaze1NotPerfectBoruvka;
		testMaze3Perfect = testMaze3PerfectBoruvka;
		testMaze3NotPerfect = testMaze3NotPerfectBoruvka;
		testMaze7Perfect = testMaze7PerfectBoruvka;
		testMaze7NotPerfect = testMaze7NotPerfectBoruvka;
		super.testNumInternalWalls();
	}
	
	@Test
	void testPerfectMazeNoRooms() 
	{
		// Update all test mazes to ones build by Boruvka's algorithm
		testMaze0Perfect = testMaze0PerfectBoruvka;
		testMaze0NotPerfect = testMaze0NotPerfectBoruvka;
		testMaze1Perfect = testMaze1PerfectBoruvka;
		testMaze1NotPerfect = testMaze1NotPerfectBoruvka;
		testMaze3Perfect = testMaze3PerfectBoruvka;
		testMaze3NotPerfect = testMaze3NotPerfectBoruvka;
		testMaze7Perfect = testMaze7PerfectBoruvka;
		testMaze7NotPerfect = testMaze7NotPerfectBoruvka;
		super.testPerfectMazeNoRooms();
	}
	
	@Test
	void testNotPerfectMazeHasRooms() 
	{
		// Update all test mazes to ones build by Boruvka's algorithm
		testMaze0Perfect = testMaze0PerfectBoruvka;
		testMaze0NotPerfect = testMaze0NotPerfectBoruvka;
		testMaze1Perfect = testMaze1PerfectBoruvka;
		testMaze1NotPerfect = testMaze1NotPerfectBoruvka;
		testMaze3Perfect = testMaze3PerfectBoruvka;
		testMaze3NotPerfect = testMaze3NotPerfectBoruvka;
		testMaze7Perfect = testMaze7PerfectBoruvka;
		testMaze7NotPerfect = testMaze7NotPerfectBoruvka;
		super.testNotPerfectMazeHasRooms();
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	//////////// Whitebox Test Methods Specific to MazeBuilder Boruvka /////////// 
	//////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Test method for getEdgeWeight() method in 
	 * MazeBuilderBoruvka
	 * 
	 * Testing for getEdgeWeight() correctness:
	 *  - This must return the same value if called
	 * repeatedly on the same wall board.
	 *  - This must return the same value for the
	 * wallboards shared by two adjacent cells.
	 * 
	 * This test determines if the getEdgeWeight()
	 * method is functions perfectly.
	 */	
	@Test
	void testGetEdgeWeightMethod()
	{	
		// Setup a new instance of MazeBuilderBoruvka for testing its methods
		MazeBuilderBorukva testBuilderBorukva = new MazeBuilderBorukva();
		
		// Setup a maze of skillLVL=2
		testBuilderBorukva.width = 15;
		testBuilderBorukva.height = 15;
		
		/*
		 * TEST 1: Does calling getEdgeWeight repeatedly
		 * on the same wallboard continually return the
		 * same value?
		 */
		int firstCall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.North);
		int secondCall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.North);
		int thirdCall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.North);
		assertEquals(firstCall, secondCall);
		assertEquals(firstCall, thirdCall);
		assertEquals(secondCall, thirdCall);
		
		/*
		 * TEST 2: Does calling getEdgeWeight on the wall of
		 * this cell that borders the adjacent cell return the 
		 * same value as calling getEdgeWeight on the wall of 
		 * the adjacent cell that borders this cell?
		 */
		
		// Test Direction North:
		int cellNorthWall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.North);
		int adjCellSouthWall = testBuilderBorukva.getEdgeWeight(3, 2, CardinalDirection.South);
		assertEquals(cellNorthWall, adjCellSouthWall);
		
		// Test Direction South:
		int cellSouthWall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.South);
		int adjCellNorthWall = testBuilderBorukva.getEdgeWeight(3, 4, CardinalDirection.North);
		assertEquals(cellSouthWall, adjCellNorthWall);
		
		// Test Direction East:
		int cellEastWall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.East);
		int adjCellWestWall = testBuilderBorukva.getEdgeWeight(4, 3, CardinalDirection.West);
		assertEquals(cellEastWall, adjCellWestWall);
		
		// Test Direction East:
		int cellWestWall = testBuilderBorukva.getEdgeWeight(3, 3, CardinalDirection.West);
		int adjCellEastWall = testBuilderBorukva.getEdgeWeight(2, 3, CardinalDirection.East);
		assertEquals(cellWestWall, adjCellEastWall);
	}
	
	/**
	 * Test method for edge weights assigned in
	 * MazeBuilderBoruvka
	 * 
	 * Testing for edge weight assignment correctness:
	 * All edges should be randomly generated. This 
	 * means that generating multiple mazes should yield
	 * mazes with edges that have different weights
	 * every time. 
	 * 
	 * This test determines if the edge weights
	 * assigned in MazeBuilderBoruvka are randomly
	 * generated.
	 */	
	@Test
	void testAllEdgesAreRandom()
	{
		// Setup new instances of MazeBuilderBoruvka for testing
		MazeBuilderBorukva testBuilderBorukva = new MazeBuilderBorukva();
		MazeBuilderBorukva testBuilderBorukva2 = new MazeBuilderBorukva();
		MazeBuilderBorukva testBuilderBorukva3 = new MazeBuilderBorukva();
		
		// Setup mazes of skillLVL=2
		testBuilderBorukva.width = 15;
		testBuilderBorukva.height = 15;
		testBuilderBorukva2.width = 15;
		testBuilderBorukva2.height = 15;
		testBuilderBorukva3.width = 15;
		testBuilderBorukva3.height = 15;
		
		// Setup boolean flags to keep track of whether the edge weights are different each time and thus random each time
		boolean builder1And2AreDifferent = false;
		boolean builder1And3AreDifferent = false;
		boolean builder2And3AreDifferent = false;
		
		// get and store the edge weights for testBuilderBoruvka
		// Loop through each cell and direction of each cell
		// Loop through vertically (y position)
		for(int h = 0; h < testBuilderBorukva.height; h++)
		{
			// Loop through horizontally (x position)
			for(int w = 0; w < testBuilderBorukva.width; w++)
			{
				// Loop through directionally
				for(int d = 0; d < 4; d++)
				{
					// For northern direction
					if(d == 0)
					{
						// check if builder1 and builder2 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.North) != testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.North))
						{
							// They are different, update boolean flag
							builder1And2AreDifferent = true;
						}
						// check if builder1 and builder 3 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.North) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.North))
						{
							// They are different, update boolean flag
							builder1And3AreDifferent = true;
						}
						// check if builder2 and builder 3 are different
						if(testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.North) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.North))
						{
							// They are different, update boolean flag
							builder2And3AreDifferent = true;
						}
					}
					// For southern direction
					if(d == 1)
					{
						// check if builder1 and builder2 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.South) != testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.South))
						{
							// They are different, update boolean flag
							builder1And2AreDifferent = true;
						}
						// check if builder1 and builder 3 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.South) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.South))
						{
							// They are different, update boolean flag
							builder1And3AreDifferent = true;
						}
						// check if builder2 and builder 3 are different
						if(testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.South) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.South))
						{
							// They are different, update boolean flag
							builder2And3AreDifferent = true;
						}
					}
					// For eastern direction
					if(d == 2)
					{
						// check if builder1 and builder2 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.East) != testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.East))
						{
							// They are different, update boolean flag
							builder1And2AreDifferent = true;
						}
						// check if builder1 and builder 3 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.East) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.East))
						{
							// They are different, update boolean flag
							builder1And3AreDifferent = true;
						}
						// check if builder2 and builder 3 are different
						if(testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.East) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.East))
						{
							// They are different, update boolean flag
							builder2And3AreDifferent = true;
						}
					}
					// For western direction
					if(d == 3)
					{
						// check if builder1 and builder2 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.West) != testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.West))
						{
							// They are different, update boolean flag
							builder1And2AreDifferent = true;
						}
						// check if builder1 and builder 3 are different
						if(testBuilderBorukva.getEdgeWeight(w, h, CardinalDirection.West) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.West))
						{
							// They are different, update boolean flag
							builder1And3AreDifferent = true;
						}
						// check if builder2 and builder 3 are different
						if(testBuilderBorukva2.getEdgeWeight(w, h, CardinalDirection.West) != testBuilderBorukva3.getEdgeWeight(w, h, CardinalDirection.West))
						{
							// They are different, update boolean flag
							builder2And3AreDifferent = true;
						}
					}
					
				}	// end for loop: direction
				
				// To optimize efficiency in running test cases, break out of the loop if all three boolean flags are true
				if(builder1And2AreDifferent && builder1And3AreDifferent && builder2And3AreDifferent)
				{
					break;
				}
			}	// end for loop: x position
			
			// To optimize efficiency in running test cases, break out of the loop if all three boolean flags are true
			if(builder1And2AreDifferent && builder1And3AreDifferent && builder2And3AreDifferent)
			{
				break;
			}
		}	// end for loop y position
		
	// Check if the builders' edge weights are different and thus edge weights are random
	assertEquals(true, builder1And2AreDifferent);
	assertEquals(true, builder1And3AreDifferent);
	assertEquals(true, builder2And3AreDifferent);
			
	}	// end testAllEdgesAreRandom()
	
	
	/**
	 * Test method for edge weights assigned in
	 * MazeBuilderBoruvka
	 * 
	 * Testing for edge weight assignment correctness:
	 * All edges should be unique. This means that 
	 * no two edges of internal walls within a maze
	 * should have the same edge weight. 
	 * 
	 * This test determines if the edge weights
	 * assigned in MazeBuilderBoruvka are unique.
	 */		
	@Test
	void testAllEdgesAreUnique()
	{
		// Setup a new instance of MazeBuilderBoruvka for testing
		MazeBuilderBorukva testBuilderBorukva = new MazeBuilderBorukva();

		// Setup a maze of skillLVL=2
		testBuilderBorukva.width = 15;
		testBuilderBorukva.height = 15;
		
		// Variable to keep track of previously seen edge weights
		int[] previousEdgeWeights = new int[(testBuilderBorukva.height * testBuilderBorukva.width * 4)];
		int indexInPreviousEdgeWeights = 0;
		
		// boolean flag to keep track of whether edges are unique or not
		boolean edgesAreUnique = true;
		
		// Loop through each cell of the Maze
		// For consistency, only check the bottom
		// and right walls of each cell
		for (int y = 0; y < testBuilderBorukva.height; y++)
		{
			for (int x = 0; x < testBuilderBorukva.width; x++)
			{
				// Special Case: Cell in Bottom/Right Corner
				if((x == testBuilderBorukva.width-1) && (y == testBuilderBorukva.height-1))
				{
					// Don't check for walls at all
				}
				
				// Special Case: Right-most column of cells
				else if(x == testBuilderBorukva.width-1)
				{
					// Only check the bottom side wall of the cell
					if(this.contains(previousEdgeWeights, testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.South)))
					{
						edgesAreUnique = false;
					}
					// if edge is unique, add edge examined to previously seen edges
					else 
					{
						previousEdgeWeights[indexInPreviousEdgeWeights] = testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.South);
						// increment index
						indexInPreviousEdgeWeights++;
					}
				}
				
				// Special Case: Bottom row of cells
				else if(y == testBuilderBorukva.height-1)
				{
					// Only check the right side wall of the cell
					if(this.contains(previousEdgeWeights, testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.East)))
					{
						edgesAreUnique = false;
					}
					// if edge is unique, add edge examined to previously seen edges
					else 
					{
						previousEdgeWeights[indexInPreviousEdgeWeights] = testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.East);
						// increment index
						indexInPreviousEdgeWeights++;
					}
				}	
				
				// Regular Case
				else
				{
					// Check the right side wall of the cell
					if(this.contains(previousEdgeWeights, testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.East)))
					{
						edgesAreUnique = false;
					}
					// Check the bottom side wall of the cell
					if(this.contains(previousEdgeWeights, testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.South)))
					{
						edgesAreUnique = false;
					}
					// if edges are unique, add edges examined to previously seen edges
					if(edgesAreUnique)
					{
						previousEdgeWeights[indexInPreviousEdgeWeights] = testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.East);
						// increment index
						indexInPreviousEdgeWeights++;
						
						previousEdgeWeights[indexInPreviousEdgeWeights] = testBuilderBorukva.getEdgeWeight(x, y, CardinalDirection.South);
						// increment index
						indexInPreviousEdgeWeights++;
					}
				}
				// To optimize efficiency, break out of the loop if edges are not unique
				if(!edgesAreUnique)
				{
					break;
				}
				
			}	// end for loop: x position
			
			// To optimize efficiency, break out of the loop if edges are not unique
			if(!edgesAreUnique)
			{
				break;
			}
			
		}	// end for loop: y position
		
		assertEquals(true, edgesAreUnique);
		
	}	// end testAllEdgesAreUnique
	

}	// end class: MazeBuilderBoruvkaTest
