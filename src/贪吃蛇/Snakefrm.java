package 贪吃蛇;

import javax.swing.*;
public class Snakefrm extends JFrame {

	private static final long serialVersionUID = 1L;


//继承顶层框架JFrame类
public Snakefrm(){
//在主窗口添加游戏主体，实例化SnakePanel对象，自动调用构造方法
    add(new SnakePane());
    //给主窗口添加标题
    setTitle("贪吃蛇");
    //设置床体大小
    setSize(900,710);
    //关闭按钮
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
   this.setLocationRelativeTo(null);
    //使窗体大小不能改变
    setResizable(false);
    //使窗体可见
    setVisible(true);
    }
 
 
public static void main(String[] args) {
	new Login();//登录界面
    }
}