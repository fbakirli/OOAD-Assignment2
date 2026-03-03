## Project Overview

This project implements a thread-safe Ring Buffer supporting:

    Single writer
    Multiple independent readers
    Overwrite of oldest data when full
    Independent reader cursors
    Slow readers may miss data if overwritten.

## Class Responsibilities

### RingBuffer

    Manages storage

    Controls concurrency

    Handles overwrite logic

    Creates readers

### Reader

    Maintains independent reading position

    Reads without affecting others

    Handles missed data safely

### RingBufferException

    Custom exception handling

## UML Class Diagram


## Sequence Diagram — write()

## Sequence Diagram — read()