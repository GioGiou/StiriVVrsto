import java.util.Arrays;
class MinMaxAlfaBetaAgent implements Agent{
    int [][] board;
    AlphaBetaNode root;

    MinMaxAlfaBetaAgent(){
        board = new int[Connect4JFrame.MAXROW][Connect4JFrame.MAXCOL];
    }
    
    public void setBoard(int[][] gameBoard){
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(gameBoard[i], board[i].length); 
          }
    }

    public boolean isFirstMove(){
        for (int i = 0; i < Connect4JFrame.MAXCOL; i++) {
            if(board[Connect4JFrame.MAXROW -1][i]>0){
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int nextMove(){
        if(isFirstMove()){return 3;}
        AlphaBetaNode.myColour=Connect4JFrame.activeColour;
        root = new AlphaBetaNode(-1,7,true,board, Connect4JFrame.activeColour);
        int idx = 3;
        int value = root.getValue();
        for(int i = 0; i<root.child.length;i++){
            if(root.child[i]!= null)
                if(root.child[i].value==value){
                    idx = i;
                    break;
                }
        }
        root =null;
        return idx ; //return i;
    }
}
