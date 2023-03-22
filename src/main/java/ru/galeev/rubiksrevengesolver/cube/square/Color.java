package ru.galeev.rubiksrevengesolver.cube.square;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Color {
    white("w"),
    yellow("y"),
    green("g"),
    blue("b"),
    red("r"),
    orange("o"),
    undefined("u");

    private String sign;
}
