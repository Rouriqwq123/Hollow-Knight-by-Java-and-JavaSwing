package CPT;

import javax.swing.*;

import CPT.Platform;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Menu extends JFrame implements ActionListener {
    private JPanel cardPanel, gamePanel; // panels for the menu and the games
    private CardLayout cardLayout;// layout
    private boolean collisionbox_show = false;
    private JButton playButton, controlsButton, quitButton, backButton; // buttons for the menu
    private ArrayList<Platform> platforms1;// to put the platform for the knight to jump
    private ArrayList<Rectangle> portalZone1;// change the map
    private ArrayList<Rectangle> portalZone2;// change the map
    private ArrayList<Gruzzer> gruzzer;// monster
    private Monster monster, monster2;// monster
    private Knight knight;// knight
    private Timer gameTimer;
    // background Image
    private Image backgroundImage;
    private Image backgroundImage1;
    private Image backgroundImage2;
    private Image backgroundImage3;
    private Image backgroundImage4;
    public static boolean map2 = false;// map 2
    private Husk husk1; // Declare the Husk object
    private JLabel label; // Label for instructions
  //set up for the map 1
    // platforms 1
   
    /*
     * Purpose: to put the platforms into the first map and the knight
     * Pre: n/a
     * Post: n/a
     */
    public void platforms_l1(){    	
    	knight.setRespawn(43, 0);
    	platforms1 = new ArrayList<>();
    	platforms1.clear();
	    // platforms
        platforms1.add(new Platform(550, 780, 150, 30, "floor1.png"));
        platforms1.add(new Platform(836, 722, 150, 30, "floor1.png"));
        platforms1.add(new Platform(452, 488, 150, 30, "floor1.png"));
        platforms1.add(new Platform(1151, 682, 150, 30, "floor1.png"));
        platforms1.add(new Platform(1449, 599, 150, 30, "floor1.png"));
        platforms1.add(new Platform(1200, 442, 150, 30, "floor1.png"));
        platforms1.add(new Platform(1506, 267, 150, 30, "floor1.png"));
        platforms1.add(new Platform(1752, 522, 300, 30, ""));
        platforms1.add(new Platform(1758, 214, 300, 30, ""));
        platforms1.add(new Platform(-700, 910, 3000, 10, ""));
        platforms1.add(new Platform(137, 913, 300, 30, "bridge.png"));
        platforms1.add(new Platform(1563, 913, 300, 30, "bridge.png"));
    }
    /*
     * Purpose: to put the monsters, platforms and set a new coordinate for the knight into the second map
     * Pre: n/a
     * Post: n/a
     */
    public void platforms_l2(){
    	gruzzer = new ArrayList<>();
    	gruzzer.add(new Gruzzer(616,594,1051,607,new String[]{"gruzzer_1.png","gruzzer_2.png"},knight));
    	gruzzer.add(new Gruzzer(1499,213,1788,626,new String[]{"gruzzer_1.png","gruzzer_2.png"},knight));
    	map2 = true;
    	monster.disappear();
    	monster2.disappear();
    	husk1 = new Husk(882, 177-110, 1301, knight); // Initialize the Husk
    	 
    	knight.x = 72;
	    knight.y = 100;
	    // platforms
    	platforms1 = new ArrayList<>();
    	platforms1.add(new Platform(0, 404, 236, 604, "block3.png"));
    	platforms1.add(new Platform(848, 170, 562, 300, "block2.png"));
    	platforms1.add(new Platform(848, 706, 562, 300, "block2.png"));
    	platforms1.add(new Platform(307, 578, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(314, 793, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(524, 813, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(551, 574, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(695, 711, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(633, 455, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(1578, 558, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(1661, 746, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(1751, 542, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(1557, 340, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(1805, 367, 200, 100, "floor2.png"));
    	platforms1.add(new Platform(316, 398, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(526, 347, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(344,265, 128, 45, "floor3.png"));
    	platforms1.add(new Platform(570,157, 128, 45, "floor3.png"));
    	     
    }
    /*
     * Purpose: to make a platform and to let the knight stand at the ending 
     * Pre: n/a
     * Post: n/a
     */
    public void platforms_l3(){
    	platforms1 = new ArrayList<>();
        platforms1.add(new Platform(-700, 910, 3000, 10, ""));
    }
    
    public Menu() {
        super("Hollow Knight");
        knight = new Knight(43, 0);
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        

        //portalzone 
        portalZone1 = new ArrayList<>();
        portalZone1.add(new Rectangle(1850,262,200,300));
        portalZone1.add(new Rectangle(1857,2,200,300));
    	portalZone2 = new ArrayList<>();
        portalZone2.add(new Rectangle(1804,236,200,300));
        
        // the card layout to show the menu and the game
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel);

        cardPanel.add(createMainMenu(), "main");
        cardPanel.add(createGifPanel(), "gif");
        cardPanel.add(createGamePanel(), "game");

        cardLayout.show(cardPanel, "main");
        setVisible(true);
    }
    /*
     * Purpose: to output the monsters and the background image, make the collision box for the monsters and the knight, and to set the platforms of the platforms
     * Pre: n/a
     * Post: return gamePanel
     */
    private JPanel createGamePanel() {
        monster = new Monster(30, 830, new String[]{"bug1.png", "bug2.png", "bug3.png", "bug4.png"});
        monster2 = new Monster(1000, 830, new String[]{"bug1.png", "bug2.png", "bug3.png", "bug4.png"});
        
        platforms_l1();
        gamePanel = new JPanel(null) { // Set layout to null for absolute positioning
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);

                g.drawImage(knight.getCurrentImage(), knight.getX(), knight.getY(), null);
                int maxHealth = 5; // Maximum health
                int currentHealth = Math.max(knight.getHealth(), 0); 
                int barWidth = 200; // Total width of the health bar
                int barHeight = 20; // Height of the health bar
                int healthWidth = (int) ((currentHealth / (double) maxHealth) * barWidth); // Width based on current health

                // Draw the background of the health bar
                g.setColor(Color.GRAY);
                g.fillRect(20, 20, barWidth, barHeight);

                // Draw the current health portion
                g.setColor(Color.RED);
                g.fillRect(20, 20, healthWidth, barHeight);

                // Draw the border of the health bar
                g.setColor(Color.BLACK);
                g.drawRect(20, 20, barWidth, barHeight);
                
                          
                           
                if (knight.isShowingSlashEffect()) {
                    Image slashEffect = knight.getSlashEffectImage();
                    int offsetX = knight.isFacingLeft() ? -230 : 70;
                    g.drawImage(slashEffect, knight.getX() + offsetX, knight.getY() - 20, null);
                }

                for (Platform platform : platforms1) {
                    g.drawImage(platform.getImage(), platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight(), null);
                }
                 
                if (collisionbox_show) {
                    g.setColor(Color.RED);
                    Rectangle collisionBox = knight.getCollisionBox();
                    g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);

                    for (Platform platform : platforms1) {
                        g.drawRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
                    }
                    
                    for (Rectangle rec : portalZone1) {
                       g2d.draw(rec);	
                    }
                    
                    Rectangle collisionBox2 = monster.getCollisionBox();               
                    g.drawRect(collisionBox2.x, collisionBox2.y, collisionBox2.width, collisionBox2.height);
                    Rectangle collisionBox3 = knight.getCollisionBox_Slash();
                    g.drawRect(collisionBox3.x, collisionBox3.y, collisionBox3.width, collisionBox3.height);
                    Rectangle collisionBox4 = monster2.getCollisionBox();
                    g.drawRect(collisionBox4.x, collisionBox4.y, collisionBox4.width, collisionBox4.height);                  
                    
					if(map2) {
					for(Gruzzer gruzzer1:gruzzer) {
                    Rectangle collisionBox5 = gruzzer1.getCollisionBox();
                    g.drawRect(collisionBox5.x, collisionBox5.y, collisionBox5.width, collisionBox5.height);}
					}
                }         
               
                monster.draw(g);
                monster2.draw(g);
                if (map2) {
                	for(Gruzzer gruzzer1:gruzzer) {
                    gruzzer1.draw(g);
                	}
                    if (husk1 != null) { // Check if husk1 is initialized
                        husk1.draw(g);
                    }
                  
            }
        }};

        gamePanel.setPreferredSize(new Dimension(1920, 1080));
        gamePanel.setFocusable(true);
        backgroundImage = backgroundImage1;
        backgroundImage1 = new ImageIcon("fb_1.jpg").getImage();
        backgroundImage2 = new ImageIcon("fb_2.png").getImage();
        backgroundImage3 = new ImageIcon("fb3.png").getImage();
        backgroundImage4 = new ImageIcon("Bigbossroom.jpg").getImage();
        backgroundImage = backgroundImage1;
        label = new JLabel("Press the 'A' key to go left", SwingConstants.CENTER);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        label.setBounds(1300, 10, 400, 50);
        label.setForeground(Color.WHITE);
        gamePanel.add(label);
     // Add the button
        JButton dismissBtn = new JButton("Dismiss");
        dismissBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        dismissBtn.setBounds(1750, 10, 120, 50);// Place it next to the label
     // Makes the button's background transparent
        dismissBtn.setOpaque(false); 
        dismissBtn.setContentAreaFilled(false); 
        dismissBtn.setBorderPainted(false);
        dismissBtn.setForeground(Color.WHITE);
        gamePanel.add(dismissBtn);

        // Add an action listener to the button
        dismissBtn.addActionListener(e -> {
            label.setVisible(false); // Hide the label
            dismissBtn.setVisible(false); // Hide the button
        });

        gamePanel.addKeyListener(new KeyAdapter() {        	
        	 /*
             * Purpose: to make the key pressed for the knight to move jump and attack
             * Pre: n/a
             * Post: n/a
             */
            @Override
            public void keyPressed(KeyEvent e) {
            	if (e.getKeyCode() == KeyEvent.VK_A) {
                    knight.moveLeft();
                    label.setText("Press the 'D' key to go right");
            	}
                 else if (e.getKeyCode() == KeyEvent.VK_D) {
                    knight.moveRight();
                    label.setText("Press the 'SPACE' key to jump ");
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (!knight.isJumping() && !knight.isFalling()) {
                        knight.jump(); // First jump
                        label.setText("double tap the 'space' to double jump");
                    } else if (knight.isJumping() || knight.isFalling()) {
                        knight.doubleJump(); // Double jump during jump or fall
                        label.setText("Press the 'C' key to dash");
                    }
                }
                else if(e.getKeyCode()==KeyEvent.VK_0) {
                	collisionbox_show =!collisionbox_show;
                }else if (e.getKeyCode() == KeyEvent.VK_C && !knight.isDashing()) {
                    knight.startDash(knight.isFacingLeft());   
                    label.setText("Press the 'Q' key to Slash");
                }///****
                else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    knight.startSlash();
                } 
            }
            /*
             * Purpose: When the key released the knight stop moving
             * Pre: n/a
             * Post: n/a
             */
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    knight.stopMovingLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    knight.stopMovingRight();
                }
            }
        });

        gameTimer = new Timer(45, e -> updateGame());
        gameTimer.start();

        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("Clicked coordinates: X=" + x + ", Y=" + y);
                knight.x = e.getX();
                knight.y= e.getY();
            }
        });

        return gamePanel;
    }
    /*
     * Purpose: to set hte logic for the knight and the monster, when the knight hit the monster, the monster take damages or dead, also for the knight, and for transition of the map and the end screen
     * Pre: n/a
     * Post: n/a
     */

    private void updateGame() {
    	Rectangle kb = knight.getCollisionBox();
    	Rectangle m1b = monster.getCollisionBox();
    	Rectangle m2b = monster2.getCollisionBox();
    	if(kb.intersects(m1b)) {
    		knight.damage(monster.getDirection());
    		knight.setHealth(knight.getHealth()-1);
    		System.out.println(knight.getHealth());
    	}
    	if(kb.intersects(m2b)) {
    		knight.damage(monster2.getDirection());
    		knight.setHealth(knight.getHealth()-1);
    		System.out.println(knight.getHealth());
    	}
    	if(map2) {
    		for(Gruzzer gruzzer1:gruzzer) {
    		Rectangle g1b = gruzzer1.getCollisionBox();
    		
    	if(kb.intersects(g1b)) {
    		knight.damage(kb.x>g1b.x?"right":"left");
    	}}
    	 
    	        Rectangle huskBox = husk1.getCollisionBox();
    	        if (kb.intersects(huskBox)) {
    	            knight.damage(kb.x > huskBox.x ? "right" : "left");
    	        }
    	    
    	}
    	if(knight.x<0){
    		
    		knight.isMovingLeft = false;
    		
    	}else if(knight.x>1880){
    		knight.isMovingRight = false;
    	}
    	
    	
    	
    	// hitting detection
    	if (knight.isSlashing()) {
            Rectangle slashBox = knight.getCollisionBox_Slash();
            Rectangle monsterBox = monster.getCollisionBox();
            
            if (slashBox.intersects(monsterBox)) {
                monster.takeDamage(); // Decrease health
            }
        }
    	if (knight.isSlashing()) {
            Rectangle slashBox = knight.getCollisionBox_Slash();
            Rectangle monsterBox2 = monster2.getCollisionBox();
            
            if (slashBox.intersects(monsterBox2)) {
                monster2.takeDamage(); // Decrease health
            }
        }if(map2) {
        	
    	if (knight.isSlashing()) {
    		for(Gruzzer gruzzer1:gruzzer) {
            Rectangle slashBox = knight.getCollisionBox_Slash();
            Rectangle b = gruzzer1.getCollisionBox();
            
            if (slashBox.intersects(b)) {
                gruzzer1.takeDamage(); // Decrease health
            }
    		}Rectangle slashBox = knight.getCollisionBox_Slash();
    		Rectangle c = husk1.getCollisionBox();
    		 if (slashBox.intersects(c)) {
                 husk1.takeDamage(); // Decrease health
             }
    		 if (knight.isSlashing()) {
    	            Rectangle slashBox1 = knight.getCollisionBox_Slash();
    	            Rectangle monsterBox = monster.getCollisionBox();
    	                	           
    		 }
    	}
        }
        if (!knight.isDashing()) {
            knight.updatePosition(); // Regular movement
        } else {
            knight.updateDash(); // Dash movement
        }
        knight.updateSlash(); // Update slash animation

        boolean isOnPlatform = false;
        if(!map2){
        for (Rectangle q : portalZone1) {
        	Rectangle knightBox_portal = knight.getCollisionBox();
        	if (knightBox_portal.intersects(q)) {
        	    backgroundImage = backgroundImage2;
        	    platforms_l2();
        	    gamePanel.repaint();
        	    
        	    monster.disappear();
        	}
        }
        	   	
        }
        

        for (Platform platform : platforms1) {
            Rectangle knightBox = knight.getCollisionBox();
            Rectangle platformBox = platform.getCollisionBox();

            // Check if the knight is on the platform
            if (knightBox.y + knightBox.height >= platformBox.y - 10 &&
                knightBox.y + knightBox.height <= platformBox.y + 10 && // Allowable range
                knightBox.x + knightBox.width / 2 >= platformBox.x &&   // Knight's center point within platform
                knightBox.x + knightBox.width / 2 <= platformBox.x + platformBox.width) {

                knight.setY(platformBox.y - knightBox.height); 
                isOnPlatform = true;
                knight.setFalling(false);
                knight.setIsDoubleJumping(0);
                knight.setDash(0);
                break; // Stop checking other platforms
            }
if (map2) {
	     if (map2) {
	    for (Rectangle q1 : portalZone2) {
	        Rectangle knightBox_portal1 = knight.getCollisionBox();
	        if (knightBox_portal1.intersects(q1)) {
	            backgroundImage = backgroundImage4; 
	            platforms_l3();// use platform 3
	            // clear every things
	            gruzzer.clear();
	            husk1.disappear();
	            gamePanel.repaint();
	            // end screen
	            JLabel youWinLabel = new JLabel("YOU WIN", SwingConstants.CENTER);
	            youWinLabel.setFont(new Font("Times New Roman", Font.BOLD, 72));
	            youWinLabel.setForeground(Color.WHITE);
	            youWinLabel.setBounds(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
	            youWinLabel.setHorizontalAlignment(SwingConstants.CENTER);
	            gamePanel.add(youWinLabel);	           

	            // Add "Exit" Button
	            JButton exitButton = new JButton("Exit");
	            exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
	            exitButton.setBounds(gamePanel.getWidth() / 2 - 150, gamePanel.getHeight() / 2 + 120, 300, 50);
	            exitButton.addActionListener(e -> System.exit(0));
	            gamePanel.add(exitButton);

	            gamePanel.setLayout(null);
	        } 
	    }
	 }
	     // set walls to block the knight to enter the big platforms
            	ArrayList<Rectangle>  wall1 = new ArrayList<Rectangle>();
            	ArrayList<Rectangle>  wall2 = new ArrayList<Rectangle>();
            	wall1.add(new Rectangle(760, 170, 1, 300)); 
                wall2.add(new Rectangle(1410, 170, 1, 300)); 
                wall1.add(new Rectangle(760, 706, 1, 300));  
                wall2.add(new Rectangle(1410, 706, 1, 300));

            	for(Rectangle wall : wall1){
            		if (knight.getCollisionBox().intersects(wall) || knight.DashBox().intersects(wall)) {
                    knight.isMovingRight = false; 
                    knight.isDashing = false; 
                    knight.width = 67;
                    knight.height =137;
                    knight.setX(wall.x + wall.width); 
            	   }
                }
            	for (Rectangle wall : wall2) {
            	    if (knight.getCollisionBox().intersects(wall) || knight.DashBox().intersects(wall)) {
            	        knight.isMovingRight = false; 
            	        knight.isDashing = false; // Stop dashing
            	        knight.width = 67;
                        knight.height =137;
            	        knight.setX(wall.x - wall.width); // Push knight to the left of the wall
            	    }
            	}
        	}
        }

        // Update monster position outside the loop
        monster.updatePosition(gamePanel.getWidth());
        monster2.updatePosition(gamePanel.getWidth());
        if(map2) {
        	for(Gruzzer gruzzer1:gruzzer) {
        gruzzer1.updatePosition(1080);
        	}
        if (map2 && husk1 != null) {
            husk1.updatePosition(); // Update husk1 only if initialized
        }
        		}
        // If knight is not on any platform, ensure it's falling unless already grounded
        if (!isOnPlatform && !knight.isJumping() && !knight.isDashing() && knight.getY() < 800) {
            knight.setFalling(true);
        } else if (knight.getY() >= 1090) {
            knight.setFalling(false);
            knight.setY(1090);
        }

        gamePanel.repaint(); // Redraw the game screen
    }
    /*
     * Purpose: to create the Menu to start the game
     * Pre: n/a
     * Post:return mainMenu
     */
    public JPanel createMainMenu() {
        IPanel mainMenu = new IPanel("background.jpg");
        mainMenu.setLayout(null);

        ImageIcon nameIcon = new ImageIcon("Nameqwq.png");
        Image img = nameIcon.getImage().getScaledInstance(1378, 527, Image.SCALE_SMOOTH);
        JLabel nameLabel = new JLabel(new ImageIcon(img));
        nameLabel.setBounds(250, 30, 1378, 527);
        mainMenu.add(nameLabel);

        playButton = new JButton("<START>");
        playButton.setBounds(840, 600, 200, 50);
        playButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        styleButton(playButton);
        playButton.addActionListener(this);
        mainMenu.add(playButton);

        controlsButton = new JButton("<CONTROL>");
        controlsButton.setBounds(820, 680, 250, 50);
        controlsButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        styleButton(controlsButton);
        controlsButton.addActionListener(this);
        mainMenu.add(controlsButton);

        quitButton = new JButton("<Quit Game>");
        quitButton.setBounds(840, 760, 200, 50);
        quitButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        styleButton(quitButton);
        quitButton.addActionListener(this);
        mainMenu.add(quitButton);

        return mainMenu;
    }
    /*
     * Purpose: to show a gif video before the game start
     * Pre: n/a
     * Post: return gifPanel
     */
    public JPanel createGifPanel() {
        JPanel gifPanel = new JPanel(new BorderLayout());
        gifPanel.setBackground(Color.BLACK);

        ImageIcon gifIcon = new ImageIcon("Hollow Knight.gif");
        Image scaledGif = gifIcon.getImage().getScaledInstance(1278, 720, Image.SCALE_DEFAULT);
        JLabel gifLabel = new JLabel(new ImageIcon(scaledGif));
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifPanel.add(gifLabel, BorderLayout.CENTER);

        backButton = new JButton("<Back>");
        backButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        styleButton(backButton);
        backButton.addActionListener(this);
        gifPanel.add(backButton, BorderLayout.SOUTH);

        return gifPanel;
    }
    /*
     * Purpose: to set the style for the Button
     * Pre: n/a
     * Post: n/a
     */
    public void styleButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
    }
    /*
     * Purpose: the buttons in the menu
     * Pre: n/a
     * Post: n/a
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == playButton) {
            cardLayout.show(cardPanel, "gif");
            SwingUtilities.invokeLater(() -> {
                Timer timer = new Timer(1, event -> {
                    cardLayout.show(cardPanel, "game");
                    gamePanel.requestFocusInWindow();
                });
                timer.setRepeats(false);
                timer.start();
            });
        } else if (source == controlsButton) {
            showControlPanel();
        } else if (source == quitButton) {
            System.exit(0);
        } else if ("backToMain".equals(e.getActionCommand())) {
            cardLayout.show(cardPanel, "main");
        } else if (source == backButton) {
            cardLayout.show(cardPanel, "main");
        }
    }
    /*
     * Purpose: show the control image and a button
     * Pre: n/a
     * Post: n/a
     */
    public void showControlPanel() {
        JPanel controlPanel = new JPanel(null);
        controlPanel.setBackground(Color.BLACK);

        ImageIcon controlIcon = new ImageIcon("control.png");
        Image scaledImage = controlIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_SMOOTH);
        JLabel controlImage = new JLabel(new ImageIcon(scaledImage));
        controlImage.setBounds(0, 0, 1920, 1080);
        controlPanel.add(controlImage);

        JButton exitButton = new JButton("<Back>");
        exitButton.setBounds(840, 900, 200, 70);
        exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        styleButton(exitButton);

        exitButton.addActionListener(e -> {
            cardPanel.remove(controlPanel);
            cardLayout.show(cardPanel, "main");
        });

        controlPanel.add(exitButton);
        cardPanel.add(controlPanel, "control");
        cardLayout.show(cardPanel, "control");
    }

    public static void main(String[] args) {
        new Menu();
    }
}