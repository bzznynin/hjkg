package Game;


import Block.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;



public class GameWin extends JFrame
{
    //游戏状态：0 准备 1 运行中 2 商店 3 失败 4 胜利
    static int state;
    Background background = new Background();
    Line line = new Line(this);
    //Gold gold1 = new Gold();
    //Hook hook = new Hook(line.endX, line.endY);
    //存储金块，石块
    List<Block> blockList = new ArrayList<>();

    {
        for (int i = 0; i < 12; i++)
        {
            double random = Math.random();
            boolean isPlace = true; //判断是否可以放置
            Gold gold; //存放当前生成的金块
            if (random < 0.3)
                gold = new GoldMini();
            else if (random < 0.7)
                gold = new Gold();
            else
                gold = new GoldSuper();

            for (Block block : blockList)
            {
                if (gold.getRectangle().intersects(block.getRectangle()))
                {
                    //重叠，不可放置，需要重新生成
                    isPlace = false;
                    break;
                }
            }
            if (isPlace)
            {
                blockList.add(gold);
            }
            else
            {
                isPlace = true;
                i--;
            }
        }

        for (int i = 0; i < 5; i++)
        {
            boolean isPlace = true; //判断是否可以放置
            Rock rock = new Rock();
            for (Block block : blockList)
            {
                if (rock.getRectangle().intersects(block.getRectangle()))
                {
                    //重叠，不可放置，需要重新生成
                    isPlace = false;
                    break;
                }
            }
            if (isPlace)
            {
                blockList.add(rock);
            }
            else
            {
                isPlace = true;
                i--;
            }
        }
    }


    Image offScreenImage;

    public void launch()
    {
        this.setVisible(true);
        this.setSize(768, 1000);
        this.setLocationRelativeTo(null);
        this.setTitle("The Gold Miner");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state)
                {
                    case 0:

                        if (e.getButton() == 3)
                        {
                            state = 1;
                            background.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        //当点击鼠标左键并且线处于左右摇摆状态时
                        if ((e.getButton() == 1) && (line.state == 0))
                        {
                            //将线的状态设置为抓取状态
                            line.state = 1;
                        }
                        //当点击鼠标右键 并且 线处于抓取返回状态 并且 药水数量大于零 时
                        if ((e.getButton() == 3) && (line.state == 3) && (Background.waterCount > 0))
                        {
                            Background.waterCount --;
                            Background.waterFlag = true;
                        }
                        break;
                    case 2:
                        if (e.getButton() == 1)
                        {
                            background.shop = true;
                        }
                        else if (e.getButton() == 3)
                        {
                            state = 1;
                            background.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 3: //break;
                    case 4:
                        if (e.getButton() == 1)
                        {
                            state = 0;
                            background.reGame();
                            line.reGame();
                        }
                        break;
                }

            }
        });

        while (true)
        {
            repaint();
           nextLevel();
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    //下一关
    public void nextLevel()
    {
        if (background.gameTime() && state == 1) {
            if (Background.score >= background.goal)
            {
                if (Background.level == 5) {
                    state = 4;
                }
                else{
                    state = 2;
                    Background.level++;
                    //background.startTime = System.currentTimeMillis();
                }
                //System.out.println("目标达成，即将进入下一关。。。");

            }
            else
                state = 3;
            dispose();
            GameWin gameWin = new GameWin();
            gameWin.launch();
        }

    }

    public void paint(Graphics g)
    {
        offScreenImage = this.createImage(768,1000);
        Graphics gImage = offScreenImage.getGraphics();

        background.paintSelf(gImage);
        //line.logic();
        if (state == 1)
        {
            //绘制金块与石块
            for (Block block : blockList)
            {
                block.paintSelf(gImage);
            }
            //绘制线
            line.paintSelf(gImage);
            //hook.paintSelf(gImage);
        }

        g.drawImage(offScreenImage,0,0,null);
    }



    public class Main {
        public static void main(String[] args) {
            CopyrightInfo copyrightInfo = CopyrightInfo.getInstance();
            System.out.println("Copyright © " + copyrightInfo.getCopyrightYear());
        }
    }
    public static void main(String[] args)
    {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
