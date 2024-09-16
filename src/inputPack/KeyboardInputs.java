package inputPack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import componentPack.Canvas;

public class KeyboardInputs implements KeyListener{
	private Canvas c;
	
	
	public KeyboardInputs(Canvas c) {
		this.c = c;System.out.println("keylistener madre");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			c.ytheta--;
			c.Chroma--;
			c.repaint();
			if (c.Chroma >= 360) {
				c.Chroma = c.Chroma - 360;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
			c.ytheta++;
			c.Chroma++;
			c.repaint();
			if (c.Chroma >= 360) {
				c.Chroma = c.Chroma - 360;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
			c.xtheta--;
			c.Chroma--;
			c.repaint();
			if (c.Chroma >= 360) {
				c.Chroma = c.Chroma - 360;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
			c.xtheta++;
			c.Chroma++;
			c.repaint();
			if (c.Chroma >= 360) {
				c.Chroma = c.Chroma - 360;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
