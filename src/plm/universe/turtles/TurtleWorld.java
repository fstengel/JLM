package plm.universe.turtles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.ImageIcon;

import plm.core.model.Game;
import plm.core.model.ProgrammingLanguage;
import plm.core.ui.ResourcesCache;
import plm.core.ui.WorldView;
import plm.universe.Entity;
import plm.universe.EntityControlPanel;
import plm.universe.World;

public class TurtleWorld extends World {

	ArrayList<Shape> shapes = new ArrayList<Shape>();
	ArrayList<SizeHint> sizeHints = new ArrayList<SizeHint>();

	private double width;
	private double height;
	
	public TurtleWorld(String name) {
		super(name);
	}

	public TurtleWorld(String name, int width, int height) {
		super(name);
		this.width = width;
		this.height = height;
	}
	
	public TurtleWorld(TurtleWorld original) {
		super(original);
		sizeHints = new ArrayList<SizeHint>();
		for (SizeHint sh :original.sizeHints)
			sizeHints.add(sh);
		for (Entity e: getEntities()){
			Turtle t = (Turtle) e;
			t.startX = t.getX();
			t.startY = t.getY();
		}
	}

	@Override
	public void reset(World w) {
		TurtleWorld initialWorld = (TurtleWorld)w;
		shapes = new ArrayList<Shape>();
		this.height = initialWorld.height;
		this.width = initialWorld.width;

		Iterator<Shape> it = initialWorld.shapes();
		while (it.hasNext()) 
			shapes.add(it.next().copy());
		
		sizeHints = new ArrayList<SizeHint>();
		for (SizeHint sh : initialWorld.sizeHints)
			sizeHints.add(sh);
		
		super.reset(w);		
	}
	
	public void addSizeHint(int x1, int y1, int x2, int y2) {
		addSizeHint(x1, y1, x2, y2, null);
	}
	public void addSizeHint(int x1, int y1, int x2, int y2, String text) {
		synchronized (sizeHints) {
			sizeHints.add(new SizeHint(x1, y1, x2, y2, text));
			notifyWorldUpdatesListeners();
		}
	}
	public void addLine(double x, double y, double newX, double newY, Color color) {
		synchronized (shapes) {
			shapes.add(new Line(x,y,newX,newY,color));
			notifyWorldUpdatesListeners();
		}
	}
	public void addCircle(double x, double y, double radius, Color color) {
		synchronized (shapes) {
			shapes.add(new Circle(x,y,radius,color));
			notifyWorldUpdatesListeners();
		}
	}

	public Iterator<Shape> shapes() {
		return shapes.iterator();
	}
	

	public double getHeight() {
		return height;
	}
	public double getWidth() {
		return width;
	}
	
	@Override
	public EntityControlPanel getEntityControlPanel() {
		return new TurtleButtonPanel();
	}

	@Override
	public WorldView getView() {
		return new TurtleWorldView(this);
	}
	@Override
	public ImageIcon getIcon() {
		return ResourcesCache.getIcon("img/world_turtle.png");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof TurtleWorld))
			return false;
		if (! super.equals(obj))
			return false;
		
		TurtleWorld other = (TurtleWorld) obj;
		if (shapes.size() != other.shapes.size())
			return false;
		Collections.sort(shapes, new ShapeComparator());
		Collections.sort(other.shapes, new ShapeComparator());
		for (int i=0;i<shapes.size();i++)
			if (! shapes.get(i).equals(other.shapes.get(i)))
				return false;
		
		return true;
	}
	
	// TODO implement world IO	
	
	@Override
	public String toString(){
		String res = "TurtleWorld: name="+getName()+
			", size="+width+"x"+height+
			", parameters: " +parameters+
			", shapes=[";
		Iterator<Shape> it = shapes();
		while (it.hasNext()) 
			res += it.next().toString();
		res += "]";
		return res;
	}
	@Override
	public void setupBindings(ProgrammingLanguage lang, ScriptEngine e) throws ScriptException {
		if (lang.equals(Game.PYTHON)) {
			e.put("Color", Color.class);
			e.eval( "def backward(i):\n"+
					"  entity.backward(i)\n"+
					"def forward(i):\n"+
					"  entity.forward(i)\n"+
					"def penUp():\n"+
					"  entity.penUp()\n"+
					"def penDown():\n"+
					"  entity.penDown()\n"+
					"def isPenDown():\n"+
					"  return entity.isPenDown()\n"+
					"def left(i):\n"+
					"  entity.left(i)\n"+
					"def right(i):\n"+
					"  entity.right(i)\n"+
					"def setColor(c):\n"+
					"  entity.setColor(c)\n" +
					"def getColor():\n"+
					"  return entity.getColor()\n" +
					"def setPos(x,y):\n" +
					"  entity.setPos(x,y)\n" +
					"def setX(x):\n" +
					"  entity.setX(x)\n" +
					"def setY(y):\n" +
					"  entity.setY(y)\n" +
					"def getX():\n" +
					"  return entity.getX()\n" +
					"def getY():\n" +
					"  entity.getY()\n" +
					"def moveTo(x,y):\n" +
					"  entity.moveTo(x,y)\n"+
					"def addSizeHint(x,y,z,t):\n"+
					"  entity.addSizeHint(x,y,z,t)\n"+
					"def circle(radius):\n"+
					"  entity.circle(radius)\n"+
					/* BINDINGS TRANSLATION: French */
					"def recule(i):\n"+
					"  entity.backward(i)\n"+
					"def avance(i):\n"+
					"  entity.forward(i)\n"+
					"def leveCrayon():\n"+
					"  entity.penUp()\n"+
					"def baisseCrayon():\n"+
					"  entity.penDown()\n"+
					"def estCrayonBaisse():\n"+
					"  return entity.isPenDown()\n"+
					"def gauche(i):\n"+
					"  entity.left(i)\n"+
					"def droite(i):\n"+
					"  entity.right(i)\n"+
					"def setCouleur(c):\n"+
					"  entity.setColor(c)\n" +
					"def allerVers(x,y):\n" +
					"  entity.moveTo(x,y)\n"+
					"def cercle(radius):\n"+
					"  entity.circle(radius)\n"
					);
		} else {
			throw new RuntimeException("No binding of TurtleWorld for "+lang);
		}
	}

	@Override
	public String diffTo(World world) {
		StringBuffer sb = new StringBuffer();
		TurtleWorld other = (TurtleWorld) world;
		if (shapes.size() != other.shapes.size())
			sb.append("  There is only "+other.shapes.size()+" lines where "+shapes.size()+" were expected.\n");
		for (int i=0;i<other.shapes.size();i++)
			if (! other.shapes.get(i).equals(shapes.get(i)))
				sb.append("  Got "+other.shapes.get(i)+" where "+shapes.get(i)+" were expected ("+
						((Line) other.shapes.get(i)).diffTo(shapes.get(i))+")\n");
		
		return sb.toString();
	}
}

class ShapeComparator implements Comparator<Shape> {

	public ShapeComparator() {
		super();
	}

	@Override
	public int compare(Shape s1, Shape s2) {
		if (s1 instanceof Line && s2 instanceof Circle)
			return -1;
		if (s1 instanceof Circle && s2 instanceof Line)
			return 1;
		
		if (s1 instanceof Line) {
			Line l1 = (Line) s1;
			Line l2 = (Line) s2;
			if (l1.x1 < l2.x1)
				return -1;
			if (l1.x1 > l2.x1)
				return 1;

			if (l1.x2 < l2.x2)
				return -1;
			if (l1.x2 > l2.x2)
				return 1;

			if (l1.y1 < l2.y1)
				return -1;
			if (l1.y1 > l2.y1)
				return 1;

			if (l1.y2 < l2.y2)
				return -1;
			if (l1.y2 > l2.y2)
				return 1;
			return 0;
		}
		if (s1 instanceof Circle) {
			Circle c1 = (Circle) s1;
			Circle c2 = (Circle) s2;
			if (c1.x < c2.x)
				return -1;
			if (c1.x > c2.x)
				return 1;
		}
		return 0;
	}
	
}
