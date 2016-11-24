import java.util.LinkedList;
import java.util.List;
import java.awt.Image;

/**
 * Created by dannyrowe on 15/07/2016.
 */
public class Queen extends Chess_Piece
{
    public Queen( int row, int col, int colour, Image iconImage, Chess_Board board )
    {
        super( row, col, colour, iconImage, board );
    }

    /**
     * @param board
     */
    @Override
    public List<Square> all_valid_moves( Chess_Board board )
    {
        List<Square>  allValidMoves = new LinkedList<Square>();

        // Diagonal Right (Up)
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( ( get_row() - nextSquare >= 0 ) || ( get_col() + nextSquare <= 7 ) )
            {
                if ( can_move_to( get_row() - nextSquare, get_col() + nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row() - nextSquare, get_col() + nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Right
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( get_col() + nextSquare <= 7 )
            {
                if ( can_move_to( get_row(), get_col() + nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row(), get_col() + nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Diagonal Right (Down)
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( ( get_row() + nextSquare <= 7 ) || ( get_col() + nextSquare <= 7 ) )
            {
                if ( can_move_to( get_row() + nextSquare, get_col() + nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row() + nextSquare, get_col() + nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Down
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( ( get_row() + nextSquare <= 7 ) )
            {
                if ( can_move_to( get_row() + nextSquare, get_col(), board))
                {
                    allValidMoves.add(new Square( get_row() + nextSquare, get_col()));
                }
            }
            else
            {
                break;
            }
        }

        // Diagonal Left (Down)
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( ( get_row() + nextSquare <= 7 ) || ( get_col() - nextSquare >= 0 ) )
            {
                if ( can_move_to( get_row() + nextSquare, get_col() - nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row() + nextSquare, get_col() - nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Left
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( get_col() - nextSquare >= 0 )
            {
                if ( can_move_to( get_row(), get_col() - nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row(), get_col() - nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Diagonal Left (Up)
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( ( get_row() - nextSquare >= 0 ) || ( get_col() - nextSquare >= 0 ) )
            {
                if ( can_move_to( get_row() - nextSquare, get_col() - nextSquare, board))
                {
                    allValidMoves.add(new Square( get_row() - nextSquare, get_col() - nextSquare));
                }
            }
            else
            {
                break;
            }
        }

        // Up
        for (int nextSquare = 1; nextSquare <= 8; nextSquare++)
        {
            if( get_row() - nextSquare >= 0 )
            {
                if ( can_move_to( get_row() - nextSquare, get_col(), board))
                {
                    allValidMoves.add(new Square( get_row() - nextSquare, get_col()));
                }
            }
            else
            {
                break;
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
        if( ( row > 7 ) || ( row < 0 ) )
            return false;

        if( ( col > 7 ) || ( col < 0 ) )
            return false;

        if( ( row == this.get_row() ) && ( col == this.get_col() ) )
            return false;

        int offsetRow = Math.abs( get_row() - row );
        int offsetCol = Math.abs( get_col() - col );

        if( offsetRow == offsetCol )
        {
            // Diagonal Right (Up)
            if( ( row < get_row() ) && ( col > get_col() ) )
            {
                int movesRemainingRightUp = get_row() - row;
                for( int nextSquare = 1; nextSquare <= movesRemainingRightUp; nextSquare++ )
                {
                    if( get_row() - nextSquare < 0 )
                        break;

                    if( get_col() + nextSquare > 7 )
                        break;

                    if( null != board.piece_at( get_row() - nextSquare, get_col() + nextSquare ) )
                    {
                        if( ( get_row() - nextSquare == row ) && ( get_col() + nextSquare == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() - nextSquare, get_col() + nextSquare ).get_colour();
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
                int movesRemainingRightDown = row - get_row();
                for( int nextSquare = 1; nextSquare <= movesRemainingRightDown; nextSquare++ )
                {
                    if( get_row() + nextSquare > 7 )
                        break;

                    if( get_col() + nextSquare > 7 )
                        break;

                    // If there's a piece
                    if( null != board.piece_at( get_row() + nextSquare, get_col() + nextSquare ) )
                    {
                        // And that piece is at our final destination (row, col)
                        if( ( get_row() + nextSquare == row ) && ( get_col() + nextSquare == col ) )
                        {
                            // Return true if the piece is an enemy piece, false otherwise.
                            return get_colour() != board.piece_at( get_row() + nextSquare, get_col() + nextSquare ).get_colour();
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
                int movesRemainingLeftDown = row - get_row();
                for( int nextSquare = 1; nextSquare <= movesRemainingLeftDown; nextSquare++ )
                {
                    if( get_row() + nextSquare > 7 )
                        break;

                    if( get_col() - nextSquare < 0 )
                        break;

                    if( null != board.piece_at( get_row() + nextSquare, get_col() - nextSquare ) )
                    {
                        if( ( get_row() + nextSquare == row ) && ( get_col() - nextSquare == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() + nextSquare, get_col() - nextSquare ).get_colour();
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
                int movesRemainingLeftUp = get_row() - row;
                for( int nextSquare = 1; nextSquare <= movesRemainingLeftUp; nextSquare++ )
                {
                    if( get_row() - nextSquare < 0 )
                        break;

                    if( get_col() - nextSquare < 0 )
                        break;

                    if( null != board.piece_at( get_row() - nextSquare, get_col() - nextSquare ) )
                    {
                        if( ( get_row() - nextSquare == row ) && ( get_col() - nextSquare == col ) )
                        {
                            return get_colour() != board.piece_at( get_row() - nextSquare, get_col() - nextSquare ).get_colour();
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

        // Right
        if( ( row == get_row() ) && ( col > get_col() ) )
        {
            int movesRemainingRight = col - get_col();
            for( int nextSquare = 1; nextSquare <= movesRemainingRight; nextSquare++ )
            {
                if( get_col() + nextSquare > 7 )
                    break;

                if( null != board.piece_at( get_row(), get_col() + nextSquare ) )
                {
                    if( ( get_row() == row ) && ( get_col() + nextSquare == col ) )
                    {
                        return get_colour() != board.piece_at( get_row(), get_col() + nextSquare ).get_colour();
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        // Down
        if( ( row > get_row() ) && ( col == get_col() ) )
        {
            int movesRemainingDown = row - get_row();
            for( int nextSquare = 1; nextSquare <= movesRemainingDown; nextSquare++ )
            {
                if( get_row() + nextSquare > 7 )
                {
                    break;
                }

                if( null != board.piece_at( get_row() + nextSquare, get_col() ) )
                {
                    if( ( get_row() + nextSquare == row ) && ( get_col() == col ) )
                    {
                        return get_colour() != board.piece_at( get_row() + nextSquare, get_col() ).get_colour();
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        // Left
        if( ( row == get_row() ) && ( col < get_col() ) )
        {
            int movesRemainingLeft = get_col() - col;
            for( int nextSquare = 1; nextSquare <= movesRemainingLeft; nextSquare++ )
            {
                if( get_col() - nextSquare < 0 )
                    break;

                if( null != board.piece_at( get_row(), get_col() - nextSquare ) )
                {
                    if( ( get_row() == row ) && ( get_col() - nextSquare == col ) )
                    {
                        return get_colour() != board.piece_at( get_row(), get_col() - nextSquare ).get_colour();
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
        }

        // Up
        if( ( row < get_row() ) && ( col == get_col() ) )
        {
            int movesRemainingUp = get_row() - row;
            for( int nextSquare = 1; nextSquare <= movesRemainingUp; nextSquare++ )
            {
                if( get_row() - nextSquare < 0 )
                    break;

                if( null != board.piece_at( get_row() - nextSquare, get_col() ) )
                {
                    if( ( get_row() - nextSquare == row ) && ( get_col() == col ) )
                    {
                        return get_colour() != board.piece_at( get_row() - nextSquare, get_col() ).get_colour();
                    }
                    else
                    {
                        return false;
                    }
                }
            }
            return true;
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
        return "Queen";
    }
}
