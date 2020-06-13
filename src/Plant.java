import java.math.BigDecimal;
import java.util.List;

public class Plant {
    public BigDecimal costOfPreparingLandAndPlanting; //koszt przygotowania ziemi i sadzenia (w przeliczeniu na hektar)
    public BigDecimal costOfPestProtection; //koszt ochrony przed szkodnikami (w przeliczeniu na hektar)
    public BigDecimal cropYield; //wydajność upraw w tonach z hektara (ile możesz zebrać gotowego towaru)
    public int lengthOfPlantingToHarvest; //długość okresu od posadzenia do zbiorów w tygodniach
    public List<Integer> weeksWithPossibilityToPlant; //informację o tym w których tygodniach roku można siać/sadzić
    public BigDecimal harvestCost; //koszt zbioru (w przeliczeniu na hektar)
    public BigDecimal purchasePricePerKilo; //cena skupu kilograma
}
