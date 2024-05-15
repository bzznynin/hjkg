package Block;

import java.awt.*;

public class Gold extends Block
{
    public Gold()
    {
        this.x = (int) (Math.random() * 550 + 100);
        this.y = (int) (Math.random() * 550 + 300);
        this.width = 71;
        this.height = 71;
        this.m = 30;
        this.score = 7;
        this.flag = false;
        this.type = 1;
        this.image = Toolkit.getDefaultToolkit().getImage("imgs/gold1.gif");
    }
}

