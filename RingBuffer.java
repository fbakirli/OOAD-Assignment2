/*
 * Name: Firangiz Bakirli
 * Project: OOAD Assignment 2 — Ring Buffer (Multiple Readers, Single Writer)
 * Course: OOAD Spring 2026
 */

public class RingBuffer<T> {

    private final Object[] buffer;
    private final int capacity;
    private int writeIndex = 0;
    private long totalWritten = 0;

    public RingBuffer(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive, got: " + capacity);
        this.capacity = capacity;
        this.buffer = new Object[capacity];
    }

    void write(T item) {
        if (item == null)
            throw new IllegalArgumentException("Null items are not allowed.");
        buffer[writeIndex] = item;
        writeIndex = (writeIndex + 1) % capacity;
        totalWritten++;
    }

    @SuppressWarnings("unchecked")
    T read(Reader<T> reader) {
        if (reader.totalRead >= totalWritten)
            throw new BufferEmptyException("Nothing to read for reader: " + reader.name);

        long available = totalWritten - reader.totalRead;
        if (available > capacity) {
            long missed = available - capacity;
            reader.totalRead += missed;
            reader.readIndex = writeIndex;
            throw new SlowReaderException("Reader '" + reader.name + "' missed " + missed + " item(s).");
        }

        T item = (T) buffer[reader.readIndex];
        reader.readIndex = (reader.readIndex + 1) % capacity;
        reader.totalRead++;
        return item;
    }

    int getWriteIndex() { return writeIndex; }
    long getTotalWritten() { return totalWritten; }
    public int getCapacity() { return capacity; }

    public Writer<T> createWriter() { return new Writer<>(this); }

    public Reader<T> createReader(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Reader name can't be null or blank.");
        return new Reader<>(this, name);
    }
}