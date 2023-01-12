package ru.galeev.rubiksrevengesolver.cube.square;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Square {

    private Position position;
    private Set<Color> colors;
    private Color color;

    public boolean isEdge() {
        return colors.size() == 2;
    }

    public boolean isCorner() {
        return colors.size() == 3;
    }
}
