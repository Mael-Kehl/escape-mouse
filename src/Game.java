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
    private Room lastRoom;
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
        Room cave, kitchen, lounge, corridor, bedroom;
        int[] weight = {0};
        
        // create the rooms
        cave = new Room("in the cave of the house");
        kitchen = new Room("in the kitchen");
        lounge = new Room("in the lounge");
        corridor = new Room("in the corridor");
        bedroom = new Room("in the bedroom");
        
        // initialise room exits
        //cave.setExits(null, lounge, null, null, null, null);
        //kitchen.setExits(null, corridor, lounge, cave, null, null);
        //lounge.setExits(null, kitchen, null, null, null, null);
        //corridor.setExits(kitchen, bedroom, null, null, null, null);
        //bedroom.setExits(null, null, null, corridor, null, null);
        
        weight[0]=10;
        cave.setExit("up", lounge);
        //cave.setItems("Une étagère avec des outils", weight);
        kitchen.setExit("east", corridor);
        kitchen.setExit("west", lounge);
        kitchen.setExit("south", cave);
        weight[0]=10;
        //kitchen.setItems("Un plan de travail", weight);
        weight[0]=1;
        //kitchen.setItems("Une pomme", weight);
        lounge.setExit("west", kitchen);
        weight[0]=10;
        //lounge.setItems("Un magnifique sofa en cuir", weight);
        corridor.setExit("north", bedroom);
        corridor.setExit("east", kitchen);
        weight[0]=5;
        //corridor.setItems("Une trappe à souris",weight);
        bedroom.setExit("south", corridor);
        weight[0]=2;
        //bedroom.setItems("  ",weight);
        

        currentRoom = cave;  // start game outside
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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        WriteDescription();
    }
    
    private void look()
    {
    System.out.println(currentRoom.getLongDescription());
    }
    private void eat()
    {
    System.out.println("You have eaten, you're not hungry anymore");
    }
    private void back() {
    	currentRoom=lastRoom;
    }
    
    private void printLocationInfo() {
    	System.out.println("You are "+currentRoom.getDescription());
    	System.out.print("Exits: ");
    	if (currentRoom.northExit !=null) {
    		System.out.print("north ");
    	}
    	if (currentRoom.eastExit !=null) {
    		System.out.print("east ");
    	}
    	if (currentRoom.southExit !=null) {
    		System.out.print("south ");
    	}
    	if (currentRoom.westExit !=null) {
    		System.out.print("west ");
    	}
    	if (currentRoom.upExit !=null) {
    		System.out.print("up ");
    	}
    	if (currentRoom.downExit !=null) {
    		System.out.print("down ");
    	}
    	System.out.println();
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
        	lastRoom=currentRoom;
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            back();
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
        parser.showCommands();
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
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }
        if(direction.equals("up")) {
            nextRoom = currentRoom.upExit;
        }
        if(direction.equals("down")) {
            nextRoom = currentRoom.downExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            WriteDescription();
        }
    }
    
    private void WriteDescription() {
        printLocationInfo();
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
