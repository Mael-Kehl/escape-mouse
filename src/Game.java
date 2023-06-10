import java.nio.file.ProviderNotFoundException;
import java.util.Vector;

public class Game
{
    private Parser parser;
    private Player player;
    private Room attic, livingRoom, cellar, parentalBedRoom, hallRoom, kitchen, garden, menuRoom;

        
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
        // create the rooms

        menuRoom = new Room("Menu of the game");
        menuRoom.setImgPath("./images/menu-background.png");

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

        /** Linking rooms with exits */

        menuRoom.setExits("north", cellar);

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
        parentalBedRoom.setExits("south", hallRoom);

        attic.setExits("south", livingRoom);
        attic.setExits("east", garden);
   

        player.setCurrentRoom(cellar);  // start game in cellar
        fillRooms();
    }

    /**
     * Fills rooms with items
     * Items can be eatable, hit the player or be used as an exit
     */
    public void fillRooms(){
        //Creation of all items 
        Item fridge, cheese1, cheese2, cheese3, hat, hallConvenient;

        fridge = new Item("fridge", "Fridge containing a lot of cheese", 25000);
        cheese1 = new Item("cheese1", "Piece of cheese that you can eat to heal", "./images/cheese-item.png",200,64, 64, 1000, 275);
        cheese2 = new Item("cheese2", "Piece of cheese that you can eat to heal", "./images/cheese-item.png",200,64, 64, 1040, 395);
        
        //Cheese is hidden by hat
        hat = new Item("hat", "This hat is hiding a cheese !", "./images/hat.png",200,101, 57, 282, 346);
        cheese3 = new Item("cheese3", "Piece of cheese that you can eat to heal", 200);
        cheese3.setImgPath("./images/cheese-item.png");
        hat.setItemToPick(cheese3);
        hallRoom.addItem(cheese3);
        hallRoom.addItem(hat);

        hallConvenient = new Item("hallConvenient", "Convenient in the hall !", "./images/hall-convenient.png",200,204, 151, 568, 336);
        hallRoom.addItem(hallConvenient);

        cellar.addItem(cheese1);
        cellar.addItem(cheese2);

        ExitItem cellarLadder, atticLadder, cellarMouseDoor, hallMouseDoor, hallDoor, hallStairs, startButton;

        startButton = new ExitItem("start button", "start button of the menu", "./images/menu-start-button.png", 1200, 368, 128, 456, 400, "north");
        menuRoom.addItem(startButton);

        cellarLadder = new ExitItem("ladder", "ladder of the cellar", "./images/cellar-ladder.png", 1200, 392, 464, 0, 60, "north");
        cellar.addItem(cellarLadder);

        atticLadder = new ExitItem("ladder", "ladder of the attic", "./images/attic-ladder.png", 1200, 286, 143, 825, 500, "south");
        attic.addItem(atticLadder);

        cellarMouseDoor = new ExitItem("cellar mouse door", "Door designed for the mouse", "./images/cellar-mouse-door.png", 8000, 86, 110, 1112, 579, "east");
        cellar.addItem(cellarMouseDoor);

        hallMouseDoor = new ExitItem("hall mouse door", "Door designed for the mouse", "./images/hall-mouse-door.png", 8000, 46, 96, 795, 460, "east");
        hallRoom.addItem(hallMouseDoor);

        hallStairs = new ExitItem("hall stairs", "Stairs of the hall", "./images/hall-stairs.png", 8000, 517, 720, 733, 0, "north");
        hallRoom.addItem(hallStairs);

        hallDoor = new ExitItem("Hall door", "Door leading to the kitchen", "./images/hall-door.png", 8000, 96, 426, 61, 107, "west");
        hallRoom.addItem(hallDoor);

        DammageItem cookies, cleaningProduct, hallMirror;

        cookies = new DammageItem("cookies", "Cookies, but they are poison for mouses", "./images/livingroom-cookies.png", 200, 181, 47, 540, 410, 1);
        livingRoom.addItem(cookies);

        cleaningProduct = new DammageItem("cleaningProduct", "Product used to clean the toilets", "./images/cellar-cleaning-product.png", 400, 85, 139, 1050,188, 1);
        cellar.addItem(cleaningProduct);

        hallMirror = new DammageItem("Mirror", "Ouch ! that isn't a window, but a mirror ! Are you a blind mouse ?", "./images/hall-mirror.png", 400, 192, 194, 550,115, 1);
        hallRoom.addItem(hallMirror);
    }

    public void update(int[] position, Command command) {
        processCommand(command);
    }

    public void resetGame(){
        this.player.setLifePoints(1);
        this.player.emptyItems();
        Item cheese = new Item("cheese", "Piece of cheese", 10 );
        cheese.setImgPath("./images/cheese-item.png");
        this.player.pickUpItem(cheese);
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
            case HIT: 
                hitPlayer(command);
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

    public void goMenu(){
        Room currentRoom = player.getCurrentRoom();
        player.addPreviousRoom(currentRoom);
        player.setCurrentRoom(menuRoom);
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public int getPlayerLife(){
        return player.getLifePoints();
    }

    public int getPlayerMaxLife() {
        return player.getMaxLifePoints();
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
        dropItem(command);
    }

    private void hitPlayer(Command command){
        player.removeLifePoint();
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
