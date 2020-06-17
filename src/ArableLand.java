import java.math.BigDecimal;

public class ArableLand {
    public double size;
    public BigDecimal buyCost;
    public BigDecimal sellCost;
    public String name;

    public ArableLand(double size, BigDecimal buyCost, BigDecimal sellCost)
    {
        this.size = size;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }
}
