

import java.util.*;

public class Sudoku {

    private static class NumberPosition{
        public int i;
        public int j;

        public NumberPosition(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean isSamePosition(int x, int y){
            if(x == i && y == j)
                return true;
            return false;
        }
    }

    public static int[] generateSudoku(int minClues, int maxClues){
        Random random = new Random();
        int clues = -1;

        clues = random.nextInt(maxClues - minClues + 1) + minClues;
        SudokuController.CLUES = clues;

        List<NumberPosition> numberPositions = new ArrayList<>();
        int[][] grid = new int[9][9];

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++)
                grid[i][j] = 0;
        }

        int[][] solvedGrid = generate();

        boolean sudokuGenerated = false;
        do{
            int randomX = random.nextInt(9);
            int randomY = random.nextInt(9);

            boolean isAlreadyUsed = false;
            for(NumberPosition np : numberPositions){
                if(np.isSamePosition(randomX, randomY))
                    isAlreadyUsed = true;
            }
            if(!isAlreadyUsed){
                numberPositions.add(new NumberPosition(randomX, randomY));
                clues--;
            }

            if(clues == 0)
                sudokuGenerated = true;

        }while(!sudokuGenerated);

        for(NumberPosition np : numberPositions)
            grid[np.i][np.j] = solvedGrid[np.i][np.j];


        return Arrays.stream(grid).flatMapToInt(Arrays::stream).toArray();
    }

    public static int[] getSubGridIndexes(int index, int[] grid){
        int[] arr = new int[9];

        int start = (index % 3) * 3 + (index/3) * 27;

        int counter = 0;
        for(int i=start;i<start+27;i=i+9){
            for(int j=i;j<i+3;j++){
                arr[counter] = j;
                counter++;
            }
        }

        return arr;
    }

    public static int[][] convertTo2DGrid(int[] g){
        int[][] grid = new int[9][9];
        int counter = 0;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                grid[i][j] = g[counter];
                counter++;
            }
        }

        return grid;
    }

    public static int[] convertTo1DGrid(int[][] g){
        int[] grid = new int[81];
        int counter = 0;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                grid[counter] = g[i][j];
                counter++;
            }
        }

        return grid;
    }

    public static void printSudokuGrid(int grid[]){
        for(int i=0;i<81;i++){
            if(i%9==0 && i>0)
                System.out.println();

            if(i%3 == 0 && i%9!=0)
                System.out.print(" ");

            if(i%27 == 0 && i>0)
                System.out.println();

            if(grid[i] != 0)
                System.out.print(grid[i] + " ");
            else
                System.out.print(". ");
        }
        System.out.println();
    }




    private static int[][] generate() {
        int[][] sudoku = new int[9][9];
        fillDiagonalBlocks(sudoku);
        solveSudoku(sudoku);
        return sudoku;
    }

    private static void fillDiagonalBlocks(int[][] sudoku) {
        for (int block = 0; block < 9; block += 3) {
            fillBlock(sudoku, block, block);
        }
    }

    private static void fillBlock(int[][] sudoku, int row, int col) {
        Random random = new Random();
        int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        shuffleArray(numbers);

        int index = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                sudoku[i][j] = numbers[index++];
            }
        }
    }

    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static boolean solveSudoku(int[][] sudoku) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (isEmpty) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isSafe(sudoku, row, col, num)) {
                sudoku[row][col] = num;

                if (solveSudoku(sudoku)) {
                    return true;
                } else {
                    sudoku[row][col] = 0;
                }
            }
        }

        return false;
    }

    private static boolean isSafe(int[][] sudoku, int row, int col, int num) {
        return !usedInRow(sudoku, row, num) && !usedInCol(sudoku, col, num) && !usedInBox(sudoku, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] sudoku, int row, int num) {
        for (int col = 0; col < 9; col++) {
            if (sudoku[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInCol(int[][] sudoku, int col, int num) {
        for (int row = 0; row < 9; row++) {
            if (sudoku[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInBox(int[][] sudoku, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (sudoku[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}
