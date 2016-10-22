import javax.swing.*;
import java.awt.*;

/**
 * Created by mirage_neos on 22/09/2016.
 * Object class for each class
 */
abstract class Card {
    //these are not used yet but will be when the GUI is implemented
    private String fileName,imageName,title;
    private ImageIcon image;
    private int index;

    Card(int index, String fileName, String imageName, String title, ImageIcon image) {
        this.fileName = fileName;
        this.imageName = imageName;
        this.title = title;
        this.index = index;
        this.image = image;
    }
    public abstract String display(int cardHandNo);

    String getTitle() {
        return title;
    }
}
