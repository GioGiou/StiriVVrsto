
class RandomAgent implements Agent{
    RandomAgent(){}
    
    @Override
    public void setBoard(int[][] gameBoard){}
    
    @Override
    public int nextMove(){
        return (int) (7*Math.random()) +1 ;
    }
}
