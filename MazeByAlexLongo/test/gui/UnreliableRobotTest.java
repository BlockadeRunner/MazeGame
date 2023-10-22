package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import gui.Constants.UserInput;
import gui.Robot.Direction;

class UnreliableRobotTest 
{
	///////////////////////////////////////////
	// Global control variables for testing  //
	//    different command line inputs		 //
	///////////////////////////////////////////
	
	public static Control controllerSecondTestOption;
	public static Control controllerThirdTestOption;
	
	
	/**
	 * Setup each class with different command line inputs for thorough testing
	 */
	@BeforeAll
	public static void setUp()
	{		
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
	 * This test determines if the 
	 * startFailureAndRepairProcess() 
	 * of UnreliableRobot correctly 
	 * starts the failure and repair
	 * process of the correct distance
	 * sensor.
	 */
	@Test
	void testStartFailureAndRepairProcess() 
	{	
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the method correctly works on all sensors
		boolean comparisonFlag2;
		try {
			controllerSecondTestOption.getRobot().startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			myUnreliableRobot.addDistanceSensor(new UnreliableSensor(Direction.FORWARD), Direction.FORWARD);
			myUnreliableRobot.startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			comparisonFlag2 = false;
		} catch (Exception e) {
			comparisonFlag2 = true;
		}
		assertEquals(true, comparisonFlag2);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the method correctly works on all sensors
		boolean comparisonFlag3;
		try {
			controllerThirdTestOption.getRobot().startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			myUnreliableRobot.addDistanceSensor(new UnreliableSensor(Direction.FORWARD), Direction.FORWARD);
			myUnreliableRobot.startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			comparisonFlag3 = false;
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
	}

	/**
	 * This test determines if the 
	 * stopFailureAndRepairProcess() 
	 * of UnreliableRobot correctly 
	 * stops the failure and repair
	 * process of the correct distance
	 * sensor.
	 */
	@Test
	void testStopFailureAndRepairProcess() 
	{
		/////////////////////////////////
		//     Setup Test Option 2:    //
		// Prim, Wallfollower, 0000    //
		/////////////////////////////////
		
		// Check that the method correctly works on all sensors
		boolean comparisonFlag2;
		try {
			controllerSecondTestOption.getRobot().stopFailureAndRepairProcess(Direction.FORWARD);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			myUnreliableRobot.addDistanceSensor(new UnreliableSensor(Direction.FORWARD), Direction.FORWARD);
			myUnreliableRobot.stopFailureAndRepairProcess(Direction.FORWARD);
			comparisonFlag2 = false;
		} catch (Exception e) {
			comparisonFlag2 = true;
		}
		assertEquals(true, comparisonFlag2);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		// Check that the method correctly works on all sensors
		boolean comparisonFlag3;
		try {
			controllerThirdTestOption.getRobot().stopFailureAndRepairProcess(Direction.FORWARD);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			myUnreliableRobot.addDistanceSensor(new UnreliableSensor(Direction.FORWARD), Direction.FORWARD);
			myUnreliableRobot.stopFailureAndRepairProcess(Direction.FORWARD);
			comparisonFlag3 = false;
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
	}

}
