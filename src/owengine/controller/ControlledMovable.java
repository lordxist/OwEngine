package owengine.controller;

import java.awt.Point;
import java.util.ArrayList;

import owengine.model.movement.Movable;

public interface ControlledMovable extends Movable {

	ArrayList<Point> getMoves();

}
