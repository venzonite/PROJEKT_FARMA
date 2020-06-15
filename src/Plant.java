import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Plant {
    public BigDecimal costOfPreparingLandAndPlanting; //koszt przygotowania ziemi i sadzenia (w przeliczeniu na hektar)
    public BigDecimal costOfPestProtection; //koszt ochrony przed szkodnikami (w przeliczeniu na hektar)
    public BigDecimal cropYield; //wydajność upraw w tonach z hektara (ile możesz zebrać gotowego towaru)
    public int lengthOfPlantingToHarvest; //długość okresu od posadzenia do zbiorów w tygodniach
    public ArrayList<Integer> weeksWithPossibilityToPlant; //informację o tym w których tygodniach roku można siać/sadzić
    public BigDecimal harvestCost; //koszt zbioru (w przeliczeniu na hektar)
    public BigDecimal purchasePricePerKilo; //cena skupu kilograma

    public boolean isSeed; //Jeżeli true, to obiekt jest nasionem.
    public BigDecimal seedCost; //Koszt nasion
    public int plantingWeek; //Tydzień zasadzenia nasion.

    public Plant(boolean isSeed, BigDecimal seedCost, BigDecimal costOfPreparingLandAndPlanting, BigDecimal costOfPestProtection, BigDecimal cropYield, int lengthOfPlantingToHarvest, ArrayList<Integer> weeksWithPossibilityToPlant, BigDecimal harvestCost, BigDecimal purchasePricePerKilo)
    {
        this.isSeed = isSeed;
        this.seedCost = seedCost;
        this.costOfPreparingLandAndPlanting = costOfPreparingLandAndPlanting;
        this.costOfPestProtection = costOfPestProtection;
        this.cropYield = cropYield;
        this.lengthOfPlantingToHarvest = lengthOfPlantingToHarvest;
        this.weeksWithPossibilityToPlant = weeksWithPossibilityToPlant;
        this.harvestCost = harvestCost;
        this.purchasePricePerKilo = purchasePricePerKilo;
    }

    //Sprawdź, czy nasiona wykiełkowały.
    public Boolean checkIfSeedsGerminated()
    {
        if(Main.weekCounter-this.plantingWeek == this.lengthOfPlantingToHarvest) //Jeżeli nasiona wykiełkowały
        {
            return true;
        }

        return false;
    }
}
