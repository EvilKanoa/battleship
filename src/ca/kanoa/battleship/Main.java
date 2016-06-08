package ca.kanoa.battleship;


public class Main {

    public static void main(String[] args) {
        System.out.println("Hello battleship!");
    }

    public static void enemyshoot(int remainingShips, String [] carrier, String [] battleship, String [] cruiser, String [] sub, String [] ptCruiser, Boolean miss, Boolean win, Boolean hit){

        if (win == false){

            if (miss == false){

                if (hit == false){

                    hit = enemyshoot(hit);

                    if (hit == true){
                        win = check(carrier,battleship,cruiser,sub,ptCruiser);
                    }

                } else if (hit == true){

                    hit = enemyhit(hit);

                    if (hit == true){
                        win = check(carrier,battleship,cruiser,sub,ptCruiser);
                    }

                }

            }

        } else if (win == true){
            loseScreen(win);
        }

    }

    public static Boolean enemyshoot (Boolean hit){
        return true;
    }

    public static Boolean enemyhit(Boolean hit){
        return true;
    }

    public static Boolean check(String [] carrier, String [] battleship, String [] cruiser, String [] sub, String [] ptCruiser){
        int[] counter = {0,0,0,0,0};
        int counter2 = 0;

        for(int i = 0; i<5; i++){
            if (carrier[i].equals("")){
                counter[0]++;
            }
        }

        if (counter[0] == 5 ){
            counter2++;
        }

        for(int i = 0; i<4; i++){
            if (battleship[i].equals("")){
                counter[1]++;
            }
        }
        if (counter[1] == 4 ){
            counter2++;
        }

        for(int i = 0; i<3; i++){
            if (cruiser[i].equals("")){
                counter[2]++;
            }
        }
        if (counter[2] == 3 ){
            counter2++;
        }

        for(int i = 0; i<3; i++){
            if (sub[i].equals("")){
                counter[3]++;
            }
        }
        if (counter[3] == 3 ){
            counter2++;
        }

        for(int i = 0; i<2; i++){
            if (ptCruiser[i].equals("")){
                counter[4]++;
            }
        }
        if (counter[4] == 2 ){
            counter2++;
        }

        if (counter2 == 5){
            return true;
        }else{
            return false;
        }

    }
    
    public static Boolean binarySearch(String [] A, int left, int right, String V){
         int middle;
         if (left > right) {
             return false;
         }

         middle = (left + right)/2;
         int compare = V.compareTo(A[middle]);
         if (compare == 0) {
             return true;
         }
         if (compare < 0) {
             return binarySearch(A, left, middle-1, V);
         } else {
             return binarySearch(A, middle + 1, right, V);
         }
     }

    public static void loseScreen(Boolean win){

    }

}
