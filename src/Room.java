import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

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
    private String description;
    private HashMap<String, Room> exits;
    private Vector<Item> items; 

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
        items = new Vector<Item>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction direction where you want to set an exit
     * @param neighbor exit you want to set in the direction specified
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Returns the exit in one direction
     * @param direction direction in the possible exit is
     * @return Room or null
     */
    public Room getExit(String direction){
        return exits.get(direction);
    }

    /**
     * Adds an item to the room
     * @param item 
     */
    public void addItem(Item item){
        this.items.add(item);
    }

    /**
     * Returns the description of an item in the room
     * @param name name of the item
     * @return Description of the item
     */
    public String getItemDescription(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) return item.getDescription();
        }
        return new String("Item not found");
    }

    /**
     * Returns a list of all items in a room
     * @return String of items
     */
    public String getItemList() {
        String itemList = "";

        for (Item item: items){
            itemList += item.getName() + " ";
        }
        return itemList;
    }

    /**
     * Return a description of the room’s exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */
    public String getExitString(){

        String returnString = "Exits : ";
        Set<String> keys = exits.keySet();
        for(String exit: keys) {
            returnString += " " + exit;
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

    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
    return "You are in " + description + ".\n" + getExitString();
    }

}
