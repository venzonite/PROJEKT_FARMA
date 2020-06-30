import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public BigDecimal money;
    public ArrayList<Farm> farms;
    public ArrayList<ArableLand> arableLands;
    public ArrayList<Seed> seeds; //Nasiona, które posiada gracz.

    public int eggsCount;

    public Player()
    {
        farms = new ArrayList<Farm>();
        arableLands = new ArrayList<ArableLand>();
        seeds = new ArrayList<Seed>();
        eggsCount = 0;
    }
}
