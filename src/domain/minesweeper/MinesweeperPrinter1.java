package domain.minesweeper;

import utility.InputUtils;

/**
 * 지뢰찾기 프린터 1 모양으로 출력한다.
 */
class MinesweeperPrinter1 implements MinesweeperPrinter
{
    @Override
    public int inputNumber(int size)
    {
        int col = InputUtils.readInt("행(R)",1,size);
        int row = InputUtils.readInt("열(C)",1,size);

        return (col-1) * size + row-1;
    }

    @Override
    public void endMsgPrint(int col, int row, int size)
    {
        System.out.printf("%d행 %d열은 폭탄입니다.\n",(col+1),(row+1));
    }

    @Override
    public void boardPrint(Cell[][] board)
    {
        int size = board.length;

        System.out.print(" ".repeat(6));
        for(int i = 1; i <= size; i++) {System.out.printf("%2d ",i);}
        System.out.println("C");

        System.out.print(" ".repeat(5));
        System.out.print("┌");
        System.out.print("─".repeat(size*3));
        System.out.println("┐");

        for(int i = 0; i < size; i++)
        {
            System.out.printf("%2d R │",(i+1));

            for(int j = 0; j < size; j++)
            {
                String str = getShape(board[i][j]);

                if(board[i][j].isChoice())
                {
                    str = "\u001B[1;93;100m" + str + "\u001B[0m";
                }

                System.out.printf("%3s",str);
            }

            System.out.println("│");
        }

        System.out.print(" ".repeat(5));
        System.out.print("└");
        System.out.print("─".repeat(size*3));
        System.out.println("┘");
    }

    @Override
    public String closeShape(Cell cell)
    {
        return " ■ ";
    }
}
