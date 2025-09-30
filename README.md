# Deep-Dive Java Problems

A collection of production-ready Java implementations tackling common concurrency and system design challenges. Each solution includes comprehensive edge case handling, thread safety considerations, and performance optimizations.

## 🎯 Overview

This repository contains three fundamental Java problems that are commonly encountered in production systems:

1. **Concurrent Counter** - Thread-safe counting with high performance
2. **Rate Limiter** - Request throttling and traffic control
3. **Simple Cache** - In-memory caching with eviction policies

Each implementation focuses on:
- ✅ **Thread Safety** - Proper synchronization and concurrent access handling
- ✅ **Edge Case Handling** - Comprehensive error handling and boundary conditions
- ✅ **Performance** - Optimized for high-throughput scenarios
- ✅ **Production Ready** - Includes logging, monitoring, and configuration
- ✅ **Testing** - Unit tests covering normal and edge cases

## 📁 Project Structure

```
real-java-problems-repo/
├── concurrent-counter/     # Thread-safe counter implementations
├── rate-limitter/         # Rate limiting algorithms and strategies
├── simple-cache/          # Cache implementations with eviction policies
└── README.md             # This file
```

## 🔢 Concurrent Counter

### Problem Statement
Implement a thread-safe counter that can handle high-concurrency scenarios with multiple threads incrementing, decrementing, and reading the counter value simultaneously.

### Key Features
- **Atomic Operations** - Lock-free implementations using `AtomicLong`
- **Multiple Strategies** - Compare different approaches (synchronized, locks, atomic)
- **Performance Metrics** - Benchmarking different implementations
- **Overflow Protection** - Handle integer overflow scenarios
- **Reset Functionality** - Thread-safe counter reset operations

### Edge Cases Handled
- Integer overflow/underflow
- Concurrent read/write operations
- High contention scenarios
- Memory visibility issues
- Performance under load

### Implementation Highlights
```java
// Example of atomic counter with overflow protection
public class SafeAtomicCounter {
    private final AtomicLong counter = new AtomicLong(0);
    
    public long incrementAndGet() throws ArithmeticException {
        return counter.updateAndGet(current -> {
            if (current == Long.MAX_VALUE) {
                throw new ArithmeticException("Counter overflow");
            }
            return current + 1;
        });
    }
}
```

## 🚦 Rate Limiter

### Problem Statement
Implement various rate limiting algorithms to control the rate of requests in a distributed system, preventing abuse and ensuring fair resource usage.

### Key Features
- **Multiple Algorithms** - Token Bucket, Sliding Window, Fixed Window
- **Distributed Support** - Redis-backed rate limiting for microservices
- **Configurable Limits** - Dynamic rate limit configuration
- **Burst Handling** - Allow controlled bursts within limits
- **Monitoring** - Rate limit metrics and alerting

### Edge Cases Handled
- Clock skew and time synchronization
- Distributed coordination challenges
- Memory leaks in sliding window implementations
- Race conditions in token replenishment
- Network partitions in distributed scenarios
- Configuration changes during runtime

### Implementation Highlights
```java
// Token Bucket with thread-safe token replenishment
public class TokenBucketRateLimiter {
    private final long capacity;
    private final long refillRate;
    private volatile long tokens;
    private volatile long lastRefillTime;
    
    public boolean tryAcquire(int permits) {
        refillTokens();
        if (tokens >= permits) {
            tokens -= permits;
            return true;
        }
        return false;
    }
}
```

## 💾 Simple Cache

### Problem Statement
Implement an in-memory cache with configurable eviction policies, thread safety, and performance optimizations suitable for production use.

### Key Features
- **Eviction Policies** - LRU, LFU, FIFO, TTL-based eviction
- **Thread Safety** - Concurrent read/write operations
- **Memory Management** - Configurable size limits and memory monitoring
- **Statistics** - Hit/miss ratios, eviction metrics
- **Expiration** - Time-based entry expiration

### Edge Cases Handled
- Memory pressure and OOM scenarios
- Concurrent modifications during iteration
- Clock adjustments affecting TTL
- Null key/value handling
- Cache warming and cold start scenarios
- Eviction during high load

### Implementation Highlights
```java
// LRU Cache with concurrent access support
public class ConcurrentLRUCache<K, V> {
    private final ConcurrentHashMap<K, Node<K, V>> cache;
    private final int capacity;
    private final ReentrantReadWriteLock lock;
    
    public V get(K key) {
        lock.readLock().lock();
        try {
            Node<K, V> node = cache.get(key);
            if (node != null && !isExpired(node)) {
                moveToHead(node);
                return node.value;
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }
}
```

## 🧪 Testing Strategy

Each implementation includes comprehensive testing:

- **Unit Tests** - Core functionality and edge cases
- **Concurrency Tests** - Multi-threaded scenarios
- **Performance Tests** - Benchmarking and load testing
- **Integration Tests** - End-to-end scenarios
- **Stress Tests** - High-load and resource exhaustion scenarios

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+ or Gradle 6+
- JUnit 5 for testing

### Building the Project
```bash
# Clone the repository
git clone <repository-url>
cd real-java-problems-repo

# Build all modules
mvn clean compile

# Run tests
mvn test

# Run performance benchmarks
mvn exec:java -Dexec.mainClass="benchmarks.BenchmarkRunner"
```

### Running Individual Problems
```bash
# Concurrent Counter
cd concurrent-counter
mvn exec:java -Dexec.mainClass="counter.CounterDemo"

# Rate Limiter
cd rate-limitter
mvn exec:java -Dexec.mainClass="ratelimiter.RateLimiterDemo"

# Simple Cache
cd simple-cache
mvn exec:java -Dexec.mainClass="cache.CacheDemo"
```

## 📊 Performance Considerations

### Concurrent Counter
- **Lock-free vs Lock-based** - Atomic operations vs synchronized methods
- **Memory Contention** - False sharing and cache line optimization
- **Scalability** - Performance under high thread count

### Rate Limiter
- **Algorithm Choice** - Token bucket vs sliding window trade-offs
- **Memory Usage** - Sliding window memory requirements
- **Latency** - Request processing overhead

### Simple Cache
- **Eviction Overhead** - Cost of different eviction policies
- **Memory Efficiency** - Object overhead and memory layout
- **Concurrent Access** - Read/write lock vs concurrent data structures

## 🔧 Configuration

Each implementation supports runtime configuration:

```properties
# Concurrent Counter Configuration
counter.initial.value=0
counter.overflow.protection=true
counter.metrics.enabled=true

# Rate Limiter Configuration
ratelimiter.algorithm=TOKEN_BUCKET
ratelimiter.capacity=100
ratelimiter.refill.rate=10
ratelimiter.distributed.enabled=false

# Cache Configuration
cache.eviction.policy=LRU
cache.max.size=1000
cache.ttl.seconds=3600
cache.stats.enabled=true
```

## 📈 Monitoring and Metrics

All implementations include built-in metrics:

- **Throughput** - Operations per second
- **Latency** - P50, P95, P99 response times
- **Error Rates** - Failed operations and exceptions
- **Resource Usage** - Memory and CPU utilization
- **Business Metrics** - Cache hit rates, rate limit violations

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards
- Follow Google Java Style Guide
- Include comprehensive unit tests
- Add JavaDoc for public APIs
- Ensure thread safety documentation
- Include performance benchmarks for new features

## 📚 Further Reading

- [Java Concurrency in Practice](https://www.oreilly.com/library/view/java-concurrency-in/0321349601/)
- [Effective Java (3rd Edition)](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)
- [High Performance Java Persistence](https://vladmihalcea.com/books/high-performance-java-persistence/)
- [Java Performance: The Definitive Guide](https://www.oreilly.com/library/view/java-performance-the/9781449363512/)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏷️ Tags

`java` `concurrency` `thread-safety` `performance` `production-ready` `rate-limiting` `caching` `atomic-operations` `system-design` `algorithms`