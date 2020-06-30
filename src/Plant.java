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

    public String name; //Nazwa rośliny

    public Plant(String name, BigDecimal costOfPestProtection, BigDecimal cropYield, int lengthOfPlantingToHarvest, ArrayList<Integer> weeksWithPossibilityToPlant, BigDecimal harvestCost, BigDecimal purchasePricePerKilo)
    {
        this.name = name;
        this.costOfPreparingLandAndPlanting = costOfPreparingLandAndPlanting;
        this.costOfPestProtection = costOfPestProtection;
        this.cropYield = cropYield;
        this.lengthOfPlantingToHarvest = lengthOfPlantingToHarvest;
        this.weeksWithPossibilityToPlant = weeksWithPossibilityToPlant;
        this.harvestCost = harvestCost;
        this.purchasePricePerKilo = purchasePricePerKilo;
    }
}
