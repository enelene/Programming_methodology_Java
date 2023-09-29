import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
		public void run () {
		// pre-condition: karel is on 3x4without newspaper //
	    // post position: karel is on 3x4 with newspaper //
	    
		
	    goOutFromHome ();
	    pickNewspaper ();
	    goBack();
			
		}
		private void goOutFromHome () {
			// pre-position: karel is on 3x4//
			// post-position: karel is on 6x3//
			turnRight ();
			move ();
			turnLeft ();
			for (int i=0; i<3; i++ ) {
				move ();
			}
		}
		private void pickNewspaper () {
		// pre-position: karel is where the newspaper is and facing east//
		// post-position : karel picked the newspaper and facing west //
		pickBeeper ();
		turnAround (); 
		}
		private void goBack () {
		// pre-position: karel is on 6x5 and facing west//
	// post-condition: karel is on 3x4 and facing east //
			for (int i=0; i< 3; i++ ) {
				move ();
			}
			turnRight ();
			move ();
			turnRight ();
		}
	}


