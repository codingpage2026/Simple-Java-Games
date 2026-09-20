import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class CarRacing extends JPanel implements KeyListener {

    private int carX = 280;
    private final int carY = 320;

    private int score = 0;
    private boolean gameOver = false;

    private final ArrayList<Rectangle> enemyCars = new ArrayList<>();
    private final Random random = new Random();

    private final javax.swing.Timer timer;

    public CarRacing() {
        setFocusable(true);
        setBackground(Color.GRAY);
        addKeyListener(this);

        createEnemies();

        timer = new javax.swing.Timer(25, e -> updateGame());
        timer.start();
    }

    private void createEnemies() {
        enemyCars.clear();

        for (int i = 0; i < 4; i++) {
            enemyCars.add(
                new Rectangle(
                    random.nextInt(400) + 80,
                    -100 - (i * 160),
                    45,
                    75
                )
            );
        }
    }

    private void updateGame() {

        if (gameOver) {
            return;
        }

        Rectangle playerCar =
                new Rectangle(carX, carY, 45, 75);

        for (Rectangle enemy : enemyCars) {

            enemy.y += 7;

            if (enemy.y > getHeight()) {
                enemy.y = -100;
                enemy.x = random.nextInt(400) + 80;
                score++;
            }

            if (enemy.intersects(playerCar)) {
                gameOver = true;
                timer.stop();
            }
        }

        repaint();
    }

    private void restartGame() {
        carX = 280;
        score = 0;
        gameOver = false;

        createEnemies();

        timer.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Grass
        g.setColor(new Color(40, 150, 60));
        g.fillRect(0, 0, width, height);

        // Road
        g.setColor(Color.DARK_GRAY);
        g.fillRect(60, 0, width - 120, height);

        // Road borders
        g.setColor(Color.WHITE);
        g.fillRect(65, 0, 5, height);
        g.fillRect(width - 70, 0, 5, height);

        // Center road lines
        g.setColor(Color.WHITE);

        for (int y = 0; y < height; y += 70) {
            g.fillRect(width / 2 - 5, y, 10, 40);
        }

        // Player car
        g.setColor(Color.BLUE);
        g.fillRoundRect(carX, carY, 45, 75, 10, 10);

        // Player windows
        g.setColor(Color.CYAN);
        g.fillRect(carX + 8, carY + 10, 29, 20);

        // Player wheels
        g.setColor(Color.BLACK);
        g.fillRect(carX - 4, carY + 10, 5, 18);
        g.fillRect(carX + 44, carY + 10, 5, 18);

        g.fillRect(carX - 4, carY + 50, 5, 18);
        g.fillRect(carX + 44, carY + 50, 5, 18);

        // Enemy cars
        for (Rectangle enemy : enemyCars) {

            g.setColor(Color.RED);
            g.fillRoundRect(
                    enemy.x,
                    enemy.y,
                    enemy.width,
                    enemy.height,
                    10,
                    10
            );

            g.setColor(Color.YELLOW);
            g.fillRect(
                    enemy.x + 8,
                    enemy.y + 10,
                    29,
                    20
            );

            g.setColor(Color.BLACK);
            g.fillRect(enemy.x - 4, enemy.y + 10, 5, 18);
            g.fillRect(enemy.x + 44, enemy.y + 10, 5, 18);

            g.fillRect(enemy.x - 4, enemy.y + 50, 5, 18);
            g.fillRect(enemy.x + 44, enemy.y + 50, 5, 18);
        }

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString(
                "Score: " + score,
                15,
                30
        );

        g.drawString(
                "← → Move",
                15,
                55
        );

        if (gameOver) {

            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 35));

            g.drawString(
                    "GAME OVER",
                    width / 2 - 120,
                    height / 2 - 20
            );

            g.setFont(new Font("Arial", Font.BOLD, 22));

            g.drawString(
                    "Score: " + score,
                    width / 2 - 50,
                    height / 2 + 20
            );

            g.drawString(
                    "Press R to Restart",
                    width / 2 - 100,
                    height / 2 + 60
            );
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (!gameOver) {

            if (key == KeyEvent.VK_LEFT) {
                carX -= 20;

                if (carX < 75) {
                    carX = 75;
                }
            }

            if (key == KeyEvent.VK_RIGHT) {
                carX += 20;

                if (carX > getWidth() - 120) {
                    carX = getWidth() - 120;
                }
            }
        }

        if (key == KeyEvent.VK_R && gameOver) {
            restartGame();
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame =
                    new JFrame("Car Racing Game");

            CarRacing game =
                    new CarRacing();

            frame.add(game);

            frame.setSize(650, 500);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );

            frame.setResizable(false);
            frame.setVisible(true);

            game.requestFocusInWindow();
        });
    }
}