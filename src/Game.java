import java.util.Vector;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room attic, laundryRoom, cellar, parentalBedRoom, catRoom, kitchen;
      
        // create the rooms
        attic = new Room("the Attic were you woke up after being captured by a weird kid");
        laundryRoom = new Room("the Laundry Room where all shoes from the familly are stored");
        cellar = new Room("the Cellar of the house, where the cheese is stored");
        parentalBedRoom = new Room("The bed room of the child's parents");
        catRoom = new Room("The Room where the evil cat sleeps, try not to enter if you want to stay alive");
        kitchen = new Room("The Kitchen with a fridge, interesting");

        // initialise room exits
        attic.setExits("south", laundryRoom);
        laundryRoom.setExits("south", cellar);
        cellar.setExits("north", parentalBedRoom);
        parentalBedRoom.setExits("north", catRoom);
        parentalBedRoom.setExits("east", kitchen);
        catRoom.setExits("south", parentalBedRoom);

        currentRoom = attic;  // start game outside

        //Creation of all items 
        Item shelves, shoes, tires, bed, catTree, fridge;

        shelves = new Item("shelves", "Shelves where shoes are stored");
        shoes = new Item("shoes", "Pair of old air jordan red/white");
        tires = new Item("tires", "Brand new Tires for a Mustang");
        bed = new Item("bed", "Bed with two places");
        catTree = new Item("cat tree", "Giant cat tree in beige color");
        fridge = new Item("fridge", "Fridge containing a lot of cheese");

        attic.addItem(shelves);
        laundryRoom.addItem(shoes);
        cellar.addItem(tires);
        parentalBedRoom.addItem(bed);
        catRoom.addItem(catTree);
        kitchen.addItem(fridge);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Escape Mouse !");
        System.out.println("Escape Mouse is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")){
            eat();
        }
        else if (commandWord.equals("items")) {
            items();
        }
        else if(commandWord.equals("inspect")) {
            inspectItem(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    private void inspectItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Inspect which item ?");
            return;
        }

        String itemName = command.getSecondWord();
        System.out.println(currentRoom.getItemDescription(itemName));
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.getExit("south");
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.getExit("west");
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /**
     * Try to look around current room to give informations
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void items() {
        System.out.println(currentRoom.getItemList());
    }   

    private void eat() {
        System.out.println("You've eaten cheese, you're now in good shape");
    }

    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
