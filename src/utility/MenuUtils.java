package utility;

import domain.base.GameMenu;
import engine.GameList;

/**
 * 메뉴를 출력하는 유틸리티
 */
public final class MenuUtils
{
    private MenuUtils(){}

    public static <T,E extends Enum<E> & GameMenu<T>> void showOption(T option, E[] list, String title)
    {
        int cancel = list.length + 1;

        System.out.println("==== " + title + " ====");

        for (E menu : list)
        {
            System.out.println(menu.ordinal()+1 + ". " + menu.getName() + " : " + menu.getExplain());
        }

        System.out.println(cancel + ". 취소");

        int answer = InputUtils.readInt("번호를 선택",1,list.length+1);

        if(answer == cancel)
        {
            return;
        }
        else
        {
            list[answer-1].setAction(option);
        }
    }

    public static void gameMenu(GameList[] list)
    {
        int option = list.length + 1;
        int exit = option + 1;

        while(true)
        {
            ConsoleUtils.clear();
            System.out.println("===== 게임 런처 =====");
            for (GameList game : list)
            {
                System.out.println(game.ordinal()+1 + ". " + game.getGameName() + " : " + game.getGameExplain());
            }
            System.out.println(option + ". 옵션 변경");
            System.out.println(exit + ". 종료");

            int answer = InputUtils.readInt("번호를 선택", 1, exit);

            if (answer == exit)
            {
                break;
            }

            else if(answer == option)
            {
                optionMenu(list);
            }

            else
            {
                list[answer-1].play();
            }
        }
    }

    private static void optionMenu(GameList[] list)
    {
        int cancel = list.length + 1;

        while(true)
        {
            ConsoleUtils.clear();
            System.out.println("===== 옵션 메뉴 =====");

            for (GameList game : list)
            {
                System.out.println(game.ordinal()+1 + ". " + game.getGameName());
            }

            System.out.println(cancel + ". 뒤로 가기");

            int answer = InputUtils.readInt("번호를 선택", 1, cancel);

            if (answer == cancel)
            {
                return;
            }
            else
            {
                list[answer-1].setOption();
            }
        }
    }
}
