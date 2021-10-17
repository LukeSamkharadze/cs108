package Bank;

public class Transaction {
    private int from;
    private int to;
    private int amount;

    public Transaction(int from, int to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String toString() {
        return("from:" + from + " to:" + to + " amount:" + amount);
    }

    public int fromWhere() {
        return from;
    }

    public int toWhere() {
        return to;
    }

    public int whatAmount() {
        return amount;
    }
}