import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

/**
 * Created by dannyrowe on 15/07/2016.
 */
public class Knight extends Chess_Piece
{
    /**
     * The default <code>Piece</code> constructor
     */
    public Knight( int row, int col, int colour, Image iconImage, Chess_Board board )
    {
        super( row, col, colour, iconImage, board );
    }

    /**
     *
     */
    @Override
    public List<Square> all_valid_moves( Chess_Board board )
    {
        List<Square>  valid_moves = new LinkedList<Square>();

        // Down 1, Right 2
        if( can_move_to( get_row() + 1, get_col() + 2, board ) )
            valid_moves.add( new Square( get_row() + 1, get_col() + 2 ) );

        // Down 1, Left 2
        if( can_move_to( get_row() + 1, get_col() - 2, board ) )
            valid_moves.add( new Square( get_row() + 1, get_col() - 2 ) );

        // Up 1, Right 2
        if( can_move_to( get_row() - 1, get_col() + 2, board ) )
            valid_moves.add( new Square( get_row() - 1, get_col() + 2 ) );

        // Up 1, Left 2
        if( can_move_to( get_row() - 1, get_col() - 2, board ) )
            valid_moves.add( new Square( get_row() - 1, get_col() - 2 ) );

        // Up 2, Right 1
        if( can_move_to( get_row() - 2, get_col() + 1, board ) )
            valid_moves.add( new Square( get_row() - 2, get_col() + 1 ) );

        // Up 2, Left 1
        if( can_move_to( get_row() - 2, get_col() - 1, board ) )
            valid_moves.add( new Square( get_row() - 2, get_col() - 1 ) );

        // Down 2, Right 1
        if( can_move_to( get_row() + 2, get_col() + 1, board ) )
            valid_moves.add( new Square( get_row() + 2, get_col() + 1 ) );

        // Down 2, Left 1
        if( can_move_to( get_row() + 2, get_col() - 1, board ) )
            valid_moves.add( new Square( get_row() + 2, get_col() - 1 ) );

        return valid_moves;
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
        if ((row > 7) || (row < 0))
            return false;

        if ((col > 7) || (col < 0))
            return false;

        if ((row == this.get_row()) && (col == this.get_col()))
            return false;

        int offsetRow = row - get_row();
        int offsetCol = col - get_col();

        if ( ( Math.abs( offsetRow ) == 1 ) && ( Math.abs( offsetCol ) == 2 ) )
        {
            if( null != ( board.piece_at( row, col ) ) )
            {
                return get_colour() != board.piece_at( row, col ).get_colour();
            }
            else
            {
                return true;
            }
        }

        if ( ( Math.abs( offsetRow ) == 2 ) && ( Math.abs( offsetCol ) == 1 ) )
        {
            if( null != ( board.piece_at( row, col ) ) )
            {
                return get_colour() != board.piece_at( row, col ).get_colour();
            }
            else
            {
                return true;
            }
        }
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
        return "Knight";
    }
}
