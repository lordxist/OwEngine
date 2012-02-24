package framework.world;

import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Vector2f;

public class Entity {

	protected RenderComponent renderComponent;
	protected Set<Component> components = new HashSet<Component>();
	protected int x;
	protected int y;
	protected Vector2f deltaPos = new Vector2f(0, 0);
	protected World world;

	public Entity(int x, int y, RenderComponent component) {
		this.x = x;
		this.y = y;

		this.renderComponent = component;
		component.setEntity(this);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void addComponent(Component component) {
		components.add(component);
		component.setEntity(this);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void paint() {
		renderComponent.paint();
	}

	public void update(int delta) {
		for (Component component : components) {
			component.update(delta);
		}
	}

	public Vector2f getDeltaPos() {
		return deltaPos;
	}

	public void setDeltaPos(Vector2f deltaPos) {
		this.deltaPos = deltaPos;
	}

}
