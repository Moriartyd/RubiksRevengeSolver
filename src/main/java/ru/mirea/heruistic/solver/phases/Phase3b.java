package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;
import ru.mirea.heruistic.databases.*;

public class Phase3b extends Phase3a {
	public Phase3b() {
		super();
		
		databases = new Database[] {
			FrontEdgeDatabase.getInstance()
		};
	}
	
	@Override
	protected boolean isGoal(Cube cube) {
		if (Edge.FL0.indexOf(cube) != Edge.FL1.indexOf(cube)) return false;
		else if (Edge.FR0.indexOf(cube) != Edge.FR1.indexOf(cube)) return false;
		else return super.isGoal(cube);
	}
}