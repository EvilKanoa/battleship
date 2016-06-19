package ca.kanoa.battleship.game;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.ButtonListener;
import ca.kanoa.battleship.ui.CellButton;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {

    private List<Entity>[][] cells;
    private CellButton[][] cellButtons;
    private Image grid;
    private Image cellButton;
    private String mapId;

    public Map(String mapId, ButtonListener buttonListener) throws SlickException {
        this.mapId = mapId;

        grid = new Image("img/grid.tga");
        cellButton = new Image("img/button/cell_hover.tga");

        cells = new List[Config.MAP_SIZE][Config.MAP_SIZE];
        cellButtons = new CellButton[Config.MAP_SIZE][Config.MAP_SIZE];
        for (int i = 0; i < Config.MAP_SIZE; i++) {
            for (int j = 0; j < Config.MAP_SIZE; j++) {
                cells[i][j] = new ArrayList<Entity>();

                CellButton button = new CellButton(mapId, cellButton, i, j);
                cellButtons[i][j] = button;
                if (buttonListener != null) {
                    button.addListener(buttonListener);
                }
            }
        }
    }

    public void update(float x, float y) {
        // update the buttons
        for (int i = 0; i < Config.MAP_SIZE; i++) {
            for (int j = 0; j < Config.MAP_SIZE; j++) {
                cellButtons[i][j].update(x, y);
            }
        }

    }

    public void render(float x, float y) {
        // draw the grid
        grid.draw(x, y);

        // draw the entities
        List<Entity> entities = getEntities();
        sortEntities(entities);
        for (Entity entity : entities) {
            entity.draw(x + (entity.getX() * 40), y + (entity.getY() * 40));
        }

        // draw the cursor
        for (int i = 0; i < Config.MAP_SIZE; i++) {
            for (int j = 0; j < Config.MAP_SIZE; j++) {
                cellButtons[i][j].render();
            }
        }

    }

    public void place(Entity entity) {
        cells[entity.getX()][entity.getY()].add(entity);
    }

    public boolean hit(int x, int y) {
        if (getMarker(x, y) != null) {
            return getMarker(x, y).isHit();
        }
        boolean hit = false;
        for (Entity entity : check(x, y)) {
            if (entity instanceof Ship) {
                // a ship was hit
                hit = true;
                break;
            }
        }

        place(new Marker(hit, x, y));
        return hit;
    }

    public Marker getMarker(int x, int y) {
        for (Entity entity : cells[x][y]) {
            if (entity instanceof Marker) {
                return (Marker) entity;
            }
        }
        return null;
    }

    public String getId() {
        return mapId;
    }

    public List<Entity> check(int x, int y) {
        List<Entity> hits = new LinkedList<Entity>();
        for (Entity entity : getEntities()) {
            if (entity.collision(x, y)) {
                hits.add(entity);
            }
        }
        return hits;
    }

    public boolean validPlacement(Ship ship) {
        for (Integer[] coord : ship.getOccupiedSpaces()) {
            if (coord[0] < 0 || coord[0] >= Config.MAP_SIZE || coord[1] < 0 || coord[1] >= Config.MAP_SIZE) {
                return false;
            }
            for (Entity entity : getEntities()) {
                if (entity.collision(coord[0], coord[1]) && entity instanceof Ship) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Entity> checkCollisions(Entity entity) {
        List<Entity> hits = new LinkedList<Entity>();
        for (Integer[] cell : entity.getOccupiedSpaces()) {
            for (Entity other : getEntities()) {
                if (other != entity && other.collision(cell[0], cell[1])) {
                    hits.add(other);
                }
            }
        }
        return hits;
    }

    public int getCursorX() {
        return getCursor()[0];
    }

    public int getCursorY() {
        return getCursor()[1];
    }

    public int[] getCursor() {
        for (int x = 0; x < Config.MAP_SIZE; x++) {
            for (int y = 0; y < Config.MAP_SIZE; y++) {
                if (cellButtons[x][y].selected()) {
                    return new int[]{x, y};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new LinkedList<Entity>();
        for (List<Entity>[] column : cells) {
            for (List<Entity> cell : column) {
                entities.addAll(cell);
            }
        }
        return entities;
    }

    private void sortEntities(List<Entity> entities) {
        // TODO: Implement a sorting algorithm for this
        List<Entity> ships = new LinkedList<Entity>();
        List<Entity> markers = new LinkedList<Entity>();
        for (Entity e : entities) {
            if (e instanceof Ship) {
                ships.add(e);
            } else if (e instanceof Marker) {
                markers.add(e);
            }
        }
        entities.clear();
        entities.addAll(ships);
        entities.addAll(markers);
    }
}
