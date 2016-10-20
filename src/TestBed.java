import javax.swing.*;
import java.awt.*;

/**
 * Created by Dale on 1/10/2016.
 * testbed
 */
public class TestBed {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HEY BRUH");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel("BRUH");
        JLabel label2 = new JLabel("BRUH2");
        frame.add(label);
        frame.add(label2);
        JTextField field = new JTextField(25);
        frame.add(field);
        field.setEditable(false);
        JButton button = new JButton("Hiya");
        frame.add(button);
    }
}
