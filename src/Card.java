/**
 * Created by mirage_neos on 22/09/2016.
 * Object class for each class
 */
public class Card {
    private String name, cleavage, abundance, value;
    private int hardness;
    private double specificGravity;

    public Card(String name, String cleavage, String abundance, String value, int hardness, double specificGravity) {
        this.name = name;
        this.cleavage = cleavage;
        this.abundance = abundance;
        this.value = value;
        this.hardness = hardness;
        this.specificGravity = specificGravity;
    }

    public String getName() {
        return name;
    }

    public String getCleavage() {
        return cleavage;
    }

    public String getAbundance() {
        return abundance;
    }

    public String getValue() {
        return value;
    }

    public int getHardness() {
        return hardness;
    }

    public double getSpecificGravity() {
        return specificGravity;
    }
}
