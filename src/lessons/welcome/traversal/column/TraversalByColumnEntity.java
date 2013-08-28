package lessons.welcome.traversal.column;

import plm.core.model.Game;
import plm.universe.bugglequest.SimpleBuggle;

public class TraversalByColumnEntity extends SimpleBuggle {
	/* BEGIN TEMPLATE */
	/* BEGIN SOLUTION */	
	public void nextStep() {	
		int x=getX();
		int y=getY();

		if (y < getWorldHeight()-1) {
			y++;
		} else {
			y = 0;
			if (x < getWorldWidth()-1) {
				x++;
			} else {
				x = 0; 
			}
		}
		setPos(x,y);
	}

	public boolean endingPosition() {
		return (getX() == getWorldWidth() -1) && (getY() == getWorldHeight()-1);
	}


	@Override
	public void run() {
		int cpt=0;
		writeMessage(cpt);
		while (!endingPosition()) {
			nextStep();
			cpt++;
			writeMessage(cpt);
		}
	}
	/* END SOLUTION */
	/* END TEMPLATE */	
	@Override
	public void forward(int i)  { 
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use forward() in this exercise."));
	}
	@Override
	public void forward()  {
		if (isInited())
			throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use forward() in this exercise."));
	}
	@Override
	public void backward(int i) {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use backward() in this exercise."));
	}
	@Override
	public void backward() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use backward() in this exercise."));
	}
	@Override
	public void left() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use left() in this exercise."));
	}
	@Override
	public void right() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use right() in this exercise."));
	}
	@Override
	public void back() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use back() in this exercise."));
	}
	@Override
	public boolean isFacingWall() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use isFacingWall() in this exercise."));
	}
	@Override
	public boolean isBackingWall() {
		throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use isBackingWall() in this exercise."));
	}
}
