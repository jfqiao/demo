package edu.zju.src_reading.collection;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HashMapDemo {
	public static void main(String[] args) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(1,  1);
		map.put(3, 2);
		map.put(2, 10);
		Map<Integer, Integer> dm = map.descendingMap();
		Iterator<Entry<Integer, Integer>> es = dm.entrySet().iterator();
		while (es.hasNext())
			System.out.println(es.next().getKey());
	}
}
