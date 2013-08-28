package lessons.recursion.hanoi.universe;
/* BEGIN TEMPLATE */
import plm.universe.Entity;
import plm.universe.World;

public class HanoiEntity extends Entity {
	/** Instantiation Constructor (used by exercises to setup the world) 
	 * Must call super(name, world). If you had fields to setup, you'd  have to add more parameters
	 */
	public HanoiEntity(String name, World world) {
		/* BEGIN HIDDEN */
		super(name,world);
		/* END HIDDEN */
	}

	/** Part of the copy process 
	 * Must call super(name)
	 */
	public HanoiEntity(String name) {
		/* BEGIN HIDDEN */
		super(name);
		/* END HIDDEN */
	}
	/** Must exist too. Calling HanoiEntity("dummy name") is ok */
	public HanoiEntity() {
		/* BEGIN HIDDEN */
		this("Hanoi Entity");
		/* END HIDDEN */
	}

	/** Must *NOT* call HanoiEntity(name, world) because it's called in a traversal of the world so you don't want to modify it.
	 * Instead, call HanoiEntity(name), leaving the world field empty; the PLM will fill it with the right value afterward 
	 */
	@Override
	public Entity copy() {
		/* BEGIN HIDDEN */
		return new HanoiEntity(name);
		/* END HIDDEN */
	}

	/** Must exist so that exercises can instantiate your entity (Entity is abstract) */
	@Override
	public void run() {
	}

	/** Part of your world logic */
	public void move(int src, int dst) {
		/* BEGIN HIDDEN */
		((HanoiWorld) world).move(src,dst);
		stepUI();
		/* END HIDDEN */
	}
	public int getSlotSize(int slot) {
		return ((HanoiWorld) world).getSlotSize(slot);
	}
	
	/* BEGIN HIDDEN */
	@Override
	public String toString(){
		return "HanoiEntity (" + this.getClass().getName() + ")";
	}
	/* END HIDDEN */
	
	
	/* BINDINGS TRANSLATION: French */
	public void deplace(int src,int dst) { move(src, dst); }
	public int  getTaillePiquet(int rank) { return getSlotSize(rank); }
}
/* END TEMPLATE */
