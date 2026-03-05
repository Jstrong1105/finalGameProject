package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Supplier;

/**
 * 사용자로부터 입력을 받는 유틸리티
 */
public final class InputUtils
{
    // 생성자 private → 외부에서 인스턴스 생성 금지
    private InputUtils(){}

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // 문자를 입력받는 메소드
    public static String readString(String prompt)
    {
        try
        {
            System.out.print(prompt);
            System.out.flush();
            String str = br.readLine();

            if(str == null)
            {
                return "";
            }

            str = str.trim();

            if(str.equalsIgnoreCase("exit"))
            {
                System.out.println("프로그램을 강제 종료합니다. 강제 종료 시 저장이 되지 않을 수 있습니다.");
                System.exit(0);
                return "";
            }
            else
            {
                return str;
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // 숫자를 입력받는 메소드
    public static int readInt(String prompt)
    {
        while(true)
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
    }

    // 숫자를 범위 내에서 입력받는 메소드
    public static int readInt(String prompt, int min, int max)
    {
        while(true)
        {
            int number = readInt(String.format(prompt + " (%d~%d) : ",min,max));

            if(number < min || number > max)
            {
                System.out.printf("%d ~ %d 사이의 값을 입력하세요.\n",min,max);
            }
            else
            {
                return number;
            }
        }
    }

    // boolean 을 입력받는 메소드
    public static boolean readBoolean(String prompt, String y, String n)
    {
        while(true)
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
    }
}
