package utility;

/**
 * 콘솔창을 컨트롤하는 메소드를 가지고 있는 유틸리티
 */
public final class ConsoleUtils
{
    // 생성자 private → 외부에서 인스턴스 생성 금지
    private ConsoleUtils() {}

    // 콘솔창을 지우는 메소드
    public static void clear()
    {
        // \033[H  : 커서를 이동시킴
        // \033[2J : 화면에 보이는 문자를 지움
        // \033[3J : 스크롤을 상단으로 올렸을때 보이는 문자를 지움
        // flush() : 명령이 버퍼에 걸려서 출력되지 않을 경우를 대비 / 없어도 실행 되기도 함
        System.out.print("\033[2J\033[H\033[3J");
        System.out.flush();
    }
}
