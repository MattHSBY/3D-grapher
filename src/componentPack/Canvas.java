package componentPack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.swing.JPanel;

import inputPack.KeyboardInputs;
import inputPack.MouseInputs;
import interpretPack.Interpreter;
import renderPack.Cube;
import renderPack.Line;
import mathpack.Matrix3;
import renderPack.Vertex;
import renderPack.Vertex2D;

public class Canvas extends JPanel{
	public int timespainted = 0;
	private MouseInputs MI;
	private KeyboardInputs KI;
	public ArrayList<Line> lines;
	private boolean useChroma = false;
	private boolean showEq = true;
	private boolean showAxes = true;
	private boolean showBox = true;
	private Color colour = Color.white;
	public int Chroma;
	private Cube c;
	private int fov = 25;
	public int xtheta = 0;
	public int ytheta = 0;
	public int ztheta = 0;
	public int precision = 100;
	private Line Xaxis;
	private Line Yaxis;
	private Line Zaxis;
	private int max = 10;
	private int realsize = 5;
	public double bounds = 8;
	public double mag = 50;
	private ArrayList<String> graphs;
	 //0 - neg x, 1 - pos x, 2 - neg y, 3 - pos y, 4 - neg z, 5 - pos z
	
	
	//cool graphs :
	//(x^2+y^2)/xy
	//x*y*(x+y)
	//(x-2y)/(x^2+y^2)
	//(0.8*(y/e^(x^2+y^2)+0.7*sin(y)*1.5cos(2x)))*cos(x*y)
	//sin(x)^3 + sin(y)^3
	//(10x^2y - 5x^2 - 4y^2 - x^4 - 2y^4)/2
	
	
	public void toggleAxes() {
		showAxes = !showAxes;
	}
	public void toggleBox() {
		showBox = !showBox;
	}
	public void toggleChroma() {
		useChroma = !useChroma;
	}
	
	public void resetRender() {
		lines = new ArrayList<Line>();
		graphs = new ArrayList<String>();
	}
	public void updateRender() {
		ArrayList<String> gs = graphs;
		resetRender();
		for (int i =0;i<gs.size();i++) {
			System.out.println(gs.get(i));
			addRender(gs.get(i));
		}
	}
	
	public void addRender(String expression) {
		graphs.add(expression);
		
		ArrayList<Line> dlines = new ArrayList<Line>();
		
		ArrayList<ArrayList<Vertex>> xyplane = new ArrayList<ArrayList<Vertex>>();
		
		double basex = (2*bounds)/precision;
		double basey = (2*bounds)/precision;
		
		for (int i = 0;i<=precision;i++) {
			xyplane.add(new ArrayList<Vertex>());
			for (int v = 0;v<=precision;v++) {
				Vertex ver = new Vertex((basex *i - bounds),0,(basey *v -bounds));
				xyplane.get(i).add(ver);
			}
		}
		
		
		//change z values here
		
		Interpreter interpreter = new Interpreter(expression);
		for (int i = 0;i<xyplane.size();i++) {
			for (int v =0;v<xyplane.get(i).size();v++) {
				//System.out.println(i + ", " + v);
				Vertex vert = xyplane.get(i).get(v);
				Double result = interpreter.substitute(vert.getX(), vert.getZ());
				//System.out.println(result + ", " + vert.getX() + ", " + vert.getZ());
				xyplane.get(i).get(v).setY(result);
				
				Double y = vert.getY();
				
			}
		}
		
		
		
		
		
		for (int i = 0;i<xyplane.size();i++) {
			
			for (int v =0;v<xyplane.get(i).size();v++) {

				//System.err.println("("+ xyplane.get(i).get(v).getX()+", " + xyplane.get(i).get(v).getY() + ", "+ xyplane.get(i).get(v).getZ() +")");
				
				if (v < xyplane.get(i).size()-1) {
					Line l = new Line(xyplane.get(i).get(v),xyplane.get(i).get(v+1));
					dlines.add(l);
					
				}
				if (i < xyplane.size()-1) {
					Line l = new Line(xyplane.get(i).get(v),xyplane.get(i+1).get(v));
					dlines.add(l);
				}
				
			}
		}
		for (int i =0;i<dlines.size();i++) {
			lines.add(dlines.get(i));
		}
		
	}
	
	private Vertex2D projectV2D(Vertex v) {
		v = new Vertex(-v.getX(),-v.getY(),-v.getZ());
		v = rotateVertex(v,0,xtheta);
		v = rotateVertex(v,1,ytheta);
		v = rotateVertex(v,2,ztheta);
		double x = v.getX();
		double y = v.getY();
		double z = v.getZ();
		
		
		
		//2/A = o/fov
		
		double Px = x*fov/(5-z+fov);
		double Py = y*fov/(5-z+fov);
		
		Vertex2D v2d = new Vertex2D(Px,Py);
		return v2d;
	}
	
	private Vertex rotateVertex(Vertex v, int axis, int theta) { // rotates a given vertex v by angle theta around axis 0-2: 0 = y, 1 = x, 2 = z
		Matrix3 t;
		double dtheta =  theta * Math.PI/180; // change theta value in degrees to radians
		if (axis == 0) {
			t = new Matrix3(new double[] {
					Math.cos(dtheta), 0, Math.sin(dtheta),
					0,				 1, 			  0,
				   -Math.sin(dtheta), 0, Math.cos(dtheta)
				});
			
			
		} else if (axis == 1) {
			
			t = new Matrix3(new double[] {
					1,				 0,				  0,
					0, Math.cos(dtheta),-Math.sin(dtheta),
					0, Math.sin(dtheta), Math.cos(dtheta)
				});
				
			
		} else if (axis == 2) {
			t = new Matrix3(new double[] {
					Math.cos(dtheta),-Math.sin(dtheta), 0,
					Math.sin(dtheta), Math.cos(dtheta), 0,
					0,			  0, 				  1
				});
		} else {
			System.err.println("Error on axis. Only use between 0-2.");
			return null;
			
		}
		
		Vertex transformed = t.transform(v);
		
		
		return transformed;
		
		
	}
	
	private Line fitGraph(Line l) {
		Vertex v = l.getv1();
		Vertex v2 = l.getv2();
		Vertex vq = new Vertex(v.getX() * realsize / bounds,
							   v.getY() * realsize / bounds,
							   v.getZ() * realsize / bounds
							   );
		Vertex vp = new Vertex(v2.getX() * realsize / bounds,
							   v2.getY() * realsize / bounds,
							   v2.getZ() * realsize / bounds
				   			   );
		
		if (Math.abs(vq.getY()) > max) {
			vq.setY(max * vq.getY()/Math.abs(vq.getY()));
		}
		if (Math.abs(vp.getY()) > max) {
			vp.setY(max * vp.getY()/Math.abs(vp.getY()));
		}
		Line l2 = new Line(vq,vp);
		
		return l2;
	}
	
	private Path2D renderLine(Line l) {
		Path2D p = new Path2D.Double();
		Vertex rv1 = l.getv1();
		Vertex rv2 = l.getv2();
		
		
		Vertex2D v1 = projectV2D(rv1);
		Vertex2D v2 = projectV2D(rv2);
		
		
		p.moveTo(mag * v1.getX() + 400, mag *v1.getY() + 400);
		p.lineTo(mag *v2.getX() + 400, mag *v2.getY() + 400);
		p.closePath();
		return p;
	}
	
	public Canvas() {
	
		graphs = new ArrayList<String>();
		
		c = new Cube(realsize);
		
		Xaxis = new Line(new Vertex((c.getSize()+1),0,0),new Vertex(-(c.getSize()+1),0,0));
		Yaxis = new Line(new Vertex(0,(c.getSize()+1),0),new Vertex(0,-(c.getSize()+1),0));
		Zaxis = new Line(new Vertex(0,0,(c.getSize()+1)),new Vertex(0,0,-(c.getSize()+1)));
		
		lines = new ArrayList<Line>();
		KI = new KeyboardInputs(this);
		MI = new MouseInputs(this);
		addKeyListener(KI);
		addMouseWheelListener(MI);
		addMouseListener(MI);
		addMouseMotionListener(MI);
		this.setFocusable(true);
		this.requestFocus();
		
	}
	
	private void drawLabels(Graphics2D g2) {
		Vertex2D Xa = projectV2D(new Vertex(realsize + 2 * realsize/5,0,0));
		Vertex2D Ya = projectV2D(new Vertex(0,0,realsize + 2 * realsize/5));
		Vertex2D Za = projectV2D(new Vertex(0,realsize + 2 * realsize/5,0));
		int m = (int) (3*mag/5);
		if (m < 20) {
			m = 20;
		}
		
		g2.setFont(new Font("Monospaced", Font.BOLD, m));
		g2.drawString("X", (int) (400 + mag *Xa.getX()), (int) (400 + mag *Xa.getY()));
		g2.drawString("Y", (int) (400 + mag *Ya.getX()), (int) (400 + mag *Ya.getY()));
		g2.drawString("Z", (int) (400 + mag *Za.getX()), (int) (400 + mag *Za.getY()));
	}
	
	private void drawAxes(Graphics2D g2) {

		g2.draw(renderLine(Xaxis));
		g2.draw(renderLine(Yaxis));
		g2.draw(renderLine(Zaxis));
		
		Vertex2D Xnegb = projectV2D(new Vertex(0,0,-realsize));
		Vertex2D Xposb = projectV2D(new Vertex(0,0,realsize));
		Vertex2D Ynegb = projectV2D(new Vertex(-realsize,0,0));
		Vertex2D Yposb = projectV2D(new Vertex(realsize,0,0));
		Vertex2D Znegb = projectV2D(new Vertex(0,-realsize,0));
		Vertex2D Zposb = projectV2D(new Vertex(0,realsize,0));
		
		g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
		g2.drawString(String.valueOf(-bounds),(int) (400 + mag * Xnegb.getX()),(int) (400 + mag * Xnegb.getY()));
		g2.drawString(String.valueOf(bounds),(int) (400 + mag * Xposb.getX()),(int) (400 + mag * Xposb.getY()));
		g2.drawString(String.valueOf(-bounds),(int) (400 + mag * Ynegb.getX()),(int) (400 + mag * Ynegb.getY()));
		g2.drawString(String.valueOf(bounds),(int) (400 + mag * Yposb.getX()),(int) (400 + mag * Yposb.getY()));
		g2.drawString(String.valueOf(-bounds),(int) (400 + mag * Znegb.getX()),(int) (400 + mag * Znegb.getY()));
		g2.drawString(String.valueOf(bounds),(int) (400 + mag * Zposb.getX()),(int) (400 + mag * Zposb.getY()));
	}
	@Override
	public void paintComponent(Graphics g) {
		if (timespainted == 0) {
			requestFocus();
			setFocusable(true);
		}
		
		
		
		
		timespainted++;
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2 = (Graphics2D) g;
		
		if (useChroma == true) {
			colour = Color.getHSBColor(((float) Chroma)/360, 1, 1);
		} else {
			colour = Color.white;
		}
		
		g2.setColor(colour);
		
		
		
		if (showAxes == true) {// ------------------------------------------------------- render axes
			drawLabels(g2);
			
		}
		
		if (showBox == true) {// ------------------------------------------------------- render box
			for (int i=0;i<c.getLines().size();i++) {
				g2.draw(renderLine(c.getLines().get(i)));
			}
		}
		
		if (showEq == true) {
			//g2.setStroke(new BasicStroke(1));
			for (int i=0;i<lines.size();i++) {// ------------------------------------------------------- render equation
				Line l = fitGraph(lines.get(i));
				if (!(Math.abs(l.getv1().getY()) >= max) && !(Math.abs(l.getv2().getY()) >= max)) {
					g2.draw(renderLine(l));
				}
				
				
			}
			//g2.setStroke(new BasicStroke(1));
		}
		
		
		if (showAxes == true) {// ------------------------------------------------------- render axes (2)
			drawAxes(g2);
			
		}
		for (int i =0;i<graphs.size();i++) {
			g.drawString(graphs.get(i), 0,15 * (i+1) - 5);
		}
		
		
	}
	
	

}
