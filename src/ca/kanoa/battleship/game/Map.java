package ca.kanoa.battleship.game;

//gets information required for program
import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.input.ButtonListener;
import ca.kanoa.battleship.ui.CellButton;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Creates the main class for the program
public class Map {

    //Creates variables for use in the program
    private List<Entity>[][] cells;
    private CellButton[][] cellButtons;
    private Image grid;
    private Image cellButton;
    private String mapId;

    //Creates method for making the map
    public Map(String mapId, ButtonListener buttonListener, boolean graphical) throws SlickException {
        this.mapId = mapId;

        if (graphical) {
            try {
                grid = new Image("img/grid.tga");
                //prepares the cells
                cellButton = new Image("img/button/cell_hover.tga");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        //creates a list of cells so it knows where to check
        cells = new List[Config.MAP_SIZE][Config.MAP_SIZE];
        cellButtons = new CellButton[Config.MAP_SIZE][Config.MAP_SIZE];
        //searches for if a cell button has been pressed
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
        // updates the buttonsbased on player input
        for (int i = 0; i < Config.MAP_SIZE; i++) {
            for (int j = 0; j < Config.MAP_SIZE; j++) {
                cellButtons[i][j].update(x, y);
            }
        }

    }

    //renders the map
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

    //places the entities based on user input
    public void place(Entity entity) {
        cells[entity.getX()][entity.getY()].add(entity);
    }

    //checks if the player has gotten a hit against the opponent
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
    
    //places the correct marker if there is a hit
    public boolean isHit(int x, int y) {
        if (getMarker(x, y) != null) {
            return getMarker(x, y).isHit();
        }
        return false;
    }

    //Checks if the ship has been sunk
    public Ship checkSunkenShip(int x, int y) {
        for (Entity entity : check(x, y)) {
            if (entity instanceof Ship) {
                for (Integer[] cell : entity.getOccupiedSpaces()) {
                    if (!isHit(cell[0], cell[1])) {
                        return null;
                    }
                }
                return (Ship) entity;
            }
        }
        return null;
    }

    //places the correct marker
    public Marker getMarker(int x, int y) {
        for (Entity entity : cells[x][y]) {
            if (entity instanceof Marker) {
                return (Marker) entity;
            }
        }
        return null;
    }

    //returns the map ID
    public String getId() {
        return mapId;
    }

    //checks for hits with the ships
    public List<Entity> check(int x, int y) {
        List<Entity> hits = new LinkedList<Entity>();
        for (Entity entity : getEntities()) {
            if (entity.collision(x, y)) {
                hits.add(entity);
            }
        }
        return hits;
    }

    //checks whether or not the ships have been placed within the game borders
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

    //Checks for collisions between ships
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

    //gets the cursor placement in the x direction
    public int getCursorX() {
        return getCursor()[0];
    }

    //gets the cursor placement in the y direction
    public int getCursorY() {
        return getCursor()[1];
    }

    //culmonates the getting of the cursor placement
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

    //lists the placement of the entities
    public List<Entity> getEntities() {
        List<Entity> entities = new LinkedList<Entity>();
        for (List<Entity>[] column : cells) {
            for (List<Entity> cell : column) {
                entities.addAll(cell);
            }
        }
        return entities;
    }

    //sorts the entities
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
