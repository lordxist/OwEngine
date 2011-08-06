package owengine.model.story;

import static owengine.model.util.action.ActionUser.NULL;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import owengine.model.util.action.ActionUser;

public /*abstract*/ class BasicStoryEvent {

	protected static HashMap<Integer, BasicStoryEvent> instances =
		new HashMap<Integer, BasicStoryEvent>();

	private static BasicStoryEvent getById(int id) {
		return instances.get(id);
	}


	public BasicStoryEvent(int id, boolean active) {
		instances.put(id, this);
		this.active = active;
		ScriptEngineManager m = new ScriptEngineManager();
		engine = m.getEngineByName("jruby");
		engine.put("world", this);
		try {
			RandomAccessFile file = new RandomAccessFile("scripts/world.rb", "r");
			byte[] script = new byte[(int) file.length()];
			file.readFully(script);
			engine.eval(new String(script));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String script;
	private ScriptEngine engine;
	protected ArrayList<Command> cmdQueue = new ArrayList<Command>();
	protected ArrayList<Command> activeCmdQueue = new ArrayList<Command>();
	protected boolean running;
	protected boolean active;
	private String scriptName;
	private HashMap<String, CompiledScript> cScripts =
		new HashMap<String, CompiledScript>();

	public void update(int delta) {
		if (activeCmdQueue.isEmpty())
			running = false;
		if (!running) return;
		activeCmdQueue.get(0).update(delta);
	}

	public void activate() {
		active = true;
	}

	public void deactivate() {
		active = false;
	}

	public void activate(final int id) {
		cmdQueue.add(new Command(this, NULL) {
	
			@Override
			protected void init() {
				BasicStoryEvent.getById(id).activate();
			}
			
		});
	}

	public void deactivate(final int id) {
		cmdQueue.add(new Command(this, NULL) {
			@Override
			protected void init() {
				BasicStoryEvent.getById(id).deactivate();
			}
		});
	}

	public void addCmd(Command cmd) {
		cmdQueue.add(cmd);
	}

	public void run() {
		if (!active) return;
		cmdQueue.clear();
		initCmdQueue();
		activeCmdQueue = new ArrayList<Command>(cmdQueue);
		if (!activeCmdQueue.isEmpty())
			activeCmdQueue.get(0).init();
		running = true;
	}

	public static abstract class Command {
	
		protected BasicStoryEvent event;
		protected ActionUser actionUser;
		
		public Command(BasicStoryEvent e, ActionUser actionUser) {
			this.event = e;
			this.actionUser = actionUser;
		}
		
		public void update(int delta) {
			if (actionUser.getAction().isFinished()) {
				event.activeCmdQueue.remove(this);
				if (!event.activeCmdQueue.isEmpty())
					event.activeCmdQueue.get(0).init();
			}
		}
		
		protected ActionUser getActionUser() {
			return actionUser;
		}
		
		protected abstract void init();
	}

	protected void initCmdQueue() {
		try {
			CompiledScript cScript = cScripts.get(scriptName);
			//if (cScript != null) {
				cScript.eval();
			//}
			//else {
			//	engine.eval(script);
			//}
		} catch (Exception exc) {exc.printStackTrace();}
	}
	
	public void setScriptName(String name) {
		scriptName = name;
		try {
			RandomAccessFile file = new RandomAccessFile("scripts/"+scriptName+".rb", "r");
			byte[] script = new byte[(int) file.length()];
			file.readFully(script);
			this.script = new String(script);
			Compilable compilingEngine = (Compilable)engine;
			CompiledScript cScript = compilingEngine.compile(this.script);
			cScripts.put(name, cScript);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
