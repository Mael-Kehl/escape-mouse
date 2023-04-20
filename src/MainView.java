import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    Game game;
    
    public MainView(Game game){
        super();
        this.game = game;
        this.setPreferredSize(new Dimension(605,485));
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLUE);
    }

    public void update() {
        repaint();
    }
}
