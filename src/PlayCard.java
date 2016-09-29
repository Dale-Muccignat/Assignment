/**
 * Created by Dale on 23/09/2016.
 */
public class PlayCard extends Card {
    private String chemistry,classification,cleavage, crustalAbundance,crystalSystem,economicValue,hardness,occurrence,specificGravity;

    public PlayCard(int index, String fileName, String imageName, String title, String chemistry, String classification, String cleavage, String crustalAbundance, String crystalSystem, String economicValue, String hardness, String occurrence, String specificGravity) {
        super(index, fileName, imageName, title);
        this.chemistry = chemistry;
        this.classification = classification;
        this.cleavage = cleavage;
        this.crustalAbundance = crustalAbundance;
        this.crystalSystem = crystalSystem;
        this.economicValue = economicValue;
        this.hardness = hardness;
        this.occurrence = occurrence;
        this.specificGravity = specificGravity;
    }

    @Override
    public String display(int cardHandNo) {
        return ("("  + cardHandNo + ") PLAY: Title: " + title + " Chemistry: " + chemistry + " Classification: " +
                classification + " Cleavage: " + cleavage + " Crystal Abundance: " +
                crustalAbundance + "\nCrystal System: " + crystalSystem + " Economic Value: " + economicValue +
                " Hardness: " + hardness + " Occurrence: " + occurrence + "Specific Gravity: " +
                specificGravity);
    }
}
