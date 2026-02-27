package domain.minesweeper;

/**
 * 지뢰찾기 한칸을 나타내는 객체
 * 패키지 프라이빗
 */
class Cell
{
    private enum CellStatus
    {
        OPEN,FLAGGED,CLOSED
        // 열린 상태
        // 깃발 상태
        // 닫힌 상태
    }

    // 상수
    private static final int MAX_ADJACENT_MINES = 8;

    // 현재 상태
    private CellStatus status;
    // 지뢰 여부
    private boolean isMine;
    // 인접한 지뢰의 개수
    private int adjacentMines;

    // 현재 선택 여부
    private boolean choice;

    // 생성자 → 패키지 프라이빗
    // 지뢰 여부만 받고 나머지는 기본값
    Cell(boolean isMine)
    {
        this.isMine = isMine;
        status = CellStatus.CLOSED;
        adjacentMines = 0;
        choice = false;
    }

    // getter
    boolean isOpen() { return status == CellStatus.OPEN; }
    boolean isFlagged() { return status == CellStatus.FLAGGED; }
    boolean isClosed() { return status == CellStatus.CLOSED; }
    boolean isMine() { return isMine; }
    boolean isChoice() { return choice; }
    int getAdjacentMines() { return adjacentMines; }

    // setter
    void openCell()
    {
        // 닫혀 있는 셀만 열 수 있다
        // 열려있거나 깃발인 칸은 열리지 않는다.
        if(isClosed())
        {
            status = CellStatus.OPEN;
        }
    }

    void flagToggle()
    {
        // 닫혀 있다면 깃발로
        if(isClosed())
        {
            status = CellStatus.FLAGGED;
        }
        // 깃발이라면 닫힌 셀로
        else if(isFlagged())
        {
            status = CellStatus.CLOSED;
        }
    }

    void setMine(boolean isMine)
    {
        this.isMine = isMine;
    }

    void addAdjacentMines()
    {
        if(adjacentMines >= MAX_ADJACENT_MINES)
        {
            throw new IllegalStateException("인접 지뢰 수는 " + MAX_ADJACENT_MINES + "개를 넘을 수 없습니다.");
        }
        adjacentMines++;
    }

    void setChoice(boolean choice)
    {
        this.choice = choice;
    }

    // 특별한 상황에서 실행되는 메소드

    // 게임이 종료되었을때
    // 모든 칸을 열기 위해서 존재하는 메소드
    void openAll()
    {
        status = CellStatus.OPEN;
    }
}
