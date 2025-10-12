public class Record {
    private final int id;
    private final String payload;

    public Record(int id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", payload='" + payload + '\'' +
                '}';
    }
}