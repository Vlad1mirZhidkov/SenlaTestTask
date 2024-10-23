class Animal {
    private String name;
    private int foodConsumption;
    private int size;

    public Animal(String name, int foodConsumption, int size) {
        this.name = name;
        this.foodConsumption = foodConsumption;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Animal{name='" + name + "', foodConsumption=" + foodConsumption + "}";
    }

    public String toFileFormat() {
        return name + "," + foodConsumption + "," + size;
    }

    public static Animal fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new Animal(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }
}
