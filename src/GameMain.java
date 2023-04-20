import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

public class GameMain {
    public static void main(String[] args) {

        MainView mainview;
        Game game = new Game();
        Controler controler = new Controler(game);

        mainview = new MainView(game);
        mainview.addMouseListener(controler);

        /******************** 
         * JFrame of the game
         *********************/
        JFrame frame=new JFrame("Escape Mouse");
		frame.getContentPane().setBackground(Color.BLUE);	 
		
		frame.getContentPane().add(mainview,BorderLayout.CENTER);
		// frame.getContentPane().add(panneauSud,BorderLayout.SOUTH);
		  
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	

        // game.play();
    }
}
