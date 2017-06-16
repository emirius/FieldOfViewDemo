package fieldofviewdemo.tilemap;

import java.awt.geom.Point2D;

/**
 * A simple Tile class that consists of a x and y position, width, height and
 * visibility properties. The <code>isVisible</code> property defines whether or
 * not this tile can be seen and is calculated programmatically. The
 * <code>blockingView</code> property specifies whether or not this tile is
 * blocking the view for other tiles.
 * 
 * @author dude
 *
 */
public class Tile {
	public static final int DEFAULT_WIDTH = 32;
	public static final int DEFAULT_HEIGHT = 32;

	private int xPosition;
	private int yPosition;
	private int width;
	private int height;
	private boolean isVisible;
	private boolean blockingView;

	public Tile(int x, int y, int w, int h, boolean blockingView) {
		init(x, y, w, h, blockingView);
	}

	public Tile(int x, int y) {
		init(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, false);
	}

	private void init(int x, int y, int w, int h, boolean blockingView) {
		xPosition = x;
		yPosition = y;
		width = w;
		height = h;
		this.isVisible = true;
		this.blockingView = blockingView;
	}

	/**
	 * Calculates the absolute, shortest distance between this tile's center and
	 * a line defined by two points.
	 * 
	 * @param p1
	 *            The first point of the line.
	 * @param p2
	 *            The second point of the line.
	 */
	public double centerDistanceToLine(Point2D.Float p1, Point2D.Float p2) {
		Point2D.Float centerCoords = getCenterCoordinates();
		double x0 = centerCoords.getX();
		double y0 = centerCoords.getY();
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		// some math magic, source:
		// http://mathworld.wolfram.com/Point-LineDistance2-Dimensional.html
		double d = Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1))
				/ Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return d;
	}

	public Point2D.Float getCenterCoordinates() {
		float x = (xPosition + (width * 0.5f));
		float y = (yPosition + (height * 0.5f));
		return new Point2D.Float(x, y);
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public boolean isBlockingView() {
		return blockingView;
	}

	public void setBlockingView(boolean b) {
		blockingView = b;
	}

	public void toggleBlockingView() {
		blockingView = !blockingView;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean b) {
		isVisible = b;
	}

	/**
	 * Determines which tiles of the given tilemap are visible and which are
	 * not, based on the given viewpoint tile.
	 * 
	 * @param tilemap
	 *            Contains all tiles (including the ones that are blocking the
	 *            view).
	 * @param viewpoint
	 *            The tile that serves as the viewpoint.
	 */
	public static void calculateVisibleTiles(Tile[][] tilemap, Tile viewpoint) {
		long t1 = System.currentTimeMillis();
		// We iterate over all tiles in the the tilemap:
		for (int x = 0; x < tilemap.length; x++) {
			for (int y = 0; y < tilemap[0].length; y++) {
				Tile tile = tilemap[x][y];
				// Set the visibility of this tile:
				tile.setVisible(!viewBlocked(viewpoint, tile, tilemap));
			}
		}
		// a basic time check to see how long this method needs:
		System.out.println(System.currentTimeMillis() - t1 + "ms");
	}

	private static boolean viewBlocked(Tile viewpoint, Tile tile,
			Tile[][] tilemap) {
		// First we calculate the viewpoint and tile positions in the tilemap:
		int vX = viewpoint.getxPosition() / viewpoint.getWidth();
		int vY = viewpoint.getyPosition() / viewpoint.getHeight();
		int tX = tile.getxPosition() / tile.getWidth();
		int tY = tile.getyPosition() / tile.getHeight();
		// Then we limit the area that we consider for the field of view check
		// based on the viewpoint and tile position:
		int minX = Math.min(vX, tX);
		int maxX = Math.max(vX, tX);
		int minY = Math.min(vY, tY);
		int maxY = Math.max(vY, tY);
		// We iterate over each tile in the limited area:
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				Tile otherTile = tilemap[x][y];
				if (otherTile.isBlockingView() && otherTile != viewpoint
						&& otherTile != tile) {
					// If current tile is a blocking-view-tile we
					// calculate its distance to the line between the
					// viewpoint's center and the center of the tile we want to
					// check:
					double d = otherTile.centerDistanceToLine(
							viewpoint.getCenterCoordinates(),
							tile.getCenterCoordinates());
					if (d < (tile.getWidth() * 0.5f)) {
						// If the distance is smaller than half of the tile's
						// width (i.e. we basically use an inner circle of the
						// tile for the check) the tile we wanted to check is
						// indeed not visible:
						return true;
					}
				}
			}
		}
		// If no tile is blocking the view, we return false:
		return false;
	}
}
