package edu.zju.src_reading.basic;

import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectioinTest {
	
	public static void testList() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer integer = iterator.next();
			System.out.println(integer);
//			iterator.remove();
		}
		ListIterator<Integer> listIterator = list.listIterator();
		while (listIterator.hasNext()) {
			if (listIterator.hasPrevious()) {
				System.out.println(listIterator.previous());
			}
		}
		System.out.println(list.size());
	}

	public static void testQueue() {
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		
	}
	
}
