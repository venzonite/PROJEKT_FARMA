import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Player {
    public BigDecimal money;
    public ArrayList<Farm> farms;
    public int eggsCount;

    public Player()
    {
        farms = new ArrayList<Farm>();
        eggsCount = 0;
    }
}
