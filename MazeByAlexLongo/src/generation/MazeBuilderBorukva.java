package generation;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;


/**
 * A maze generation class that builds a maze using Boruvka's algorithm.
 * 
 * @author Alex Longo
 *
 */


public class MazeBuilderBorukva extends MazeBuilder implements Runnable
{
	/**
	 * The logger is used to track execution and report issues.
	 */
	private static final Logger LOGGER = Logger.getLogger(MazeBuilderBorukva.class.getName());
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////// Private Instance Variables for use by the whole class /////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
	
    /**
	 * Declare a 3D ArrayList ('edgeWeights') for storing the edge weights of each wall board
	 * The array will store the values as follows:
	 * edgeWeights[x position][y position][direction]
	 * 
	 * The directions will be represented as integers as follows:
	 * North = 0
	 * South = 1
	 * East = 2
	 * West = 3
	 */
	private int[][][] edgeWeights;
	
	// Boolean flag to keep track of whether edge weights have been assigned already or not
	boolean alreadyAssigned = false;
	
	// Keep track of the number of cells in the maze
	private int numCells;
	
	// Random Object for random integer generation for edge weights
	private Random myRnd;
	
	// Constant that stores infinity (largest possible int) for the weight of border walls
	private final int INFINITY = Integer.MAX_VALUE;
	
	
	// Constructor
	public MazeBuilderBorukva()
	{
		// Call the superclass constructor
		super();
		LOGGER.config("Using Boruvka's algorithm to generate maze.");		
	}
	
	
	/**
	 * This method generates pathways into the maze by using Borukva's algorithm to generate a minimum spanning tree for an undirected graph.
	 * The cells are the nodes of the graph and the spanning tree. An edge represents that one can move from one cell to an adjacent cell, 
	 * AKA a doorway between cells. So an edge implies that its nodes are adjacent cells in the maze and that there is no wallboard separating 
	 * these cells in the maze. 
	 */
	@Override
	protected void generatePathways()
	{
		/*
		 * The main idea of Boruvka's is to grow multiple minimal spanning trees (MSTs).
		 * These trees are grown by connecting a node (cell) with the cell next to it 
		 * that has the least expensive edge weight. After the initial connection of nodes
		 * the first connected trees of nodes are formed. Next, each tree is connected and 
		 * merged with the tree next to it that has the least expensive edge weight. This 
		 * process is repeated until only 1, single MST remains. The maze is now complete
		 * with no enclosed spaces.
		 */
		
		//////////////////////////////////////////////////////////////////
		//                   		PART 1:						 		//
		//  Initialize all instance variables and setup weighted edges	//
		////////////////////////////////////////////////////////////////// 
		
		// Initialize numCells to the correct number of cells in the maze
		numCells = width * height;
		
		// Initialize edgeWeights to be an array of the correct length based on numCells
		edgeWeights = new int[width][height][4];
		
		// Initialize myRnd as a new Random object
		myRnd = new Random();
		
		// Initialize edgeWeights using helper method
		this.assignEdgeWeights(edgeWeights, numCells);
		
		// Change alreadyAssigned to true
		alreadyAssigned = true;
		
		// System.out.println("Passed Part 1");
		
		
		
		////////////////////////////////////////////////////////////
		//                        PART 2:					      //
		//        Now its time to create the initial MSTs		  //
		//////////////////////////////////////////////////////////// 
		  			
		// Loop through the entire maze cell by cell
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				// identify which wallboard of this cell has the lowest edgeWeight using a helper method
				CardinalDirection lowestWeightDirection = this.getDirectionOfEdgeWithLowestWeight(x, y, edgeWeights);
				
				// check if the wall is still there (can be destroyed)
				if(floorplan.hasWall(x, y, lowestWeightDirection))
				{
					// Wall is still there, destroy it
					Wallboard curWallBoard = new Wallboard(x, y, lowestWeightDirection);
					floorplan.deleteWallboard(curWallBoard);							
				}	
			}	// end x for loop
		}	// end y for loop
		
		// System.out.println("Passed Part 2");
		
		
		
		//////////////////////////////////////////////////////////////////
		//                   		PART 3:						 		//
		//  Now its time to analyze the maze and identify which tree	//
		//  each cell belongs to.										//
		////////////////////////////////////////////////////////////////// 
		
		// Create an ArrayList ('listOfTrees') to store each tree
		ArrayList<ArrayList<int[]>> listOfTrees = new ArrayList<ArrayList<int[]>>();
		
		// Call helper method to identify and store trees in listOfTrees
		this.identifyTrees(listOfTrees);
		
		// System.out.println("Passed Part 3");
		

		
		//////////////////////////////////////////////////////////////////
		//                   		PART 4:						 		//
		//  Now its time to identify the lowest edge weight for each 	//
		//  tree in the maze.    										//
		////////////////////////////////////////////////////////////////// 
		
		// Setup and array list to hold important information about each tree
		ArrayList<int[]> treesAndTheirLowestWeightEdgesArrayList = new ArrayList<int[]>();
		
		// Loop through each tree
		for(int i = 0; i < listOfTrees.size(); i++)
		{
			// store the lowest valid edge weight for each tree obtained from helper function 
			int val = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[3];
			
			// create an integer array for each tree to store all relevant information
			int[] everything = new int[5];
			
			// store the index of the tree
			everything[0] = i;
			
			// store the lowest valid edge weight for the tree
			everything[1] = val;
			
			// store the x pos of cell containing lowest valid edge weight
			everything[2] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[0];
			
			// store the y pos of cell containing lowest valid edge weight
			everything[3] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[1];
			
			// store the direction of the lowest valid edge weight
			everything[4] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[2];
			
			treesAndTheirLowestWeightEdgesArrayList.add(everything);
		}
		
		// System.out.println("Passed Part 4");
		
	
		
		//////////////////////////////////////////////////////////////////
		//                   		PART 5:						 		//
		//  Now its time to merge trees until there is only one 		//
		//  remaining.    												//
		//////////////////////////////////////////////////////////////////
		
		// Merge trees until there is only one tree remaining
		while(listOfTrees.size() > 1)
		{	
			// LOOP THROUGH EACH TREE IN LIST OF TREES, DESTROY THE LOWEST VALID EDGE WEIGHT OF EACH TREE
			for(int tree = 0; tree < listOfTrees.size(); tree++)
			{
				// Break out of this merge and destroy loop when there is only one tree remaining
				if(listOfTrees.size() < 2)
				{
					break;
				}
				
				// Obtain the location of the wall board that will be deleted from the current tree
				// (aka the wall with the lowest valid edge weight)
				
				// Obtain x position or wall board
				int x = treesAndTheirLowestWeightEdgesArrayList.get(tree)[2];
				
				// Obtain y position or wall board
				int y = treesAndTheirLowestWeightEdgesArrayList.get(tree)[3];
				
				// Obtain numerical direction or wall board
				int dir = treesAndTheirLowestWeightEdgesArrayList.get(tree)[4];
				
				// Convert numerical direction to cardinal direction
				CardinalDirection trueDirection = CardinalDirection.North;
				if(dir == 1)
				{
					trueDirection = CardinalDirection.South; 
				}
				if(dir == 2)
				{
					trueDirection = CardinalDirection.East; 
				}
				if(dir == 3)
				{
					trueDirection = CardinalDirection.West; 
				}
				
				// Find out what other tree that wall board belongs to
				int indexOfSecondTree = 0;
				
				// Loop through the list of trees
				for(int tree2 = 0; tree2 < listOfTrees.size(); tree2++)
				{
					// Check to make sure we are not looking at the same tree
					if(tree != tree2)
					{
						// Loop through each cell in each tree
						for(int cell = 0; cell < listOfTrees.get(tree2).size(); cell++)
						{	
							// if the wall board to be deleted has a Northern direction, check if the cell to the north is in tree2
							if (trueDirection == CardinalDirection.North) 
							{
								// check if the cell to the north is in tree2
								if ((listOfTrees.get(tree2).get(cell)[0] == treesAndTheirLowestWeightEdgesArrayList.get(tree)[2]) && (listOfTrees.get(tree2).get(cell)[1] == (treesAndTheirLowestWeightEdgesArrayList.get(tree)[3] - 1))) 
								{
									// Found cell to the north in tree2
									indexOfSecondTree = tree2;
								}
							}
							// if the wall board to be deleted has a Southern direction, check if the cell to the south is in tree2
							if (trueDirection == CardinalDirection.South) 
							{
								// check if the cell to the south is in tree2
								if ((listOfTrees.get(tree2).get(cell)[0] == treesAndTheirLowestWeightEdgesArrayList.get(tree)[2]) && (listOfTrees.get(tree2).get(cell)[1] == (treesAndTheirLowestWeightEdgesArrayList.get(tree)[3] + 1))) 
								{
									// Found cell to the south in tree2
									indexOfSecondTree = tree2;
								}
							}
							// if the wall board to be deleted has an Eastern direction, check if the cell to the east is in tree2
							if (trueDirection == CardinalDirection.East) 
							{
								// check if the cell to the east is in tree2
								if ((listOfTrees.get(tree2).get(cell)[0] == (treesAndTheirLowestWeightEdgesArrayList.get(tree)[2] + 1)) && (listOfTrees.get(tree2).get(cell)[1] == treesAndTheirLowestWeightEdgesArrayList.get(tree)[3])) 
								{
									// Found cell to the east in tree2
									indexOfSecondTree = tree2;
								}
							}
							// if the wall board to be deleted has a Western direction, check if the cell to the west is in tree2
							if (trueDirection == CardinalDirection.West) 
							{
								// check if the cell to the east is in tree2
								if ((listOfTrees.get(tree2).get(cell)[0] == (treesAndTheirLowestWeightEdgesArrayList.get(tree)[2] - 1)) && (listOfTrees.get(tree2).get(cell)[1] == treesAndTheirLowestWeightEdgesArrayList.get(tree)[3])) 
								{
									// Found cell to the west in tree2
									indexOfSecondTree = tree2;
								}
							}
						}	// end inner for loop: looping through the cells in a tree
					}	// end if (not examining the same tree)
				}	// end outer for loop: looping through each tree
				
				/*
				 * Now that the the other tree that the wall board belongs to has been found,
				 * delete that wall board and merge the two trees.
				 */	
				
				// Create a wall board object with the location information of the wall to be deleted
				Wallboard curWallBoard = new Wallboard(x, y, trueDirection);
				
				// Delete that wall
				floorplan.deleteWallboard(curWallBoard);
				
				
				////////// NOW MERGE THE TWO TREES //////////
					
				// For every cell in the second of the two merging trees, add it to the first of the two merging trees
				for(int c = 0; c < listOfTrees.get(indexOfSecondTree).size(); c++)
				{
					listOfTrees.get(tree).add(listOfTrees.get(indexOfSecondTree).get(c));
				}
				
				// Remove the second of the two merging trees from listOfTrees
				listOfTrees.remove(indexOfSecondTree);
				
				// Update lowest edgeWeights of trees using helper method
				treesAndTheirLowestWeightEdgesArrayList = updateLowestEdgeWeightsOfTrees(listOfTrees);
				
				// DECREMENT TREE INDEX VARIABLE: 
				// Since two trees have merged, all tree indexes have shifted down 1
				tree--;
				
			}	// end looping through each tree to destroy the lowest edge weight
		
		}	// end while loop
		
		// System.out.println("Passed Part 5");
		
	}	// end generate pathways method
	
	/**
	 * This method returns the edgeWeight of the wall
	 * at the given position and direction.
	 * @param x - The x coordinate of the desired wallboard edge weight.
	 * @param y	- The y coordinate of the desired wallboard edge weight.
	 * @param cd - The direction of the desired wallboard edge weight.
	 * @return - The edge weight of the wall at the supplied location.
	 */
	public int getEdgeWeight(int x, int y, CardinalDirection cd)
	{
		// Check if edge weights have already been assigned, if they haven't, then assign them
		if(alreadyAssigned == false)
		{
			// Initialize numCells to the correct number of cells in the maze
			numCells = width * height;
			
			// Initialize edgeWeights to be an array of the correct length based on numCells
			edgeWeights = new int[width][height][4];
			
			// Initialize myRnd as a new Random object
			myRnd = new Random();
			
			// Initialize edgeWeights using helper method
			this.assignEdgeWeights(edgeWeights, numCells);
			
			// Change alreadyAssigned to true
			alreadyAssigned = true;
		}
		
		/*
		 * Translate all cardinal directions into numerical directions
		 * to retrieve the exact edge weight from storage.
		 * Then return that exact edge weight.
		 */	
		int retInt = 0;
		if(cd == CardinalDirection.North)
		{
			retInt = edgeWeights[x][y][0];
		}
		else if(cd == CardinalDirection.South)
		{
			retInt = edgeWeights[x][y][1];
		}
		else if(cd == CardinalDirection.East)
		{
			retInt = edgeWeights[x][y][2];
		}
		else if(cd == CardinalDirection.West)
		{
			retInt = edgeWeights[x][y][3];
		}
		return retInt;
	}
	
	/**
	 * Private helper method that assigns all the unique
	 * edge weights and stores them in a 3D array
	 * @param edgeWeights - 3D array for edge weight storage.
	 * @param numCells - Number of cells in the maze
	 */
	private void assignEdgeWeights(int[][][] edgeWeights, int numCells)
	{
		// Setup an array ('previousEdgeWeights') to hold the previously set edge weights
		// This helps with making sure all edge weights are unique
		int[] previousEdgeWeights = new int[numCells * 4];
		
		// Create counter to keep track of index for previousEdgeWeights
		int indexInPreviousEdgeWeights = 0;
		
		// Loop through each cell in edgeWeights
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				// Loop through each directional edge for each cell in edgeWeights
				for(int d = 0; d < 4; d++)
				{
					// check if this wall is a northern border wall
					if (y == 0 && d == 0)
					{
						// Border wall identified, set weight to INFINITY
						edgeWeights[x][y][d] = INFINITY;
					}
					
					// check if this wall is a southern border wall
					else if ((y == height - 1) && d == 1)
					{
						// Border wall identified, set weight to INFINITY
						edgeWeights[x][y][d] = INFINITY;
					}
					
					// check if this wall is an Eastern (right side) border wall
					else if ((x == width - 1) && d == 2)
					{
						// Border wall identified, set weight to INFINITY
						edgeWeights[x][y][d] = INFINITY;
					}
					
					// check if this wall is a Western (left side) border wall
					else if ((x == 0) && d == 3)
					{
						// Border wall identified, set weight to INFINITY
						edgeWeights[x][y][d] = INFINITY;
					}
					
					// This is not a border wall, assign a random edge weight or obtain already existing edge
					else 
					{
						// check if this is a northern wall
						if(d == 0)
						{
							// set edge weight to pre-existing edge
							edgeWeights[x][y][d] = edgeWeights[x][y-1][1];
						}
						
						// check if this is a western wall
						else if(d == 3)
						{
							// set edge weight to pre-existing edge
							edgeWeights[x][y][d] = edgeWeights[x-1][y][2];
						}
						
						// This must be an southern or eastern wall
						else 
						{
							// create a new, random, and unique edgeWeight
							int newEdgeWeight = myRnd.nextInt();
							
							// check if the new edge weight already exists
							if (this.contains(previousEdgeWeights, newEdgeWeight))
							{
								// keep creating new random edge weights until a unique one is generated
								while(this.contains(previousEdgeWeights, newEdgeWeight))
								{
									newEdgeWeight = myRnd.nextInt();
								}
							}
							
							// assign the new edge weight value to the edge that the code is currently standing on
							edgeWeights[x][y][d] = newEdgeWeight;
							
							// add the newly assigned edge weight to previousEdgeWeights to avoid duplicates
							previousEdgeWeights[indexInPreviousEdgeWeights] = newEdgeWeight;
						
						} 	// end else: eastern or southern wall
					}	// end else: not border wall	
				}	// end for loop: loop through each directional edge/wall of a cell 
			
			// increment index counter
			indexInPreviousEdgeWeights++;
			
			}	// end for loop: looping through each cell horizontally
		}	// end for loop: looping through each cell vertically	
	}	// end method: assignEdgeWeights
	
	/**
	 * Private helper method that returns the direction of the
	 * wall with lowest edge weight of the cell at the 
	 * given coordinates.	
	 * @param x - The x position of the cell.
	 * @param y - The y position of the cell.
	 * @param edgeWeights - 3D array containing all edge weights of all cells in the maze.
	 * @return - The CardinalDirection of the edge with the lowest weight.
	 */
	private CardinalDirection getDirectionOfEdgeWithLowestWeight(int x, int y, int[][][] edgeWeights)
	{
		// keep track of the lowest weight using int 'lowestWeight'
		int lowestWeight;
		
		// keep track of the direction with lowest weight using 'directionOfLowestWeight'
		CardinalDirection directionOfLowestWeight = null;
			
		// update lowest weight
		lowestWeight = edgeWeights[x][y][0];
		
		// update directionOfLowestWeight
		directionOfLowestWeight = CardinalDirection.North;
		
		// Check if South has lower edge weight
		if (edgeWeights[x][y][1] < lowestWeight)
		{
			// South has lower edge weight, update variables
			lowestWeight = edgeWeights[x][y][1];
			directionOfLowestWeight = CardinalDirection.South;
		}
		
		// Check if East has lower edge weight
		if (edgeWeights[x][y][2] < lowestWeight)
		{
			// East has lower edge weight, update variables
			lowestWeight = edgeWeights[x][y][2];
			directionOfLowestWeight = CardinalDirection.East;
		}
		
		// Check if West has lower edge weight
		if (edgeWeights[x][y][3] < lowestWeight)
		{
			// West has lower edge weight, update variables
			lowestWeight = edgeWeights[x][y][3];
			directionOfLowestWeight = CardinalDirection.West;
		}
		
		// return directionOfLowestWeight
		return directionOfLowestWeight;
	}
	
	/**
	 * Private helper method that identifies the MSTs 
	 * in the floor plan and adds them to an ArrayList
	 * @param listOfTrees - An ArrayList to store the trees that are found.
	 */
	private void identifyTrees(ArrayList<ArrayList<int[]>> listOfTrees)
	{		
		// Loop through all the cells of the maze
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				// Call the recursive version of this function on each cell
				recIdentifyTrees(x, y, listOfTrees, CardinalDirection.North, true);
			}	// end x for loop
		}	// end y for loop
		
		// Remove any duplicate cells from recursion that are in a tree
		// Loop through the list of trees
		for(int t = 0; t < listOfTrees.size(); t++)
		{
			// Store the cells that have been seen before in an ArrayList
			ArrayList<int[]> seenBefore = new ArrayList<int[]>();
			
			// Loop through each cell in this tree
			for(int c = 0; c < listOfTrees.get(t).size(); c++)
			{
				// Check if this cell is already in the tree
				// Create a boolean flag to identify if this was found before and removed
				boolean found = false;
				
				// Loop through Array list of seen before cells
				for(int q = 0; q < seenBefore.size(); q++)
				{	
					// Check if this cell has been seen before
					if((listOfTrees.get(t).get(c)[0] == seenBefore.get(q)[0]) && (listOfTrees.get(t).get(c)[1] == seenBefore.get(q)[1]))
					{
						// erase this cell copy since it is already in this tree
						listOfTrees.get(t).remove(c);
						
						// set boolean flag found to true
						found = true;
						
						// decrement c if not last cell, because all indexes will shift back one when a cell is removed
						if(c < (listOfTrees.get(t).size()))
						{
							c--;
						}
						
						// tell code to stop searching if match has been found and deleted
						break;
					}
				}
	
				// if it is not already in this tree, then add it to seenBefore and move on
				if(found == false)
				{
					seenBefore.add(listOfTrees.get(t).get(c));
				}	
			}	// end for: loop through each tell in the tree
		}	// end for: loop through each tree
	}	// end identifyTrees method
	
	/**
	 * Private recursive helper method that adds cells to 
	 * their respective MSTs.
	 * @param int cellPosX - X position of cell to be added to tree.
	 * @param int cellPosY - Y position of cell to be added to tree.
	 * @param listOfTrees - ArrayList of Trees that the function will add new trees to.
	 * @param cameFrom - CardinalDirection of the cell that this recursive method previously visited before this cell.
	 * @param isStart - Identifies if this cell is the starting cell of a MST.
	 */
	private void recIdentifyTrees(int cellPosX, int cellPosY, ArrayList<ArrayList<int[]>> listOfTrees, CardinalDirection cameFrom, boolean isStart)
	{		
		// create an index variable to identify the tree that the current cell is in, if it is in a tree
		int indexOfTree = -1;
		
		// Loop through the listOfTrees, checking each tree for the current cell
		for(int i = 0; i < listOfTrees.size(); i++)
		{	
			for(int z = 0; z < listOfTrees.get(i).size(); z++)
			{
				// Check if current cell is in current tree
				if((listOfTrees.get(i).get(z)[0] == cellPosX) && (listOfTrees.get(i).get(z)[1] == cellPosY))
				{
					// Current cell found, update index of tree to be that of the tree that the current cell is in
					indexOfTree = i;
				}
			}
		}
		
		// Check if current cell is NOT in a tree
		if(indexOfTree == -1)
		{
			// Since it is not in a tree, create a tree
			ArrayList<int[]> newTree = new ArrayList<int[]>();
			
			// add current cell to that tree
			int[] newCell = new int[2];
			newCell[0] = cellPosX;
			newCell[1] = cellPosY;
			newTree.add(newCell);
			
			// add that tree to 'listofTrees'
			listOfTrees.add(newTree);
			
			// recur onto this cell again
			recIdentifyTrees(cellPosX, cellPosY, listOfTrees, cameFrom, isStart);
		}
		
		// Check if current cell is in a tree
		else if(indexOfTree != -1)
		{
			
			// Base Case: if current cell is not starting cell and has 3 walls
			if(((!isStart) && (this.numWalls(cellPosX, cellPosY) == 3)) || (!floorplan.isFirstVisit(cellPosX, cellPosY)))
			{
				return;
			}
			
			// Alternate recur case 1: current cell is starting cell
			else if(isStart)
			{
				// Check if Northern Wall Exists
				if(!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.North))
				{
					// Since Northern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX;
					newCell[1] = cellPosY - 1;					
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX, cellPosY-1, listOfTrees, CardinalDirection.South, false);
				}
				
				// Check if Southern Wall Exists
				if(!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.South))
				{
					// Since Southern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX;
					newCell[1] = cellPosY + 1;					
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX, cellPosY+1, listOfTrees, CardinalDirection.North, false);
				}
				
				// Check if Eastern Wall Exists
				if(!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.East))
				{
					// Since Eastern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX + 1;
					newCell[1] = cellPosY;
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX + 1, cellPosY, listOfTrees, CardinalDirection.West, false);
				}
				
				// Check if Western Wall Exists
				if(!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.West))
				{
					// Since Western Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX - 1;
					newCell[1] = cellPosY;
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX-1, cellPosY, listOfTrees, CardinalDirection.East, false);
				}
			}	
			
			// Alternate recur case 2: current cell is not starting cell and has less than 3 walls
			else if(this.numWalls(cellPosX, cellPosY) < 3)
			{
				// Check if Northern Wall Exists and is not where we came from
				if((!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.North)) && (cameFrom != CardinalDirection.North))
				{
					// Since Northern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX;
					newCell[1] = cellPosY - 1;					
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX, cellPosY-1, listOfTrees, CardinalDirection.South, false);
				}
				
				// Check if Southern Wall Exists and is not where we came from
				if((!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.South)) && (cameFrom != CardinalDirection.South))
				{
					// Since Southern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX;
					newCell[1] = cellPosY + 1;					
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX, cellPosY+1, listOfTrees, CardinalDirection.North, false);
				}
				
				// Check if Eastern Wall Exists and is not where we came from
				if((!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.East)) && (cameFrom != CardinalDirection.East))
				{
					// Since Eastern Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX + 1;
					newCell[1] = cellPosY;
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX + 1, cellPosY, listOfTrees, CardinalDirection.West, false);
				}
				
				// Check if Western Wall Exists and is not where we came from
				if((!floorplan.hasWall(cellPosX, cellPosY, CardinalDirection.West)) && (cameFrom != CardinalDirection.West))
				{
					// Since Western Wall Does Not Exist, add adjacent cell to the tree
					int[] newCell = new int[2];
					newCell[0] = cellPosX - 1;
					newCell[1] = cellPosY;
					listOfTrees.get(indexOfTree).add(newCell);
					
					// Set cell current cell as visited
					floorplan.setCellAsVisited(cellPosX, cellPosY);
					
					// now recur onto adjacent cell
					recIdentifyTrees(cellPosX-1, cellPosY, listOfTrees, CardinalDirection.East, false);
				}
			}	// End recur case 2	
		}	// end if current cell is in a tree
	}	// end recursive recIdentifyTrees function
	
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
	
	/**
	 * Private helper method that returns the
	 * number of walls present for the cell at 
	 * the given position
	 * @param x - X position of cell.
	 * @param y - Y position of cell.
	 * @return - The number of walls that cell has.
	 */
	private int numWalls(int x, int y)
	{
		// Counter for keeping track of the number of Walls found
		int numWalls = 0;
		
		// Check for Northern Wall
		if(floorplan.hasWall(x, y, CardinalDirection.North))
		{
			// Wall found, increment wall counter
			numWalls++;
		}
		
		// Check for Southern Wall
		if(floorplan.hasWall(x, y, CardinalDirection.South))
		{
			// Wall found, increment wall counter
			numWalls++;
		}
		
		// Check for Eastern Wall
		if(floorplan.hasWall(x, y, CardinalDirection.East))
		{
			// Wall found, increment wall counter
			numWalls++;
		}
		
		// Check for Western Wall
		if(floorplan.hasWall(x, y, CardinalDirection.West))
		{
			// Wall found, increment wall counter
			numWalls++;
		}
		
		// Return the number of walls found
		return numWalls;
	}
	
	
	/**
	 * Private helper method that returns the location 
	 * and value of the smallest valid (bordering other tree)
	 * edge of a given tree.
	 * @param tree - The tree to be explored.
	 * @param edgeWeight - Storage medium containing all edge weights of the entire maze.
	 * @return - Integer array containing: x position, y position, numerical direction, value of lowest edge weight.
	 */
	private int[] getLocationOfLowestValidEdgeWeight(ArrayList<int[]> tree, int[][][] edgeWeight)
	{
		// Keep track of lowest acceptable edge
		int[] lowestEdgeLocation = new int[4];
		int lowestEdgeVal = INFINITY;
		
		// Loop through every cell in the given tree
		for(int c = 0; c < tree.size(); c++)
		{			
			// check if it has a wall (north)
			if(floorplan.hasWall(tree.get(c)[0], tree.get(c)[1], CardinalDirection.North))
			{
				// check that the wall is not a border wall
				if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][0] != INFINITY)
				{	
					/*
					 * Check if that wall borders a cell in this tree  
					 * by looping through this tree and checking if the 
					 * adjacent cell is in this tree.
					 */
					// Create a boolean flag to keep track of whether the adjacent cell is in this tree
					boolean cellInThisTree = false;
					for(int z = 0; z < tree.size(); z++)
					{
						// check if that wall borders cell in this tree
						if((tree.get(z)[0] == (tree.get(c)[0])) && (tree.get(z)[1] == (tree.get(c)[1] - 1)))
						{
							// cell already in this tree
							cellInThisTree = true;
							break;
						}
					}
					// Wall borders a cell NOT in this tree already
					if(cellInThisTree == false)
					{
						// Adjacent cell not in tree, valid edge possibility, update lowest acceptable edge if less than current lowest edge weight
						if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][0] < lowestEdgeVal)
						{
							lowestEdgeLocation[0] = tree.get(c)[0];
							lowestEdgeLocation[1] = tree.get(c)[1]; 
							lowestEdgeLocation[2] = 0;
							lowestEdgeVal = edgeWeight[tree.get(c)[0]][tree.get(c)[1]][0];	
						}	// end if it contains a lower edge weight
					}	// end if cell not in this tree already
				}	// end if this cell is not a border wall
			}	// end if this cell has a Northern wall
			
			// check if it has a wall (south)
			if(floorplan.hasWall(tree.get(c)[0], tree.get(c)[1], CardinalDirection.South))
			{
				// check that the wall is not a border wall
				if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][1] != INFINITY)
				{
					/*
					 * Check if that wall borders a cell in this tree  
					 * by looping through this tree and checking if the 
					 * adjacent cell is in this tree.
					 */
					// Create a boolean flag to keep track of whether the adjacent cell is in this tree
					boolean cellInThisTree = false;
					for(int z = 0; z < tree.size(); z++)
					{
						// check if that wall borders cell in this tree
						if((tree.get(z)[0] == (tree.get(c)[0])) && (tree.get(z)[1] == (tree.get(c)[1] + 1)))
						{
							// cell already in this tree
							cellInThisTree = true;
							break;
						}
					}
					// wall borders a cell NOT in this tree already
					if(cellInThisTree == false)
					{
						// Adjacent cell not in tree, valid edge possibility, update lowest acceptable edge if less than current lowest edge weight
						if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][1] < lowestEdgeVal)
						{
							lowestEdgeLocation[0] = tree.get(c)[0];
							lowestEdgeLocation[1] = tree.get(c)[1]; 
							lowestEdgeLocation[2] = 1;
							lowestEdgeVal = edgeWeight[tree.get(c)[0]][tree.get(c)[1]][1];	
						}	// end if it contains a lower edge weight
					}	// end if cell not in this tree already
				}	// end if this cell is not a border wall
			}	// end if this cell has a Southern wall
			
			// check if it has a wall (East)
			if(floorplan.hasWall(tree.get(c)[0], tree.get(c)[1], CardinalDirection.East))
			{
				// check that the wall is not a border wall
				if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][2] != INFINITY)
				{
					/*
					 * Check if that wall borders a cell in this tree  
					 * by looping through this tree and checking if the 
					 * adjacent cell is in this tree.
					 */
					// Create a boolean flag to keep track of whether the adjacent cell is in this tree
					boolean cellInThisTree = false;
					for(int z = 0; z < tree.size(); z++)
					{
						// check if that wall borders cell in this tree
						if((tree.get(z)[0] == (tree.get(c)[0] + 1)) && (tree.get(z)[1] == tree.get(c)[1]))
						{
							// cell already in this tree
							cellInThisTree = true;
							break;
						}
					}
					// wall borders a cell NOT in this tree already
					if(cellInThisTree == false)
					{
						// Adjacent cell not in tree, valid edge possibility, update lowest acceptable edge if less than current lowest edge weight
						if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][2] < lowestEdgeVal)
						{
							lowestEdgeLocation[0] = tree.get(c)[0];
							lowestEdgeLocation[1] = tree.get(c)[1]; 
							lowestEdgeLocation[2] = 2;
							lowestEdgeVal = edgeWeight[tree.get(c)[0]][tree.get(c)[1]][2];	
						}	// end if it contains a lower edge weight
					}	// end if cell not in this tree already
				}	// end if this cell is not a border wall
			}	// end if this cell has an eastern wall
			
			// check if it has a wall (West)
			if(floorplan.hasWall(tree.get(c)[0], tree.get(c)[1], CardinalDirection.West))
			{
				// check that the wall is not a border wall
				if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][3] != INFINITY)
				{
					/*
					 * Check if that wall borders a cell in this tree  
					 * by looping through this tree and checking if the 
					 * adjacent cell is in this tree.
					 */
					// Create a boolean flag to keep track of whether the adjacent cell is in this tree
					boolean cellInThisTree = false;
					for(int z = 0; z < tree.size(); z++)
					{
						// check if that wall borders cell in this tree
						if((tree.get(z)[0] == (tree.get(c)[0] - 1)) && (tree.get(z)[1] == tree.get(c)[1]))
						{
							// cell already in this tree
							cellInThisTree = true;
							break;
						}
					}
					// wall borders a cell NOT in this tree already
					if(cellInThisTree == false)
					{
						// Adjacent cell not in tree, valid edge possibility, update lowest acceptable edge if less than current lowest edge weight
						if(edgeWeight[tree.get(c)[0]][tree.get(c)[1]][3] < lowestEdgeVal)
						{
							lowestEdgeLocation[0] = tree.get(c)[0];
							lowestEdgeLocation[1] = tree.get(c)[1]; 
							lowestEdgeLocation[2] = 3;
							lowestEdgeVal = edgeWeight[tree.get(c)[0]][tree.get(c)[1]][3];	
						}	// end if it contains a lower edge weight
					}	// end if cell not in this tree already
				}	// end if this cell is not a border wall
			}	// end if this cell has a western wall
			
		}	// end the search through the tree
		
		// Return the integer array containing the location, direction, and value of the lowest valid edge weight for this tree
		lowestEdgeLocation[3] = lowestEdgeVal;
		return lowestEdgeLocation;
	}
	
	/**
	 * Private helper method that loops through
	 * a given list of trees and identifies and 
	 * returns the location, direction, and value
	 * of each tree's lowest valid edge weight.
	 * @param listOfTrees - An array list of trees (array lists of int arrays) that contains the trees whose lowest edge weight the user needs.
	 * @return - An array list of int arrays containing location, direction, and value of each tree's lowest valid edge weight.
	 */
	private ArrayList<int[]> updateLowestEdgeWeightsOfTrees(ArrayList<ArrayList<int[]>> listOfTrees)
	{
		// Setup and array list to hold important information about each tree
		ArrayList<int[]> treesAndTheirLowestWeightEdgesArrayList = new ArrayList<int[]>();
		
		// Loop through each tree
		for(int i = 0; i < listOfTrees.size(); i++)
		{
			// store the lowest valid edge weight for each tree obtained from helper function 
			int val = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[3];
			
			// create an int array for each tree to store all relevant information
			int[] everything = new int[5];
			
			// store the index of the tree
			everything[0] = i;
			
			// store the lowest valid edge weight for the tree
			everything[1] = val;
			
			// store the x pos of cell containing lowest valid edge weight
			everything[2] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[0];
			
			// store the y pos of cell containing lowest valid edge weight
			everything[3] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[1];
			
			// store the numerical direction of the lowest valid edge weight
			everything[4] = this.getLocationOfLowestValidEdgeWeight(listOfTrees.get(i), edgeWeights)[2];
			
			treesAndTheirLowestWeightEdgesArrayList.add(everything);
			
		}	
		// return the array list containing all the information for each tree
		return treesAndTheirLowestWeightEdgesArrayList;
	}	// end updateLowestEdgeWeightsOfTrees() method
}	// end class MazeBuilderBoruvka
