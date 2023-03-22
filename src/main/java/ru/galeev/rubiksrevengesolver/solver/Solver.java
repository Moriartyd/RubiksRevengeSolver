package ru.galeev.rubiksrevengesolver.solver;

import ru.galeev.rubiksrevengesolver.cube.Cube;
import ru.galeev.rubiksrevengesolver.cube.Face;
import ru.galeev.rubiksrevengesolver.cube.square.Color;
import ru.galeev.rubiksrevengesolver.cube.square.Position;
import ru.galeev.rubiksrevengesolver.cube.square.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private String solveEges(Cube cube) { //https://kubikus.top/4x4/10-sborka-reber-kubik-4x4-step-2.html
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

    /*
    private String solveCenters(Cube cube) {
        String algorithm = "";
//        String algo1 = "R r U' R' r' U' R r U' U' R' r'";
        String pifPafSmall = "r u r' u'";

        //Поиск грани с наибольшим количеством белых !!!центров!!!
        Face[] faces = cube.getFaces();
        int i = 0;
        int whiteCounter = 0;
        while (i < 6 || whiteCounter >= 2) {
            whiteCounter = 0;

            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    if (faces[i].getSquares()[y][x].getColor().equals(Color.white)) {
                        whiteCounter++;
                    }
                }
            }
            faces[i].setWhiteCounter(whiteCounter);
            i++;
        }
        //Установка этой грани центрального белого цвета
        if (whiteCounter == 0) {
            for (int j = 0; j < 6; j++) {
                Face face = cube.getFaces()[j];
                search: {
                    for (int y = 0; y < 4; y++) {
                        for (int x = 0; x < 4; x++) {
                            if (face.getSquares()[y][x].getColor().equals(Color.white)) {
                                cube.getFaces()[j].setCenterColor(Color.white);
                                break search;
                            }
                        }
                    }
                }
            }
        } else if (whiteCounter >= 2) {
            cube.getFaces()[i].setCenterColor(Color.white);
        }
        //Расстановка цветов остальным граням
        for (int index = 0; index < 6; index++) {
            int j = 0;
            while (!cube.getFaces()[j].getCenterColor().equals(Color.white)) {
                j++;
            }
            String whiteFaceName = cube.getFaces()[j].getName();
            switch (whiteFaceName) {
                case UP:
                    append(algorithm, "rotateD", cube);
                    break;
                case DOWN:
                    append(algorithm, "rotateU", cube);
                    break;
                case LEFT:
                    append(algorithm, "rotateR", cube);
                    break;
                case RIGHT:
                    append(algorithm, "rotateL", cube);
                    break;
                case BACK:
                    append(algorithm, "rotateU", cube);
                    append(algorithm, "rotateU", cube);
                    break;
                case FRONT:
                    break;
            }
            switch (cube.getFaces()[index].getName()) {
                case UP:
                    cube.getFaces()[index].setCenterColor(Color.orange);
                    break;
                case DOWN:
                    cube.getFaces()[index].setCenterColor(Color.red);
                    break;
                case LEFT:
                    cube.getFaces()[index].setCenterColor(Color.green);
                    break;
                case RIGHT:
                    cube.getFaces()[index].setCenterColor(Color.blue);
                    break;
                case BACK:
                    cube.getFaces()[index].setCenterColor(Color.yellow);
                    break;
                case FRONT:
                    break;
            }
        }
        append(algorithm, "rotateU", cube); //Поварачиваем белой стороной вверх

        //Сборка белого центра
        if (whiteCounter < 2) { // сборка до 3х граней белого цвета сверху
            //Возможно, стоит вынести из ифа, так как любую ситуацию можно упростить до 3х квадратов белого цвета сверху
            while (cube.getFace(UP).getWhiteNumber() < 3) {
                append(algorithm, pifPafSmall, cube);
            }
        }

        if (whiteCounter >= 2) { //сборка при имеющихся минимум 2х белых центров сверху
            while (cube.getFace(UP).getWhiteNumber() < 3) { // собираем до 3х центров сверху
                append(algorithm, pifPafSmall, cube);
            }

            while (cube.getFace(UP).getSquares()[2][2].getColor().equals(Color.white)) {
                append(algorithm, "U", cube);
            }

            while (!cube.getFace(FRONT).getSquares()[1][1].getColor().equals(Color.white) ||
                    !cube.getFace(FRONT).getSquares()[2][2].getColor().equals(Color.white)) {
                append(algorithm, "D' d' u", cube);
            }
            if (cube.getFace(FRONT).getSquares()[1][1].getColor().equals(Color.white)) {
                append(algorithm, "R' r' F R r", cube);
            }
            if (cube.getFace(FRONT).getSquares()[2][2].getColor().equals(Color.white)) {
                append(algorithm, "U U", cube);
                append(algorithm, "F R' r F' R r", cube);
            }
        }

        append(algorithm, "rotateU", cube); //Поварачиваем желтой стороной вверх
        append(algorithm, "rotateU", cube); //Поварачиваем желтой стороной вверх

        if (cube.getFace(UP).getCenterColorNumber() <= 2) {
            if (cube.getFace(UP).getCenterColorNumber() == 0) {
                String algo = "R r U U R' r'";
            } else if (cube.getFace(UP).getCenterColorNumber() == 1) {
                String algo = "R r U R' r'";
            } else if (cube.getFace(UP).getCenterColorNumber() == 2) {
                while (!cube.getFace(UP).getSquares()[1][2].getColor().equals(cube.getFace(UP).getCenterColor())) {
                    append(algorithm, "U", cube);
                }
                String algo1 = "R r U R' r'";
                String algo2 = "R r U U R' r'";

//                if (cube.getFace(UP).getSquares()[2][1].getColor().equals(cube.getFace(UP).getCenterColor())) {
//                    while (cube.getFrontFace().getCenterColor)
//                }

            }
        }

        return algorithm;
    }
*/

    private Position searchWhiteSquare(Face face) {
        int y = 0;
        int x = 0;
        while (y < 4) {
            while (x < 4) {
                if (face.getSquares()[y][x].getColor().equals(Color.white)) {
                    return new Position(face.getName(), y, x);
                }
                x++;
            }
            y++;
        }
        return null;
    }

    private String solveStandartRubiksCube(Cube cube) {

        return rotateToWhiteUp(cube) + //разворот белоым центром вверх
                solveStandartRubiksCubeEdges(cube) + // 3
                rotateToWhiteDown(cube) + //разворот белоым центром вниз
                solveStandartRubiksCubeUp(cube) + // 4
                solveStandartRubiksCubeUncleUnts(cube) + // 5
//                "rotateD rotateD" +         // Переворачиваем кубик желтым цветом вверх
                solveStandartRubiksCubeUpLine(cube) + // 6
                solveStandartRubiksCubeUpCross(cube) + // 7
                solveStandartRubiksCubeUpCorners(cube) + // 8
                solveStandartRubiksCubeUpCornersLast(cube);
    }

    private String rotateToWhiteDown(Cube cube) {
        String algorithm = "";
        String rotateAlgo1 = "rotateL";
        String rotateAlgo2 = "rotateD";
        int i = 0;
        while (!cube.getFace(DOWN).getCenterColor().equals(Color.white)) {
            if (i / 4 < 1) {
                append(algorithm, rotateAlgo1, cube);
            } else {
                append(algorithm, rotateAlgo2, cube);
            }
            i++;
        }
        return algorithm;
    }

    private String rotateToWhiteUp(Cube cube) {
        String algorithm = "";
        String rotateAlgo1 = "rotateL";
        String rotateAlgo2 = "rotateD";
        int i = 0;
        while (!cube.getFace(UP).getCenterColor().equals(Color.white)) {
            if (i / 4 < 1) {
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
        String algo3 = "r r U U r r U u U u r r U u U u R U R' U' R' F R R U' R' U' R U R' F'";

        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUpCross(Cube cube) {
        StringBuilder algorithm = new StringBuilder();
        String algo1 = "R u d R u d R u d R u d";
        String algo2 = "r r B B U U l l U U r' U U r U U F F r F F l' B B r r";


        return algorithm.toString();
    }

    private String solveStandartRubiksCubeUpLine(Cube cube) {
        String algorithm = "";
        String algo = "U F R U R' U' F'";
        int rotates = 0;
        while (rotates < 4) {
            append(algorithm, "rotateL", cube);
            rotates++;
        }

        return algorithm;
    }

    private String solveStandartRubiksCubeUncleUnts(Cube cube) { // Дяди-Тети
        String algorithm = "";
        String algo1 = "U R U' R' F R' F' R";
        String algo2 = "U' L' U L F' L F L'";

        while (!checkUncleAunts(cube.getFaces())) {
            Face frontFace = cube.getFrontFace();
            int rot = 0;
            while (rot < 4 && (!frontFace.getSquares()[0][1].getColor().equals(frontFace.getCenterColor()) ||
                    cube.getFace(UP).getSquares()[3][1].equals(cube.getFace(UP).getCenterColor()))) {
                append(algorithm, "U", cube);
                rot++;
            }

            if (frontFace.getSquares()[0][1].getColor().equals(frontFace.getCenterColor())) {
                if (frontFace.getSquares()[0][1].getColors().contains(cube.getFace(LEFT).getCenterColor())) {
                    append(algorithm, algo2, cube);
                } else if (frontFace.getSquares()[0][1].getColors().contains(cube.getFace(RIGHT).getCenterColor())) {
                    append(algorithm, algo1, cube);
                }
            } else if (frontFace.getSquares()[1][3].getColors().contains(frontFace.getCenterColor())) {

                if (frontFace.getSquares()[1][3].getColors().contains(cube.getFace(RIGHT).getCenterColor())) {
                    append(algorithm, algo1, cube);
                    append(algorithm, "U U", cube);
                    append(algorithm, algo1, cube);
                }
                if (!frontFace.getSquares()[1][3].getColors().contains(cube.getFace(RIGHT).getCenterColor())) {
                    append(algorithm, algo1, cube);
                }
            }

            append(algorithm, "rotateL", cube);
        }
        return algorithm;
    }

    private boolean checkUncleAunts(Face[] faces) {
        faces.
    }

    private String solveStandartRubiksCubeUp(Cube cube) { //https://kubikus.top/3x3/5-verhniy-sloy-kubika-3x3-shag-2.html
        String algorithm = "";
        String pifPaf = "R U R' U'";
        String rotateAll = "u d' D'";

        while (!checkCorners(cube.getFace(DOWN))) {
            Face frontFace = cube.getFrontFace();
            Face upFace = cube.getFace(UP);
            Face rightFace = cube.getFace(RIGHT);
            Face downFace = cube.getFace(DOWN);

            if (frontFace.getSquares()[0][3].getColors().contains(Color.white)) {

                while (!(frontFace.getSquares()[0][3].getColors().contains(rightFace.getCenterColor()) &&
                        frontFace.getSquares()[0][3].getColors().contains(frontFace.getCenterColor()))) {
                    append(algorithm, rotateAll, cube);
                }

                if (frontFace.getSquares()[0][3].getColor().equals(Color.white)) {
                    for (int i = 0; i < 5; i++) {
                        append(algorithm, pifPaf, cube);
                    }
                } else if (rightFace.getSquares()[0][0].getColor().equals(Color.white)) {
                    append(algorithm, pifPaf, cube);
                } else if (upFace.getSquares()[3][3].getColor().equals(Color.white)) {
                    for (int i = 0; i < 3; i++) {
                        append(algorithm, pifPaf, cube);
                    }
                }
            }

            if (frontFace.getSquares()[3][3].getColors().contains(Color.white) &&
                    !frontFace.getSquares()[3][3].getColor().equals(frontFace.getCenterColor())) {
                if (frontFace.getSquares()[3][3].getColor().equals(Color.white)) {
                    for (int i = 0; i < 4; i++) {
                        append(algorithm, pifPaf, cube);
                    }
                }
                if (rightFace.getSquares()[3][0].getColor().equals(Color.white)) {
                    for (int i = 0; i < 2; i++) {
                        append(algorithm, pifPaf, cube);
                    }
                }
                if (downFace.getSquares()[3][3].getColor().equals(Color.white)) {
                    append(algorithm, pifPaf, cube);
                }
            }
            append(algorithm, "rotateL", cube);
        }
        return algorithm;

    }

    private boolean checkCorners(Face face) {
        Square[][] squares = face.getSquares();

        squares[0][0].isEdge();
        face.getName()
        squares[0][3];
        squares[3][0];
        squares[3][3];
    }

    private String solveStandartRubiksCubeEdges(Cube cube) { // 3 https://kubikus.top/4x4/11-legkiy-sposob-sobrat-kubik-4x4-step-3.html

        String algorithm = "";

        while (!checkUpCross(cube.getFace(UP))) {
            Face frontFace = cube.getFrontFace();
            Face upFace = cube.getFace(UP);
            Face downFace = cube.getFace(DOWN);
            if (frontFace.getSquares()[1][3].getColor().equals(Color.white)) {
                while (upFace.getSquares()[1][3].getColor().equals(Color.white)) {
                    append(algorithm, "U", cube);
                }
                append(algorithm, "R", cube);
            }
            if (frontFace.getSquares()[3][1].getColor().equals(Color.white)) {
                while (upFace.getSquares()[0][1].getColor().equals(Color.white)) {
                    append(algorithm, "U", cube);
                }
                append(algorithm, "F L'", cube);
            }

            if (frontFace.getSquares()[0][1].getColor().equals(Color.white)) {
                while (upFace.getSquares()[0][1].getColor().equals(Color.white)) {
                    append(algorithm, "U", cube);
                }
                append(algorithm, "F L'", cube);
            }

            if (frontFace.getSquares()[0][1].getColor().equals(Color.white)) {
                while (upFace.getSquares()[1][3].getColor().equals(Color.white)) {
                    append(algorithm, "U", cube);
                }
                append(algorithm, "F R", cube);
            }

            //Добавить белые с нижней грани
            if (downFace.getSquares()[0][1].getColor().equals(Color.white)) {
                while (upFace.getSquares()[3][1].getColor().equals(Color.white)) {
                    append(algorithm, "U", cube);
                }
                append(algorithm, "F F", cube);
            }

            append(algorithm, "rotateL", cube);
        }

        //На этой строчке собран верхний крест, но грани не совпадают с центрами

        while (checkUpEdgesAgreement(cube.getFaces())) { //вертим кубик до момента, когда две грани совпадут с двумя центрами
            append(algorithm, "U", cube);
        }

        Face frontFace = cube.getFrontFace();
        solveCentersWitchMainCross(cube, algorithm, frontFace);

        String algo10 = "R U R' U R U U R' U";
        if (cube.getFace(BACK).getCenterColor().equals(cube.getFace(BACK).getSquares()[0][1].getColor())) {
            append(algorithm, algo10, cube);
        } else if (cube.getFace(LEFT).getCenterColor().equals(cube.getFace(LEFT).getSquares()[0][1].getColor())) {
            append(algorithm, algo10, cube);
            while (checkUpEdgesAgreement(cube.getFaces())) { //вертим кубик до момента, когда две грани совпадут с двумя центрами
                append(algorithm, "U", cube);
            }
            frontFace = cube.getFrontFace();
            solveCentersWitchMainCross(cube, algorithm, frontFace);
            append(algorithm, algo10, cube);
        }
        //ToDO: Проверить, не сломаются ли грани

        return algorithm;
    }

    private void solveCentersWitchMainCross(Cube cube, String algorithm, Face frontFace) { //Становит грани на свои места
        while (frontFace.getCenterColor().equals(frontFace.getSquares()[0][1].getColor())) { //Вертим кубик до момента, когда совпадающие грани не выйдут вперед
            append(algorithm, "rotateL", cube);
            frontFace = cube.getFrontFace();
        }
        if (cube.getFace(LEFT).getCenterColor().equals(cube.getFace(LEFT).getSquares()[0][1].getColor())) {
            append(algorithm, "rotateR", cube);
            append(algorithm, "rotateR", cube);
        } else {
            append(algorithm, "rotateR", cube);
        }
    }

    private boolean checkUpCross(Face face) {

        return face.getSquares()[0][1].getColor().equals(face.getCenterColor()) &&
                face.getSquares()[3][1].getColor().equals(face.getCenterColor()) &&
                face.getSquares()[1][3].getColor().equals(face.getCenterColor()) &&
                face.getSquares()[1][0].getColor().equals(face.getCenterColor());
    }

    private boolean checkUpEdgesAgreement(Face[] faces) {
        int i = 0;
        List<Integer> agreements = new ArrayList<>();
        while (i < 6) {
            if (!faces[i].getName().equals(UP) && !faces[i].getName().equals(DOWN)) {
                if (faces[i].getCenterColor().equals(faces[i].getSquares()[0][1].getColor())) {
                    agreements.add(1);
                } else {
                    agreements.add(0);
                }
            }
            i++;
        }
        int agreementsCounter = 0;
        for (int j = 0; j < agreements.size(); j++) {
            agreementsCounter += agreements.get(j);
        }
        return agreementsCounter == 2;
    }

    private void append(String algorithm, String command, Cube cube) {
        algorithm += " " + command;
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
