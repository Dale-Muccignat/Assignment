/**
 * Created by Dale on 23/09/2016.
 */
public class PlayCard extends Card {
    private String chemistry,classification,cleavage, crustalAbundance,crystalSystem,economicValue,occurrence,specificGravity,hardness;

    public PlayCard(int index, String fileName, String imageName, String title, String chemistry, String classification, String cleavage, String crustalAbundance, String crystalSystem, String economicValue, String hardness, String occurrence, String specificGravity) {
        super(index, fileName, imageName, title);
        this.chemistry = chemistry.trim();                                      //trim strings
        this.classification = classification.trim();
        this.cleavage = cleavage.trim();
        this.crustalAbundance = crustalAbundance.trim();
        this.crystalSystem = crystalSystem.trim();
        this.economicValue = economicValue.trim();
        this.hardness = hardness.trim();
        this.occurrence = occurrence.trim();
        this.specificGravity = specificGravity.trim();
    }

    @Override
    public String display(int cardHandNo) {
        return ("("  + cardHandNo + ") PLAY: Title: " + title + " Hardness: (" + hardness + ") Specific Gravity: (" +
                specificGravity + ") Cleavage: (" + cleavage + ") Crustal Abundance: (" +
                crustalAbundance + ") Economic Value: (" + economicValue);
//                " Chemistry: " + chemistry + " Classification: " +
//                classification + "Crystal System: " + crystalSystem +
//                " Occurrence: " + occurrence);
    }

    public String getCleavage() {
        return cleavage;
    }

    public String getCrustalAbundance() {
        return crustalAbundance;
    }

    public String getEconomicValue() {
        return economicValue;
    }

    public String getHardness() {
        return hardness;
    }

    public String getSpecificGravity() {
        return specificGravity;
    }

    public double getHigherValue(int ident) {
        String string;
        if (ident == 1) {
            // remove all whitespace from the string
            string = specificGravity.replaceAll("\\s+","");;
        } else {
            string = hardness.replaceAll("\\s+","");;
        }
        // If the string contains a hyphen, take the value after the hyphen, else take the value. Convert to double.
        double higherValue;
        int index = string.indexOf('-');
        if (index == -1) {
            higherValue = Double.parseDouble(string);
        }
        else {
            String stringHigherValue = string.substring(index + 1);
            higherValue = Double.parseDouble(stringHigherValue);
        }
        return higherValue;
    }
}
