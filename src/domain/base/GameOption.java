package domain.base;

/**
 * 게임 옵션 수정 템플릿
 */
public interface GameOption<T>
{
    String getName();
    String getExplain();
    void setOption(T option);
}
