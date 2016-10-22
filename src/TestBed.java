import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Dale on 1/10/2016.
 * testbed
 */
public class TestBed{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.setVisible(true);
        for (int i =0; i<60;++i) {
            System.out.println("hi");
            frame.add(new JLabel(new ImageIcon("images\\Slide0" + i + ".jpg")));
        }
        frame.pack();
//        frame.invalidate();
//        frame.validate();
//        frame.repaint();
    }
}
