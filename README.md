# Ring Buffer — Multiple Readers, Single Writer

**Course:** Object-Oriented Analysis and Design — Spring 2026  
**Assignment:** 2  
**Author:** Firangiz Bakirli

---

## Overview

A generic ring buffer that supports one writer and multiple independent readers. Each reader tracks its own position, so one reader consuming items doesn't affect the others. When the buffer is full, the writer overwrites the oldest slot — readers that fall too far behind will get a `SlowReaderException` and are automatically fast-forwarded to the oldest available item.

---

## Class Responsibilities

**`RingBuffer<T>`** — owns the backing array and manages the write pointer. Creates `Writer` and `Reader` instances.

**`Writer<T>`** — the only class that can write into the buffer.

**`Reader<T>`** — reads independently from the buffer. Each instance tracks its own `readIndex` and `totalRead`, so multiple readers never interfere with each other.

**`BufferEmptyException`** — thrown when a reader has nothing new to read.

**`SlowReaderException`** — thrown when a reader fell so far behind that its slot was overwritten. The reader is fast-forwarded automatically so it can keep reading.

**`BufferFullException`** — reserved for a future strict no-overwrite mode.

**`Main`** — runs through all the scenarios: basic read/write, multiple independent readers, and edge cases like reading from an empty buffer or a slow reader getting lapped by the writer.

---

## Test Cases

`Main.java` runs through these scenarios in order:

- **Basic write/read** — writes 3 items and reads them back in order
- **Multiple readers** — two readers read the same items independently without affecting each other
- **BufferEmptyException** — reader tries to read when there's nothing left
- **SlowReaderException** — writer fills and overflows a small buffer, slow reader gets fast-forwarded
- **readAll()** — drains all available items at once, then verifies the buffer is empty
- **Invalid inputs** — null item, zero capacity, and null reader name all throw exceptions

## How to Run

```bash
javac *.java
java Main
```

---

## Class Diagram

<img width="681" height="435" alt="Screenshot 2026-03-03 at 22 35 05" src="https://github.com/user-attachments/assets/84b46562-3b3d-4511-a190-ed12d00836ca" />

## Sequence Diagram for write()

<img width="582" height="617" alt="Screenshot 2026-03-03 at 22 46 16" src="https://github.com/user-attachments/assets/6586fd55-9c3c-42a6-b173-a479785fc560" />

## Sequence Diagram for read()

<img width="578" height="633" alt="Screenshot 2026-03-03 at 22 46 53" src="https://github.com/user-attachments/assets/b7ac4442-a271-49c7-b937-49489d6367f0" />

