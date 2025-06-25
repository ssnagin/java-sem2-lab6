package com.ssnagin.collectionmanager.gui.nodes.animation;

import com.ssnagin.collectionmanager.collection.model.MusicBand;
import com.ssnagin.collectionmanager.console.ClientConsole;
import com.ssnagin.collectionmanager.gui.alert.InfoAlert;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class AnimationElement {
    private final Canvas canvas;
    private final GraphicsContext gc;
    // Используем ConcurrentHashMap для потенциальной потокобезопасности, если updateBands будет вызываться из разных потоков
    private final Map<Long, MusicBand> bands = new ConcurrentHashMap<>();
    private final Map<String, Color> userColors = new HashMap<>();
    private final Random random = new Random();
    private MusicBand clickedBand = null; // Для отслеживания кликнутого объекта

    // Хранилище для текущих анимаций объектов
    private final Map<Long, Animation> activeAnimations = new ConcurrentHashMap<>();

    public AnimationElement(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(this::handleClick);

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> drawBands());
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> drawBands());
    }

    private void handleClick(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        MusicBand bandUnderMouse = null;
        // Итерируемся по коллекции для поиска объекта под курсором
        for (Map.Entry<Long, MusicBand> entry : bands.entrySet()) {
            MusicBand band = entry.getValue();
            if (band.getCoordinates() == null) continue;

            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            double centerX = canvasWidth / 2;
            double centerY = canvasHeight / 2;

            Long xCoord = band.getCoordinates().getX();
            Integer yCoord = band.getCoordinates().getY();

            if (xCoord == null || yCoord == null) continue;

            // Преобразуем координаты объекта к экранным координатам Canvas
            double objectX = centerX + xCoord;
            double objectY = centerY - yCoord;
            double size = calculateSize(band);

            // Простая проверка попадания в область звезды
            if (isPointInStar(mouseX, mouseY, objectX, objectY, size)) {
                bandUnderMouse = band;
                break; // Нашли объект, выходим из цикла
            }
        }

        if (bandUnderMouse != null) {
            // Если кликнули по объекту, который уже был кликнут, сбрасываем выделение
            if (clickedBand != null && clickedBand.getId() == bandUnderMouse.getId()) {
                this.clickedBand = null;
                drawBands(); // Перерисовываем, чтобы убрать выделение
            } else {
                this.clickedBand = bandUnderMouse;
                // Запускаем анимацию трансформации для кликнутого объекта
                animateBandAppearance(bandUnderMouse);
                // Показываем информацию об объекте
                showBandInfo(bandUnderMouse);
            }
        } else {
            // Если кликнули вне объектов, сбрасываем выделение
            this.clickedBand = null;
            drawBands(); // Перерисовываем, чтобы убрать выделение
        }
    }

    // Простая проверка попадания точки в звезду (приблизительная)
    private boolean isPointInStar(double px, double py, double centerX, double centerY, double size) {
        double radius = size; // Внешний радиус звезды
        double distanceSquared = Math.pow(px - centerX, 2) + Math.pow(py - centerY, 2);
        return distanceSquared <= Math.pow(radius, 2);
    }

    // Метод для обновления списка групп и перерисовки Canvas
    public void updateBands(Collection<MusicBand> newBands) {
        // Очищаем старые анимации, если объекты были удалены
        clearInactiveAnimations(newBands);

        // Обновляем данные
        bands.clear();
        newBands.forEach(band -> {
            bands.put(band.getId(), band);
            // Запускаем анимацию появления для новых или измененных объектов
            // Если для объекта еще нет активной анимации, запускаем ее
            if (!activeAnimations.containsKey(band.getId())) {
                animateBandAppearance(band);
            }
        });
        // Перерисовываем весь Canvas
        Platform.runLater(this::drawBands);
    }

    // Очистка анимаций для удаленных объектов
    private void clearInactiveAnimations(Collection<MusicBand> currentBands) {
        activeAnimations.entrySet().removeIf(entry -> {
            if (!bands.containsKey(entry.getKey())) {
                // Если анимация активна, но объекта уже нет, останавливаем ее
                entry.getValue().stop();
                return true; // Удаляем из мапы
            }
            return false;
        });
    }


    // Метод для рисования всех групп на Canvas
    private void drawBands() {
        // Очистка Canvas
        gc.setFill(Color.WHITE); // Устанавливаем фон Canvas
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (bands.isEmpty()) {
            return;
        }

        // Рисуем оси координат
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(1.0);
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        gc.strokeLine(0, centerY, canvas.getWidth(), centerY); // Ось X
        gc.strokeLine(centerX, 0, centerX, canvas.getHeight()); // Ось Y

        // Рисуем каждую группу
        bands.values().forEach(band -> {
            // Получаем цвет пользователя (для примера, фиксированный цвет для "admin")
            String username = "admin"; // В реальной системе: band.getOwnerUsername()
            Color color = userColors.computeIfAbsent(username,
                    k -> Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            // Если объект кликнут, меняем цвет на выделенный
            if (band.equals(clickedBand)) {
                color = Color.RED; // Выделенный цвет
            }

            drawBand(band, color);
        });
    }

    // Метод для отрисовки отдельной группы
    private void drawBand(MusicBand band, Color color) {
        if (band.getCoordinates() == null) {
            System.out.println("У группы " + band.getName() + " отсутствуют координаты");
            return;
        }

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        double centerX = canvasWidth / 2;
        double centerY = canvasHeight / 2;

        Long xCoord = band.getCoordinates().getX();
        Integer yCoord = band.getCoordinates().getY();

        if (xCoord == null || yCoord == null) {
            System.out.println("У группы " + band.getName() + " неполные координаты");
            return;
        }

        // Преобразуем координаты группы в координаты на Canvas
        double x = centerX + xCoord;
        double y = centerY - yCoord; // Ось Y направлена вверх

        double size = calculateSize(band);

        // Рисуем объект
        gc.setFill(color);
        drawStar(gc, x, y, size);

        // Подпись
        gc.setFill(Color.BLACK);
        gc.fillText(band.getName(), x + size, y - size/2);
    }

    // Расчет размера звезды на основе количества участников
    private double calculateSize(MusicBand band) {
        double calculatedSize = 10 + band.getNumberOfParticipants() / 10.0;
        return Math.min(50.0, Math.max(10.0, calculatedSize)); // Ограничиваем размер от 10 до 50
    }

    // Отрисовка звезды
    private void drawStar(GraphicsContext gc, double centerX, double centerY, double size) {
        int spikes = 5;
        double outerRadius = size;
        double innerRadius = size / 2.0;

        gc.beginPath();

        for (int i = 0; i < spikes; i++) {
            double angle1 = 2 * Math.PI * i / spikes - Math.PI/2; // Первая точка сверху
            double x1 = centerX + Math.cos(angle1) * outerRadius;
            double y1 = centerY + Math.sin(angle1) * outerRadius;

            if (i == 0) {
                gc.moveTo(x1, y1);
            } else {
                gc.lineTo(x1, y1);
            }

            double angle2 = 2 * Math.PI * (i + 0.5) / spikes - Math.PI/2;
            double x2 = centerX + Math.cos(angle2) * innerRadius;
            double y2 = centerY + Math.sin(angle2) * innerRadius;
            gc.lineTo(x2, y2);
        }

        gc.closePath();
        gc.fill();
    }

    // Метод для создания и запуска анимации появления объекта
    private void animateBandAppearance(MusicBand band) {
        if (band.getCoordinates() == null) return;

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double centerX = canvasWidth / 2;
        double centerY = canvasHeight / 2;
        double x = centerX + band.getCoordinates().getX();
        double y = centerY - band.getCoordinates().getY();
        double size = calculateSize(band);

        // Создаем временный холст для анимации
        Canvas tempCanvas = new Canvas(canvas.getWidth(), canvas.getHeight());
        GraphicsContext tempGc = tempCanvas.getGraphicsContext2D();

        // Добавляем временный холст в родительский контейнер
        StackPane parent = (StackPane) canvas.getParent();
        if (parent == null) {
            System.err.println("Ошибка: Родительский элемент для Canvas не найден. Невозможно выполнить анимацию.");
            return;
        }
        // Добавляем временный холст поверх основного канваса, чтобы он рисовался поверх
        parent.getChildren().add(tempCanvas);

        // Начальное состояние: звезда, маленькая и полупрозрачная
        tempGc.setFill(Color.ORANGE); // Цвет для анимации
        drawStar(tempGc, x, y, size * 0.3); // Начинаем с маленького размера (30%)
        tempCanvas.setOpacity(0.0); // Начинаем полностью прозрачным

        // Анимация масштабирования (от маленького до нормального размера)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.7), tempCanvas);
        scaleTransition.setFromX(0.3);
        scaleTransition.setFromY(0.3);
        scaleTransition.setToX(1.0); // Нормальный размер
        scaleTransition.setToY(1.0);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT); // Плавное завершение

        // Анимация появления (прозрачность от 0 до 1)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.7), tempCanvas);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setInterpolator(Interpolator.EASE_IN); // Плавное появление

        // Объединяем анимации
        ParallelTransition animation = new ParallelTransition(scaleTransition, fadeTransition);

        // Обработчик завершения анимации
        animation.setOnFinished(e -> {
            // Убираем временный холст
            parent.getChildren().remove(tempCanvas);
            // Перерисовываем основной Canvas, чтобы объект отрисовался в своем финальном состоянии
            // (с возможностью дальнейшего выделения при клике)
            drawBands();
        });

        // Сохраняем анимацию для возможности ее остановки, если объект будет удален
        activeAnimations.put(band.getId(), animation);
        animation.play();
    }

    // Метод для показа информации об объекте при клике
    private void showBandInfo(MusicBand band) {
        // Вызов InfoAlert происходит в UI-потоке, т.к. handleClick вызывается из MouseEvent
        InfoAlert.showInfoAlert(
                "Информация о группе",
                "Данные о группе: " + band.getName(),
                band.toString()
                // true // если у вас есть параметр закрытия, можете добавить
        );
    }
}