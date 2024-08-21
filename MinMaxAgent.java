import java.util.Arrays;
class MinMaxAgent implements Agent{
    int [][] board;
    Node root;

    MinMaxAgent(){
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
        Node.myColour=Connect4JFrame.activeColour;
        root = new Node(-1,6,true,board, Connect4JFrame.activeColour);
        int max = Integer.MIN_VALUE;
        int idx = 3;
        for(int i = 0; i<root.child.length;i++){
            if(root.child[i]!= null)
                if(root.child[i].value>max){
                    max = root.child[i].value;
                    idx = i;
                }
        }
        root =null;
        System.out.print("");
        return idx ; //return i;
    }
}
