package obniavka.timemanagment.data;

public enum Currency {
    EURO("EURO", "cents"),
    USD("USD", "cents"),
    UAH("UAH", "kopecks"),
    GBP("GBP", "pennies"),
    PLN("PLN", "grosze");

    private final String currency;
    private final String fractional;

    private Currency(String currency, String fractional){
        this.currency = currency;
        this.fractional = fractional;
    }

    public String getCurrency(){
        return this.currency;
    }

    public String getFractional(){
        return this.fractional;
    }
}
