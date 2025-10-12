import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ApiClient {
    // Simulate an API response wrapped in Optional (missing data without nulls)
    public static Optional<Record> fetchOptional(int id) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double p = rnd.nextDouble();
        if (p < 0.05) { // ~5% missing
            return Optional.empty();
        }
        // Simulate lightweight CPU work (e.g., parsing)
        String payload = computePayload(id);
        return Optional.of(new Record(id, payload));
    }

    // Simulate an API response wrapped in Try (exceptions handled gracefully)
    public static Try<Record> fetchTry(int id) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        double p = rnd.nextDouble();
        if (p < 0.1) { // ~10% failures
            return Try.failure(new RuntimeException("Upstream error for id=" + id));
        }
        // Simulate lightweight CPU work
        String payload = computePayload(id);
        return Try.success(new Record(id, payload));
    }

    private static String computePayload(int id) {
        // Synthetic payload; include 'api' sometimes for filtering
        String base = (id % 3 == 0) ? "api:/v1/items" : "ui:/page";
        // Cheap CPU work; avoid sleeps to keep demo fast
        int hash = (base.hashCode() * 31) ^ id;
        return base + "#" + Integer.toHexString(hash);
    }
}