import java.math.BigDecimal;
import java.util.ArrayList;

public class Seed {
    public BigDecimal costOfPreparingLandAndPlanting; //koszt przygotowania ziemi i sadzenia (w przeliczeniu na hektar)
    public BigDecimal costOfPestProtection; //koszt ochrony przed szkodnikami (w przeliczeniu na hektar)
    public BigDecimal cropYield; //wydajność upraw w tonach z hektara (ile możesz zebrać gotowego towaru)
    public int lengthOfPlantingToHarvest; //długość okresu od posadzenia do zbiorów w tygodniach
    public ArrayList<Integer> weeksWithPossibilityToPlant; //informację o tym w których tygodniach roku można siać/sadzić
    public BigDecimal harvestCost; //koszt zbioru (w przeliczeniu na hektar)
    public BigDecimal purchasePricePerKilo; //cena skupu kilograma

    public BigDecimal seedCost; //Koszt nasion
    public int plantingWeek; //Tydzień zasadzenia nasion.
    public String seedName; //Nazwa nasiona
    public String plantName; //Nazwa rośliny

    public Seed(String seedName, String plantName, BigDecimal seedCost, BigDecimal costOfPreparingLandAndPlanting, BigDecimal costOfPestProtection, BigDecimal cropYield, int lengthOfPlantingToHarvest, ArrayList<Integer> weeksWithPossibilityToPlant, BigDecimal harvestCost, BigDecimal purchasePricePerKilo)
    {
        this.seedName = seedName;
        this.plantName = plantName;
        this.seedCost = seedCost;
        this.costOfPreparingLandAndPlanting = costOfPreparingLandAndPlanting;
        this.costOfPestProtection = costOfPestProtection;
        this.cropYield = cropYield;
        this.lengthOfPlantingToHarvest = lengthOfPlantingToHarvest;
        this.weeksWithPossibilityToPlant = weeksWithPossibilityToPlant;
        this.harvestCost = harvestCost;
        this.purchasePricePerKilo = purchasePricePerKilo;
    }

    //Sprawdza, czy nasiono wykiełkowało
    public Boolean checkIfSeedGerminated()
    {
        if(Main.weekCounter-this.plantingWeek == this.lengthOfPlantingToHarvest) //Jeżeli nasiona wykiełkowały
        {
            return true;
        }

        return false;
    }
}
