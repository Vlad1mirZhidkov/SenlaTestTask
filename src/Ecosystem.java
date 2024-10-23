import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

class Ecosystem {
    private List<Plant> plants;
    private List<Animal> animals;
    private int temperature;
    private int humidity;
    private int water;
    private int food;
    private Random random;
    private FileHandler fileHandler;
    private String LOG_FILE;

    public Ecosystem(String name, int temperature, int humidity, int water, int food) {
        this.plants = new ArrayList<>();
        this.animals = new ArrayList<>();
        this.temperature = temperature;
        this.humidity = humidity;
        this.water = water;
        this.food = food;
        this.random = new Random();
        this.fileHandler = new FileHandler(name);
        LOG_FILE = name + "/ecosystem_log.txt";
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWater(int water) {
        this.water = water;
    }
    public void setFood(int food){
        this.food = food;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
        logChange("Добавлено растение: " + plant);
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
        logChange("Добавлено животное: " + animal);
    }

    public void removePlant(int index) {
        Plant removedPlant = plants.remove(index);
        logChange("Удалено растение: " + removedPlant);
    }

    public void removeAnimal(int index) {
        Animal removedAnimal = animals.remove(index);
        logChange("Удалено животное: " + removedAnimal);
    }

    public void simulateStep() {
        Iterator<Plant> plantIterator = plants.iterator();
        while (plantIterator.hasNext()) {
            Plant plant = plantIterator.next();
            if (water > plant.getWaterConsumption()) {
                plant.grow();
                water -= plant.getWaterConsumption();
                logChange(plant.getName() + " вырос до размера: " + plant.getSize());
            } else {
                logChange("Растение " + plant.getName() + " высохло");
                plantIterator.remove();
            }
        }
        logChange("Растения выросли, остаток воды: " + water);

        Iterator<Animal> animalIterator = animals.iterator();
        while (animalIterator.hasNext()) {
            Animal animal = animalIterator.next();
            if(animal.getFoodConsumption() <= food){
                food -= animal.getFoodConsumption();
            }
            else{
                if (!eat(animal)) {
                    animalIterator.remove();
                }
            }
        }

        int waterIncrease = (humidity + temperature) / 10;
        water += waterIncrease;
        logChange("Вода пополнилась на " + waterIncrease);

        int foodIncrease = food / 10;
        food += foodIncrease;
        logChange("Еда пополнилась на " + foodIncrease);

        changeWeather();

        logChange("Текущая вода: " + water + ", температура: " + temperature + ", влажность: " + humidity);

        removeDeadEntities();
    }

    private boolean eat(Animal predator) {
        int foodConsumed = 0;
        boolean isAlive = true;

        Iterator<Plant> plantIterator = plants.iterator();
        while (plantIterator.hasNext()) {
            Plant plant = plantIterator.next();
            if (plant.getSize() <= predator.getSize()) {
                logChange(predator.getName() + " съел " + plant.getName());
                foodConsumed += plant.getWaterConsumption();
                plantIterator.remove();
            }
            if (foodConsumed >= predator.getFoodConsumption()) {
                break;
            }
        }

        Iterator<Animal> animalIterator = animals.iterator();
        while (animalIterator.hasNext()) {
            Animal prey = animalIterator.next();
            if (prey.getSize() < predator.getSize() && water >= predator.getFoodConsumption()) {
                logChange(predator.getName() + " съел " + prey.getName());
                foodConsumed += prey.getFoodConsumption();
                animalIterator.remove();
            }
            if (foodConsumed >= predator.getFoodConsumption()) {
                break;
            }
        }

        if (foodConsumed < predator.getFoodConsumption()) {
            isAlive = false;
            logChange(predator.getName() + " умер от голода");
        }

        return isAlive;
    }

    private void changeWeather() {
        int tempChange = random.nextInt(5) - 2;
        int humidityChange = random.nextInt(5) - 2;
        temperature += tempChange;
        humidity += humidityChange;
        logChange("Изменение погоды: температура изменена на " + tempChange + ", влажность изменена на " + humidityChange);
    }

    private void removeDeadEntities(){
        plants.removeIf(plant -> water < plant.getWaterConsumption());
        animals.removeIf(animal -> water < animal.getFoodConsumption());
    }

    private void logChange(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePredictions() {
        System.out.println("Прогнозы по экосистеме:");

        for (Plant plant : plants) {
            String prediction = (water >= plant.getWaterConsumption()) ? "Увеличение" : "Уменьшение";
            System.out.println(plant.getName() + ": " + prediction + " популяции.");
        }

        for (Animal animal : animals) {
            String prediction = (water >= (animal.getFoodConsumption() * 2)) ? "Увеличение" : "Уменьшение";
            System.out.println(animal.getName() + ": " + prediction + " популяции.");
        }
        System.out.println();
    }

    public void saveToFiles(String folderName) throws IOException {
        folderName = folderName.split("/")[0];
        File dir = new File(folderName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        fileHandler.savePlantsToFile(plants, folderName);
        fileHandler.saveAnimalsToFile(animals, folderName);
        fileHandler.saveConfigToFile(temperature, humidity, water, food, folderName);
    }

    public static Ecosystem loadFromFiles(String folderName) throws IOException {
        FileHandler fileHandler = new FileHandler(folderName);
        return fileHandler.loadFromFiles(folderName);
    }
}