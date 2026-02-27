package domain.minesweeper;

import domain.base.GameTemplate;
import utility.ConsoleUtils;
import utility.InputUtils;

/**
 * 지뢰찾기 내부 로직
 */
class MinesweeperLauncher extends GameTemplate
{
    private Board board;

    private final int SIZE;
    private final int LEVEL;
    private final MinesweeperPrinter PRINT;

    private final int MINES_COUNT;
    private int chanceCount;

    private static final int[] chaneArr = {5,4,3};

    private int playerCol;
    private int playerRow;
    private int playerAnswer;
    private boolean first;

    // 생성자
    public MinesweeperLauncher(MinesweeperOption option)
    {
        SIZE = option.getSize();
        LEVEL = option.getLevel();
        PRINT = option.getPrinter();
        MINES_COUNT = SIZE*SIZE / 10 * LEVEL;
    }

    @Override
    protected void initialize()
    {
        ConsoleUtils.clear();
        InputUtils.readString("지뢰찾기 게임입니다.");
        board = new Board(SIZE,MINES_COUNT,PRINT);
        chanceCount = chaneArr[LEVEL-1];
        playerCol = -1;
        playerRow = -1;
        playerAnswer = -1;
        first = true;
    }

    @Override
    protected void render()
    {
        ConsoleUtils.clear();
        board.printBoard();
        System.out.println("난이도 : " + LEVEL);
        System.out.println("지뢰의 수 : " + MINES_COUNT);
        System.out.println("남은 찬스 : " + chanceCount);
    }

    @Override
    protected void inputHandle()
    {
        // 셀을 고르지 않은 상황
        if(playerCol == -1 || playerRow == -1)
        {
            if (PRINT == 1)
            {
                playerCol = InputUtils.readInt("열 번호 ", 1, SIZE) - 1;
                playerRow = InputUtils.readInt("행 번호 ", 1, SIZE) - 1;
            }
            else
            {
                int num = InputUtils.readInt("번호 : ", 1, SIZE * SIZE) - 1;
                playerCol = num / SIZE;
                playerRow = num % SIZE;
            }
        }

        // 셀을 고른 상황
        else
        {
            System.out.println("1. 오픈");
            System.out.println("2. 깃발");
            System.out.println("3. 스킬");
            System.out.println("4. 취소");

            playerAnswer = InputUtils.readInt("번호 : ",1,4);
        }
    }

    @Override
    protected void update()
    {
        // 사용자 입력에서 사용자가 셀을 고른 상황이라면
        // 해당 셀을 초이스 해줘야 하는 상황
        if(!board.isChoice(playerCol,playerRow))
        {
            if(board.isOpen(playerCol,playerRow))
            {
                InputUtils.readString("열린 칸은 오픈 할 수 없습니다.");
                playerCol = -1;
                playerRow = -1;
                playerAnswer = -1;
                return;
            }
            board.choiceCell(playerCol,playerRow);
        }
        // 사용자 입력에서 사용자가 셀을 고른 이후에 액션을 처리한 상황이라면
        // 해당하는 액션을 수행해야하는 상황
        else
        {
            switch (playerAnswer)
            {
                case 1 : openCell(); break;
                case 2 : flagCell(); break;
                case 3 : chanceCell(); break;
                case 4 : ;
            }

            board.cancelCell(playerCol,playerRow);

            if(board.isClear())
            {
                finish(true);
                return;
            }

            playerRow = -1;
            playerCol = -1;
            playerAnswer = -1;
        }
    }

    // 열기 액션 수행
    private void openCell()
    {
        if(first)
        {
            first = false;
            board.firstInput(playerCol,playerRow);
        }
        if(board.isOpen(playerCol,playerRow))
        {
            InputUtils.readString("이미 오픈된 칸입니다.");
            return;
        }
        else if(board.isFlag(playerCol,playerRow))
        {
            InputUtils.readString("깃발은 열 수 없습니다.");
            return;
        }
        else if(board.isMine(playerCol,playerRow))
        {
            finish(false);
            return;
        }

        board.openCell(playerCol,playerRow);
    }

    // 깃발 액션 수행
    private void flagCell()
    {
        board.toggleFlag(playerCol,playerRow);
    }
    // 스킬 액션 수행
    private void chanceCell()
    {
        if(chanceCount > 0)
        {
            chanceCount--;

            if(board.isMine(playerCol,playerRow))
            {
                InputUtils.readString("해당 칸은 지뢰입니다.");
            }
            else
            {
                InputUtils.readString("해당 칸은 지뢰가 아닙니다.");
            }
        }
        else
        {
            InputUtils.readString("찬스를 모두 사용했습니다.");
        }
    }

    // 게임 종료
    private void finish(boolean isWin)
    {
        board.openAll();
        board.printBoard();

        if(isWin)
        {
            System.out.println("지뢰를 전부 찾아냈습니다.");
        }
        else
        {
            if(PRINT == 1)
            {
                System.out.println((playerCol+1) + " 열" + (playerRow+1) + "행은 지뢰입니다.");
            }
            else
            {
                System.out.println(playerCol*SIZE+playerRow+1+"번은 지뢰입니다.");
            }
        }
        endGame();
    }
}
