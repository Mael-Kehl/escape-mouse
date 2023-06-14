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
   

        player.setCurrentRoom(menuRoom);  // start game in cellar
        fillRooms();
    }

    /**
     * Fills rooms with items
     * Items can be eatable, hit the player or be used as an exit
     */
    public void fillRooms(){
        //Creation of all items 
        Item messageButton, fridge, cheese1, cheese2, cheese3, cheese4, cheese5, hat, hallConvenient, hallClock, bedroomConvenient, bedroomClock, flatware, postIt1, postIt2, postIt3, endOfGameMessage;

        //Description are used to show an information dialog to the user, 
        //we let them empty if we don't want to show a message

        messageButton = new Item("messageButton", "", "./images/message-button.png", 2000, 522, 225, 380, 250);
        menuRoom.addItem(messageButton);

        endOfGameMessage = new Item("endMessage", "", "./images/end-message-button.png", 2000, 522, 93, 380, 350);
        garden.addItem(endOfGameMessage);

        cheese1 = new Item("cheese1", "", "./images/cheese-item.png",200,64, 64, 1000, 275);
        cheese2 = new Item("cheese2", "", "./images/cheese-item.png",200,64, 64, 1040, 395);
        
        cellar.addItem(cheese1);
        cellar.addItem(cheese2);

        //Cheese is hidden by hat
        hat = new Item("hat", "This hat was hiding a cheese ! Try to click on other objects to earn food !", "./images/hat.png",200,101, 57, 282, 346);
        cheese3 = new Item("cheese3", "", 200);
        cheese3.setImgPath("./images/cheese-item.png");
        hat.setItemToPick(cheese3);
        hallRoom.addItem(cheese3);
        hallRoom.addItem(hat);

        hallConvenient = new Item("hallConvenient", "", "./images/hall-convenient.png",2000,204, 151, 568, 336);
        cheese4 = new Item("cheese4", "", 200);
        cheese4.setImgPath("./images/cheese-item.png");
        hallConvenient.setItemToPick(cheese4);
        hallRoom.addItem(cheese4);
        hallRoom.addItem(hallConvenient);

        hallClock = new Item("hallClock", "The time isn't correct on this clock, try to find another one", "./images/hall-clock.png", 2000, 63, 84, 195, 110 );
        hallRoom.addItem(hallClock);

        bedroomConvenient = new Item("bedroomConvenient", "", "./images/bedroom-convenient.png",2000,213, 139, 914, 400);
        parentalBedRoom.addItem(bedroomConvenient);

        bedroomClock = new Item("bedRoomclock", "You should check the post-it in the kitchen, you might find something in there", "./images/bedroom-clock.png", 2000, 185, 318, 248, 178);
        parentalBedRoom.addItem(bedroomClock);

        fridge = new Item("fridge", "The fridge was containing a cheese, congratulations captain obvious !","./images/kitchen-fridge.png", 25000, 181, 495, 60, 137);
        cheese5 = new Item("cheese5", "",200);
        cheese5.setImgPath("./images/cheese-item.png");
        fridge.setItemToPick(cheese5);
        kitchen.addItem(cheese5);
        kitchen.addItem(fridge);

        postIt1 = new Item("postIt1", "The parents are hiding the key in their room, search in the convenients !", "./images/post-it2.png", 2000, 38, 44, 340, 250);
        postIt2 = new Item("postIt2", "300cl of milk + 2 eggs + 400g of butter", "./images/post-it3.png", 2000, 38, 44, 390, 250);
        postIt3 = new Item("postIt3", "There is another way to get outside, in the attic ...", "./images/post-it1.png", 2000, 38, 44, 440, 250);


        kitchen.addItem(postIt1);
        kitchen.addItem(postIt2);
        kitchen.addItem(postIt3);

        flatware = new Item("Flatware", "You are not ratatouille, We don't have time for this !", "./images/kitchen-flatware.png", 2500, 165,110,770,249);
        kitchen.addItem(flatware);


        ExitItem cellarLadder, atticLadder, cellarMouseDoor, hallMouseDoor, hallDoor, hallStairs, startButton, livingroomStairs, livingroomMouseDoor, atticTube;

        startButton = new ExitItem("start button", "", "./images/menu-start-button.png", 1200, 368, 128, 456, 500, "north");
        menuRoom.addItem(startButton);

        cellarLadder = new ExitItem("ladder", "", "./images/cellar-ladder.png", 1200, 392, 464, 0, 60, "north");
        cellar.addItem(cellarLadder);

        atticLadder = new ExitItem("ladder", "", "./images/attic-ladder.png", 1200, 286, 143, 825, 500, "south");
        attic.addItem(atticLadder);

        cellarMouseDoor = new ExitItem("cellar mouse door", "This door is locked ! You might check the clocks first !", "./images/cellar-mouse-door.png", 8000, 86, 110, 1112, 579, "");
        cellar.addItem(cellarMouseDoor);

        hallMouseDoor = new ExitItem("hall mouse door", "", "./images/hall-mouse-door.png", 8000, 46, 96, 795, 460, "east");
        hallRoom.addItem(hallMouseDoor);

        hallStairs = new ExitItem("hall stairs", "", "./images/hall-stairs.png", 8000, 517, 720, 733, 0, "north");
        hallRoom.addItem(hallStairs);

        hallDoor = new ExitItem("Hall door", "", "./images/hall-door.png", 8000, 96, 426, 61, 107, "west");
        hallRoom.addItem(hallDoor);

        livingroomStairs = new ExitItem("LivingRoom Stairs", "", "./images/livingroom-stairs.png", 2000, 774, 402, 500, 0, "north");
        livingRoom.addItem(livingroomStairs);

        livingroomMouseDoor = new ExitItem("LivingRoom mouse door", "", "./images/livingroom-mouse-door.png", 2000, 71, 78, 5, 270, "west");
        livingRoom.addItem(livingroomMouseDoor);

        atticTube = new ExitItem("Attic tube", "", "./images/attic-tube.png", 2000, 251, 153, 770, 252, "east");
        attic.addItem(atticTube);

        DammageItem cookies, cleaningProduct, hallMirror, pressureCooker, kitchenWindow;

        cookies = new DammageItem("cookies", "You've been hit by the cookies, it acts like poison for mouses !", "./images/livingroom-cookies.png", 200, 181, 47, 540, 410, 1);
        livingRoom.addItem(cookies);

        cleaningProduct = new DammageItem("cleaningProduct", "Don't try to drink cleaning product, are you mad ?", "./images/cellar-cleaning-product.png", 400, 85, 139, 1050,188, 1);
        cellar.addItem(cleaningProduct);

        hallMirror = new DammageItem("Mirror", "Ouch ! that isn't a window, but a mirror ! Are you a blind mouse ?", "./images/hall-mirror.png", 400, 192, 194, 550,115, 1);
        hallRoom.addItem(hallMirror);

        pressureCooker = new DammageItem("Pressure cooker", "Ouch ! that's hot", "./images/kitchen-pressure-cooker.png", 2000, 65,52,380,330, 1 );
        kitchen.addItem(pressureCooker);

        kitchenWindow = new DammageItem("Kitchen window", "Haven't you seen that the window was close ? You're a mouse not a dumb bird !", "./images/kitchen-window.png", 2500, 287, 312, 965, 80, 1);
        kitchen.addItem(kitchenWindow);
    }

    public void update(int[] position, Command command) {
        processCommand(command);
    }

    /** 
     * Resets game by setting life points to 1
     * Empty the inventory of the player
     * Add a cheese to player's inventory
     */
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

    /*
    * Drops an item base on its name 
    */
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

    /**
     * Brings the player to menu
     */
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
     * Not used in the final game
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

    /**
     * Eats an element in the inventory
     * @param command
     */
    private void eat(Command command) {
        player.addLifePoint();
        dropItem(command);
    }

    /**
     * Harms the player while removing one life point
     * @param command
     */
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
