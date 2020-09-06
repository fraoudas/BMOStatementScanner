package models;

public class Transaction {

    private Character type;
    private String desc;
    private Float amount;
    private String date;

    public Transaction(char type, String desc, float amount, String date) {
        this.type = type;
        this.desc = desc;
        this.amount = amount;
        this.date = date;
    }

    public char getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public float getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
    public String getMonth() { return date.substring(5,7);}

    @Override
    public String toString() {
        return date +" "+ desc +" "+ amount +" "+ type;
    }


}
