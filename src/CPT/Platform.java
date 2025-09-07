package CPT;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Platform {
    private int x, y, width, height;
    private Image image;

    public Platform(int x, int y, int width, int height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = new ImageIcon(imagePath).getImage();
    }

    public int getX() {
        return x;
    }
    
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
    }
    
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
