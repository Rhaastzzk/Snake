package 贪吃蛇;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;


public class Login extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	JTextField t1;
	JPasswordField pass;
	JButton b1,b2;
	static Connection conn = null;
	static Statement stmt;
	static ResultSet rs;
	static String sql;
	Login(){
		setTitle("玩家登录界面");
		t1 = new JTextField(12);//文本框
		pass = new JPasswordField(12);//密码框
		JLabel l1 = new JLabel("账号："); JLabel l2 = new JLabel("密码："); //标签对象
		setLayout(new GridLayout(3,1));
		JPanel p1 = new JPanel(); 
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		p1.add(l1); p2.add(l2);
		p1.add(t1); p2.add(pass);
		add(p1); add(p2);
		b1 = new JButton("登录"); b2 = new JButton("注册");
		b1.addActionListener(this);
		b2.addActionListener(this);
		p3.add(b1);p3.add(b2);add(p3);
		setSize(400,150);
		setLocationRelativeTo(null);
		setResizable(false); 
		setVisible(true);
	}
	
	//数据库连接操作方法
		public static void OpenConn() throws Exception{ 
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost:3306/stu?useSSL=false&useUncode=true&characterEncoding=utf8";
				String username = "root"; String password = "123456";//密码是你安装mysql时的密码
				conn = DriverManager.getConnection(url,username,password);
				if(conn != null) System.out.println("数据库连接成功");
			}catch(Exception e) {
				System.err.println("数据库连接："+e.getMessage()+"\n");
			}
		}
		
		//信息比对 登录时使用
		public static boolean login(String lname,String lpassword) throws SQLException {
        	stmt = conn.createStatement();
        	sql = "select password from user where id="+"'"+lname+"'";
        	rs = stmt.executeQuery(sql);
        	while(rs.next()) {
        		if(rs.getString("password").equals(lpassword)) {
        			System.out.println("密码正确！");
        			return true;
        		}else {
        			System.out.println("密码错误！");
        			return false;
        		}	
        	}
        return false;
 }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String name = this.t1.getText();
		@SuppressWarnings("deprecation")
		String passw = this.pass.getText();
		if(obj==b1) {
			
			boolean t = false;
			try {
				Login.OpenConn();
				t = Login.login(name, passw);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(t == true) 
				{
				this.dispose();
					System.out.println("登录成功！");
					//实例化Snakefrm对象，自动调用Snakefrm构造方法
				     new Snakefrm();
				 
				     //背景音乐启动
				     Music audioPlayWave = new Music("music/bgm.wav");
				     // 开音乐 
				     audioPlayWave.start();
				}
			else {
				this.dispose();
				System.out.println("登录失败！");
			}
		}else if(obj==b2) {
			try {
				Login.OpenConn();//连接数据库
				String sql = "insert into user(id,password) values('"+name+"','"+passw+"')";
				Statement st=Login.conn.createStatement();
				st.executeUpdate(sql);
				st.close();
				Login.conn.close();
			}catch(Exception e1) {
				System.err.println(e1.getMessage());
			}
			System.out.println("注册成功！");
			this.t1.setText("");
			this.pass.setText("");
		}
		
	}

}
