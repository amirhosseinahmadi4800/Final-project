public class SudokuBoard {

   private ArrayList<ArrayList<Integer>> numberPool;
    private boolean[][] forbiddenMove;
    private Random rand;
    private int[][] board;
    private int[][] originalBoard;
    public static int outpot[][];

    @FXML
    private TextField text26 ;
    @FXML
    private TextField text69;
    @FXML
    private TextField text25;
    @FXML
    private TextField text28;
    @FXML
    private TextField text27;
    @FXML
    private TextField text29;
    @FXML
    private TextField text62;
    @FXML
    private TextField text61;
    @FXML
    private TextField text64;
    @FXML
    private TextField text63;
    @FXML
    private TextField text66;
    @FXML
    private TextField text22;
    @FXML
    private TextField text65;
    @FXML
    private TextField text21;
    @FXML
    private TextField text68;
    @FXML
    private TextField text24;
    @FXML
    private TextField text67;
    @FXML
    private TextField text23;
    @FXML
    private TextField text59;
    @FXML
    private TextField text15;
    @FXML
    private TextField text58;
    @FXML
    private TextField text14;
    @FXML
    private TextField text17;
    @FXML
    private TextField text16;
    @FXML
    private TextField text19;
    @FXML
    private TextField text18;
    @FXML
    private TextField text95;
    @FXML
    private TextField text51;
    @FXML
    private TextField text94;
    @FXML
    private TextField text97;
    @FXML
    private TextField text53;
    @FXML
    private TextField text96;
    @FXML
    private TextField text52;
    @FXML
    private TextField text99;
    @FXML
    private TextField text55;
    @FXML
    private TextField text11;
    @FXML
    private TextField text98;
    @FXML
    private TextField text54;
    @FXML
    private TextField text57;
    @FXML
    private TextField text13;
    @FXML
    private TextField text56;
    @FXML
    private TextField text12;
    @FXML
    private TextField text91;
    @FXML
    private TextField text93;
    @FXML
    private TextField text92;
    @FXML
    private TextField text48;
    @FXML
    private TextField text47;
    @FXML
    private TextField text49;
    @FXML
    private TextField text84;
    @FXML
    private TextField text83;
    @FXML
    private TextField text86;
    @FXML
    private TextField text42;
    @FXML
    private TextField text85;
    @FXML
    private TextField text41;
    @FXML
    private TextField text88;
    @FXML
    private TextField text44;
    @FXML
    private TextField text87;
    @FXML
    private TextField text43;
    @FXML
    private TextField text46;
    @FXML
    private TextField text89;
    @FXML
    private TextField text45;
    @FXML
    private TextField text82;
    @FXML
    private TextField text81;
    @FXML
    private TextField text37;
    @FXML
    private TextField text36;
    @FXML
    private TextField text39;
    @FXML
    private TextField text38;
    @FXML
    private TextField text73;
    @FXML
    private TextField text72;
    @FXML
    private TextField text75;
    @FXML
    private TextField text31;
    @FXML
    private TextField text74;
    @FXML
    private TextField text77;
    @FXML
    private TextField text33;
    @FXML
    private TextField text76;
    @FXML
    private TextField text32;
    @FXML
    private TextField text79;
    @FXML
    private TextField text35;
    @FXML
    private TextField text78;
    @FXML
    private TextField text34;
    @FXML
    private TextField text71;
    @FXML
    private Button buttenStart;

private void defaultBoard() {
        board = new int[][]{
                {Integer.parseInt(text11.getText()), Integer.parseInt(text12.getText()), Integer.parseInt(text13.getText()), Integer.parseInt(text14.getText()), Integer.parseInt(text15.getText()), Integer.parseInt(text16.getText()), Integer.parseInt(text17.getText()), Integer.parseInt(text18.getText()), Integer.parseInt(text19.getText())},
                {Integer.parseInt(text21.getText()), Integer.parseInt(text22.getText()), Integer.parseInt(text23.getText()), Integer.parseInt(text24.getText()), Integer.parseInt(text25.getText()), Integer.parseInt(text26.getText()), Integer.parseInt(text27.getText()), Integer.parseInt(text28.getText()), Integer.parseInt(text29.getText())},
                {Integer.parseInt(text31.getText()), Integer.parseInt(text32.getText()), Integer.parseInt(text33.getText()), Integer.parseInt(text34.getText()), Integer.parseInt(text35.getText()), Integer.parseInt(text36.getText()), Integer.parseInt(text37.getText()), Integer.parseInt(text38.getText()), Integer.parseInt(text39.getText())},
                {Integer.parseInt(text41.getText()), Integer.parseInt(text42.getText()), Integer.parseInt(text43.getText()), Integer.parseInt(text44.getText()), Integer.parseInt(text45.getText()), Integer.parseInt(text46.getText()), Integer.parseInt(text47.getText()), Integer.parseInt(text48.getText()), Integer.parseInt(text49.getText())},
                {Integer.parseInt(text51.getText()), Integer.parseInt(text52.getText()), Integer.parseInt(text53.getText()), Integer.parseInt(text54.getText()), Integer.parseInt(text55.getText()), Integer.parseInt(text56.getText()), Integer.parseInt(text57.getText()), Integer.parseInt(text58.getText()), Integer.parseInt(text59.getText())},
                {Integer.parseInt(text61.getText()), Integer.parseInt(text62.getText()), Integer.parseInt(text63.getText()), Integer.parseInt(text64.getText()), Integer.parseInt(text65.getText()), Integer.parseInt(text66.getText()), Integer.parseInt(text67.getText()), Integer.parseInt(text68.getText()), Integer.parseInt(text69.getText())},
                {Integer.parseInt(text71.getText()), Integer.parseInt(text72.getText()), Integer.parseInt(text73.getText()), Integer.parseInt(text74.getText()), Integer.parseInt(text75.getText()), Integer.parseInt(text76.getText()), Integer.parseInt(text77.getText()), Integer.parseInt(text78.getText()), Integer.parseInt(text79.getText())},
                {Integer.parseInt(text81.getText()), Integer.parseInt(text82.getText()), Integer.parseInt(text83.getText()), Integer.parseInt(text84.getText()), Integer.parseInt(text85.getText()), Integer.parseInt(text86.getText()), Integer.parseInt(text87.getText()), Integer.parseInt(text88.getText()), Integer.parseInt(text89.getText())},
                {Integer.parseInt(text91.getText()), Integer.parseInt(text92.getText()), Integer.parseInt(text93.getText()), Integer.parseInt(text94.getText()), Integer.parseInt(text95.getText()), Integer.parseInt(text96.getText()), Integer.parseInt(text97.getText()), Integer.parseInt(text98.getText()), Integer.parseInt(text99.getText())},};


        originalBoard = board;

      
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 0) {
                    

                    
                    forbiddenMove[i][j] = true;

                    
                    numberPool.get(j).remove(new Integer(board[i][j]));
                }
            }
        }
        fillBlanks();
    }




}