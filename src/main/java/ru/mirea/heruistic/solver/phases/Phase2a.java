package ru.mirea.heruistic.solver.phases;

import ru.mirea.heruistic.cube.*;
import ru.mirea.heruistic.databases.Database;
import ru.mirea.heruistic.databases.UDFBCenterDatabase;

public class Phase2a extends Phase1 {
	protected static final Center[] UD = new Center[] {
		Center.U0, Center.U1, Center.U2, Center.U3,
		Center.D0, Center.D1, Center.D2, Center.D3
	};
	
	protected static final Center[] FB = new Center[] {
		Center.F0, Center.F1, Center.F2, Center.F3,
		Center.B0, Center.B1, Center.B2, Center.B3
	};
	
	protected static final int NUM_CENTERS = 8;
	
	public Phase2a() {
		super();
		
		databases = new Database[] {
			UDFBCenterDatabase.getInstance()
		};
		
		generators = new Twist[] {
			Twist.U,  Twist.R,  Twist.F,  Twist.D,  Twist.L,  Twist.B,            Twist.r,                      Twist.l,
			Twist.U2, Twist.R2, Twist.F2, Twist.D2, Twist.L2, Twist.B2, Twist.u2, Twist.r2, Twist.f2, Twist.d2, Twist.l2, Twist.b2,
			Twist.U_, Twist.R_, Twist.F_, Twist.D_, Twist.L_, Twist.B_,           Twist.r_,                     Twist.l_
		};
	}
	
	protected boolean isGoal(Cube cube) {
		byte[] colors = cube.getColors();
		
		for (int i = 0; i < NUM_CENTERS; i++) {
			int value = colors[UD[i].x];
			if (value != Cube.W && value != Cube.Y) return false;
			
			value = colors[FB[i].x];
			if (value != Cube.B && value != Cube.G) return false;
		}
		
		if (0 < Edge.getParity(cube)) return false;
		
		return true;
	}
}