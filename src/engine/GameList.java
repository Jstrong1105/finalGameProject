package engine;

import domain.minesweeper.MinesweeperGetter;

/**
 * 모든 게임 목록을 가지고 있는 enum
 */
public enum GameList
{
    MINESWEEPER("지뢰찾기","지뢰를 피하세요.", MinesweeperGetter::play,MinesweeperGetter::setOption)
    ;

    private final String gameName;                      // 게임 이름
    private final String gameExplain;                   // 게임 설명
    private final Runnable gameStarter;    // 게임 공급
    private final Runnable optionSetter;                // 옵션 설정

    GameList(String gameName,String gameExplain, Runnable gameStarter, Runnable optionSetter)
    {
        this.gameName = gameName;
        this.gameExplain = gameExplain;
        this.gameStarter = gameStarter;
        this.optionSetter = optionSetter;
    }

    public String getGameName() { return gameName; }
    public String getGameExplain() { return gameExplain; }
    public void play()
    {
        gameStarter.run();
    }
    public void setOption()
    {
        optionSetter.run();
    }
}
