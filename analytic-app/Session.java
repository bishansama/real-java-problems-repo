import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Session {
    private final String sessionId;
    private final String userId;
    private final boolean active;
    private final List<Event> events;

    public Session(String sessionId, String userId, boolean active, List<Event> events) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.active = active;
        this.events = events != null ? new ArrayList<>(events) : new ArrayList<>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isActive() {
        return active;
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }
}