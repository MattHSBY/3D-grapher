package renderPack;

public class Line {
	private Vertex v1;
	private Vertex v2;
	public Vertex getv1()	{
		return this.v1;
	}
	public Vertex getv2() {
		return this.v2;
	}
	public void setv1(Vertex v) {
		this.v1 = v;
	}
	public void setv2(Vertex v) {
		this.v2 = v;
	}
	
	public Line(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

}
