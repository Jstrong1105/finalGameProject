package domain.minesweeper;

import domain.base.GameMenu;
import utility.InputUtils;

import java.util.function.Consumer;

/**
 * 지뢰찾기 프린터 메뉴
 */
enum MinesweeperPrinterMenu implements GameMenu<MinesweeperOption>
{
    PRINTER1("문자 출력","셀을 모양으로 출력합니다.",(option)->
    {
        option.setPrinter(new MinesweeperPrinter1());
        InputUtils.readString("프린터가 문자 타입으로 변경되었습니다.");
    }),
    PRINTER2("숫자 출력","셀을 숫자로 출력합니다.",(option)->
    {
        option.setPrinter(new MinesweeperPrinter2());
        InputUtils.readString("프린터가 숫자 타입으로 변경되었습니다.");
    })
    ;

    MinesweeperPrinterMenu(String name,String explain, Consumer<MinesweeperOption> setter)
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
