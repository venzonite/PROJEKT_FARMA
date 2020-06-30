import jdk.jshell.spi.ExecutionControl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static ArrayList<Farm> startingFarms;
    public static ArrayList<ArableLand> startingArableLands;
    public static ArrayList<Seed> availableSeedsToBuy;
    public static ArrayList<Building> availableBuildingsToBuy;
    public static ArrayList<Animal> availableAnimalsToBuy;

    public static Player player;
    public static int weekCounter = 1;
    public static int currentYear = 2020;
    public static int currentWeek = 1;

    public static void main(String[] args) throws IOException, ExecutionControl.NotImplementedException {

        System.out.println("Projekt FARMA - GRA - Mateusz Wejer");
        System.out.println("- Wybierz farmę startową (Wpisz numer od 1 do 10):");

        startingFarms = new ArrayList<Farm>();
        prepareStartingFarms();

        startingArableLands = new ArrayList<ArableLand>();
        prepareStartingArableLands();

        availableSeedsToBuy = new ArrayList<Seed>();
        prepareAvailableSeedsToBuy();

        availableBuildingsToBuy = new ArrayList<Building>();
        prepareAvailableBuildingsToBuy();

        availableAnimalsToBuy = new ArrayList<Animal>();
        prepareAvailableAnimalsToBuy();

        player = new Player();
        player.money = new BigDecimal(200000); //200.000 zł na start.
        player.money = player.money.setScale(2, RoundingMode.HALF_EVEN);

        int counter = 0;
        for(Farm x : startingFarms)
        {
            String buildingString = "";
            for(Building b : x.buildings)
            {
                buildingString += b.name + ",";
            }

            System.out.println(counter + ". Farma numer " + counter + " (+ 1 pole uprawne " + x.size + " HA) (koszt: " + x.buyCost + " zł), posiada budynki: " + buildingString);
            counter++;
        }

        chooseStartingFarm();

        //Główna pętla gry. Każda iteracja odpowiada jednemu tygodniowi w roku.
        for(;;) {
            showAvailableActionsDialog();
        }
    }

    public static void showAvailableActionsDialog() throws IOException, ExecutionControl.NotImplementedException {

        double sumOfHA = 0.0;

        for(ArableLand x : player.arableLands)
        {
            sumOfHA += x.size;
        }

        if(currentWeek == 48) {
            currentWeek = 1;
            currentYear++;
        }

        System.out.println("===================================================================");
        System.out.println("Jest tydzień " + currentWeek + " roku " + currentYear + ", jakie akcje podejmujesz? [Gotówka: " + player.money + " zł. | Suma hektarów ziem: " + sumOfHA + " | Ilość farm: " + player.farms.size() + "]");

        System.out.println("0. Przejdź do następnego tygodnia");
        System.out.println("1. Zakup farmy");
        System.out.println("2. Zakup/sprzedaż ziemi uprawnej");
        System.out.println("3. Zakup nowych budynków");
        System.out.println("4. Zakup zwierząt lub roślin");
        System.out.println("5. Posadzenie roślin (jeżeli posiadasz sadzonki, które można posadzić w tym okresie)");
        System.out.println("6. Zbiory roślin (jeżeli masz gotowe do zebrania plony)");
        System.out.println("7. Sprzedaż roślin lub zwierząt");
        System.out.println("8. Sprawdzenie stanu zapasów");
        System.out.println("9. Przejrzenie informacji o posiadanych zwierzętach");
        System.out.println("10. Przejrzenie informacji o posiadanych sadzonkach i zasadzonych roślinach");
        System.out.println("===================================================================");

        Integer choosenNumber = -1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            choosenNumber = Integer.parseInt(br.readLine());
        }
        catch(Exception e)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do 10.");
            showAvailableActionsDialog();
            return;
        }

        if(choosenNumber < 0 || choosenNumber > 10)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do 10.");
            showAvailableActionsDialog();
        }

        switch(choosenNumber)
        {
            case 0: //0. Przejdź do następnego tygodnia

                System.out.println("(info) Wybrano opcję \"Przejdź do następnego tygodnia\"");

                break;

            case 1: //1. Zakup farmy

                System.out.println("(info) Wybrano opcję \"Zakup farmy\"");

                System.out.println("(Zakup farmy) Wybierz farmę, którą chcesz zakupić.");

                System.out.println("0. Powrót.");

                int counter = 1;
                for(Farm x : startingFarms)
                {
                    String buildingString = "";
                    for(Building b : x.buildings)
                    {
                        buildingString += b.name + ",";
                    }

                    System.out.println(counter + ". " + x.name + " (" + x.size + " HA) (koszt: " + x.buyCost + " zł), posiada budynki: " + buildingString);
                    counter++;
                }

                chooseFarmToBuy();

                showAvailableActionsDialog();
                return;

            case 2: //2. Zakup/sprzedaż ziemi uprawnej

                System.out.println("(info) Wybrano opcję \"Zakup/sprzedaż ziemi uprawnej\"");

                showBuySellArableLandDialog();
                showAvailableActionsDialog();

                return;

            case 3: //3. Zakup nowych budynków

                System.out.println("(info) Wybrano opcję \"Zakup nowych budynków\"");




                System.out.println("0. Powrót.");

                counter = 1;
                for(Building x : availableBuildingsToBuy)
                {
                    System.out.println(counter + ". " + x.name + " (koszt: " + x.cost + " zł)");
                    counter++;
                }

                chooseBuildingToBuy();

                showAvailableActionsDialog();
                return;

            case 4: //4. Zakup zwierząt lub roślin

                System.out.println("(info) Wybrano opcję \"Zakup zwierząt lub roślin\"");

                System.out.println("0. Powrót");
                System.out.println("1. Zakup zwierząt");
                System.out.println("2. Zakup roślin (nasion)");

                Integer writtenNumber = readNumberFromConsole();

                switch(writtenNumber)
                {
                    case 0: break;
                    case 1:

                        System.out.println("0. Powrót");

                        int c = 1;
                        for(Animal x : availableAnimalsToBuy)
                        {
                            System.out.println(c + ". " + x.getName() + " (Koszt: " + x.buyCost + " zł)");
                            c++;
                        }

                        writtenNumber = readNumberFromConsole();

                        if(writtenNumber == 0)
                            break;
                        else
                        {
                            Integer selectedAnimalIndex = writtenNumber-1;

                            if(selectedAnimalIndex > availableAnimalsToBuy.size()-1)
                            {
                                System.out.println("(błąd) Takie zwierzę nie istnieje.");
                                break;
                            }

                            Animal animalWhoWannaBuy = availableAnimalsToBuy.get(selectedAnimalIndex);

                            if(Utils.lessThan(player.money, animalWhoWannaBuy.buyCost)) {
                                System.out.println("(błąd) Nie posiadasz tyle kasy, by kupić to zwierzę.");
                                break;
                            }

                            System.out.println("(Zakup zwierzęcia) Wybierz farmę gdzie ma trafić zwierzę:");

                            System.out.println("0. Powrót");

                            counter = 1;
                            for(Farm x : player.farms)
                            {
                                System.out.println(counter + ". " + x.name);
                                counter++;
                            }

                            writtenNumber = readNumberFromConsole();

                            if(writtenNumber == 0)
                                break;

                            Integer choosenFarmIndex = writtenNumber-1;

                            if(choosenFarmIndex > player.farms.size()-1)
                            {
                                System.out.println("(błąd) Nie posiadasz takiej farmy.");
                                break;
                            }

                            Farm selectedFarm = player.farms.get(choosenFarmIndex);

                            if(animalWhoWannaBuy.getName().contains("ura")) //Jeżeli gracz kupuje kurę, to na farmie musi być kurnik.
                            {
                                Boolean hovelFound = false;
                                for(Building b : selectedFarm.buildings)
                                {
                                    if(b.name.contains("urnik"))
                                    {
                                        hovelFound = true;
                                    }
                                }

                                if(hovelFound == false)
                                {
                                    System.out.println("(błąd) Próbujesz kupić kurę na farmę, gdzie nie ma kurnika. Kup conajmniej 1 kurnik.");
                                    break;
                                }

                                System.out.println("(Zakup kury) Kura trafila na twoja farme.");
                                player.farms.get(choosenFarmIndex).chickens.add((Chicken) animalWhoWannaBuy);
                            }
                            else {
                                if (animalWhoWannaBuy.getName().contains("ies")) //Jeżeli gracz kupuje psa
                                {
                                    player.farms.get(choosenFarmIndex).dogs.add((Dog) animalWhoWannaBuy);
                                } else if (animalWhoWannaBuy.getName().contains("ot")) //Jeżeli gracz kupuje kota
                                {
                                    player.farms.get(choosenFarmIndex).cats.add((Cat) animalWhoWannaBuy);
                                }

                                System.out.println("(info) Zakupiono zwierzę.");

                                player.money = player.money.subtract(animalWhoWannaBuy.buyCost);

                            }
                        }

                        break;

                    case 2:


                        System.out.println("0. Powrót");

                        c = 1;
                        for(Seed x : availableSeedsToBuy)
                        {
                            BigDecimal seedCostx100 = x.seedCost.multiply(new BigDecimal(100));
                            seedCostx100 = seedCostx100.setScale(2, RoundingMode.HALF_EVEN);

                            System.out.println(c + ". " + x.seedName + " (Koszt za 100 sztuk: " + seedCostx100 + " zł)");
                            c++;
                        }

                        writtenNumber = readNumberFromConsole();

                        if(writtenNumber == 0)
                            break;
                        else {
                            Integer selectedSeedIndex = writtenNumber - 1;

                            if (selectedSeedIndex > availableSeedsToBuy.size() - 1) {
                                System.out.println("(błąd) Takie nasiono nie istnieje.");
                                break;
                            }

                            Seed seedWhichWannaBuy = availableSeedsToBuy.get(selectedSeedIndex);

                            BigDecimal seedCostx100 = seedWhichWannaBuy.seedCost.multiply(new BigDecimal(100));
                            seedCostx100 = seedCostx100.setScale(2, RoundingMode.HALF_EVEN);

                            if (Utils.lessThan(player.money, seedCostx100)) {
                                System.out.println("(błąd) Nie posiadasz tyle kasy, by kupić 100 sztuk tych nasion.");
                                break;
                            }

                            player.money = player.money.subtract(seedCostx100);

                            for(int i=0;i<100;i++) {
                                player.seeds.add(seedWhichWannaBuy);
                            }

                            System.out.println("(Zakup nasiona) Zakupiono 100 szt. nasion");
                        }

                        break;
                }

                showAvailableActionsDialog();
                return;

            case 5: //5. Posadzenie roślin (jeżeli posiadasz sadzonki, które można posadzić w tym okresie)

                System.out.println("(info) Wybrano opcję \"Posadzenie roślin\"");

                System.out.println("(Posadzenie roślin) Wybierz nasiona, które chcesz posadzić na polu (Wyświetlane są tylko te, które możesz teraz posadzić):");

                System.out.println("0. Powrót");

                HashMap<String, ArrayList<Seed>> hashMap = new HashMap<String, ArrayList<Seed>>();

                //Tworzenie hash mapy, gdzie kluczem jest nazwa nasiona.
                for(Seed x : player.seeds)
                {
                    if(x.weeksWithPossibilityToPlant.contains(currentWeek) == false)
                        continue;

                    if (!hashMap.containsKey(x.seedName)) {
                        ArrayList<Seed> list = new ArrayList<Seed>();
                        list.add(x);

                        hashMap.put(x.seedName, list);
                    } else {
                        hashMap.get(x.seedName).add(x);
                    }
                }

                Integer c = 1;
                ArrayList<String> keys = new ArrayList<String>();
                for(Map.Entry<String, ArrayList<Seed>> entry : hashMap.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<Seed> value = entry.getValue();
                    keys.add(key);

                    System.out.println(c + ". " + key + " (" + value.size() + " szt. nasion)");
                    c++;
                }

                writtenNumber = readNumberFromConsole();

                if(writtenNumber == 0)
                    return;

                Integer index = writtenNumber-1;

                if(index > hashMap.size()-1)
                {
                    System.out.println("(blad) Podano zly numer nasion.");
                    return;
                }

                //Napisać kod na sadzenie nasion

                ArrayList<Seed> selectedSeeds = hashMap.get(keys.get(index));

                System.out.println("(Posadzenie roślin) Wybrano nasiona: " + keys.get(index) + ". (Posiadasz " + selectedSeeds.size() + " szt.)");
                System.out.println("(Posadzenie roślin) Podaj ile sztuk nasion chcesz posadzić:");
                System.out.println("0. Powrót");

                writtenNumber = readNumberFromConsole();

                if(writtenNumber == 0)
                    return;

                if(writtenNumber > selectedSeeds.size())
                {
                    System.out.println("(blad) Nie posiadasz tyle nasion.");
                    return;
                }

                BigDecimal costOfPlanting = selectedSeeds.get(0).costOfPreparingLandAndPlanting.multiply(new BigDecimal(writtenNumber));
                costOfPlanting = costOfPlanting.setScale(2, RoundingMode.HALF_EVEN);

                if(Utils.lessThan(player.money, costOfPlanting))
                {
                    System.out.println("(blad) Nie stać Cię na przygotowanie ziemi dla tylu nasion.");
                    return;
                }

                Integer selectedSeedCount = writtenNumber;

                System.out.println("(Posadzenie roślin) Wybierz pole, na ktorym chcesz zasadzic nasiona:");
                System.out.println("0. Powrót");

                counter = 1;
                for(ArableLand x : player.arableLands)
                {
                    System.out.println(counter + ". " + x.name);
                    counter++;
                }

                writtenNumber = readNumberFromConsole();

                if(writtenNumber == 0)
                    return;

                index = writtenNumber-1;

                if(index > player.arableLands.size()-1)
                {
                    System.out.println("(blad) Podano zly numer pola.");
                    return;
                }

                for(int i=0;i<selectedSeedCount;i++) {
                    player.arableLands.get(index).seeds.add(selectedSeeds.get(0));

                }

                //Usuwane nasion ze stanu gracza po zasaniu:

                Integer seedsRemoved = 0;
                for(int i=0;i<player.seeds.size();i++)
                {
                    if(player.seeds.get(i).seedName.equals(selectedSeeds.get(0).seedName))
                    {
                        player.seeds.remove(i);
                        seedsRemoved++;

                        if(seedsRemoved == selectedSeedCount)
                            break;
                    }
                }

                player.money = player.money.subtract(costOfPlanting);
                System.out.println("(Posadzenie roślin) Posadzono " + selectedSeedCount + " szt. nasion: " + selectedSeeds.get(0).seedName + " na polu: " + player.arableLands.get(index).name);

                showAvailableActionsDialog();
                return;

            case 6: //6. Zbiory roślin (jeżeli masz gotowe do zebrania plony)

                System.out.println("(info) Wybrano opcję \"Zbiory roślin\"");

                System.out.println("(Zbiory roślin) Wybierz farmę, gdzie chcesz zebrać rośliny:");

                System.out.println("0. Powrót");

                c = 1;
                for(ArableLand x : player.arableLands)
                {
                    System.out.println(c + ". " + x.name + " (Suma plonów: " + x.plants.size() + ")");
                    c++;
                }

                writtenNumber = readNumberFromConsole();

                if(writtenNumber == 0)
                    return;

                index = writtenNumber-1;

                if(index > player.arableLands.size()-1)
                {
                    System.out.println("(blad) Nie posiadasz takiego pola.");
                    return;
                }

                System.out.println("(Zbiory roślin) Wybrano pole do zbioru roślin.");

                Integer arableLandIndex = index;

                Boolean wareHouseFound = false;
                Integer farmIndexWithWareHouse = -1;

                for(int w=0;w<player.farms.size();w++)
                {
                    for(Building b : player.farms.get(w).buildings)
                    {
                        if(b.name.contains("todo")) //Stodoła
                        {
                            farmIndexWithWareHouse = w;
                            wareHouseFound = true;
                            break;
                        }
                    }
                }

                if(wareHouseFound == false)
                {
                    System.out.println("(blad) Nie mozesz zebrac plonow. Nie posiadasz zadnej stodoly.");
                    return;
                }

                for(Building b : player.farms.get(farmIndexWithWareHouse).buildings)
                {
                    if(b.name.contains("todo")) //Stodoła
                    {
                        ((Warehouse)b).storedPlants.addAll(player.arableLands.get(arableLandIndex).plants);
                        player.arableLands.get(arableLandIndex).plants.clear();
                    }
                }

                System.out.println("(Zbiory roślin) Zebrano rośliny z pola.");

                showAvailableActionsDialog();
                return;

            case 7: //7. Sprzedaż roślin lub zwierząt

                System.out.println("(info) Wybrano opcję \"Sprzedaż roślin, zwierząt lub jajek\"");

                System.out.println("0. Powrót");
                System.out.println("1. Sprzedaż roślin");
                System.out.println("2. Sprzedaż zwierząt");
                System.out.println("3. Sprzedaż jajek");

                writtenNumber = readNumberFromConsole();

                if(writtenNumber == 0)
                    return;

                if(writtenNumber > 3)
                    return;

                switch(writtenNumber)
                {
                    case 1:

                        //Pobieranie stodoły głównej

                        Integer farmIndexWithMainWarehouse = -1;
                        Integer mainWarehouseIndex = -1;

                        outerloop:
                        for(int i=0;i<player.farms.size();i++)
                        {

                            for(int b=0;b<player.farms.get(i).buildings.size();b++)
                            {
                                if(farmIndexWithMainWarehouse == -1 && mainWarehouseIndex == -1) {
                                    if (player.farms.get(i).buildings.get(b).name.contains("todo")) //Stodoła
                                    {
                                        farmIndexWithMainWarehouse = i;
                                        mainWarehouseIndex = b;
                                    }
                                }
                            }
                        }

                        if(farmIndexWithMainWarehouse == -1 && mainWarehouseIndex == -1)
                        {
                            System.out.println("(blad) Nie posiadasz stodoly.");
                            return;
                        }

                        HashMap<String, ArrayList<Plant>> hashMap2 = new HashMap<String, ArrayList<Plant>>();

                        //Tworzenie hash mapy, gdzie kluczem jest nazwa rośliny.
                        for(Plant x : ((Warehouse)player.farms.get(farmIndexWithMainWarehouse).buildings.get(mainWarehouseIndex)).storedPlants)
                        {
                            if(x.weeksWithPossibilityToPlant.contains(currentWeek) == false)
                                continue;

                            if (!hashMap2.containsKey(x.name)) {
                                ArrayList<Plant> list = new ArrayList<Plant>();
                                list.add(x);

                                hashMap2.put(x.name, list);
                            } else {
                                hashMap2.get(x.name).add(x);
                            }
                        }

                        System.out.println("(Stodola glowna) Lista roslin na sprzedaz w stodole glownej:");
                        System.out.println("0. Powrót");

                        c = 1;
                        keys = new ArrayList<String>();
                        for(Map.Entry<String, ArrayList<Plant>> entry : hashMap2.entrySet()) {
                            String key = entry.getKey();
                            ArrayList<Plant> value = entry.getValue();
                            keys.add(key);

                            System.out.println(c + ". " + key + " (" + value.size() + " szt.)");
                            c++;
                        }

                        writtenNumber = readNumberFromConsole();

                        if(writtenNumber == 0)
                            return;

                        index = writtenNumber-1;

                        if(index > hashMap2.size()-1)
                        {
                            return;
                        }

                        String keyForSell = keys.get(index);


                        //((Warehouse)player.farms.get(farmIndexWithMainWarehouse).buildings.get(mainWarehouseIndex)).storedPlants.

                        Iterator<Plant> i = ((Warehouse)player.farms.get(farmIndexWithMainWarehouse).buildings.get(mainWarehouseIndex)).storedPlants.iterator();
                        while (i.hasNext()) {
                            Plant s = i.next();

                            if(s.name.equals(keyForSell))
                            {
                                i.remove();
                                player.money = player.money.add(s.purchasePrice);
                            }
                        }


                        System.out.println("(Sprzedaz roslin) Sprzedano rosliny.");

                        break;

                    case 2:

                        //TODO: Logika sprzedazy zwierzat.
                        throw new ExecutionControl.NotImplementedException("TODO: Logika sprzedazy zwierzat.");

                    case 3:

                                if(player.eggsCount == 0)
                                {
                                    System.out.println("(blad) Nie posiadasz zadnych jajek.");
                                    return;
                                }

                                BigDecimal moneyFromEggs = new BigDecimal(2*player.eggsCount);
                                moneyFromEggs.setScale(2, RoundingMode.HALF_EVEN);
                                player.money = player.money.add(moneyFromEggs);

                                System.out.println("(Sprzedaz jajek) Jajka zostaly sprzedane.");
return;



                    default: return;
                }

                showAvailableActionsDialog();
                return;

            case 8: //8. Sprawdzenie stanu zapasów (W tym przypadku pobiera to po prostu stan stodoły głównej)

                System.out.println("(info) Wybrano opcję \"Sprawdzenie stanu zapasów\"");

                showInfoFromMainMagazine();

                writtenNumber = readNumberFromConsole();

                return;


            case 9: //9. Przejrzenie informacji o posiadanych zwierzętach

                System.out.println("(info) Wybrano opcję \"Przejrzenie informacji o posiadanych zwierzętach\"");

                //TODO: Pokazac informacje o posiadanych zwierzetach

                showAvailableActionsDialog();
                return;

            case 10: //10. Przejrzenie informacji o posiadanych sadzonkach i zasadzonych roślinach

                System.out.println("(info) Wybrano opcję \"Przejrzenie informacji o posiadanych sadzonkach i zasadzonych roślinach\"");

                showInfoFromMainMagazine();
                showInfoAboutPlantedSeeds();

                showAvailableActionsDialog();
                return;
        }

        //Akcje, które zdarzaja sie na koniec każdego tygodnia

        doScheduledActionsAtTheEndOfWeek();
        doRandomActionsAtTheEndOfWeek();

        end_week:

        weekCounter++;
        currentWeek++;
    }

    public static void showInfoAboutPlantedSeeds()
    {
        for(ArableLand x : player.arableLands)
        {
            System.out.println("===================================");
            System.out.println("Pole: " + x.name);

            for(Seed s : x.seeds)
            {
                System.out.println(x.name);
            }

            for(Seed s : x.seeds)
            {
                System.out.println("Zasadzone nasiono: " + x.name);
            }
        }
    }

    public static void showInfoFromMainMagazine()
    {
        //Pobieranie stodoły głównej

        Integer farmIndexWithMainWarehouse = -1;
        Integer mainWarehouseIndex = -1;

        outerloop:
        for(int i=0;i<player.farms.size();i++)
        {

            for(int b=0;b<player.farms.get(i).buildings.size();b++)
            {
                if(farmIndexWithMainWarehouse == -1 && mainWarehouseIndex == -1) {
                    if (player.farms.get(i).buildings.get(b).name.contains("todo")) //Stodoła
                    {
                        farmIndexWithMainWarehouse = i;
                        mainWarehouseIndex = b;
                    }
                }
            }
        }

        if(farmIndexWithMainWarehouse == -1 && mainWarehouseIndex == -1)
        {
            System.out.println("(blad) Nie posiadasz stodoly.");
            return;
        }

        HashMap<String, ArrayList<Plant>> hashMap2 = new HashMap<String, ArrayList<Plant>>();

        //Tworzenie hash mapy, gdzie kluczem jest nazwa rośliny.
        for(Plant x : ((Warehouse)player.farms.get(farmIndexWithMainWarehouse).buildings.get(mainWarehouseIndex)).storedPlants)
        {
            if(x.weeksWithPossibilityToPlant.contains(currentWeek) == false)
                continue;

            if (!hashMap2.containsKey(x.name)) {
                ArrayList<Plant> list = new ArrayList<Plant>();
                list.add(x);

                hashMap2.put(x.name, list);
            } else {
                hashMap2.get(x.name).add(x);
            }
        }

        System.out.println("(Stodola glowna) Lista roslin w stodole glownej:");

        var keys = new ArrayList<String>();
        for(Map.Entry<String, ArrayList<Plant>> entry : hashMap2.entrySet()) {
            String key = entry.getKey();
            ArrayList<Plant> value = entry.getValue();
            keys.add(key);

            System.out.println(key + " (" + value.size() + " szt.)");
        }
    }

    public static Integer readNumberFromConsole()
    {
        Integer choosenNumber = -1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            choosenNumber = Integer.parseInt(br.readLine());
            return choosenNumber;
        }
        catch(Exception e)
        {
            System.out.println("(błąd) Możesz podać tylko cyfry.");
            return readNumberFromConsole();
        }
    }

    //Akcje zaplanowane, które mają się dziać na koniec każdego tygodnia
    public static void doScheduledActionsAtTheEndOfWeek()
    {
        for(ArableLand x : player.arableLands)
        {
            x.checkIfSeedsGerminated();
        }
    }

    public static boolean showBuySellArableLandDialog()
    {
        System.out.println("0. Powrót");
        System.out.println("1. Zakup ziemi uprawnej");
        System.out.println("2. Sprzedaż ziemi uprawnej");

        Integer choosenNumber2 = 0;
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        try {
            choosenNumber2 = Integer.parseInt(br2.readLine());
        }
        catch(Exception e)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do 2.");

            return showBuySellArableLandDialog();
        }

        if(choosenNumber2 < 0 || choosenNumber2 > 10)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do 2.");

            return showBuySellArableLandDialog();
        }

        switch(choosenNumber2) {

            case 0: return false;
            case 1:
                System.out.println("(info) Wybrano opcję \"Zakup ziemi uprawnej\"");

                System.out.println("(Zakup ziemi uprawnej) Dostępne ziemie do kupna:");

                return showAvailableArableLandsToBuy();

            case 2:

                System.out.println("(info) Wybrano opcję \"Sprzedaż ziemi uprawnej\"");

                System.out.println("(Sprzedaż ziemi uprawnej) Ziemie, które możesz sprzedać:");

                return showAvailableArableLandsToSell();

        }

        return false;
    }

    public static boolean showAvailableArableLandsToSell()
    {
        System.out.println("0. Powrót");

        int counter = 1;
        for(ArableLand x : player.arableLands)
        {
            System.out.println(counter + ". " + x.name + " (" + x.size + " HA), cena sprzedaży: " + x.sellCost + " zł");
            counter++;
        }

        Integer choosenNumber3 = 0;
        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
        try {
            choosenNumber3 = Integer.parseInt(br3.readLine());
        }
        catch(Exception e)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do " + player.arableLands.size() +".");

            return showAvailableArableLandsToSell();
        }

        if(choosenNumber3 < 0 || choosenNumber3 > player.arableLands.size())
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do " + player.arableLands.size() +".");

            return showAvailableArableLandsToSell();
        }

        if(choosenNumber3 == 0)
        {
            return false;
        }

        boolean sellResult = sellPlayerArableLand(choosenNumber3-1);

        if(sellResult == false)
        {
            return showAvailableArableLandsToSell();
        }
        else
        {
            return true;
        }
    }

    public static boolean showAvailableArableLandsToBuy()
    {
        System.out.println("0. Powrót");

        int counter = 1;
        for(ArableLand x : startingArableLands)
        {
            System.out.println(counter + ". " + x.name + " (" + x.size + " HA), koszt: " + x.buyCost + " zł");
            counter++;
        }

        Integer choosenNumber3 = 0;
        BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
        try {
            choosenNumber3 = Integer.parseInt(br3.readLine());
        }
        catch(Exception e)
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do " + startingArableLands.size() +".");

            return showAvailableArableLandsToBuy();
        }

        if(choosenNumber3 < 0 || choosenNumber3 > startingArableLands.size())
        {
            System.out.println("(błąd) Możesz wpisać tylko cyfry od 0 do " + startingArableLands.size() +".");

            return showAvailableArableLandsToBuy();
        }

        if(choosenNumber3 == 0)
        {
            return false;
        }

        boolean buyResult = buyPlayerArableLand(choosenNumber3-1);

        if(buyResult == false)
        {
            return showAvailableArableLandsToBuy();
        }
        else
        {
            return true;
        }
    }

    public static boolean buyPlayerArableLand(int index)
    {
        if(Utils.lessThan(player.money, startingArableLands.get(index).buyCost))
        {
            System.out.println("(błąd) Nie posiadasz tyle kasy, by kupić tę ziemię.");

            return false;
        }
        else
        {
            player.arableLands.add(startingArableLands.get(index));

            player.money = player.money.subtract(startingArableLands.get(index).buyCost);
            startingArableLands.remove(index);

            System.out.println("(info) Ziemia uprawna została zakupiona.");

            return true;
        }
    }

    public static boolean sellPlayerArableLand(int index)
    {
        player.money = player.money.add(player.arableLands.get(index).sellCost);

        player.arableLands.remove(index);

        System.out.println("(info) Ziemia uprawna została sprzedana.");

        return true;
    }

    public static void doRandomActionsAtTheEndOfWeek()
    {
        /*

        rośliny rosną, zwierzęta przybierają na masie
        istnieje pewna, niewielka szansa, że zwierzęta się rozmnożą jeżeli posiada więcej niż jedno
        ponosisz koszty ochrony roślin przed szkodnikami
        jeżeli masz kury/krowy/owce dostajesz pieniądze za jajka albo mleko
        zwierzęta wcinają paszę, jeśli masz dla nich odłożone plony to w pierwszej kolejności ze stodoły, jeżeli nie to musisz je kupić.

         */

        for(Farm farm : player.farms)
        {
            farm.doAnimalsWeightChange(); //Zarządzanie wagą zwierząt
            farm.doAnimalsGrowUp(); //Zarządzanie rozwojem zwierząt
            farm.doAnimalsMultiply(); //Zarządzanie rozmnażaniem zwierząt
            farm.getEggsFromChickens(); //Zbieranie jajek od kur
        }
    }

    public static boolean givePlayerStartingFarm(int farmId)
    {
        if(farmId > startingFarms.size()-1)
        {
            System.out.println("(błąd) Taka farma nie istnieje.");

            return false;
        }

        if(Utils.lessThan(player.money, startingFarms.get(farmId).buyCost))
        {
            System.out.println("(błąd) Nie masz dość kasy na zakup tej farmy.");

            return false;
        }
        else
        {
            player.farms.add(startingFarms.get(farmId));

            ArableLand tmpArableLand = new ArableLand(startingFarms.get(farmId).size, startingFarms.get(farmId).buyCost, startingFarms.get(farmId).buyCost.divide(new BigDecimal(2)));
            tmpArableLand.name = "Ziemia startowa";

            player.arableLands.add(tmpArableLand);

            System.out.println("(info) Farma startowa wybrana.");

            player.money = player.money.subtract(startingFarms.get(farmId).buyCost);
            startingFarms.remove(farmId);

            return true;
        }
    }

    public static boolean buyPlayerFarm(int farmId)
    {
        if(farmId > startingFarms.size()-1)
        {
            System.out.println("(błąd) Taka farma nie istnieje");

            return false;
        }

        if(Utils.lessThan(player.money, startingFarms.get(farmId).buyCost))
        {
            System.out.println("(błąd) Nie posiadasz kasy na zakup tej farmy.");

            return false;
        }
        else {
            player.farms.add(startingFarms.get(farmId));

            player.money = player.money.subtract(startingFarms.get(farmId).buyCost);
            startingFarms.remove(farmId);


            System.out.println("(info) Dodatkowa farma została zakupiona.");

            return true;
        }
    }

    public static boolean chooseStartingFarm() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int i = Integer.parseInt(br.readLine());

            boolean giveResult = givePlayerStartingFarm(i);

            if(giveResult == false)
            {
                return chooseStartingFarm();
            }
            else
            {
                return true;
            }

        } catch(NumberFormatException nfe) {
            System.err.println("(Błąd) Musisz podać numer.");
            return chooseStartingFarm();
        }
    }

    public static boolean chooseFarmToBuy() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int i = Integer.parseInt(br.readLine());

            if(i == 0)
                return false;


            boolean buyResult = buyPlayerFarm(i-1);

            if(buyResult == false)
            {
                return chooseFarmToBuy();
            }
            else
            {
                return true;
            }

        } catch(NumberFormatException nfe) {
            System.err.println("(Błąd) Musisz podać numer.");
            return chooseFarmToBuy();
        }
    }

    public static boolean buyPlayerBuilding(int buildingId) throws IOException {
        if(buildingId > availableBuildingsToBuy.size()-1)
        {
            System.out.println("(błąd) Taki budynek nie istnieje");

            return false;
        }

        if(Utils.lessThan(player.money, availableBuildingsToBuy.get(buildingId).cost))
        {
            System.out.println("(błąd) Nie posiadasz kasy na zakup tego budynku.");

            return false;
        }
        else {

            //Pokazanie listy farm gracza, na ktorej budynek ma zostac utworzony

            System.out.println("(Zakup budynku) Wybierz farmę, na której ma się pojawić budynek.");

            System.out.println("0. Powrót");

            int counter = 1;
            for(Farm x : player.farms)
            {
                String buildingsOnFarm = "";

                for(Building b : x.buildings)
                {
                    buildingsOnFarm += b.name + ",";
                }

                System.out.println(counter + ". " + x.name + " (Posiada budynki: " + buildingsOnFarm + ")");
                counter++;
            }

            //Wczytywanie z klawiatury
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                int i = Integer.parseInt(br.readLine());
                int farmIndex = i-1;

                if(i == 0)
                    return true;

                if(i < 0 || farmIndex > player.farms.size()-1) {
                    System.out.println("(błąd) Nie masz takiej farmy.");
                    return false;
                }

                player.farms.get(farmIndex).buildings.add(availableBuildingsToBuy.get(buildingId));
                player.money = player.money.subtract(availableBuildingsToBuy.get(buildingId).cost);
                System.out.println("(info) Dodatkowy budynek został zakupiony.");

                return true;

            } catch(NumberFormatException | IOException nfe) {
                System.err.println("(Błąd) Musisz podać numer.");
                return chooseBuildingToBuy();
            }
        }
    }

    public static boolean chooseBuildingToBuy() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            int i = Integer.parseInt(br.readLine());

            if(i == 0)
                return false;


            boolean buyResult = buyPlayerBuilding(i-1);

            if(buyResult == false)
            {
                return chooseBuildingToBuy();
            }
            else
            {
                return true;
            }

        } catch(NumberFormatException nfe) {
            System.err.println("(Błąd) Musisz podać numer.");
            return chooseBuildingToBuy();
        }
    }

    //Dodaje dostępne nasiona do kupna w sklepie
    public static void prepareAvailableSeedsToBuy()
    {
        var weeksWithPossibilityToPlant = new ArrayList<Integer>();
        weeksWithPossibilityToPlant.add(20);
        weeksWithPossibilityToPlant.add(21);
        weeksWithPossibilityToPlant.add(22);
        weeksWithPossibilityToPlant.add(23);
        weeksWithPossibilityToPlant.add(24);
        weeksWithPossibilityToPlant.add(25);
        weeksWithPossibilityToPlant.add(26);
        weeksWithPossibilityToPlant.add(27);
        weeksWithPossibilityToPlant.add(28);
        weeksWithPossibilityToPlant.add(29);
        weeksWithPossibilityToPlant.add(30);

        Seed tmpSeed = new Seed("Nasiono zboża","Zboże", new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), 48, weeksWithPossibilityToPlant, new BigDecimal(0.05), new BigDecimal(0.05));
        availableSeedsToBuy.add(tmpSeed);


        weeksWithPossibilityToPlant = new ArrayList<Integer>();
        weeksWithPossibilityToPlant.add(20);
        weeksWithPossibilityToPlant.add(21);
        weeksWithPossibilityToPlant.add(22);
        weeksWithPossibilityToPlant.add(23);
        weeksWithPossibilityToPlant.add(24);
        weeksWithPossibilityToPlant.add(25);
        weeksWithPossibilityToPlant.add(26);
        weeksWithPossibilityToPlant.add(27);
        weeksWithPossibilityToPlant.add(28);
        weeksWithPossibilityToPlant.add(29);
        weeksWithPossibilityToPlant.add(30);
        tmpSeed = new Seed("Nasiono jęczmienia","Jęczmień", new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), 48, weeksWithPossibilityToPlant, new BigDecimal(0.05), new BigDecimal(0.05));
        availableSeedsToBuy.add(tmpSeed);

        weeksWithPossibilityToPlant = new ArrayList<Integer>();
        weeksWithPossibilityToPlant.add(20);
        weeksWithPossibilityToPlant.add(21);
        weeksWithPossibilityToPlant.add(22);
        weeksWithPossibilityToPlant.add(23);
        weeksWithPossibilityToPlant.add(24);
        weeksWithPossibilityToPlant.add(25);
        weeksWithPossibilityToPlant.add(26);
        weeksWithPossibilityToPlant.add(27);
        weeksWithPossibilityToPlant.add(28);
        weeksWithPossibilityToPlant.add(29);
        weeksWithPossibilityToPlant.add(30);
        tmpSeed = new Seed("Nasiono pszenicy","Pszenica", new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), 48, weeksWithPossibilityToPlant, new BigDecimal(0.05), new BigDecimal(0.05));
        availableSeedsToBuy.add(tmpSeed);

        weeksWithPossibilityToPlant = new ArrayList<Integer>();
        weeksWithPossibilityToPlant.add(20);
        weeksWithPossibilityToPlant.add(21);
        weeksWithPossibilityToPlant.add(22);
        weeksWithPossibilityToPlant.add(23);
        weeksWithPossibilityToPlant.add(24);
        weeksWithPossibilityToPlant.add(25);
        weeksWithPossibilityToPlant.add(26);
        weeksWithPossibilityToPlant.add(27);
        weeksWithPossibilityToPlant.add(28);
        weeksWithPossibilityToPlant.add(29);
        weeksWithPossibilityToPlant.add(30);
        tmpSeed = new Seed("Nasiono ziemniaka","Ziemniak", new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), 48, weeksWithPossibilityToPlant, new BigDecimal(0.05), new BigDecimal(0.05));
        availableSeedsToBuy.add(tmpSeed);
    }

    //Dodaje dostępne budynki do kupna w sklepie
    public static void prepareAvailableBuildingsToBuy()
    {
        int rand = ThreadLocalRandom.current().nextInt(5000, 10000);
        Hovel hovel = new Hovel();
        hovel.cost = new BigDecimal(rand);
        hovel.name = "Kurnik";
        availableBuildingsToBuy.add(hovel);

        rand = ThreadLocalRandom.current().nextInt(10000, 15000);
        Warehouse wareHouse = new Warehouse();
        wareHouse.name = "Stodoła";
        wareHouse.cost = new BigDecimal(rand);
        availableBuildingsToBuy.add(wareHouse);
    }

    //Dodaje dostępne zwierzątka do kupna w sklepie
    public static void prepareAvailableAnimalsToBuy()
    {
        ArrayList<Integer> acceptableFoodTypes = new ArrayList<Integer>();
        acceptableFoodTypes.add(FoodTypes.SANDWICH.getValue());
        acceptableFoodTypes.add(FoodTypes.MEAT.getValue());
        acceptableFoodTypes.add(FoodTypes.APPLE.getValue());

        int rand = ThreadLocalRandom.current().nextInt(500, 1000);
        Dog doggy = new Dog("Pies", new BigDecimal(rand), new BigDecimal(rand/2), 0.2, 20, 7, acceptableFoodTypes, 5, 4.00, 4.00, true, 1);
        availableAnimalsToBuy.add(doggy);

        rand = ThreadLocalRandom.current().nextInt(300, 900);
        Cat cat = new Cat("Kot", new BigDecimal(rand), new BigDecimal(rand/2), 0.2, 20, 7, acceptableFoodTypes, 5, 4.00, 4.00, true, 1);
        availableAnimalsToBuy.add(cat);

        acceptableFoodTypes = new ArrayList<Integer>();
        acceptableFoodTypes.add(FoodTypes.FORAGE.getValue()); //Pasza

        rand = ThreadLocalRandom.current().nextInt(200, 500);
        Chicken chicken = new Chicken("Kura", new BigDecimal(rand), new BigDecimal(rand/2), 0.2, 10, 7, acceptableFoodTypes, 5, 4.00, 4.00, false, 1);
        availableAnimalsToBuy.add(chicken);

    }

    //Dodaje 10 losowych farm do listy startingFarms
    public static void prepareStartingFarms()
    {
        for(Integer i=0;i<10;i++)
        {
            Farm tmpFarm = new Farm();
            tmpFarm.name = "Farma numer " + (i+1);
            tmpFarm.size = Math.round(Math.random() * (5 - 1 + 1) + 1);

            int rand = ThreadLocalRandom.current().nextInt(150000, 200000);

            tmpFarm.buyCost = new BigDecimal(rand);

            var d = Math.random(); // 20% procent szans na kurnik w farmie
            if (d < 0.2)
            {
                Hovel hovel = new Hovel();
                hovel.name = "Kurnik";
                tmpFarm.buildings.add(hovel);
            }

            d = Math.random(); // 10% procent szans na stodołę w farmie
            if (d < 0.1)
            {
                Warehouse wareHouse = new Warehouse();
                wareHouse.name = "Stodoła";
                tmpFarm.buildings.add(wareHouse);
            }

            startingFarms.add(tmpFarm);
        }
    }

    //Dodaje 10 losowych pól uprawnych do możliwości zakupu. Cena jest różna za hektar.
    public static void prepareStartingArableLands()
    {
        String[] arr={"Pole uprawne obok rzeki", "Pole uprawne dobrej jakości", "Pole uprawne kamieniste", "Pole uprawne", "Pole uprawne z żyzną glebą", "Pole do upraw wszelkiego rodzaju", "Pole",
                "Pole uprawne", "Pole uprawne podmokłe", "Pole uprawne nierówne", "Pole uprawne równe", "Pole", "Pole", "Pole"};

        for(Integer i=0;i<10;i++)
        {
            Random r=new Random();
            int randomNumber=r.nextInt(arr.length);

            int rand = ThreadLocalRandom.current().nextInt(150000, 200000);
            ArableLand tmpArableLand = new ArableLand(Math.round(Math.random() * (5 - 1 + 1) + 1), new BigDecimal(rand), new BigDecimal(rand).divide(new BigDecimal(2)));
            tmpArableLand.name = arr[randomNumber];

            startingArableLands.add(tmpArableLand);
        }
    }
}
