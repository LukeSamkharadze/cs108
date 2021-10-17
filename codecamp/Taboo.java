import java.util.*;

public class Taboo<T> {
	List<T> rules;

	public Taboo(List<T> rules) {
		this.rules = rules;
	}

	public Set<T> noFollow(T elem) {
		var set = new HashSet<T>();

		for (var i = 0; i < rules.size() - 1; i++)
			if (rules.get(i) != null && rules.get(i).equals(elem))
				set.add(rules.get(i + 1));

		return set;
	}

	public void reduce(List<T> list) {
		for (var i = 1; i < list.size(); i++)
			if (list.get(i) != null && noFollow(list.get(i - 1)).contains(list.get(i)))
				list.remove(i--);
	}
}
