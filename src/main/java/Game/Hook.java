package Game;

import java.awt.*;

public class Hook
{
    //坐标
    int x;
    int y;
    //宽高
    int width;
    int height;
    //图片
    Image hook = Toolkit.getDefaultToolkit().getImage("imgs/hook.png");

    public Hook(int x, int y)
    {
        this.width = 72;
        this.height = 50;
        this.x = x - width / 2;
        this.y = y;
    }

    public void paintSelf(Graphics g)
    {
        g.drawImage(hook, x, y, null);
    }
}

