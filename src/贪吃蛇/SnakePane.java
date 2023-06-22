package 贪吃蛇;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class SnakePane extends JPanel implements KeyListener, ActionListener {
	
	private static final long serialVersionUID = 1L;
//标题栏
ImageIcon title =new ImageIcon("statics/headerdwj.png");
//蛇头
//上
ImageIcon up =new ImageIcon("statics/up.png");
//下
ImageIcon down =new ImageIcon("statics/down.png");
//左
ImageIcon left =new ImageIcon("statics/left.png");
//右
ImageIcon right =new ImageIcon("statics/right.png");
 
//蛇身
ImageIcon body =new ImageIcon("statics/body.png");
//食物
ImageIcon food =new ImageIcon("statics/food.png");
//分数
int score; 
//蛇的长度
int length;
int[] snakex = new int[700];//蛇的横坐标
int[] snakey = new int[700];//蛇的纵坐标
//蛇头方向
String direction;
int speed = 1; //蛇的速度
int modern = 0; //游戏模式：0为撞墙失败，1为可穿墙
 
boolean isStart; //游戏启动状态
boolean isFail;  //游戏是否失败
//创建timer对象，设置刷新时间100毫秒
Timer timer = new Timer(180,this);
 
//定义食物的坐标
Random r = new Random();
int foodx = r.nextInt(34)*25 + 25;
int foody = r.nextInt(23)*25 + 75;
//Random.nextInt()随机产生一个数，如果有参数就是在0到参数之间产生。
 
public SnakePane(){
//让当前的Jpanel能获取焦点，这样在Jpanel的键盘事件才能起作用
init();
this.setFocusable(true);
this.addKeyListener(this);//添加键盘的监听
//刷新开始，实现动画
timer.start();
//创建选项栏
JMenuBar menuBar = new JMenuBar();
//添加标题，创建按键
JButton jb1 = new JButton("退出游戏");
JButton jb3 = new JButton("简单");
JButton jb4 = new JButton("困难");
JButton jb5 = new JButton("地狱");
JButton jb6 = new JButton("穿墙模式");
JButton jb7 = new JButton("撞墙模式");
//添加按键
menuBar.add(jb1);
menuBar.add(jb3);
menuBar.add(jb4);
menuBar.add(jb5);
menuBar.add(jb6);
menuBar.add(jb7);
jb1.addActionListener(e -> {//添加jb1的监听
      System.exit(0);//退出
 });
      jb3.addActionListener(e -> {
this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
speed = 0 ;
timer.stop();//把原来的timer停止
timer = new Timer(180, this);//使蛇的速度变慢
timer.start();//重新开始timer
});
jb4.addActionListener(e -> {
this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
speed = 2 ;
timer.stop();//把原来的timer停止
timer = new Timer(110, this);//使蛇的速度变快一点实现难度提升
      timer.start();//重新开始timer
});
jb5.addActionListener(e -> {
this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
speed = 3 ;
timer.stop();//把原来的timer停止
timer = new Timer(40, this);//使蛇的速度变快很多实现难度提升
      timer.start();//重新开始timer
});
jb6.addActionListener(e -> {
this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
      modern = 1;//游戏模式：0为撞墙失败
 });
jb7.addActionListener(e -> {
     this.requestFocus();//不获取焦点后面操作键盘都会在菜单栏里,就无法操作蛇了
     modern = 0;//游戏模式：1为可穿墙
});
//添加选项栏
this.add(menuBar);
JButton jb2 =  new JButton("重新开始");
jb2.addActionListener(e -> {
init();//重新开始所以重新初始化一下
repaint();//重画
this.requestFocus();
});
  this.add(jb2);
}
//初始化一些值
public void init(){
score = 0;
length = 3;
direction = "R"; //默认蛇头向右

//蛇头坐标
snakex[0] = 100;
snakey[0] = 100;
//第一个蛇身坐标
snakex[1] = 75;
snakey[1] = 100;
//第二个蛇身坐标
snakex[2] = 50;
snakey[2] = 100;
isStart = false; //默认游戏未启动
 isFail = false; //默认游戏未失败
 }
//获取食物坐标（排除碰巧生成在小蛇身体上的食物坐标）
public int[] Foodxy(int foodxx,int foodyy){
for (int i = 0; i <length ; i++) {
    if (foodxx == snakex[i] && foodyy == snakey[i]) { //如果食物坐标与小蛇的坐标重合，则重新取随机值
         foodxx = 25 + 25 * r.nextInt(34);
         foodyy = 75 + 25 * r.nextInt(23);
         Foodxy(foodxx, foodyy);
         }
   }
   return new int[]{foodxx,foodyy};
}
@Override
//创建画笔对象Graphics
protected void paintComponent(Graphics g) {
//调用swing类中方法，进行界面重绘，保留界面中的原本组件
super.paintComponent(g);
title.paintIcon(this,g,25,21);  //画标题
//画笔变白色
g.setColor(Color.white);
g.fillRect(25,75,850,575);  //画一个白色的背景
//画笔变黑色
g.setColor(Color.black);
//第一步、画表格
//1.画横线 总共24条线 23行格子
for (int i =0 ; i < 24 ; i++){
     g.drawLine(25,75+i*25,875,75+i*25);
    }
//2.画竖线 总共35条线 34行格子
for (int i = 0; i < 35; i++){
    g.drawLine(25 + i*25 , 75 , 25 + i *25 , 650);
   }
if(modern == 0){     //撞墙模式下的提示红线
    g.setColor(Color.RED);
    g.drawLine(25 , 75 , 25 , 650);
    g.drawLine(26 , 75 , 26 , 650);
    g.drawLine(27 , 75 , 27 , 650);
    g.drawLine(28 , 75 , 28 , 650);
 
    g.drawLine(872 , 75 , 872 , 650);
    g.drawLine(873 , 75 , 873 , 650);
    g.drawLine(874 , 75 , 874 , 650);
    g.drawLine(875 , 75 , 875 , 650);
 
    g.drawLine(25 , 75 , 875 , 75);
    g.drawLine(25 , 76 , 875 , 76);
    g.drawLine(25 , 77 , 875 , 77);
    g.drawLine(25 , 78 , 875 , 78);
 
    g.drawLine(25 , 647 , 875 , 647);
    g.drawLine(25 , 648 , 875 , 648);
    g.drawLine(25 , 649 , 875 , 649);
    g.drawLine(25 , 650 , 875 , 650);
    }else if (modern == 1){      //穿墙模式下的灰线
    g.setColor(Color.lightGray);
    g.drawLine(25 , 75 , 25 , 650);
    g.drawLine(875 , 75 , 875 , 650);
    g.drawLine(25 , 75 , 875 , 75);
    g.drawLine(25 , 650 , 875 , 650);
    }
     //第二步、画分数
     g.setColor(Color.white);
     g.setFont(new Font("微软雅黑",Font.BOLD,18));
     g.drawString("分数："+ score,750,43);
     g.drawString("长度："+ length,750,68);
     //第三步、画蛇
     //1.画蛇头
     if("R".equals(direction)){
        right.paintIcon(this , g , snakex[0] , snakey[0]);
     }else if ("L".equals(direction)){
        left.paintIcon(this , g , snakex[0] , snakey[0]);
     }else if("U".equals(direction)){
        up.paintIcon(this , g , snakex[0] , snakey[0]);
     }else if("D".equals(direction)){
        down.paintIcon(this , g , snakex[0] , snakey[0]);
     }
     //2.画蛇身
     for(int i = 1; i < length; i++){
        body.paintIcon(this , g , snakex[i] , snakey[i]);
     }
 
     //第四步、画食物
     food.paintIcon(this , g , foodx , foody);
     //当游戏还没有启动时
     if (isStart == false){
            g.setColor(new Color(169,166,171));
            g.setFont(new Font("微软雅黑", Font.BOLD , 36));
            g.drawString("请按空格键开始/暂停",275,300);
            g.setFont(new Font("微软雅黑", Font.BOLD , 32));
            g.drawString("(默认为正常难度，撞墙模式)",238,350);
            g.setColor(new Color(232,212,119));
            g.setFont(new Font("微软雅黑", Font.BOLD , 22));
            g.drawString("按下J键可以冲刺喔~",338,630);
     }
     if(isFail == true){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD , 36));
            g.drawString("游戏失败!请按空格键重新开始" , 225 , 300);
        }
    }
@Override
public void keyPressed(KeyEvent e) {    //
 int keyCode = e.getKeyCode();         //赋值
//空格键控制游戏开始暂停
 if (keyCode == KeyEvent.VK_SPACE) {
     if (isFail) {   //游戏失败，重新开始
        isFail = false;
        init();    //初始化
     } else {
        isStart = !isStart;
     }
     repaint();     //刷新界面
}
//方向键控制蛇的走向
if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
    if (!direction.equals("D")){
          direction = "U";
     }
 } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
     if (!direction.equals("U")) {
         direction = "D";
    }
} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
     if (!direction.equals("R")) {
           direction = "L";
     }
} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
     if (!direction.equals("L")) {
           direction = "R";
     }
}
 
//按J可加速
if (keyCode == KeyEvent.VK_J){
       timer.stop();
       timer = new Timer(20, this);
       timer.start();
    }
}
@Override
public void actionPerformed(ActionEvent e) {
  if (isStart && isFail == false){   //游戏是启动状态，并且没有失败时
     //1.移动蛇身，后边的蛇身坐标取前一个蛇身的坐标
     for (int i = length-1 ; i > 0 ; i--){
         snakex[i] = snakex[i-1];   //后边蛇身的坐标取自于前一个蛇身的坐标
         snakey[i] = snakey[i-1];
        }
      //2.移动蛇头，根据蛇头的方向决定下一个蛇头的位置
     if (direction.equals("U")){
          snakey[0] = snakey[0] - 25; //向上移动一格
     if (snakey[0] < 75){
         if (modern == 1){
             snakey[0] = 625;    //当运动到最上面时，从最下面出来
         }else{
             isFail = true;
              }
     }
     }else if (direction.equals("D")){
           snakey[0] = snakey[0] + 25; //向下移动一格
           if(snakey[0] > 625){
               if (modern == 1){
                   snakey[0] = 75;     //当运动到最下面时，从最上面出来
               }else{
                   isFail = true;
               }
      }
     }else if (direction.equals("L")){
           snakex[0] = snakex[0] - 25; //向左移动一格
           if(snakex[0] < 25){
              if (modern == 1){
                  snakex[0] = 850;    //当运动到最左边时，从最右边出来
              }else{
                  isFail = true;
              }
   }
   }else if (direction.equals("R")){
         snakex[0] = snakex[0] + 25; //向右移动一格
         if(snakex[0] > 850){
            if (modern == 1){
              snakex[0] = 25;     //当运动到最右边时，从最左边出来
            }else{
               isFail = true;
            }
         }
  }
  //3-1.吃食物，当蛇头和食物的坐标重叠时，则代表吃到了食物
  if(snakex[0] == foodx && snakey[0]== foody){
try {
         Music foodMusic = new Music("music/eat.wav");
         foodMusic.start();
         Thread.sleep(100);
         foodMusic.stopPlaying();
         }catch(InterruptedException a) {
                 a.printStackTrace();
          }
         length++;
         score += 10;
         int foodxx = 25 + r.nextInt(34) * 25;
         int foodyy = 75 + r.nextInt(23) * 25;
         int[] test = Foodxy(foodxx,foodyy);
         foodx = test[0];
         foody = test[1];
     }
  //4.判断游戏是否失败（蛇头与任何一个蛇身重叠，则失败）
for (int i = 1; i < length ; i++){
       if (snakex[0] == snakex[i] && snakey[0] == snakey[i]){
            isFail = true;
        }
     }
     repaint();
   }
timer.start();
}
@Override
public void keyReleased(KeyEvent e) {
   int keyCode = e.getKeyCode();         //赋值
   if (keyCode == KeyEvent.VK_J){
       timer.stop();
       if (speed == 0){
          timer = new Timer(120, this);
       }else if (speed == 1){
          timer = new Timer(100, this);
       }else if (speed == 2){
          timer = new Timer(60, this);
       }else if (speed == 3){
          timer = new Timer(40, this);
       }
           timer.start();
        }
    }

@Override
public void keyTyped(KeyEvent e) {
	
}
 

}
