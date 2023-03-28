package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Twist;

import java.util.ArrayList;


public interface IPhase {
	public ArrayList<Twist> search(Cube cube, int maxDepth);
}