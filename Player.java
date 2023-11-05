public class Player {

    // Player has a name and a number
    private String  name;
    private int     number;
    private int     seeds = 0;

    // Constructor
    public Player(String name, int number) {
        this.name   = name;
        this.number = number;
    }

    public Player clone() {

        Player clonedPlayer = new Player(this.name, this.number);
        clonedPlayer.seeds  = this.seeds;

        return clonedPlayer;

    }

    // Getters
    public String getName() {
        return name;
    }

    public int getNumber(){
        return number;
    }

    public int getSeeds(){
        return seeds;
    }

    public void takeSeeds(int seeds){
        this.seeds += seeds;
    }
}
