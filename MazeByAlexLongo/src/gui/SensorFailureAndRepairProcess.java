package gui;

public class SensorFailureAndRepairProcess implements Runnable
{
	// private variable to store the unreliable sensor that will be broken and repaired by the sensor failure and repair thread
	private UnreliableSensor unreliableSensor;
	
	// private variable to keep track of the operation time of the unreliable sensor in question
	private int unreliableSensorOperationTime;
	
	// private variable to keep track of the time taken to repair the unreliable sensor in question
	private int unreliableSensorRepairTime;
	
	// constructor for setting up the broken sensor repair 
	public SensorFailureAndRepairProcess(UnreliableSensor unreliableSensor, int unreliableSensorOperationTime, int unreliableSensorRepairTime) 
	{
		this.unreliableSensor = unreliableSensor;
		this.unreliableSensorOperationTime = unreliableSensorOperationTime;
		this.unreliableSensorRepairTime = unreliableSensorRepairTime;
	}
	
	
	/**
	 * Thread for continually running the operation, breaking, and repair processes for the sensor in question
	 */
	@Override
	public void run() 
	{
		// Create a variable to allow a while loop that runs continuously
		boolean runContinually = true;
		
		// Attempt to run the failure and repair process
		try 
		{
			// run the while loop continuously to the keep the process constant
			while(runContinually) 
			{
				// Wait of the given operation time of the sensor
				Thread.sleep(unreliableSensorOperationTime);
				
				// set the sensor to non-operational
				unreliableSensor.setBroken();
				
				// wait the time necessary for repair
				Thread.sleep(unreliableSensorRepairTime);
				// set the sensor to operational
				unreliableSensor.setWorking();
			}
		} 
		
		// Interruption to failure and repair cycle
		catch (InterruptedException e) 
		{
			// End the repair process, and as per the project specs, set operational to true
			unreliableSensor.setWorking();
			return;
		}	
	}	
}