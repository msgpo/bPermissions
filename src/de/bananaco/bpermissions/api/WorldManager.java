package de.bananaco.bpermissions.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * This is the api that will be accessed
 * by plugins utilising the new bPermissions API
 * (isn't it pretty?)
 * 
 * Basically, this passes you an instance of the World object, which
 * contains instances of User and Group, which you then do what you want
 * with, you can add new permissions, or groups, to both users and groups.
 * 
 * Just look into the comments if you get stuck, it's pretty easy, I even
 * added .getUser and .getGroup for those people of you who are super lazy :P
 * 
 * The main classes to look at if you're curious about the internals of User and Group
 * would be the Calculable class
 */
public class WorldManager {
	
	public static WorldManager instance = null;
	
	private World defaultWorld = null;
	
	private Map<String, String> mirrors = new HashMap<String, String>();
	
	Map<String, World> worlds = new HashMap<String, World>();
	
	protected WorldManager() {

	}
	/**
	 * This returns the Singleton instance of
	 * WorldManager. This will be the same across
	 * implementations! :D
	 * @return WorldManager
	 */
	public static WorldManager getInstance() {
		if(instance == null)
			instance = new WorldManager();
		return instance;
	}
	
	public void setMirrors(Map<String, String> mirrors) {
		this.mirrors = mirrors;
	}
	
	public void setDefaultWorld(World world) {
		defaultWorld = world;
	}
	
	public World getDefaultWorld() {
		return defaultWorld;
	}
	
	public World getWorld(String name) {
		if(name == null)
			return getDefaultWorld();
		
		name = name.toLowerCase();
		
		if(mirrors.containsKey(name))
			name = mirrors.get(name);
		
		return worlds.get(name);
	}
	
	public boolean containsWorld(String name) {
		if(name == null)
			return false;
		
		name = name.toLowerCase();
		
		if(mirrors.containsKey(name))
			name = mirrors.get(name);
		
		return worlds.containsKey(name);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<World> getAllWorlds() {
		Set<World> worlds = new HashSet();
		for(String key : this.worlds.keySet()) {
			worlds.add(this.worlds.get(key));
		}
		return worlds;
	}
	
	/**
	 * Used to store a reference to a World object by name
	 * @param name
	 * @param world
	 */
	public void createWorld(String name, World world) {
		name = name.toLowerCase();
		worlds.put(name, world);
		world.load();
	}
	
	/**
	 * Cleans up all worlds of unusted groups
	 */
	public void cleanup() {
		for(World world : getAllWorlds())
			world.cleanup();
	}

}
