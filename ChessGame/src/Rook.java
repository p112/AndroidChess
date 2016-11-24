import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

public class Rook extends Chess_Piece
{
    /**
     * Default Constructor
     *
     * @param row
     * @param col
     * @param colour
     */
    public Rook( int row, int col, int colour, Image iconImage, Chess_Board board )
    {
        super( row, col, colour, iconImage, board );
    }

    /**
     *  Returns a list of all the squares the piece can move to
     */
    @Override
    public List<Square> all_valid_moves( Chess_Board board )
    {
        List<Square>  allValidMoves = new LinkedList<Square>();

        int squaresRemainingRight = Math.abs( get_col() - 7 );
        int squaresRemainingLeft = get_col();
        int squaresRemainingUp = get_row();
        int squaresRemainingDown = Math.abs( get_row() - 7 );

        // Right
        for (int nextSquare = 1; nextSquare <= squaresRemainingRight; nextSquare++)
        {
            if( null == ( board.piece_at( get_row(), get_col() + nextSquare ) ) )
            {
                allValidMoves.add( new Square( get_row(), get_col() + nextSquare ) );
            }
            else
            {
                if ( get_colour() != board.piece_at( get_row(), get_col() + nextSquare ).get_colour())
                {
                    allValidMoves.add( new Square( get_row(), get_col() + nextSquare ) );
                    break;
                }
                else
                {
                    break;
                }
            }
        }

        // left
        for (int nextSquare = 1; nextSquare <= squaresRemainingLeft; nextSquare++)
        {
            if( null == ( board.piece_at( get_row(), get_col() - nextSquare ) ) )
            {
                allValidMoves.add( new Square( get_row(), get_col() - nextSquare ) );
            }
            else
            {
                if ( get_colour() != board.piece_at( get_row(), get_col() - nextSquare ).get_colour())
                {
                    allValidMoves.add( new Square( get_row(), get_col() - nextSquare ) );
                    break;
                }
                else
                {
                    break;
                }
            }
        }

        // Up
        for (int nextSquare = 1; nextSquare <= squaresRemainingUp; nextSquare++)
        {
            if( null == ( board.piece_at( get_row() - nextSquare, get_col() ) ) )
            {
                allValidMoves.add( new Square( get_row() - nextSquare, get_col() ) );
            }
            else
            {
                if ( get_colour() != board.piece_at( get_row() - nextSquare, get_col() ).get_colour())
                {
                    allValidMoves.add( new Square( get_row() - nextSquare, get_col() ) );
                    break;
                }
                else
                {
                    break;
                }
            }
        }

        // Down
        for (int nextSquare = 1; nextSquare <= squaresRemainingDown; nextSquare++)
        {
            if( null == ( board.piece_at( get_row() + nextSquare, get_col() ) ) )
            {
                allValidMoves.add( new Square( get_row() + nextSquare, get_col() ) );
            }
            else
            {
                if ( get_colour() != board.piece_at( get_row() + nextSquare, get_col() ).get_colour())
                {
                    allValidMoves.add( new Square( get_row() + nextSquare, get_col() ) );
                    break;
                }
                else
                {
                    break;
                }
            }
        }
        return allValidMoves;
    }

    /**
     * Checks to see if this Piece can legally move to the position ( x, y )
     * on the Board legally
     *
     * @param row   the new position of the Piece along the x-axis
     * @param col   the new position of the Piece along the y-axis
     * @param board
     */
    @Override
    public boolean can_move_to(int row, int col, Chess_Board board)
    {
        if ((row > 7) || (row < 0)) return false;
        if ((col > 7) || (col < 0)) return false;

        if ((row == this.get_row()) && ( col == this.get_col() ) ) return false;

        int rowOffset = Math.abs( row - get_row() );
        int colOffset = Math.abs( col - get_col() );

        if( ( rowOffset > 0 ) && ( colOffset == 0 ) ) return true;
        if( ( rowOffset == 0 ) && ( colOffset > 0 ) ) return true;

        return false;
    }

    /**
     * Returns a string containing the class name
     *
     * @return the class name
     */
    @Override
    public String toString()
    {
        return "Rook";
    }
}
