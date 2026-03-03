/*
 * Name: Firangiz Bakirli
 * Project: OOAD Assignment 2 — Ring Buffer (Multiple Readers, Single Writer)
 * Course: OOAD Spring 2026
 */

public class Main {

    public static void main(String[] args) {

        // basic write and read
        System.out.println("-- Basic write/read");
        RingBuffer<Integer> buf = new RingBuffer<>(5);
        Writer<Integer> writer = buf.createWriter();
        Reader<Integer> reader = buf.createReader("R1");

        writer.write(10);
        writer.write(20);
        writer.write(30);

        System.out.println(reader.read()); 
        System.out.println(reader.read()); 
        System.out.println(reader.read()); 

        // multiple independent readers
        System.out.println("\n-- Multiple readers");
        RingBuffer<String> buf2 = new RingBuffer<>(10);
        Writer<String> writer2 = buf2.createWriter();
        Reader<String> kamila = buf2.createReader("Kamila");
        Reader<String> fira = buf2.createReader("Firangiz");

        writer2.write("hello");
        writer2.write("world");

        System.out.println("Kamila: " + kamila.read() + ", " + kamila.read());
        System.out.println("Firangiz:   " + fira.read()   + ", " + fira.read());

        // buffer empty
        System.out.println("\n-- BufferEmptyException");
        try {
            reader.read();
        } catch (BufferEmptyException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // slow reader
        System.out.println("\n-- SlowReaderException");
        RingBuffer<Integer> buf3 = new RingBuffer<>(3);
        Writer<Integer> writer3 = buf3.createWriter();
        Reader<Integer> slow = buf3.createReader("Ilbiz");

        for (int i = 1; i <= 5; i++) writer3.write(i);

        try {
            slow.read();
        } catch (SlowReaderException e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Resumed, got: " + slow.read());
        }

        // readAll
        System.out.println("\n-- readAll");
        RingBuffer<String> buf4 = new RingBuffer<>(10);
        Writer<String> writer4 = buf4.createWriter();
        Reader<String> reader4 = buf4.createReader("R1");

        writer4.write("a");
        writer4.write("b");
        writer4.write("c");

        System.out.println(reader4.readAll()); 
        System.out.println(reader4.readAll()); 

        // invalid inputs
        System.out.println("\n-- Invalid inputs");
        try { writer.write(null); } catch (IllegalArgumentException e) { System.out.println("Caught: " + e.getMessage()); }
        try { new RingBuffer<>(0); } catch (IllegalArgumentException e) { System.out.println("Caught: " + e.getMessage()); }
        try { buf.createReader(null); } catch (IllegalArgumentException e) { System.out.println("Caught: " + e.getMessage()); }
    }
}