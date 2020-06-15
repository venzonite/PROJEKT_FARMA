import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static List<Farm> startingFarms;
    public static Player player;
    public static int weekCounter = 1;

    public static void main(String[] args) throws IOException {

        System.out.println("Projekt FARMA - GRA - Mateusz Wejer");
        System.out.println("- Wybierz farmę startową (Wpisz numer od 1 do 10):");

        startingFarms = new ArrayList<Farm>();
        prepareStartingFarms();

        player = new Player();
        player.money = new BigDecimal(200000); //200.000 zł na start. Dlaczego tak dużo? Bo gracz na starcie musi kupić jakąś farmę :)

        int counter = 0;
        for(Farm x : startingFarms)
        {
            String buildingString = "";
            for(Building b : x.buildings)
            {
                buildingString += b.name + ",";
            }

            counter++;
            System.out.println("Farma " + counter + " (" + x.size + " HA) (koszt: " + x.buyCost + " zł), posiada budynki: " + buildingString);
        }

        chooseStartingFarm();

        //Główna pętla gry. Każda iteracja odpowiada jednemu tygodniowi w roku.
        for(;;) {
            showAvailableActionsDialog();
        }
    }

    public static void showAvailableActionsDialog() throws IOException {

        begin_week:

        System.out.println("===================================================================");
        System.out.println("Jest tydzień " + weekCounter + " roku 2020, jakie akcje podejmujesz?");

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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choosenStartingFarmNumber = br.readLine();

        //Tutaj akcje losowe, które zdarzaja sie bez wzgledu na akcje gracza

        doRandomActionsAtTheEndOfWeek();

        end_week:

        weekCounter++;
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

    public static void givePlayerStartingFarm(int farmId)
    {
        player.farms.add(startingFarms.get(farmId-1));
        System.out.println("(info) Farma startowa wybrana.");
    }

    public static void chooseStartingFarm() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choosenStartingFarmNumber = br.readLine();

        try {
            int i = Integer.parseInt(br.readLine());
            givePlayerStartingFarm(i);

        } catch(NumberFormatException nfe) {
            System.err.println("(Błąd) Musisz podać numer.");
            chooseStartingFarm();
        }
    }

    //Dodaje 10 losowych farm do listy startingFarms
    public static void prepareStartingFarms()
    {
        for(Integer i=0;i<10;i++)
        {
            Farm tmpFarm = new Farm();
            tmpFarm.name = "Farma " + i+1;
            tmpFarm.size = Math.round(Math.random() * (5 - 1 + 1) + 1);
            tmpFarm.buyCost = new BigDecimal(Math.round(Math.random() * (190000 - 180000 + 180000) + 180000));

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
}
