public class Reader<T> {

    private final RingBuffer<T> buffer;
    private long readSequence;

    protected Reader(RingBuffer<T> buffer, long startSequence) {
        this.buffer = buffer;
        this.readSequence = startSequence;
    }

    public T read() {
        try {
            T data = buffer.read(readSequence);
            readSequence++;
            return data;
        } catch (RingBufferException ex) {
            handleMissedData();
            throw ex;
        }
    }

    private void handleMissedData() {
        long currentGlobal = buffer.getGlobalSequence();
        long minValid = Math.max(0, currentGlobal - buffer.getCapacity());
        readSequence = minValid;
    }

    public long getReadSequence() {
        return readSequence;
    }
}