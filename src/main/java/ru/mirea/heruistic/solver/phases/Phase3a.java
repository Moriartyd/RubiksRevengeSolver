package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;

public class Phase3a extends Phase1 {
	public Phase3a() {
		super();
		
		generators = new Twist[] {
			Twist.U,            Twist.F,  Twist.D,            Twist.B,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.u2, Twist.r2, Twist.f2, Twist.d2, Twist.l2, Twist.b2,
			Twist.U_,           Twist.F_, Twist.D_,           Twist.B_
		};
	}
	
	@Override
	protected boolean isGoal(Cube cube) {
		if (Center.R0.indexOf(cube) != Center.R2.indexOf(cube)) return false;
		else if (Center.R1.indexOf(cube) != Center.R3.indexOf(cube)) return false;
		else if (Center.L0.indexOf(cube) != Center.L2.indexOf(cube)) return false;
		else if (Center.L1.indexOf(cube) != Center.L3.indexOf(cube)) return false;
		else if (Center.F0.indexOf(cube) != Center.F2.indexOf(cube)) return false;
		else if (Center.F1.indexOf(cube) != Center.F3.indexOf(cube)) return false;
		else if (Center.B0.indexOf(cube) != Center.B2.indexOf(cube)) return false;
		else if (Center.B1.indexOf(cube) != Center.B3.indexOf(cube)) return false;
		else return true;
	}
}