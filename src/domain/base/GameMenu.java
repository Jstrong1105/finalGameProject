package domain.base;

/**
 * 게임 메뉴
 */
public interface GameMenu<T>
{
    // 메뉴 이름
    String getName();
    // 메뉴 설명
    String getExplain();
    // 메뉴 액션
    void setAction(T menu);
}
