class Plant {
    private String name;
    private int waterConsumption;
    private int size;

    public Plant(String name, int waterConsumption, int size) {
        this.name = name;
        this.waterConsumption = waterConsumption;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void grow() {
        this.size++;
    }

    public int getWaterConsumption() {
        return waterConsumption;
    }

    @Override
    public String toString() {
        return "Plant{name='" + name + "', waterConsumption=" + waterConsumption + "}";
    }

    public String toFileFormat() {
        return name + "," + waterConsumption + "," + size;
    }

    public static Plant fromFileFormat(String line) {
        String[] parts = line.split(",");
        return new Plant(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWaterConsumption(int waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public void setSize(int size){
        this.size = size;
    }
}
