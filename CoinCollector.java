import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class CoinCollector extends JPanel
        implements KeyListener {

    private int playerX = 100;
    private int playerY = 100;

    private int coinX;
    private int coinY;

    private int score = 0;

    private final Random random = new Random();

    public CoinCollector() {

        setPreferredSize(
                new Dimension(600, 400)
        );

        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

        moveCoin();
    }

    private void moveCoin() {

        coinX = random.nextInt(560);
        coinY = random.nextInt(340) + 30;
    }

    private void checkCoin() {

        Rectangle player =
                new Rectangle(
                        playerX,
                        playerY,
                        30,
                        30
                );

        Rectangle coin =
                new Rectangle(
                        coinX,
                        coinY,
                        25,
                        25
                );

        if (player.intersects(coin)) {

            score++;

            moveCoin();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Player
        g.setColor(Color.BLUE);

        g.fillRect(
                playerX,
                playerY,
                30,
                30
        );

        // Coin
        g.setColor(Color.YELLOW);

        g.fillOval(
                coinX,
                coinY,
                25,
                25
        );

        // Text
        g.setColor(Color.WHITE);

        g.drawString(
                "Score: " + score,
                15,
                20
        );

        g.drawString(
                "Use Arrow Keys",
                15,
                40
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            playerX -= 10;
        }

        if (key == KeyEvent.VK_RIGHT) {
            playerX += 10;
        }

        if (key == KeyEvent.VK_UP) {
            playerY -= 10;
        }

        if (key == KeyEvent.VK_DOWN) {
            playerY += 10;
        }

        // Boundary
        if (playerX < 0) {
            playerX = 0;
        }

        if (playerX > 570) {
            playerX = 570;
        }

        if (playerY < 0) {
            playerY = 0;
        }

        if (playerY > 370) {
            playerY = 370;
        }

        checkCoin();

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        JFrame frame =
                new JFrame("Coin Collector");

        CoinCollector game =
                new CoinCollector();

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