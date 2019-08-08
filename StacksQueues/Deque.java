import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Item item; 
    private Node first, last = null;
    class Node {
        Item item;
        Node next;
    }

    public Deque() {
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size(){
        return 0;
    }

    public void addFirst(Item item){
        // push
        Node oldFirst = first;
        Node first = new Node();
        first.item = item;
        first.next = oldFirst;
    }

    public void addLast(Item item){
        // enque
        Node oldLast = last;
        Node last = new Node();
        last.item = item;
        last.next = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

    }

    public Item removeFirst(){
        // deque
        first.item = item;
        first = first.next;
        return item;
    }

    public Item removeLast(){
        // pop
        last.item = item;
        last = last.next;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
        public boolean hasNext() {
            return false;
        }
        public Item next(){
            return item;
        }        
    }

    public static void main(String[] args){
        // client code
        Deque<String> deck = new Deque<String>();   
        deck.addFirst("hello");
        deck.addFirst("my");
        deck.addLast("name");
        deck.addLast("Fayeez")
    }
}



