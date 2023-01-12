package ru.galeev.rubiksrevengesolver.cube.square;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private int face;
    private int x;
    private int y;
}