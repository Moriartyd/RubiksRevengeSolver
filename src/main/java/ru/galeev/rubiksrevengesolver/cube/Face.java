package ru.galeev.rubiksrevengesolver.cube;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.galeev.rubiksrevengesolver.cube.square.Color;
import ru.galeev.rubiksrevengesolver.cube.square.Square;

@Getter
@Setter
@AllArgsConstructor
public class Face {

    private Square[][] squares = new Square[4][4];
    private String name;
    private int whiteCounter;
    private Color centerColor;

    public static boolean checkForSimilar(Square a, Square b) {
//        a.getColor()
        return false;
    }

    public int getWhiteNumber() {
        return getColorCounter(Color.white);
    }

    public int getCenterColorNumber() {
        return getColorCounter(this.centerColor);
    }

    public int getColorCounter(Color color) {
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for  (int j = 0; j < 4; j++) {
                if (this.squares[i][j].getColor().equals(color)) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
