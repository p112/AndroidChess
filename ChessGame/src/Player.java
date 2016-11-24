import java.util.List;
import java.util.ArrayList;

public class Player
{
    private int                 colour;

    private ArrayList<Chess_Piece>   chess_pieces;

    private static final int MAX_PIECES = 16;

    /**
     * Default constructor
     *
     * @param colour the player's colour - white or black.
     */
    Player( int colour )
    {
        this.colour     = colour;
        chess_pieces    = new ArrayList<Chess_Piece>( MAX_PIECES );
    }

    /**
     * Returns the colour of the player.
     *
     * @return the colour of the player
     */
    public int get_colour()
    {
        return colour;
    }

    /**
     * Returns an ArrayList containing all of the player's chess pieces
     *
     * @return a list of chess pieces.
     */
    public ArrayList< Chess_Piece > get_chess_pieces()
    {
        return chess_pieces;
    }

    /**
     * Adds a chess piece to the player's list of chess pieces.
     *
     * @param piece the chess piece to be added.
     */
    public void add_chess_piece( Chess_Piece piece )
    {
        chess_pieces.add( piece );
    }

    /**
     * Removes a chess piece to the player's list of chess pieces.
     *
     * @param piece the chess piece to be removed.
     */
    public void remove_chess_piece( Chess_Piece piece )
    {
        chess_pieces.remove( piece );
    }
}

    

