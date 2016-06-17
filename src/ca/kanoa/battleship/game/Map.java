package ca.kanoa.battleship.game;

import java.util.ArrayList;
import java.util.List;

public class Map {

    List<Entity>[][] cells;

    public Map(int size) {
        cells = new List[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = new ArrayList<Entity>();
            }
        }
    }

}
