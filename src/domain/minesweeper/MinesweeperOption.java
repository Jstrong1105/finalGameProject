package domain.minesweeper;

/**
 * 지뢰찾기 옵션
 */
class MinesweeperOption
{
    // 크기
    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 20;
    private int size = MIN_SIZE;

    int getMinSize() { return MIN_SIZE; }
    int getMaxSize() { return MAX_SIZE; }
    int getSize() { return size; }

    // 난이도
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 3;
    private int level = MIN_LEVEL;

    int getMinLevel() { return MIN_LEVEL; }
    int getMaxLevel() { return MAX_LEVEL; }
    int getLevel() { return level; }

    // 출력 타입
    private MinesweeperPrinter printer = new MinesweeperPrinter1();

    MinesweeperPrinter getPrinter() { return printer; }

    void setSize(int size)
    {
        if(size < MIN_SIZE || size > MAX_SIZE)
        {
            throw new IllegalArgumentException("허용하지 않는 사이즈입니다.");
        }
        this.size = size;
    }

    void setLevel(int level)
    {
        if(level < MIN_LEVEL || level > MAX_LEVEL)
        {
            throw new IllegalArgumentException("허용하지 않는 레벨입니다.");
        }
        this.level = level;
    }

    void setPrinter(MinesweeperPrinter printer)
    {
        this.printer = printer;
    }
}
