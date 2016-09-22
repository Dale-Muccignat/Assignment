/**
 * Created by mirage_neos on 22/09/2016.
 * Object class for each class
 */
public class Card {
    private String cardType,chemistry,classification,cleavage,crystalAbundance,crystalSystem,economicValue,fileName,hardness,imageName,occurrence,specificGravity,title;

    public Card(String cardType, String chemistry, String classification, String cleavage, String crystalAbundance, String crystalSystem, String economicValue, String fileName, String hardness, String imageName, String occurrence, String specificGravity, String title) {
        this.cardType = cardType;
        this.chemistry = chemistry;
        this.classification = classification;
        this.cleavage = cleavage;
        this.crystalAbundance = crystalAbundance;
        this.crystalSystem = crystalSystem;
        this.economicValue = economicValue;
        this.fileName = fileName;
        this.hardness = hardness;
        this.imageName = imageName;
        this.occurrence = occurrence;
        this.specificGravity = specificGravity;
        this.title = title;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getCleavage() {
        return cleavage;
    }

    public void setCleavage(String cleavage) {
        this.cleavage = cleavage;
    }

    public String getCrystalAbundance() {
        return crystalAbundance;
    }

    public void setCrystalAbundance(String crystalAbundance) {
        this.crystalAbundance = crystalAbundance;
    }

    public String getCrystalSystem() {
        return crystalSystem;
    }

    public void setCrystalSystem(String crystalSystem) {
        this.crystalSystem = crystalSystem;
    }

    public String getEconomicValue() {
        return economicValue;
    }

    public void setEconomicValue(String economicValue) {
        this.economicValue = economicValue;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHardness() {
        return hardness;
    }

    public void setHardness(String hardness) {
        this.hardness = hardness;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public String getSpecificGravity() {
        return specificGravity;
    }

    public void setSpecificGravity(String specificGravity) {
        this.specificGravity = specificGravity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void display() {
        System.out.println(cardType+chemistry+classification+cleavage+crystalAbundance+crystalSystem+economicValue+fileName+hardness+imageName+occurrence+specificGravity+title);
    }
}
