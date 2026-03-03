import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RingBuffer<T> {

    private final Object[] buffer;
    private final int capacity;

    private long globalSequence = 0;  // total number of writes
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    private final Set<Reader<T>> readers = new HashSet<>();

    public RingBuffer(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

    // -----------------------------
    // WRITE METHOD (Single Writer)
    // -----------------------------
    public void write(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot write null value.");
        }

        lock.writeLock().lock();
        try {
            long index = globalSequence % capacity;
            buffer[(int) index] = item;
            globalSequence++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    // -----------------------------
    // READER REGISTRATION
    // -----------------------------
    public Reader<T> createReader() {
        lock.writeLock().lock();
        try {
            Reader<T> reader = new Reader<>(this, globalSequence);
            readers.add(reader);
            return reader;
        } finally {
            lock.writeLock().unlock();
        }
    }

    protected T read(long sequence) {
        lock.readLock().lock();
        try {
            if (sequence >= globalSequence) {
                throw new RingBufferException("No new data available.");
            }

            long oldestValidSequence = Math.max(0, globalSequence - capacity);

            if (sequence < oldestValidSequence) {
                throw new RingBufferException("Data has been overwritten. Reader is too slow.");
            }

            int index = (int) (sequence % capacity);
            return (T) buffer[index];
        } finally {
            lock.readLock().unlock();
        }
    }

    protected long getGlobalSequence() {
        lock.readLock().lock();
        try {
            return globalSequence;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getCapacity() {
        return capacity;
    }
}