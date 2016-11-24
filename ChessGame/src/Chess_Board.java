import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.lang.Character;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;

/**
 * The <code>Chess_Board</code> comprises of 8 x 8 squares. Each square can be
 * located by ( row, column ).
 */
public final class Chess_Board implements ActionListener
{
    private final JPanel            panel_gui = new JPanel( new BorderLayout( 3, 3 ) );

    private JPanel                  chess_board_panel;
    private final JButton[][]       chess_board_squares;
    private final Chess_Piece[][]   chess_board_pieces;

    private final Image[][]         chess_piece_images;

    private static final String     COLS = "ABCDEFGH";

    private int                     active_player;
    private int                     inactive_player;

    private Square                  active_square;
    private boolean                 is_square_active;

    private boolean                 display_valid_moves;
    private boolean                 display_pieces_under_attack;

    private int                     games_played;

    public static final int BOARD_SIZE = 8;

    public static final int QUEEN = 0, KING = 1, ROOK = 2, KNIGHT = 3,
            BISHOP = 4, PAWN = 5;

    public static final int[] STARTING_ROW = { ROOK, KNIGHT, BISHOP, KING,
            QUEEN, BISHOP, KNIGHT, ROOK
    };

    public static final int BLACK = 0, WHITE = 1;

    /**
     * Default Constructor
     */
    public Chess_Board()
    {
        games_played                = 0;
        chess_board_pieces          = new Chess_Piece[ BOARD_SIZE ][ BOARD_SIZE ];
        chess_piece_images          = new Image[ 2 ][ 6 ];
        chess_board_squares         = new JButton[ BOARD_SIZE ][ BOARD_SIZE ];
        active_square               = new Square( 0, 0 );
        is_square_active            = false;
        display_valid_moves         = false;
        display_pieces_under_attack = false;

        initialise();
    }

    /**
     * This method initialises the chess board
     */
    private void initialise()
    {
        create_gui();
        create_squares();
        create_chess_piece_images();
    }

    private void create_gui()
    {
        chess_board_panel = new JPanel( new GridLayout( 0, 8 ) );
        chess_board_panel.setBorder( new CompoundBorder( new EmptyBorder( 8, 8, 8, 8 ), new LineBorder( Color.BLACK ) ) );
        chess_board_panel.setBackground( Color.BLACK );

        JPanel board_constraints = new JPanel( new GridBagLayout() );
        board_constraints.setBackground( Color.RED );
        board_constraints.add( chess_board_panel );
        panel_gui.add( board_constraints );
    }

    /**
     * Creates all of the chess pieces and positions them correctly on the chess board.
     */
    private void create_chess_pieces()
    {
        for( int next_piece = 0; next_piece < 8; next_piece++ )
        {
            add_piece( new Pawn( 6, next_piece, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 5 ], this ) );
            add_piece( new Pawn( 1, next_piece, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 5 ], this ) );
        }

        add_piece( new Knight( 7, 1, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 3 ], this ) );
        add_piece( new Knight( 7, 6, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 3 ], this ) );
        add_piece( new Knight( 0, 1, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 3 ], this ) );
        add_piece( new Knight( 0, 6, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 3 ], this ) );

        add_piece( new Bishop( 7, 2, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 4 ], this ) );
        add_piece( new Bishop( 7, 5, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 4 ], this ) );
        add_piece( new Bishop( 0, 2, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 4 ], this ) );
        add_piece( new Bishop( 0, 5, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 4 ], this ) );

        add_piece( new Rook( 7, 0, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 2 ], this ) );
        add_piece( new Rook( 7, 7, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 2 ], this ) );
        add_piece( new Rook( 0, 0, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 2 ], this ) );
        add_piece( new Rook( 0, 7, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 2 ], this ) );

        add_piece( new Queen( 7, 3, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 1 ], this ) );
        add_piece( new Queen( 0, 3, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 1 ], this ) );

        add_piece( new King( 7, 4, Chess_Piece.WHITE, chess_piece_images[ 1 ][ 0 ], this ) );
        add_piece( new King( 0, 4, Chess_Piece.BLACK, chess_piece_images[ 0 ][ 0 ], this ) );
    }

    /**
     * Deletes all of the chess pieces.
     */
    private void delete_chess_pieces()
    {
        Game.get_player( WHITE ).get_chess_pieces().clear();
        Game.get_player( BLACK ).get_chess_pieces().clear();

        for( int row = 0; row < BOARD_SIZE; row++ )
            for( int col = 0; col < BOARD_SIZE; col++ )
                chess_board_pieces[ row ][ col ] = null;
    }

    /**
     * This method renders the chess board
     */
    private void render()
    {
        for( int row = 0; row < 8; row++ )
        {
            for( int col = 0; col < 8; col++ )
            {
                if( null != piece_at( row, col ) )
                {
                    Chess_Piece current_piece = piece_at( row, col );
                    chess_board_squares[ row ][ col ].setIcon( new ImageIcon( current_piece.get_icon_image() ) );
                }
                else
                {
                    chess_board_squares[ row ][ col ].setIcon( null );
                }
            }
        }
    }

    /**
     * This method creates the chess piece images that are used to fill the squares
     * that make up the board.
     */
    private void create_chess_piece_images()
    {
        try
        {
            URL url = new URL( "http://i.stack.imgur.com/memI0.png" );
            BufferedImage bi = ImageIO.read( url );
            for( int colour = 0; colour < 2; colour++ )
            {
                for( int piece = 0; piece < 6; piece++ )
                {
                    chess_piece_images[ colour ][ piece ] = bi.getSubimage( piece * 64, colour * 64, 64, 64 );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }

    /**
     * This method creates all of the 64 squares that make up the chess board
     */
    private void create_squares()
    {
        Insets button_margin = new Insets( 0, 0, 0, 0 );
        for( int row = 0; row < BOARD_SIZE; row++ )
        {
            for( int col = 0; col < BOARD_SIZE; col++ )
            {
                JButton button = new JButton();

                String row_string = Integer.toString( row );
                String col_string = Integer.toString( col );
                button.setActionCommand( row_string + col_string );
                button.addActionListener( this );

                ImageIcon icon = new ImageIcon( new BufferedImage( 64, 64, BufferedImage.TYPE_INT_ARGB ) );
                button.setIcon( icon );

                button.setMargin( button_margin );
                button.setOpaque( true );

                chess_board_squares[ row ][ col ] = button;
                chess_board_panel.add( chess_board_squares[ row ][ col ] );
            }
        }
    }

    /**
     * This method creates the labels around the edge of the Chess_Board
     */
    private void create_labels()
    {
        chess_board_panel.add( new JLabel( "" ) );

        // fill the top row
        for( int i = 0; i < 8; i++ )
        {
            chess_board_panel.add( new JLabel( COLS.substring( i, i + 1 ), SwingConstants.CENTER ) );
        }

        // fill the black non-pawn piece row
        for( int i = 0; i < 8; i++ )
        {
            for( int j = 0; j < 8; j++ )
            {
                switch( j )
                {
                    case 0:
                        chess_board_panel.add( new JLabel( "" + ( 9 - ( i + 1 ) ), SwingConstants.CENTER ) );
                    default:
                }
            }
        }
    }

    /**
     * This method is called when one of the board squares
     * are clicked by the player. It calls the event handler
     * that relates to the square that has been clicked.
     *
     * @param e
     */
    public void actionPerformed( ActionEvent e )
    {
        String cmd = e.getActionCommand();

        char row_char = cmd.charAt( 0 );
        int row_int = Character.getNumericValue( row_char );

        char col_char = cmd.charAt( 1 );
        int col_int = Character.getNumericValue( col_char );

        on_square_click( row_int, col_int );
    }

    /**
     * Returns the chess piece located at ( row, col ).
     *
     * @param row
     * @param col
     * @return
     */
    public Chess_Piece piece_at( int row, int col )
    {
        return chess_board_pieces[ row ][ col ];
    }

    /**
     * Adds a chess piece to the chess board.
     *
     * @param piece the piece to be added
     */
    private void add_piece( Chess_Piece piece )
    {
        chess_board_pieces[ piece.get_row() ][ piece.get_col() ] = piece;

        if( WHITE == piece.get_colour() )
        {
            Game.get_player( WHITE ).add_chess_piece( piece );
        }
        else
        {
            Game.get_player( BLACK ).add_chess_piece( piece );
        }
    }

    /**
     * Removes a chess piece from the chess board
     *
     * @param row the row where the chess piece is located
     * @param col the column where the chess piece is located
     */
    private void remove_piece( int row, int col )
    {
        Game.get_player( piece_at( row, col ).get_colour() ).remove_chess_piece( piece_at( row, col ) );
        chess_board_pieces[ row ][ col ] = null;
    }

    /**
     * This method simply returns the graphical user interface
     * to the calling method.
     *
     * @return the graphical user interface to be returned.
     */
    public final JComponent get_gui()
    {
        return panel_gui;
    }

    /**
     * Invoked automatically when a player clicks on one of the
     * 8 x 8 squares that make up the chess board.
     *
     * @param row the row of the square that's been clicked
     * @param col the column of the square that's been clicked
     */
    private void on_square_click( int row, int col )
    {
        if( !square_activated() )
        {
            if( null != piece_at( row, col ) )
            {
                if( piece_at( row, col ).get_colour() == Game.get_player( get_active_player() ).get_colour() )
                {
                    set_activated_square( row, col );
                    highlight_square( row, col, Color.BLACK );
                    if( is_displaying_valid_moves() ) show_valid_moves( row, col );
                    System.out.println( "Selected " + piece_at( row, col ).toString()
                            + " on row " + row + "; col " + col + "." );
                }
            }
        }
        else
        {
            Square activated_square = new Square( get_active_square().get_row(), get_active_square().get_col() );
            if( ( activated_square.get_row() == row ) && ( activated_square.get_col() == col ) )
            {
                deactivate_square( activated_square.get_row(), activated_square.get_col() );
                unhighlight_all_squares();
                if( get_setting_pieces_under_attack() ) show_pieces_under_attack();
                System.out.println( "Unselected " + piece_at( row, col ).toString()
                        + " on row " + row + "; col " + col + "." );
            }
            else
            {
                if( piece_at( activated_square.get_row(), activated_square.get_col() ).can_move_to( row, col, this ) )
                {
                    if( ( piece_at( activated_square.get_row(), activated_square.get_col() ) instanceof King ) )
                    {
                        if( activated_square.get_col() < col )
                        {
                            move_piece( new Square( activated_square.get_row(), activated_square.get_col() + 3 ), new Square( row, col - 1 ) );
                        }
                        else
                        {
                            move_piece( new Square( activated_square.get_row(), activated_square.get_col() + 3 ), new Square( row, col + 3 ) );
                        }
                    }
                    else
                    {
                        move_piece( activated_square, new Square( row, col ) );
                    }
                    change_active_player();
                    deactivate_square( activated_square.get_row(), activated_square.get_col() );
                    unhighlight_all_squares();
                    if( get_setting_pieces_under_attack() ) show_pieces_under_attack();
                    render();
                    System.out.println( "Moved " + piece_at( row, col ).toString()
                            + " to row " + row + "; col " + col + "." );
                }
                else
                {
                    System.out.println( "Unable to move " + piece_at( get_active_square().get_row(),
                            get_active_square().get_col() ).toString() + " to row " + row + "; col " + col + "." );
                }
            }
        }
    }

    /**
     * This method swaps the active player. For example,
     * if white is the active player, calling this method
     * will make black the active player.
     */
    private void change_active_player()
    {
        if( WHITE == get_active_player() )
        {
            set_active_player( BLACK );
        }
        else
        {
            set_active_player( WHITE );
        }
    }

    /**
     * This method sets the background colour of all the squares to their
     * default colour: white.
     */
    private void unhighlight_all_squares()
    {
        for( int row = 0; row < Chess_Board.BOARD_SIZE; row++ )
            for( int col = 0; col < Chess_Board.BOARD_SIZE; col++ )
                chess_board_squares[ row ][ col ].setBackground( Color.WHITE );
    }

    /**
     * Highlights all of the active player's pieces that are currently under attack.
     */
    private void show_pieces_under_attack()
    {
        for( Chess_Piece player_piece : Game.get_player( get_active_player() ).get_chess_pieces() )
        {
            for( Chess_Piece enemy_piece : Game.get_player( get_inactive_player() ).get_chess_pieces() )
            {
                if( enemy_piece.can_move_to( player_piece.get_row(), player_piece.get_col(), this ) )
                {
                    highlight_square( player_piece.get_row(), player_piece.get_col(), Color.RED );
                    break;
                }
            }
        }
    }

    /**
     * @param flag
     */
    public void highlight_pieces_under_attack( boolean flag )
    {
        display_pieces_under_attack = flag;
    }

    public boolean get_setting_pieces_under_attack()
    {
        return display_pieces_under_attack;
    }

    /**
     * Highlights all of the squares that the player can move their chess piece to
     *
     * @param row
     * @param col
     */
    private void show_valid_moves( int row, int col )
    {
        List< Square > valid_moves = piece_at( row, col ).all_valid_moves( this );
        for( Square valid_move : valid_moves )
            highlight_square( valid_move.get_row(), valid_move.get_col(), Color.GREEN );
    }

    /**
     * Hides the valid moves from the player
     *
     * @param row
     * @param col
     */
    private void hide_valid_moves( int row, int col )
    {
        List< Square > validMoves = piece_at( row, col ).all_valid_moves( this );
        for( Square nextSquare : validMoves )
            highlight_square( nextSquare.get_row(), nextSquare.get_col(), Color.WHITE );
    }

    /**
     * Moves a piece from one square on the Chess_Board to another.
     *
     * @param from the square the piece is moving from
     * @param to   the square the piece is moving to
     */
    private void move_piece( Square from, Square to )
    {
        piece_at( from.get_row(), from.get_col() ).move_to( to.get_row(), to.get_col() );
        chess_board_pieces[ to.get_row() ][ to.get_col() ] = piece_at( from.get_row(), from.get_col() );
        chess_board_pieces[ from.get_row() ][ from.get_col() ] = null;
        chess_board_squares[ to.get_row() ][ to.get_col() ].setIcon( new ImageIcon( piece_at( to.get_row(), to.get_col() ).get_icon_image() ) );
        chess_board_squares[ from.get_row() ][ from.get_col() ].setIcon( null );
    }

    /**
     * Returns the location of the clicked square.
     *
     * @return
     */
    private Square get_active_square()
    {
        return active_square;
    }

    /**
     * Returns true if the player has already selected a square, false otherwise.
     *
     * @return
     */
    private boolean square_activated()
    {
        return is_square_active;
    }

    /**
     * Sets the active square on the chess board, i.e. the last square
     * clicked on by the player.
     */
    private void set_activated_square( int row, int col )
    {
        active_square = new Square( row, col );
        is_square_active = true;
    }

    /**
     * Highlights a specified square on the chessboard with the supplied
     * colour
     */
    private void highlight_square( int row, int col, Color c )
    {
        chess_board_squares[ row ][ col ].setBackground( c );
    }

    /**
     * Brings the GameBoard back to it's original start state
     */
    public void new_game()
    {
        if( games_played > 0 )
        {
            delete_chess_pieces();
            unselect_all();
        }
        create_chess_pieces();
        set_active_player( WHITE );
        render();
    }

    /**
     * Returns the specified square on the displayed chessboard to its default color
     */
    private void deactivate_square( int row, int col )
    {
        chess_board_squares[ row ][ col ].setBackground( Color.WHITE );
        is_square_active = false;
    }

    /**
     * Returns all squares on the displayed chessboard to their default colors
     */
    private void unselect_all()
    {
        for( int row = 0; row < 8; row++ )
        {
            for( int col = 0; col < 8; col++ )
            {
                deactivate_square( row, col );
            }
        }
    }

    /**
     * Sets whether all valid moves should be shown each time the player selects
     * a chess piece.
     */
    public void display_valid_moves( boolean flag )
    {
        display_valid_moves = flag;
    }

    /**
     * Returns true if the user wants to display all valid when they select a
     * chess piece
     *
     * @return
     */
    private boolean is_displaying_valid_moves()
    {
        return display_valid_moves;
    }

    /**
     * Returns an array containing all the images of the chess pieces.
     */
    private Image[][] get_icon_images()
    {
        return chess_piece_images;
    }

    // Changes the labeling convention for the chessboard as it is drawn to the screen.
    // Passing in true will label the chessboard according to the official convention,
    // while passing in false will label the chessboard according to the ACM Graphics convention.
    private void use_real_chess_labels( boolean useLabel )
    {
    }

    /**
     * Returns the active player
     *
     * @return
     */
    public int get_active_player()
    {
        return active_player;
    }

    /**
     * Sets the active player. 0 indicates BLACK whereas
     * 1 indicates WHITE
     *
     * @param player the active player
     */
    private void set_active_player( int player )
    {
        active_player = player;
    }

    /**
     * Returns the inactive player
     *
     * @return
     */
    public int get_inactive_player()
    {
        return inactive_player;
    }

    /**
     * Sets the inactive player. 0 indicates BLACK whereas
     * 1 indicates WHITE
     *
     * @param player the inactive player
     */
    private void set_inactive_player( int player )
    {
        inactive_player = player;
    }

    /**
     * @return
     */
    private boolean check()
    {
        return false;
    }

    /**
     * @return
     */
    private boolean checkmate()
    {
        return false;
    }

    /**
     * @return
     */
    private boolean stalemate()
    {
        return false;
    }
}