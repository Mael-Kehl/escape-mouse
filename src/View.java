import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class View extends JPanel {
    Game game;

    //Controlers
    DirectionButtonsControler dirControler;
    InventoryControler invControler;
    PickableItemControler pickableControler;

    //Buttons, and panels
    JButton northButton, southButton, eastButton, westButton, backButton, downButton;
    Vector<JButton> inventoryButtons;
    JLabel inventory;
    Insets insets;

    //Constants
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = 720;
    final int[] ITEMS_X_POS = {440, 520, 610, 695, 783};
    final int[] ITEMS_Y_POS = {575, 605, 585, 575, 605};
    int current_item_in_inv = 0;

    //Variable that allows us knowing if we are in the menu
    boolean isInMenu = true;

    Image backgroundImage;
    
    public View(Game game){
        super();
        this.game = game;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setLayout(null);
        try {
            backgroundImage = ImageIO.read(getClass().getResource("./images/cellar-background.png"));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        insets = this.getInsets();
         /* Controller that listens to this element, especially to its buttons */
        dirControler = new DirectionButtonsControler(game, this);
        invControler = new InventoryControler(game, this);
        pickableControler = new PickableItemControler(game, this);
        updateItemsInRoom();
    }

    /*
     * Creates buttons with content
     * Give them a command in ClientProperty so the controler can read it
     * Places the button with absolute positionning (simplier than doing panel + flowlayout, etc..)
     */
    private void initDirectionalButtons(){
        //Getting the icon of the arrow
        Icon icon = new ImageIcon(getClass().getResource("icons/up-arrow.png"));
        //Assigning it to a JButton
        northButton = new JButton(icon);
        //Adding the command associated to the button in its properties
        //So the controler is able to read the command and update the model(game)
        northButton.putClientProperty("command", new Command(CommandWord.GO, "north"));
        this.add(northButton, 10, 0);
        //PLacing the element
        northButton.setBounds(60 + insets.left, 5 + insets.top, 32,32);
        enableButtonTransparency(northButton);

        icon = new ImageIcon(getClass().getResource("icons/down-arrow.png"));

        southButton = new JButton(icon);
        southButton.putClientProperty("command", new Command(CommandWord.GO, "south"));
        this.add(southButton, 10, 0);
        southButton.setBounds(60 + insets.left, 69 + insets.top, 32, 32);
        enableButtonTransparency(southButton);

        icon = new ImageIcon(getClass().getResource("icons/left-arrow.png"));

        westButton = new JButton(icon);
        westButton.putClientProperty("command", new Command(CommandWord.GO, "west"));
        this.add(westButton, 10, 0);
        westButton.setBounds(28 + insets.left, 37 + insets.top, 32, 32);
        enableButtonTransparency(westButton);

        backButton = new JButton(icon);
        backButton.putClientProperty("command", new Command(CommandWord.BACK, null));
        this.add(backButton, 10, 0);
        backButton.setBounds(60 + insets.left, 101 + insets.top, 32, 32);
        enableButtonTransparency(backButton);

        icon = new ImageIcon(getClass().getResource("icons/right-arrow.png"));

        eastButton = new JButton(icon);
        eastButton.putClientProperty("command", new Command(CommandWord.GO, "east"));
        this.add(eastButton, 10, 0);
        eastButton.setBounds(92 + insets.left, 37 + insets.top, 32, 32);
        enableButtonTransparency(eastButton);

        /* Add controler to buttonss */
        northButton.addMouseListener(dirControler);
        southButton.addMouseListener(dirControler);
        eastButton.addMouseListener(dirControler);
        westButton.addMouseListener(dirControler);
        backButton.addMouseListener(dirControler);
    }

    /**
     * Draws the inventory on the panel
     */
    private void initInventory() {
        //Getting the inventory icon and assigning it to a JLbale
        Icon icon = new ImageIcon(getClass().getResource("icons/inventory.png"));
        inventory = new JLabel(icon);
        this.add(inventory);
        //Enable transparency
        inventory.setOpaque(false);
        //Placing the element
        inventory.setBounds(273 + insets.left, 516 + insets.top, 626, 204);
        //Filling the inventory 
        updateInventory();
    }

    /**
     * Updates the inventory by removing all its elements, and adding the new ones 
     * Function called at every update of the view
     */
    private void updateInventory(){
        try {
            
            //Empty the inventory

            clearInventoryItems();
            //Fill the inventory

            Vector<Item> playerItems = game.getPlayerItems();

            if (!playerItems.isEmpty()) {
                for (Item item : playerItems) {
                    //Get string path to the icon of the item
                    String itemPath = item.getImgPath();
                    //Create new icon
                    Icon icon = new ImageIcon(getClass().getResource(itemPath));
                    //Create new JButton containing the icon
                    JButton inventoryItem = new JButton(icon);
                    enableButtonTransparency(inventoryItem);
                    //Setting bounds dynamically, based on absolute positions
                    inventoryItem.setBounds(ITEMS_X_POS[current_item_in_inv]+insets.left, ITEMS_Y_POS[current_item_in_inv]+insets.top, 64, 64);
                    
                    //Assigning a command to the JButton properties, so the controler can read and execute it
                    inventoryItem.putClientProperty("command", new Command(CommandWord.EAT, item.getName()));
                    //Adding a controler (listener) to the item
                    inventoryItem.addMouseListener(invControler);
                    //adding the item to the layout
                    this.add(inventoryItem, 10, 0);
                    current_item_in_inv ++;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception drawing items in inventory : " + e.getMessage());
        }
    }

    /**
     * Clears items in inventory
     */
    private void clearInventoryItems() {
        //We get all elements in the app
        Component[] components = this.getComponents();
        for (Component comp : components) {
            //We test if these components are JButton, if so we're getting it's associated command word
            if (comp instanceof JButton) {
                JButton butt = (JButton)comp;
                Command com = (Command)butt.getClientProperty("command");

                //if the command word is eat, it means that the button is an eatable item in the inventory
                if (com.getCommandWord() == CommandWord.EAT) {
                    //So we have to remove it to clear the inventory
                    this.remove(comp);
                }
            }
        }
        current_item_in_inv = 0;
    }

    private void updateItemsInRoom(){
        clearItemsInRoom();
        //Because we removed all buttons with a "GO" command, we have to print again directional buttons
        // initDirectionalButtons();
        Vector<Item> items = game.getCurrentRoom().getItems();

        for (Item item : items) {
            String itemPath = item.getImgPath();
            //Create new icon
            Icon icon = new ImageIcon(getClass().getResource(itemPath));
            JButton itemButton = new JButton(icon);
            enableButtonTransparency(itemButton);

            if (item instanceof ExitItem) {
                ExitItem exitItem = (ExitItem)item;
                itemButton.putClientProperty("command", new Command(CommandWord.GO, exitItem.getExit()));
            }
            else if (item instanceof DammageItem){
                DammageItem damItem = (DammageItem)item;
                itemButton.putClientProperty("command", new Command(CommandWord.HIT, damItem.getName()));
            }
            else {
                //If the item is hidding another one, we pick the hidden item
                if (item.getItemToPick() != null) {
                    itemButton.putClientProperty("command", new Command(CommandWord.TAKE, item.getItemToPick().getName()));
                }
                else {
                    itemButton.putClientProperty("command", new Command(CommandWord.TAKE, item.getName()));
                }
            }
            itemButton.putClientProperty("item", item);
            
            //Some items are pickable when another one is clicked (like hidden cheese under a hat for instance)
            //So we check is the values height, width, posx and posy are set to -1, if so it means we don't
            //Want this item to be placed on scene
            if (item.getHeight() != -1) {
                itemButton.setBounds(item.getPosX() + insets.left, item.getPosY() + insets.top, item.getWidth(), item.getHeight());
                this.add(itemButton);
                itemButton.addMouseListener(pickableControler);
            }
            
        }
    }

    private void clearItemsInRoom() {
        //Remove items in room so we can render the new ones
        Component[] components = this.getComponents();
        for (Component comp : components) {
            //We test if these components are JButton, if so we're getting it's associated command word
            if (comp instanceof JButton) {
                JButton butt = (JButton)comp;
                Command com = (Command)butt.getClientProperty("command");

                //if the command word is eat, it means that the button is an eatable item in the inventory
                if (com.getCommandWord() == CommandWord.TAKE || com.getCommandWord() == CommandWord.GO 
                || com.getCommandWord() == CommandWord.HIT || com.getCommandWord() == CommandWord.BACK) {
                    //So we have to remove it to clear the inventory
                    this.remove(comp);
                }
            }
        }
    }

    private void removeInventoryFromView(){
        Component[] components = this.getComponents();
        for (Component comp : components) {
            //We test if these components are JButton, if so we're getting it's associated command word
            if (comp instanceof JLabel) {
                this.remove(comp);
            }
        }
    }

    
    /**
     * Enables button to be transparent
     * @param button
     */
    private void enableButtonTransparency(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public void paintComponent(Graphics g) {

        /** --------- Draw background --------- */
        super.paintComponent(g);

        //Getting the path of the background image
        Room currentRoom = game.getCurrentRoom();
        String bgImgPath = currentRoom.getImgPath();

        //Trying to get the image associated to the current room based on its path
        try {
            // Rescale image to the game resolution 
            backgroundImage = ImageIO.read(getClass().getResource(bgImgPath)).getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_DEFAULT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Drawing the background image
        g.drawImage(backgroundImage, 0, 0, this);

        /* Drawing hearts in scene */
        if (!isInMenu) paintHearts(g);
    }

    public void paintHearts(Graphics g){
        super.paintComponents(g);
        try {
            int image_offset = 0;
            //Get the images of filled and empty heart
            Image heartImage = ImageIO.read(getClass().getResource("./images/heart.png")).getScaledInstance(32, 32, Image.SCALE_DEFAULT);
            Image emptyHeartImage = ImageIO.read(getClass().getResource("./images/empty-heart.png")).getScaledInstance(32, 32, Image.SCALE_DEFAULT);

            
            for (int index = 0; index < game.getPlayerMaxLife(); index++) {
                //drawing hearts on scene based on players life points
                if (index < game.getPlayerLife()) {
                    g.drawImage(heartImage, 350 + image_offset, 520, this);
                }
                else {
                    //Drawing the rest of hearts empty 
                    g.drawImage(emptyHeartImage, 350 + image_offset, 520, this);
                }
                image_offset += 40;
            }
        } catch (Exception e) {
            System.out.println("Error happened drawing life points on screen : " + e.getMessage());
        }
    }

    public void showInformationDialog(String desc) {
        JOptionPane.showMessageDialog(this, desc);
    }

    public void update() {
        isInMenu = false;
        repaint();
        initInventory();
        updateInventory();
        updateItemsInRoom();
        initDirectionalButtons();
    }

    public void updateMenu() {
        isInMenu = true;
        repaint();
        clearInventoryItems();
        clearItemsInRoom();
        removeInventoryFromView();
        updateItemsInRoom();        
    }
}
