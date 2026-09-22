import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndlessRunner extends JPanel implements KeyListener {
    int playerY = 300;
    int velocity = 0;

    int obstacleX = 600;
    int score = 0;

    boolean gameOver = false;

    Timer timer = new Timer(30, e -> updateGame());

    EndlessRunner() {
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    void updateGame() {
        if (gameOver) return;

        velocity += 1;
        playerY += velocity;

        if (playerY >= 300) {
            playerY = 300;
            velocity = 0;
        }

        obstacleX -= 7;

        if (obstacleX < -30) {
            obstacleX = getWidth();
            score++;
        }

        Rectangle player = new Rectangle(100, playerY, 40, 40);
        Rectangle obstacle = new Rectangle(obstacleX, 320, 30, 50);

        if (player.intersects(obstacle))
            gameOver = true;

        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(0, 340, getWidth(), 5);

        g.setColor(Color.BLUE);
        g.fillRect(100, playerY, 40, 40);

        g.setColor(Color.RED);
        g.fillRect(obstacleX, 320, 30, 50);

        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 20);

        if (gameOver)
            g.drawString("GAME OVER - Press R", 230, 200);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && playerY == 300)
            velocity = -15;

        if (e.getKeyCode() == KeyEvent.VK_R) {
            playerY = 300;
            obstacleX = 600;
            score = 0;
            gameOver = false;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame f = new JFrame("Endless Runner");
        f.add(new EndlessRunner());
        f.setSize(650, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}