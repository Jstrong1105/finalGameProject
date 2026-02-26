package engine;

import domain.base.GameApp;

import java.util.function.Supplier;

/**
 * 모든 게임 목록을 가지고 있는 enum
 */
public enum GameList
{

    ;
    GameList(String gameName,String gameExplain, Supplier<GameApp> gameAppSupplier, Runnable optionSetter)
    {
        this.gameName = gameName;
        this.gameExplain = gameExplain;
        this.gameAppSupplier = gameAppSupplier;
        this.optionSetter = optionSetter;
    }
    private final String gameName;                      // 게임 이름
    private final String gameExplain;                   // 게임 설명
    private final Supplier<GameApp> gameAppSupplier;    // 게임 공급
    private final Runnable optionSetter;                // 옵션 설정

    public String getGameName() { return gameName; }
    public String getGameExplain() { return gameExplain; }
    public GameApp getGame()
    {
        return gameAppSupplier.get();
    }
    public void setOption()
    {
        optionSetter.run();
    }
}
