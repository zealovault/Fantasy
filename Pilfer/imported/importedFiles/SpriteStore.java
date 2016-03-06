package fantasy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class SpriteStore {
    
    private String backPath = "src/sprites/";
    private String playerPath = "src/sprites/player_";
    
    private static SpriteStore single = new SpriteStore();
    
    public static SpriteStore get(){
        return single;
    }
    
    private HashMap backSprites = new HashMap();
    private HashMap playerSprites = new HashMap();
    
    public Sprite getSprite(String ref, String type){
        if (type.equals("back")){
            if (backSprites.get(ref) != null){
                return (Sprite) (backSprites.get(ref));
            }
        } else if (type.equals("player")){
            if (playerSprites.get(ref) != null){
                return (Sprite) (playerSprites.get(ref));
            }
        }
        
        BufferedImage sourceImg = null;
        
        try {
            if (type.equals("back")){
                sourceImg = ImageIO.read(new File(backPath + ref + ".png"));
            } else if (type.equals("player")){
                sourceImg = ImageIO.read(new File(playerPath + ref + ".png"));
            }
        } catch(Exception e) {System.out.println(e);}
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImg.getWidth(),sourceImg.getHeight(),Transparency.BITMASK);

        image.getGraphics().drawImage(sourceImg,0,0,null);

	Sprite sprite = new Sprite(image);
        if (type.equals("back")){
            backSprites.put(ref,sprite);
        } else if (type.equals("player")){
            playerSprites.put(ref,sprite);
        }
	return sprite;
    }
}
