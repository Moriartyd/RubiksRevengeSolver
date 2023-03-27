package ru.mirea.heruistic.solver;

import lombok.AllArgsConstructor;
import ru.mirea.heruistic.cube.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class Solver {

	private Cube cube;
	private static final int MAX_DEPTH = 100;
	private static final int MIN_DEPTH = 29;

	public List<Twist> solve() {
		LinkedList<Twist> solution = new LinkedList<>();
		int depth = 0;
		int minDepthDiscriminator = 0;
		Twist lastTwist = null;
		LinkedList<Twist> deprecatedTwists = new LinkedList<>();
		int widthCounter = 0;
		while (!cube.isSolved()) {
			if (depth == MAX_DEPTH) {
				while (depth > MIN_DEPTH - minDepthDiscriminator && !solution.isEmpty()) {
					lastTwist = solution.removeLast();
					depth--;
				}
				deprecatedTwists.add(lastTwist);
				if (widthCounter == Twist.values().length && minDepthDiscriminator < MIN_DEPTH) {
					deprecatedTwists.clear();
					widthCounter = 0;
					minDepthDiscriminator++;
				} else {
					widthCounter += 1;
				}
			}
			if (deprecatedTwists.size() == Twist.values().length) {
				deprecatedTwists.clear();
				widthCounter = 0;
				minDepthDiscriminator++;
			}
			while (depth < MAX_DEPTH && !cube.isSolved()) {
				List<Twist> moves = List.of(Twist.values());
				int minDistance = Integer.MAX_VALUE;
				Twist bestMove = null;

				for (Twist twist : moves) {
					if (!deprecatedTwists.contains(twist)) {
						Cube newCube = cube.apply(twist);
						int distance = calculateDistance(newCube);

						if (distance < minDistance) {
							minDistance = distance;
							bestMove = twist;
						}
					}
				}

				cube.turn(bestMove);
				depth++;
				System.out.println(bestMove + " ");
				solution.addLast(bestMove);
			}
		}

		return solution;
	}

	public static void clrscr(){
		//Clears Screen in java
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (IOException | InterruptedException ex) {}
	}

	private int calculateDistance(Cube cube) {
		int distance = 0;

		for (Corner corner : Corner.values()) {
			if (cube.getColors()[corner.x] != Cube.SOLVED[corner.x] ||
					cube.getColors()[corner.y] != Cube.SOLVED[corner.y] ||
					cube.getColors()[corner.z] != Cube.SOLVED[corner.z]) {
				distance++;
			}
		}

		for (Edge edge : Edge.values()) {
			if (cube.getColors()[edge.x] != Cube.SOLVED[edge.x] ||
					cube.getColors()[edge.y] != Cube.SOLVED[edge.y]) {
				distance++;
			}
		}

		for (Center center : Center.values()) {
			if (cube.getColors()[center.x] != Cube.SOLVED[center.x]) {
				distance++;
			}
		}
		return distance;
	}
}