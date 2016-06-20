package ca.kanoa.battleship.game;

import ca.kanoa.battleship.network.AIGame;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Creates a class to control the AI
 * @author Evan
 */
public class AI {

    //Ceates variables for use in the class
    boolean win = false;
    boolean[] remainingShips = {false,false,false,false,false}; //remaining ships is a array which holds whether or not the ships are sunk 0 is carrier, 1 is battleship, 2 is cruiser, 3 is sub and 4 is pt boat
    boolean hit = false;
    boolean check = false;
    boolean[] miss = {false,false,false,false};
    int x = 0;
    int xtemp = 0;
    long temp = 0;
    int y;
    int ytemp = 0;
    ArrayList filledGrids = new ArrayList();
    private Map myMap;
    private Map theirMap;
    private AIGame game;

    public AI() throws SlickException {
        myMap = new Map("mymap", null, false);
        theirMap = new Map("theirmap", null, false);
    }

    /**
     * Gives the AI the option to place its ships into it's internal map
     */
    public void placeShips() { }

    /**
     * Gets called when the AI has sunk one of the players ships. Used to update the map
     * @param theirShip
     */
    public void sunkenShip(Ship theirShip) { }

    /**
     * Gets called for each time a player attacks the AI
     * @param x The x position of the player's attack
     * @param y The y position of the player's attack
     * @return The ship sunk if a player hit a ship, otherwise null
     */
    public Ship attack(int x, int y) { return null; }

    /**
     * Causes the AI to determine where its next attack will be
     * @return An array of length two with the x and y coordinate
     */
    public int[] getAttack() { return new int[]{/* x */ 0, /* y */ 0}; }

    //Creates a method to conrol the AI's shooting
    public int[] getAttack(boolean[] remainingShips, boolean [] miss, boolean lose, boolean hit){

        //creates variables for use in the method
        String j;
        int multiplier = 0;
        int numberOfTiles = 0;
        long tiles;

        //Checks if the game is over
        if (lose == false){

            //Makes sure the AI has not hit a ship yet
            if (hit == false){

                //Checks to see if the Carrier and the Battleship are still in play
                if (!game.isPlayerShipSunk(ShipType.CARRIER) || !game.isPlayerShipSunk(ShipType.BATTLESHIP)){

                    //Partitions the shooting to target the Carrier and the Battleship
                    multiplier = 4;
                    numberOfTiles = 20;

                }else if (!game.isPlayerShipSunk(ShipType.CRUISER) || !game.isPlayerShipSunk(ShipType.SUBMARINE)){ //Checks to see if the Cruiser and the Sub are still in play

                    //Partitions the shooting to target the Cruiser and the Sub
                    multiplier = 3;
                    numberOfTiles = 33;

                }else if (!game.isPlayerShipSunk(ShipType.DESTROYER)){ //Checks to see if the destroyer is still in play

                    //Partitions the shooting to target the Destroyer
                    multiplier = 2;
                    numberOfTiles = 50;

                }else{

                    //Ends the game if all ships are hit
                    lose= true;

                }

                //Generates a tile
                tiles = Math.round(Math.random() * numberOfTiles) * multiplier;

                //Splits the number into rows and collums
                for (long i = tiles; i > 9 ; i = i-10){

                    y++;
                    temp = i;

                }

                if (temp > 9){

                    temp = temp - 10;
                    y++;

                }

                //Makes sure to hit odd numbered tiles for evenly partitioned numbers
                if (multiplier == 2 && y%2 == 1 || multiplier == 4 && y%2 == 1){
                    temp = temp - 1;
                }

                //converts long to string
                j = Long.toString(temp);
                x = Integer.parseInt(j);

                //Creates co-ordinants
                j = x + "," + y;

                //Makes sure that the co-ordinant has not already been generated
                if (filledGrids.indexOf(j) == -1) {
                    filledGrids.add(j);
                
                    hit = myMap.hit(x,y);

                }else {//Generates new co-ordinant
                    getAttack(remainingShips,miss, lose, hit);
                }

                return new int [] {x,y};
            } else if (hit == true){


                //makes sure the point isn't against a wall
                if (x > 0 && x < 9 && y > 0 && y < 9){
                    //Searches for the ship and seeks out after it
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);
                        
                        if (miss[0] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }

                    }

                }else if (x == 0 && x < 9 && y > 0 && y < 9){//Searches for ship if ship is against a wall
                    miss[0] = false;
                    if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                         xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                      ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }

                    }

                }else if (x > 0 && x == 9 && y > 0 && y < 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);

                        if (miss[0] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                        miss[2] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                       ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }
                    }

                }else if (x > 0 && x < 9 && y == 0 && y < 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);

                        if (miss[0] = false) {
                            xtemp = x;
                        }

                        miss[1] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                         xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }
                    }
                }else if (x > 0 && x < 9 && y > 0 && y == 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);

                        if (miss[0] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                         xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }

                }else if (x == 0 && x < 9 && y == 0 && y < 9){ //Searches for ship if ship is against two walls
                    miss[0] = false;
                    miss[1] = false;
                    if (miss[0] == false && miss [1] == false && miss [2] == true){
                        xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }

                    }

                }else if (x == 0 && x < 9 && y > 0 && y == 9){ //Searches for ship if ship is against two walls
                    miss[0] = false;
                    if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                         xtemp++;
                        miss[2] = myMap.hit(xtemp,y);

                        if (miss[2] = false) {
                            xtemp = x;
                        }

                    }

                }else if (x > 0 && x == 9 && y == 0 && y < 9){ //Searches for ship if ship is against two walls
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);

                        if (miss[0] = false) {
                            xtemp = x;
                        }

                        miss[1] = false;
                        miss[2] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        ytemp++;
                        miss[3] = myMap.hit(x,ytemp);

                        if (miss[3] = false) {
                            ytemp = y;
                        }

                    }

                }else if (x > 0 && x == 9 && y > 0 && y == 9){ //Searches for ship if ship is against two walls
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        xtemp--;
                        miss[0] = myMap.hit(xtemp,y);

                        if (miss[0] = false) {
                            xtemp = x;
                        }

                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        ytemp--;
                        miss[1] = myMap.hit(x,ytemp);

                        if (miss[1] = false) {
                            ytemp = y;
                        }

                    }

                }

                //Checks to make sure spot has not been guessed yet and commits
                if (miss[0] == true && miss [1] == true && miss [2] == true){

                    check = myMap.checkSunkenShip(xtemp, y) != null;

                    j = xtemp + "," + y;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(xtemp,y);

                        return new int [] {x,y};

                    }else {
                        getAttack(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                    
                    j = x + "," + ytemp;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(x,ytemp);

                        return new int [] {x,y};

                    }else {
                        getAttack(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                    
                    j = xtemp + "," + y;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(xtemp,y);

                        return new int [] {x,y};

                    }else {
                        getAttack(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                    
                    j = x + "," + ytemp;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(x,ytemp); 

                        return new int [] {x,y};

                    }else {
                        getAttack(remainingShips,miss, lose, hit);
                    }

                }

            }

        }
        return null;

    }

}
