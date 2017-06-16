package fieldofviewdemo.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import fieldofviewdemo.tilemap.Tile;

public class TilePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Tile[][] tiles;
	private Tile viewpoint;
	
	public TilePanel(Tile[][] tiles) {
		super();
		this.tiles = tiles;
		int tileWidth = tiles[0][0].getWidth();
		int tileHeight = tiles[0][0].getHeight();
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tileX = (e.getX()/tileWidth);
				int tileY = (e.getY()/tileHeight);
				if (e.getButton()==MouseEvent.BUTTON1) {
					if (tiles[tileX][tileY]!=viewpoint) {
						tiles[tileX][tileY].toggleBlockingView();
						if (viewpoint!=null) {
							Tile.calculateVisibleTiles(tiles, viewpoint);
						}
						repaint();
					}
				} else {
					if (!tiles[tileX][tileY].isBlockingView()) {
						viewpoint = tiles[tileX][tileY];
						Tile.calculateVisibleTiles(tiles, viewpoint);
						repaint();
					}
				}
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.black);
		for (int x=0; x<tiles.length; x++) {
			for (int y=0; y<tiles[0].length; y++) {
				Tile tile = tiles[x][y];
				int xPos = tile.getxPosition();
				int yPos = tile.getyPosition();
				int width = tile.getWidth();
				int height = tile.getHeight();
				
				if (tile.isBlockingView()) {
					// draw view-blocking tiles in black...
					g.setColor(Color.BLACK);
				} else if (tile==viewpoint) {
					// ...and the viewpoint in blue...
					g.setColor(Color.BLUE);
				} else {
					// ...and visible tiles in green
					if (tile.isVisible())
						g.setColor(Color.GREEN);
					else
						g.setColor(Color.RED);
				}
				g.fillRect(xPos, yPos, width, height);
				// black border
				g.setColor(Color.BLACK);
				g.drawRect(xPos, yPos, width, height);
			}
		}
	}
}
