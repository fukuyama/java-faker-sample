package org.faker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.github.javafaker.Faker;

public class ListFaker implements List<Object> {

	public static ListFaker create(UnaryOperator<ListFaker> value) {
		ListFaker list = new ListFaker();
		return value.apply(list);
	}

	public static Object create(Supplier<Object> value) {
		return create(items -> {
			items.add(value);
			return items;
		});
	}

	public static Object createFix(int len, Supplier<Object> value) {
		return create(items -> {
			items.addFix(len, value);
			return items;
		});
	}

	public static Object createBetween(int min, int max, Supplier<Object> value) {
		return create(items -> {
			items.addBetween(min, max, value);
			return items;
		});
	}

	public void addFix(int length, Supplier<Object> value) {
		for (int i = 0; i < length; i++) {
			add(value.get());
		}
	}

	public void addRandom(Supplier<Object> value) {
		int length = faker.number().randomDigit();
		addFix(length, value);
	}

	public void addBetween(int min, int max, Supplier<Object> value) {
		int length = faker.number().numberBetween(min, max);
		addFix(length, value);
	}

	private List<Object> base;
	private Faker faker;
	// private Supplier<List<Object>> constructor;

	public ListFaker() {
		this(ArrayList<Object>::new);
	}

	public ListFaker(Supplier<List<Object>> constructor) {
		this(constructor, Locale.JAPANESE);
	}

	public ListFaker(Supplier<List<Object>> constructor, Locale locale) {
		this(constructor, new Faker(locale));
	}

	public ListFaker(Supplier<List<Object>> constructor, Faker faker) {
		// this.constructor = constructor;
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
	public boolean contains(Object o) {
		return base.contains(o);
	}

	@Override
	public Iterator<Object> iterator() {
		return base.iterator();
	}

	@Override
	public Object[] toArray() {
		return base.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return base.toArray(a);
	}

	@Override
	public boolean add(Object e) {
		return base.add(expression(e));
	}

	@Override
	public boolean remove(Object o) {
		return base.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return base.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		return base.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		return base.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return base.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return base.retainAll(c);
	}

	@Override
	public void clear() {
		base.clear();
	}

	@Override
	public Object get(int index) {
		return base.get(index);
	}

	@Override
	public Object set(int index, Object element) {
		return base.set(index, expression(element));
	}

	@Override
	public void add(int index, Object element) {
		base.add(index, expression(element));
	}

	@Override
	public Object remove(int index) {
		return base.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return base.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return base.lastIndexOf(o);
	}

	@Override
	public ListIterator<Object> listIterator() {
		return base.listIterator();
	}

	@Override
	public ListIterator<Object> listIterator(int index) {
		return base.listIterator(index);
	}

	@Override
	public List<Object> subList(int fromIndex, int toIndex) {
		return base.subList(fromIndex, toIndex);
	}

	private Object expression(Object e) {
		if (e instanceof String) {
			String exp = (String) e;
			return faker.expression(exp);
		}
		return e;
	}

}
