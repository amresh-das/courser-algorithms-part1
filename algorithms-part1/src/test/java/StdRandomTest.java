import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class StdRandomTest {

    @Test
    void checkRandomness() {
        int limit = 100000;
        Map<Integer, Integer> freq = new HashMap<>();
        for (int i = 0; i < limit; i++) {
            freq.compute(StdRandom.uniform(limit), (k,v) -> v == null ? 1 : v + 1);
        }
        freq.values().stream().sorted(Comparator.reverseOrder()).forEach(StdOut::println);
    }
}
