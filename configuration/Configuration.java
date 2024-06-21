
import java.util.Objects;

public class Configuration {
    private int minClues;
    private int maxClues;
    private int populationSize;
    private SelectionType selectionType;
    private CrossoverType crossoverType;
    private int mutationChance;
    private int giveUpAtEvolution;

    public Configuration(int minClues, int maxClues, int populationSize, SelectionType selectionType,
                         CrossoverType crossoverType, int mutationChance, int giveUpAtEvolution) {
        this.minClues = minClues;
        this.maxClues = maxClues;
        this.populationSize = populationSize;
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationChance = mutationChance;
        this.giveUpAtEvolution = giveUpAtEvolution;
    }

    public int getMinClues() {
        return minClues;
    }

    public int getMaxClues() {
        return maxClues;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public CrossoverType getCrossoverType() {
        return crossoverType;
    }

    public int getMutationChance() {
        return mutationChance;
    }

    public int getGiveUpAtEvolution() {
        return giveUpAtEvolution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return minClues == that.minClues && maxClues == that.maxClues && populationSize == that.populationSize && mutationChance == that.mutationChance && giveUpAtEvolution == that.giveUpAtEvolution && selectionType == that.selectionType && crossoverType == that.crossoverType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minClues, maxClues, populationSize, selectionType, crossoverType, mutationChance, giveUpAtEvolution);
    }
}
