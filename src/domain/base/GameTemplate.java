package domain.base;

import utility.InputUtils;

/**
 * 일부 게임이 공통적으로 공유하는 부분을 구현하고
 * 게임이 구현해야하는 메소드를 강제하는 추상 클래스
 */
public abstract class GameTemplate implements GameApp
{
    // 진행 여부 컨트롤
    private boolean isRunning = false;

    @Override
    public void run()
    {
        do
        {
            isRunning = true;
            initialize();

            // update 에서 내부적으로 게임이 종료되었는지 판단해서
            // endGame을 호출한다.
            while(isRunning)
            {
                render();
                inputHandle();
                update();
            }
        }
        while(restart());
    }

    // 다시 시작하는지 여부를 묻는 메소드
    // 필요하다면 하위에서 재정의 가능하다.
    protected boolean restart()
    {
        return InputUtils.readBoolean("다시 시작하시겠습니까?","y","n");
    }
    
    // 게임이 종료될때 하위 클래스에서 실행할 메소드
    protected void endGame()
    {
        isRunning = false;
    }

    /**
     * 게임이 처음 시작될때 또는 다시 시작할때 한번 실행할 메소드
     */
    protected abstract void initialize();

    /**
     * 사용자에게 화면을 출력하는 메소드
     */
    protected abstract void render();

    /**
     * 사용자에게 입력을 받는 메소드
     */
    protected abstract void inputHandle();

    /**
     * 사용자에게 받은 입력으로 게임의 상태를 갱신하는 메소드
     */
    protected abstract void update();
}
