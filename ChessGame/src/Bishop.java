import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

/**
 * The Bishop chess piece
 */
public class Bishop extends Chess_Piece
{
    public Bishop( int row, int col, int colour, Image icon_image, Chess_Board board )
    {
        super(row, col, colour, icon_image, board );
    }

    /**
     * Returns a list containing all of the valid moves that the Bishop
     * can make.
     *
     * @param board the chess board
     */
    @Override
    public List<Square> all_valid_moves( Chess_Board board)
    {
        List<Square> valid_moves = new LinkedList<Square>();

        // Diagonal Right (Up)
        int moves_remaining_up = get_row();
        for (int next_square = 1; next_square < moves_remaining_up; next_square++)
        {
            if ( can_move_to( get_row() - next_square, get_col() + next_square, board))
                valid_moves.add(new Square( get_row() - next_square, get_col() + next_square));
        }

        // Diagonal Right (Down)
        for (int next_square = 1; next_square < 8; next_square++)
        {
            if ( can_move_to( get_row() + next_square, get_col() + next_square, board))
                valid_moves.add(new Square( get_row() + next_square, get_col() + next_square));
        }

        // Diagonal Left (Down)
        for (int next_square = 1; next_square < 8; next_square++)
        {
            if ( can_move_to( get_row() + next_square, get_col() - next_square, board))
                valid_moves.add(new Square( get_row() + next_square, get_col() - next_square));
        }

        // Diagonal Left (Up)
        for (int next_square = 1; next_square < 8; next_square++)
        {
            if ( can_move_to( get_row() - next_square, get_col() - next_square, board))
                valid_moves.add(new Square( get_row() - next_square, get_col() - next_square));
        }
        return valid_moves;
    }

    /**
     * Checks to see if this Piece can legally move to the position ( x, y )
     * on the <code>Chess_Board</code> legally
     *
     * @param row   the new position of the Piece along the x-axis
     * @param col   the new position of the Piece along the y-axis
     * @param board the chess board
     */
    @Override
    public boolean can_move_to(int row, int col, Chess_Board board)
    {
        if ( ( row > 7 ) || ( row < 0 ) )
            return false;

        if ( ( col > 7 ) || ( col < 0 ) )
            return false;

        if ( ( row == this.get_row() ) && ( col == this.get_col() ) )
            return false;

        int offset_row = Math.abs( get_row() - row );
        int offset_col = Math.abs( get_col() - col );

        if( offset_row == offset_col )
        {
            // Diagonal Right (Up)
            if( ( row < get_row() ) && ( col > get_col() ) )
            {
                int moves_remaining_right_up = Math.abs( get_row() - row );
                for (int next_square = 1; next_square <= moves_remaining_right_up; next_square++)
                {
                    if( null != board.piece_at( get_row() - next_square, get_col() + next_square ) )
                    {
                        if( ( get_row() - next_square == row ) && ( get_col() + next_square == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() - next_square, get_col() + next_square ).get_colour();
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                return true;
            }

            // Diagonal Right (Down)
            if( ( row > get_row() ) && ( col > get_col() ) )
            {
                int moves_remaining_right_down = Math.abs( get_row() - row );
                for (int next_square = 1; next_square <= moves_remaining_right_down; next_square++)
                {
                    if( null != board.piece_at( get_row() + next_square, get_col() + next_square ) )
                    {
                        if( ( get_row() + next_square == row ) && ( get_col() + next_square == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() + next_square, get_col() + next_square ).get_colour();
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                return true;
            }

            // Diagonal Left (Down)
            if( ( row > get_row() ) && ( col < get_col() ) )
            {
                int moves_remaining_left_down = Math.abs( get_row() - row );
                for (int next_square = 1; next_square <= moves_remaining_left_down; next_square++)
                {
                    if( null != board.piece_at( get_row() + next_square, get_col() - next_square ) )
                    {
                        if( ( get_row() + next_square == row ) && ( get_col() - next_square == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() + next_square, get_col() - next_square ).get_colour();
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                return true;
            }

            // Diagonal Left (Up)
            if( ( row < get_row() ) && ( col < get_col() ) )
            {
                int moves_remaining_left_up = Math.abs( get_row() - row );
                for (int next_square = 1; next_square <= moves_remaining_left_up; next_square++)
                {
                    if( null != board.piece_at( get_row() - next_square, get_col() - next_square ) )
                    {
                        if( ( get_row() - next_square == row ) && ( get_col() - next_square == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() - next_square, get_col() - next_square ).get_colour();
                        }
                        else
                        {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
