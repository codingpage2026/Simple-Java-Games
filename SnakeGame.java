import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final int SIZE = 20;

    private int[] snakeX = new int[400];
    private int[] snakeY = new int[400];

    private int length = 5;

    private int foodX;
    private int foodY;

    private int dx = SIZE;
    private int dy = 0;

    private boolean running = true;

    private final Random random = new Random();

    private final Timer timer;

    public SnakeGame() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

        for (int i = 0; i < length; i++) {

            snakeX[i] = 200 - i * SIZE;
            snakeY[i] = 200;
        }

        createFood();

        timer = new Timer(100, this);
        timer.start();
    }

    private void createFood() {

        foodX = random.nextInt(WIDTH / SIZE) * SIZE;
        foodY = random.nextInt(HEIGHT / SIZE) * SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (running) {

            g.setColor(Color.RED);
            g.fillOval(foodX, foodY, SIZE, SIZE);

            for (int i = 0; i < length; i++) {

                if (i == 0) {
                    g.setColor(Color.GREEN);
                }
                else {
                    g.setColor(new Color(0, 180, 0));
                }

                g.fillRect(
                        snakeX[i],
                        snakeY[i],
                        SIZE,
                        SIZE
                );
            }

            g.setColor(Color.WHITE);
            g.drawString(
                    "Score: " + (length - 5),
                    10,
                    20
            );

        }
        else {

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));

            g.drawString(
                    "GAME OVER",
                    220,
                    180
            );

            g.setFont(new Font("Arial", Font.PLAIN, 18));

            g.drawString(
                    "Press R to restart",
                    220,
                    215
            );

            g.drawString(
                    "Score: " + (length - 5),
                    260,
                    245
            );
        }
    }

    private void move() {

        for (int i = length - 1; i > 0; i--) {

            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        snakeX[0] += dx;
        snakeY[0] += dy;
    }

    private void checkFood() {

        if (snakeX[0] == foodX &&
            snakeY[0] == foodY) {

            length++;

            createFood();
        }
    }

    private void checkCollision() {

        if (snakeX[0] < 0 ||
            snakeX[0] >= WIDTH ||
            snakeY[0] < 0 ||
            snakeY[0] >= HEIGHT) {

            running = false;
            timer.stop();
            return;
        }

        for (int i = 1; i < length; i++) {

            if (snakeX[0] == snakeX[i] &&
                snakeY[0] == snakeY[i]) {

                running = false;
                timer.stop();
                return;
            }
        }
    }

    private void restart() {

        length = 5;

        dx = SIZE;
        dy = 0;

        running = true;

        for (int i = 0; i < length; i++) {

            snakeX[i] = 200 - i * SIZE;
            snakeY[i] = 200;
        }

        createFood();

        timer.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            move();
            checkFood();
            checkCollision();

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && dy == 0) {

            dx = 0;
            dy = -SIZE;
        }

        else if (key == KeyEvent.VK_DOWN && dy == 0) {

            dx = 0;
            dy = SIZE;
        }

        else if (key == KeyEvent.VK_LEFT && dx == 0) {

            dx = -SIZE;
            dy = 0;
        }

        else if (key == KeyEvent.VK_RIGHT && dx == 0) {

            dx = SIZE;
            dy = 0;
        }

        else if (key == KeyEvent.VK_R && !running) {

            restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");

        SnakeGame game = new SnakeGame();

        frame.add(game);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setResizable(false);

        frame.setVisible(true);

        game.requestFocusInWindow();
    }
}