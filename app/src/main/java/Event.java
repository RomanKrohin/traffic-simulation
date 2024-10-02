class Event {
    private final String sourceId;
    private final int queueSize;
    private final String trafficLightState;
    private final Direction direction; // Добавлено направление движения

    public Event(String sourceId, int queueSize, String trafficLightState, Direction direction) {
        this.sourceId = sourceId;
        this.queueSize = queueSize;
        this.trafficLightState = trafficLightState;
        this.direction = direction;
    }

    public String getSourceId() {
        return sourceId;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Event from " + sourceId + " with queueSize=" + queueSize + ", state=" + trafficLightState + ", direction=" + direction;
    }
}
