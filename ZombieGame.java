import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ZombieGame extends JPanel implements MouseListener {
    Random random = new Random();

    ArrayList<Point> zombies = new ArrayList<>();
    int score = 0;

    ZombieGame() {
        addMouseListener(this);

        for (int i=0;i<8;i++)
            zombies.add(new Point(
                random.nextInt(550),
                random.nextInt(350)));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawString("ZOMBIE SHOOTER", 20, 20);
        g.drawString("Score: " + score, 20, 40);

        g.setColor(Color.GREEN);

        for(Point p:zombies) {
            g.fillOval(p.x, p.y, 40, 40);

            g.setColor(Color.BLACK);
            g.fillOval(p.x+8, p.y+10, 6, 6);
            g.fillOval(p.x+25, p.y+10, 6, 6);

            g.setColor(Color.GREEN);
        }
    }

    public void mousePressed(MouseEvent e) {
        for(int i=zombies.size()-1;i>=0;i--) {
            Point p=zombies.get(i);

            if(e.getX()>=p.x && e.getX()<=p.x+40 &&
               e.getY()>=p.y && e.getY()<=p.y+40) {

                zombies.remove(i);
                score++;

                zombies.add(new Point(
                    random.nextInt(550),
                    random.nextInt(350)));

                break;
            }
        }

        repaint();
    }

    public void mouseReleased(MouseEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}

    public static void main(String[] args) {
        JFrame f=new JFrame("Zombie Shooter");
        f.add(new ZombieGame());
        f.setSize(600,400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}