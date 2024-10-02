import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Создаем 4 светофора для машин
        for (int i = 0; i < 4; i++) {
            TrafficLight carLight = new TrafficLight("Car-" + i, false, 10);
            Intersection.addTrafficLight(carLight);
        }

        // Создаем 8 светофоров для пешеходов
        for (int i = 0; i < 8; i++) {
            TrafficLight pedestrianLight = new TrafficLight("Pedestrian-" + i, true, 5);
            Intersection.addTrafficLight(pedestrianLight);
        }

        // Запускаем симуляцию
        Intersection.simulate();

        // Генерируем случайные очереди для тестирования
        Intersection.randomizeQueues();
    }
}
