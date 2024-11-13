import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe{
            
    int boardWidth = 600;
    int boardHeight = 700;  // 50-50px for top/buttom text panels
    
    JFrame frame = new JFrame("Tic Tac Toe");
    JLabel textLabel = new JLabel(); // lable for title
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();// space where players play(input)
    JPanel bottomPanel = new JPanel(); // for other panel button such as restart etc...

    JButton[][] board = new JButton[3][3];// 3x3 grid 2d array
    String playerX = "X";
    String playerO = "O";
    String cuttrentPlayer = playerX;
    
    boolean gameOver = false;
    int turns =0;

    // counters for bottom panel...
    int matchesPlayed = 0;
    int playerXWins = 0;
    int playerOWins = 0;

    JLabel matchCounter = new JLabel("Matches: 0");
    JLabel playerXCounter = new JLabel("PlayerX Wins: 0");
    JLabel playerOCounter = new JLabel("PlayerO Wins: 0");
    JButton restartButton = new JButton("Restart");

    TicTacToe(){

        // setting up the frame of game
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null); // putting window in centre
        frame.setResizable(false);  // setting non resizable window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // allowing window to close directly
        frame.setLayout(new BorderLayout());
        
        // setting up the top label(whose chance, winner, etc..)
        textLabel.setBackground(Color.darkGray); //backgroung set 
        textLabel.setForeground(Color.white);// set text color
        textLabel.setFont(new Font("Arial", Font.BOLD, 50)); // set the font
        textLabel.setHorizontalAlignment(JLabel.CENTER); // text alignment to center
        textLabel.setText("Tic-Tac-Toe"); // default setting to tic tac toe
        textLabel.setOpaque(true); // make a label background opaque
        
        textPanel.setLayout(new BorderLayout()); // adding text to text panel
        textPanel.add(textLabel);// add label to text panel
        // till now text panel will going to be in whole center after this...
        frame.add(textPanel, BorderLayout.NORTH);// putting at top
        
        // set up of the game board(3x3 grid)
        boardPanel.setLayout(new GridLayout(3,3)); //making 3x3 grid
        boardPanel.setBackground(Color.darkGray); //setting its background
        frame.add(boardPanel); //adding that panel to frame

        for(int row = 0; row<3; row++){  // for loop to go the buttons we made as tiles
            for(int col = 0; col<3; col++){
                JButton tile = new JButton();
                board[row][col] = tile;
                boardPanel.add(tile);// adding those 9 tiles:*buttons*

                tile.setBackground(Color.darkGray);// setting there bg 
                tile.setForeground(Color.white);//setting text color of 'X' nd 'O'
                tile.setFont(new Font("Arial", Font.BOLD, 120));//setting font of X and O
                tile.setFocusable(false);//as we make the buttons they were distinguised to make them as same
                // tile.setText(cuttrentPlayer); // setting current player on each button... not needed

                tile.addActionListener(new ActionListener() {   // to put our actions on buttons we use actionListener
                    public void actionPerformed(ActionEvent e){ // func. for actionevents
                        if(gameOver)
                            return;
                        JButton tile = (JButton) e.getSource(); // here event is coming from JButton but necessarily source can come from different places so we use (JButtons)
                        if(tile.getText() == ""){
                            tile.setText(cuttrentPlayer); // now player can give the input
                            turns++; // to iterate the no. of turns
                            checkWinner();
                            if(!gameOver){
                                cuttrentPlayer = cuttrentPlayer == playerX ? playerO : playerX;// ternary expression to switch the player...
                                textLabel.setText(cuttrentPlayer + "'s turn");
                            }
                        }                    
                    }
                });

            }
        }

        // set up of the bottom panel with match counter(consist of restart button, no. of matches, etc..)
        bottomPanel.setLayout(new FlowLayout());
        matchCounter.setFont(new Font("Arial", Font.BOLD, 16));
        playerXCounter.setFont(new Font("Arial", Font.BOLD, 16));
        playerOCounter.setFont(new Font("Arial", Font.BOLD, 16));

        restartButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resetGame();// it resets the game by clicking the button
            }
        });

        bottomPanel.add(restartButton); // restart button first
        bottomPanel.add(matchCounter);//adding counter
        bottomPanel.add(playerXCounter);
        bottomPanel.add(playerOCounter);
        //adding bottom panel to frame
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    void checkWinner(){
        // checking for the rows
        for(int row=0; row<3; row++){
            if(board[row][0].getText() == "")//we check forst tile if there's no x/0 in 1st tile theres no way to win 
                continue;
            if(board[row][0]. getText() == board[row][1].getText() && // else we check this condition (if) 
                board[row][1].getText() == board[row][2].getText()){
                for(int i=0; i<3; i++){
                    setWinner(board[row][i]);
                }
                gameOver = true;
                updateWinCounter(); // updating the win counter if any player wins the game
                return;
            }
        }

        // checking for the columns
        for(int col=0; col<3; col++){
            if(board[0][col].getText() == "")//we check forst tile if there's no x/0 in 1st tile theres no way to win 
                continue;
            if(board[0][col]. getText() == board[1][col].getText() &&
                board[1][col].getText() == board[2][col].getText()){
                for(int i=0; i<3; i++){
                    setWinner(board[i][col]);
                }
                gameOver = true;
                updateWinCounter(); // updating the win counter if any player wins the game
                return;
            }
        }

        //checking for the diagonally
        if(board[0][0].getText() == board[1][1].getText() && 
            board[1][1].getText() == board[2][2].getText() && 
            board[0][0].getText() != ""){
            for(int i= 0; i<3; i++){
                setWinner(board[i][i]);
            }
            gameOver = true;
            updateWinCounter(); // updating the win counter if any player wins the game
            return;
        }

        // checking for the anti-diagonally
        if(board[0][2].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][0].getText() &&
            board[0][2].getText() != ""){
            setWinner(board[0][2]); // checking individually
            setWinner(board[1][1]);
            setWinner(board[2][0]);

            gameOver = true;
            updateWinCounter(); // updating the win counter if any player wins the game
            return;
        }

        // checking for a tie game and updating to matches played
        if( turns == 9 ){
            for(int row=0; row<3; row++){
                for(int col=0; col<3; col++){
                    setTie(board[row][col]);
                }
            }
            textLabel.setText("Tie!");// putting the tie label in the panel
            gameOver = true;
            matchesPlayed++; // here updating the match counter for tie
            matchCounter.setText("Matches: "+ matchesPlayed);
        }
    }

    void setWinner(JButton tile){ //setting winner when any condition meets true
        tile.setForeground(Color.green);
        tile.setBackground(Color.darkGray);
        textLabel.setText(cuttrentPlayer + " is the winner");
    }

    void setTie(JButton tile){ // setting game tie if any condition does not meet
        tile.setForeground(Color.red);
        tile.setBackground(Color.gray);
    }

    void resetGame(){
        // resetting the game board and the UI components for a new game
        for(int row=0; row<3; row++){
            for(int col=0; col<3; col++){
                board[row][col].setText("");
                board[row][col].setForeground(Color.white);
                board[row][col].setBackground(Color.darkGray);
            }
        }
        gameOver = false;
        turns = 0;
        cuttrentPlayer = playerX;
        textLabel.setText("Tic-Tac-Toe");
    }

    void updateWinCounter(){
        // updating win counter and match counter after every match and game
        if(cuttrentPlayer.equals(playerX)){
            playerXWins++;
            playerXCounter.setText("Player X Wins: "+ playerXWins);
        }
        else{
            playerOWins++;
            playerOCounter.setText("Player O Wins: "+ playerOWins);
        }
        matchesPlayed++;
        matchCounter.setText("Matches: "+ matchesPlayed);
    }
}