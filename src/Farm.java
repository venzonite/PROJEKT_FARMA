import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Farm
{
    public double size;
    public ArrayList<Building> buildings;
    public ArrayList<Dog> dogs;
    public ArrayList<Cat> cats;
    public ArrayList<Chicken> chickens;
    public ArrayList<Plant> plants;
    public BigDecimal buyCost;
    public String name;

    public Farm()
    {
        this.buildings = new ArrayList<Building>();
        this.plants = new ArrayList<Plant>();

        this.dogs = new ArrayList<Dog>();
        this.cats = new ArrayList<Cat>();
        this.chickens = new ArrayList<Chicken>();
    }

    public void getEggsFromChickens()
    {
        //Kury znoszą jajka (przyjąłem, że 1 kura znosi 2 jajka na tydzień)
        int eggsEarnedFromFarm = 0;
        for(Chicken chicken : this.chickens)
        {
            Main.player.eggsCount += 2;
            eggsEarnedFromFarm += 2;
        }

        System.out.println("(info) Otrzymałeś " + eggsEarnedFromFarm + " jajek z farmy: " + this.name);
    }

    public void doAnimalsMultiply()
    {
        //Jeżeli farma posiada conajmniej 2 psy, to jest mała szansa na rozmnożenie się.
        if(this.dogs.size() >= 2)
        {
            var d = Math.random(); // 3% procent szans rozmnożenie się
            if (d < 0.03) {

                ArrayList<Integer> acceptableFoodTypes = new ArrayList<Integer>();
                acceptableFoodTypes.add(FoodTypes.MEAT.getValue());
                acceptableFoodTypes.add(FoodTypes.SANDWICH.getValue());

                Dog youngDog = new Dog(new BigDecimal(300), new BigDecimal(150), 0.5, 12, 14, acceptableFoodTypes, 3, 1.0, 20.0, true, Main.weekCounter);

                this.dogs.add(youngDog);
                System.out.println("(info) Twoje psy rozmnożyły się! Zyskujesz jednego małego pieska.");
            }
        }

        //Jeżeli farma posiada conajmniej 2 koty, to jest mała szansa na rozmnożenie się.
        if(this.dogs.size() >= 2)
        {
            var d = Math.random(); // 3% procent szans rozmnożenie się
            if (d < 0.03) {

                ArrayList<Integer> acceptableFoodTypes = new ArrayList<Integer>();
                acceptableFoodTypes.add(FoodTypes.MEAT.getValue());
                acceptableFoodTypes.add(FoodTypes.SANDWICH.getValue());

                Cat youngCat = new Cat(new BigDecimal(300), new BigDecimal(150), 0.5, 12, 14, acceptableFoodTypes, 3, 1.0, 20.0, true, Main.weekCounter);

                this.cats.add(youngCat);
                System.out.println("(info) Twoje koty rozmnożyły się! Zyskujesz jednego małego kotka.");
            }
        }
    }

    public void doAnimalsGrowUp()
    {
        for (Dog dog : this.dogs) {

            if(Main.weekCounter - dog.birthWeek == dog.growthTimeToMaturity)
            {
                dog.isYoung = false;
            }
        }

        for (Cat cat : this.cats) {

            if(Main.weekCounter - cat.birthWeek == cat.growthTimeToMaturity)
            {
                cat.isYoung = false;
            }
        }

        for (Chicken chicken : this.chickens) {

            if(Main.weekCounter - chicken.birthWeek == chicken.growthTimeToMaturity)
            {
                chicken.isYoung = false;
            }
        }
    }

    public void doAnimalsWeightChange()
    {
        //Zwierzęta chudną, jeśli gracz nie ma pieniędzy.
        if(Utils.lessThanOrEquals(Main.player.money, new BigDecimal(0.0))) {
            for (Dog dog : this.dogs) {
                dog.weight -= dog.weightIncreasingTempo;

                if(dog.weight <= 0.0) {
                    dog.weight = 0.0;
                }
            }

            for (Cat cat : this.cats) {
                cat.weight -= cat.weightIncreasingTempo;

                if(cat.weight <= 0.0) {
                    cat.weight = 0.0;
                }
            }

            for (Chicken chicken : this.chickens) {
                chicken.weight -= chicken.weightIncreasingTempo;

                if(chicken.weight <= 0.0) {
                    chicken.weight = 0.0;
                }
            }

        }
        else
        {
            for (Dog dog : this.dogs) {
                dog.weight += dog.weightIncreasingTempo;

                if(dog.weight >= dog.maxWeight) {
                    dog.weight = dog.maxWeight;
                }
            }

            for (Cat cat : this.cats) {
                cat.weight += cat.weightIncreasingTempo;

                if(cat.weight >= cat.maxWeight) {
                    cat.weight = cat.maxWeight;
                }
            }

            for (Chicken chicken : this.chickens) {
                chicken.weight += chicken.weightIncreasingTempo;

                if(chicken.weight >= chicken.maxWeight) {
                    chicken.weight = chicken.maxWeight;
                }
            }
        }
    }
}
