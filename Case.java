import java.util.Set;

public class Case {

    // Case has a collection of seeds
    private int redSeeds;
    private int blueSeeds;
    private int transparentSeeds;

    public Case() {

        this.redSeeds           = 2;
        this.blueSeeds          = 2;
        this.transparentSeeds   = 1;

    }

    public Case clone() {

        Case clonedCase             = new Case();

        clonedCase.redSeeds         = this.redSeeds;
        clonedCase.blueSeeds        = this.blueSeeds;
        clonedCase.transparentSeeds = this.transparentSeeds;

        return clonedCase;

    }

    // Getters

    public int getRedSeeds() {
        return redSeeds;
    }

    public int getBlueSeeds() {
        return blueSeeds;
    }

    public int getTransparentSeeds() {
        return transparentSeeds;
    }

    public int getTotalSeeds() {
        return redSeeds + blueSeeds + transparentSeeds;
    }

    public boolean isEmpty() {
        return this.getTotalSeeds() == 0;
    }

    public String showSeeds() {
        return "R:" + redSeeds + " B:" + blueSeeds + " T:" + transparentSeeds;
    }

    // Input seeds in case
    public void inputSeeds(int seeds, String color) {

        switch (color) {
            case "R":
                this.redSeeds += seeds;
                break;
            case "B":
                this.blueSeeds += seeds;
                break;
            case "TR", "TB", "TC":
                this.transparentSeeds += seeds;
                break;
            default:
                break;
        }
    }

    public int removeSeeds(String color) {

        int seeds = 0;

        switch (color) {

            case "R":
                seeds = this.redSeeds;
                this.redSeeds = 0;
                return seeds;

            case "B":
                seeds = this.blueSeeds;
                this.blueSeeds = 0;
                return seeds;

            case "TR", "TC", "TB":
                seeds = this.transparentSeeds;
                this.transparentSeeds = 0;
                return seeds;

            default:
                return seeds;
        }
    }

    public boolean hasColor(String color) {
        switch (color) {
            case "R":
                return redSeeds > 0;
            case "B":
                return blueSeeds > 0;
            case "TR","TB":
                return transparentSeeds > 0; // Assuming you have a variable named transparentRedSeeds
            default:
                return false;
        }
    }
    public void emptyCase() {
        this.redSeeds = 0;
        this.blueSeeds = 0;
        this.transparentSeeds = 0;
    }
}
