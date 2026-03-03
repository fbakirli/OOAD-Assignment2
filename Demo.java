public class Demo {

    public static void main(String[] args) throws InterruptedException {

        RingBuffer<String> ringBuffer = new RingBuffer<>(5);

        Reader<String> reader1 = ringBuffer.createReader();
        Reader<String> reader2 = ringBuffer.createReader();

        Thread writerThread = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                ringBuffer.write("Message " + i);
                System.out.println("Written: Message " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            }
        });

        Thread readerThread1 = new Thread(() -> {
            while (true) {
                try {
                    String data = reader1.read();
                    System.out.println("Reader1 read: " + data);
                    Thread.sleep(300);
                } catch (Exception ignored) {}
            }
        });

        Thread readerThread2 = new Thread(() -> {
            while (true) {
                try {
                    String data = reader2.read();
                    System.out.println("Reader2 read: " + data);
                    Thread.sleep(700);
                } catch (Exception ignored) {}
            }
        });

        writerThread.start();
        readerThread1.start();
        readerThread2.start();
    }
}