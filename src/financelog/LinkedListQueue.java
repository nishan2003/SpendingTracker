package financelog;

import java.util.LinkedList;

public class LinkedListQueue implements Queue {
    LinkedList LLQueue = new LinkedList();
    public LinkedListQueue() {
        LinkedList LLQueue = new LinkedList();
        this.LLQueue = LLQueue;
    }

    public void enqueue(Object a) {
        LLQueue.addLast(a);
    }

    public Object dequeue() {
        return LLQueue.removeFirst();
    }

    public Object peekFront() {
        return LLQueue.getFirst();
    }

    public Boolean isEmpty() {
        return LLQueue.isEmpty();
    }
}
