package net.bddtrader.portfolios;

public class Position {
    private final String securityCode;
    private final Long amount;
    private final Long totalPurchasePriceInCents;
    private final Long marketValueInCents;


    public Position(String securityCode, Long amount, Long totalPurchasePriceInCents, Long marketValueInCents) {
        this.securityCode = securityCode;
        this.amount = amount;
        this.totalPurchasePriceInCents = totalPurchasePriceInCents;
        this.marketValueInCents = marketValueInCents;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getMarketValueInCents() {
        return marketValueInCents;
    }

    public double getMarketValueInDollars() {
        return marketValueInCents / 100;
    }

    public Long getTotalPurchasePriceInCents() {
        return totalPurchasePriceInCents;

    }
    public double getTotalPurchasePriceInDollars() {
        return totalPurchasePriceInCents / 100;
    }

    public Long getTotalValueInCents() {
        return amount * marketValueInCents;
    }


    public double getTotalValueInDollars() {
        return getTotalValueInCents() / 100;
    }

    public static Position fromTrade(Trade trade) {
        return new Position(trade.getSecurityCode(), trade.getAmount(), trade.getTotalInCents(), trade.getPriceInCents());
    }

    public Position apply(Trade newTrade) {
        return new Position(securityCode,
                amount + newTrade.getAmount() * newTrade.getType().multiplier(),
                totalPurchasePriceInCents + newTrade.getTotalInCents() * newTrade.getType().multiplier(),
                newTrade.getPriceInCents()
        );
    }

    public Position withMarketPriceOf(Double marketPrice) {
        return new Position(securityCode, amount, totalPurchasePriceInCents, (long) (marketPrice * 100));
    }

    public Double getProfit() {
        return ((double) (getTotalValueInCents() - getTotalPurchasePriceInCents())) / 100;
    }
}
