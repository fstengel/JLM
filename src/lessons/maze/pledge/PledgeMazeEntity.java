package lessons.maze.pledge;

import plm.core.model.Game;
import plm.universe.Direction;

public class PledgeMazeEntity extends plm.universe.bugglequest.SimpleBuggle {
	@Override
	public void setX(int i)  {
		if (isInited())
			throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use setX() in this exercise."));
	}
	@Override
	public void setY(int i)  { 
		if (isInited())
			throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use setY() in this exercise."));
	}
	@Override
	public void setPos(int i,int j)  { 
		if (isInited())
			throw new RuntimeException(Game.i18n.tr("I'm sorry Dave, I'm affraid I can't let you use setPos() in this exercise."));
	}

	/* BEGIN TEMPLATE */
	/* BEGIN SOLUTION */
	public void run() {
		int state = 0 ;
		this.angleSum = 0;
		this.setDirection(this.chosenDirection);
		while ( !isOverBaggle() )
		{
			switch ( state )
			{
			case 0: // North runner mode
				while ( !isFacingWall() )
					forward();
				
				right(); // make sure that we have a left wall
				angleSum--;
				state = 1; // time to enter the Left Follower mode
				break;
			case 1: // Left Follower Mode
				this.stepHandOnWall(); // follow the left wall
				if ( this.isChosenDirectionFree() && this.angleSum == 0  ) 
					state =0; // time to enter in North Runner mode
				
				break;
			}
		}
		pickupBaggle();
	}

	int angleSum;

	private void stepHandOnWall(){
		while ( ! isFacingWall() )
		{
			forward();
			left();
			this.angleSum++;
		}
		right();
		this.angleSum--;
	}

	Direction chosenDirection = Direction.NORTH;

	private boolean isChosenDirectionFree() {
		Direction memorizedD = getDirection();
		this.setDirection(this.chosenDirection);
		boolean isFree = !isFacingWall();
		this.setDirection(memorizedD);
		return isFree;
	}
	/* END SOLUTION */
	/* END TEMPLATE */
}
