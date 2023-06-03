import java.util.Stack;
import java.util.Vector;

/**
 * Class for the player of the game
 * A player is able to store items in his bag, under 1000 grams
 * A player also knows which rooms he has visited and in which room he is currently
 * Finally a player has health points
 */
public class Player {
    private Room currentRoom;
    private Stack<Room> previousRooms;
    private Vector<Item> items;
    private int currentWeight = 0; //current weight carried by the player
    private int maxWeight = 1000; //Maximum weight the player can carry in grams
    private final int MAX_ITEMS_NUMBER = 5;
    private int life_points = 1;
    private final int MAX_LIFE_POINTS = 3;

    public Player(){
        previousRooms = new Stack<Room>();
        items = new Vector<Item>();
        Item cheese = new Item("cheese", "Piece of cheese", 10 );
        cheese.setImgPath("./images/cheese-item.png");
        items.add(cheese);
        items.add(cheese);
        items.add(cheese);
        items.add(cheese);
        items.add(cheese);
    }

    /**
     * Picks up an item if it is not too heavy
     * @param item Item to pick
     */
    public void pickUpItem(Item item){
        if (currentWeight < maxWeight && item.getWeight() <= maxWeight) {
            this.items.add(item);
            this.currentWeight += item.getWeight();
            //If we pick an item, it is no more in the room, so we remove it from the room items list
            this.currentRoom.removeItem(item);
        }
        else {
            System.out.println("This object is too heavy !");
        }
    }

    /**
     * Player drops an item
     * @param item to drop
     */
    public void dropItem(Item item) {
        if (!items.isEmpty()) {
            items.remove(item);
            // If we drop an item, it is in the room 
            this.currentRoom.addItem(item);
        }
    }

        /**
     * Returns an item carried by a player from its name
     * @param name String 
     * @return Item
     */
    public Item getItemByName(String name){
        for (Item item: items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

      /**
     * Returns a list of all items carried by player
     * @return String of items
     */
    public String getItemsCarriedString() {
        String itemList = "";

        for (Item item: items){
            itemList += item.getName() + " ";
        }
        return itemList;
    }

    public Vector<Item> getItemsCarried() {
        return this.items;
    }

    /**
     * Returns room where the player is currently
     * @return Room
     */
    public Room getCurrentRoom(){
        return this.currentRoom;
    }

    /**
     * Sets the room where the player is currently
     * @param currentRoom Room
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Adds a room in the list of those which the player visited (previous)
     * @param previousRoom Room
     */
    public void addPreviousRoom(Room previousRoom) {
        this.previousRooms.push(previousRoom);
    }

    /**
     * Removes the last room previously visited, basically if the player goes back to it,
     * the room becomes current, so we remove it from prviousRoom stack
     */
    public void popPreviousRoom() {
        if(!previousRooms.isEmpty()) this.previousRooms.pop();
    }

    /**
     * Returns the room vitied before the current one 
     * @return Room 
     */
    public Room getLastPreviousRoom(){
        return this.previousRooms.peek();
    }

    /**
     * Returns all rooms visited by the player
     * @return Stack<Room>
     */
    public Stack<Room> getAllPreviousRooms(){
        return this.previousRooms;
    }

    public int getLifePoints() {
        return life_points;
    }

    public void setLifePoints(int life_points) {
        this.life_points = life_points;
    }

    public void addLifePoint(){
        if (life_points < MAX_LIFE_POINTS) life_points++;
    }

    public void removeLifePoint(){
        if (life_points>0) life_points--;
    }


}
