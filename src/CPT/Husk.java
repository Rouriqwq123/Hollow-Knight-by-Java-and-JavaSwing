package CPT;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Husk {
    private int x, y; // Current position
    private final int speed = 6; // Walking speed
    private final int runSpeed = (int) (speed * 2); // Running speed when activated
    private int x1, x2; // Walking range
    private boolean movingToX2 = true; // Patrol direction
    private boolean activated = false; // Whether the knight is within range
    private boolean isDead = false;
    private boolean showDamaged = false;
    private Knight knight; // Reference to the knight
    private int health = 500; // Health points
    private long lastUpdateTime;
    private Image[] walkLeftFrames;
    private Image[] walkRightFrames;
    private Image attackLeft, attackRight;
    private Image damagedLeft, damagedRight, attackDamagedLeft, attackDamagedRight;
    private Image deadLeft, deadRight;
    private int currentFrame = 0;
    private int frameDelay = 0;
    private boolean isDisappear = false;

    public Husk(int startX1, int startY, int endX2, Knight knight) {
        this.x = startX1;
        this.y = startY;
        this.x1 = startX1;
        this.x2 = endX2;
        this.knight = knight;

        // Load images
        walkLeftFrames = new Image[]{
            new ImageIcon("husk_left_1.png").getImage(),
            new ImageIcon("husk_left_2.png").getImage()
        };
        walkRightFrames = new Image[]{
            new ImageIcon("husk_right_1.png").getImage(),
            new ImageIcon("husk_right_2.png").getImage()
        };
        attackLeft = new ImageIcon("husk_attack_left.png").getImage();
        attackRight = new ImageIcon("husk_attack_right.png").getImage();
        damagedLeft = new ImageIcon("husk_left_damage.png").getImage();
        damagedRight = new ImageIcon("husk_right_damage.png").getImage();
        attackDamagedLeft = new ImageIcon("husk_attack_left_damage.png").getImage();
        attackDamagedRight = new ImageIcon("husk_attack_right_damage.png").getImage();
        deadLeft = new ImageIcon("husk_dead_left.png").getImage();
        deadRight = new ImageIcon("husk_dead_right.png").getImage();
    }

    public void updatePosition() {
        if (isDead) return; // Stop updating if dead

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= 50) {
            lastUpdateTime = currentTime;

            // Activation condition: Knight within specific range
            activated = knight.getX() > 854 && knight.getX() < 1396 && knight.getY() <= 172;

            if (activated) {
                moveToKnight(); // Run toward the knight
            } else {
                patrol(); // Patrol between x1 and x2
            }

            // Update animation frame
            if (!showDamaged) {
                frameDelay = (frameDelay + 1) % 10;
                if (frameDelay == 0) currentFrame = (currentFrame + 1) % 2;
            }
        }
    }

    private void patrol() {
        if (movingToX2) {
            moveTo(x2, y);
            if (Math.abs(x - x2) <= speed) { // Close enough to the target
                x = x2; // Snap to target position
                movingToX2 = false; // Switch direction
            }
        } else {
            moveTo(x1, y);
            if (Math.abs(x - x1) <= speed) { // Close enough to the target
                x = x1; // Snap to target position
                movingToX2 = true; // Switch direction
            }
        }
    }

    private void moveTo(int targetX, int targetY) {
        if (x < targetX) {
            x = Math.min(x + speed, targetX); // Prevent overshooting
        } else if (x > targetX) {
            x = Math.max(x - speed, targetX); // Prevent overshooting
        }
    }

    private void moveToKnight() {
        if (knight.getX() > x) x += runSpeed;
        else if (knight.getX() < x) x -= runSpeed;
    }

  

    public void takeDamage() {
        if (!isDead) {
            health -= 10; // Reduce health by 10
            showDamaged = true; // Show damaged image
            Timer damageTimer = new Timer(100, e -> showDamaged = false); // Reset damage image after 100ms
            damageTimer.setRepeats(false);
            damageTimer.start();

            if (health <= 0) {
                die();
            }
        }
    }

    private void die() {
        isDead = true; // Mark as dead
       
    }

    public void draw(Graphics g) {
        if (isDead) {
            g.drawImage(deadRight, x, y, 82, 110, null);
            return;
        }
        if(isDisappear){
           return;
        }

        Image currentImage;
        if (showDamaged) {
            currentImage = activated
                ? (knight.getX() < x ? attackDamagedLeft : attackDamagedRight)
                : (knight.getX() < x ? damagedLeft : damagedRight);
        } else if (activated) {
            currentImage = knight.getX() < x ? attackLeft : attackRight;
        } else {
            currentImage = movingToX2 ? walkRightFrames[currentFrame] : walkLeftFrames[currentFrame];
        }

        g.drawImage(currentImage, x, y, 82, 110, null);
    }

    public Rectangle getCollisionBox() {
        if (isDead||isDisappear) return new Rectangle(0, 0, 0, 0); // No collision if dead
        return new Rectangle(x, y, 82, 110); // Adjust dimensions as needed
    }

    public boolean isAlive() {
        return !isDead;
    }

	public void disappear() {
    	isDisappear=true;
    }
}
