package owengine.core.world;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TileLayer {

	private String name;
	private Set<Tile> tiles = new HashSet<Tile>();

	public TileLayer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public Tile tileAtPos(Point pos) {
		for (Tile tile : tiles) {
			if (tile.getPos().equals(pos)) {
				return tile;
			}
		}
		return null;
	}

	public Set<Tile> getTiles() {
		return Collections.unmodifiableSet(tiles);
	}

	public void touchPos(Entity e) {
		tileAtPos(e.getPosition()).touch(e);
	}

}
