import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Dale on 10/20/2016.
 *
 */

public class GameGui extends JFrame implements MouseListener,ActionListener {
    private JPanel inputPanel = new JPanel();
    private JLabel inputLabel = new JLabel("Please Input:");
    private JTextField inputText = new JTextField("Enter Input Here");
    private JButton confirmInputButton = new JButton("Confirm Input");
    private Boolean confirmInput;


    public GameGui() {
        Container con = getContentPane();
        con.setLayout(new GridLayout(2,2));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(400,400);
        inputPanel.setLayout(new GridLayout(3,1));
        inputPanel.add(inputLabel);
        inputPanel.add(inputText);
        inputPanel.add(confirmInputButton);
        confirmInputButton.addActionListener(this);
        inputText.addMouseListener(this);

        con.add(inputPanel);
        repaint();

    }

    public String askInput(String message) {
        inputLabel.setText(message);
        confirmInput = false;
        while (!confirmInput) {}
        return inputLabel.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmInputButton) {
            confirmInput = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == inputText) {
            inputText.setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public JLabel getInputLabel() {
        return inputLabel;
    }

    public void setInputLabel(JLabel inputLabel) {
        this.inputLabel = inputLabel;
    }

    public JTextField getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText.setText(inputText);
    }
}
