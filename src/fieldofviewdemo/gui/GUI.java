package fieldofviewdemo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import fieldofviewdemo.tilemap.Tile;

public class GUI {
	private JFrame frame;
	private TilePanel panel;

	public GUI(Tile[][] tiles) {
		initializeFrame(tiles);
		
	}

	private void initializeFrame(Tile[][] tiles) {
		int width = tiles.length*tiles[0][0].getWidth();
		int height = tiles[0].length*tiles[0][0].getHeight();
		
		// Configure the frame:
		frame = new JFrame("Field of vision - Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Configure the panel on which we draw:
		panel = new TilePanel(tiles);
		panel.setPreferredSize(new Dimension(width, height));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(width+frame.getInsets().left+frame.getInsets().right+1, height+frame.getInsets().top+frame.getInsets().bottom+1);
		
		frame.setVisible(true);
	}
}