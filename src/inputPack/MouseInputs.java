package inputPack;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import componentPack.Canvas;

public class MouseInputs extends MouseAdapter{
	private Canvas canvas;
	private Point origin;
	
	private double xDel = 0;
	private double yDel = 0;
	
	
	public void mouseWheelMoved(MouseWheelEvent e){
		int dir = -(e.getWheelRotation());
		
		if (dir < 0) {
			if (canvas.mag > 10) {
				canvas.mag += dir;
			}
		} else {
			if (canvas.mag < 70) {
				canvas.mag += dir;  
			}
		}
		
		if (canvas.mag > 10 && canvas.mag < 70) {
			canvas.mag += dir;
			canvas.repaint();
		}
		
		
		
	}
	public MouseInputs(Canvas canvas) {
		this.canvas = canvas;
	}
	public void mousePressed(MouseEvent e) {
		
		origin = e.getPoint();
		
	}

	public void mouseDragged(MouseEvent e){
		xDel = -(e.getPoint().x - origin.x);
		yDel = (e.getPoint().y - origin.y);
		canvas.xtheta += xDel;
		canvas.ytheta += yDel;
		canvas.Chroma += xDel + yDel;
		if (canvas.Chroma >= 360) {
			canvas.Chroma = canvas.Chroma - 360;
		}
		origin = e.getPoint();
		canvas.repaint();
	}
}
