package domain.minesweeper;

import utility.MenuUtils;

/**
 * 외부와 연결된 클래스
 */
public class MinesweeperGetter
{
    private static MinesweeperOption option;

    private static MinesweeperOption getOption()
    {
        if(option == null)
        {
            option = new MinesweeperOption();
        }

        return option;
    }

    public static void play()
    {
        new MinesweeperLauncher(getOption()).run();
    }

    public static void setOption()
    {
        MenuUtils.showOption(getOption(),MinesweeperOptionMenu.values(),"지뢰찾기 옵션");
    }
}
