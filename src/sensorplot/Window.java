package sensorplot;

import java.awt.LayoutManager;

import javax.swing.JFrame;

public class Window {
	
	private JFrame window;
	private boolean hasBeenReDrawn;
	
	public Window(int width, int height, String title, LayoutManager layout) {
		
		window = new JFrame(title);
		window.setSize(width, height);
		window.setLayout(layout);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.requestFocus();	
	}
	
	public JFrame getWindow() {
		return this.window;
	}
	
	public void show() {
		window.setVisible(true);
	}

	public synchronized boolean getHasBeenReDrawn() {
		return hasBeenReDrawn;
	}

	public void setHasBeenReDrawn(boolean hasBeenReDrawn) {
		this.hasBeenReDrawn = hasBeenReDrawn;
	}
	
}
