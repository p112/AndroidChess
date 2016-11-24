import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;

/**
 * The <code>Game</code> class is the primary class for the chess application.
 */
public final class Game extends JFrame implements ActionListener
{
    private Chess_Board chess_board;

    private static Player  white_player;
    private static Player  black_player;

    public static final int BLACK = 0, WHITE = 1;

    /**
     * Default constructor
     */
    public Game()
    {
        super("Danny's Chess Game");
        white_player = new Player( WHITE );
        black_player = new Player( BLACK );
    }

    /**
     * <Code>InitInstance</Code> initialises the chess application
     * @return true if the initialisation is successful, false otherwise.
     */
    public boolean init_instance()
    {
        build_menu();
        create_chess_board();
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        setLocationByPlatform( true );
        pack();
        setMinimumSize( getSize() );
        setMaximumSize( getSize() );
        setVisible( true );
        return true;
    }

    /**
     * Creates the Chess_Board and configures it
     */
    private void create_chess_board()
    {
        chess_board = new Chess_Board();
        add( chess_board.get_gui() );
        chess_board.display_valid_moves( true );
        chess_board.highlight_pieces_under_attack( true );
    }

    /**
     * The game loop
     */
    public void game_loop()
    {
    }

    /**
     * New Game
     */
    public void new_game()
    {

    }

    /**
     * Creates the menu bar
     */
    private void build_menu()
    {
        JMenuBar menu_bar = new JMenuBar();

        JMenu game_menu = new JMenu( "Game" );
        game_menu.setMnemonic(KeyEvent.VK_A);
        game_menu.getAccessibleContext().setAccessibleDescription("The game menu");
        game_menu.add( menu_bar );

        JMenuItem game_new_computer = new JMenuItem( "New game against computer", KeyEvent.VK_T );
        game_new_computer.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_new_computer.getAccessibleContext().setAccessibleDescription("Start a new game with a computer player.");
        game_new_computer.setActionCommand( "Game_New_Computer" );
        game_new_computer.addActionListener( this );
        game_menu.add( game_new_computer );

        JMenuItem game_new_human = new JMenuItem( "New game against human", KeyEvent.VK_T );
        game_new_human.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_new_human.getAccessibleContext().setAccessibleDescription("Start a new game with a human player.");
        game_new_human.setActionCommand( "Game_New_Human" );
        game_new_human.addActionListener( this );
        game_menu.add( game_new_human );

        game_menu.addSeparator();

        JMenuItem game_undo_move = new JMenuItem("Undo move", KeyEvent.VK_T);
        game_undo_move.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_undo_move.getAccessibleContext().setAccessibleDescription("Undo the last move that was played.");
        game_undo_move.addActionListener( this );
        game_menu.add( game_undo_move );

        game_menu.addSeparator();

        JMenuItem game_stats = new JMenuItem("Statistics", KeyEvent.VK_8);
        game_stats.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_stats.getAccessibleContext().setAccessibleDescription("Display stats for the game.");
        game_stats.addActionListener( this );
        game_menu.add( game_stats );

        JMenuItem game_options = new JMenuItem("Options", KeyEvent.VK_8);
        game_options.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_options.getAccessibleContext().setAccessibleDescription("Display options for the game.");
        game_options.addActionListener( this );
        game_menu.add( game_options );

        game_menu.addSeparator();

        JMenuItem game_help = new JMenuItem("Help", KeyEvent.VK_8);
        game_help.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_help.getAccessibleContext().setAccessibleDescription("Display the help screen.");
        game_help.addActionListener( this );
        game_menu.add( game_help );

        game_menu.addSeparator();

        JMenuItem game_resign = new JMenuItem("Resign Game", KeyEvent.VK_8);
        game_resign.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_resign.getAccessibleContext().setAccessibleDescription("Resign from the current game.");
        game_resign.addActionListener( this );
        game_menu.add( game_resign );

        JMenuItem game_exit = new JMenuItem("Exit Game", KeyEvent.VK_8);
        game_exit.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        game_exit.getAccessibleContext().setAccessibleDescription("Exit from the current game and return to the OS.");
        game_exit.addActionListener( this );
        game_menu.add( game_exit );

        menu_bar.add( game_menu );

        JMenu debug_menu = new JMenu( "Debug" );

        JMenuItem debug_move_all_pieces = new JMenuItem( "Move all pieces", KeyEvent.VK_8 );
        debug_move_all_pieces.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
        debug_move_all_pieces.getAccessibleContext().setAccessibleDescription("Each player can move any piece they wish.");
        debug_move_all_pieces.addActionListener( this );
        debug_menu.add( debug_move_all_pieces );

        menu_bar.add( debug_menu );

        setJMenuBar( menu_bar );
    }

    /**
     * Invoked when one of the menu items are clicked
     *
     * @param e the action command
     */
    @Override
    public void actionPerformed( ActionEvent e)
    {
        String cmd = e.getActionCommand();

        switch( cmd )
        {
            case "Game_New_Computer":
                chess_board.new_game();
                break;

            case "Game_New_Human":
                chess_board.new_game();
                break;

            default:
                System.out.println( "Unknown action in Game detected");
                break;
        }
    }

    /**
     * Returns the white/black player
     * @param colour the player of the colour
     */
    public static Player get_player( int colour )
    {
        if( WHITE == colour )
        {
            return white_player;
        }
        else
        {
            return black_player;
        }
    }

    /**
     * Application entry point
     */
    public static void main(String[] args)
    {
        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                Game chessGame = new Game();
                chessGame.init_instance();
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}






