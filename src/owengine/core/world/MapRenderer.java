package owengine.core.world;

import java.awt.Graphics;

import javax.vecmath.Vector2f;

public abstract class MapRenderer {

	public static abstract class CenteredRenderer extends MapRenderer {

		protected Vector2f center;
		protected Entity centerEntity;

		public void setCenter(Vector2f center) {
			this.center = center;
		}

		public void setCenterEntity(Entity centerEntity) {
			this.centerEntity = centerEntity;
		}

		@Override
		public int getX() {
			return (int)(centerEntity.getExactPos().x*fieldSize) - (width/2);
		}

		@Override
		public int getY() {
			return (int)(centerEntity.getExactPos().y*fieldSize) - (height/2);
		}

	}

	protected GameMap map;
	protected int fieldSize;
	protected int width;
	protected int height;

	public GameMap getMap() {
		return map;
	}

	void setMap(GameMap map) {
		this.map = map;
	}

	public void paint(Graphics g) {
		paintTiles(g);
		paintEntities(g);
	}

	protected void paintTiles(Graphics g) {
		for (TileLayer layer : map.getLayers()) {
			for (Tile tile : layer.getTiles()) {
				tile.paint(g);
			}
		}
	}

	protected void paintEntities(Graphics g) {
		for (Entity e : map.getEntities()) {
			e.paint(g);
		}
	}

	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFieldSize() {
		return fieldSize;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return 0;
	}

	public int getY() {
		return 0;
	}

}
