import java.util.Optional;
import java.util.function.Function;

public final class Try<T> {
    private final T value;
    private final Throwable error;

    private Try(T value, Throwable error) {
        this.value = value;
        this.error = error;
    }

    public static <T> Try<T> success(T value) {
        return new Try<>(value, null);
    }

    public static <T> Try<T> failure(Throwable error) {
        return new Try<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isFailure() {
        return error != null;
    }

    public T get() {
        if (isFailure()) throw new RuntimeException(error);
        return value;
    }

    public Optional<T> toOptional() {
        return isSuccess() ? Optional.ofNullable(value) : Optional.empty();
    }

    public <U> Try<U> map(Function<T, U> mapper) {
        if (isFailure()) return failure(error);
        try {
            return success(mapper.apply(value));
        } catch (Throwable t) {
            return failure(t);
        }
    }

    public <U> Try<U> flatMap(Function<T, Try<U>> mapper) {
        if (isFailure()) return failure(error);
        try {
            return mapper.apply(value);
        } catch (Throwable t) {
            return failure(t);
        }
    }

    public T getOrElse(T fallback) {
        return isSuccess() ? value : fallback;
    }

    public Try<T> recover(Function<Throwable, T> handler) {
        if (isSuccess()) return this;
        try {
            return success(handler.apply(error));
        } catch (Throwable t) {
            return failure(t);
        }
    }
}