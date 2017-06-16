package fieldofviewdemo;

import fieldofviewdemo.gui.GUI;
import fieldofviewdemo.tilemap.Tile;

/**
 * <p>
 * This is a simple demo to show a basic (and naive) "field of view"-algorithm.
 * Tiles that are visible will be rendered <code>green</code>, tiles that can
 * not be seen will be rendered <code>red</code>, tiles that are blocking the
 * view will be rendered <code>black</code> and the viewpoint will be rendered
 * <code>blue</code>.<br>
 * <b>Note:</b> Although the tiles that are used in this demo have a width and
 * height (indicating that the tiles do not have to be squares) the algorithm
 * expects them to have the same value (thus resulting in squares).
 * </p>
 * <p>
 * The demo can be controlled using the left and right mouse buttons on the
 * tilemap:
 * <ul>
 * <li><b>Left button:</b> Toggles the tile's <code>isBlockingView</code>
 * property.</li>
 * <li><b>Right button:</b> Sets the current tile as the viewpoint.</li>
 * </ul>
 * </p>
 * <p>
 * The algorithm itself is implement in
 * {@link Tile#calculateVisibleTiles(Tile[][], Tile)}.
 * </p>
 * 
 * @author dude
 *
 */
public class Main {
	/*
	 * These two values define the size of the tilemap:
	 */
	public static final int TILEMAP_SIZE_X = 32;
	public static final int TILEMAP_SIZE_Y = 24;

	public static void main(String[] args) {
		// load a simple GUI to show the algorithm in action:
		new GUI(createTilemap());
	}

	/*
	 * Initializes the tilemap that we use.
	 */
	private static Tile[][] createTilemap() {
		Tile[][] tiles = new Tile[TILEMAP_SIZE_X][TILEMAP_SIZE_Y];
		for (int x = 0; x < TILEMAP_SIZE_X; x++) {
			for (int y = 0; y < TILEMAP_SIZE_Y; y++) {
				Tile tile = new Tile(x * Tile.DEFAULT_WIDTH, y
						* Tile.DEFAULT_HEIGHT);
				tiles[x][y] = tile;
			}
		}

		return tiles;
	}
}
