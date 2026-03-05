package domain.minesweeper;

import domain.base.GameMenu;
import domain.base.GameTemplate;
import utility.ConsoleUtils;
import utility.InputUtils;
import utility.MenuUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

/**
 * 지뢰찾기 내부 로직
 */
class MinesweeperLauncher extends GameTemplate
{
    // 보드판
    private Board board;

    // 크기 / 난이도 / 지뢰의 수
    private final int size,level,minesCount;

    // 찬스 횟수
    private int chanceCount;

    // 보드판 프린터
    private final MinesweeperPrinter printer;

    // 난이도에 따른 찬스 사용 가능 횟수
    private static final int[] chanceArr = {5,4,3};

    // 플레이어가 선택한 행 / 열 / 액션
    private int playerRow,playerCol;

    // 첫 입력 여부
    private boolean first;

    // 시작 시간
    private Instant startTime;

    // 생성자
    public MinesweeperLauncher(MinesweeperOption option)
    {
        size = option.getSize();
        level = option.getLevel();
        printer = option.getPrinter();
        minesCount = size * size / 10 * level;
    }

    @Override
    protected void initialize()
    {
        ConsoleUtils.clear();
        InputUtils.readString("지뢰찾기 게임입니다.");

        board = new Board(size,minesCount, printer);
        chanceCount = chanceArr[level -1];
        playerRow = -1;
        playerCol = -1;
        first = true;
        startTime = Instant.now();
    }

    @Override
    protected void render()
    {
        ConsoleUtils.clear();
        board.printBoard();
        System.out.println("난이도 : " + level);
        System.out.println("지뢰의 수 : " + minesCount);
        System.out.println("남은 찬스 : " + chanceCount);
    }

    @Override
    protected void inputHandle()
    {
        playerRow = -1;
        playerCol = -1;

        while(true)
        {
            int number = printer.inputNumber(size);

            playerRow = number / size;
            playerCol = number % size;

            // 오픈한 칸은 선택할 수 없다.
            if(board.isOpen(playerRow,playerCol))
            {
                InputUtils.readString("이미 오픈한 칸입니다.");
            }
            else
            {
                // 플레이어가 입력한 칸 선택해주기
                board.choiceCell(playerRow,playerCol);

                render();

                break;
            }
        }
    }

    @Override
    protected void update()
    {
        MenuUtils.showOption(this,CellActionMenu.values(),"셀 옵션");

        board.cancelCell(playerRow,playerCol);

        if(board.isClear())
        {
            finish(true);
        }
    }

    private enum CellActionMenu implements GameMenu<MinesweeperLauncher>
    {
        OPEN("열기","선택한 셀을 엽니다.",MinesweeperLauncher::openCell),
        FLAG("깃발","선택한 셀에 깃발을 설치하거나 회수합니다.",MinesweeperLauncher::flagCell),
        CHANCE("찬스","선택한 셀이 지뢰인지 확인합니다.",MinesweeperLauncher::chanceCell)
        ;

        CellActionMenu(String name,String explain, Consumer<MinesweeperLauncher> setter)
        {
            this.name = name;
            this.explain = explain;
            this.setter = setter;
        }

        private final String name;
        private final String explain;
        private final Consumer<MinesweeperLauncher> setter;

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public String getExplain()
        {
            return explain;
        }

        @Override
        public void setAction(MinesweeperLauncher menu)
        {
            setter.accept(menu);
        }
    }

    // 열기 액션 수행
    private void openCell()
    {
        // 첫 입력이라면
        if(first)
        {
            first = false;
            board.firstInput(playerRow,playerCol);
        }

        // 깃발이 설치된 칸이라면
        if(board.isFlag(playerRow,playerCol))
        {
            InputUtils.readString("깃발은 열 수 없습니다.");
            return;
        }

        // 지뢰인 칸이라면
        else if(board.isMine(playerRow,playerCol))
        {
            finish(false);
            return;
        }

        board.openCell(playerRow,playerCol);
    }

    // 깃발 액션 수행
    private void flagCell()
    {
        board.toggleFlag(playerRow,playerCol);
    }

    // 스킬 액션 수행
    private void chanceCell()
    {
        // 찬스의 횟수가 남아 있다면
        if(chanceCount > 0)
        {
            // 찬스를 차감하고
            chanceCount--;

            // 해당 칸의 지뢰 여부를 알려준다.
            if(board.isMine(playerRow,playerCol))
            {
                InputUtils.readString("해당 칸은 지뢰입니다.");

                // 해당 칸이 지뢰고 깃발이 아니라면 깃발을 설치한다.
                if(!board.isFlag(playerRow,playerCol))
                {
                    board.toggleFlag(playerRow,playerCol);
                }
            }
            else
            {
                InputUtils.readString("해당 칸은 지뢰가 아닙니다.");

                // 해당 칸이 지뢰가 아니라면 자동으로 열어준다.
                // 깃발 상태라면 깃발을 해제하고
                if(board.isFlag(playerRow,playerCol))
                {
                    board.toggleFlag(playerRow,playerCol);
                }

                openCell();
            }
        }
        // 찬스의 횟수가 남아 있지 않다면
        else
        {
            InputUtils.readString("찬스를 모두 사용했습니다.");
        }
    }

    // 게임 종료
    private void finish(boolean isWin)
    {
        // 보드판의 모든 셀을 오픈하고 출력해준다.
        board.openAll();
        board.printBoard();

        if(isWin)
        {
            System.out.printf("클리어 시간 : %d초\n", Duration.between(startTime,Instant.now()).getSeconds());
            System.out.println("지뢰를 전부 찾아냈습니다.");
        }
        else
        {
            printer.endMsgPrint(playerRow,playerCol,size);
        }
        endGame();
    }
}
