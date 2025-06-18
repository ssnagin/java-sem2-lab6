#!/bin/bash

PORT=22815

# Находим PID процесса, использующего порт
PID=$(sudo lsof -t -i :$PORT)

if [ -z "$PID" ]; then
    echo "Порт $PORT не занят."
    exit 0
else
    echo "Найден процесс с PID $PID, занимающий порт $PORT."
    
    # Убиваем процесс
    sudo kill -9 $PID
    
    # Проверяем, был ли процесс убит
    if sudo lsof -t -i :$PORT > /dev/null; then
        echo "Не удалось убить процесс."
        exit 1
    else
        echo "Процесс успешно убит."
        exit 0
    fi
fi
