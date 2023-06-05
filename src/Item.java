public class Item {

    protected String name;
    protected String description;
    protected int weight; //weight of the object in grams
    protected String imgPath;
    protected int posX, posY, width, height;

    
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public Item(String name, String description, String imgpath, int weight, int width, int height, int posx, int posy) {
        this.name = name;
        this.description = description;
        this.imgPath = imgpath;
        this.weight = weight;
        this.posX = posx;
        this.posY = posy;
        this.width = width;
        this.height = height;
    }

    public void setPos(int posx, int posy) {
        this.posX = posx;
        this.posY = posy;
    }

    public void setDimension(int width, int height) {
        this.width = width;
        this.height = height;
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

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
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
