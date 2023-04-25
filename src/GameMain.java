import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

public class GameMain {

    
    public static void main(String[] args) {

        View mainview;
        Game game = new Game();
        mainview = new View(game);

        Controler controler = new Controler(game, mainview);
        mainview.addMouseListener(controler);
        


        /******************** 
         * JFrame of the game
         *********************/
        JFrame frame=new JFrame("Escape Mouse");
		
		frame.getContentPane().add(mainview,BorderLayout.CENTER);
		// frame.getContentPane().add(panneauSud,BorderLayout.SOUTH);
		  
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	

        // game.play();
    }
}
