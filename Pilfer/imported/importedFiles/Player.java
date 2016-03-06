package fantasy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class Player {
    
    private Sprite[] sprite = new Sprite[2];
    private Image image;
    private boolean[] keyPressed = new boolean[4];
    private int frame;
    private int x;
    private int y;
    private int speed = 5;
    private int dx;
    private int dy;
    
    public Player() {
        
        sprite[0] = SpriteStore.get().getSprite("stop0", "player");
        sprite[1] = SpriteStore.get().getSprite("stop1", "player");
        
        x = 400;
        y = 400;
        
    }
    
    public void move(boolean[] solid, int[] tile, int[] roundedTile, int frame){
        if (keyPressed[0] && !solid[0]) {
            for (int i=0; i<sprite.length; i++){
                sprite[i] = SpriteStore.get().getSprite("up" + i, "player");
            }
            
            if (tile[0] != roundedTile[0]){
                if (!solid[1]){
                    dy = -speed;
                }
            } else {
                dy = -speed;
            }
        }
        
        if (keyPressed[1] && (!solid[2] || !solid[3])) {
            for (int i=0; i<sprite.length; i++){
                sprite[i] = SpriteStore.get().getSprite("right" + i, "player");
            }
            
            if (tile[1] != roundedTile[1]){
                if (!solid[3]){
                    dx = speed;
                }
            } else {
                dx = speed; 
            }  
        }

        if (keyPressed[2] && !solid[4]) {
            for (int i=0; i<sprite.length; i++){
                sprite[i] = SpriteStore.get().getSprite("down" + i, "player");
            }
            
            if (tile[0] != roundedTile[0]){
                if (!solid[3]){
                    dy = speed;
                }
            } else{
                dy = speed;
            }
        }

        if (keyPressed[3] && (!solid[6] || !solid[7])) {
            for (int i=0; i<sprite.length; i++){
                sprite[i] = SpriteStore.get().getSprite("left" + i, "player");
            }
            
            if (tile[1] != roundedTile[1]){
                if (!solid[5]){
                    dx = -speed;
                }
            } else {
                dx = -speed;
            }
        }
        
        if (!keyPressed[0] && !keyPressed[1] && !keyPressed[2] && !keyPressed[3]){
            for (int i=0; i<sprite.length; i++){
                sprite[i] = SpriteStore.get().getSprite("stop" + i, "player");
            }
        }
        
        this.frame = frame;
        
        x += dx;
        y += dy;
        dx = 0;
        dy = 0;
    }
    
    public void drawPlayer(Graphics2D g2d, Board b){
        image = sprite[frame/10].getImage();
        g2d.drawImage(image, x, y, b);
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int newX){
        x = newX;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int newY){
        y = newY;
    }

    public Image getImage() {
        return image;
    }
    
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_W) {
            keyPressed[0] = true;
        }
        
        if (key == KeyEvent.VK_D) {
            keyPressed[1] = true;
        }

        if (key == KeyEvent.VK_S) {
            keyPressed[2] = true;
        }

        if (key == KeyEvent.VK_A) {
            keyPressed[3] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            keyPressed[0] = false;
            dy = 0;
        }
        
        if (key == KeyEvent.VK_D) {
            keyPressed[1] = false;
            dx = 0;
        }

        if (key == KeyEvent.VK_S) {
            keyPressed[2] = false;
            dy = 0;
        }
        
        if (key == KeyEvent.VK_A) {
            keyPressed[3] = false;
            dx = 0;
        }
    }
    
}
