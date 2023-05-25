package obniavka.timemanagment.helper;

public class NumberToWordsConverter {
    private static final String[] units = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen"
    };
    private static final String[] tens = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static final String[] unitsUk = {
            "", "одна", "дві", "три", "чотири", "п'ять", "шість", "сім", "вісім", "дев'ять", "десять", "одинадцять",
            "дванадцять", "тринадцять", "чотирнадцять", "п'ятнадцять", "шістнадцять", "сімнадцять", "вісімнадцять", "дев'ятнадцять"
    };

    private static final String[] tensUk = {
            "", "", "двадцять", "тридцять", "сорок", "п'ятдесят", "шістдесят", "сімдесят", "вісімдесят", "дев'яносто"
    };

    private static final String[] hundreds = {
            "", "сто", "двісті", "триста", "чотириста", "п'ятсот", "шістсот", "сімсот", "вісімсот", "дев'ятсот"
    };
    public static String convertNumberToWordsEN(int number){
        String english="";
        if (number / 1000 > 0) {
            english += convertNumberToWordsEN(number / 1000) + " thousand ";
            number %= 1000;
        }

        if (number / 100 > 0) {
            english += convertNumberToWordsEN(number / 100) + " hundred ";
            number %= 100;
        }

        if (number > 0) {
            if (number < 20) {
                english += units[number];
            } else {
                english += tens[number / 10];
                if (number % 10 > 0) {
                    english += "-" + units[number % 10];
                }
            }
        }

        return english.trim().substring(0, 1).toUpperCase() + english.trim().substring(1);

    }

    public static String convertToUkrainian(int number) {
        String word = "";

        if ((number / 1000000) > 0) {
            word += convertToUkrainian(number / 1000000) + " мільйон ";
            number %= 1000000;
        }

        if ((number / 1000) > 0) {
            if(convertToUkrainian(number / 1000).charAt(0) == '1'){
                word += convertToUkrainian(number / 1000) + " тисяча ";
            }
            else if(convertToUkrainian(number / 1000).charAt(0) == '4' || convertToUkrainian(number / 1000).charAt(0) == '2'){
                word += convertToUkrainian(number / 1000) + " тисячі ";
            }
            else {
                word += convertToUkrainian(number / 1000) + " тисяч ";
            }
            number %= 1000;
        }

        if ((number / 100) > 0) {
            word += hundreds[number / 100] + " ";
            number %= 100;
        }

        if (number > 0) {
            if (number < 20) {
                word += unitsUk[number] + " ";
            } else {
                word += tensUk[number / 10] + " " + unitsUk[number % 10] + " ";
            }
        }
        return word.trim().substring(0, 1).toUpperCase() + word.trim().substring(1);
    }

}
