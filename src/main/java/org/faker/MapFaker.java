package org.faker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

public class MapFaker implements Map<String, Object> {

	public static MapFaker create(UnaryOperator<MapFaker> value) {
		MapFaker map = new MapFaker();
		return value.apply(map);
	}

	private Map<String, Object> base;
	private Faker faker;
	private Supplier<Map<String, Object>> constructor;

	public MapFaker() {
		this(HashMap<String, Object>::new);
	}

	public MapFaker(Supplier<Map<String, Object>> constructor) {
		this(constructor, Locale.JAPANESE);
	}

	public MapFaker(Supplier<Map<String, Object>> constructor, Locale locale) {
		this(constructor, new Faker(locale));
	}

	public MapFaker(Supplier<Map<String, Object>> constructor, Faker faker) {
		this.constructor = constructor;
		this.base = constructor.get();
		this.faker = faker;
	}

	@Override
	public int size() {
		return base.size();
	}

	@Override
	public boolean isEmpty() {
		return base.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return base.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return base.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return base.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return base.put(key, expression(value));
	}

	@Override
	public Object remove(Object key) {
		return base.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		base.putAll(m);
	}

	@Override
	public void clear() {
		base.clear();
	}

	@Override
	public Set<String> keySet() {
		return base.keySet();
	}

	@Override
	public Collection<Object> values() {
		return base.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return base.entrySet();
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(base);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	public Object put(String key, UnaryOperator<MapFaker> value) {
		MapFaker map = new MapFaker(constructor);
		return put(key, value.apply(map));
	}

	private Object expression(Object e) {
		if (e instanceof String) {
			String exp = (String) e;
			return faker.expression(exp);
		}
		return e;
	}
}
