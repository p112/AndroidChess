import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

public class Pawn extends Chess_Piece
{
    /**
     * The default <code>Piece</code> constructor
     *
     * @param row      the position of the Piece along the x-axis
     * @param col      the position of the Piece along the y-axis
     * @param colour   the player that the piece should be assigned to
     */
    public Pawn( int row, int col, int colour, Image iconImage, Chess_Board board  )
    {
        super( row, col, colour, iconImage, board );
    }

    /**
     * Returns true if the Pawn can move to the position
     * (row, col), false otherwise.
     *
     * @param row The position of the Pawn along the x-axis
     * @param col The position of the Pawn along the y-axis
     */
    @Override
    public boolean can_move_to( int row, int col, Chess_Board board )
    {
        if( ( row > 7 ) || ( row < 0 ) )
            return false;

        if( ( col > 7 ) || ( col < 0 ) )
            return false;

        if( ( row == get_row() ) && ( col == get_col() ) )
            return false;

        if( get_colour() == Chess_Piece.WHITE )
        {
            // Move Up 1
            if ( ( row == get_row() - 1 ) && ( col == get_col() ) )
                return null == board.piece_at( row, col );

            // Move Up 2
            if ( ( row == get_row() - 2 ) && ( col == get_col() ) )
            {
                if ( get_row() == 6)
                {
                    if ( null == board.piece_at(row, col))
                    {
                        if ( null == board.piece_at(row + 1, col)) return true;
                    }
                }
                return false;
            }

            // Capture Left
            if ( ( col == get_col() - 1 ) && ( row == get_row() - 1))
                if ( null != board.piece_at( row, col ) )
                    return Chess_Piece.BLACK == board.piece_at( row, col ).get_colour();

            // Capture Right
            if ( ( col == get_col() + 1 ) && ( row  == get_row() - 1))
            {
                if ( null != board.piece_at(row, col) )
                {
                    return Chess_Piece.BLACK == board.piece_at(row, col).get_colour();
                }
                else
                {
                    // TODO: En Passant
                    return false;
                }
            }
            return false;
        }
        else
        {
            // Move Down 1
            if ( ( col == get_col() ) && ( row == ( get_row() + 1 ) ) )
                return ( null == board.piece_at( row, col ) );

            // Move Down 2
            if ( ( col == get_col()) && ( row == get_row() + 2 ) )
                if ( get_row() == 1 )
                    if ( null == board.piece_at(row, col))
                        if ( null == board.piece_at(row - 1, col ))
                            return true;

            // Capture Left
            if ( ( col == get_col() + 1) && ( row == get_row() + 1 ) )
                if ( null != board.piece_at( row, col ) )
                    return Chess_Piece.WHITE == board.piece_at(row, col).get_colour();

            // Capture Right
            if( ( col == get_col() - 1) && (row == get_row() + 1))
            {
                if( null != board.piece_at( row, col ) )
                {
                    return Chess_Piece.WHITE == board.piece_at( row, col ).get_colour();
                }
                else
                {
                    // TODO: En Passant
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list containing all of the squares the Pawn can move to.
     */
    public List<Square> all_valid_moves( Chess_Board board )
    {
        List<Square>  valid_moves = new LinkedList<Square>();

        if( get_colour() == Chess_Piece.WHITE )
        {
            // Move Up 1
            if( can_move_to( get_row() - 1, get_col(), board ) )
                valid_moves.add( new Square( get_row() - 1, get_col() ) );

            // Move Up 2
            if( can_move_to( get_row() - 2, get_col(), board ) )
                valid_moves.add( new Square( get_row() - 2, get_col() ) );

            // Capture Left
            if( can_move_to( get_row() - 1, get_col() - 1, board ) )
                valid_moves.add( new Square( get_row() - 1, get_col() - 1 ) );

            // Capture Right (+ en passant)
            if( can_move_to( get_row() - 1, get_col() + 1, board ) )
                valid_moves.add( new Square( get_row() - 1, get_col() + 1 ) );
        }
        else
        {
            // Move Down 1
            if( can_move_to( get_row() + 1, get_col(), board ) )
                valid_moves.add( new Square( get_row() + 1, get_col() ) );

            // Move Down 2
            if( can_move_to( get_row() + 2, get_col(), board ) )
                valid_moves.add( new Square( get_row() + 2, get_col() ) );

            // Capture Left
            if( can_move_to( get_row() + 1, get_col() + 1, board ) )
                valid_moves.add( new Square( get_row() + 1, get_col() + 1 ) );

            // Capture Right (+ en passend)
            if( can_move_to( get_row() + 1, get_col() - 1, board ) )
                valid_moves.add( new Square( get_row() + 1, get_col() - 1 ) );
        }
        return valid_moves;
    }

    /**
     * Returns a string containing the class name
     *
     * @return the class name
     */
    @Override
    public String toString()
    {
        return "Pawn";
    }

    /**
     * Calculates the number of points associated
     * with moving to a square (row, col). The higher
     * the points, the 'better' the move. This method
     * is used for AI/suggested move functionality.
     */
    public int calculate_move_points( int row, int col, Chess_Board board )
    {
        return 0;
    }
}
