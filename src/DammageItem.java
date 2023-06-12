public class DammageItem extends Item {
    private int dammage;


    public DammageItem(String name, String description, int weight, int dammage) {
        super(name, description, weight);
        this.dammage = dammage;
    }

    public DammageItem(String name, String description, String imgpath, int weight, int w, int h, int posx, int posy, int dammage) {
        super(name, description, imgpath, weight,w,h, posx, posy);
        this.dammage = dammage;
    }

    public int getDammage(){
        return this.dammage;
    }

    public void setDammage(int dammage){
        this.dammage = dammage;
    }
}
