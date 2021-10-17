import org.junit.jupiter.api.Test;

import java.util.*;

public class Appearances {
	@Test
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		var count = 0;
		var aElemCounts = new HashMap<T, Integer>();

		for (var elem : a)
			aElemCounts.put(elem, aElemCounts.get(elem) == null ? 1 : aElemCounts.get(elem) + 1);
		for (var elem : b)
			aElemCounts.put(elem, aElemCounts.get(elem) == null ? -1 : aElemCounts.get(elem) - 1);

		for (var key : aElemCounts.keySet())
			if (aElemCounts.get(key) == 0)
				count++;

		return count;
	}
}
