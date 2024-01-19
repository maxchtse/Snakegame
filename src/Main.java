import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends JPanel implements KeyListener {
    public static final int CELL_SIZE = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = height / CELL_SIZE;
    public static int col = width / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Timer t;
    private int speed = 100;
    private static String direction;
    private boolean allowKeyPress;

    public Main() {
        snake = new Snake();
        fruit = new Fruit();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },0,speed);
        direction = "Right";
        addKeyListener(this);
        allowKeyPress = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        // Draw a black BG
        g.fillRect(0, 0, width, height);
        snake.drawSnake(g);
        fruit.drawFruit(g);

        // Remove snake tail and put in head
        int snakeX = snake.getSnakeBody().getFirst().x;
        int snakeY = snake.getSnakeBody().getFirst().y;

        // Snake Changing its direction
        if (direction.equals("Left")) {
            snakeX -= CELL_SIZE;
        } else if (direction.equals("Right")) {
            snakeX += CELL_SIZE;
        } else if (direction.equals("Up")) {
            snakeY -= CELL_SIZE;
        } else if (direction.equals("Down")) {
            snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);

        // Eating Logic
        // Check if the snake eats the fruit
        if (snake.getSnakeBody().getFirst().x == fruit.getX() && snake.getSnakeBody().getFirst().y == fruit.getY()) {
            // Draw a new fruit
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);

        } else {
            snake.getSnakeBody().removeLast();
        }
        snake.getSnakeBody().addFirst(newHead);

        allowKeyPress = true;
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (allowKeyPress) {
            if (e.getKeyCode() == 37 && !direction.equals("Right")) {
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
            allowKeyPress = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}