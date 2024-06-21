
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SudokuGenome {
    private int[] grid;
    private int fitness;

    public SudokuGenome(){}
    public SudokuGenome(int[] unsolvedGrid){
        this.grid = Arrays.copyOf(unsolvedGrid, unsolvedGrid.length);
        generateSudokuGenome();
    }
    public int[] getSubGridNumbers(int index){
        int[] numbers = new int[9];

        int start = (index % 3) * 3 + (index/3) * 27;

        int counter = 0;
        for(int i=start;i<start+27;i=i+9){
            for(int j=i;j<i+3;j++){
                numbers[counter] = grid[j];
                counter++;
            }
        }

        return numbers;
    }
    public void generateSudokuGenome(){

        for(int k=0;k<9;k++){
            int[] subGridNumbers = getSubGridNumbers(k);
            Set<Integer> allowedNumbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
            for(int number : subGridNumbers){
                allowedNumbers.remove(number);
            }

            generateSubGrid(k, allowedNumbers);
        }

    }

    private void generateSubGrid(int index, Set<Integer> allowedNumbers) {
        Random random = new Random();
        int start = (index % 3) * 3 + (index/3) * 27;

        for(int i=start;i<start+27;i=i+9){
            for(int j=i;j<i+3;j++){
                if(grid[j] == 0){
                    int randomIndex = random.nextInt(allowedNumbers.size());
                    int randomNumber = (int) allowedNumbers.toArray()[randomIndex];
                    grid[j] = randomNumber;
                    allowedNumbers.remove(randomNumber);
                }
            }
        }
    }

    public int[] getGrid() {
        return grid;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public void setGrid(int[] grid) {
        this.grid = grid;
    }
}
