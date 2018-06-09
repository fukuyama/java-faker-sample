package org.faker;

import org.junit.Test;

public class MapFakerTest {

	@Test
	public void testDefault() throws Exception {
		MapFaker map = MapFaker.create(o -> {
			// https://github.com/DiUS/java-faker/blob/master/README.md
			// http://dius.github.io/java-faker/apidocs/index.html
			o.put("name", "#{Name.fullName}");
			o.put("test", test -> {
				test.put("A", "#{Number.numberBetween '0','100'}");
				test.put("B", "#{Number.numberBetween '0','100'}");
				test.put("C", "#{Number.numberBetween '0','100'}");
				test.put("D", "#{Number.numberBetween '0','100'}");
				return test;
			});
			o.put("items", ListFaker.createBetween(1, 4, () -> {
				return MapFaker.create(item -> {
					item.put("str", "#{regexify '[A-Z]{9}'}");
					return item;
				});
			}));
			return o;
		});

		System.out.println(map.toJson());
	}
}
