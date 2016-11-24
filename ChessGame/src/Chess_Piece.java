import java.awt.Image;
import java.util.List;

/**
 * The <code>Chess_Piece</code> is an abstract class that provides the default behaviour for all
 * the chess pieces, namely the <code>Pawn</code>, <code>Knight</code>, <code>Bishop</code>,
 * <code>Rook</code>, <code>Queen</code> and King.
 */
public abstract class Chess_Piece
{
    protected int           row;                          // The position of the Piece along the x-axis
    protected int           col;                          // The position of the Piece along the y-axis
    protected int           colour;                       // The colour of the piece - black or white
    private Image           icon_image;
    protected Chess_Board   board;                        // A reference to the Chess Board
    private Game            game;                         // A reference to the game object
    public static final int BLACK = 0;
    public static final int WHITE = 1;

    /**
     * The default <code>Piece</code> constructor
     *
     * @param row the position of the Piece along the x-axis
     * @param col the position of the Piece along the y-axis
     * @param colour the player that the piece should be assigned to
     */
    public Chess_Piece( int row, int col, int colour, Image icon_image, Chess_Board board )
    {
        this.row = row;
        this.col = col;
        this.colour = colour;
        this.icon_image = icon_image;
        this.board = board;
    }

    /**
     * This method returns a list containing all of the valid moves that the
     * chess piece can make.
     */
    public abstract List<Square> all_valid_moves( Chess_Board board );

    /**
     * This method returns the current row of where
     * the piece is located on the chess board.
     *
     * @return the position of the piece along the x-axis
     */
    public int get_row() {
        return row;
    }

    /**
     * This method returns the current column of where
     * the piece is located on the chess board.
     *
     * @return the position of the Piece along the y-axis
     */
    public int get_col() { return col; }

    /**
     * This method returns the colour of the chess piece.
     */
    public int get_colour()
    {
        return colour;
    }

    /**
     * This method moves the piece from it's current position on the chess board
     * to the new position ( row, col ).
     *
     * @param row the new position of the piece along the x-axis
     * @param col the new position of the piece along the y-axis
     */
    public void move_to( int row, int col )
    {
        if ( ( row >= 0 ) && ( row < 8 ) )
        {
            if( ( col >= 0 ) && ( col < 8 ) )
            {
                this.row = row;
                this.col = col;
            }
        }
    }

    /**
     * This method checks to see if this piece can legally move to the position ( x, y )
     * on the chess board.
     *
     * @param row the new position of the Piece along the x-axis
     * @param col the new position of the Piece along the y-axis
     *
     */
    public abstract boolean can_move_to( int row, int col, Chess_Board board );

    /**
     * Checks to see if a chess piece would be under attack (i.e. it can be taken by
     * at least one of the enemy's pieces) if it moved to position
     * (row, col) or if it remained in it's current position (row, col).
     *
     * @return true if the piece is under attack, false otherwise
     */
    public boolean under_attack(int row, int col, Chess_Board board) {
        for( int next_row = 0; next_row < 8; next_row++ ) {
            for (int next_col = 0; next_col < 8; next_col++) {
                if ( ( next_row != row ) && ( next_col != col ) ) {
                    // This if prevents recursion / stack overflow error
                    if( ( next_row != get_row() && next_col != get_col()) ) {
                        if( null != board.piece_at( next_row, next_col ) ) {
                            if( board.piece_at( next_row, next_col ).can_move_to( row, col, board ) ) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns an instance of the piece
     *
     * @return the piece
     */
    public Chess_Piece get_piece()
    {
        return this;
    }

    /**
     * Returns the icon image associated with the piece.
     *
     * @return the icon image
     */
    public Image get_icon_image()
    {
        return icon_image;
    }
}
