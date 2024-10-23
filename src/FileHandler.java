import java.io.*;
import java.util.List;

class FileHandler {
    private String folderName;

    public FileHandler(String folderName) {
        this.folderName = folderName;
    }

    public void createNewSimulation() throws IOException {
        File dir = new File(folderName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File logFile = new File(folderName + "/ecosystem_log.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
    }

    public Ecosystem loadEcosystem() throws IOException {
        if (!new File(folderName).exists()) {
            throw new FileNotFoundException("Папка симуляции не существует.");
        }
        return Ecosystem.loadFromFiles(folderName);
    }

    public void saveEcosystem(Ecosystem ecosystem) throws IOException {
        ecosystem.saveToFiles(folderName);
    }

    public void savePlantsToFile(List<Plant> plants, String folderName) throws IOException {
        try (BufferedWriter plantWriter = new BufferedWriter(new FileWriter(folderName + "/plants.txt"))) {
            for (Plant plant : plants) {
                plantWriter.write((plant.toFileFormat()));
                plantWriter.newLine();
            }
        }
    }

    public void saveAnimalsToFile(List<Animal> animals, String folderName) throws IOException {
        try (BufferedWriter animalWriter = new BufferedWriter(new FileWriter(folderName + "/animals.txt"))) {
            for (Animal animal : animals) {
                animalWriter.write(animal.toFileFormat());
                animalWriter.newLine();
            }
        }
    }

    public void saveConfigToFile(int temperature, int humidity, int water, int food, String folderName) throws IOException {
        try (BufferedWriter configWriter = new BufferedWriter(new FileWriter(folderName + "/config.txt"))) {
            configWriter.write("temperature=" + temperature);
            configWriter.newLine();
            configWriter.write("humidity=" + humidity);
            configWriter.newLine();
            configWriter.write("water=" + water);
            configWriter.newLine();
            configWriter.write("food=" + food);
            configWriter.newLine();
        }
    }

    public Ecosystem loadFromFiles(String folderName) throws IOException {
        Ecosystem ecosystem = new Ecosystem(folderName, 0, 0, 0, 0);

        try (BufferedReader plantReader = new BufferedReader(new FileReader(folderName + "/plants.txt"))) {
            String line;
            while ((line = plantReader.readLine()) != null) {
                ecosystem.getPlants().add(Plant.fromFileFormat(line));
            }
        }

        try (BufferedReader animalReader = new BufferedReader(new FileReader(folderName + "/animals.txt"))) {
            String line;
            while ((line = animalReader.readLine()) != null) {
                ecosystem.getAnimals().add(Animal.fromFileFormat(line));
            }
        }

        try (BufferedReader configReader = new BufferedReader(new FileReader(folderName + "/config.txt"))) {
            ecosystem.setTemperature(Integer.parseInt(configReader.readLine().split("=")[1]));
            ecosystem.setHumidity(Integer.parseInt(configReader.readLine().split("=")[1]));
            ecosystem.setWater(Integer.parseInt(configReader.readLine().split("=")[1]));
            ecosystem.setFood(Integer.parseInt(configReader.readLine().split("=")[1]));
        }

        return ecosystem;
    }
}
