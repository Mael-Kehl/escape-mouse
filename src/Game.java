import java.util.Vector;

public class Game
{
    private Parser parser;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room attic, livingRoom, cellar, parentalBedRoom, hallRoom, kitchen, garden;
      
        // create the rooms
        cellar = new Room("Cellar of the house");
        cellar.setImgPath("./images/cellar-background.png");

        hallRoom = new Room("Hall of the house");
        hallRoom.setImgPath("./images/hall-background.png");

        livingRoom = new Room("Living room of the house");
        livingRoom.setImgPath("./images/livingroom-background.png");

        attic = new Room("Attic of the house");
        attic.setImgPath("./images/attic-background.png");

        parentalBedRoom = new Room("The bed room of the child's parents");
        parentalBedRoom.setImgPath("./images/bedroom-background.png");

        kitchen = new Room("The Kitchen with a fridge, interesting");
        kitchen.setImgPath("./images/kitchen-background.png");

        garden = new Room("Garden of the house");
        garden.setImgPath("./images/garden-background.png");

        cellar.setExits("north", hallRoom);
        cellar.setExits("east", garden);

        hallRoom.setExits("east", livingRoom);
        hallRoom.setExits("west", kitchen);
        hallRoom.setExits("north", parentalBedRoom);
        hallRoom.setExits("south", cellar);

        kitchen.setExits("east", hallRoom);
        
        livingRoom.setExits("north", attic);
        livingRoom.setExits("west", hallRoom);

        parentalBedRoom.setExits("east", attic);
        parentalBedRoom.setExits("south", livingRoom);

        attic.setExits("south", livingRoom);
        attic.setExits("east", garden);
   

        player.setCurrentRoom(cellar);  // start game outside

        //Creation of all items 
        Item shelves, shoes, tires, bed, catTree, fridge, cheese;

        shelves = new Item("shelves", "Shelves where shoes are stored", 20000);
        shoes = new Item("shoes", "Pair of old air jordan red/white", 500);
        tires = new Item("tires", "Brand new Tires for a Mustang", 5000);
        bed = new Item("bed", "Bed with two places", 10000);
        fridge = new Item("fridge", "Fridge containing a lot of cheese", 25000);
        cheese = new Item("cheese", "Piece of cheese that you can eat to heal", "./images/cheese-item.png", 200, 0, 0);

        // attic.addItem(shelves);
        cellar.addItem(cheese);
        // cellar.addItem(tires);
        // parentalBedRoom.addItem(bed);
        // kitchen.addItem(fridge);
    }

    public void update(int[] position, Command command) {
        System.out.println("Position : " + position[0] + " " + position[1]);
        processCommand(command);
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
            //Command comming from controler
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

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("This command dosen't exists ! ");
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case BACK:
                goBack();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case LOOK: 
                look();
                showRoomItems();
                break;
            case EAT:
                eat(command);
                break;
            case ITEMS:
                showPlayerItems();
                break;
            case TAKE:
                pickItem(command);
                break;
            case DROP: 
                dropItem(command);
                break;
            case INSPECT: 
                inspectItem(command);
                break;
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

    /**
     * Prints description of an item in the room
     * @param command command parsed in terminal, that has two fields including the name of the item
     */
    private void inspectItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Inspect which item ?");
            return;
        }

        String itemName = command.getSecondWord();
        System.out.println(player.getCurrentRoom().getItemDescription(itemName));
    }

    /**
     * Try to pick an item 
     * @param command
     */
    private void pickItem(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what item we should take
            System.out.println("Which item do you want to take ?");
            return;
        }

        //We first recover the item from its name
        String itemName = command.getSecondWord();
        Item itemToPick = player.getCurrentRoom().getItemByName(itemName);

        if (itemToPick != null) {
            player.pickUpItem(itemToPick);
        }
        else {
            System.out.println("There is no object named " + itemName + " !");
        }
    }

    private void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what item we should drop
            System.out.println("Which item do you want to drop ?");
            return;
        }
        String itemName = command.getSecondWord();
        Item itemToDrop = player.getItemByName(itemName);

        if (itemToDrop != null ) player.dropItem(itemToDrop);
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
        Room current = player.getCurrentRoom();
        // Try to leave current room.
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = current.getExit("north");
        }
        if(direction.equals("east")) {
            nextRoom = current.getExit("east");
        }
        if(direction.equals("south")) {
            nextRoom = current.getExit("south");
        }
        if(direction.equals("west")) {
            nextRoom = current.getExit("west");
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            //Every time we change room we push it to the previousRoom Stack
            player.addPreviousRoom(current);
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public int getPlayerLife(){
        return player.getLifePoints();
    }

    /**
     * Try to go on the previous room
     * If not possible, there is an error message
     */
    private void goBack(){
        //The last element of stack is the previous room
        //When we're in we can pop it from the stack
        if (!player.getAllPreviousRooms().isEmpty()) {
            Room previous = player.getLastPreviousRoom();
            player.setCurrentRoom(previous);
            player.popPreviousRoom();
            printLocationInfo();
        }
        else {
            System.out.println("You cannot go back ! ");
        }
    }



    /**
     * Try to look around current room to give informations
     */
    private void look() {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    private void showRoomItems() {
        System.out.print("Items : ");
        System.out.println(player.getCurrentRoom().getItemList());
    }   

    private void showPlayerItems() {
        System.out.print("Inventory : ");
        System.out.println(player.getItemsCarriedString());
    }

    public Vector<Item> getPlayerItems(){
        return player.getItemsCarried();
    }

    private void eat(Command command) {
        player.addLifePoint();
        System.out.println(player.getLifePoints());
        dropItem(command);
    }

    private void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
        
        showRoomItems();
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
