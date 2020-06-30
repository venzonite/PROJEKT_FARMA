import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Plant {
    public BigDecimal costOfPreparingLandAndPlanting; //koszt przygotowania ziemi i sadzenia (w przeliczeniu na hektar)
    public BigDecimal costOfPestProtection; //koszt ochrony przed szkodnikami (w przeliczeniu na hektar)
    public BigDecimal cropYield; //wydajność upraw w tonach z hektara (ile możesz zebrać gotowego towaru)
    public int lengthOfPlantingToHarvest; //długość okresu od posadzenia do zbiorów w tygodniach
    public ArrayList<Integer> weeksWithPossibilityToPlant; //informację o tym w których tygodniach roku można siać/sadzić
    public BigDecimal harvestCost; //koszt zbioru (w przeliczeniu na hektar)
    public BigDecimal purchasePrice; //cena sprzedazy rosliny

    public String name; //Nazwa rośliny

    public Plant(String name, BigDecimal costOfPestProtection, BigDecimal cropYield, int lengthOfPlantingToHarvest, ArrayList<Integer> weeksWithPossibilityToPlant, BigDecimal harvestCost, BigDecimal purchasePrice)
    {
        this.name = name;
        this.costOfPreparingLandAndPlanting = costOfPreparingLandAndPlanting;
        this.costOfPestProtection = costOfPestProtection;
        this.cropYield = cropYield;
        this.lengthOfPlantingToHarvest = lengthOfPlantingToHarvest;
        this.weeksWithPossibilityToPlant = weeksWithPossibilityToPlant;
        this.harvestCost = harvestCost;
        this.purchasePrice = purchasePrice;

        //Normalizacja decimali:
        this.purchasePrice = this.purchasePrice.setScale(2, RoundingMode.HALF_EVEN);
        this.harvestCost = this.harvestCost.setScale(2, RoundingMode.HALF_EVEN);
        this.costOfPreparingLandAndPlanting = this.costOfPreparingLandAndPlanting.setScale(2, RoundingMode.HALF_EVEN);
        this.costOfPestProtection = this.costOfPestProtection.setScale(2, RoundingMode.HALF_EVEN);
    }
}
