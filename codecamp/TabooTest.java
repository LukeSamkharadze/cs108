import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TabooTest {
    @Test
    public void testNoFollow1() {
        var a = Arrays.asList("a", "c", "a", "b");
        var t = new Taboo<String>(a);

        var s = new HashSet<String>(Arrays.asList("c", "b"));
        assertEquals(s, t.noFollow("a"));

        s = new HashSet<>();
        assertEquals(s, t.noFollow("x"));
        assertEquals(s, t.noFollow("b"));

        s = new HashSet<>(Collections.singletonList("a"));
        assertEquals(s, t.noFollow("c"));
    }

    @Test
    public void testReduce1() {
        var a = new ArrayList<>(Arrays.asList("a", "c", "a", "b"));
        var t = new Taboo<String>(a);

        var b = new ArrayList<>(Arrays.asList("a", "c", "b", "x", "c", "a"));
        t.reduce(b);
        assertEquals(Arrays.asList("a", "x", "c"), b);
    }
}
