package renderPack;

import java.util.ArrayList;

public class Cube {
	private int size;
	private ArrayList<Line> lines;
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<Line> getLines() {
		return lines;
	}
	
	public Cube(int size) {
		this.size=size;
		this.lines = new ArrayList<Line>();
		
		Vertex v0 = new Vertex(-size,-size,-size);
		Vertex v1 = new Vertex(-size,-size,size);
		Vertex v2 = new Vertex(-size,size,-size);
		Vertex v3 = new Vertex(-size,size,size);
		Vertex v4 = new Vertex(size,-size,-size);
		Vertex v5 = new Vertex(size,-size,size);
		Vertex v6 = new Vertex(size,size,-size);
		Vertex v7 = new Vertex(size,size,size);
		
		Line l0 = new Line(v0,v1);lines.add(l0);
		Line l1 = new Line(v1,v5);lines.add(l1);
		Line l2 = new Line(v5,v4);lines.add(l2);
		Line l3 = new Line(v4,v0);lines.add(l3);
		Line l4 = new Line(v0,v2);lines.add(l4);
		Line l5 = new Line(v2,v3);lines.add(l5);
		Line l6 = new Line(v3,v1);lines.add(l6);
		Line l7 = new Line(v3,v7);lines.add(l7);
		Line l8 = new Line(v7,v5);lines.add(l8);
		Line l9 = new Line(v7,v6);lines.add(l9);
		Line l10 = new Line(v6,v4);lines.add(l10);
		Line l11 = new Line(v6,v2);lines.add(l11);
		
		
		
		
		
		
				
		
		
	}

}
