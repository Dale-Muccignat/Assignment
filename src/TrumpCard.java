/**
 * Created by Dale on 23/09/2016.
 */
public class TrumpCard extends Card{
    private String subtitle;

    public TrumpCard(int index, String fileName, String imageName, String title, String subtitle) {
        //todo overloaded method for geologist
        super(index, fileName, imageName, title);
        this.subtitle = subtitle.trim();
    }

    @Override
    public String display(int cardHandNo) {
        return ("(" + cardHandNo + ") TRUMP: Titile: " + title + " Subtitle: " + subtitle);
    }
    public Category getCategory() {
        switch (this.title) {
            case "The Miner": return Category.ECON;
            case "The Petrologist": return Category.CRUS;
            case "The Gemmologist": return Category.HARD;
            case "The Mineralogist": return Category.CLEA;
            case "The Geophysicist": return Category.SPEC;
            case "The Geologist": return Category.GEOL;
        }
        return null;
    }
}
