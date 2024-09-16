package componentPack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SidePanel extends JPanel{
	private JPanel contentPanel;
	
	private Canvas canvas;
	
	private JButton magnifyIn;
	private JButton magnifyOut;
	private JPanel magnifyPanel;
	private JTextField jta;
	private JPanel jtapanel;
	private JButton addButton;
	private JPanel addPanel;
	private JPanel showPanel;
	private JPanel showPanel2;
	private JButton showBox;
	private JButton showAxes;
	private JPanel colourPanel;
	private JButton chroma;
	private JButton reset;
	private JPanel precisionPanel;
	private JTextField precision;
	private JButton precisionb;
	
	
	public SidePanel(Canvas canvas) {
		this.canvas = canvas;
		
		setBackground(Color.DARK_GRAY);
		
		
		contentPanel = new JPanel();
		jtapanel = new JPanel();
		magnifyPanel = new JPanel();
		addPanel = new JPanel();
		showPanel = new JPanel();
		showPanel2 = new JPanel();
		colourPanel = new JPanel();
		precisionPanel = new JPanel();
		
		addButton = new JButton("add expression");
		magnifyIn = new JButton("zoom in");
		magnifyOut = new JButton("zoom out");
		showBox = new JButton("show/hide box");
		showAxes = new JButton("show/hide axes");
		chroma = new JButton("chroma");
		reset = new JButton("reset");
		precision = new JTextField(5);
		precisionb = new JButton("change precision");
		
		addButton.setBackground(Color.white);
		magnifyIn.setBackground(Color.white);
		magnifyOut.setBackground(Color.white);
		showBox.setBackground(Color.white);
		showAxes.setBackground(Color.white);
		chroma.setBackground(Color.white);
		reset.setBackground(Color.white);
		precisionb.setBackground(Color.white);
		
		jta = new JTextField(16);
		
		precision.setText(String.valueOf(canvas.precision));
		precisionb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.precision = Integer.valueOf(precision.getText());
				canvas.updateRender();
				canvas.repaint();
				canvas.requestFocus();
			}
		
		});
		
		
		magnifyIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (canvas.bounds > 1 && canvas.bounds <= 8) {
					canvas.bounds--;
				} else if (canvas.bounds > 8 || canvas.bounds <= 1) {
					canvas.bounds = canvas.bounds /2;
				}
				canvas.updateRender();
				canvas.requestFocus();
				canvas.repaint();
			}
			
		});
		magnifyOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.bounds < 8 && canvas.bounds >1) {
					canvas.bounds++;
				} else if (canvas.bounds >= 8 || canvas.bounds <= 1){
					canvas.bounds = canvas.bounds *2;
				}
				canvas.updateRender();
				canvas.requestFocus();
				canvas.repaint();
			}
			
		});
		
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.resetRender();
				canvas.repaint();
				canvas.requestFocus();
			}
			
		});
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String exp = jta.getText();
				
				if (!exp.isEmpty()) {
					canvas.addRender(exp);
					canvas.repaint();
					canvas.requestFocus();
				}
				
			}
			
		});
		showBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.toggleBox();
				canvas.repaint();
				canvas.requestFocus();
			}
		});
		showAxes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.toggleAxes();
				canvas.repaint();
				canvas.requestFocus();
			}
		});
		chroma.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.toggleChroma();
				canvas.repaint();
				canvas.requestFocus();
			}
			
		});
		
		
		
		
		contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.PAGE_AXIS));
		contentPanel.setBackground(Color.DARK_GRAY);
		magnifyPanel.setBackground(Color.DARK_GRAY);
		jtapanel.setBackground(Color.DARK_GRAY);
		addPanel.setBackground(Color.DARK_GRAY);
		showPanel.setBackground(Color.DARK_GRAY);
		showPanel2.setBackground(Color.DARK_GRAY);
		colourPanel.setBackground(Color.DARK_GRAY);
		precisionPanel.setBackground(Color.DARK_GRAY);
		
		jtapanel.add(jta);
		magnifyPanel.add(magnifyIn);
		magnifyPanel.add(magnifyOut);
		addPanel.add(addButton);
		addPanel.add(reset);
		showPanel.add(showBox);
		showPanel2.add(showAxes);
		colourPanel.add(chroma);
		precisionPanel.add(precision);
		precisionPanel.add(precisionb);
		
		contentPanel.add(magnifyPanel);
		contentPanel.add(jtapanel);
		contentPanel.add(addPanel);
		contentPanel.add(showPanel);
		contentPanel.add(showPanel2);
		contentPanel.add(colourPanel);
		contentPanel.add(precisionPanel);
		add(contentPanel);
		
	}

}
