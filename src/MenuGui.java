import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dale on 10/20/2016.
 */
public class MenuGui extends JFrame implements ActionListener {
    private JLabel titleLabel = new JLabel("Welcome to SuperTrump!",SwingConstants.CENTER);
    private JButton startButton = new JButton("Start Game");
    private JButton howButton = new JButton("How to play");
    private JButton quitButton = new JButton("How to play");

    public static void main(String[] args) {
        MenuGui menu = new MenuGui();
    }

    public MenuGui() {

        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1));
        add(titleLabel);
        add(startButton);
        add(howButton);
        add(quitButton);
        repaint();
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
        startButton.addActionListener(this);
        howButton.addActionListener(this);
        quitButton.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == startButton) {
            TrumpGame newGame = new TrumpGame();
            newGame.startGame();
        } else if (source == howButton) {
            System.out.println("How do you play?");
        } else if (source == quitButton) {
            dispose();
        }
    }
}
