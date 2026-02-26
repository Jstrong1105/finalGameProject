package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 사용자로부터 입력을 받는 유틸리티
 */
public final class InputUtils
{
    // 생성자 private → 외부에서 인스턴스 생성 금지
    private InputUtils(){}

    // 입력 오류 제한 횟수
    // 해당 횟수 이상 입력 오류가 발생하면 악의적인 입력으로 판단
    private static final int LIMIT = 10;
    private static final String ERROR_MESSAGE = "지정된 횟수를 초과했습니다.";

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 문자를 입력받는 메소드
    public static String readString(String prompt)
    {
        try
        {
            System.out.print(prompt);
            System.out.flush();
            return br.readLine().trim();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // 숫자를 입력받는 메소드
    public static int readInt(String prompt)
    {
        int limit = LIMIT;

        while (limit-- > 0)
        {
            try
            {
                return Integer.parseInt(readString(prompt));
            }

            catch (NumberFormatException e)
            {
                System.out.println("숫자만 입력하세요.");
            }
        }

        throw new RuntimeException(ERROR_MESSAGE);
    }

    // 숫자를 범위 내에서 입력받는 메소드
    public static int readInt(String prompt, int min, int max)
    {
        int limit = LIMIT;

        while (limit-- > 0)
        {
            try
            {
               int num = Integer.parseInt(readString(String.format("%s (%d~%d) : ",prompt,min,max)));

                if(num < min || num > max)
                {
                    System.out.printf("%d ~ %d 사이로 입력하세요.\n",min,max);
                }
                else
                {
                    return num;
                }
            }

            catch (NumberFormatException e)
            {
                System.out.println("숫자만 입력하세요.");
            }
        }

        throw new RuntimeException(ERROR_MESSAGE);
    }

    // boolean 을 입력받는 메소드
    public static boolean readBoolean(String prompt, String y, String n)
    {
        int limit = LIMIT;

        while(limit-- > 0)
        {
            String answer = readString(String.format("%s (%s/%s) : ",prompt,y,n));

            if(answer.equalsIgnoreCase(y))
            {
                return true;
            }
            else if(answer.equalsIgnoreCase(n))
            {
                return false;
            }
            else
            {
                System.out.println("잘못된 입력입니다.");
            }
        }

        throw new RuntimeException(ERROR_MESSAGE);
    }
}
