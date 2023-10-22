package gui;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Checkbox;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import gui.Constants.UserInput;
import gui.Robot.Direction;

class UnreliableSensorTest 
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
	 * of UnreliableSensor correctly 
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
			UnreliableSensor testFrontSensor = new UnreliableSensor(Direction.FORWARD);
			UnreliableSensor testBackSensor = new UnreliableSensor(Direction.BACKWARD);
			UnreliableSensor testLeftSensor = new UnreliableSensor(Direction.LEFT);
			UnreliableSensor testRightSensor = new UnreliableSensor(Direction.RIGHT);
			myUnreliableRobot.addDistanceSensor(testFrontSensor, Direction.FORWARD);
			myUnreliableRobot.addDistanceSensor(testBackSensor, Direction.BACKWARD);
			myUnreliableRobot.addDistanceSensor(testLeftSensor, Direction.LEFT);
			myUnreliableRobot.addDistanceSensor(testRightSensor, Direction.RIGHT);
			testFrontSensor.startFailureAndRepairProcess(4, 2);
			testBackSensor.startFailureAndRepairProcess(4, 2);
			testLeftSensor.startFailureAndRepairProcess(4, 2);
			testRightSensor.startFailureAndRepairProcess(4, 2);
			
			// check if each sensor is alternating between states of operation and in-operation
			if(testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(testFrontSensor.isOperational())
					comparisonFlag2 = false;
			}
			else if(!testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testFrontSensor.isOperational())
					comparisonFlag2 = false;
			}
			
			if(testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(testBackSensor.isOperational())
					comparisonFlag2 = false;
			}
			else if(!testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testBackSensor.isOperational())
					comparisonFlag2 = false;
			}
			
			if(testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(testLeftSensor.isOperational())
					comparisonFlag2 = false;
			}
			else if(!testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testLeftSensor.isOperational())
					comparisonFlag2 = false;
			}
			
			if(testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(testRightSensor.isOperational())
					comparisonFlag2 = false;
			}
			else if(!testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testRightSensor.isOperational())
					comparisonFlag2 = false;
			}
			comparisonFlag2 = false;
		} catch (Exception e) {
			comparisonFlag2 = true;
		}
		assertEquals(true, comparisonFlag2);
		
		
		/////////////////////////////////
		//     Setup Test Option 3:    //
		//   DFS, Wallfollower, 0101   //
		/////////////////////////////////
		
		boolean comparisonFlag3;
		try {
			controllerThirdTestOption.getRobot().startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			UnreliableSensor testFrontSensor = new UnreliableSensor(Direction.FORWARD);
			UnreliableSensor testBackSensor = new UnreliableSensor(Direction.BACKWARD);
			UnreliableSensor testLeftSensor = new UnreliableSensor(Direction.LEFT);
			UnreliableSensor testRightSensor = new UnreliableSensor(Direction.RIGHT);
			myUnreliableRobot.addDistanceSensor(testFrontSensor, Direction.FORWARD);
			myUnreliableRobot.addDistanceSensor(testBackSensor, Direction.BACKWARD);
			myUnreliableRobot.addDistanceSensor(testLeftSensor, Direction.LEFT);
			myUnreliableRobot.addDistanceSensor(testRightSensor, Direction.RIGHT);
			testFrontSensor.startFailureAndRepairProcess(4, 2);
			testBackSensor.startFailureAndRepairProcess(4, 2);
			testLeftSensor.startFailureAndRepairProcess(4, 2);
			testRightSensor.startFailureAndRepairProcess(4, 2);
			
			// check if each sensor is alternating between states of operation and in-operation
			if(testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(testFrontSensor.isOperational())
					comparisonFlag3 = false;
			}
			else if(!testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testFrontSensor.isOperational())
					comparisonFlag3 = false;
			}
			
			if(testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(testBackSensor.isOperational())
					comparisonFlag3 = false;
			}
			else if(!testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testBackSensor.isOperational())
					comparisonFlag3 = false;
			}
			
			if(testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(testLeftSensor.isOperational())
					comparisonFlag3 = false;
			}
			else if(!testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testLeftSensor.isOperational())
					comparisonFlag3 = false;
			}
			
			if(testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(testRightSensor.isOperational())
					comparisonFlag3 = false;
			}
			else if(!testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testRightSensor.isOperational())
					comparisonFlag3 = false;
			}
			comparisonFlag3 = false;
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
	}

	/**
	 * This test determines if the 
	 * stopFailureAndRepairProcess() 
	 * of UnreliableSensor correctly 
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
			controllerSecondTestOption.getRobot().startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			UnreliableSensor testFrontSensor = new UnreliableSensor(Direction.FORWARD);
			UnreliableSensor testBackSensor = new UnreliableSensor(Direction.BACKWARD);
			UnreliableSensor testLeftSensor = new UnreliableSensor(Direction.LEFT);
			UnreliableSensor testRightSensor = new UnreliableSensor(Direction.RIGHT);
			myUnreliableRobot.addDistanceSensor(testFrontSensor, Direction.FORWARD);
			myUnreliableRobot.addDistanceSensor(testBackSensor, Direction.BACKWARD);
			myUnreliableRobot.addDistanceSensor(testLeftSensor, Direction.LEFT);
			myUnreliableRobot.addDistanceSensor(testRightSensor, Direction.RIGHT);
			testFrontSensor.startFailureAndRepairProcess(4, 2);
			testBackSensor.startFailureAndRepairProcess(4, 2);
			testLeftSensor.startFailureAndRepairProcess(4, 2);
			testRightSensor.startFailureAndRepairProcess(4, 2);
			testFrontSensor.stopFailureAndRepairProcess();
			testBackSensor.stopFailureAndRepairProcess();
			testLeftSensor.stopFailureAndRepairProcess();
			testRightSensor.stopFailureAndRepairProcess();
			
			// check if each sensor remains operational and not broken
			if(testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(testFrontSensor.isOperational())
					comparisonFlag2 = true;
			}
			else if(!testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testFrontSensor.isOperational())
					comparisonFlag2 = true;
			}
			
			if(testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(testBackSensor.isOperational())
					comparisonFlag2 = true;
			}
			else if(!testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testBackSensor.isOperational())
					comparisonFlag2 = true;
			}
			
			if(testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(testLeftSensor.isOperational())
					comparisonFlag2 = true;
			}
			else if(!testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testLeftSensor.isOperational())
					comparisonFlag2 = true;
			}
			
			if(testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(testRightSensor.isOperational())
					comparisonFlag2 = true;
			}
			else if(!testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testRightSensor.isOperational())
					comparisonFlag2 = true;
			}
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
			controllerSecondTestOption.getRobot().startFailureAndRepairProcess(Direction.FORWARD, 4, 2);
			UnreliableRobot myUnreliableRobot = new UnreliableRobot();
			UnreliableSensor testFrontSensor = new UnreliableSensor(Direction.FORWARD);
			UnreliableSensor testBackSensor = new UnreliableSensor(Direction.BACKWARD);
			UnreliableSensor testLeftSensor = new UnreliableSensor(Direction.LEFT);
			UnreliableSensor testRightSensor = new UnreliableSensor(Direction.RIGHT);
			myUnreliableRobot.addDistanceSensor(testFrontSensor, Direction.FORWARD);
			myUnreliableRobot.addDistanceSensor(testBackSensor, Direction.BACKWARD);
			myUnreliableRobot.addDistanceSensor(testLeftSensor, Direction.LEFT);
			myUnreliableRobot.addDistanceSensor(testRightSensor, Direction.RIGHT);
			testFrontSensor.startFailureAndRepairProcess(4, 2);
			testBackSensor.startFailureAndRepairProcess(4, 2);
			testLeftSensor.startFailureAndRepairProcess(4, 2);
			testRightSensor.startFailureAndRepairProcess(4, 2);
			testFrontSensor.stopFailureAndRepairProcess();
			testBackSensor.stopFailureAndRepairProcess();
			testLeftSensor.stopFailureAndRepairProcess();
			testRightSensor.stopFailureAndRepairProcess();
			
			// check if each sensor remains operational and not broken
			if(testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(testFrontSensor.isOperational())
					comparisonFlag3 = true;
			}
			else if(!testFrontSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testFrontSensor.isOperational())
					comparisonFlag3 = true;
			}
			
			if(testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(testBackSensor.isOperational())
					comparisonFlag3 = true;
			}
			else if(!testBackSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testBackSensor.isOperational())
					comparisonFlag3 = true;
			}
			
			if(testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(testLeftSensor.isOperational())
					comparisonFlag3 = true;
			}
			else if(!testLeftSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testLeftSensor.isOperational())
					comparisonFlag3 = true;
			}
			
			if(testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(testRightSensor.isOperational())
					comparisonFlag3 = true;
			}
			else if(!testRightSensor.isOperational())
			{
				Thread.sleep(4);
				if(!testRightSensor.isOperational())
					comparisonFlag3 = true;
			}
			comparisonFlag3 = false;
		} catch (Exception e) {
			comparisonFlag3 = true;
		}
		assertEquals(true, comparisonFlag3);
	}
}
