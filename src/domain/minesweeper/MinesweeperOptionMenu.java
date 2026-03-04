package domain.minesweeper;

import domain.base.GameMenu;
import utility.InputUtils;
import utility.MenuUtils;

import java.util.function.Consumer;

/**
 * 지뢰찾기 옵션 수정 메뉴
 */
enum MinesweeperOptionMenu implements GameMenu<MinesweeperOption>
{
    SIZE("크기","보드판의 크기를 결정합니다.",(option)->
    {
        int size = InputUtils.readInt("크기를 입력",option.getMinSize(),option.getMaxSize());
        option.setSize(size);
        InputUtils.readString("보드판의 크기가 " + size + "로 변경되었습니다.");
    }),
    LEVEL("난이도","지뢰의 개수와 찬스의 수를 결정합니다.",(option)->
    {
        int level = InputUtils.readInt("레벨을 입력",option.getMinLevel(),option.getMaxLevel());
        option.setLevel(level);
        InputUtils.readString("난이도가 " + level + "로 변경되었습니다.");
    }),
    PRINT_TYPE("출력 형식","출력 형식을 결정합니다.",(option)->
    {
        MenuUtils.showOption(option, MinesweeperPrinterMenu.values(),"프린터");
    })
    ;

    MinesweeperOptionMenu(String name,String explain, Consumer<MinesweeperOption> setter)
    {
        this.name = name;
        this.explain = explain;
        this.setter = setter;
    }

    private final String name;
    private final String explain;
    private final Consumer<MinesweeperOption> setter;

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
    public void setAction(MinesweeperOption menu)
    {
        setter.accept(menu);
    }
}
