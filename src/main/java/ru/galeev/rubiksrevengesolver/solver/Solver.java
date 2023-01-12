package ru.galeev.rubiksrevengesolver.solver;

import ru.galeev.rubiksrevengesolver.cube.Cube;
import ru.galeev.rubiksrevengesolver.cube.Face;
import ru.galeev.rubiksrevengesolver.cube.square.Color;

import static ru.galeev.rubiksrevengesolver.cube.Face.checkForSimilar;

public class Solver {

    private static final String LEFT = "L";
    private static final String RIGHT = "R";
    private static final String UP = "U";
    private static final String DOWN = "D";
    private static final String FRONT = "F";
    private static final String BACK = "B";

    public String getSolveAlgorithm(Cube cube) {
        return solveCenters(cube) + // 1
                solveEges(cube) + // 2
                solveStandartRubiksCube(cube);
    }

    private String solveEges(Cube cube) {
        String algorithm = "";
        String start1 = "L l U' ";
        String start2 = "R r U ";
        String algo1 = start1 + "R U R' r'";
        String algo2 = start1 + "R R U R' r'";
        String algo3 = start1 + "R R U R' r'";
        String algo4 = start2 + "L' U' R' r'";
        String algo5 = start2 + "L' L' U R' r'";
        String algo6 = start2 + "L U R' r'";
        String algo7 = "U F' L F' L' F U' D d R F' U R' F D' d'";
        String algo8 = "R r U F' L U' F R' r'";

        int rotates = 0;
        while (rotates < 6) {
            Face frontFace = cube.getFrontFace();
            if (checkForSimilar(frontFace.getSquares()[0][1], frontFace.getSquares()[3][2]) &&
                    checkForSimilar(frontFace.getSquares()[0][2], frontFace.getSquares()[3][1])) {
                append(algorithm, algo7, cube);

            }
            if (checkForSimilar(frontFace.getSquares()[0][1], frontFace.getSquares()[3][1]) &&
                    checkForSimilar(frontFace.getSquares()[0][2], frontFace.getSquares()[3][2])) {
                append(algorithm, algo8, cube);
            }

            if (checkForSimilar(frontFace.getSquares()[0][2], frontFace.getSquares()[3][1])) {
                Face rightFace = cube.getFace(RIGHT);
                if (!checkForSimilar(frontFace.getSquares()[1][3], frontFace.getSquares()[2][3])) {
//                algorithm += algo1;
//                cube.execute(algo1);
                    append(algorithm, algo1, cube);
                }
                if (!checkForSimilar(rightFace.getSquares()[3][1], rightFace.getSquares()[3][2])) {
//                algorithm += algo2;
//                cube.execute(algo2);
                    append(algorithm, algo2, cube);
                }
                if (!checkForSimilar(rightFace.getSquares()[1][3], rightFace.getSquares()[2][3])) {
//                algorithm += algo3;
//                cube.execute(algo3);
                    append(algorithm, algo3, cube);
                }
            }

            if (checkForSimilar(frontFace.getSquares()[0][1], frontFace.getSquares()[3][2])) {
                Face leftFace = cube.getFace(LEFT);
                if (!checkForSimilar(frontFace.getSquares()[1][0], frontFace.getSquares()[2][0])) {
//                algorithm += algo4;
//                cube.execute(algo4);
                    append(algorithm, algo4, cube);
                }
                if (!checkForSimilar(leftFace.getSquares()[3][1], leftFace.getSquares()[3][2])) {
//                algorithm += algo5;
//                cube.execute(algo5);
                    append(algorithm, algo5, cube);
                }
                if (!checkForSimilar(leftFace.getSquares()[1][0], leftFace.getSquares()[2][0])) {
//                algorithm += algo6;
//                cube.execute(algo6);
                    append(algorithm, algo6, cube);
                }
            }
            // Каждый четный раз поворачиваем налево
            // Каждый нечетный раз - вниз
            // Таким образом за 6 итераций будет пройдена каждая грань и в конце куб вернется к первоначальному состоянию
            String rotateAlgo1 = "rotateL";
            String rotateAlgo2 = "rotateD";
            if (rotates % 2 == 0) {
                append(algorithm, rotateAlgo1, cube);
//                cube.rotate(LEFT);
            } else {
                append(algorithm, rotateAlgo2, cube);
//                cube.rotate(DOWN);
            }
            rotates++;
        }
        return algorithm;
    }

    private String solveCenters(Cube cube) {
        String algo1 = "R r U' R' r' U' R r U' U' R' r'";

        return algo1;
    }

    private String solveStandartRubiksCube(Cube cube) {

        return solveStandartRubiksCubeEdges(cube) + // 3
                rotateToWhiteUp(cube) + //разворот белоым центром вверх
                solveStandartRubiksCubeUp(cube) + // 4
                solveStandartRubiksCubeUncleUnts(cube) + // 5
                "Перевернуть кубик вверх дном" +
                solveStandartRubiksCubeUpLine(cube) + // 6
                solveStandartRubiksCubeUpCross(cube) + // 7
                solveStandartRubiksCubeUpCorners(cube) + // 8
                solveStandartRubiksCubeUpCornersLast(cube);
    }

    private String rotateToWhiteUp(Cube cube) {
        String algorithm = "";
        String rotateAlgo1 = "rotateL";
        String rotateAlgo2 = "rotateD";
        int i = 0;
        while (!cube.getFace(UP).getCenterColor().equals(Color.white)) {
            if (i % 2 == 0) {
                append(algorithm, rotateAlgo1, cube);
            } else {
                append(algorithm, rotateAlgo2, cube);
            }
            i++;
        }
        return algorithm;
    }

    private String solveStandartRubiksCubeUpCornersLast(Cube cube) {
        StringBuilder algorithm = new StringBuilder();
        String algo1 = "R F' R' F R F' R' F";
        String dop1 = "U";
        String dop2 = "U U";

        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUpCorners(Cube cube) {
        StringBuilder algorithm = new StringBuilder();
        String algo1 = "R' F' L' F R F' L F";
        String algo2 = "F' L' F R' F' L F R";
        String algo3 = "r r U U rr U u U u r r U u U u R U R' U' R' F R R U' R' U' R U R' F'";

        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUpCross(Cube cube) {
        StringBuilder algorithm = new StringBuilder();
        String algo1 = "R u d R u d R u d R u d";
        String algo2 = "r r B B U U l l U U r' U U r U U F F r F F l' B B r r";


        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUpLine(Cube cube) {
        StringBuilder algorithm = new StringBuilder();
        String algo = "U F R U R' U' F'";


        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUncleUnts(Cube cube) {
        String algo1 = "D L D' L' D' F' D F";
        String algo2 = "D' R' D R D F D' F'";
    }

    private String solveStandartRubiksCubeUp(Cube cube) {
        String algorithm = "";
        String algo1 = "L D L'";
        String algo2 = "F' D' F";
        String algo3 = "F' R' D D R F";

        int rotates = 0;
        while (rotates < 4) {
            Face frontFace = cube.getFrontFace();
            if (frontFace.getSquares()[3][1].getColors().contains(Color.white) &&
                    frontFace.getSquares()[3][1].getColor().equals(Color.white)) {
                append(algorithm,);
            }

            append(algorithm, "rotateL", cube);
            rotates++;
        }

    }

    private String solveStandartRubiksCubeEdges(Cube cube) { // 3
        String algo1 = "F F";
        String algo2 = "D R F' R'";
        String algorithm = "";

        int rotates = 0;
        while (rotates < 4) {
            Face frontFace = cube.getFrontFace();
            if (frontFace.getSquares()[3][1].getColors().contains(Color.white) &&
                    frontFace.getSquares()[3][1].getColor().equals(Color.white)) {
                append(algorithm, algo1, cube);
            } else if (frontFace.getSquares()[3][1].getColors().contains(Color.white) &&
                    !frontFace.getSquares()[3][1].getColor().equals(Color.white)) {
                append(algorithm, algo2, cube);
            }

            append(algorithm, "rotateL", cube);
            rotates++;
        }

        return algorithm;
    }

    private void append(String algorithm, String command, Cube cube) {
        algorithm += command;
        cube.execute(command);
    }

//    private String getFaceLetter(Face face) {
//        String result = null;
//        switch (face.getIndex()) {
//            case (1):
//                result = LEFT;
//                break;
//            case (2):
//                result = FRONT;
//                break;
//            case (3):
//                result = RIGHT;
//                break;
//            case (4):
//                result = BACK;
//                break;
//            case (5):
//                result = UP;
//                break;
//            case (6):
//                result = DOWN;
//                break;
//            default:
//        }
//        return result;
//    }
}
