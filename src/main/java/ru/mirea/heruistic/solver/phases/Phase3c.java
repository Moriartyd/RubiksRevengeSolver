package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Edge;
import ru.mirea.heruistic.databases.BackEdgeDatabase;
import ru.mirea.heruistic.databases.Database;

public class Phase3c extends Phase3b {
	public Phase3c() {
		super();
		
		databases = new Database[] {
			BackEdgeDatabase.getInstance()
		};
	}
	
	@Override
	protected boolean isGoal(Cube cube) {
		if (Edge.BR0.indexOf(cube) != Edge.BR1.indexOf(cube)) return false;
		else if (Edge.BL0.indexOf(cube) != Edge.BL1.indexOf(cube)) return false;
		else return super.isGoal(cube);
	}
}