package model;

public class CardAcceptor implements MoneyAcceptor {
    private int amount;

    public CardAcceptor() {
        this.amount = 0;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void addAmount(int amount) {
        this.amount += amount;
    }

    @Override
    public void subtractAmount(int amount) {
        this.amount -= amount;
    }
}