// Thrown when a reader fell behind and some items were overwritten.
// The reader is automatically fast-forwarded, so reading can resume immediately.
public class SlowReaderException extends RuntimeException {
    public SlowReaderException(String message) { super(message); }
}