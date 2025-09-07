package CPT;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Gruzzer {
    private int x, y; // Current position
    private final int speed = 8; // Movement speed
    private int x1, y1, x2, y2; // Track endpoints
    private boolean movingToX2 = true; // Direction along the track
    private int health = 35;
    private boolean isDead = false;
    private boolean isDisappear = false;
    private boolean activated = false; // Activation state
    private boolean showDamaged = false; // Flag to show damaged image
    private Knight player; // Reference to the player
    private int detectionRadius = 500; // New detection range for activation
    private long lastUpdateTime;
    private Image[] frames;
    private Image deathImage; // Image for when Gruzzer is dead
    private Image damagedImage; // Damaged image
    private int currentFrame = 0;
    private int fallSpeed = 9; // Speed of falling after death


    public Gruzzer(int startX1, int startY1, int endX2, int endY2, String[] framePaths, Knight player) {
        this.x = startX1;
        this.y = startY1;
        this.x1 = startX1;
        this.y1 = startY1;
        this.x2 = endX2;
        this.y2 = endY2;
        this.player = player;
        this.frames = new Image[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            this.frames[i] = new ImageIcon(framePaths[i]).getImage();
        }
        this.damagedImage = new ImageIcon("gruzzer_damaged.png").getImage(); // Load damaged image
        this.deathImage = new ImageIcon("gruzzer_corpse.png").getImage(); // Load death image
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void updatePosition(int screenHeight) {
        if (isDisappear) return;

        if (isDead) {
            y += fallSpeed; // Fall down after death
            if (y > screenHeight) {
                isDisappear = true; // Disappear when off-screen
            }
            return;
        }

        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - lastUpdateTime;

        if (elapsed >= 50) {
            lastUpdateTime = currentTime;

            // Check if the player is within detection range
            int distanceToPlayer = (int) Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
            activated = distanceToPlayer <= detectionRadius;

            if (activated) {
                moveToPlayer(); // Move toward the player
            } else {
                followTrack(); // Move along the predefined track
            }

            // Update animation frame if not showing damaged frame
            if (!showDamaged) {
                currentFrame = (currentFrame + 1) % frames.length;
            }
        }
    }

    private void moveToPlayer() {
        if (player.getX() < x) x -= speed;
        else if (player.getX() > x) x += speed;

        if (player.getY() < y) y -= speed;
        else if (player.getY() > y) y += speed;
    }

    private void followTrack() {
        if (movingToX2) {
            moveTo(x2, y2);
            if (Math.abs(x - x2) <= speed && Math.abs(y - y2) <= speed) {
                x = x2; // Snap to target to avoid overshooting
                y = y2;
                movingToX2 = false; // Switch direction
            }
        } else {
            moveTo(x1, y1);
            if (Math.abs(x - x1) <= speed && Math.abs(y - y1) <= speed) {
                x = x1; // Snap to target to avoid overshooting
                y = y1;
                movingToX2 = true; // Switch direction
            }
        }
    }

    private void moveTo(int targetX, int targetY) {
        if (Math.abs(x - targetX) > speed) {
            x += (x < targetX) ? speed : -speed;
        } else {
            x = targetX; // Snap to target
        }

        if (Math.abs(y - targetY) > speed) {
            y += (y < targetY) ? speed : -speed;
        } else {
            y = targetY; // Snap to target
        }
    }

    public void takeDamage() {
        if (!isDead) {
            health--;
            showDamaged = true; // Show damaged image
            Timer damageTimer = new Timer(100, e -> showDamaged = false); // Reset after 100ms
            damageTimer.setRepeats(false);
            damageTimer.start();

            if (health <= 0) {
                isDead = true; // Mark as dead
            }
        }
    }

    public void draw(Graphics g) {
        if (isDisappear) return;

        if (isDead) {
            g.drawImage(deathImage, x, y, 82, 110, null); // Draw death image during fall
        } else if (showDamaged) {
            g.drawImage(damagedImage, x, y, 82, 110, null); // Draw damaged image
        } else {
            g.drawImage(frames[currentFrame], x, y, 82, 110, null); // Draw normal frame
        }
    }

    public Rectangle getCollisionBox() {
        if (isDead) {
            return new Rectangle(x, y, 0, 0); // No collision if dead
        }
        return new Rectangle(x, y, 82, 110);
    }

    public boolean isAlive() {
        return !isDead;
    }
    public void disappear() {
    	isDisappear=true;
    }
}
