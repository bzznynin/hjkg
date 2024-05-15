package Game;

import java.awt.*;

public class Background
{
    //总分
    static int score = 0;
    //关卡数
    static int level = 1;
    //目标得分
    public int goal = level * 25;
    //药水数量
    static int waterCount = 3;
    //药水状态，默认false，true表示正在使用
    static boolean waterFlag = false;
    //开始时间
    public long startTime;
    //结束时间
    public long endTime;
    //药水价格
    public int price = (int) (Math.random() * 10);
    //是否购买
    public boolean shop = false;

    //载入图片
    Image background = Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
    Image sky = Toolkit.getDefaultToolkit().getImage("imgs/bg1.jpg");
    Image peo = Toolkit.getDefaultToolkit().getImage("imgs/peo.png");
    Image water = Toolkit.getDefaultToolkit().getImage("imgs/water.png");

    //绘制各个
    public void paintSelf(Graphics g)
    {
        g.drawImage(background,0,200,null);
        g.drawImage(sky,0,0,null);

        switch (GameWin.state)
        {
            case 0:
                drawWord(g, 60, Color.green, "按下鼠标右键以开始",100, 400);
                break;
            case 1:
                g.drawImage(peo,310, 50,null);

                drawWord(g, 30, Color.black, "积分：" + score, 30, 150);

                //药水
                g.drawImage(water, 450, 40, null);
                drawWord(g, 30, Color.black, "x" + waterCount, 510, 70);

                //关卡数
                drawWord(g, 20, Color.red, "第" + level + "关", 30, 60);
                //目标积分
                drawWord(g, 30, Color.red, "目标：" + goal, 30, 110);

                //时间
                endTime = System.currentTimeMillis();
                long time = 20 - (endTime - startTime) / 1000;
                drawWord(g, 30, Color.orange, "时间：" + (time > 0 ? time : 0), 520, 150);
                break;
            case 2:
                g.drawImage(water, 300, 400, null);
                drawWord(g, 30, Color.black, "价格：" + price, 300, 500);
                drawWord(g, 30, Color.black, "是否购买？", 300, 550);
                if (shop)
                {
                    score -= price;
                    waterCount++;
                    shop = false;
                    GameWin.state = 1;
                    startTime = System.currentTimeMillis();
                }
                break;
            case 3:
                drawWord(g, 80, Color.cyan, "失败", 280, 350);
                drawWord(g, 80, Color.cyan, "积分：" + score, 200, 450);
                drawWord(g, 80, Color.cyan, "按下鼠标左键重置" + score, 120, 550);
                break;
            case 4:
                drawWord(g, 80, Color.red, "恭喜通关！", 200, 350);
                drawWord(g, 80, Color.red, "积分：" + score, 200, 450);
                drawWord(g, 80, Color.cyan, "按下鼠标左键重置" + score, 120, 550);
                break;
        }

    }

    //true表示倒计时完成，false表示正在倒计时
    public boolean gameTime()
    {
        long time = (endTime - startTime) / 1000;
        if (time > 20) return true;
        return false;
    }

    //重置元素
    public void reGame()
    {
        //总分
        score = 0;
        //关卡数
        level = 1;
        //目标得分
        goal = level * 25;
        //药水数量
        waterCount = 3;
        //药水状态，默认false，true表示正在使用
        waterFlag = false;
    }

    //绘制字符串
    public static void drawWord(Graphics g, int size, Color color, String str, int x, int y)
    {
        g.setColor(color);
        g.setFont(new Font("JetBrains Mono", Font.BOLD, size));
        g.drawString(str, x, y);
    }
}
