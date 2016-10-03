/**
 * Created by mirage_neos on 22/09/2016.
 * Object class for each class
 */
abstract class Card {
    //these are not used yet but will be when the GUI is implemented
    private String fileName,imageName,title;
    private int index;

    Card(int index, String fileName, String imageName, String title) {
        this.fileName = fileName;
        this.imageName = imageName;
        this.title = title;
        this.index = index;
    }
    public abstract String display(int cardHandNo);

    String getTitle() {
        return title;
    }
}
