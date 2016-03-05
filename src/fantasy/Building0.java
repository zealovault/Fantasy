package fantasy;

import java.awt.Graphics2D;
import java.awt.Image;

public class Building0 {
    private Sprite sprite;
    private Image image;
    private int x;
    private int y;
    
    public Building0(int xLoc, int yLoc, int id){
        this.sprite = SpriteStore.get().getSprite("" + id, "back");
        image = sprite.getImage();
        
        x = xLoc;
        y = yLoc;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }
    
    public void draw(Graphics2D g2d, Board b){
        g2d.drawImage(getImage(), getX(), getY(), b);
    }
}
