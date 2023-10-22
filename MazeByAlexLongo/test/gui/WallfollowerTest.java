package gui;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.ModuleLayer.Controller;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import gui.Constants.UserInput;

class WallfollowerTest 
{
	///////////////////////////////////////////
	// Global control variables for testing  //
	//    different command line inputs		 //
	///////////////////////////////////////////
	
	public static Control controllerFirstTestOption;
	public static Control controllerSecondTestOption;
	public static Control controllerThirdTestOption;
	
	
	/**
	 * Setup each class with different command line inputs for thorough testing
	 */
	@BeforeAll
	public static void setUp()
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		controllerFirstTestOption = new Control();
		controllerFirstTestOption.start();
		
		String[] inputFirstOption = new String[6];
		inputFirstOption[0] = "-g";
		inputFirstOption[1] = "Boruvka";
		inputFirstOption[2] = "-d";
		inputFirstOption[3] = "Wallfollower";
		inputFirstOption[4] = "-r";
		inputFirstOption[5] = "1111";
	
		controllerFirstTestOption.handleCommandLineInput(inputFirstOption);
		controllerFirstTestOption.handleKeyboardInput(UserInput.START, 0);
		
		while(!(controllerFirstTestOption.currentState instanceof StateWinning))
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		controllerSecondTestOption = new Control();
		controllerSecondTestOption.start();
		
		String[] inputSecondOption = new String[6];
		inputSecondOption[0] = "-g";
		inputSecondOption[1] = "Prim";
		inputSecondOption[2] = "-d";
		inputSecondOption[3] = "Wallfollower";
		inputSecondOption[4] = "-r";
		inputSecondOption[5] = "0000";
	
		controllerSecondTestOption.handleCommandLineInput(inputSecondOption);
		controllerSecondTestOption.handleKeyboardInput(UserInput.START, 1);
		
		while(!(controllerSecondTestOption.currentState instanceof StateWinning))
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		controllerThirdTestOption = new Control();
		controllerThirdTestOption.start();
		
		String[] inputThirdOption = new String[6];
		inputThirdOption[0] = "-g";
		inputThirdOption[1] = "DFS";
		inputThirdOption[2] = "-d";
		inputThirdOption[3] = "Wallfollower";
		inputThirdOption[4] = "-r";
		inputThirdOption[5] = "0101";
	
		controllerThirdTestOption.handleCommandLineInput(inputThirdOption);
		controllerThirdTestOption.handleKeyboardInput(UserInput.START, 2);
		
		while(!(controllerThirdTestOption.currentState instanceof StateWinning))
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}	// end before all setup
	
	/**
	 * This test determines whether the setRobot()
	 * function of Wallfollower correctly sets 
	 * up the robot according to the specified 
	 * command line input.
	 */
	@Test
	void testSetRobot() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the robot was correctly set to be a reliable robot
		assertEquals(true, controllerFirstTestOption.robot instanceof ReliableRobot);
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try a reliable robot
		ReliableRobot myReliableRobot1 = new ReliableRobot();
		controllerFirstTestOption.setRobot(myReliableRobot1);
		controllerFirstTestOption.getDriver().setRobot(myReliableRobot1);
		assertEquals(myReliableRobot1, controllerFirstTestOption.getRobot());
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try an unreliable robot
		ReliableRobot myUnreliableRobot1 = new UnreliableRobot();
		controllerFirstTestOption.setRobot(myUnreliableRobot1);
		controllerFirstTestOption.getDriver().setRobot(myUnreliableRobot1);
		assertEquals(myUnreliableRobot1, controllerFirstTestOption.getRobot());
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the robot was correctly set to be a reliable robot
		assertEquals(true, controllerSecondTestOption.robot instanceof ReliableRobot);
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try a reliable robot
		ReliableRobot myReliableRobot2 = new ReliableRobot();
		controllerSecondTestOption.setRobot(myReliableRobot2);
		controllerSecondTestOption.getDriver().setRobot(myReliableRobot2);
		assertEquals(myReliableRobot2, controllerSecondTestOption.getRobot());
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try an unreliable robot
		ReliableRobot myUnreliableRobot2 = new UnreliableRobot();
		controllerSecondTestOption.setRobot(myUnreliableRobot2);
		controllerSecondTestOption.getDriver().setRobot(myUnreliableRobot2);
		assertEquals(myUnreliableRobot2, controllerSecondTestOption.getRobot());
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the robot was correctly set to be a reliable robot
		assertEquals(true, controllerThirdTestOption.robot instanceof ReliableRobot);
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try a reliable robot
		ReliableRobot myReliableRobot3 = new ReliableRobot();
		controllerThirdTestOption.setRobot(myReliableRobot3);
		controllerThirdTestOption.getDriver().setRobot(myReliableRobot3);
		assertEquals(myReliableRobot3, controllerThirdTestOption.getRobot());
		
		// Check that the setRobot method sets the robot to the robot passed in
		// Try an unreliable robot
		ReliableRobot myUnreliableRobot3 = new UnreliableRobot();
		controllerThirdTestOption.setRobot(myUnreliableRobot3);
		controllerThirdTestOption.getDriver().setRobot(myUnreliableRobot3);
		assertEquals(myUnreliableRobot3, controllerThirdTestOption.getRobot());
		
	}

	/**
	 * This test determines whether the setMaze()
	 * function of Wallfollower correctly sets 
	 * up the maze according to the specified 
	 * command line input.
	 */
	@Test
	void testSetMaze() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the setMaze method sets the maze to the maze passed in
		boolean comparisonFlag1;
		try {
			Maze myMaze1 = new MazeContainer();
			myMaze1 = controllerFirstTestOption.getMaze();
			comparisonFlag1 = false;
		} catch (Exception e) {
			comparisonFlag1 = true;
		}
		assertEquals(true, comparisonFlag1);

		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the setMaze method sets the maze to the maze passed in
		boolean comparisonFlag2;
		try {
			Maze myMaze2 = new MazeContainer();
			myMaze2 = controllerSecondTestOption.getMaze();
			comparisonFlag2 = false;
		} catch (Exception e) {
			comparisonFlag2 = true;
		}
		assertEquals(true, comparisonFlag2);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the setMaze method sets the maze to the maze passed in
		boolean comparisonFlag3;
		try {
			Maze myMaze3 = new MazeContainer();
			myMaze3 = controllerThirdTestOption.getMaze();
			comparisonFlag3 = false;
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
	}

	/**
	 * This test determines whether the drive2Exit()
	 * method of Wallfollower correctly guides the 
	 * robot out of the maze by following the left
	 * wall.
	 */
	@Test
	void testDrive2Exit() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit
		assertEquals(true, controllerFirstTestOption.currentState instanceof StateWinning);
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit
		assertEquals(true, controllerSecondTestOption.currentState instanceof StateWinning);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit
		assertEquals(true, controllerThirdTestOption.currentState instanceof StateWinning);
	}

	/**
	 * This test determines whether the drive1Step2Exit()
	 * method of Wallfollower correctly guides the 
	 * robot one step closer to getting out of the maze 
	 * by following the left wall.
	 */
	@Test
	void testDrive1Step2Exit() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit using drive1Step2Exit many times
		boolean comparisonFlag;
		try {
			comparisonFlag = controllerFirstTestOption.getCurrentPosition() != controllerFirstTestOption.getMaze().getExitPosition();
		} catch (Exception e) {
			comparisonFlag = true;
		}
		assertEquals(true, comparisonFlag);
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit using drive1Step2Exit many times
		boolean comparisonFlag2;
		try {
			comparisonFlag2 = controllerSecondTestOption.getCurrentPosition() != controllerSecondTestOption.getMaze().getExitPosition();
		} catch (Exception e) {
			comparisonFlag2 = true;
		}
		assertEquals(true, comparisonFlag2);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the robot was successfully guided along the left wall to the exit using drive1Step2Exit many times
		boolean comparisonFlag3;
		try {
			comparisonFlag3 = controllerThirdTestOption.getCurrentPosition() != controllerThirdTestOption.getMaze().getExitPosition();
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
		
	}

	/**
	 * This test determines whether the getEnergyConsumption()
	 * method of Wallfollower correctly returns the energy
	 * consumed in traversing the maze until the exit was 
	 * reached. 
	 */
	@Test
	void testGetEnergyConsumption() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the energy consumed over the journey
		assertEquals(true, controllerFirstTestOption.getDriver().getEnergyConsumption() < 3000);
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the energy consumed over the journey
		assertEquals(true, controllerSecondTestOption.getDriver().getEnergyConsumption() < 3000);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the energy consumed over the journey
		assertEquals(true, controllerThirdTestOption.getDriver().getEnergyConsumption() < 3000);
	}

	/**
	 * This test determines whether the getPathLength()
	 * method of Wallfollower correctly returns the number
	 * of cells traversed by the robot to get to the exit.
	 */
	@Test
	void testGetPathLength() 
	{
		/////////////////////////////////
		//     Setup Test Option 1:    //
		// Boruvka, Wallfollower, 1111 //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the number of cells traversed over the journey
		assertEquals(true, controllerFirstTestOption.getDriver().getPathLength() > 2);
		
		
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the number of cells traversed over the journey
		assertEquals(true, controllerSecondTestOption.getDriver().getPathLength() > 4);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the driver successfully keeps track of the number of cells traversed over the journey
		assertEquals(true, controllerThirdTestOption.getDriver().getPathLength() > 6);
	}

}
