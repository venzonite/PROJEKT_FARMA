import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Animal {
    public BigDecimal buyCost; //koszt zakupu (Musi być większa niż sellCost)
    public BigDecimal sellCost; //koszt sprzedaży

    public double weightIncreasingTempo; //tempo przybierania na wadze na tydzień
    public int growthTimeToMaturity; //czas wzrostu do dojrzałości w tygodniach (po tym okresie przestaje rosnąć, ale może się rozmnażać)
    public int foodNeededPerWeek; //ilość jedzenia jaką musisz dostarczyć na tydzień
    public ArrayList<Integer> acceptableFoodTypes; //rodzaje jedzenia jakie to zwierze akceptuje, pożywienie dla niektórych zwierząt możesz wyhodować samodzielnie. (Sprawdzić czy List<Type> działa dobrze)
    public int chanceToMultiply; //szansę na rozmnożenie, jeżeli posiadasz więcej niż jedno (w procentach)
    public double weight; //Waga zwierzęcia
    public double maxWeight; //Maksymalna waga zwierzęcia

    public Boolean isYoung;
    public int birthWeek; //Tydzień urodzenia zwierzęcia
    public String name = "";

    public Animal(String name, BigDecimal buyCost, BigDecimal sellCost, double weightIncreasingTempo, int growthTimeToMaturity, int foodNeededPerWeek, ArrayList<Integer> acceptableFoodTypes, int chanceToMultiply, double weight, double maxWeight, Boolean isYoung, int birthWeek)
    {
        this.name = name;
        this.buyCost = buyCost;
        this.sellCost = sellCost;
        this.weightIncreasingTempo = weightIncreasingTempo;
        this.growthTimeToMaturity = growthTimeToMaturity;
        this.foodNeededPerWeek = foodNeededPerWeek;
        this.acceptableFoodTypes = acceptableFoodTypes;
        this.chanceToMultiply = chanceToMultiply;
        this.weight = weight;
        this.maxWeight = maxWeight;
        this.isYoung = isYoung;
        this.birthWeek = birthWeek;
    }

    public String getName()
    {
        return this.isYoung == true ? ("Mł. " + this.name) : this.name;
    }
}
