package utility;

/**
 * Thread를 컨트롤하는 유틸리티
 */
public final class ThreadUtils
{
    // 생성자 private → 외부에서 인스턴스 생성 금지
    private ThreadUtils(){}

    // 입력받은 초만큼 프로그램을 정지시키는 메소드
    public static void sleep(long second)
    {
        if(second < 0)
        {
            throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
        }
        try
        {
            Thread.sleep(second * 1000L);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    // 입력받은 초만큼 프로그램을 정지시키면서 남은 시간을 출력하는 메소드
    public static void sleepCountDown(long second)
    {
        if(second < 0)
        {
            throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
        }
        while(second > 0)
        {
            System.out.printf("\r%2d초",second);
            System.out.flush();

            sleep(1);

            second--;
        }
        System.out.println();
    }
}
