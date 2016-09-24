/**
 * Created by mirage_neos on 22/09/2016.
 * Object class for each class
 */
public abstract class Card {
    String fileName,imageName,title;
    int index;

    public Card(int index, String fileName, String imageName, String title) {
        this.fileName = fileName;
        this.imageName = imageName;
        this.title = title;
        this.index = index;
    }
    public abstract String display(int cardHandNo);

}
