import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainView extends JPanel {
    Game game;
    JButton fwdButton, bwdButton, leftButton, rightButton, upButton, downButton;
    Color randomColor;
    
    public MainView(Game game){
        super();
        this.game = game;
        this.setPreferredSize(new Dimension(605,485));
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(randomColor);
    }

    public void update() {
        Random rand = new Random();

        float r = rand.nextFloat();
        float b = rand.nextFloat();
        float g = rand.nextFloat();

        this.randomColor = new Color(r,g,b);
        repaint();
    }
}
