package domain.minesweeper;

/**
 * 지뢰찾기 보드판 프린터
 */
interface MinesweeperPrinter
{
     String[] OPEN_SHAPE = {" □ ","\u001B[92m ① \u001B[0m"
            ,"\u001B[94m ② \u001B[0m","\u001B[91m ③ \u001B[0m","\u001B[91m ④ \u001B[0m"
            ,"\u001B[91m ⑤ \u001B[0m","\u001B[91m ⑥ \u001B[0m","\u001B[91m ⑦ \u001B[0m"
            ,"\u001B[91m ⑧ \u001B[0m"};

     String FLAG_SHAPE = " P ";
     String MINE_SHAPE = " ※ ";

    default void print(Cell[][] board)
    {
        int size = board.length;
        System.out.print("==".repeat(size));
        System.out.print(" 지뢰찾기 ");
        System.out.println("==".repeat(size));
        System.out.println("====".repeat(size) + "==========");

        boardPrint(board);
    }

   default String getShape(Cell cell)
   {
        // 열렸다면
        if(cell.isOpen())
        {
            if(cell.isMine())
            {
                return MINE_SHAPE;
            }
            else
            {
                return OPEN_SHAPE[cell.getAdjacentMines()];
            }
        }

        // 깃발이라면
        else if(cell.isFlagged())
        {
            return FLAG_SHAPE;
        }

        // 닫혀있다면
        else if(cell.isClosed())
        {
            return closeShape(cell);
        }

        // 예외 적인 상황이라면
        else
        {
            throw new IllegalStateException("셀의 상태를 확인할 수 없습니다.");
        }
   }

    String closeShape(Cell cell);

    void boardPrint(Cell[][] board);

    int inputNumber(int size);

    void endMsgPrint(int col,int row, int size);
}
