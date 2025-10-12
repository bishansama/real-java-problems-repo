import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class AnalyticsDemo {
    public static void main(String[] args) {
        int userCount = 1000;
        int sessionsPerUser = 10; // 1000 * 10 = 10,000 sessions
        int minEventsPerSession = 5;
        int maxEventsPerSession = 20;

        List<User> users = generateUsers(userCount, sessionsPerUser, minEventsPerSession, maxEventsPerSession);

        // Using flatMap + filter to prune early and process API events of interest
        long startStream = System.nanoTime();
        List<Event> apiEventsStream = users.stream()
                .filter(User::isActive) // prune inactive users early
                .flatMap(u -> u.getSessions().stream())
                .filter(Session::isActive) // prune inactive sessions early
                .flatMap(s -> s.getEvents().stream())
                .filter(e -> e.getType() == EventType.API_CALL)
                .filter(e -> e.getValue() > 0.5) // keep only high-value API calls
                .collect(Collectors.toList());
        long durationStreamMs = (System.nanoTime() - startStream) / 1_000_000;

        // Baseline: traditional nested loops
        long startLoop = System.nanoTime();
        List<Event> apiEventsLoop = new ArrayList<>();
        for (User u : users) {
            if (u == null || !u.isActive()) continue;
            for (Session s : u.getSessions()) {
                if (s == null || !s.isActive()) continue;
                for (Event e : s.getEvents()) {
                    if (e != null && e.getType() == EventType.API_CALL && e.getValue() > 0.5) {
                        apiEventsLoop.add(e);
                    }
                }
            }
        }
        long durationLoopMs = (System.nanoTime() - startLoop) / 1_000_000;

        System.out.println("Processed 10,000 sessions across " + userCount + " users.");
        System.out.println("flatMap + filter: " + apiEventsStream.size() + " API events, took " + durationStreamMs + " ms");
        System.out.println("nested loops:     " + apiEventsLoop.size() + " API events, took " + durationLoopMs + " ms");

        // Sanity check: both approaches should yield same results
        System.out.println("Results equal: " + (apiEventsStream.size() == apiEventsLoop.size()));
    }

    private static List<User> generateUsers(int userCount, int sessionsPerUser, int minEventsPerSession, int maxEventsPerSession) {
        Random rnd = new Random(42); // deterministic for repeatable runs
        List<User> users = new ArrayList<>(userCount);
        for (int i = 0; i < userCount; i++) {
            boolean userActive = rnd.nextDouble() > 0.1; // ~90% active
            List<Session> sessions = new ArrayList<>(sessionsPerUser);
            for (int s = 0; s < sessionsPerUser; s++) {
                boolean sessionActive = rnd.nextDouble() > 0.2; // ~80% active
                int eventsCount = ThreadLocalRandom.current().nextInt(minEventsPerSession, maxEventsPerSession + 1);
                List<Event> events = new ArrayList<>(eventsCount);
                for (int e = 0; e < eventsCount; e++) {
                    EventType type = randomEventType(rnd);
                    double value = rnd.nextDouble();
                    long ts = System.currentTimeMillis() - rnd.nextInt(1_000_000);
                    String payload = type == EventType.API_CALL ? "api:/v1/resource" : "ui:" + type.name().toLowerCase();
                    events.add(new Event(ts, type, value, payload));
                }
                sessions.add(new Session("S-" + i + "-" + s, "U-" + i, sessionActive, events));
            }
            users.add(new User("U-" + i, "User" + i, userActive, sessions));
        }
        return users;
    }

    private static EventType randomEventType(Random rnd) {
        // Bias towards non-API events to simulate typical UI-heavy traffic
        List<EventType> pool = Arrays.asList(
                EventType.PAGE_VIEW,
                EventType.CLICK,
                EventType.LOGIN,
                EventType.LOGOUT,
                EventType.PURCHASE,
                EventType.API_CALL
        );
        return pool.get(rnd.nextInt(pool.size()));
    }
}