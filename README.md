# Ecosystem Simulation

## Описание
Это приложение моделирует экосистему, состоящую из растений и животных. Пользователь может добавлять, удалять и изменять характеристики растений и животных, а также сохранять и загружать состояние экосистемы из файлов.

## Установка
1. Клонируйте репозиторий:
    ```sh
    git clone <URL вашего репозитория>
    ```
2. Перейдите в директорию проекта:
    ```sh
    cd <имя директории проекта>
    ```
3. Соберите проект с помощью вашей IDE (например, IntelliJ IDEA) или с помощью командной строки:
    ```sh
    ./gradlew build
    ```

## Использование скрипта для запуска:
Linux:
    ```sh
    chmod +x run.sh
    ./run.sh
    ```
    
Windows:
    ```sh
    run.bat
    ```
    
## Структура проекта
- `src/EcosystemManager.java`: Точка входа в приложение.
- `src/EcosystemController.java`: Управляет взаимодействием пользователя с экосистемой.
- `src/FileHandler.java`: Обрабатывает сохранение и загрузку данных экосистемы из файлов.
- `src/Plant.java`: Класс, представляющий растение.
- `src/Animal.java`: Класс, представляющий животное.
- `src/Ecosystem.java`: Класс, представляющий экосистему.

## Примеры команд
- Добавление растения:
    ```sh
    Введите имя растения: Роза
    Введите потребление воды: 5
    Введите размер: 10
    ```
- Удаление животного:
    ```sh
    Введите номер животного для удаления: 2
    ```
## Выполнил
Жидков Владимир

## Лицензия
Этот проект лицензирован под лицензией MIT. Подробности см. в файле `LICENSE`.
