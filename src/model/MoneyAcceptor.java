package model;

public interface MoneyAcceptor {
    int getAmount();
    void addAmount(int amount);
    void subtractAmount(int amount);
}