#!/bin/bash

if [ ! -d "bin" ]; then
  echo "Компиляция Java файлов..."
  mkdir -p bin
  javac -d bin src/*.java
fi

echo "Запуск симуляции экосистемы..."
java -cp bin EcosystemManager
