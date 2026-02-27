package utility;

import domain.base.GameOption;

/**
 * 메뉴를 출력하는 유틸리티
 */
public final class MenuUtils
{
    private MenuUtils(){}

    public static <T,E extends Enum<E> & GameOption<T>> void optionRender(T option,E[] list,String title)
    {
        int cancel = list.length + 1;

        System.out.println("==== " + title + " ====");

        for (E menu : list)
        {
            System.out.println(menu.ordinal()+1 + ". " + menu.getName() + " " + menu.getExplain());
        }

        System.out.println(cancel + ". 취소");

        int answer = InputUtils.readInt("번호를 선택",1,list.length+1);

        if(answer == cancel)
        {
            return;
        }
        else
        {
            list[answer-1].setOption(option);
        }
    }
}
