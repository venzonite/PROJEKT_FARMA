import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Chicken extends Animal
{
    public Chicken(BigDecimal buyCost, BigDecimal sellCost, double weightIncreasingTempo, int growthTimeToMaturity, int foodNeededPerWeek, ArrayList<Integer> acceptableFoodTypes, int chanceToMultiply, double weight, double maxWeight, Boolean isYoung, int birthWeek)
    {
        super(buyCost, sellCost, weightIncreasingTempo, growthTimeToMaturity, foodNeededPerWeek, acceptableFoodTypes, chanceToMultiply, weight, maxWeight, isYoung, birthWeek);
    }
}
