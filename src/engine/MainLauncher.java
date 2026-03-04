package engine;

import utility.MenuUtils;

public class MainLauncher
{
    public static void main(String[] args)
    {
        MenuUtils.gameMenu(GameList.values());
    }
}
