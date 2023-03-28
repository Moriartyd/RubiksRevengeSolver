package ru.mirea.heruistic.solver;

import ru.mirea.heruistic.cube.*;
import ru.mirea.heruistic.solver.phases.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {

	private Cube cube;
	private static final int MAX_DEPTH = 100;
//	private static final int MIN_DEPTH = 29;

	private final IPhase[] phases = new IPhase[] {
			new Phase1(),
			new Phase2a(),
			new Phase2b(),
			new Phase3a(), new Phase3b(), new Phase3c(),
			new Phase4a(), new Phase4b(), new Phase4c(), new Phase4d(),
			new Phase5(),
			new Phase6(),
			new Phase7(),
			new Phase8()
	};

	private ArrayList<Twist> solution = new ArrayList<>();

	public Solver(Cube cube) {
		this.cube = cube;
	}

	public List<Twist> solve() {
//		LinkedList<Twist> solution = new LinkedList<>();
//		int depth = 0;
//		int minDepthDiscriminator = 0;
//		Twist lastTwist = null;
//		LinkedList<Twist> deprecatedTwists = new LinkedList<>();
//		int widthCounter = 0;
//		while (!cube.isSolved()) {
//			if (depth == MAX_DEPTH) {
//				while (depth > MIN_DEPTH - minDepthDiscriminator && !solution.isEmpty()) {
//					lastTwist = solution.removeLast();
//					depth--;
//				}
//				deprecatedTwists.add(lastTwist);
//				if (widthCounter == Twist.values().length && minDepthDiscriminator < MIN_DEPTH) {
//					deprecatedTwists.clear();
//					widthCounter = 0;
//					minDepthDiscriminator++;
//				} else {
//					widthCounter += 1;
//				}
//			}
//			if (deprecatedTwists.size() == Twist.values().length) {
//				deprecatedTwists.clear();
//				widthCounter = 0;
//				minDepthDiscriminator++;
//			}
//			while (depth < MAX_DEPTH && !cube.isSolved()) {
//				List<Twist> moves = List.of(Twist.values());
//				int minDistance = Integer.MAX_VALUE;
//				Twist bestMove = null;
//
//				for (Twist twist : moves) {
//					if (!deprecatedTwists.contains(twist)) {
//						Cube newCube = cube.apply(twist);
//						int distance = calculateDistance(newCube);
//
//						if (distance < minDistance) {
//							minDistance = distance;
//							bestMove = twist;
//						}
//					}
//				}
//
//				cube.turn(bestMove);
//				depth++;
//				System.out.println(bestMove + " ");
//				solution.addLast(bestMove);
//			}
//		}

		for (int i = 0; i < phases.length; i++) {
			IPhase phase = phases[i];

			for (int depth = 0; depth <= MAX_DEPTH; depth++) {
				ArrayList<Twist> path = phase.search(cube, depth);

				if (path == null) {
					continue;
				}

				for (Twist twist : path) {
					cube.turn(twist);
					solution.add(twist);
				}
				break;
			}
		}

		solution = postProcess(solution);
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

	private ArrayList<Twist> postProcess(ArrayList<Twist> solution) {
		ArrayList<Twist> result = new ArrayList<Twist>();

		while (!solution.isEmpty()) {
			Twist next = solution.remove(0);

			if (result.isEmpty()) {
				result.add(next);
				continue;
			}

			Twist last = result.get(result.size() - 1);
			int lMod = last.ordinal() % Twist.NUM_UNIQUE_TWIST;
			int nMod = next.ordinal() % Twist.NUM_UNIQUE_TWIST;

			if (lMod == nMod) {
				result.remove(result.size() - 1);
				Twist swap = Twist.swap(last, next);

				if (swap != null) result.add(swap);
			}
			else result.add(next);
		}

		return result;
	}

//	public int getProgress() {
//		return (int) (progress / phases.length * 100.0);
//	}
}