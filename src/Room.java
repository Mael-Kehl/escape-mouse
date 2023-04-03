import java.util.HashMap;
import java.util.Set;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
	private HashMap<String, Room> exits;
	private HashMap<String,int[]> items;
    private String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public Room upExit;
    public Room downExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param up The up exit.
     * @param down The down exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
    {
        if(north != null) {
            exits.put("north", north);
        }
        if(east != null) {
            exits.put("east", east);
        }
        if(south != null) {
            exits.put("south", south);
        }
        if(west != null) {
            exits.put("west", west);
        }
        if(up != null) {
            exits.put("up", up);
        }
        if(down != null) {
            exits.put("down", down);
        }
    }
    public void setExit(String direction, Room neighboor) {
    	exits.put(direction, neighboor);
    }
    /* Ne marche pas
    public void setItems(String description, int[] weight) {
    	int[] weight2 = {weight[0]};
    	new Items(description, weight[0]);
    	items.put(description, weight2);
    }
    */
    public Room getExits(String direction) {
    	if (direction.equals("north")) {
    		return northExit;
    	}
    	if (direction.equals("east")) {
    		return eastExit;
    	}
    	if (direction.equals("south")) {
    		return southExit;
    	}
    	if (direction.equals("west")) {
    		return westExit;
    	}
    	if (direction.equals("up")) {
    		return upExit;
    	}
    	if (direction.equals("down")) {
    		return downExit;
    	}	
    	return null;
    }
    

    public Room leaveRoom(String direction) {    	
    	return exits.get(direction);
    }
    
    public Room getExit(String direction) {
    	
    }
    
    
    
    /**
    * Return a description of the room’s exits,
    * for example, "Exits: north west".
    * @return A description of the available exits.
    */
    public String getExitString() {
    	String returnString ="Exits: ";
    	Set<String> keys = exits.keySet();
    	for(String exit : keys) {
    		returnString +=" "+exit;
    	}
    	return returnString;
    }
    public String getItemString() {
    	String returnString="Items: ";
    	Set<String> keys=items.keySet();
    	for(String items : keys) {
    		returnString +=" "+items;
    	}
    	return returnString;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    public String getLongDescription() {
    	return "you are "+description+".\n"+getItemString()+".\n"+getExitString();
    }

}
