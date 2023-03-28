package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;

public class Phase4a extends Phase2b {
	public Phase4a() {
		super();
		
		generators = new Twist[] {
			Twist.U,                      Twist.D,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.r2, Twist.f2, Twist.l2, Twist.b2,
			Twist.U_,                     Twist.D_
		};
	}
	
	@Override
	protected boolean isGoal(Cube cube) {
		return getDistance(cube) == 0;
	}
	
	@Override
	protected float calculateDistance(Cube cube) {
		float result = 0;
		result += Edge.UB1.indexOf(cube) == Edge.UB0.indexOf(cube) ? 0 : 2;
		result += Edge.UL0.indexOf(cube) == Edge.UL1.indexOf(cube) ? 0 : 2;
		result += Edge.DF1.indexOf(cube) == Edge.DF0.indexOf(cube) ? 0 : 2;
		result += Edge.DL0.indexOf(cube) == Edge.DL1.indexOf(cube) ? 0 : 2;
		return result;
	}
}