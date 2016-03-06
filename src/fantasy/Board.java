package fantasy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Board extends JPanel implements Runnable {
    
    private Thread main;
    private ImageIcon logo = new ImageIcon("src/sprites/logo.png");
    private Image logoImg;
    private float logoOpac = .01f;
    private int[] tile = new int[2];
    private int[] roundedTile = new int[2];
    private boolean[] solid = new boolean[10];
    private int[] scroll = {0, 0};
    private int scrollSpeed = 5;
    private int z = 0;
    boolean ingame = false;
    BufferedReader inStream;
    File mapData = new File("src/maps/map0.dat");
    File terData = new File("src/maps/ter0.dat");
    Player player = new Player();
    Wall0 wall;
    Dirt0 dirt;
    Building0 building;
    
    int[][] map;
    int[] terID;
    int[][] terLoc;
    int[] transID;
    int[][] transLoc;
    
    int[] mapBound = new int[2];
    int[] tileBound = new int[2];
    
    private int DELAY = 25;
    
    public Board(){
        addKeyListener(new keyListener());
    	setFocusable(true);
    	setBackground(Color.BLACK);
      
        try {            
            inStream = new BufferedReader(new FileReader(mapData));
            String inString = inStream.readLine();
            
            mapBound[0] = Integer.parseInt(inString);
            inString = inStream.readLine();
            mapBound[1] = Integer.parseInt(inString);
            tileBound[0] = mapBound[0]/50;
            tileBound[1] = mapBound[1]/50;
            map = new int[tileBound[1]][tileBound[0]];
            
            String[] parts;
            int i = 0;
        
            while((inString = inStream.readLine()) != null){
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    map[i][k] = Integer.parseInt(parts[k]);
                }
                i++;
            }
            
            inStream = new BufferedReader(new FileReader(terData));
            inString = inStream.readLine();
            terID = new int[Integer.parseInt(inString)];
            terLoc = new int[Integer.parseInt(inString)][2];
            
            i=0;
            while (!(inString = inStream.readLine()).equals("*")){
                terID[i] = Integer.parseInt(inString);
                inString = inStream.readLine();
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    terLoc[i][k] = Integer.parseInt(parts[k]);
                }
                i++;
            }
            
            inString = inStream.readLine();
            transID = new int[Integer.parseInt(inString)];
            transLoc = new int[Integer.parseInt(inString)][2];
            i=0;
            while ((inString = inStream.readLine()) != null){
                transID[i] = Integer.parseInt(inString);
                inString = inStream.readLine();
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    transLoc[i][k] = Integer.parseInt(parts[k]);
                }
                i++;
            }
        } catch(Exception e) {System.out.println("ERROR: " + e);}
        
        if (main == null || !ingame) {
            main = new Thread(this);
            main.start();
        }
    }
    
    public void transport(int to, int direction){
        mapData = new File("src/maps/map" + to + ".dat");
        terData = new File("src/maps/ter" + to + ".dat");
        
        try {            
            inStream = new BufferedReader(new FileReader(mapData));
            String inString = inStream.readLine();
            
            mapBound[0] = Integer.parseInt(inString);
            inString = inStream.readLine();
            mapBound[1] = Integer.parseInt(inString);
            tileBound[0] = mapBound[0]/50;
            tileBound[1] = mapBound[1]/50;
            map = new int[tileBound[1]][tileBound[0]];
            
            String[] parts;
            int i = 0;
        
            while((inString = inStream.readLine()) != null){
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    map[i][k] = Integer.parseInt(parts[k]);
                }
                i++;
            }
            
            inStream = new BufferedReader(new FileReader(terData));
            inString = inStream.readLine();
            terID = new int[Integer.parseInt(inString)];
            terLoc = new int[Integer.parseInt(inString)][2];
            
            i=0;
            while (!(inString = inStream.readLine()).equals("*")){
                terID[i] = Integer.parseInt(inString);
                inString = inStream.readLine();
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    terLoc[i][k] = Integer.parseInt(parts[k]);
                }
                i++;
            }
            
            inString = inStream.readLine();
            transID = new int[Integer.parseInt(inString)];
            transLoc = new int[Integer.parseInt(inString)][2];
            i=0;
            while ((inString = inStream.readLine()) != null){
                transID[i] = Integer.parseInt(inString);
                System.out.println(transID[i]);
                inString = inStream.readLine();
                parts = inString.split(" ");
                for (int k=0; k<parts.length; k++){
                    transLoc[i][k] = Integer.parseInt(parts[k]);
                    System.out.print(transLoc[i][k] + " ");
                }
                i++;
            }
        } catch(Exception e) {System.out.println("ERROR: " + e);}
        
        if (direction==0){
            player.setY(650);
            scroll[1] = 200;
        } else if (direction==1){
            player.setX(50);
            scroll[0] = 0;
        } else if (direction==2){
            player.setY(50);
            scroll[1] = 0;
        } else if (direction==3){
            player.setX(850);
            scroll[0] = 200;
        } else {
            scroll[0] = 0;
            scroll[1] = 0;
            System.out.println("HEYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        }
    }    
    
    public String checkID(int id){
        if (id == 00){
            return "terrain";
        } else if (id >= 1 && id <= 9){
            return "dirt";
        } else if (id >= 11 && id <= 19){
            return "wall";
        }
        return "ERROR";
    }
    
    public void drawTile(Graphics2D g2d, String IDtype, int i, int k, int centerX, int centerY){
        switch (IDtype) {
            case "dirt":
                dirt = new Dirt0((50*k)-scroll[0]+(50*centerX),(50*i)-scroll[1]+(50*centerY), map[i][k]);
                dirt.draw(g2d, this);
                break;
             case "wall":
                wall = new Wall0((50*k)-scroll[0]+(50*centerX),(50*i)-scroll[1]+(50*centerY), map[i][k]);
                wall.draw(g2d, this);
                break;
            case "building":
                building = new Building0(terLoc[i][0]-scroll[0], terLoc[i][1]-scroll[1], terID[i]);
                building.draw(g2d, this);
        }
    }
   
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        
        if (!ingame){
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, logoOpac));
            g2d.drawImage(logoImg, 0, 0, this);
        } else {
            //DRAW BACKGROUND MAP AND COLLISION
            int centerX = 0;
            int centerY = 0;
            if (map.length < 16){
                    centerY = (16-map.length)/2;
                }
            for (int i=0; i<map.length; i++){
                if (map[i].length < 24){
                    centerX = (24-map[i].length)/2;
                }
                for (int k=0; k<map[i].length; k++){
                    String IDtype = checkID(map[i][k]);
                    drawTile(g2d, IDtype, i, k, centerX, centerY);
                }
                centerX=0;
            }
            centerY=0;

            //DRAW TERRAIN LAYER
            for (int i=0; i<terID.length; i++){
                drawTile(g2d, "building", i, i, 0, 0);
            }
        
            //DRAW PLAYER
            player.drawPlayer(g2d, this);
        
            //DRAW OVERLAPPING BACKGROUND
           for (int i=0; i<3; i++){
               try {
                    String IDtype = checkID(map[roundedTile[1]+1][tile[0]-1+i]);
           
                    switch (IDtype) {
                        case "dirt":
                            if (dirt.isSolid()){
                                drawTile(g2d, "dirt", tile[0]-1+i, roundedTile[1]+1, 0, 0);
                            }
                            break;
                        case "wall":
                            if (wall.isSolid()){
                                if (map[roundedTile[1]+1][tile[0]-1+i] != 00){
                                    drawTile(g2d, "wall", roundedTile[1]+1, tile[0]-1+i, 0, 0);
                                }
                            }
                            break;
                        case "terrain":
                            for (int k=0;  k<terID.length; k++){
                                if (tile[0]-1+i > terLoc[k][0] || roundedTile[1]+1 < terLoc[k][1]){
                                    drawTile(g2d, "building", k, k, 0, 0);
                                }
                            }
                            break;
                    }
                } catch(Exception e){}
            }
        }

        g.dispose();
    }
    
    public void engine(){
        z++;
        if (z>19){
            z=0;
        }
        
        //CHECK X SCROLL
        if (player.getX() > 800){
            if (1200+scroll[0] < mapBound[0]){
                scroll[0] += scrollSpeed;
                player.setX(800);
            }
        } else if (player.getX() < 400){
            if (scroll[0] > 0){
                scroll[0] -= scrollSpeed;
                player.setX(400);
            }
        }
        
        //CHECK Y SCROLL
        if (player.getY() > 600){
            if (800+scroll[1] < mapBound[1]){
                scroll[1] += scrollSpeed;
                player.setY(600);
            }
        } else if (player.getY() < 200){
            if (scroll[1] > 0){
                scroll[1] -= scrollSpeed;
                player.setY(200);
            }
        }
        
        tile[0] = (player.getX()+scroll[0])/50;
        tile[1] = (player.getY()+scroll[1])/50;
        roundedTile[0] = (player.getX()+scroll[0]+49)/50;
        roundedTile[1] = (player.getY()+scroll[1]+49)/50;
        
        //CHECK SURROUNDING TILES        
        
        int[] surrTileID = new int[10];
        
        try {
            surrTileID[0] = map[roundedTile[1]][tile[0]];
            surrTileID[1] = map[roundedTile[1]][tile[0]+1];
            surrTileID[2] = map[tile[1]][tile[0]+1];
            surrTileID[3] = map[tile[1]+1][tile[0]+1];
            surrTileID[4] = map[tile[1]+1][tile[0]];
            surrTileID[5] = map[tile[1]+1][roundedTile[0]-1];
            surrTileID[6] = map[tile[1]+1][roundedTile[0]-1];
            surrTileID[7] = map[tile[1]][roundedTile[0]-1];
            
        } catch(Exception e) {}
        
        for (int i=0; i<10; i++) {
            switch (checkID(surrTileID[i])) {
                case "dirt":
                    solid[i] = Dirt0.isSolid();
                    break;
                case "wall":
                    solid[i] = Wall0.isSolid();
                    break;
                case "terrain":
                    solid[i] = true;
                    break;
            }
        }
        
        //CHECK TRANSPORT
        for (int i=0; i<transID.length; i++){
            if (tile[0] == transLoc[i][0] && roundedTile[1]+1 == transLoc[i][1]){
                int d;
                if (roundedTile[1] == 0){
                    d=0;
                } else if (tile[0] == tileBound[0]){
                    d=1;
                } else if (roundedTile[1]+1 == (tileBound[1])){
                    d=2;
                } else if (tile[0] == 0){
                    d=3;
                } else {
                    d=4;
                }
                transport(transID[i], d);
            }
        }
        
        System.out.println(tile[0] + ", " + (roundedTile[1]+1));
        System.out.println(tileBound[0] + ", " + (tileBound[1]));
    }
    
    public void run() {
        long beforeTime, timeDiff, sleep;
        System.out.println(BufferedImage.TYPE_INT_ARGB);
        try {
            logoImg = logo.getImage();
            for (int i=0; i<100; i++){
                logoOpac /= .955;
                repaint();
                Thread.sleep(10);
            }
            System.out.println(logoOpac);
            Thread.sleep(2000);
            for (int i=0; i<100; i++){
                logoOpac *= .97;
                repaint();
                Thread.sleep(10);
            }
        } catch (Exception e) {System.out.println("ERROR: "+e);}
        
        ingame = true;
        beforeTime = System.currentTimeMillis();
        while (ingame) {
            player.move(solid, tile, roundedTile, z);
            engine();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0)
                sleep = 2;
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
    }
    
    private class keyListener extends KeyAdapter {
        
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}