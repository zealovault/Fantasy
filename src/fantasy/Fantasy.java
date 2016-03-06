package fantasy;

import javax.swing.*;

public class Fantasy extends JFrame{

    public Fantasy(){
        add(new Board());
    	setTitle("Fantasy");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
    }
    
    public static void main(String[] args) {
        new Fantasy();
    }
    
}
