import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickBreaker extends JPanel implements ActionListener, KeyListener {

    private int ballX = 300;
    private int ballY = 300;

    private int ballDX = 4;
    private int ballDY = -4;

    private int paddleX = 250;

    private final int paddleY = 360;
    private final int paddleWidth = 100;
    private final int paddleHeight = 10;

    private final boolean[][] bricks = new boolean[5][8];

    private int score = 0;

    private boolean gameOver = false;

    private final Timer timer;

    public BrickBreaker() {

        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

        resetBricks();

        timer = new Timer(15, this);
        timer.start();
    }

    private void resetBricks() {

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 8; col++) {

                bricks[row][col] = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 15, 15);

        // Paddle
        g.setColor(Color.BLUE);
        g.fillRect(
                paddleX,
                paddleY,
                paddleWidth,
                paddleHeight
        );

        // Bricks
        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 8; col++) {

                if (bricks[row][col]) {

                    g.setColor(Color.RED);

                    g.fillRect(
                            col * 75 + 5,
                            row * 25 + 30,
                            70,
                            20
                    );
                }
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);

        if (gameOver) {

            g.setFont(
                    new Font("Arial", Font.BOLD, 30)
            );

            g.drawString(
                    "GAME OVER",
                    215,
                    200
            );

            g.setFont(
                    new Font("Arial", Font.PLAIN, 18)
            );

            g.drawString(
                    "Press R to restart",
                    220,
                    230
            );
        }
    }

    private void updateGame() {

        if (gameOver) {
            return;
        }

        ballX += ballDX;
        ballY += ballDY;

        if (ballX <= 0 || ballX >= 585) {
            ballDX = -ballDX;
        }

        if (ballY <= 0) {
            ballDY = -ballDY;
        }

        Rectangle ball =
                new Rectangle(ballX, ballY, 15, 15);

        Rectangle paddle =
                new Rectangle(
                        paddleX,
                        paddleY,
                        paddleWidth,
                        paddleHeight
                );

        if (ball.intersects(paddle)) {

            ballDY = -Math.abs(ballDY);
        }

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 8; col++) {

                if (bricks[row][col]) {

                    Rectangle brick =
                            new Rectangle(
                                    col * 75 + 5,
                                    row * 25 + 30,
                                    70,
                                    20
                            );

                    if (ball.intersects(brick)) {

                        bricks[row][col] = false;

                        ballDY = -ballDY;

                        score++;

                        break;
                    }
                }
            }
        }

        if (ballY > 400) {

            gameOver = true;
            timer.stop();
        }

        repaint();
    }

    private void restart() {

        ballX = 300;
        ballY = 300;

        ballDX = 4;
        ballDY = -4;

        paddleX = 250;

        score = 0;

        gameOver = false;

        resetBricks();

        timer.start();

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            paddleX -= 20;

            if (paddleX < 0) {
                paddleX = 0;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            paddleX += 20;

            if (paddleX > 500) {
                paddleX = 500;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R &&
            gameOver) {

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

        JFrame frame =
                new JFrame("Brick Breaker");

        BrickBreaker game =
                new BrickBreaker();

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