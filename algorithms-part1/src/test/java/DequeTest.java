import edu.princeton.cs.algs4.StdOut;
import org.junit.jupiter.api.Test;
import queues.Deque;

import java.util.Iterator;

public class DequeTest {

    @Test
    void shouldCreateIterator() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("A");
        deque.removeLast();
        Iterator<String> iterator = deque.iterator();
        while(iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }
}
