package componentPack;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Window extends JFrame {
	private Canvas c;
	private SidePanel p;

	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("jajajajjajajajajjajajajajajajajjajjajajajjajajajajaj");
		setSize(997,832);
		setResizable(false);
		
		c = new Canvas();
		p = new SidePanel(c);
		
		
		
		
		
		add(p,BorderLayout.WEST);
		add(c);
		
		
		
		
		
		
		setVisible(true);
	}

}
