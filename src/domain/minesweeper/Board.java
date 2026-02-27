package domain.minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 지뢰찾기 보드판
 */
class Board
{
    // 상수
    private static final int[] DIRECTION_COL = {-1,-1,-1,0,0,1,1,1};
    private static final int[] DIRECTION_ROW = {-1,0,1,-1,1,-1,0,1};

    // 보드판
    private Cell[][] board;

    // 프린터
    private final MinesweeperPrinter printer;

    // 보드판 가로 세로 사이즈
    private final int SIZE;
    private final int TOTAL_CELL;

    // 지뢰 개수
    private final int MINE_COUNT;

    private static final Random RD = new Random();

    private int openCellCount;

    // 생성자
    Board(int size, int mineCount, MinesweeperPrinter printer)
    {
        SIZE = size;
        TOTAL_CELL = SIZE*SIZE;
        MINE_COUNT = mineCount;

        openCellCount = 0;

        this.printer = printer;
        boardReset();
    }

    // 보드판에 지뢰 채우기
    private void boardReset()
    {
        // 0 부터 SIZE*SIZE 까지 숫자가 들어있는 리스트를 만들고
        // 그걸 셔플해서 앞에서부터 원하는 개수만큼 뽑는 방식은
        // 지뢰 개수가 적을때는 셔플하는 자원소모가 더 비효울적이다.

        // 전체에서 랜덤하게 뽑는 방식은 지뢰의 비율이 굉장히 높을 경우
        // 거의 무한루프에 가까운 시간이 소모될 수 있지만
        // 지뢰의 비율이 50%를 초과하는지 여부에따라
        // 전체를 지뢰로 만들고 일반셀로 바꾸거나
        // 전체를 일반셀로 만들고 지뢰로 바꿀지를 나눔으로써
        // 셔플하는 방식보다 적은 자원으로 지뢰를 배치할 수 있다.

        board = new Cell[SIZE][SIZE];

        // 지뢰의 비율이 절반 이하라면 전체를 일반 셀로 채우고
        // 지뢰 개수만큼 지뢰로 만듬
        if(TOTAL_CELL/2 > MINE_COUNT)
        {
            for(int i = 0; i < SIZE; i++)
            {
                for(int j = 0; j < SIZE; j++)
                {
                    board[i][j] = new Cell(false);
                }
            }

            // 지뢰로 만들기
            RD.ints(0,TOTAL_CELL)
                    .distinct()
                    .limit(MINE_COUNT)
                    .forEach(num->board[num/SIZE][num%SIZE].setMine(true));
        }

        // 지뢰의 비율이 절반 초과라면 전체를 지뢰로 만들고
        // 지뢰가 아닌 개수만큼 일반 셀로 만듬
        else
        {
            for(int i = 0; i < SIZE; i++)
            {
                for(int j = 0; j < SIZE; j++)
                {
                    board[i][j] = new Cell(true);
                }
            }

            // 일반셀로 만들기
            RD.ints(0,TOTAL_CELL)
                    .distinct()
                    .limit((TOTAL_CELL)-MINE_COUNT)
                    .forEach(num->board[num/SIZE][num%SIZE].setMine(false));
        }

        // 지뢰의 위치는 사용자가 한번의 입력을 해야지만 확정된다.
        // 따라서 사용자가 한번의 입력을 하고 난 후에 인접 지뢰의 수를 계산한다.

        /*
        IntStream.range(0,SIZE*SIZE)
                .filter(num->board[num/SIZE][num%SIZE].isMine())
                .forEach(num->
                {
                    int nCol = num / SIZE;
                    int nRow = num % SIZE;

                    setAdjacentMines(nCol,nRow);
                });
         */
    }

    // 출력하기
    void printBoard()
    {
        printer.print(board);
    }

    // 좌표가 보드판 범위를 벗어났는지 확인하는 메소드
    private boolean isOutOfArray(int col, int row)
    {
        return col < 0 || row < 0 || col >= SIZE || row >= SIZE;
    }

    // 보드판의 범위를 벗어난 값을 입력해 에러를 던지는 래퍼 메소드
    private void arrayOutCheck(int col, int row)
    {
        if(isOutOfArray(col,row))
        {
            throw new IllegalArgumentException("보드판의 범위를 벗어난 입력입니다.");
        }
    }

    // 선택한 셀인지 확인하기
    boolean isChoice(int col,int row)
    {
        return board[col][row].isChoice();
    }

    // 한칸 선택하기
    void choiceCell(int col, int row)
    {
        arrayOutCheck(col,row);

        // 이미 열린 셀은 선택할 수 없다.
        if(!board[col][row].isOpen())
        {
            board[col][row].setChoice(true);
        }
    }

    // 선택 취소하기
    void cancelCell(int col, int row)
    {
        arrayOutCheck(col,row);

        board[col][row].setChoice(false);
    }

    // 깃발 체크
    void toggleFlag(int col, int row)
    {
        arrayOutCheck(col,row);

        board[col][row].flagToggle();
    }

    // 첫 입력 체크
    void firstInput(int col, int row)
    {
        arrayOutCheck(col,row);

        // 처음 입력한 값이 지뢰라면 다른 곳으로 지뢰를 옮긴다.
        if(board[col][row].isMine())
        {
            RD.ints(0,TOTAL_CELL)
                    .distinct()
                    .filter(num->!board[num/SIZE][num%SIZE].isMine())
                    .limit(1)
                    .forEach(index->
                    {
                        int nCol = index / SIZE;
                        int nRow = index % SIZE;

                        board[col][row].setMine(false);
                        board[nCol][nRow].setMine(true);
                    });
        }

        // 사용자가 한칸을 입력해야 지뢰의 위치가 확정되기때문에 이때 인접 지뢰 수를 계산한다.

        // 지뢰 주변 인접 지뢰 수 증가 시키기
        IntStream.range(0,SIZE*SIZE)
                .filter(num->board[num/SIZE][num%SIZE].isMine())
                .forEach(num->
                {
                    int nCol = num / SIZE;
                    int nRow = num % SIZE;

                    setAdjacentMines(nCol,nRow);
                });
    }

    // 지뢰 근처 셀에 인접 지뢰 수를 증감시키는 메소드
    private void setAdjacentMines(int col, int row)
    {
        for(int i = 0; i < DIRECTION_COL.length; i++)
        {
            int nCol = col + DIRECTION_COL[i];
            int nRow = row + DIRECTION_ROW[i];

            if(!isOutOfArray(nCol,nRow))
            {
                board[nCol][nRow].addAdjacentMines();
            }
        }
    }

    // 한칸 열림 여부 확인하기
    boolean isOpen(int col, int row)
    {
        arrayOutCheck(col,row);

        return board[col][row].isOpen();
    }

    // 한칸 깃발 여부 확인하기
    boolean isFlag(int col, int row)
    {
        arrayOutCheck(col,row);

        return board[col][row].isFlagged();
    }

    // 한칸 폭탄 여부 확인하기
    boolean isMine(int col, int row)
    {
        arrayOutCheck(col,row);

        return board[col][row].isMine();
    }

    // 한칸 열기
    void openCell(int col, int row)
    {
        arrayOutCheck(col,row);

        Cell cell = board[col][row];

        if(cell.isClosed())
        {
            cell.openCell();
            openCellCount++;

            if(cell.getAdjacentMines() == 0)
            {
                openAdjacentCell(col,row);
            }
        }
    }

    // 주변에 지뢰가 없는 칸을 열어서 연쇄적으로 주변 8칸을 열기
    private void openAdjacentCell(int col,int row)
    {
        for(int i = 0; i < DIRECTION_COL.length; i++)
        {
            int nCol = col + DIRECTION_COL[i];
            int nRow = row + DIRECTION_ROW[i];

            if(!isOutOfArray(nCol,nRow))
            {
                openCell(nCol,nRow);
            }
        }
    }

    // 모든 칸을 열었는지 확인하기
    boolean isClear()
    {
        /*
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(cell->!cell.isMine() || cell.isOpen());
         */
        // 위의 방식은 보드판 전체를 돌면서 확인하는 구조 직관적이라곤 할 수 있겠지만
        // 조금 자원 소모가 큰거 같다.

        return openCellCount >= TOTAL_CELL - MINE_COUNT;
    }

    // 게임이 종료되어서 모든 칸을 오픈하기
    void openAll()
    {
        Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(cell -> !cell.isOpen())
                .forEach(Cell::openAll);
    }
}
