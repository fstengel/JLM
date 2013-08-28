package lessons.welcome.methods.picture;


class ScalaPictureMono2Entity extends plm.universe.bugglequest.SimpleBuggle {

	/* BEGIN TEMPLATE */
	/* BEGIN SOLUTION */
	def mark() {
		brushDown();
		brushUp();
	}

	def makeV() {
		forward();
		mark();

		forward();
		left();
		forward();
		mark();

		backward();
		right();
		forward();
		mark();

		forward();
		left();
	}

	def makePattern() {
	  for (i <- 1 to 4) {
		  makeV();
	  }
	  forward(5);
	}

	def makeLine(count: Int){
		for (i <- 1 to count)
			makePattern();
		backward(count*5);
	}

	def nextLine() {
		left();
		forward(5);
		right();	
	}

	override def run() {
		for (i <- 1 to 3) {
			makeLine(3);
			nextLine();
		}
	}
	/* END SOLUTION */
	/* END TEMPLATE */
}
