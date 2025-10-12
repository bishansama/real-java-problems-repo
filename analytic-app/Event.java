public class Event {
    private final long timestamp;
    private final EventType type;
    private final double value;
    private final String payload;

    public Event(long timestamp, EventType type, double value, String payload) {
        this.timestamp = timestamp;
        this.type = type;
        this.value = value;
        this.payload = payload;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public EventType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Event{" +
                "timestamp=" + timestamp +
                ", type=" + type +
                ", value=" + value +
                ", payload='" + payload + '\'' +
                '}';
    }
}