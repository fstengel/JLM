package lessons.welcome.basics;

import plm.universe.bugglequest.SimpleBuggle;

class ScalaInstructionsEntity extends SimpleBuggle {

	protected def run() {
		/* BEGIN TEMPLATE */
		/* BEGIN SOLUTION */
		brushDown();
		for (i <- 0 to 3) {
			forward(2);
			right();
		}
		brushUp();
		forward();
		right();
		forward();
		left();
		/* END SOLUTION */
		/* END TEMPLATE */
	}

}
