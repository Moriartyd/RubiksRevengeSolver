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
    private Color centerColor;

    public static boolean checkForSimilar(Square a, Square b) {
//        a.getColor()
        return false;
    }
}
