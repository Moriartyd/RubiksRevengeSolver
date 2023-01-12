package ru.galeev.rubiksrevengesolver.cube;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Cube {

    @Getter
    @Setter
    private Face[] faces = new Face[6];

    public void rotate(String turn) {

    }

    public Face getFrontFace() {
        return getFace(CubeDictionary.FRONT);
    }

    public Face getFace(String faceName) {
        int i = 0;
        while (!faces[i].getName().equals(faceName) ) {
            i++;
        }
        return faces[i];
    }

    public void execute(String algorithm) {
        if (algorithm.contains("rotate")) {
            this.rotate("get Last CHar");
        }
    }

}
