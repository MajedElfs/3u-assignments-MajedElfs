package FinalProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;

/**
 *
 * @author hadim9637
 */
public class FinalProject extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 1000;
    static final int HEIGHT = 700;
    //Title of the window
    String title = "My Game";
    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an approproate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));
    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;
    // YOUR GAME VARIABLES WOULD GO HERE
    Color grass = new Color(23, 183, 31);
    Rectangle mainChar = new Rectangle(400, 350, 50, 50);
    Rectangle enemy = new Rectangle(500, 10, 50, 50);
    //Control variables
    boolean charRight = false;
    boolean charLeft = false;
    //Speed of Character
    int charSpeed = 5;
    //Monster's fall speed
    int mobFallSpeed = 2;
    int originalPosX = 500;
    int originalPosY = 350;
    boolean movingRight = true;
    int stopPosLeft = enemy.x - 100;
    int stopPosRight = enemy.x + 100;
    int health = 3;
    Font BiggerFont = new Font("arial",Font.BOLD,36);
    //Getting hit (flashing)
    long flashUntil = System.currentTimeMillis();
    int flashDelay = 50;
    boolean flash = false;
    //Temporary game over screen
    String gameOver = new String("Game Over");
    //Hitting
    boolean hitting = false;


    // GAME VARIABLES END HERE    
    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public FinalProject() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        gameTimer = new Timer(desiredTime, this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.GRAY);
        g.fillRect(0, 400, WIDTH, 650);
        g.setColor(Color.WHITE);
        g.fillRect(mainChar.x, mainChar.y, mainChar.width, mainChar.height);
        g.setColor(Color.RED);
        g.fillRect(enemy.x, enemy.y, enemy.width, enemy.height);
        g.setFont(BiggerFont);
        g.drawString(""+health, 50, 50);
        if (flash){
            g.setColor(Color.RED);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
        if (health <= 0){
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.setFont(BiggerFont);
            g.drawString(gameOver, 400, 350);
        }
        
        


        // GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void preSetup() {
        // Any of your pre setup before the loop starts should go here
    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void gameLoop() {
        moveChar();
        enemy();
        collision();
        healthBar();
    }

    private void moveChar() {
        if (charRight) {
            mainChar.x = mainChar.x + charSpeed;
        } else if (charLeft) {
            mainChar.x = mainChar.x - charSpeed;
        }

        if (mainChar.x > WIDTH) {
            mainChar.x = WIDTH - mainChar.width;
        } else if (mainChar.x < 0) {
            mainChar.x = 0;
        }
    }

    private void enemy() {
        //Causes the enemy to fall
        if (enemy.y < 350) {
            enemy.y = enemy.y + mobFallSpeed;
        }
        if (movingRight) {
            enemy.x++;
        } else {
            enemy.x--;
        }
        if (enemy.x == stopPosRight) {
            movingRight = false;
        } else if (enemy.x == stopPosLeft) {
            movingRight = true;
        }

    }

    private void collision() {
        
    }

    private void healthBar() {
        if (mainChar.intersects(enemy)) {
            health--;
            //If enemy is on the right of the main char and is touched, bounce back
            if (enemy.x>mainChar.x){
                mainChar.x = mainChar.x - 50;
            }//If enemy is on the left of the main char and is touched, bounce back
            else if (enemy.x<mainChar.x){
               mainChar.x = mainChar.x + 50; 
            }
            
            flash = true;
            flashUntil = System.currentTimeMillis() + flashDelay;
        }
        
        if(System.currentTimeMillis() > flashUntil){
            flash = false;
        }
       
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {
        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT) {
                charRight = true;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                charLeft = true;
            }
            if (keyCode ==KeyEvent.VK_SPACE){
                
            }

        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_RIGHT) {
                charRight = false;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                charLeft = false;
            }
            if (keyCode ==KeyEvent.VK_SPACE){
                hitting = true;
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        preSetup();
        gameLoop();
        repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        FinalProject game = new FinalProject();
    }
}
