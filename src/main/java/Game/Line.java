package Game;



import Block.Block;

import java.awt.*;

public class Line
{
    //线的起点坐标
    int x = 380;
    int y = 180;
    //线的终点坐标
    int endX = 380;
    int endY = 380;
    //线段长度
    double length = 100;
    //最小长度
    final double MIN_LENGTH = 100;
    //最大长度
    final double MAX_LENGTH = 750;
    double n = 0.1;
    //方向参数
    int direction = 1;
    //线的状态：0 摇摆 1 抓取 2 收回 3 抓取返回
    int state = 0;

    GameWin frame;

    public Line(GameWin frame)
    {
        this.frame = frame;
    }

    //抓取判定（碰撞检测），检测物体是否被抓取
    public void logic()
    {
        for (Block block : this.frame.blockList)
        {
            if (endX > block.x && endX < block.x + block.width
                    && endY > block.y && endY < block.y + block.height)
            {
                state = 3;
                block.flag = true;
                //System.out.println(1);
            }
        }

    }

    public void paintSelf(Graphics g)
    {
        logic();
        switch (state)
        {
            case 0:
                if (n <= 0.1)
                    direction = 1;
                else if (n >= 0.9)
                    direction = -1;

                n += 0.005 * direction;
                break;
            case 1:
                if (length < MAX_LENGTH)
                    length += 10;
                else
                    state = 2;
                break;
            case 2:
                if (length > MIN_LENGTH)
                    length -= 10;
                else
                    state = 0;
                break;
            case 3:
                int m = 0;
                if (length > MIN_LENGTH)
                {
                    length -= 10;
                    for (Block block : this.frame.blockList)
                    {
                        if (block.flag)
                        {
                            m = block.m;
                            block.x = endX - block.width / 2;
                            block.y = endY; //- this.frame.gold1.height / 4;
                            if (length <= MIN_LENGTH)
                            {
                                block.x = -100;
                                block.y = -100;
                                block.flag = false;
                                //加分
                                Background.score += block.score;
                                Background.waterFlag = false;
                                state = 0;
                            }
                            if (Background.waterFlag)
                            {
                                if (block.type == 1)
                                {
                                    m = 0;
                                }
                                if (block.type == 2)
                                {
                                    block.x = -100;
                                    block.y = -100;
                                    block.flag = false;
                                    Background.waterFlag = false;
                                    state = 2;
                                }
                            }
                        }
                    }
                }

                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

        endX = (int) (x + length * Math.cos(n * Math.PI));
        endY = (int) (y + length * Math.sin(n * Math.PI));

        g.setColor(Color.red);
        g.drawLine(x - 1, y, endX - 1, endY);
        g.drawLine(x, y, endX, endY);
        g.drawLine(x + 1, y, endX + 1, endY);
        //g.drawImage(hook,endX,endY,null);
        Hook hook = new Hook(endX, endY);
        hook.paintSelf(g);
    }

    public void reGame()
    {
        n = 0.1;
        length = 100;
    }
}
