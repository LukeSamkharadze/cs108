import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCodeTest {

	@Test
	public void testBlowup1() {
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}

	@Test
	public void testBlowup2() {
		assertEquals("axxx", StringCode.blowup("a2x3"));
		assertEquals("a33111", StringCode.blowup("a231"));
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}

	@Test
	public void testBlowup3() {
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}

	@Test
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}

	@Test
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}

	@Test
	public void testRun3() {
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}

	@Test
	public void testIntersect1() {
		assertTrue(StringCode.stringIntersect("a", "a", 1));
		assertTrue(StringCode.stringIntersect("ab", "a", 1));
		assertTrue(StringCode.stringIntersect("a", "ab", 1));
	}

	@Test
	public void testIntersect2() {
		assertTrue(StringCode.stringIntersect("abc", "bcaa", 2));
		assertTrue(StringCode.stringIntersect("bcaa", "abc", 2));
	}

	@Test
	public void testIntersect3() {
		assertFalse(StringCode.stringIntersect("bcaa", "abc", 3));
		assertFalse(StringCode.stringIntersect("xixi", "zdarova", 2));
	}
}
