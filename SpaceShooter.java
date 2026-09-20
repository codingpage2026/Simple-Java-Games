import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.*;

public class SpaceShooter extends JPanel implements ActionListener, KeyListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;

    private int playerX = WIDTH / 2 - 25;
    private final int playerY = HEIGHT - 70;

    private int score = 0;
    private int lives = 3;

    private boolean gameOver = false;

    private final ArrayList<Rectangle> bullets = new ArrayList<>();
    private final ArrayList<Rectangle> enemies = new ArrayList<>();

    private final Random random = new Random();

    private final javax.swing.Timer timer;

    private int enemySpawnCounter = 0;

    public SpaceShooter() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        setFocusable(true);
        addKeyListener(this);

        timer = new javax.swing.Timer(20, this);
        timer.start();
    }

    private void spawnEnemy() {

        int x = random.nextInt(WIDTH - 40);

        enemies.add(
                new Rectangle(x, -40, 35, 35)
        );
    }

    private void shoot() {

        if (!gameOver) {

            bullets.add(
                    new Rectangle(
                            playerX + 22,
                            playerY - 15,
                            6,
                            15
                    )
            );
        }
    }

    private void updateGame() {

        if (gameOver) {
            return;
        }

        // Move bullets upward
        Iterator<Rectangle> bulletIterator =
                bullets.iterator();

        while (bulletIterator.hasNext()) {

            Rectangle bullet = bulletIterator.next();

            bullet.y -= 10;

            if (bullet.y < 0) {
                bulletIterator.remove();
            }
        }

        // Move enemies
        Iterator<Rectangle> enemyIterator =
                enemies.iterator();

        while (enemyIterator.hasNext()) {

            Rectangle enemy = enemyIterator.next();

            enemy.y += 3;

            if (enemy.y > HEIGHT) {

                enemyIterator.remove();

                lives--;

                if (lives <= 0) {
                    gameOver = true;
                    timer.stop();
                }
            }
        }

        // Bullet vs Enemy collision
        for (int i = bullets.size() - 1; i >= 0; i--) {

            Rectangle bullet = bullets.get(i);

            boolean hit = false;

            for (int j = enemies.size() - 1; j >= 0; j--) {

                Rectangle enemy = enemies.get(j);

                if (bullet.intersects(enemy)) {

                    bullets.remove(i);
                    enemies.remove(j);

                    score += 10;

                    hit = true;
                    break;
                }
            }

            if (hit) {
                continue;
            }
        }

        // Enemy vs Player collision
        Rectangle player =
                new Rectangle(
                        playerX,
                        playerY,
                        50,
                        30
                );

        for (int i = enemies.size() - 1; i >= 0; i--) {

            if (player.intersects(enemies.get(i))) {

                enemies.remove(i);

                lives--;

                if (lives <= 0) {

                    gameOver = true;
                    timer.stop();
                }

                break;
            }
        }

        // Spawn enemies
        enemySpawnCounter++;

        if (enemySpawnCounter >= 35) {

            spawnEnemy();

            enemySpawnCounter = 0;
        }

        repaint();
    }

    private void restartGame() {

        playerX = WIDTH / 2 - 25;

        score = 0;
        lives = 3;

        bullets.clear();
        enemies.clear();

        enemySpawnCounter = 0;

        gameOver = false;

        timer.start();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Stars
        g.setColor(Color.WHITE);

        for (int i = 0; i < 50; i++) {

            int x = (i * 97) % WIDTH;
            int y = (i * 53) % HEIGHT;

            g.fillRect(x, y, 2, 2);
        }

        // Player spaceship
        g.setColor(Color.BLUE);

        int[] xPoints = {
                playerX + 25,
                playerX,
                playerX + 50
        };

        int[] yPoints = {
                playerY - 10,
                playerY + 30,
                playerY + 30
        };

        g.fillPolygon(
                xPoints,
                yPoints,
                3
        );

        // Player body
        g.setColor(Color.CYAN);

        g.fillRect(
                playerX + 15,
                playerY + 10,
                20,
                20
        );

        // Bullets
        g.setColor(Color.YELLOW);

        for (Rectangle bullet : bullets) {

            g.fillRect(
                    bullet.x,
                    bullet.y,
                    bullet.width,
                    bullet.height
            );
        }

        // Enemies
        g.setColor(Color.RED);

        for (Rectangle enemy : enemies) {

            g.fillOval(
                    enemy.x,
                    enemy.y,
                    enemy.width,
                    enemy.height
            );

            g.setColor(Color.WHITE);

            g.fillOval(
                    enemy.x + 8,
                    enemy.y + 8,
                    6,
                    6
            );

            g.fillOval(
                    enemy.x + 21,
                    enemy.y + 8,
                    6,
                    6
            );

            g.setColor(Color.RED);
        }

        // HUD
        g.setColor(Color.WHITE);

        g.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        g.drawString(
                "Score: " + score,
                15,
                25
        );

        g.drawString(
                "Lives: " + lives,
                15,
                50
        );

        g.drawString(
                "← → Move   SPACE Shoot",
                350,
                25
        );

        // Game over screen
        if (gameOver) {

            g.setColor(
                    new Color(0, 0, 0, 180)
            );

            g.fillRect(
                    0,
                    0,
                    WIDTH,
                    HEIGHT
            );

            g.setColor(Color.RED);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            40
                    )
            );

            g.drawString(
                    "GAME OVER",
                    195,
                    220
            );

            g.setColor(Color.WHITE);

            g.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            20
                    )
            );

            g.drawString(
                    "Final Score: " + score,
                    230,
                    260
            );

            g.drawString(
                    "Press R to Restart",
                    215,
                    300
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (!gameOver) {

            if (key == KeyEvent.VK_LEFT) {

                playerX -= 15;

                if (playerX < 0) {
                    playerX = 0;
                }
            }

            if (key == KeyEvent.VK_RIGHT) {

                playerX += 15;

                if (playerX > WIDTH - 50) {
                    playerX = WIDTH - 50;
                }
            }

            if (key == KeyEvent.VK_SPACE) {

                shoot();
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
                    new JFrame("Space Shooter");

            SpaceShooter game =
                    new SpaceShooter();

            frame.add(game);

            frame.pack();

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