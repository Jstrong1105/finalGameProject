package domain.minesweeper;

import domain.base.GameApp;
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

    public static GameApp getLauncher()
    {
        return new MinesweeperLauncher(getOption());
    }

    public static void setOption()
    {
        MenuUtils.optionRender(getOption(),MinesweeperOptionMenu.values(),"지뢰찾기 옵션");
    }
}
