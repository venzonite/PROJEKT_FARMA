import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

public class Animal {
    public BigDecimal buyCost; //koszt zakupu (Musi być większa niż sellCost)
    public BigDecimal sellCost; //koszt sprzedaży

    public double weightIncreasingTempo; //tempo przybierania na wadze na tydzień
    public int growthTimeToMaturity; //czas wzrostu do dojrzałości w tygodniach (po tym okresie przestaje rosnąć, ale może się rozmnażać)
    public int foodNeededPerWeek; //ilość jedzenia jaką musisz dostarczyć na tydzień
    public List<Type> acceptableFoodTypes; //rodzaje jedzenia jakie to zwierze akceptuje, pożywienie dla niektórych zwierząt możesz wyhodować samodzielnie. (Sprawdzić czy List<Type> działa dobrze)
    public int chanceToMultiply; //szansę na rozmnożenie, jeżeli posiadasz więcej niż jedno (w procentach)

}
