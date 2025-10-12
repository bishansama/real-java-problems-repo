import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelFlatMapDemo {
    public static void main(String[] args) {
        int size = args.length > 0 ? Integer.parseInt(args[0]) : 200_000; // tuneable up to 1_000_000

        List<Integer> ids = IntStream.rangeClosed(1, size).boxed().collect(Collectors.toList());

        // Optional-based pipeline: sequential
        long t1 = System.nanoTime();
        long countOptSeq = ids.stream()
                .map(ApiClient::fetchOptional)
                .flatMap(Optional::stream)
                .filter(r -> r.getPayload().contains("api"))
                .count();
        long d1 = (System.nanoTime() - t1) / 1_000_000;

        // Optional-based pipeline: parallel
        long t2 = System.nanoTime();
        long countOptPar = ids.parallelStream()
                .map(ApiClient::fetchOptional)
                .flatMap(Optional::stream)
                .filter(r -> r.getPayload().contains("api"))
                .count();
        long d2 = (System.nanoTime() - t2) / 1_000_000;

        // Try-based pipeline: sequential (flatMap composition)
        long t3 = System.nanoTime();
        long countTrySeq = ids.stream()
                .map(ApiClient::fetchTry)
                .flatMap(tr -> tr.toOptional().stream())
                .filter(r -> r.getPayload().contains("api"))
                .count();
        long d3 = (System.nanoTime() - t3) / 1_000_000;

        // Try-based pipeline: parallel
        long t4 = System.nanoTime();
        long countTryPar = ids.parallelStream()
                .map(ApiClient::fetchTry)
                .flatMap(tr -> tr.toOptional().stream())
                .filter(r -> r.getPayload().contains("api"))
                .count();
        long d4 = (System.nanoTime() - t4) / 1_000_000;

        System.out.println("Parallel flatMap demo (size=" + size + ")");
        System.out.println("Optional  seq: count=" + countOptSeq + ", time=" + d1 + " ms");
        System.out.println("Optional  par: count=" + countOptPar + ", time=" + d2 + " ms");
        System.out.println("Try       seq: count=" + countTrySeq + ", time=" + d3 + " ms");
        System.out.println("Try       par: count=" + countTryPar + ", time=" + d4 + " ms");
        System.out.println("Counts equal (Optional)? " + (countOptSeq == countOptPar));
        System.out.println("Counts equal (Try)? " + (countTrySeq == countTryPar));

        System.out.println("Note: Parallel improves throughput for large datasets; mind order sensitivity. " +
                "Use forEachOrdered or collect to preserve encounter order when needed.");
    }
}