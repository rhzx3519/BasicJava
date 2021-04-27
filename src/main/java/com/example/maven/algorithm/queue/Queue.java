package com.example.maven.algorithm.queue;

public interface Queue {
    void enqueue(Action action);
    Action dequeue();
    boolean empty();
    int size();
}
