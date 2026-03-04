package domain.minesweeper;

import utility.InputUtils;

/**
 * 지뢰찾기 프린터 2
 */
class MinesweeperPrinter2 implements MinesweeperPrinter
{
    @Override
    public int inputNumber(int size)
    {
        return InputUtils.readInt("번호",1,size*size) - 1;
    }

    @Override
    public void endMsgPrint(int col, int row, int size)
    {
        System.out.printf("%d번은 폭탄입니다.\n",(col*size)+(row+1));
    }

    @Override
    public void boardPrint(Cell[][] board)
    {
        int size = board.length;

        System.out.print(" ".repeat(5));
        System.out.print("┌");
        System.out.print("─".repeat(size*4+1));
        System.out.println("┐");

        for(int i = 0; i < size; i++)
        {
            System.out.print(" ".repeat(5));
            System.out.print("│");
            for(int j = 0; j < size; j++)
            {
                String str = getShape(board[i][j]);

                if(board[i][j].isClosed())
                {
                    str = String.format("%3d",i*size+j+1);
                }

                if(board[i][j].isChoice())
                {
                    str = "\u001B[1;93;100m" + str + "\u001B[0m";
                }

                System.out.printf(" %3s",str);
            }

            System.out.println(" │");
        }

        System.out.print(" ".repeat(5));
        System.out.print("└");
        System.out.print("─".repeat(size*4+1));
        System.out.println("┘");
    }

    @Override
    public String closeShape(Cell cell)
    {
        return "";
    }
}
