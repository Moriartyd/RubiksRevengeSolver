package ru.mirea.heruistic;

import ru.mirea.heruistic.cube.Cube;
import ru.mirea.heruistic.cube.Twist;
import ru.mirea.heruistic.solver.Solver;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Cube cube = new Cube(Cube.SOLVED.clone(), new ArrayList<Twist>());
        cube.scramble();
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
