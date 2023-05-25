package obniavka.timemanagment.helper;

import java.util.HashMap;
import java.util.Map;

public class Transliteration {
    private static final Map<Character, String> transliterationMap = new HashMap<>();
    private static final Map<String, String> currency = new HashMap<>();


    static {
        transliterationMap.put('а', "a");
        transliterationMap.put('б', "b");
        transliterationMap.put('в', "v");
        transliterationMap.put('г', "h");
        transliterationMap.put('д', "d");
        transliterationMap.put('е', "e");
        transliterationMap.put('є', "ie");
        transliterationMap.put('ж', "zh");
        transliterationMap.put('з', "z");
        transliterationMap.put('и', "y");
        transliterationMap.put('і', "i");
        transliterationMap.put('ї', "i");
        transliterationMap.put('й', "i");
        transliterationMap.put('к', "k");
        transliterationMap.put('л', "l");
        transliterationMap.put('м', "m");
        transliterationMap.put('н', "n");
        transliterationMap.put('о', "o");
        transliterationMap.put('п', "p");
        transliterationMap.put('р', "r");
        transliterationMap.put('с', "s");
        transliterationMap.put('т', "t");
        transliterationMap.put('у', "u");
        transliterationMap.put('ф', "f");
        transliterationMap.put('х', "kh");
        transliterationMap.put('ц', "ts");
        transliterationMap.put('ч', "ch");
        transliterationMap.put('ш', "sh");
        transliterationMap.put('щ', "shch");
        transliterationMap.put('ь', "'");
        transliterationMap.put('ю', "iu");
        transliterationMap.put('я', "ia");

        transliterationMap.put('А', "A");
        transliterationMap.put('Б', "B");
        transliterationMap.put('В', "V");
        transliterationMap.put('Г', "H");
        transliterationMap.put('Д', "D");
        transliterationMap.put('Е', "E");
        transliterationMap.put('Є', "Ye");
        transliterationMap.put('Ж', "Zh");
        transliterationMap.put('З', "Z");
        transliterationMap.put('І', "I");
        transliterationMap.put('Ї', "I");
        transliterationMap.put('Й', "I");
        transliterationMap.put('К', "K");
        transliterationMap.put('Л', "L");
        transliterationMap.put('М', "M");
        transliterationMap.put('Н', "N");
        transliterationMap.put('О', "O");
        transliterationMap.put('П', "P");
        transliterationMap.put('Р', "R");
        transliterationMap.put('С', "S");
        transliterationMap.put('Т', "T");
        transliterationMap.put('У', "U");
        transliterationMap.put('Ф', "F");
        transliterationMap.put('Х', "Kh");
        transliterationMap.put('Ц', "Ts");
        transliterationMap.put('Ч', "Ch");
        transliterationMap.put('Ш', "Sh");
        transliterationMap.put('Щ', "Shch");
        transliterationMap.put('Ю', "Yu");
        transliterationMap.put('Я', "Ya");

        currency.put("EUR", "cents");
        currency.put("USD", "cents");
        currency.put("GBP", "pennies");
        currency.put("PLN", "grosze");
        currency.put("UAH", "kopecks");

    }

    public static String transliterate(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (transliterationMap.containsKey(c)) {
                result.append(transliterationMap.get(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String getFractional(String cur){
        return currency.get(cur);
    }
}
