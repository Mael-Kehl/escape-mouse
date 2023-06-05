public class ExitItem extends Item {
    String exit = "";
    
    public ExitItem(String name, String description, int weight, String exit) {
        super(name, description, weight);
        this.exit = exit;
    }

    public ExitItem(String name, String description, String imgpath, int weight, int w, int h, int posx, int posy, String exit) {
        super(name, description, imgpath, weight,w,h, posx, posy);
        this.exit = exit;
    }

    public String getExit() {
        return this.exit;
    }
}
