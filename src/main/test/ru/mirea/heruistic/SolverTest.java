package ru.mirea.heruistic;

import org.junit.jupiter.api.Test;
import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Twist;
import ru.mirea.heruistic.solver.Solver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolverTest {

    @Test
    public void test() throws IOException, InterruptedException {
        Cube cube = new Cube(Cube.SOLVED.clone(), new ArrayList<Twist>());
//        cube.printCube();
        cube.scramble();
//        cube.turn(Twist.L);
        cube.printCube();
        Solver solver = new Solver(cube);
        List<Twist> solution = solver.solve();
        System.out.print("SOLUTION: ");
        for (Twist twist : solution) {
            System.out.print(twist + " ");
        }
        System.out.println();
        cube.printCube();
    }
}
