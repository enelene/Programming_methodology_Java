
import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	public void run() {
		goToTheHighestPoint ();
		findMidPoint ();
		if (noBeepersPresent ()) {
		putBeeper ();
		turnLeft ();
		} else {
			turnLeft ();
		}
}
	    private void goToTheHighestPoint () {
		turnLeft (); //karel is facing North 
		if (frontIsClear ()) {
			while (frontIsClear ()) {
				move ();
	    }  // karel is at the  left highest point 
			turnAround(); // karel is facing south 
		
		}  else {
		turnAround (); // this is for 1x1 world 
		} 
		}
		
		private void findMidPoint () {
		while (frontIsClear ()) {
			move ();
			if (frontIsClear ()) {
				move ();
				turnLeft ();
				if (frontIsClear ()) {
					move ();
					turnRight ();
				}
			} else {
				putBeeper ();
			}
		}
	}
}



