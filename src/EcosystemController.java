import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class EcosystemController {
    private Scanner scanner = new Scanner(System.in);
    private Ecosystem ecosystem;
    private FileHandler fileHandler;

    public void start() {
        try {
            initializeEcosystem();
            runSimulation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeEcosystem() throws IOException {
        System.out.println("Введите имя симуляции (папка): ");
        String folderName = scanner.nextLine();

        fileHandler = new FileHandler(folderName);

        if (new File(folderName).exists()) {
            System.out.println("Загрузка симуляции из папки...");
            ecosystem = fileHandler.loadEcosystem();
        } else {
            System.out.println("Создание новой симуляции...");
            createNewEcosystem(folderName);
        }
    }

    private void createNewEcosystem(String folderName) throws IOException {
        fileHandler.createNewSimulation();
        ecosystem = createEcosystem(folderName);
    }

    private Ecosystem createEcosystem(String folderName) {
        System.out.print("Введите температуру: ");
        int temperature = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите влажность: ");
        int humidity = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите количество воды: ");
        int water = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите количество еды: ");
        int food = Integer.parseInt(scanner.nextLine());
        return new Ecosystem(folderName, temperature, humidity, water, food);
    }

    private void runSimulation() throws IOException {
        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            handleUserChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("1. Добавить растение");
        System.out.println("2. Добавить животное");
        System.out.println("3. Показать растения");
        System.out.println("4. Показать животных");
        System.out.println("5. Изменить растение");
        System.out.println("6. Изменить животное");
        System.out.println("7. Удалить растение");
        System.out.println("8. Удалить животное");
        System.out.println("9. Показать прогнозы");
        System.out.println("10. Шаг симуляции");
        System.out.println("11. Сохранить и выйти");
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addPlant();
                break;
            case 2:
                addAnimal();
                break;
            case 3:
                showPlants();
                break;
            case 4:
                showAnimals();
                break;
            case 5:
                editPlant();
                break;
            case 6:
                editAnimal();
                break;
            case 7:
                deletePlant();
                break;
            case 8:
                deleteAnimal();
                break;
            case 9:
                ecosystem.generatePredictions();
                break;
            case 10:
                ecosystem.simulateStep();
                break;
            case 11:
                saveAndExit();
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void saveAndExit() {
        try {
            fileHandler.saveEcosystem(ecosystem);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPlant() {
        System.out.print("Введите название растения: ");
        String name = scanner.nextLine();
        System.out.print("Введите потребление воды: ");
        int waterConsumption = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Введите размер растения: ");
        int size = Integer.parseInt(scanner.nextLine().trim());
        ecosystem.addPlant(new Plant(name, waterConsumption, size));
        System.out.println();
    }

    private void addAnimal() {
        System.out.print("Введите название животного: ");
        String name = scanner.nextLine();
        System.out.print("Введите потребление пищи: ");
        int foodConsumption = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Введите размер животного: ");
        int size = Integer.parseInt(scanner.nextLine().trim());
        ecosystem.addAnimal(new Animal(name, foodConsumption, size));
        System.out.println();
    }

    private void showPlants() {
        System.out.println("Растения: ");
        for (int i = 0; i < ecosystem.getPlants().size(); i++) {
            System.out.println(i + ". " + ecosystem.getPlants().get(i));
        }
        System.out.println();
    }

    private void showAnimals() {
        System.out.println("Животные: ");
        for (int i = 0; i < ecosystem.getAnimals().size(); i++) {
            System.out.println(i + ". " + ecosystem.getAnimals().get(i));
        }
        System.out.println();
    }

    private void editPlant() {
        showPlants();
        System.out.print("Введите номер растения для редактирования: ");
        int index = Integer.parseInt(scanner.nextLine());

        if (index >= 0 && index < ecosystem.getPlants().size()) {
            Plant plant = ecosystem.getPlants().get(index);
            System.out.print("Введите новое название растения (текущее: " + plant.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Введите новое потребление воды (текущее: " + plant.getWaterConsumption() + "): ");
            int waterConsumption = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите новый размер растения (текущий: " + plant.getSize() + "): ");
            int size = Integer.parseInt(scanner.nextLine());
            plant.setName(name);
            plant.setWaterConsumption(waterConsumption);
            plant.setSize(size);
        } else {
            System.out.println("Неверный номер.");
        }
        System.out.println();
    }

    private void editAnimal() {
        showAnimals();
        System.out.print("Введите номер животного для редактирования: ");
        int index = Integer.parseInt(scanner.nextLine());

        if (index >= 0 && index < ecosystem.getAnimals().size()) {
            Animal animal = ecosystem.getAnimals().get(index);
            System.out.print("Введите новое название животного (текущее: " + animal.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Введите новое потребление пищи (текущее: " + animal.getFoodConsumption() + "): ");
            int foodConsumption = Integer.parseInt(scanner.nextLine());
            System.out.println("Введите новый размер животного (текущий: " + animal.getSize() + "): ");
            int size = Integer.parseInt(scanner.nextLine());
            animal.setName(name);
            animal.setFoodConsumption(foodConsumption);
            animal.setSize(size);
        } else {
            System.out.println("Неверный номер.");
        }
        System.out.println();
    }

    private void deletePlant() {
        showPlants();
        System.out.print("Введите номер растения для удаления: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < ecosystem.getPlants().size()) {
            ecosystem.removePlant(index);
            System.out.println("Растение удалено.");
        } else {
            System.out.println("Неверный номер.");
        }
        System.out.println("\n");
    }

    private void deleteAnimal() {
        showAnimals();
        System.out.print("Введите номер животного для удаления: ");
        int index = Integer.parseInt(scanner.nextLine());

        if (index >= 0 && index < ecosystem.getAnimals().size()) {
            ecosystem.removeAnimal(index);
            System.out.println("Животное удалено.");
        } else {
            System.out.println("Неверный номер.");
        }
        System.out.println("\n");
    }
}
