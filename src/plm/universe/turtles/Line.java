package plm.universe.turtles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;


public class Line implements Shape {
	public double x1, y1,  x2, y2;
	public Color color;
	
	public Line(double x1, double y1, double x2, double y2, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.draw(new Line2D.Double(x1,y1,x2,y2));
	}

	public Line copy() {
		return new Line(x1,y1,x2,y2,color);
	}
	private boolean doubleEqual(double a, double b) {
		return (Math.abs(a-b)<0.000001);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof Line))
			return false;
		
		Line other = (Line) obj;
		if (!doubleEqual(x1,other.x1))
			return false;
		if (!doubleEqual(x2,other.x2))
			return false;
		if (!doubleEqual(y1,other.y1))
			return false;
		if (!doubleEqual(y2,other.y2))
			return false;
				
		return true;
	}
	public String diffTo(Shape o) {
		if (o instanceof Line) {
			Line other = (Line) o;
			if (!doubleEqual(x1,other.x1))
				return "x1 differs";
			if (!doubleEqual(x2,other.x2))
				return "x2 differs";
			if (!doubleEqual(y1,other.y1))
				return "y1 differs";
			if (!doubleEqual(y2,other.y2))
				return "y2 differs";
			return "I dont see the difference";
		} else 
			return "That's not a line";
	}
	
	@Override
	public String toString(){
		return "Line ("+x1+","+y1+";"+x2+","+y2+";"+color+")";
	}
}
