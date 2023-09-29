
import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
		public void run () {
	putBeeper ();
	 if (frontIsClear ()) {
			fillRowLikeCheckboard ();                                  // firstly we just fill first row //
		   while (leftIsClear ()) {
			 repositionForRowToWestAndPutBeeper ();                // Karel from the end of the left side will be moved to the upper row //
			 fillRowLikeCheckboard (); 
		   if (rightIsClear()) {
			 repositionForRowToEastAndPutBeeper  ();             // Karel from the end of the right side will be moved to the upper row //
			 fillRowLikeCheckboard ();
	       } else {
	    	 turnAround ();                                    // we Change karels direction on purpose so we can end the cycle //
	   }
	  }
	 } else {                           // we need this if we have 1xN world where N is countable number bigger than 1 //
		 turnLeft ();
		 fillRowLikeCheckboard ();
		 }
	  }

	     private void fillRowLikeCheckboard () { 
		                                                  // we put beeper , than move and check once again if front is clear to move //
		while (frontIsClear ()) {	
			move();	 
		 if (frontIsClear ()) {
			 move ();
			 putBeeper ();
		 }
		}
	 }
	     private void repositionForRowToWestAndPutBeeper  () {
		if (beepersPresent ()) { 
		                                       	// if Beepers present, it means that we have checkboard with odd number of squares //
		    turnLeft ();                          // karel is facing north//
		    move ();                             // karel is one step high //
		    turnLeft ();                        // karel is facing west //
		    move();
		    putBeeper (); 
		}
		else {       
			                                  // if beepers do not present, it means that we have checkboard with even number of squares //
			turnLeft ();                     //karel is facing north//
			move();                         //karel is one step high //
			turnLeft ();                   // karel is facing west //
			putBeeper ();
		}
	   }
	    private void repositionForRowToEastAndPutBeeper  () {	
	    	turnRight();  // karel is facing north //
			move (); // karel is one step above //
			turnRight (); // karel is facing east //
			putBeeper (); 
		}	 
	   }

