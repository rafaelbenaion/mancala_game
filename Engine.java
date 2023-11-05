import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Engine {

    // Engine has a board and two players
    private Board       board;
    private Player      player1;
    private Player      player2;
    private Integer     turn        = 0;
    private boolean     gameStatus  = true;


    // Constructor
    public Engine() {

        board   = new Board();
        player1 = new Player("Player 1", 1);
        player2 = new Player("Player 2", 2);

    }

    public Engine cloneEngine() {

        Engine clonedEngine     = new Engine();

        clonedEngine.board      = this.board.clone();
        clonedEngine.player1    = this.player1.clone();
        clonedEngine.player2    = this.player2.clone();
        clonedEngine.turn       = this.turn;
        clonedEngine.gameStatus = this.gameStatus;

        return clonedEngine;
    }

    // Getters
    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void play() {

        MinMax AI = new MinMax();

        while (gameStatus) {


            System.out.println("\n" +
                    "Player 1: " + player1.getSeeds() +
                    " | Player 2: " + player2.getSeeds() +
                    "\n");

            if (turn == 0) {
                System.out.println("Player 1 turn: ");
            } else {
                System.out.println("Player 2 turn: ");
            }

            board.showBoard();

            //Read input text

            System.out.println("Choice: ");

            String  input   = "";

            if (turn == 0) {

                input = AI.findBestMove(this, 0);
/*
                if (input == null) {
                    List<String> moves = AI.getLegalMoves(this, 0);

                    // Choose random move inside the list
                    // Check if moves is not empty
                    if (!moves.isEmpty()) {
                        System.out.println("No moves available");

                    }
                    int randomIndex = (int) (Math.random() * moves.size());
                    input = moves.get(randomIndex);
                }
*/
                System.out.println("AI input : " + input);

                /*
                Scanner scanner = new Scanner(System.in);
                input           = scanner.nextLine();
                */
            }else{
                /*
                Scanner scanner = new Scanner(System.in);
                input           = scanner.nextLine();
                */

                int numberCase = 0;
                numberCase = (int) (Math.random() * 8 + 1) * 2;

                String color = "";
                switch ((int) (Math.random() * 4 + 1)) {
                    case 1:
                        color = "R";
                        break;
                    case 2:
                        color = "B";
                        break;
                    case 3:
                        color = "TR";
                        break;
                    case 4:
                        color = "TB";
                        break;
                }

                input = numberCase + color;
                System.out.println("Player 2 input : " + input);
            }

            // Mode aleatoire pour tester
            /* ---------------------------------------------------------------------------------

            String input = "";

            // Aleatory input, for testing purposes
            int numberCase = 0;

            if (turn == 0) {
                // Get a random number between 1 and 16 that is odd
                numberCase = (int) (Math.random() * 8 + 1) * 2 - 1;
            } else {
                // Get a random number between 1 and 16 that is even
                numberCase = (int) (Math.random() * 8 + 1) * 2;
            }

            // Get a random color between R, B, TR or TB
            String color = "";
            switch ((int) (Math.random() * 4 + 1)) {
                case 1:
                    color = "R";
                    break;
                case 2:
                    color = "B";
                    break;
                case 3:
                    color = "TR";
                    break;
                case 4:
                    color = "TB";
                    break;
            }

            input = numberCase + color;
            System.out.println("Random input : " + input);
            System.out.println("Minmax input : " + findBestMoveForPlayer1());

             --------------------------------------------------------------------------------- */


            // If move successful, change turn

            if (move(input, turn)) {

                // Next player turn modulo 2
                turn = (turn + 1) % 2;

            }
        }

        System.out.println("\n ------------------------------------ \n");
        System.out.println("\n ------------------------------------ \n");
        System.out.println("\n ------------------------------------ \n");

        this.board.showBoard();
        System.out.println("\n" +
                "Player 1: " + player1.getSeeds() +
                " | Player 2: " + player2.getSeeds() +
                "\n");

    }

    public void checkWinner() {

        if (this.player1.getSeeds() > 40) {
            //System.out.println("\nPlayer 1 wins!\n");
            this.gameStatus = false;
        }
        if (this.player2.getSeeds() > 40) {
            //System.out.println("\nPlayer 2 wins!\n");
            this.gameStatus = false;
        }
        if (this.player1.getSeeds() == 40 && this.player2.getSeeds() == 40) {
            //System.out.println("\nDraw! Nobody won.\n");
            this.gameStatus = false;
        }
        if (this.board.getTotalSeeds() < 10) {

            if (this.player1.getSeeds() > this.player2.getSeeds()) {
                //System.out.println("\nPlayer 1 wins!\n");
                this.gameStatus = false;
            } else if (this.player1.getSeeds() < this.player2.getSeeds()) {
                //System.out.println("\nPlayer 2 wins!\n");
                this.gameStatus = false;
            } else if (this.player1.getSeeds() == this.player2.getSeeds()) {
                //System.out.println("\nDraw! Nobody won.\n");
                this.gameStatus = false;
            }
        }

        if (this.getBoard().getAllEvenSeeds() == 0) {
            this.player1.takeSeeds(this.board.getTotalSeeds());
            this.board.emptyBoard();
            this.gameStatus = false;
        }

    }

    // Fonction to check if the player has still moves available
    public boolean hasMoves(int player) {
        List<String> moves = getLegalMoves(player);
        return !moves.isEmpty();
    }

    public boolean move(String input, int player) {

        // Take number inside the input
        String[] numbers = input.split("\\D+");
        int numberCase = Integer.parseInt(numbers[0]);
        int seeds = 0;

        // Take words inside the input
        String[] words = input.split("\\d+");
        String color = words[1];

        // Check if number between 1 and 16
        if (numberCase < 1 || numberCase > 16) {
            System.out.println("Invalid case number input, try again.");
            return false;
        }

        // Check if color is R, B, TC, TR or TB
        if (!color.equals("R") && !color.equals("B") && !color.equals("TR") && !color.equals("TB")) {
            System.out.println("Invalid color input, try again.");
            return false;
        }

        // Check if the player can select this case
        if (player == 0 && (numberCase % 2 == 0)) {
            System.out.println("Invalid case selected, try again. Case :" + numberCase);
            System.out.println("Player :" );
            return false;
        } else if (player == 1 && (numberCase % 2 == 1)) {
            System.out.println("Invalid case selected, try again. Case :" + numberCase);
            return false;
        }

        seeds = board.getCase(numberCase).removeSeeds(color);

        distribution(color, seeds, turn, numberCase);

        /*
        if (this.getBoard().getAllEvenSeeds() == 0) {
            this.player1.takeSeeds(this.board.getTotalSeeds());
            this.board.emptyBoard();
            this.gameStatus = false;
        }

        if (this.getBoard().getAllOddSeeds() == 0) {
            this.player2.takeSeeds(this.board.getTotalSeeds());
            this.board.emptyBoard();
            this.gameStatus = false;
        }
        */
        //checkWinner();

        return true;
    }

    public void capturing(int position, int player) {

        Case[] cases = this.getBoard().getCases();
        position = (position - 1) % 17;

        if (position == 0) {
            position = 16;
        }

        // Check if final case has 2 or 3 seeds
        if (cases[position].getTotalSeeds() == 2 || cases[position].getTotalSeeds() == 3) {
            if (player == 0) {
                this.player1.takeSeeds(cases[position].removeSeeds("R"));
                this.player1.takeSeeds(cases[position].removeSeeds("B"));
                this.player1.takeSeeds(cases[position].removeSeeds("TC"));
            } else if (player == 1) {
                this.player2.takeSeeds(cases[position].removeSeeds("R"));
                this.player2.takeSeeds(cases[position].removeSeeds("B"));
                this.player2.takeSeeds(cases[position].removeSeeds("TC"));
            }

            //Check if game is over
            checkWinner();

            //Check if past case has 2 or 3 seeds
            capturing(position, player);
        }
    }

    // Solve 0 case problem
    public int incrementPosition(int position) {

        position = (position + 1) % 17;

        if (position == 0) {
            position = 1;
        }

        return position;
    }

    public void distribution(String color, int quantity, int player, int caseNumber) {

        // Put one seed in each next case
        Case[] cases = this.getBoard().getCases();

        int position = incrementPosition(caseNumber);

        if (color.equals("R") || color.equals("TR")) {

            while (quantity > 0) {

                if (position != caseNumber) {

                    cases[position].inputSeeds(1, color);
                    quantity--;

                }
                position = incrementPosition(position);
            }

            capturing(position, player);

        } else if (color.equals("B") || color.equals("TB")) {

            switch (player) {
                case 1:
                    while (quantity > 0) {

                        if (position % 2 == 1 && position != caseNumber) {

                            cases[position].inputSeeds(1, color);
                            quantity--;

                        }
                        position = incrementPosition(position);
                    }
                    capturing(position, player);
                    break;
                case 0:
                    while (quantity > 0) {

                        if (position % 2 == 0 && position != caseNumber) {

                            cases[position].inputSeeds(1, color);
                            quantity--;

                        }
                        position = incrementPosition(position);
                    }
                    capturing(position, player);
                    break;
            }
        }

    }


    public boolean isValidMove(int player, int caseNumber, String color) {
        // For player 0 is valid if the caseNumber is odd
        // For player 1 is valid if the caseNumber is even

        // Check if the case is empty
        if (this.getBoard().getCase(caseNumber).isEmpty()) {
            return false;
        }

        // Check if the selected color is available in the case
        if (!this.getBoard().getCase(caseNumber).hasColor(color)) {
            return false;
        }


        // Check if number between 1 and 16
        if (caseNumber < 1 || caseNumber > 16) {
            return false;
        }

        // Check if color is valid
        if (!color.equals("R") && !color.equals("B") && !color.equals("TR") && !color.equals("TB")) {
            return false;
        }

        if (player == 0) {
            return caseNumber % 2 == 1;
        }
        else if (player == 1) {
            return caseNumber % 2 == 0;
        }

        return false;
    }

    // Getters
    public boolean getGameStatus() {
        return gameStatus;
    }

    public boolean isGameOver() {

        if (this.player1.getSeeds() > 40) {
            return true;
        }
        if (this.player2.getSeeds() > 40) {
            return true;
        }
        if (this.player1.getSeeds() == 40 && this.player2.getSeeds() == 40) {
            return true;
        }
        if (this.board.getTotalSeeds() < 10) {

            if (this.player1.getSeeds() > this.player2.getSeeds()) {
                return true;
            } else if (this.player1.getSeeds() < this.player2.getSeeds()) {
                return true;
            } else if (this.player1.getSeeds() == this.player2.getSeeds()) {
                return true;
            }
        }

        return false;
    }

    public List<String> getLegalMoves(int player) {
        List<String> legalMoves = new ArrayList<>();
        Case[] cases = this.getBoard().getCases();

        for (int caseNumber = 1; caseNumber <= 16; caseNumber++) {
            if (isValidMove(player, caseNumber, "R")) {
                legalMoves.add(caseNumber + "R");
            }
            if (isValidMove(player, caseNumber, "B")) {
                legalMoves.add(caseNumber + "B");
            }
            if (isValidMove(player, caseNumber, "TR")) {
                legalMoves.add(caseNumber + "TR");
            }
            if (isValidMove(player, caseNumber, "TB")) {
                legalMoves.add(caseNumber + "TB");
            }
        }

        return legalMoves;
    }
}