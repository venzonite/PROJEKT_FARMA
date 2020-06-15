public enum FoodTypes {
    APPLE(0), MEAT(1), SANDWICH(2), FORAGE(3);

    private final int value;
    private FoodTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}