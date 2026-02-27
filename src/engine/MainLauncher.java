package engine;

import domain.base.GameApp;
import domain.minesweeper.MinesweeperGetter;
import utility.InputUtils;


public class MainLauncher
{
    public static void main(String[] args)
    {
        while(true)
        {
            System.out.println("1. 실행");
            System.out.println("2. 옵션");
            System.out.println("3. 종료");

            int answer = InputUtils.readInt("번호 ", 1, 3);

            if (answer == 1)
            {
                GameApp app = MinesweeperGetter.getLauncher();

                app.run();
            }
            else if(answer == 2)
            {
                MinesweeperGetter.setOption();
            }
            else
            {
                break;
            }
        }
    }
}
