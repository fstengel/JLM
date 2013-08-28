package plm.universe.sort;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.ImageIcon;

import plm.core.model.Game;
import plm.core.model.ProgrammingLanguage;
import plm.core.ui.ResourcesCache;
import plm.core.ui.WorldView;
import plm.universe.EntityControlPanel;
import plm.universe.World;

public class SortingWorld extends World {
	private int[] values;	// the values as they are sorted in the array
	private int[] initValues;	// needed by the chronoview
	private int readCount = 0;	// the count of the read made
	private int writeCount = 0;	// the count of the write made
	/*
	 * It's needed by the chronoview
	 * This list contains all the operations ( SetVal, CopyVal or Swap ) made on the world since 
	 * the moment where it has been shown to the user
	 */
	private ArrayList<Operation> operations = new ArrayList<Operation>(1) ;	

	/** Copy constructor used by the PLM internals */
	public SortingWorld(SortingWorld world) {
		super(world);
	}

	/** Constructor used in the exercises */
	public SortingWorld(String name, int nbValues) {
		super(name);
		setDelay(50);
		this.values = new int[nbValues];
		for (int i=0 ; i< this.values.length ; i++) 
		{
			this.values[i] = i;
		}
		scramble();
		this.initValues = this.values;
	}

	private void scramble() {
		while( this.isSorted() )
		{
			for ( int caseNumber = 0 ; caseNumber < this.values.length ; caseNumber++)
			{
				// Swapping time !
				if ( Math.random() > 0.5)
				{
					this.exchangeValues(caseNumber,(int)(Math.random()*this.values.length));
				}
			}
		}
	}

	public boolean equals(Object o) {
		if (o == null || !(o instanceof SortingWorld))
			return false;

		SortingWorld other = (SortingWorld) o;
		if (values.length != other.values.length)
			return false;
		for (int i = 0 ; i < this.values.length ; i++) 
			if ( this.values[i] != other.values[i] )
				return false;
		
		if (operations.size() != other.operations.size())
			return false;
		for (int i = 0 ; i < this.operations.size() ; i++) 
			if (! operations.get(i).equals( other.operations.get(i)) )
				return false;
		
		if (! (this.readCount == other.readCount) )
			return false;
		if (! (this.writeCount == other.writeCount) )
			return false;
		
		return true;
	}

	/**
	 * Tells if the array is Sorted or not
	 */
	private boolean isSorted() {
		boolean sw = true;
		for ( int i = 0 ; i < this.values.length && sw ; i++ )
		{
			if ( this.values[i] != i )
			{
				sw = false ;
			}
		}
		return sw;
	}

	/**
	 * Copy the value from the cell of index from into the cell of index to<br>
	 * It costs one read and one write.
	 * @param from The index of the cell to copy
	 * @param to The index of the cell where copy the value
	 */
	public void copy(int from,int to) {
		if (from<0) throw new RuntimeException("Out of bounds in copy("+from+","+to+"): "+from+"<0");
		if (to<0) throw new RuntimeException("Out of bounds in copy("+from+","+to+"): "+to+"<0");
		if (from>=getValueCount()) throw new RuntimeException("Out of bounds in copy("+from+","+to+"), "+from+">= value count");
		if (to>=getValueCount()) throw new RuntimeException("Out of bounds in copy("+from+","+to+"), "+to+">= value count");

		this.operations.add(new CopyVal(from, to));

		this.readCount++;
		this.writeCount++;
		this.values[to] = this.values[from];
	}

	/**
	 * Make a textual description of the differences between the caller and world
	 * @param world : the world with which you want to compare your world
	 * @return A textual description of the differences between the caller and world
	 */
	@Override
	public String diffTo(World world) {
		String s ;
		if (world == null || !(world instanceof SortingWorld)) {
			s="This is not a world of sorting :(";
		} else {
			SortingWorld other = (SortingWorld) world;
			StringBuffer sb = new StringBuffer();
			if ( this.readCount != other.readCount )
				sb.append("Invalid read count : expected "+this.readCount+" found "+other.readCount+"\n");
			
			if ( this.writeCount != other.writeCount )
				sb.append("Invalid write count : expected "+this.writeCount+" found "+other.writeCount+"\n");
			
			for (int i = 0 ; i < this.values.length ; i++) 
				if ( this.values[i] != other.values[i] )
					sb.append("Index "+i+": expected "+this.values[i]+" found "+other.values[i]+"\n");
					
			s = sb.toString();
		}
		return s;
	}


	/** Returns the panel which let the user to interact dynamically with the world */
	@Override
	public EntityControlPanel getEntityControlPanel() {
		return new SortingButtonPanel();
	}
	/** Returns the icon of the universe */
	@Override
	public ImageIcon getIcon() {
		return ResourcesCache.getIcon("img/world_sorting.png");
	}

	/**
	 * Returns the initial state of the array
	 */
	public int[] getInitValues() {
		return initValues;
	}

	/**
	 * Return the list of all the operations made on the world since its initialization/last reset
	 */
	public ArrayList<Operation> getOperations() {
		return this.operations;
	}

	/**
	 * Return the read counter
	 */
	public int getReadCount() {
		return this.readCount;
	}

	/** Returns the amount of values in the array */
	public int getValueCount() {
		return values.length;
	}

	/** Returns the array of values that need to be sorted */
	public int[] getValues() {
		return this.values;
	}

	/** Returns a component able at displaying the world */
	@Override
	public WorldView getView() {
		return new SortingWorldView(this);
	}
	
	/** Returns the write counter */
	public int getWriteCount() {
		return this.writeCount;
	}

	/**
	 * Tell if the value at the index i is smaller than the value at index j<br>
	 * It costs two reads.
	 * @param i the index of the first cell that we want to check
	 * @param j the index of the second cell that we want to check
	 * @return if the content of the cell of index i is smaller than the content of the cell of index j
	 */
	public boolean isSmaller(int i, int j) {
		if (i<0) throw new RuntimeException("Out of bounds in isSmaller("+i+","+j+"): "+i+"<0");
		if (j<0) throw new RuntimeException("Out of bounds in isSmaller("+i+","+j+"): "+j+"<0");
		if (i>=getValueCount()) throw new RuntimeException("Out of bounds in isSmaller("+i+","+j+"), "+i+">= value count");
		if (j>=getValueCount()) throw new RuntimeException("Out of bounds in isSmaller("+i+","+j+"), "+j+">= value count");

		this.readCount+=2;
		return this.values[i]< this.values[j];
	}
	
	/**
	 * Tell if the value at the index i is smaller than val<br>
	 * It costs one read.
	 * @param i the index of case that we want to check
	 * @param val the value with which compare the cell
	 * @return if the case of index i is smaller than val
	 */
	public boolean isSmallerThan(int i, int val) {
		if (i<0) throw new RuntimeException("Out of bounds in isSmallerThan("+i+","+val+"): "+i+"<0");
		if (i>=getValueCount()) throw new RuntimeException("Out of bounds in isSmallerThan("+i+","+val+"), "+i+">= value count");

		this.readCount+=1;
		return this.values[i]<val;
	}
	
	/** 
	 * Reset the state of the current world to the one passed in argument
	 * @param w the world which must be the new start of your current world
	 */
	@Override
	public void reset(World w) {
		super.reset(w);
		SortingWorld world = (SortingWorld)w;
		
		this.values = world.values.clone();
		this.initValues = world.initValues.clone();
		this.readCount = world.readCount ;
		this.writeCount = world.writeCount;
		this.operations = new ArrayList<Operation>(1);
		for ( Operation o: world.operations)
			this.operations.add(o);
	}

	/**
	 * Setup the engine so that it's ready to host the user code
	 *
	 * @param lang the programming language used
	 * @throws ScriptException some error reported by the scripting engine itself
	 */
	@Override
	public void setupBindings(ProgrammingLanguage lang, ScriptEngine e) throws ScriptException {
		if (lang.equals(Game.PYTHON)) {
			e.eval(
					"def getValueCount():\n" +
					"  return entity.getValueCount()\n" +
					"def swap(i,j):\n" +
					"  entity.swap(i,j)\n" +
					"def copy(i,j):\n" +
					"  entity.copy(i,j)\n" +
					"def getValue(i):\n" +
					"  return entity.getValue(i)\n" +
					"def setValue(i,j):\n" +
					"  entity.setValue(i,j)\n" +
					"def isSmaller(i,j):\n"+
					"  return entity.isSmaller(i,j)\n"+
					"def isSmallerThan(i,j):\n"+
					"  return entity.isSmallerThan(i,j)\n"+
					/* BINDINGS TRANSLATION: French */
					"def getNombreValeurs():\n" +
					"  return entity.getValueCount()\n" +
					"def echange(i,j):\n" +
					"  entity.swap(i,j)\n" +
					"def copie(i,j):\n" +
					"  entity.copy(i,j)\n" +
					"def getValeur(i):\n" +
					"  return entity.getValue(i)\n" +
					"def setValeur(i,j):\n" +
					"  entity.setValue(i,j)\n" +
					"def plusPetit(i,j):\n"+
					"  return entity.isSmaller(i,j)\n"+
					"def plusPetitQue(i,j):\n"+
					"  return entity.isSmallerThan(i,j)\n"

			);
		} else {
			throw new RuntimeException("No binding of SortingWorld for "+lang);
		}
	}

	/**
	 * Return the value of index i in the array
	 * @param i the index wanted in the array
	 * @return the value of index i in the array
	 */
	public int getValue(int i) {
		if (i<0) throw new RuntimeException("Out of bounds in getValue("+i+"): "+i+"<0");
		if (i>=getValueCount()) throw new RuntimeException("Out of bounds in getValue("+i+"), "+i+">= value count");
		this.operations.add(new GetVal(i));
		readCount++;
		return values[i];
	}

	/**
	 * Set the value of the case number i of the array at val<br>
	 * It costs only one write.
	 * @param i the index of the cell
	 * @param val the value that you want to set
	 */
	public void setValue(int i,int val) {
		if (i<0) throw new RuntimeException("Out of bounds in setValue("+i+"): "+i+"<0");
		if (i>=getValueCount()) throw new RuntimeException("Out of bounds in setValue("+i+"), "+i+">= value count");

		this.operations.add(new SetVal(i, val));

		this.writeCount++;
		this.values[i]=val;
	}

	/**
	 * Swap the value of the case number i with the value of the case number j<br>
	 * It costs two reads and two writes
	 * @param i the index of the first cell concerned
	 * @param j the index of the second cell concerned
	 */
	public void swap(int i, int j) {
		if (i<0) throw new RuntimeException("Out of bounds in swap("+i+","+j+"): "+i+"<0");
		if (j<0) throw new RuntimeException("Out of bounds in swap("+i+","+j+"): "+j+"<0");
		if (i>=getValueCount()) throw new RuntimeException("Out of bounds in swap("+i+","+j+"), "+i+">= value count");
		if (j>=getValueCount()) throw new RuntimeException("Out of bounds in swap("+i+","+j+"), "+j+">= value count");

		this.operations.add(new Swap(i, j));

		this.readCount+=2;
		this.writeCount+=2;

		exchangeValues(i,j);
	}

	/**
	 * Exchange the value of the case number i with the value of the case number j<br>
	 * It doesn't increase the read or the write counters.
	 * @param i the index of the first cell concerned
	 * @param j the index of the second cell concerned
	 */
	private void exchangeValues(int i, int j) {
		int tmp = this.values[i];
		this.values[i] = this.values[j];
		this.values[j] = tmp;
	}

}
