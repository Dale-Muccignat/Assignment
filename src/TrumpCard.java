/**
 * Created by Dale on 23/09/2016.
 */
public class TrumpCard extends Card{
    private String subtitle;

    public TrumpCard(int index, String fileName, String imageName, String title, String subtitle) {
        super(index, fileName, imageName, title);
        this.subtitle = subtitle.trim();
    }

    @Override
    public String display(int cardHandNo) {
        return ("(" + cardHandNo + ") TRUMP: Titile: " + title + " Subtitle: " + subtitle);
    }
    public Category getCategory() {
        switch (this.title) {
            case "The Miner": return Category.ECONOMICVALUE;
            case "The Petrologist": return Category.CRUSTALABUNDANCE;
            case "The Gemmologist": return Category.HARDNESS;
            case "The Mineralogist": return Category.CLEAVAGE;
            case "The Geophysicist": return Category.SPECIFICGRAVITY;
            case "The Geologist": return Category.GEOLOGIST;
        }
        return null;
    }
}
