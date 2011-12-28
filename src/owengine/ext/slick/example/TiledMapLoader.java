package owengine.ext.slick.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.tiled.TiledMap;

import owengine.ext.slick.ActionAnimationsView;
import owengine.ext.slick.PcSpritesView;
import owengine.model.entities.MessageEntity;
import owengine.model.entities.character.PlayerChar;
import owengine.model.entities.character.NonPlayerChar;
import owengine.model.story.EventMap;
import owengine.model.story.StoryEvent;
import owengine.view.CharView;

public class TiledMapLoader {

	public static void loadMap(TiledMap tiledMap, EventMap gameMap,
			ArrayList<CharView> charViews, PlayerChar pc) {
		for (int i = 0; i < tiledMap.getObjectGroupCount(); i++)
			for (int j = 0; j < tiledMap.getObjectCount(i); j++)
				processObject(tiledMap, gameMap, i, j, charViews, pc);
	}

	private static void processObject(final TiledMap tiledMap, EventMap gameMap,
			final int i, final int j, ArrayList<CharView> charViews, final PlayerChar pc) {
		String type = tiledMap.getObjectType(i, j);
		if (type.equals("MessageEntity")) {
			ArrayList<String> msg = new ArrayList<String>(
				Arrays.asList(tiledMap.getObjectProperty(i, j, "msg", "").split("\\\\n"))
			);
			gameMap.addEntity(new MessageEntity(null, msg.get(0)));
			return;
		} else if (type.equals("NonPlayerChar")) {
			int id = Integer.parseInt(tiledMap.getObjectProperty(i, j, "id", null));
			ArrayList<String> msg = new ArrayList<String>(
				Arrays.asList(tiledMap.getObjectProperty(i, j, "msg", "").split("\\\\n"))
			);
			NonPlayerChar character;
			if (tiledMap.getObjectProperty(i, j, "path", null) == null)
				character = new NonPlayerChar(id, msg);
			else character = new NonPlayerChar(id, msg, loadMoves(tiledMap, i, j));
			gameMap.addEntity(character);
			charViews.add(new CharView(character, new ActionAnimationsView(),
					new PcSpritesView()));
			return;
		/*} else if (type.equals("WarpTile")) {
			gameMap.putWarpTile(
					new Point(tiledMap.getObjectX(i, j)/16,
							tiledMap.getObjectY(i, j)/16),
					new WarpTile(
						new Point(Integer.parseInt(
								tiledMap.getObjectProperty(i, j, "target_x", null)),
							Integer.parseInt(
								tiledMap.getObjectProperty(i, j, "target_y", null))),
						OverworldState.getMap(
							tiledMap.getObjectProperty(i, j, "target_map", null))));
			return;
		} else if (type.equals("ConnectTile")) {
			gameMap.putDoorTile(
					new Point(tiledMap.getObjectX(i, j)/16,
							tiledMap.getObjectY(i, j)/16),
					new DoorTile(
						new Point(Integer.parseInt(
							tiledMap.getObjectProperty(i, j, "target_x", null)),
						Integer.parseInt(
							tiledMap.getObjectProperty(i, j, "target_y", null))),
						OverworldState.getMap(
							tiledMap.getObjectProperty(i, j, "target_map", null)),
						MoveDir.valueOf(tiledMap.getObjectProperty(i, j, "dir", null))));
			return;
		} else if (type.equals("ItemEntity")) {
			ArrayList<String> msg = new ArrayList<String>(
				Arrays.asList(tiledMap.getObjectProperty(i, j, "msg", "").split("\\\\n"))
			);
			try {
				gameMap.addEntity(new ItemEntity(tiledMap.getObjectX(i, j)/16,
					tiledMap.getObjectY(i, j)/16,
					(StoryEvent) Class.forName(
						"game.model.story"+
						tiledMap.getObjectProperty(i, j, "event", null)
					).getConstructors()[0].newInstance(-1, true), msg));
			} catch (Exception e) {e.printStackTrace();}
			return;
		*/} else if (!type.equals("")) {
			try {
				/*
				MyStoryEvent event = new MyStoryEvent(
					Integer.parseInt(
						tiledMap.getObjectProperty(i, j, "id", null)),
					Boolean.parseBoolean(
						tiledMap.getObjectProperty(i, j,
								"active", null)));
				event.setScriptName(type);
				*/
				type = "game.model.story."+type;
				if (new ArrayList<Class<?>>(Arrays.asList(
						Class.forName(type).getInterfaces())).contains(StoryEvent.class)) {
					gameMap.addEventTile(
							tiledMap.getObjectX(i, j)/16, tiledMap.getObjectY(i, j)/16,
							(StoryEvent)
								Class.forName(type).getConstructors()[0].newInstance(
										/*Integer.parseInt(
											tiledMap.getObjectProperty(i, j, "id", null)),
										Boolean.parseBoolean(
											tiledMap.getObjectProperty(i, j,
													"active", null))*/
							gameMap)/* event*/);
					return;
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		for (int x = 0; x < tiledMap.getObjectWidth(i, j)/16; x++)
			for (int y = 0; y < tiledMap.getObjectHeight(i, j)/16; y++)
				gameMap.block(new Point(
						tiledMap.getObjectX(i, j)/16+x,
						tiledMap.getObjectY(i, j)/16+y));
	}

	private static ArrayList<Point> loadMoves(TiledMap tiledMap, int i, int j) {
		ArrayList<Point> moves = new ArrayList<Point>();
		for (String p : tiledMap.getObjectProperty(i, j, "path", null).split(",")) {
			String[] coords = p.split(":");
			moves.add(new Point(Integer.parseInt(coords[0]),
					Integer.parseInt(coords[1])));
		}
		return moves;
	}

}
