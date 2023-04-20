import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class Controler implements MouseInputListener{
    Game game;
    //int positionImage = (int)Math.floor(x/120)+(int)Math.floor(y/120)*5;

    public Controler(Game game){
        this.game = game;
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
        game.update(positionClick);
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
