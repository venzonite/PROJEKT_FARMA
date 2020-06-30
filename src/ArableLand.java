import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class ArableLand {
    public double size;
    public BigDecimal buyCost;
    public BigDecimal sellCost;
    public String name;
    public ArrayList<Plant> plants; //Rośliny na polu
    public ArrayList<Seed> seeds; //Nasiona zasiane w polu

    public ArableLand(double size, BigDecimal buyCost, BigDecimal sellCost)
    {
        this.plants = new ArrayList<Plant>();
        this.seeds = new ArrayList<Seed>();
        this.size = size;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
    }

    public void checkIfSeedsGerminated()
    {
        Integer seedsCount = seeds.size();

        Iterator<Seed> i = seeds.iterator();
        while (i.hasNext()) {
            Seed s = i.next();

            if(s.checkIfSeedGerminated()) {

                Plant x = new Plant(s.plantName, s.costOfPestProtection, s.cropYield, s.lengthOfPlantingToHarvest, s.weeksWithPossibilityToPlant, s.harvestCost, s.purchasePricePerKilo);

                this.plants.add(x);
                i.remove();

            }

            //i.remove();
        }


        /*for(int i=0;i<seedsCount;i++)
        {
            if(seeds.get(i).checkIfSeedGerminated())
            {
                Seed s = seeds.get(i);

                Plant x = new Plant(s.plantName, s.costOfPestProtection, s.cropYield, s.lengthOfPlantingToHarvest, s.weeksWithPossibilityToPlant, s.harvestCost, s.purchasePricePerKilo);

                this.plants.add(x);
                this.seeds.remove(i);
            }
        }*/
    }
}
