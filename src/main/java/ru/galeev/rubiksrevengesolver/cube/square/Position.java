package ru.galeev.rubiksrevengesolver.cube.square;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private String faceName;
    private int y;
    private int x;
}