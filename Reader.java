/*
 * Name: Firangiz Bakirli
 * Project: OOAD Assignment 2 — Ring Buffer (Multiple Readers, Single Writer)
 * Course: OOAD Spring 2026
 */

import java.util.ArrayList;
import java.util.List;

public class Reader<T> {

    private final RingBuffer<T> ringBuffer;
    final String name;
    int readIndex;
    long totalRead;

    Reader(RingBuffer<T> ringBuffer, String name) {
        this.ringBuffer = ringBuffer;
        this.name = name;
        this.readIndex = ringBuffer.getWriteIndex();
        this.totalRead = ringBuffer.getTotalWritten();
    }

    public T read() {
        return ringBuffer.read(this);
    }

    public List<T> readAll() {
        List<T> results = new ArrayList<>();
        while (true) {
            try {
                results.add(read());
            } catch (BufferEmptyException e) {
                break;
            }
        }
        return results;
    }

    public boolean hasNext() {
        return totalRead < ringBuffer.getTotalWritten();
    }

    public String getName() { return name; }
}