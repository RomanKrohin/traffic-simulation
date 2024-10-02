import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class TrafficLight {
    private final String id;
    private final Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();
    private final int maxQueueLength;
    private int straightQueue;  // Очередь для движения прямо
    private int rightTurnQueue; // Очередь для поворота направо
    private String state; // "green", "yellow", "red"
    public final boolean isPedestrian;

    public TrafficLight(String id, boolean isPedestrian, int maxQueueLength) {
        this.id = id;
        this.isPedestrian = isPedestrian;
        this.state = isPedestrian ? "red" : "green";
        this.maxQueueLength = maxQueueLength;
        this.straightQueue = 0;
        this.rightTurnQueue = 0;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void changeState(String newState) {
        System.out.println("Traffic light " + id + " changing state from " + state + " to " + newState);
        state = newState;
    }

    // Метод для обновления очередей в зависимости от направления
    public void detectQueue(int count, Direction direction) {
        if (direction == Direction.STRAIGHT) {
            straightQueue = Math.min(count, maxQueueLength);
            System.out.println("Traffic light " + id + " detected " + straightQueue + " cars in straight queue");
        } else if (direction == Direction.RIGHT) {
            rightTurnQueue = Math.min(count, maxQueueLength);
            System.out.println("Traffic light " + id + " detected " + rightTurnQueue + " cars in right turn queue");
        }
        // Отправляем событие о состоянии очередей
        Event event = new Event(id, direction == Direction.STRAIGHT ? straightQueue : rightTurnQueue, state, direction);
        sendEvent(event);
    }

    public void processEvents() {
        Event event;
        while ((event = eventQueue.poll()) != null) {
            System.out.println("Traffic light " + id + " processing event from " + event.getSourceId());
            adaptStateBasedOnEvent(event);
        }
    }

    // Адаптация состояния светофора в зависимости от события
    private void adaptStateBasedOnEvent(Event event) {
        if (event.getQueueSize() > 0 && state.equals("red")) {
            changeState("green");
        } else if (event.getQueueSize() == 0 && state.equals("green")) {
            changeState("red");
        }
    }
    

    public void receiveEvent(Event event) {
        System.out.println("Traffic light " + id + " received event: " + event);
        eventQueue.add(event);
    }

    public void sendEvent(Event event) {
        for (TrafficLight otherLight : Intersection.getAllTrafficLights()) {
            if (!otherLight.getId().equals(id) && this.isPedestrian == otherLight.isPedestrian) {
                otherLight.receiveEvent(event);
            }
        }
    }
}

enum ObjectType {
    VEHICLE, PEDESTRIAN
}

enum TrafficLightState {
    RED, YELLOW, GREEN
}

enum Direction {
    STRAIGHT, RIGHT
}
