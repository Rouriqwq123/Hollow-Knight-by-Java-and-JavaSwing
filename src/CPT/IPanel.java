package CPT;

import java.awt.*;

import javax.swing.JPanel;
 public class IPanel extends JPanel {
     Image background;

     public IPanel(String backgroundPath) {
         super();
         Toolkit kit = Toolkit.getDefaultToolkit();
         background = kit.getImage("background_hd.png");  
     }

     @Override
     public void paintComponent(Graphics c) {
         super.paintComponent(c); 
         Graphics2D c2D = (Graphics2D) c;
         c2D.drawImage(background, 0, 0, getWidth(), getHeight(), this);  
     }
 }

