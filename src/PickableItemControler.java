import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import javax.swing.JButton;

public class PickableItemControler implements MouseInputListener{
    Game game;
    View mainView;
    //int positionImage = (int)Math.floor(x/120)+(int)Math.floor(y/120)*5;

    public PickableItemControler(Game game, View mainview){
        this.game = game;
        this.mainView = mainview;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int[] positionClick = {(int)Math.floor(e.getX()) ,(int)Math.floor(e.getY())};

        Command currentCommand = new Command(CommandWord.UNKNOWN, "");
        Item item = new Item("", "", 0);

        //If the source is a button, we recover its command associated in client properties
        if (e.getSource() instanceof JButton) {
            currentCommand = (Command)((JButton)e.getSource()).getClientProperty("command");
            //Then we recover the item clicked
            item = (Item)((JButton)e.getSource()).getClientProperty("item");

        }

        //We update the model
        game.update(positionClick, currentCommand);

        //IF the player has no life, we go back to menu
        if (game.getPlayerLife() == 0) {
            game.goMenu();
            game.resetGame();
            mainView.updateMenu();
        }
        else {
            //else we search for a description in the item to print it as an information message
            mainView.update();
            if (!item.getDescription().isEmpty()) {
                mainView.showInformationDialog(item.getDescription());
            }
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
