package alerts;

public class EmailData {

    private int sellValue;
    private int buyValue;
    private String abbreviation;
    private String recipient;
    private String increase;

    public EmailData() {
    }

    public EmailData(int sellValue, int buyValue, String abbreviation, String recipient, String increase) {
        this.sellValue = sellValue;
        this.buyValue = buyValue;
        this.abbreviation = abbreviation;
        this.recipient = recipient;
        this.increase = increase;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public float getSellValue() {
        return sellValue;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }

    public float getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(int buyValue) {
        this.buyValue = buyValue;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String toString() {
        return "EmailData{" +
                "sellValue=" + sellValue +
                ", buyValue=" + buyValue +
                ", abbreviation='" + abbreviation + '\'' +
                ", recipient='" + recipient + '\'' +
                ", increase='" + increase + '\'' +
                '}';
    }
}
