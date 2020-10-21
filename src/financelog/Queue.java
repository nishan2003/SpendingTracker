package financelog;

public interface Queue {
    public void enqueue(Object a);
    public Object dequeue();
    public Object peekFront();
    public Boolean isEmpty();
}
