package CPT;

import java.awt.*;
import javax.swing.*;


public class Monster {
    private int x, y; // Position of the monster
    private final int speed = 10; // Constant movement speed
    private boolean movingRight = true; // Movement direction
    private int currentFrame = 0; // Current animation frame
    private Image[] frames; // Animation frames
    private int frameWidth = 100; // Width of the monster
    private int frameHeight = 100; // Height of the monster
    private int width = 100;// width of the hitbox
    private int height = 100;// height of the hitbox
    private long lastUpdateTime; // Last update timestamp
    private int health = 50; // the hp for the monster
    private Image damagedImage_right;// image for damaged monster
    private Image damagedImage_left;// image for damaged monster
    private Image monster1_dead;
    private boolean showDamaged = false;
    private boolean isDead = false; // Track if the monster is dead
    private boolean isDisappear = false; 

    public Monster(int startX, int startY, String[] framePaths) {
        this.x = startX;
        this.y = startY;
        this.frames = new Image[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            this.frames[i] = new ImageIcon(framePaths[i]).getImage();
        }
        this.lastUpdateTime = System.currentTimeMillis(); // Initialize timing
        this.damagedImage_right = new ImageIcon("bug_damaged.png").getImage();
        this.damagedImage_left = new ImageIcon("bug_damaged_left.png").getImage();
        this.monster1_dead = new ImageIcon("monster1_death.png").getImage();
    }
    /*
     * purpose:
     * pre:
     * post:
     */
    public String getDirection() {
    	if(movingRight) {
    		return "Right";
    	}
    	else {
    		return "Left";
    	}
    }
    /*
     * purpose: for the damages that the knight did to the monsters 
     * pre:
     * post:
     */
    public void takeDamage() {
        if (!isDead && health > 0) {
            health--; // Decrease health
            showDamaged = true; // Temporarily show the damaged image

            // Use a timer to revert back to the normal image after a short delay
            Timer damageTimer = new Timer(100, e -> showDamaged = false); // 100ms delay
            damageTimer.setRepeats(false);
            damageTimer.start();

            if (health <= 0) {
                isDead = true; // Mark the monster as dead
            }
        }
    }
    /*
     * purpose:
     * pre:
     * post:
     */
    public void disappear() {
    	isDisappear=true;
        width = 0;
        height = 0;
    }
    /*
     * purpose:
     * pre:
     * post:
     */
    public boolean isAlive() {
        return health > 0;
    }
    /*
     * purpose: Stop updating position if the monster is dead
     * pre:
     * post:
     */
    public void updatePosition(int screenWidth) {
        if (isDead) {
            return; // Stop updating position if the monster is dead
        }
     // Calculate the time elapsed since the last update
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastUpdateTime;
        
        // Update position only if enough time has passed (to regulate speed)
        if (elapsed >= 50) { // Adjust the delay as needed (50ms ~= 20 FPS)
            lastUpdateTime = currentTime; // Reset the last update time
        
        

            // Update position and direction
            if (movingRight) {
            	 x += speed;
                if (x + frameWidth >= screenWidth) {
                    movingRight = false;
                }
            } else {
                
                if (x <= 0) {
                    movingRight = true;
                }
            }

            // Update the animation frame
            currentFrame = (currentFrame + 1) % frames.length;
        }
}
    /*
     * purpose:
     * pre:
     * post:
     */
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
    }
    /*
     * purpose: to draw the image of the monster
     * pre:
     * post:
     */
    public void draw(Graphics g) {
        if (isDead) {
            g.drawImage(monster1_dead, x, y, 126, 94, null); // Always show death image when dead
            width = 0;
            height = 0;
            return;
        }
        if(isDisappear) {
        	return;
        }

        if (showDamaged) {
            if (movingRight) {
                g.drawImage(damagedImage_right, x, y, frameWidth, frameHeight, null); // Show damaged image
            } else {
                g.drawImage(damagedImage_left, x, y, frameWidth, frameHeight, null); // Show damaged image
            }
        } else if (frames[currentFrame] != null) {
            if (movingRight) {
                g.drawImage(frames[currentFrame], x, y, frameWidth, frameHeight, null);
            } else {
                g.drawImage(frames[currentFrame], x + frameWidth, y, -frameWidth, frameHeight, null);
            }
        }
    }
    /*
     * purpose:
     * pre:
     * post:
     */
    public int getHealth() {
        return health;
    }
}
