import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.Instant;
import java.time.Duration;


public class Connect4 {
 
        /**
        *       Program:        Connect4.java
        *       Purpose:        Stacking disk game for 2 players
        *       Creator:        Chris Clarke
        *       Created:        19.08.2007
        *       Modified:       29.11.2012 (JFrame)
        */     
 
        public static void main(String[] args) {
                Connect4JFrame frame = new Connect4JFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}

class Connect4JFrame extends JFrame implements ActionListener {
 
        private Button          btn1, btn2, btn3, btn4, btn5, btn6, btn7;
        private Label           lblSpacer;
        MenuItem                newMI, exitMI, redMI, yellowMI, humanMIR, rndMIR, mMAMIR,abAMIR,humanMIY, rndMIY, mMAMIY,abAMIY,startTest;
        int[][]                 theArray;
        JLabel RMoves, YMoves, TMoves, TimeMove;
        int RCount;
        int YCount;
        Agent botY;
        Agent botR;
        boolean botPlayingY = false;
        boolean botPlayingR = false;
        boolean                 end=false;
        boolean                 gameStart;
        public static final int BLANK = 0;
        public static final int RED = 1;
        public static final int YELLOW = 2;
 
        public static final int MAXROW = 6;     // 6 rows
        public static final int MAXCOL = 7;     // 7 columns
 
        public static final String SPACE = "                  "; // 18 spaces
        
        public static int activeColour = RED;
        Instant startMove;
        Instant endMove;
        

        public Connect4JFrame() {
                RCount = 0;
                YCount = 0;
                setTitle("Connect4 by Chris Clarke");
                MenuBar mbar = new MenuBar();
                Menu fileMenu = new Menu("File");
                newMI = new MenuItem("New");
                newMI.addActionListener(this);
                fileMenu.add(newMI);
                exitMI = new MenuItem("Exit");
                exitMI.addActionListener(this);
                fileMenu.add(exitMI);
                mbar.add(fileMenu);
                Menu optMenu = new Menu("Options");
                redMI = new MenuItem("Red starts");
                redMI.addActionListener(this);
                optMenu.add(redMI);
                yellowMI = new MenuItem("Yellow starts");
                yellowMI.addActionListener(this);
                optMenu.add(yellowMI);
                mbar.add(optMenu);
                Menu playerRMenu = new Menu("Player - Red");
                rndMIR = new MenuItem("Random player");
                rndMIR.addActionListener(this);
                playerRMenu.add(rndMIR);
                mMAMIR = new MenuItem("Min-Max player");
                mMAMIR.addActionListener(this);
                playerRMenu.add(mMAMIR);
                abAMIR = new MenuItem("Alpha-Beta player");
                abAMIR.addActionListener(this);
                playerRMenu.add(abAMIR);
                humanMIR = new MenuItem("Human");
                humanMIR.addActionListener(this);
                playerRMenu.add(humanMIR);
                mbar.add(playerRMenu);


                Menu playerYMenu = new Menu("Player - Yellow");
                rndMIY = new MenuItem("Random player");
                rndMIY.addActionListener(this);
                playerYMenu.add(rndMIY);
                mMAMIY = new MenuItem("Min-Max player");
                mMAMIY.addActionListener(this);
                playerYMenu.add(mMAMIY);
                abAMIY = new MenuItem("Alpha-Beta player");
                abAMIY.addActionListener(this);
                playerYMenu.add(abAMIY);
                humanMIY = new MenuItem("Human");
                humanMIY.addActionListener(this);
                playerYMenu.add(humanMIY);
                mbar.add(playerYMenu);
                
                Menu test = new Menu("Test");
                startTest = new MenuItem("Start");
                startTest.addActionListener(this);
                test.add(startTest);
                mbar.add(test);
                setMenuBar(mbar);
                
 
                // Build control panel.
                Panel panel = new Panel();
                Panel panel1 = new Panel();
                RMoves = new JLabel("Red: " +RCount);
                
                panel1.add(RMoves);

                btn1 = new Button("1");
                btn1.addActionListener(this);
                panel.add(btn1);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn2 = new Button("2");
                btn2.addActionListener(this);
                panel.add(btn2);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn3 = new Button("3");
                btn3.addActionListener(this);
                panel.add(btn3);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn4 = new Button("4");
                btn4.addActionListener(this);
                panel.add(btn4);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn5 = new Button("5");
                btn5.addActionListener(this);
                panel.add(btn5);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn6 = new Button("6");
                btn6.addActionListener(this);
                panel.add(btn6);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);
 
                btn7 = new Button("7");
                btn7.addActionListener(this);
                panel.add(btn7);

                YMoves = new JLabel("Yellow: " + YCount);
                panel1.add(YMoves);
                TMoves = new JLabel("Total: " +(RCount+YCount));
                panel1.add(TMoves);
 
                add(panel, BorderLayout.NORTH);
                add(panel1, BorderLayout.SOUTH);
                initialize();
                TimeMove = new JLabel("Time of the move: ");
                panel1.add(TimeMove);
                startMove = Instant.now();
                // Set to a reasonable size.
                setSize(1024, 768);

        } // Connect4
 
        public void initialize() {
                RCount = 0;
                RMoves.setText("Red: " +YCount); 
                YCount = 0;
                YMoves.setText("Yellow: " +YCount); 
                TMoves.setText("Total: " +(RCount+YCount)); 
                theArray=new int[MAXROW][MAXCOL];
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++)
                                theArray[row][col]=BLANK;
                gameStart=false;
        } // initialize
 
        public void paint(Graphics g) {
                g.setColor(Color.BLUE);
                g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++) {
                                if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
                                if (theArray[row][col]==RED) g.setColor(Color.RED);
                                if (theArray[row][col]==YELLOW) g.setColor(Color.YELLOW);
                                g.fillOval(160+100*col, 100+100*row, 100, 100);
                        }
                check4(g);
        } // paint
 
        public void putDisk(int n) {
                if(activeColour == RED){
                        RCount++;
                        RMoves.setText("Red: " +RCount);
                }
                else{
                        YCount++;
                        YMoves.setText("Yellow: " +YCount); 
                }
                TMoves.setText("Total: " +(RCount+YCount)); 
        // put a disk on top of column n
                // if game is won, do nothing
                if (end) return;
                gameStart=true;
                int row;
                n--;
                for (row=0; row<MAXROW; row++)
                        if (theArray[row][n]>0) break;
                if (row>0) {
                        theArray[--row][n]=activeColour;
                        if (activeColour==RED)
                                activeColour=YELLOW;
                        else
                                activeColour=RED;
                        
                }
                repaint(10);
                boolean nextPlayer=check4();
                if(!nextPlayer){
                        if(botPlayingY && !end){
                                botYPlay();
                                
                        }
                        if(botPlayingR && !end){
                                botRPlay();
                        }
                }
        }
        
        public int putDiskBots(){
                boolean nextPlayer =check4();
                int ret = activeColour;
                while(!nextPlayer){
                        if(activeColour == RED){
                                RMoves.setText("Red: " +RCount);
                        }
                        else{
                                YMoves.setText("Yellow: " +YCount); 
                        }
                        TMoves.setText("Total: " +(RCount+YCount)); 
                        gameStart=true;
                        int row;
                        nextPlayer=check4();
                        if(!nextPlayer){
                                if(activeColour == RED){
                                        botRPlay();
                                        activeColour=YELLOW;
                                }
                                else if(activeColour == YELLOW){
                                        botYPlay();
                                        activeColour=RED;
                                }
                                ret = activeColour;
                        }
                        repaint(10);
                }
                return ret;
        }
        
        public void botRPlay(){
                RCount++;
                RMoves.setText("Red: " +RCount);

                TMoves.setText("Total: " +(RCount+YCount)); 
                int n=-1;
                int row=0;
                if(botR instanceof RandomAgent){
                        do{
                                n = botR.nextMove();
                                if (end) return;
                                gameStart=true;
                                
                                n--;
                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                else if(botR instanceof MinMaxAgent){
                        botR.setBoard(theArray);
                        //n = botR.nextMove(); Do While je potrebno izbrisati
                        do{
                                
                                n = botR.nextMove();
                                if (end) return;
                                gameStart=true;
                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                else if(botR instanceof MinMaxAlfaBetaAgent){
                        botR.setBoard(theArray);
                        //n = botR.nextMove(); Do While je potrebno izbrisati
                        do{
                                
                                n = botR.nextMove();
                                if (end) return;
                                gameStart=true;

                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                endMove = Instant.now();
                TimeMove.setText("Time of the move: "+ durationFormat());
                if (row>0) {
                        theArray[--row][n]=activeColour;
                        activeColour=YELLOW;
                }
                repaint(10);
                startMove = Instant.now();
        }
        
        public void botYPlay(){
                YCount++;
                YMoves.setText("Yellow: " +YCount); 
                TMoves.setText("Total: " +(RCount+YCount)); 
                int n=-1;
                int row=0;
                if(botY instanceof RandomAgent){
                        do{
                                n = botY.nextMove();
                                if (end) return;
                                gameStart=true;
                                
                                n--;
                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                else if(botY instanceof MinMaxAgent){
                        botY.setBoard(theArray);
                        //n = botY.nextMove(); Do While je potrebno izbrisati
                        do{
                                
                                n = botY.nextMove();
                                if (end) return;
                                gameStart=true;
                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                else if(botY instanceof MinMaxAlfaBetaAgent){
                        botY.setBoard(theArray);
                        //n = botR.nextMove(); Do While je potrebno izbrisati
                        do{
                                
                                n = botY.nextMove();
                                if (end) return;
                                gameStart=true;

                                for (row=0; row<MAXROW; row++)
                                        if (theArray[row][n]>0) break;
                                
                        }while(row == 0);
                }
                endMove = Instant.now();
                TimeMove.setText("Time of the move: "+ durationFormat());
                if (row>0) {
                        theArray[--row][n]=activeColour;
                        activeColour=RED;
                }
                repaint(10);
                startMove = Instant.now();
        }
 
        public void displayWinner(Graphics g, int n) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier", Font.BOLD, 100));
                if (n==RED)
                        g.drawString("Red wins!", 100, 400);
                else
                        g.drawString("Yellow wins!", 100, 400);
                end=true;
        }

        public void displayDrow(Graphics g) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier", Font.BOLD, 100));
                g.drawString("DROW", 100, 400);

        }
        
        public boolean check4() {
                // see if there are 4 disks in a row: horizontal, vertical or diagonal
                // horizontal rows
                for (int row=0; row<MAXROW; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row][col+1]
                                 && curr == theArray[row][col+2]
                                 && curr == theArray[row][col+3]) {
                                        return true;
                                }
                        }
                }
                // vertical columns
                for (int col=0; col<MAXCOL; col++) {
                        for (int row=0; row<MAXROW-3; row++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col]
                                 && curr == theArray[row+2][col]
                                 && curr == theArray[row+3][col]){
                                        return true;
                                }
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<MAXROW-3; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col+1]
                                 && curr == theArray[row+2][col+2]
                                 && curr == theArray[row+3][col+3]){
                                        return true;
                                }
                        }
                }
                // diagonal upper left to lower right
                for (int row=MAXROW-1; row>=3; row--) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row-1][col+1]
                                 && curr == theArray[row-2][col+2]
                                 && curr == theArray[row-3][col+3]){
                                        return true;
                                }
                        }
                }
                return false;
        } // end check4


        public void check4(Graphics g) {
        // see if there are 4 disks in a row: horizontal, vertical or diagonal
                // horizontal rows
                for (int row=0; row<MAXROW; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row][col+1]
                                 && curr == theArray[row][col+2]
                                 && curr == theArray[row][col+3]) {
                                        displayWinner(g, theArray[row][col]);
                                }
                        }
                }
                // vertical columns
                for (int col=0; col<MAXCOL; col++) {
                        for (int row=0; row<MAXROW-3; row++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col]
                                 && curr == theArray[row+2][col]
                                 && curr == theArray[row+3][col])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<MAXROW-3; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row+1][col+1]
                                 && curr == theArray[row+2][col+2]
                                 && curr == theArray[row+3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal upper left to lower right
                for (int row=MAXROW-1; row>=3; row--) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                 && curr == theArray[row-1][col+1]
                                 && curr == theArray[row-2][col+2]
                                 && curr == theArray[row-3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }

                boolean drow=true;
                for(int i = 0; i<MAXCOL;i++){
                        if(theArray[0][i]==0){
                                drow=false;
                        }
                }
                if(drow){
                        displayDrow(g);
                }
        } // end check4
 
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn1){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(1);
                        startMove = Instant.now();}
                else if (e.getSource() == btn2){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(2);
                        startMove = Instant.now();}
                else if (e.getSource() == btn3){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(3);
                        startMove = Instant.now();}
                else if (e.getSource() == btn4){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(4);
                        startMove = Instant.now();}
                else if (e.getSource() == btn5){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(5);
                        startMove = Instant.now();}
                else if (e.getSource() == btn6){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(6);
                        startMove = Instant.now();}
                else if (e.getSource() == btn7){
                        endMove = Instant.now();
                        TimeMove.setText("Time of the move: "+ durationFormat());
                        putDisk(7);
                        startMove = Instant.now();}
                else if (e.getSource() == newMI) {
                        
                        end=false;
                        initialize();
                        repaint(10);
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(botPlayingR == true && activeColour==RED){
                                botRPlay();
                        }
                        else if(botPlayingY == true && activeColour==YELLOW){
                                botYPlay();
                        }
                        startMove = Instant.now();
                } else if (e.getSource() == exitMI) {
                        System.exit(0);
                } else if (e.getSource() == redMI) {
                        // don't change colour to play in middle of game
                        if (!gameStart) activeColour=RED;
                } else if (e.getSource() == yellowMI) {
                        if (!gameStart) activeColour=YELLOW;
                }
                else if (e.getSource() == rndMIY) {
                        botPlayingY = true;
                        botY = new RandomAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==YELLOW){
                                botYPlay();
                        }
                        
                }
                else if (e.getSource() == mMAMIY) {
                        botPlayingY = true;
                        botY = new MinMaxAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==YELLOW){
                                botYPlay();
                        }
                        
                }
                else if (e.getSource() == abAMIY) {
                        botPlayingY = true;
                        botY = new MinMaxAlfaBetaAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==YELLOW){
                                botYPlay();
                        }
                        
                }
                else if (e.getSource() == humanMIY) {
                        botPlayingY = false;
                }
                else if (e.getSource() == rndMIR) {
                        botPlayingR = true;
                        botR = new RandomAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==RED){
                                botRPlay();
                        }
                        
                }
                else if (e.getSource() == mMAMIR) {
                        botPlayingR = true;
                        botR = new MinMaxAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==RED){
                                botRPlay();
                        }
                        
                }
                else if (e.getSource() == abAMIR) {
                        botPlayingR = true;
                        botR = new MinMaxAlfaBetaAgent();
                        if(botPlayingR && botPlayingY){
                                putDiskBots();
                        }
                        else if(activeColour==RED){
                                botRPlay();
                        }
                        
                }
                else if (e.getSource() == humanMIR) {
                        botPlayingR = false;
                }
                else if(e.getSource() == startTest){
                        testing();
                }
        } // end ActionPerformed

        public String durationFormat(){
                Duration duration = Duration.between(startMove,endMove);
                long hours = duration.toHours();
                long minutes = duration.toMinutes() % 60;
                long seconds = duration.getSeconds() % 60;
                long milliseconds = duration.toMillis() % 1000;
                return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
        }
        public void testing(){
                botPlayingR = true;
                botPlayingY = true;
                int yWin=0;
                int rWin=0;
                int drowRez=0;
                
                yWin=0;
                rWin=0;
                drowRez=0;
                System.out.println("Yellow: MinMax");
                System.out.println("Red: Random");
                for(int i = 0; i<100; i++){
                        initialize();
                        botR = new RandomAgent();                
                        botY = new MinMaxAgent();        
                        int ret =  putDiskBots();
                        boolean drow=true;
                        for(int j = 0; j<MAXCOL;j++){
                                if(theArray[0][j]==0){
                                        drow=false;
                                }
                        }
                        if(drow){
                                drowRez++;
                        }
                        else if(ret == YELLOW){
                                yWin++;
                        }
                        else{
                                rWin++;
                        }
                }
                System.out.println("Red: "+yWin);
                System.out.println("Yellow: "+rWin);
                System.out.println("Drow: "+drowRez);

                /* Yellow: MinMax
                 * Red: Random
                 * Yellow: 25
                 * Red: 75 
                 * Drow: 0
                 */
                
                yWin=0;
                rWin=0;
                drowRez=0;
                System.out.println("Yellow: AlphaBeta");
                System.out.println("Red: Random");
                for(int i = 0; i<100; i++){
                        initialize();
                        botR = new RandomAgent();                
                        botY = new MinMaxAlfaBetaAgent();        
                        int ret =  putDiskBots();
                        boolean drow=true;
                        for(int j = 0; j<MAXCOL;j++){
                                if(theArray[0][j]==0){
                                        drow=false;
                                }
                        }
                        if(drow){
                                drowRez++;
                        }
                        else if(ret == YELLOW){
                                yWin++;
                        }
                        else{
                                rWin++;
                        }
                }
                System.out.println("Red: "+yWin);
                System.out.println("Yellow: "+rWin);
                System.out.println("Drow: "+drowRez);

                /* Yellow: AlphaBeta 
                 * Red: Random
                 * Yellow: 16 
                 * Red: 84 
                 * Drow: 0
                 */

                yWin=0;
                rWin=0;
                drowRez=0;
                System.out.println("Yellow: AlphaBeta");
                System.out.println("Red: MinMax");
                for(int i = 0; i<20; i++){
                        System.out.println(i);
                        initialize();
                        botR = new MinMaxAgent();                
                        botY = new MinMaxAlfaBetaAgent();        
                        int ret =  putDiskBots();
                        boolean drow=true;
                        for(int j = 0; j<MAXCOL;j++){
                                if(theArray[0][j]==0){
                                        drow=false;
                                }
                        }
                        if(drow){
                                drowRez++;
                        }
                        else if(ret == YELLOW){
                                yWin++;
                        }
                        else{
                                rWin++;
                        }
                }
                System.out.println("Yellow: "+yWin);
                System.out.println("Red: "+rWin);
                System.out.println("Drow: "+drowRez);
                /* Yellow: AlphaBeta 
                 * Red: MinMax 
                 * Yellow: 57 
                 * Red: 42
                 * Drow: 1*/
                
                
        }
 
} // class
