void main(){
	// (User turns power on)
	indicatePower();
	
	// Constant loop that waits for user to start game
	while(true){
		if(startButtonPressed){
		startGame();
	}
}

//---------------------------------------------------- MAIN FUNCTIONS ---------------------------------------------------------

//*********************************************************************
// Indicates that the machine is on and ready for game start		  *
//*********************************************************************
void indicatePower(){
	// Power LEDs or something
}
//*********************************************************************
// Begins and operates the main game							      *
//*********************************************************************
void startGame(){
	bool mistake = false, success = false;
	int elapsedTimeMS = 0, timeWindow = 2000ms, points = 0, startRollAngle = readRollSensor();

	while(!mistake){
		// Speaker gives random commmand to user
		Command givenCommand = generateCommand();
		shoutCommand(givenCommand);
		
		// Prepping vars for user time window
		elapsedTimeMS = 0;
		startRollAngle = readRollSensor();
		
		// User must respond with correct command in time window
		while(elapsedTimeMS != timeWindow){
			
			// Check if user made an input.  If so, judge it
			if(readUserInput(startRollAngle) != null){
				if(readUserInput == givenCommand){
					points++;
					speaker("Nice!"); // Or another cue for good input
				}
				else{
					mistake = true;
				}
			}
			
			// Time elapses when the user is not making input
			stall(10ms);
			elapsedTimeMS += 10;
			
			// Determine if user ran out of time for input
			if(elapsedTimeMS == timeWindow){
				mistake = true;
			}
			// Determine if user succeeded
			if(points == 99){
				mistake = true;
				success = true;
			}
			
		}
		// Time window is reduced with every success
		timeWindow -= 15;
	}
	
	// When the game ends, deliver the results
	endMessage(success);
}
//--------------------------------------- FUNCTIONS DEALING WITH USER INTERACTION ---------------------------------------------

//*********************************************************************
// A random command is generated.  Used by shoutCommand				  *
//*********************************************************************
Command generateCommand(){
	// Randomly generate command
	return command;
}
//*********************************************************************
// The speaker plays a command that the user should perform			  *
//*********************************************************************
void shoutCommand(Command paramCommand){
	switch(paramCommand){
		case flick:
			speaker("flick it!");
			break;
		case roll:
			speaker("roll it!");
			break;
		case blow:
			speaker("blow it!");
			break;
		default:
			// Not sure yet
	}
}
//*********************************************************************
// The speaker gives a final output depending on the user's success	  *
//*********************************************************************
void endMessage(bool paramSuccess){
	if(success){
		speaker("Congratulations");
	}
	else{
		speaker("You scored " + score + " points!");
	}
}


//------------------------------------------- FUNCTIONS DEALING WITH HARDWARE ------------------------------------------------

//*********************************************************************
// Returns the command that the user engages in						  *
//*********************************************************************
Command readUserInput(int startRollAngle){
	Command outputCommand;
	
	// Check sensors
	if(rollSensorTriggered(startRollAngle)){
		outputCommand = rollSensor;
	}
	if(flickSensorTriggered()){
		outputCommand = flickSensor;
	}
	if(blowSensorTriggered()){
		outputCommand = blowSensor;
	}
	return outputCommand;
}
//*********************************************************************
// Returns if the user flicked the sensor							  *
//*********************************************************************
bool flickSensorTriggered(){
	if(readFlickSensor() >= /*Baseline*/){
		return true;
	}
	else{
		return false;
	}
}
//*********************************************************************
// Returns number given by the flick sensor							  *
//*********************************************************************
int readFlickSensor(){
	return // Number given by sensor
}
//*********************************************************************
// Returns if the user rolled the sensor							  *
//*********************************************************************
bool rollSensorTriggered(int startRollAngle){
	if(readRollSensor() - startRollAngle > /*Baseline*/){
		return true;
	}
	else{
		return false;
	}
}
//*********************************************************************
// Returns the angle given by the roll sensor						  *
//*********************************************************************
int readRollSensor(){
	return // Angle given by sensor
}
//*********************************************************************
// Returns if the user blew into the sensor							  *
//*********************************************************************
bool blowSensorTriggered(){
	if(readBlowSensor() >= /*Baseline*/){
		return true;
	}
	else{
		return false;
	}
}
//*********************************************************************
// Returns the number given by the blow sensor						  *
//*********************************************************************
int readBlowSensor(){
	return // Number given by sensor
}
