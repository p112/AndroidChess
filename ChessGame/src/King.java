import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

/**
 * Created by dannyrowe on 15/07/2016.
 */
public class King extends Chess_Piece
{
    boolean m_hasMoved = false;
    boolean m_hasCastled = false;

    /**
     *
     * Default class constructor
     *
     * @param row
     * @param col
     * @param colour
     * @param iconImage
     */
    public King( int row, int col, int colour, Image iconImage, Chess_Board board  )
    {
        super( row, col, colour, iconImage, board );
    }

    /**
     * @param board
     */
    @Override
    public List<Square> all_valid_moves( Chess_Board board)
    {
        List<Square>  allValidMoves = new LinkedList<Square>();

        // Straight Down
        if( can_move_to( get_row() + 1, get_col(), board ) )
        {
            allValidMoves.add( new Square( get_row() + 1, get_col() ) );
        }

        // Diagonal Right (Down)
        if( can_move_to( get_row() + 1, get_col() + 1, board ) )
        {
            allValidMoves.add( new Square( get_row() + 1, get_col() + 1 ) );
        }

        // Right
        if( can_move_to( get_row(), get_col() + 1, board ) )
        {
            allValidMoves.add( new Square( get_row(), get_col() + 1 ) );
        }

        // Castle Right
        if( can_move_to( get_row(), get_col() + 2, board ) )
        {
            allValidMoves.add( new Square( get_row(), get_col() + 2 ) );
        }

        // Diagonal Right (Up)
        if( can_move_to( get_row() - 1, get_col() + 1, board ) )
        {
            allValidMoves.add( new Square( get_row() - 1, get_col() + 1 ) );
        }

        // Up
        if( can_move_to( get_row() - 1, get_col(), board ) )
        {
            allValidMoves.add( new Square( get_row() - 1, get_col() ) );
        }

        // Diagonal Left (Up)
        if( can_move_to( get_row() - 1, get_col() - 1, board ) )
        {
            allValidMoves.add( new Square( get_row() - 1, get_col() - 1 ) );
        }

        // Left
        if( can_move_to( get_row(), get_col() - 1, board ) )
        {
            allValidMoves.add( new Square( get_row(), get_col() - 1 ) );
        }

        // Castle Left
        if( can_move_to( get_row(), get_col() - 2, board ) )
        {
            allValidMoves.add( new Square( get_row(), get_col() - 2 ) );
        }

        // Diagonal Left (Down)
        if( can_move_to( get_row() + 1, get_col() - 1, board ) )
        {
            allValidMoves.add( new Square( get_row() + 1, get_col() - 1 ) );
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
    public boolean can_move_to( int row, int col, Chess_Board board ) {

        if ((row > 7) || (row < 0))
            return false;

        if ((col > 7) || (col < 0))
            return false;

        if ((row == this.get_row()) && (col == this.get_col()))
            return false;

        int offsetCol = Math.abs( get_col() - col );
        int offsetRow = Math.abs( get_row() - row );

        if( ( offsetRow <= 1 ) && ( offsetCol <= 1 ) ) {
            if( null == board.piece_at( row, col ) ) {
                return true;
            }
            else {
                return get_colour() != board.piece_at( row, col ).get_colour();
            }
        }

        if( !m_hasCastled && !m_hasMoved ) {

            // Castle Right
            if( ( col - get_col() ) == 2 ) {
                for (int nextSquare = 1; nextSquare <= 2; nextSquare++) {
                     if ( null != board.piece_at( get_row(), get_col() + nextSquare ) )
                         return false;
                }
                if( null != board.piece_at( get_row(), get_col() + 3 ) ) {
                    if( board.piece_at( get_row(), get_col() + 3 ) instanceof Rook )
                        return get_colour() == board.piece_at( get_row(), get_col() + 3 ).get_colour();
                }
            }

            // Castle Left
            if( ( get_col() - col ) == 2 ) {
                for( int nextSquare = 1; nextSquare <= 3; nextSquare++ ) {
                    if ( null != board.piece_at( get_row(), get_col() - nextSquare ) )
                        return false;
                }
                if( null != board.piece_at( get_row(), get_col() - 4 ) ) {
                    if( board.piece_at( get_row(), get_col() - 4 ) instanceof Rook )
                        return get_colour() == board.piece_at( get_row(), get_col() - 4 ).get_colour();
                }
            }
        }
        return false;
    }

    /**
     * Moves the piece
     *
     * @param row the new position of the piece along the x-axis
     * @param col the new position of the piece along the y-axis
     */
    @Override
    public void move_to( int row, int col ) {
        if( !m_hasCastled && !m_hasMoved ) {
            if ( col > get_col() ) {
                // Castle Right
                if( ( col - get_col() ) == 2 ) {
                    m_hasCastled = true;
                }
            }
            else {
                // Castle Left
                if( ( get_col() - col ) == 2 ) {
                    m_hasCastled = true;
                }
            }
        }
        super.move_to( row, col );
        m_hasMoved = true;
    }

    /**
     * Returns a string containing the class name
     *
     * @return the class name
     */
    @Override
    public String toString()
    {
        return "King";
    }
}
