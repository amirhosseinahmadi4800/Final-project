
public class Information {
    private int clues;

    private int evolution;

    private boolean status;

    private int averageEvolution;

    private int minEvolution;

    private int maxEvolution;

    public Information() {}

    public Information(int clues, int evolution, boolean status, int averageEvolution, int minEvolution, int maxEvolution) {
        this.clues = clues;
        this.evolution = evolution;
        this.status = status;
        this.averageEvolution = averageEvolution;
        this.minEvolution = minEvolution;
        this.maxEvolution = maxEvolution;
    }

    public int getClues() {
        return clues;
    }

    public int getEvolution() {
        return evolution;
    }

    public boolean isStatus() {
        return status;
    }

    public int getAverageEvolution() {
        return averageEvolution;
    }

    public int getMinEvolution() {
        return minEvolution;
    }

    public int getMaxEvolution() {
        return maxEvolution;
    }

    public void setClues(int clues) {
        this.clues = clues;
    }

    public void setEvolution(int evolution) {
        this.evolution = evolution;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAverageEvolution(int averageEvolution) {
        this.averageEvolution = averageEvolution;
    }

    public void setMinEvolution(int minEvolution) {
        this.minEvolution = minEvolution;
    }

    public void setMaxEvolution(int maxEvolution) {
        this.maxEvolution = maxEvolution;
    }
}
