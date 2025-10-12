import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final String id;
    private final String name;
    private final boolean active;
    private final List<Session> sessions;

    public User(String id, String name, boolean active, List<Session> sessions) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.sessions = sessions != null ? new ArrayList<>(sessions) : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<Session> getSessions() {
        return Collections.unmodifiableList(sessions);
    }
}