import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Intersection {
    private static final List<TrafficLight> trafficLights = new ArrayList<>();

    public static void addTrafficLight(TrafficLight light) {
        trafficLights.add(light);
    }

    public static List<TrafficLight> getAllTrafficLights() {
        return trafficLights;
    }

    public static void simulate() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            System.out.println("Simulating traffic lights...");
            for (TrafficLight light : trafficLights) {
                light.processEvents();
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    // Метод для случайной генерации очередей
    public static void randomizeQueues() {
        Random random = new Random();
        for (TrafficLight light : trafficLights) {
            if (!light.isPedestrian) {
                // Для машин случайно определяем, прямо они едут или направо
                Direction randomDirection = random.nextBoolean() ? Direction.STRAIGHT : Direction.RIGHT;
                int detectedQueue = random.nextInt(10); // до 10 машин в очереди
                light.detectQueue(detectedQueue, randomDirection);
            } else {
                // Для пешеходов
                int detectedQueue = random.nextInt(5); // до 5 человек в очереди
                light.detectQueue(detectedQueue, Direction.STRAIGHT);
            }
        }
    }
}
