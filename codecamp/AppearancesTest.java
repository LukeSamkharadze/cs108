import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AppearancesTest {
	@Test
	private List<String> stringToList(String s) {
		var list = new ArrayList<String>();
		for (int i = 0; i < s.length(); i++)
			list.add("" + s.charAt(i));
		return list;
	}

	@Test
	public void testSameCount1() {
		var a = stringToList("abbccc");
		var b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}

	@Test
	public void testSameCount2() {
		var a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
}
