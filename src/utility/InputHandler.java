package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 사용자로부터 입력을 받는 유틸리티
 */
public class InputHandler
{

    private InputHandler(){}

    private static final int LIMIT = 10;

    // 문자를 입력받는 메소드
    public String readString(String prompt)
    {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
        {
            System.out.print(prompt);
            return br.readLine().trim();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e.toString());
        }
    }

    // 숫자를 입력받는 메소드
    public int readInt(String prompt)
    {
        int limit = LIMIT;

        while (true)
        {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
            {
                limit--;
                System.out.print(prompt);
                return Integer.parseInt(br.readLine().trim());
            }
            catch (IOException e)
            {
                throw new RuntimeException(e.toString());
            }
            catch (NumberFormatException e)
            {
                System.out.println("숫자만 입력하세요.");
            }

            if(limit <= 0)
            {
                throw new RuntimeException("지정된 횟수를 초과했습니다.");
            }
        }
    }

    // 숫자를 범위 내에서 입력받는 메소드
    public int readInt(String prompt, int min, int max)
    {
        int limit = LIMIT;

        while (true)
        {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
            {
                limit--;
                System.out.print(prompt);
                int num =  Integer.parseInt(br.readLine().trim());

                if(num < min || min > max)
                {
                    System.out.println(min + " ~ " + max + " 사이로 입력하세요.");
                }
                else
                {
                    return num;
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e.toString());
            }
            catch (NumberFormatException e)
            {
                System.out.println("숫자만 입력하세요.");
            }

            if(limit <= 0)
            {
                throw new RuntimeException("지정된 횟수를 초과했습니다.");
            }
        }
    }

    // boolean 을 입력받는 메소드
    public boolean readBoolean(String prompt, String y, String n)
    {
        int limit = LIMIT;

        while(true)
        {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
            {
                limit--;

                System.out.print(prompt);

                String answer = br.readLine().trim();

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
                    System.out.println("잘못된 입력입니다..");
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e.toString());
            }

            if(limit <= 0)
            {
                throw new RuntimeException("지정된 횟수를 초과했습니다.");
            }
        }
    }
}
