/**
 * Created by Dale on 23/09/2016.
 */
public class PlayCard extends Card {
    private String chemistry,classification,cleavage, crustalAbundance,crystalSystem,economicValue,occurrence,specificGravity,hardness;
    private String[] economicValueValues = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"},
            crustalAbundanceValues = {"ultratrace", "trace", "low", "moderate", "high", "very high"},
            cleavageValues = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good",
                    "3 good", "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect",
                    "4 perfect", "6 perfect"};

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
                specificGravity + ") \nCleavage: (" + cleavage + ") Crustal Abundance: (" +
                crustalAbundance + ") Economic Value: (" + economicValue + ")");
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

    public Boolean compareCards(PlayCard lastCard, Category currentCategory) {
        int lastint=0, thisint=0;
        double lastDouble,thisDouble;
        switch (currentCategory) {
            case HARDNESS:
                lastDouble = lastCard.getHigherValue(2);
                thisDouble = getHigherValue(2);
                return lastDouble < thisDouble;
            case SPECIFICGRAVITY:
                lastDouble = lastCard.getHigherValue(1);
                thisDouble = getHigherValue(1);
                return lastDouble < thisDouble;
            case CLEAVAGE:
                for (int x=0; x < cleavageValues.length; x++) {
                    if (cleavageValues[x].equals(lastCard.getCleavage())) {
                        lastint = x;
                    }
                    if (cleavageValues[x].equals(getCleavage())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case CRUSTALABUNDANCE:
                for (int x=0; x < crustalAbundanceValues.length; x++) {
                    if (crustalAbundanceValues[x].equals(lastCard.getCrustalAbundance())) {
                        lastint = x;
                    }
                    if (crustalAbundanceValues[x].equals(getCrustalAbundance())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            case ECONOMICVALUE:
                for (int x=0; x < economicValueValues.length; x++) {
                    if (economicValueValues[x].equals(lastCard.getEconomicValue())) {
                        lastint = x;
                    }
                    if (economicValueValues[x].equals(getEconomicValue())) {
                        thisint = x;
                    }
                }
                return lastint < thisint;
            default: return false;
        }
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

    public String getCategoryValue(Category category) {
        switch (category) {
            case HARDNESS: return hardness;
            case CLEAVAGE: return cleavage;
            case CRUSTALABUNDANCE: return crustalAbundance;
            case SPECIFICGRAVITY: return specificGravity;
            case ECONOMICVALUE: return economicValue;
            default: return null;
        }
    }
}
