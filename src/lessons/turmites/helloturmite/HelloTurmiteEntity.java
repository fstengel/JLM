package lessons.turmites.helloturmite;

import java.awt.Color;

import plm.universe.bugglequest.SimpleBuggle;

public class HelloTurmiteEntity extends SimpleBuggle {
	Color[] allColors = {Color.white, Color.black, Color.blue, Color.cyan, Color.green, Color.orange, Color.red, 
			Color.gray, Color.magenta, Color.darkGray, Color.pink, Color.lightGray};

	/* BEGIN TEMPLATE */
	final static int STOP   = 0; /* for example */ 
	/* BEGIN HIDDEN */
	final static int NOTURN = 1;
	final static int LEFT   = 2;
	final static int BACK   = 4;
	final static int RIGHT  = 8;

	final static int NEXT_COLOR = 0;
	final static int NEXT_MOVE  = 1;
	final static int NEXT_STATE = 2;
	/* END HIDDEN */

	int state = 0;

	public void step(Color[] colors, int[][][] rule ) {
		int currentColor=0;
		/* Your code comes here */
		/* BEGIN SOLUTION */
		Color current = getGroundColor(); 
		for (int i=0;i<colors.length;i++) 
			if (current.equals(colors[i])) 
				currentColor = i;

		setBrushColor(colors[ rule[state][currentColor][NEXT_COLOR] ]);
		brushDown();
		brushUp();

		switch (rule[state][currentColor][NEXT_MOVE]) {
		case STOP:   /* nothing */;            break;
		case NOTURN: /* no turn */; forward(); break;
		case LEFT:   left();   	forward(); break;
		case RIGHT:  right();   forward(); break;
		case BACK:   back();    forward(); break;
		default:
			System.out.println("Unknown turn command associated to i="+currentColor+": "+rule[state][currentColor][NEXT_MOVE]);
		}

		state = rule[state][currentColor][NEXT_STATE];

		/* END SOLUTION */
	}
	/* END TEMPLATE */

	@Override
	public void run() { 
		int nbSteps = (Integer)getParam(0);
		Color[] colors; 
		int[][][] rule; 

		rule = ((int[][][])getParam(1));

		colors = new Color[rule.length];
		for (int i=0; i<rule.length; i++)
			colors[i] = allColors[i];

		for (int i=0;i<nbSteps;i++) {
			((lessons.turmites.universe.TurmiteWorld)world).stepDone();
			step(colors,rule);
		}
	}
}
