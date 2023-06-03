public class Item {

    private String name;
    private String description;
    private int weight; //weight of the object in grams
    private String imgPath;
    private int posX, posY;

    
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public Item(String name, String description, String imgpath, int weight, int posx, int posy) {
        this.name = name;
        this.description = description;
        this.imgPath = imgpath;
        this.weight = weight;
        this.posX = posx;
        this.posY = posy;
    }

    public void setPos(int posx, int posy) {
        this.posX = posx;
        this.posY = posy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getImgPath(){
        return this.imgPath;
    }

    public void setImgPath(String path) {
        this.imgPath = path;
    }

    public int getPosX(){
        return this.posX;
    }

    public int getPosY(){
        return this.posY;
    }


    

}
