/*
 * Name: Firangiz Bakirli
 * Project: OOAD Assignment 2 — Ring Buffer (Multiple Readers, Single Writer)
 * Course: OOAD Spring 2026
 */

public class Writer<T> {

    private final RingBuffer<T> ringBuffer;

    Writer(RingBuffer<T> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void write(T item) {
        ringBuffer.write(item);
    }
}