package domain.minesweeper;

import domain.base.GameOption;
import utility.InputUtils;

import java.util.function.Consumer;

/**
 * 지뢰찾기 옵션 수정 메뉴
 */
enum MinesweeperOptionMenu implements GameOption<MinesweeperOption>
{
    SIZE("크기","보드판의 크기를 결정합니다.",(option)->
    {
        int size = InputUtils.readInt("크기를 입력",option.getMinSize(),option.getMaxSize());
        option.setSize(size);
    }),
    LEVEL("난이도","지뢰의 개수와 찬스의 수를 결정합니다.",(option)->
    {
        int level = InputUtils.readInt("레벨을 입력",option.getMinLevel(),option.getMaxLevel());
        option.setLevel(level);
    }),
    PRINT_TYPE("출력 형식","출력 형식을 결정합니다.",(option)->
    {
        int printType = InputUtils.readInt("출력 형식 1 : 문자 / 2 : 숫자",1,2);
        if(printType == 1)
        {
            option.setPrinter(new MinesweeperPrinter1());
        }
        else
        {
            option.setPrinter(new MinesweeperPrinter2());
        }
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
    public void setOption(MinesweeperOption option)
    {
        setter.accept(option);
    }
}
