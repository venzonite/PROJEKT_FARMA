import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.Random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static ArrayList<Farm> startingFarms;
    public static ArrayList<ArableLand> startingArableLands;
    public static ArrayList<Seed> availableSeedsToBuy;

    public static Player player;
    public static int weekCounter = 1;
    public static int currentYear = 2020;
    public static int currentWeek = 1;

    public static void main(String[] args) throws IOException {

        System.out.println("Projekt FARMA - GRA - Mateusz Wejer");
        System.out.println("- Wybierz farmę startową (Wpisz numer od 1 do 10):");

        startingFarms = new ArrayList<Farm>();
        prepareStartingFarms();

        startingArableLands = new ArrayList<ArableLand>();
        prepareStartingArableLands();

        availableSeedsToBuy = new ArrayList<Seed>();
        prepareAvailableSeedsToBuy();

        player = new Player();
        player.money = new BigDecimal(200000); //200.000 zł na start.

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

    public static void showAvailableActionsDialog() throws IOException {

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

                showAvailableActionsDialog();
                return;

            case 4: //4. Zakup zwierząt lub roślin

                System.out.println("(info) Wybrano opcję \"Zakup zwierząt lub roślin\"");

                showAvailableActionsDialog();
                return;

            case 5: //5. Posadzenie roślin (jeżeli posiadasz sadzonki, które można posadzić w tym okresie)

                System.out.println("(info) Wybrano opcję \"Posadzenie roślin\"");

                showAvailableActionsDialog();
                return;

            case 6: //6. Zbiory roślin (jeżeli masz gotowe do zebrania plony)

                System.out.println("(info) Wybrano opcję \"Zbiory roślin\"");

                showAvailableActionsDialog();
                return;

            case 7: //7. Sprzedaż roślin lub zwierząt

                System.out.println("(info) Wybrano opcję \"Sprzedaż roślin lub zwierząt\"");

                showAvailableActionsDialog();
                return;

            case 8: //8. Sprawdzenie stanu zapasów

                System.out.println("(info) Wybrano opcję \"Sprawdzenie stanu zapasów\"");

                showAvailableActionsDialog();
                return;

            case 9: //9. Przejrzenie informacji o posiadanych zwierzętach

                System.out.println("(info) Wybrano opcję \"Przejrzenie informacji o posiadanych zwierzętach\"");

                showAvailableActionsDialog();
                return;

            case 10: //10. Przejrzenie informacji o posiadanych sadzonkach i zasadzonych roślinach

                System.out.println("(info) Wybrano opcję \"Przejrzenie informacji o posiadanych sadzonkach i zasadzonych roślinach\"");

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

    //Dodaje dostępne nasiona do kupna w sklepie
    public static void prepareAvailableSeedsToBuy()
    {
        for(Integer i=0;i<10;i++)
        {
            var weeksWithPossibilityToPlant = new ArrayList<Integer>();
            weeksWithPossibilityToPlant.add(4);
            weeksWithPossibilityToPlant.add(5);
            weeksWithPossibilityToPlant.add(6);

            //utworzyć klase zboże, przenica itp i tam dodac ceny statyczne w przeliczeniu na hektar
            Seed tmpSeed = new Seed("Nasiono zboża","Zboże", new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), new BigDecimal(0.05), 90, weeksWithPossibilityToPlant, new BigDecimal(0.05), new BigDecimal(0.05));
            availableSeedsToBuy.add(tmpSeed);
        }
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
