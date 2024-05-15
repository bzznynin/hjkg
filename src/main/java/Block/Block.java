package Block;

import java.awt.*;

public class Block
{
    //坐标
    public int x;
    public int y;
    //宽高
    public int width;
    public int height;
    //质量
    public int m;
    //积分
    public int score;
    //标记，是否能够移动
    public boolean flag;
    //标记，使用药水的区别  1为金块 2为石块
    public int type;
    //图片
    Image image;

    public void paintSelf(Graphics g)
    {
        g.drawImage(image, x, y, null);
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(x, y, width, height);
    }
}
