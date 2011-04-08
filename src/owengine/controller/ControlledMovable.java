package owengine.controller;

import java.awt.Point;
import java.util.ArrayList;

import owengine.model.movement.MovablePosition;

public interface ControlledMovable {

	MovablePosition<?> getPos();

	ArrayList<Point> getMoves();

}
