import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
		public void run () {
			while (frontIsClear ()) {
			fillColumnAndTurnBack ();
			moveFourTimes ();
			}
			
			while (frontIsBlocked ()) {
			fillColumnAndTurnBack ();
				
	    	}
			turnRight (); // karel is facing east //	
			
		    }
	    private void fillColumnAndTurnBack () {
	    	turnLeft ();// Karel now is facing north //
	    	while (frontIsClear()) {
		    if (noBeepersPresent()) {
				putBeeper ();
			} 
		    else { move (); 
	    	}
	       }
		// Karel is on the top //
		while (frontIsBlocked()) { 
			if (noBeepersPresent ()) {
			putBeeper();
			// Karel finishes filling column here //
			turnBack ();		
		    }
			else {
			turnBack ();
			}
		  }
	     }
	    private void moveFourTimes () {
		   for (int i=0; i<4; i++) {
			move ();
		    } // this helps Karel to move to another column//
	       }
	    private void turnBack () {
		   turnLeft (); // Karel is facing west //
		   turnLeft (); // Karel is facing south //
		while (frontIsClear ()) {
	    	move ();
	    	}
		while (frontIsBlocked ()) {
		   turnLeft (); 
		   // karel is facing east //
		    }
	       }
	}
