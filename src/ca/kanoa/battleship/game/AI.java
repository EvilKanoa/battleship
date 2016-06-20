package ca.kanoa.battleship.game;

import java.util.ArrayList;

/**
 * Creates a class to control the AI
 * @author Evan
 */
public class AI {

    //Ceates variables for use in the class
    Boolean win = false;
    Boolean[] remainingShips = {false,false,false,false,false}; //remaining ships is a array which holds whether or not the ships are sunk 0 is carrier, 1 is battleship, 2 is cruiser, 3 is sub and 4 is pt boat
    Boolean hit = false;
    Boolean check = false;
    Boolean[] miss = {false,false,false,false};
    int x = 0;
    int xtemp = 0;
    long temp = 0;
    int y;
    int ytemp = 0;
    ArrayList filledGrids = new ArrayList();
    private Map myMap;

    //Creates a method to conrol the AI's shooting
    public void EnemyShoot(Boolean[] remainingShips, Boolean [] miss, Boolean lose, Boolean hit){

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
                if (remainingShips[0] == true || remainingShips[1] == true){

                    //Partitions the shooting to target the Carrier and the Battleship
                    multiplier = 4;
                    numberOfTiles = 20;

                }else if (remainingShips[2] == true || remainingShips[3] == true){ //Checks to see if the Cruiser and the Sub are still in play

                    //Partitions the shooting to target the Cruiser and the Sub
                    multiplier = 3;
                    numberOfTiles = 33;

                }else if (remainingShips[4] == true){ //Checks to see if the destroyer is still in play

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
                    EnemyShoot(remainingShips,miss, lose, hit);
                }

            } else if (hit == true){


                //makes sure the point isn't against a wall
                if (x > 0 && x < 9 && y > 0 && y < 9){
                    //Searches for the ship and seeks out after it
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                        check = myMap.checkSunkenShip(x, y);
                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);

                    }

                }else if (x == 0 && x < 9 && y > 0 && y < 9){//Searches for ship if ship is against a wall
                    miss[0] = false;
                    if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);

                    }

                }else if (x > 0 && x == 9 && y > 0 && y < 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);
                        miss[2] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);
                    }

                }else if (x > 0 && x < 9 && y == 0 && y < 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                        miss[1] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);
                    }
                }else if (x > 0 && x < 9 && y > 0 && y == 9){ //Searches for ship if ship is against a wall
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }

                }else if (x == 0 && x < 9 && y == 0 && y < 9){ //Searches for ship if ship is against two walls
                    miss[0] = false;
                    miss[1] = false;
                    if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);

                    }

                }else if (x == 0 && x < 9 && y > 0 && y == 9){ //Searches for ship if ship is against two walls
                    miss[0] = false;
                    if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                        x++;
                        miss[2] = myMap.hit(x,y);
                    }

                }else if (x > 0 && x == 9 && y == 0 && y < 9){ //Searches for ship if ship is against two walls
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                        miss[1] = false;
                        miss[2] = false;
                    }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                        y++;
                        miss[3] = myMap.hit(x,y);

                    }

                }else if (x > 0 && x == 9 && y > 0 && y == 9){ //Searches for ship if ship is against two walls
                    if (miss[0] == true && miss [1] == true && miss [2] == true){
                        x--;
                        miss[0] = myMap.hit(x,y);
                    }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                        y--;
                        miss[1] = myMap.hit(x,y);

                    }

                }

                //Checks to make sure spot has not been guessed yet
                if (miss[0] == true && miss [1] == true && miss [2] == true){
                    
                    j = xtemp + "," + y;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(xtemp,y); 
                    }else {
                        EnemyShoot(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == true && miss [2] == true){
                    
                    j = x + "," + ytemp;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(x,ytemp); 
                    }else {
                        EnemyShoot(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == false && miss [2] == true){
                    
                    j = xtemp + "," + y;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(xtemp,y); 
                    }else {
                        EnemyShoot(remainingShips,miss, lose, hit);
                    }
                    
                }else if (miss[0] == false && miss [1] == false && miss [2] == false){
                    
                    j = x + "," + ytemp;
                
                    if (filledGrids.indexOf(j) == -1) {
                        filledGrids.add(j);

                        hit = myMap.hit(x,ytemp); 
                    }else {
                        EnemyShoot(remainingShips,miss, lose, hit);
                    }

                }

            }

        }

    }

}
