package datum;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpecialDates {
    private Map<String, String> specialDates;

    public SpecialDates() {
        specialDates = new HashMap<>();

        try {
            // Olvasas
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File("C:\\Users\\Admin\\eclipse-nav\\NavDateNow2\\src\\datum\\Season.json");
            specialDates = objectMapper.readValue(jsonFile, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            System.err.println("Error reading special dates from JSON file: " + e.getMessage());
        }
    }

    //Nem ünnepnapok
    public String getDate(String dateString) {
        String holiday = specialDates.get(dateString);
        String season = "";

        if (holiday != null) {
            return holiday;
        } else {
            if (isWinterMonth(dateString)) {
                season = "Tel";
            } else if (isSpringMonth(dateString)) {
                season = "Tavasz";
            } else if (isSummerMonth(dateString)) {
                season = "Nyar";
            } else if (isAutumnMonth(dateString)) {
                season = "Osz";
            }
            return season;
        }
    }
    //Évszakok intervallumai
    private boolean isWinterMonth(String dateString) {
        int month = Integer.parseInt(dateString.split("-")[1]);
        return (month == 12 || month == 1 || month == 2);
    }

    private boolean isSpringMonth(String dateString) {
        int month = Integer.parseInt(dateString.split("-")[1]);
        return (month >= 3 && month <= 5);
    }

    private boolean isSummerMonth(String dateString) {
        int month = Integer.parseInt(dateString.split("-")[1]);
        return (month >= 6 && month <= 8);
    }

    private boolean isAutumnMonth(String dateString) {
        int month = Integer.parseInt(dateString.split("-")[1]);
        return (month >= 9 && month <= 11);
    }
    //Datum(yyy-MM-dd)
    public static void main(String[] args) {
        SpecialDates specialDates = new SpecialDates();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);
        System.out.println("yyyy-MM-dd: ");
        String dateStr = scanner.next();

        try {
            Date date = dateFormat.parse(dateStr);
            String result = specialDates.getDate(dateStr);
            System.out.println(dateStr + ": " + result);
        } catch (ParseException e) {
            System.err.println("Hibás dátum formátum.");
        }

        scanner.close();
    }
}
