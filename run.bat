@echo off

IF NOT EXIST "bin" (
    echo Компиляция Java файлов...
    mkdir bin
    javac -d bin src\*.java
)

echo Запуск симуляции экосистемы...
java -cp bin EcosystemManager
