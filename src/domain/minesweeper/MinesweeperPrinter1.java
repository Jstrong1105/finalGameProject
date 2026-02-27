package domain.minesweeper;

/**
 * 지뢰찾기 프린터 1
 */
class MinesweeperPrinter1 implements MinesweeperPrinter
{

    @Override
    public void print(Cell[][] board)
    {
        int size = board.length;

        System.out.print("==".repeat(size));
        System.out.print(" 지뢰찾기 ");
        System.out.println("==".repeat(size));
        System.out.println("====".repeat(size) + "==========");

        System.out.print("    ");

        System.out.print(" ".repeat(size/2));

        for(int i = 0; i < size; i++)
        {
            System.out.printf("%2d ",(i+1));
        }

        System.out.println();

        System.out.print(" ".repeat(size/2));

        System.out.print("   ┌");

        for(int i = 0; i < size*3; i++)
        {
            System.out.printf("%s","─");
        }

        System.out.print("─┐");

        System.out.println();

        for(int i = 0; i < size; i++)
        {
            System.out.print(" ".repeat(size/2));
            System.out.printf("%2d | ",(i+1));

            for(int j = 0; j < size; j++)
            {
                System.out.printf("%3s",getShape(board[i][j]));
            }

            System.out.println("| ");
        }

        System.out.print(" ".repeat(size/2));

        System.out.print("   └");

        for(int i = 0; i < size*3; i++)
        {
            System.out.printf("%s","─");
        }

        System.out.print("─┘");

        System.out.println();
    }

    private static final String[] OPEN_SHAPE = {" □ ","\u001B[92m ① \u001B[0m"
            ,"\u001B[94m ② \u001B[0m","\u001B[91m ③ \u001B[0m","\u001B[91m ④ \u001B[0m"
            ,"\u001B[91m ⑤ \u001B[0m","\u001B[91m ⑥ \u001B[0m","\u001B[91m ⑦ \u001B[0m"
            ,"\u001B[91m ⑧ \u001B[0m"};

    private static final String CLOSE_SHAPE = " ■ ";
    private static final String FLAG_SHAPE = " P ";
    private static final String MINE_SHAPE = " ※ ";

    private String getShape(Cell cell)
    {
        // 열린 상태라면
        if(cell.isOpen())
        {
            // 지뢰라면
            if(cell.isMine())
            {
                return MINE_SHAPE;
            }
            else
            {
                return OPEN_SHAPE[cell.getAdjacentMines()];
            }
        }

        // 열리진 않았고
        else
        {
            String shape = "";

            // 닫힌 상태라면
            if(cell.isClosed())
            {
                shape += CLOSE_SHAPE;
            }

            // 깃발 상태라면
            else if(cell.isFlagged())
            {
                shape += FLAG_SHAPE;
            }

            if(cell.isChoice())
            {
                shape = String.format("\u001B[100;93m%s\u001B[0m",shape);
            }

            return shape;
        }
    }
}
