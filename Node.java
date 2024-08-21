import java.util.Arrays;

class Node{
    boolean type; // True Max, False Min
    int move;
    int value;
    int depth;
    Node child[];
    int [][] board;
    int winner;
    int activeColour;
    static int myColour;
    
    public Node(int move, int depth, boolean type, int[][] boardG, int ac){
        this.type=type;
        this.move=move;
        this.depth=depth;
        activeColour=ac;
        winner =0;
        if(this.type){
            value = Integer.MIN_VALUE;
        }
        else{
            value = Integer.MAX_VALUE;
        }
        child=new Node[7];
        board = new int[Connect4JFrame.MAXROW][Connect4JFrame.MAXCOL];
        setBoard(boardG);
        if(depth>0){
            if(!is4()){
                for(int i =0; i< child.length; i++){
                    if(board[0][i]== Connect4JFrame.BLANK){
                        int tempBoard[][] = new int[Connect4JFrame.MAXROW][Connect4JFrame.MAXCOL];
                        for (int j = 0; j < tempBoard.length; j++) {
                            tempBoard[j] = Arrays.copyOf(board[j], tempBoard[j].length); 
                        }
                        int row;
                        for (row=0; row<Connect4JFrame.MAXROW; row++){
                            if (tempBoard[row][i]>0) break;
                        }
                        if (row>0) {
                            tempBoard[--row][i]=ac;
                            if (ac==Connect4JFrame.RED){
                                ac=Connect4JFrame.YELLOW;
                            }
                            else{
                                ac=Connect4JFrame.RED;
                            }
                                
                        }
                        child[i]=new Node(i,depth-1,!type,tempBoard,ac);
                    }
                }
                int nextVal = Integer.MIN_VALUE;
                if(type){
                    nextVal = Integer.MIN_VALUE;
                }
                else{
                    nextVal = Integer.MAX_VALUE;
                }
                int idx = 3;
                for(int i =0; i< child.length; i++){
                    if(type){
                        if(child[i]!= null){
                            if(child[i].value>nextVal){
                                nextVal = child[i].value;
                                idx = i;
                            }
                        }
                    }
                    else{
                        if(child[i]!= null){
                            if(child[i].value<nextVal){
                                nextVal = child[i].value;
                                idx = i;
                            }
                        }
                    }
                }
                this.value = nextVal;
            }
            else{
                
                if(winner==myColour){
                    this.value=15000;
                }
                else{
                    this.value=-15000;

                }
            }
        }
        else{
            this.value = utility();
        }
        if(this.isLeaf() && depth>0){        
            this.value = utility();
        }
    }
    
    public void setBoard(int[][] gameBoard){
        for (int i = 0; i < board.length; i++) {
            board[i] = Arrays.copyOf(gameBoard[i], board[i].length); 
          }
    }

    public int utility(){
        int ret =0;
        int err = (int) (Math.random()*(this.move-3));
        if(is4()){
            if (winner == myColour){
                ret = ret +1500;
            }
            else{
                ret = ret - 1000;
            }
            return ret + err;
        }
        //Implementiraj pravilno utility function
        ret = ret + numberOf3InARow();
        ret = ret + 10*moveEffect();
        return ret + err;
    }
    public int moveEffect(){
        int ret =0;
        if(move <0){return -100;}
        for (int row=0; row<Connect4JFrame.MAXROW; row++) {
            int curr = board[row][this.move];
            if(curr== Connect4JFrame.BLANK){continue;}
            else{
                int moveValueDown=1;
                //Stolpec
                if(row+1<Connect4JFrame.MAXROW){
                    if(board[row+1][this.move]==curr){
                        moveValueDown=2;
                        if(row+2<Connect4JFrame.MAXROW){
                            if(board[row+2][this.move]==curr){
                                moveValueDown=4;
                                if(row+3<Connect4JFrame.MAXROW){
                                    if(board[row+3][this.move]==curr){
                                        moveValueDown=8;
                                    }
                                }
                            }
                        }
                        }
                    }
                    if(curr!=myColour){
                    moveValueDown=moveValueDown*-1;
                }
                
                ret = ret+moveValueDown;
                //Vrstica Desno
                int moveValueRight=1;
                //Stolpec
                if(this.move+1<Connect4JFrame.MAXCOL){
                    if(board[row][this.move+1]==curr){
                        moveValueRight=2;
                        if(this.move+2<Connect4JFrame.MAXROW){
                            if(board[this.move+2][this.move]==curr){
                                moveValueRight=4;
                                if(this.move+3<Connect4JFrame.MAXROW){
                                    if(board[this.move+3][this.move]==curr){
                                        moveValueRight=8;
                                    }
                                }
                            }
                        }
                        }
                    }
                
            
                if(curr!=myColour){
                    moveValueRight=moveValueRight*-1;
                }
                ret = ret+moveValueRight;
                //Vrstica Levo
                int moveValueLeft=1;
                //Stolpec
                if(this.move-1>=0){
                    if(board[row][this.move-1]==curr){
                        moveValueLeft=2;
                        if(this.move-2>=0){
                            if(board[this.move-2][this.move]==curr){
                                moveValueLeft=4;
                                if(this.move-3>=0){
                                    if(board[this.move-3][this.move]==curr){
                                        moveValueLeft=8;
                                    }
                                }
                            }
                        }
                        }
                    }
                
            
                if(curr!=myColour){
                    moveValueLeft=moveValueLeft*-1;
                }
                //Vrstica Desno
                ret = ret+moveValueLeft;
            }
           }     
            
                
            
        return ret;
    }
    public int numberOf3InARow(){
        int ret = 0;
        for (int row=0; row<Connect4JFrame.MAXROW; row++) {
                for (int col=0; col<Connect4JFrame.MAXCOL-2; col++) {
                        int curr = board[row][col];
                            if (curr>0 && curr == board[row][col+1] && curr == board[row][col+2]) {
                                if(curr == Node.myColour){
                                    ret++;
                                }
                                else{
                                    ret--;
                                }
                            }
                    }
                }
                // vertical columns
                for (int col=0; col<Connect4JFrame.MAXCOL; col++) {
                        for (int row=0; row<Connect4JFrame.MAXROW-2; row++) {
                                int curr = board[row][col];
                                if (curr>0 && curr == board[row+1][col] && curr == board[row+2][col]){
                                    if(curr == Node.myColour){
                                        ret++;
                                    }
                                    else{
                                        ret--;
                                    }
                                }
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<Connect4JFrame.MAXROW-2; row++) {
                        for (int col=0; col<Connect4JFrame.MAXCOL-2; col++) {
                                int curr = board[row][col];
                                if (curr>0 && curr == board[row+1][col+1] && curr == board[row+2][col+2]){
                                    if(curr == Node.myColour){
                                        ret++;
                                    }
                                    else{
                                        ret--;
                                    }
                                }
                        }
                }
                // diagonal upper left to lower right
                for (int row=Connect4JFrame.MAXROW-1; row>=2; row--) {
                        for (int col=0; col<Connect4JFrame.MAXCOL-2; col++) {
                                int curr = board[row][col];
                                if (curr>0 && curr == board[row-1][col+1] && curr == board[row-2][col+2]){
                                    if(curr == Node.myColour){
                                        ret++;
                                    }
                                    else{
                                        ret--;
                                    }
                                }
                        }
                }
                return ret;
    }
    
    public boolean is4(){
        for (int row=0; row<Connect4JFrame.MAXROW; row++) {
                for (int col=0; col<Connect4JFrame.MAXCOL-3; col++) {
                        int curr = board[row][col];
                            if (curr>0
                            && curr == board[row][col+1]
                            && curr == board[row][col+2]
                            && curr == board[row][col+3]) {
                                winner= curr;
                                return true;
                            }
                    }
                }
                // vertical columns
                for (int col=0; col<Connect4JFrame.MAXCOL; col++) {
                        for (int row=0; row<Connect4JFrame.MAXROW-3; row++) {
                                int curr = board[row][col];
                                if (curr>0
                                && curr == board[row+1][col]
                                && curr == board[row+2][col]
                                && curr == board[row+3][col]){
                                    winner= curr;
                                    return true;
                                }
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<Connect4JFrame.MAXROW-3; row++) {
                        for (int col=0; col<Connect4JFrame.MAXCOL-3; col++) {
                                int curr = board[row][col];
                                if (curr>0
                                && curr == board[row+1][col+1]
                                && curr == board[row+2][col+2]
                                && curr == board[row+3][col+3]){
                                    winner= curr;
                                    return true;
                                }
                        }
                }
                // diagonal upper left to lower right
                for (int row=Connect4JFrame.MAXROW-1; row>=3; row--) {
                        for (int col=0; col<Connect4JFrame.MAXCOL-3; col++) {
                                int curr = board[row][col];
                                if (curr>0
                                && curr == board[row-1][col+1]
                                && curr == board[row-2][col+2]
                                && curr == board[row-3][col+3]){
                                    winner= curr;
                                    return true;
                                }
                        }
                }
                return false;
    }
    
    public boolean isLeaf(){
        for(int i =0; i< child.length; i++){
            if(child[i]!=null){
                return false;
            }
        }        
        return true;
    }
    
}
