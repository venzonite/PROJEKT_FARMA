import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public BigDecimal money;
    public ArrayList<Farm> farms;
    public ArrayList<ArableLand> arableLands;
    public int eggsCount;

    public Player()
    {
        farms = new ArrayList<Farm>();
        arableLands = new ArrayList<ArableLand>();
        eggsCount = 0;
    }
}
