import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Random;

public class View extends JPanel {
    Game game;
    Color randomColor;
    DirectionButtonsControler dirControler;
    JButton northButton, southButton, eastButton, westButton, backButton, downButton;
    Insets insets;
    
    public View(Game game){
        super();
        this.game = game;
        this.setPreferredSize(new Dimension(605,485));
        this.setLayout(null);
        insets = this.getInsets();

        initButtons();

        /* Controller that listens to this element, especially to its buttons */
        dirControler = new DirectionButtonsControler(game, this);

        northButton.addMouseListener(dirControler);
        southButton.addMouseListener(dirControler);
        eastButton.addMouseListener(dirControler);
        westButton.addMouseListener(dirControler);
        backButton.addMouseListener(dirControler);

    }

    /*
     * Creates buttons with content
     * Give them a command in ClientProperty so the controler can read it
     * Places the button with absolute positionning (simplier than doing panel + flowlayout, etc..)
     */
    private void initButtons(){

        Icon icon = new ImageIcon(getClass().getResource("icons/up-arrow.png"));

        northButton = new JButton(icon);
        northButton.putClientProperty("command", new Command(CommandWord.GO, "north"));
        this.add(northButton);
        northButton.setBounds(60 + insets.left, 5 + insets.top, 32,32);
        setButtonTransparent(northButton);

        icon = new ImageIcon(getClass().getResource("icons/down-arrow.png"));

        southButton = new JButton(icon);
        southButton.putClientProperty("command", new Command(CommandWord.GO, "south"));
        this.add(southButton);
        southButton.setBounds(60 + insets.left, 69 + insets.top, 32, 32);
        setButtonTransparent(southButton);

        icon = new ImageIcon(getClass().getResource("icons/left-arrow.png"));

        westButton = new JButton(icon);
        westButton.putClientProperty("command", new Command(CommandWord.GO, "west"));
        this.add(westButton);
        westButton.setBounds(28 + insets.left, 37 + insets.top, 32, 32);
        setButtonTransparent(westButton);

        backButton = new JButton(icon);
        backButton.putClientProperty("command", new Command(CommandWord.BACK, null));
        this.add(backButton);
        backButton.setBounds(60 + insets.left, 101 + insets.top, 32, 32);
        setButtonTransparent(backButton);

        icon = new ImageIcon(getClass().getResource("icons/right-arrow.png"));

        eastButton = new JButton(icon);
        eastButton.putClientProperty("command", new Command(CommandWord.GO, "east"));
        this.add(eastButton);
        eastButton.setBounds(92 + insets.left, 37 + insets.top, 32, 32);
        setButtonTransparent(eastButton);

    }

    private void setButtonTransparent(JButton button){
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(randomColor);
    }

    public void update() {
        System.out.println("Fonction called");
        Random rand = new Random();

        float r = rand.nextFloat();
        float b = rand.nextFloat();
        float g = rand.nextFloat();

        this.randomColor = new Color(r,g,b);
        repaint();
    }
}
