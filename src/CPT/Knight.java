/*
 * Ray Zhang
 * January 14 2025
 * The class of the knight - the main character of the game
 */
package CPT;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Knight {
    public int x, y; // The X,Y Coordinates of the knight (top left)
    private boolean isJumping; // To record if the knight is jumping
    private boolean isFalling;// to record if the knight is falling
    public boolean isMovingLeft; // to record if the knight is moving left
    public boolean isMovingRight; // to record if the knight is moving right
    private boolean lastDirectionLeft;  // if the knight's direction is left
    private int jumpVelocity; // the velocity of the jumping 
    public static int isDoubleJumping; // detect if the knight did double jump or not
    public static int dashtime; // the time of the dash
    private int health = 5;  //
    private boolean isDamaged = false; // tracks if the Knight is currently damaged
    private long damageStartTime = 0; // the time that the knight is damaged

    // Dashing variables
    public int dashSpeed = 30; // Speed of the dash
    public int dashDistance = 300; // Total distance of the dash
    public  int dashDistanceCovered = 0;
    public boolean isDashing = false;
    boolean dashLeft = true; // Direction of the dash
    private int dashFrame = 1; // Current dash animation frame

    public int width = 67, height = 127; // Collision box dimensions e.g. x and y
    private int frame = 1; // the frame for the walking animation
    
    private int dashFrameDelay = 0; // Count how many frames are there for the delay frame
    private final int dashFrameDelayLimit = 5; // the limit delay for the dashing
    private final int totalDashFrames = 8; // The total frames for the dashing animation
  
    // slash variable
    private boolean isSlashing = false; // Is the character slashing?
    private int slashFrame = 1; // Current slash animation frame
    private int slashFrameDelay = 0; // Frame delay counter
    private final int slashFrameDelayLimit = 2; // The limit delay for slashing
    private final int totalSlashFrames = 5; // Total slash frames
    
    // die animation
    
    private boolean isDead = false; // if the knight is dead
    private int deathFrame = 1; // the frame of the death animation
    private int deathFrameDelay = 30; // frame delay counter for death animation
    private final int deathFrameDelayLimit = 30; // the delay between each frame of the animation
    private final int totalDeathFrames = 2; // Number of frames in the death animation
    private int respawnX, respawnY; // Original position for respawn
    
    // the knockedback animation 
    public boolean isKnockedBack = false; // Is the Knight currently in knockback
    private int knockbackDistance = 330; // Total distance for the knockback
    private int knockbackSpeed = 30; // Speed of the knockback
    private int knockbackCovered = 0; // Distance covered during knockback
    private boolean knockbackLeft = true; // Direction of knockback
    private Image heartImage = new ImageIcon("heart.png").getImage();// image for the heart
    
    /*
     * Purpose: To set the respawn of the knight
     * Pre: 2 int x and y = the coordinates of the respwaning point
     * Post: N/A
     */
    public void setRespawn(int x,int y) {
    	respawnX = x;
    	respawnY = y;
    }
    /*
     * Purpose: To get the collisionbox of the knight
     * Pre: n/a
     * Post: A Rectangle - the collision box of the knight
     */
    public Rectangle getCollisionBox_Slash(){
    	if(!lastDirectionLeft){
    	return new Rectangle(x,y,350,131);
    	}
    	else{
    		return new Rectangle(x-235,y,282,131);
    	}
    	
    }
    /*
     * Purpose: To start the slashing action, and initialize all the slashing variables
     * Pre: n/a
     * Post: n/a
     */
    public void startSlash() {
        if (!isSlashing) {
            isSlashing = true;
            isShowingSlashEffect = true; // Show the slash effect
            slashFrame = 1; // Reset to the first frame
            slashEffectFrame = 1; // Reset slash effect animation
            dashtime++;
        }
    }
   
    /*
     * Purpose: To get the heart image
     * Pre: n/a
     * Post: An image = the heart image
     */
    public Image getHeartImage() {
        return heartImage;
    }
    
    
    /*
     * Purpose: To update the slash animation and action
     * Pre: n/a
     * Post: n/a
     */
    public void updateSlash() {
        if (isSlashing) {
            slashFrameDelay++;
            if (slashFrameDelay >= slashFrameDelayLimit) {
                slashFrame++;
                slashFrameDelay = 0;

                // End the slash animation after the last frame
                if (slashFrame > totalSlashFrames) {
                    isSlashing = false;
                    slashFrame = 1; // Reset for next time
                }
            }
        }
    }
    /*
     * Purpose: To detect if the knight is slashing
     * Pre: n/a
     * Post: a boolean - if the knight is slashing
     */
    public boolean isSlashing() {
        return isSlashing;
    }

    
   // Slash effect variables
    private boolean isShowingSlashEffect = false; // is it showing the slash effect?
    private int slashEffectFrame = 1; // Current frame for the slash effect
    private int slashEffectFrameDelay = 0; // Frame delay counter
    private final int slashEffectFrameDelayLimit = 3; // Delay between frames
    private final int totalSlashEffectFrames = 2; // Total frames for the slash effect
    
    /*
     * Purpose: To update the frames of the slash effect
     * Pre: n/a
     * Post: n/a
     */
    public void updateSlashEffect() {
        if (isShowingSlashEffect) {
            slashEffectFrameDelay++;
            if (slashEffectFrameDelay >= slashEffectFrameDelayLimit) {
                slashEffectFrame++;
                slashEffectFrameDelay = 0;

                // Stop showing the effect after the last frame
                if (slashEffectFrame > totalSlashEffectFrames) {
                    isShowingSlashEffect = false; // End the effect
                    slashEffectFrame = 1; // Reset for next time
                }
            }
        }
    }
   
    /*
     * Purpose: To get the corresponding images for slashing
     * Pre: n/a
     * Post: An image, the slash image
     */
    public Image getSlashEffectImage() {
        String imagePath;

        if (isFacingLeft()) {
            // Use "slash1_left" for the first frame, "slash2_left" for the second frame
            imagePath = "slash" + (slashEffectFrame == 1 ? "1" : "2") + "_left_" + slashEffectFrame + ".png";
        } else {
            // Use "slash1_right" for the first frame, "slash2_right" for the second frame
            imagePath = "slash" + (slashEffectFrame == 1 ? "1" : "2") + "_right_" + slashEffectFrame + ".png";
        }

        return new ImageIcon(imagePath).getImage();
    }
    /*
     * Purpose: To check if it is showing the slash effect
     * Pre: n/a
     * Post: a boolean - if it is slashing
     */
    public boolean isShowingSlashEffect() {
    	return isShowingSlashEffect;
    }
    
    // Constructor
    public Knight(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.respawnX = startX; // Store respawn position
        this.respawnY = startY;
        this.isJumping = false;
        this.isFalling = false;
        this.isMovingLeft = false;
        this.isMovingRight = false;
        this.lastDirectionLeft = true;
        this.jumpVelocity = 0;
        this.isDoubleJumping = 0;
        this.dashtime = 0;
    }

    /*
     * Purpose: To check if it is dashing
     * Pre: n/a
     * Post: a boolean - if it is dashing
     */
    public boolean isDashing() {
        return isDashing;
    }
    /*
     * Purpose: To initialize the dash frames and start the dash
     * Pre: n/a
     * Post: n/a
     */
    public void startDash(boolean dashLeft) {
    	if (dashtime < 1 && !isSlashing && !isDamaged) { // Allow dash only if not slashing
            this.isDashing = true;
            this.dashLeft = dashLeft;
            this.dashFrame = 1;
            this.dashDistanceCovered = 0;
            dashtime++;
        }
    }
    /*
     * Purpose: To let the knight die
     * Pre: n/a
     * Post: n/a
     */
    public void die() {
        isDead = true;
        deathFrame = 1; // Start death animation
    }
    /*
     * Purpose: To respawn the knight
     * Pre: n/a
     * Post: n/a
     */
    private void respawn() {
        isDead = false;
        health = 5; // Reset health
        x = respawnX; // Reset position
        y = respawnY;
        isJumping = false;
        isFalling = false;
        isMovingLeft = false;
        isMovingRight = false;
        lastDirectionLeft = true;
    }
    /*
     * Purpose: To update the position of the knight depends on its senarios
     * Pre: n/a
     * Post: n/a
     */
    public void updatePosition() {
        if (isDashing) {
            updateDash(); // Handle dashing logic
            return; // Skip other movements during dash
        }
        if (isKnockedBack) {
            applyKnockback(); // Handle knockback effect
            return; // Skip other updates during knockback
        }
        ////****
        

        if (isDead) {
            updateDeathAnimation();
            return; // Skip other updates when dead
        }
        if (isSlashing) {
            updateSlash(); // Update character slash animation
        }

        updateSlashEffect(); // Update the slash effect
        ////****
        
        // Regular movement
        if (isMovingLeft) {
            x -= 13;
            lastDirectionLeft = true;
        }
        if (isMovingRight) {
            x += 13;
            lastDirectionLeft = false;
        }

        // Jumping and falling logic
        if (isJumping) {
            y -= jumpVelocity;
            jumpVelocity -= 2;
            if (jumpVelocity <= 0) {
                isJumping = false;
                isFalling = true;
            }
        }
        if (isFalling) {
            y += 15;
        }
        if(y>1000){
        	die();
        }
        frame = (isMovingLeft || isMovingRight) ? (frame % 4) + 1 : 1; // Update walking frame
    }
    /*
     * Purpose: To set the x coordinates of the knight
     * Pre: int q, the value of the x coordinates
     * Post: n/a
     */
    public void setX(int q){
    	x = q;
    }
    /*
     * Purpose: To make the knight invisible
     * Pre: n/a
     * Post: n/a
     */
   public void invinsible() {
	   width = 0;
	   height = 0;
   }
   /*
    * Purpose: To update the dashframes and variables
    * Pre: n/a
    * Post: n/a
    */
    public void updateDash() {
        if (isDashing) {
            // Update position
        
        	int k = y;
        	width = 0; 
        	height = 0;
            int dashAmount = dashLeft ? -dashSpeed : dashSpeed;
            x += dashAmount;
            dashDistanceCovered += Math.abs(dashAmount);

            // Update dash frame with delay
            dashFrameDelay++;
            if (dashFrameDelay >= dashFrameDelayLimit) {
                dashFrame = (dashFrame % totalDashFrames) + 1; // Cycle through 1 to 8
                dashFrameDelay = 0; // Reset delay
            }

            // Stop dashing when the distance is covered
            if (dashDistanceCovered >= dashDistance) {
                isDashing = false;
                width = 67;
                height = 127;
                y= k;
            }
        }
    }
    /*
     * Purpose: To update the death animation
     * Pre: n/a
     * Post: n/a
     */
    private void updateDeathAnimation() {
        deathFrameDelay++;
        if (deathFrameDelay >= deathFrameDelayLimit) {
            deathFrame++;
            deathFrameDelay = 0;
            
            // End death animation and respawn
            if (deathFrame > totalDeathFrames) {
                respawn();
            }
        }
    }
    /*
     * Purpose: To get the current images for the corresponding actions
     * Pre: n/a
     * Post: n/a
     */
    public Image getCurrentImage() {
        String imagePath;
        
        if (isDashing) {
            imagePath = dashLeft ? "dash_left_" + dashFrame + ".png" : "dash_right_" + dashFrame + ".png";
        } 
        else if (isKnockedBack || isDamaged) {
            imagePath = lastDirectionLeft ? "damage_shock_left.png" : "damage_shock_right.png";
        }
        else if (isDamaged) {
            imagePath = lastDirectionLeft ? "damage_shock_left.png" : "damage_shock_right.png";
        }
        else if (isSlashing) {
            imagePath = lastDirectionLeft ? "slash1_char_left_" + slashFrame + ".png" 
                    : "slash1_char_right_" + slashFrame + ".png";
        }else if (isDead) {
            imagePath = lastDirectionLeft ? "death_left_" + deathFrame + ".png"
                    : "death_right_" + deathFrame + ".png";
        }
        else if (isJumping || isFalling) {
            imagePath = isJumping ? (lastDirectionLeft ? "jump_left.png" : "jump_right.png")
                                  : (lastDirectionLeft ? "fall_left.png" : "fall_right.png");
        } else if (isMovingLeft) {
            imagePath = "walk_left_" + frame + ".png";}
            
        else if (isMovingRight) {
            imagePath = "walk_right_" + frame + ".png";
        } else {
            imagePath = lastDirectionLeft ? "player_left.png" : "player_right.png";
        }
        return new ImageIcon(imagePath).getImage();
    }

    /*
     * Purpose: To get the collionBox of the knight
     * Pre: n/a
     * Post: A rectangle - the collision box of the knight
     */
    public Rectangle getCollisionBox() {
        return new Rectangle(x, y, width, height);
    }

    /*
     * Purpose: To let the knight move left
     * Pre: n/a
     * Post: n/a
     */
    public void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
    }
    /*
     * Purpose: To let the knight move right
     * Pre: n/a
     * Post: n/a
     */
    public void moveRight() {
        isMovingRight = true;
        isMovingLeft = false;
    }
    /*
     * Purpose: To let the knight jump
     * Pre: n/a
     * Post: n/a
     */
    public void jump() {
        if (!isJumping && !isFalling) {
            isJumping = true;
            jumpVelocity = 20;
            isDoubleJumping = 0; 
        }
    }
    /*
     * Purpose: To get the normal hitbox of the knight while dashing
     * Pre: n/a
     * Post: a rectangle - the dash box of the kngiht
     */
    public Rectangle DashBox(){
    	return new Rectangle(x,y,67,127);
    }
    /*
     * Purpose: To let the knight do a double jump
     * Pre: n/a
     * Post: n/a
     */
    public void doubleJump() {
        if ((isJumping || isFalling) && isDoubleJumping < 1) { 
            jumpVelocity = 20; 
            isFalling = false; 
            isJumping = true;  
            isDoubleJumping++; 
        }
    }
    /*
     * Purpose: To set the health of the knight
     * Pre: int t, the health of the knight that people want to set
     * Post: n/a
     */
    public void setHealth(int t) {
    	health = t;
    }
    /*
     * Purpose: To let the knight stop moving left
     * Pre: n/a
     * Post: n/a
     */
    public void stopMovingLeft() {
        isMovingLeft = false;
    }
    /*
     * Purpose: To let the knight stop moving right
     * Pre: n/a
     * Post: n/a
     */
    public void stopMovingRight() {
        isMovingRight = false;
    }
    /*
     * Purpose: To check if the knight is jumping
     * Pre: n/a
     * Post: a boolean - if the knight is jumping
     */
    public boolean isJumping() {
        return isJumping;
    }
    /*
     * Purpose: To check if the knight is falling
     * Pre: n/a
     * Post: a boolean - if the knight is jumping
     */
    public boolean isFalling() {
        return isFalling;
    }
    /*
     * Purpose: To set the knight falling
     * Pre: n/a
     * Post: a boolean - if the knight is falling
     */
    public void setFalling(boolean falling) {
        isFalling = falling;
    }
    /*
     * Purpose: To set the y coordinates of the knight 
     * Pre: an integer - the new y value
     * Post: n/a
     */
    public void setY(int newY) {
        y = newY;
    }
    /*
     * Purpose: To get the x coordinates of the knight
     * Pre: n/a
     * Post: an integer x - the x value
     */
    public int getX() {
        return x;
    }
    /*
     * Purpose: To get the y coordinates of the knight
     * Pre: n/a
     * Post: an integer y - the x value
     */
    public int getY() {
        return y;
    }
    /*
     * Purpose: To check if the knight is facing left
     * Pre: n/a
     * Post: a boolean - if the knight is facing left
     */
    public boolean isFacingLeft() {
        return lastDirectionLeft;
    }
    /*
     * Purpose: To adjust the value of the doubleJumping variables
     * Pre: an integers - the value of the target doublejumping variables
     * Post: n/a
     */
    public void setIsDoubleJumping(int value) {
        isDoubleJumping = value;
    }
    /*
     * Purpose: To adjust the value of the dashingtime  variables
     * Pre: an integers - the value of the target dashingtime variables
     * Post: n/a
     */
    public void setDash(int value) {
    	dashtime = value;
    }
    /*
     * Purpose: To damage the knight
     * Pre: a string - the direction of the damage from
     * Post: n/a
     */
    public void damage(String direction) {
        if (!isDamaged && !isDead) {
            health--; // Reduce health
            isDamaged = true; // Start invincibility
            damageStartTime = System.currentTimeMillis(); // Record damage time
            isKnockedBack = true; // Trigger knockback
            knockbackCovered = 0; // Reset knockback progress
            knockbackLeft = direction.equalsIgnoreCase("left"); // Determine knockback direction

            // Check if health is below 0
            if (health <= 0) {
                die(); // Trigger death
            }
        }
    }
    /*
     * Purpose: To get the health of the knight
     * Pre: n/a
     * Post: n/a
     */
	public int getHealth() {
	
		return health;
	}
	 /*
     * Purpose: To apply a knockback on the knight
     * Pre: n/a
     * Post: n/a
     */
	private void applyKnockback() {
	    int knockbackStep = knockbackLeft ? -knockbackSpeed : knockbackSpeed;
	    x += knockbackStep;
	    knockbackCovered += Math.abs(knockbackStep);

	    // End knockback after covering the total distance
	    if (knockbackCovered >= knockbackDistance) {
	        isKnockedBack = false;
	        isDamaged = false; // Reset damaged state after knockback
	    }
	}
}
