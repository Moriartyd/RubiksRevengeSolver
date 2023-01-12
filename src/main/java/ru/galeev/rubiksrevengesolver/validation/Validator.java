package ru.galeev.rubiksrevengesolver.validation;

import lombok.AllArgsConstructor;
import ru.galeev.rubiksrevengesolver.cube.Cube;
import ru.galeev.rubiksrevengesolver.cube.square.Color;
import ru.galeev.rubiksrevengesolver.cube.square.Square;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Validator {


    public boolean isValidCube(Cube cube) {
        boolean result = true;
        int parityErrorCount = 0;
        Map<Color, Integer> colorMap = new HashMap<Color, Integer>();

        for (int i = 0; i < 6; i++) {
            Square[][] squares = cube.getFaces()[i].getSquares();
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 4; x++) {
                    Color color = squares[y][x].getColor();
                    Integer count = colorMap.get(color);
                    colorMap.remove(color);
                    count++;
                    colorMap.put(color, count);
                    if (isEdgePiece(x,y)) {

                    }
                }
            }
        }

        //Проверка на количество цветов (каждого цвета должно быть не больше 16
        List<Map.Entry<Color, Integer>> wrongFaces = colorMap.entrySet()
                .stream()
                .filter(e -> e.getValue() > 16)
                .collect(Collectors.toList());

        if (!wrongFaces.isEmpty()) {
            return false;
        }



    }

    private int parityErrorCount(int[][][] cube) {
        int parityErrorCount = 0;
        // Check the orientation of each edge piece
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (isEdgePiece(i, j, k)) {
                        if (isEdgePieceFlipped(cube, i, j, k)) {
                            parityErrorCount++;
                        }
                    }
                }
            }
        }
        // Check the orientation of each corner piece
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (isCornerPiece(i, j, k)) {
                        if (isCornerPieceTwisted(cube, i, j, k)) {
                            parityErrorCount++;
                        }
                    }
                }
            }
        }
        return parityErrorCount;
    }

    private boolean isEdgePiece(int x, int y) {
        return (x == 0 && y == 0) ||
                (x == 0 && y == 3) ||
                (x == 3 && y == 0) ||
                (x == 3 && y == 3);
    }

}
