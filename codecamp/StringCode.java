import java.util.HashSet;

public class StringCode {
	public static int maxRun(String str) {
		if (str.isEmpty())
			return 0;

		int max = 0, curr = 1;

		for (var i = 0; i < str.length() - 1; i++) {
			max = Math.max(max, curr);
			if (str.charAt(i) == str.charAt(i + 1)) curr++;
			else curr = 1;
		}

		max = Math.max(max, curr);
		return max;
	}

	public static String blowup(String str) {
		StringBuilder result = new StringBuilder();

		for (var i = 0; i < str.length(); i++)
			if (!Character.isDigit(str.charAt(i)))
				result.append(str.charAt(i));
			else if (Character.isDigit(str.charAt(i)) && i + 1 < str.length())
				result.append(String.valueOf(str.charAt(i + 1)).repeat(Integer.parseInt("" + str.charAt(i))));

		return result.toString();
	}

	public static boolean stringIntersect(String a, String b, int len) {
		var aSubs = new HashSet<String>();

		for (var i = 0; i <= a.length() - len; i++)
			aSubs.add(a.substring(i, i + len));
		for (var i = 0; i <= b.length() - len; i++)
			if (aSubs.contains(b.substring(i, i + len)))
				return true;

		return false;
	}
}
