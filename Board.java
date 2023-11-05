public class Board {

    // Collection of cases
    private Case[] cases;

    // Constructor
    public Board() {

        cases = new Case[17];

        for (int i = 1; i < 17; i++) {
            cases[i] = new Case();
        }
    }

    public Board clone() {

        Board clonedBoard = new Board();
        clonedBoard.cases = new Case[17];

        for (int i = 1; i < 17; i++) {
            clonedBoard.cases[i] = this.cases[i].clone();
        }

        return clonedBoard;
    }

    // Getters
    public Case[] getCases() {
        return cases;
    }

    public void showBoard() {

        for (int i = 1; i < 9; i++) {
            System.out.print("| (0" + i + "): " + cases[i].showSeeds());
        }

        System.out.println();

        for (int i = 16; i >= 9; i--) {
            if(i == 9){
                System.out.print("| (0" + i + "): " + cases[i].showSeeds());
            }
            else{
                System.out.print("| (" + i + "): " + cases[i].showSeeds());
            }
        }

        System.out.println();
    }

    // Return one case
    public Case getCase(int index) {
        return cases[index];
    }

    public int getTotalSeeds(){

        int total = 0;

        for(int i = 1; i < 17; i++){
            total += cases[i].getTotalSeeds();
        }

        return total;
    }

    public int getAllEvenSeeds(){

        int total = 0;

        for(int i = 2; i < 17; i += 2){
            total += cases[i].getTotalSeeds();
        }

        return total;
    }

    public int getAllOddSeeds(){

        int total = 0;

        for(int i = 1; i < 17; i += 2){
            total += cases[i].getTotalSeeds();
        }

        return total;
    }

    public void emptyBoard(){

        for(int i = 1; i < 17; i++){
            cases[i].emptyCase();
        }

    }

}
