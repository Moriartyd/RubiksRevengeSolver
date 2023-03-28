package ru.mirea.heruistic.databases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Twist;

public abstract class Generator extends Thread {
	protected long count;
	
	public Generator() {
		count = 0;
	}
	
	protected void generate(byte[] database, Twist[] generators, LinkedList<Cube> cubes) {
		double runtime = System.currentTimeMillis();
		
		Arrays.fill(database, (byte) -1);
		byte[] helper = new byte[database.length];
		LinkedList<Cube> stack = new LinkedList<Cube>();
		
		boolean exit = false;
		count = 0;
		
		for (int depth = 0; !exit; depth++) {
			for (Cube cube : cubes) {
				stack.addLast(cube);
			}
			
			Arrays.fill(helper, (byte) 127);
			
			while (!stack.isEmpty()) {
				Cube cube = stack.removeLast();
				int distance = cube.numTwists();
				
				if (distance == depth) {
					int index = index(cube);
					
					if (database[index] < 0) {
						database[index] = (byte) distance;
						count++;
						
						if (count == getLimit()) {
							exit = true;
							break;
						}
					}
					
					continue;
				}
				
				for (Twist twist : generators) {
					if (twist.shouldAvoid(cube.lastTwist())) continue;
					
					Cube clone = cube.clone();
					clone.twist(twist);
					distance = clone.numTwists();
					int index = index(clone);
					
					if (helper[index] <= distance) continue;
					
					helper[index] = (byte) distance;
					stack.addLast(clone);
				}
			}
		}
		
		runtime = System.currentTimeMillis() - runtime;
		System.out.println("Runtime: " + (runtime / 60000.0) + " minutes.");
	}
	
	protected abstract int index(Cube cube);
	
	protected int index(int[] permutation) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		Set<Integer> keys = map.keySet();
		int index = 0;
		long suffix = 1;
		
		for (int i = permutation.length - 1; 0 <= i; i--) {
			int key = permutation[i];
			int value = map.containsKey(key) ?  map.get(key) + 1 : 1;
			map.put(key, value);
			
			for (Integer k : keys) {
				if (k < key) {
					index += suffix * map.get(k) / value;
				}
			}
			
			suffix *= permutation.length - i;
			suffix /= value;
		}
		
		return index;
	}
	
	protected abstract int getLimit();
	
	public double percentage() {
		return (double) count / (double) getLimit();
	}
}