/**
 * The <code>Square</code> class is used to represent one of the 64 squares
 * that make up the <code>Chess_Board</code>.
 */
public class Square
{
    private int row;
    private int col;
    private int colour;

    /**
     * Default Constructor
     *
     * @param row row of the position
     * @param col column of the position
     */
    public Square( int row, int col )
    {
        this.row = row;
        this.col = col;
    }

    /**
     * Alternative constructor
     */
    public Square( int row, int col, int colour )
    {
        this.row = row;
        this.col = col;
        this.colour = colour;
    }

    /**
     * Returns the row that the square sits on
     */
    public int get_row()
    {
        return row;
    }

    /**
     * Returns the column that the square sits on.
     *
     * @return column of the position
     */
    public int get_col()
    {
        return col;
    }

    /**
     * Returns a string containing the square's row and column number.
     */
    public String toString()
    {
        return Integer.toString( row  ) + Integer.toString( col );
    }
}
