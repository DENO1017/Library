package library;
//author:苏曼
//date：2018/3/23
//文本文件编码：UTF-8

import java.sql.*;
import java.util.*;
import java.io.*;
import java.math.BigDecimal;


public class library {
	//数据库library,用户root,密码720708
	private static String url = "jdbc:mysql://localhost:3306/library";
	private static String user = "root";
	private static String pass = "720708";
	private static Connection con;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		try
		{
			//加载mysql-jdbc驱动程序
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException ce)
		{
			System.out.println("SQLException:"+ce.getMessage());
		}
		try
		{
			con=DriverManager.getConnection(url,user,pass);//建立连接
			adminLogin();//管理员登录
			//登录成功，选择功能
			System.out.println("请选择:\n1:图书入库\n2:图书查询\n3:借书\n4:还书\n5:退出");
			int i = input.nextInt();
			while(i!=5)
			{	
				switch(i)
				{
					case 1:
						bookInput();//图书入库
						break;
					case 2:
						bookInquiry();//图书查询
						break;
					case 3:
						bookBorrow();//借书
						break;
					case 4:
						bookReturn();//还书
						break;
				}
				System.out.println("请选择:\n1:图书入库\n2:图书查询\n3:借书\n4:还书\n5:退出");
				i = input.nextInt();
			}
			con.close();//关闭连接
		}
		catch(SQLException e)
		{
			System.out.println("SQLException:"+e.getMessage());
		}
	}
	//管理员登录
	public static void adminLogin() throws SQLException{
		String ano;
		char[] password;
		start app = new start();
		ano=app.id;
		password=app.pass;
		
		input.next();/*
		try
		{
			//在admin表中验证管理员ID密码
			String sqlstr="select ano,password from admin "+
			"where ano = ? and password = ?";
			PreparedStatement ptmt=con.prepareStatement(sqlstr);
			ptmt.setString(1,ano);
			ptmt.setString(2,password);
			ResultSet rs = ptmt.executeQuery();
			//登录成功，关闭Statement和ResultSet对象，退出函数
			if(rs.next())
			{
				System.out.println("登录成功！");
			}
			//登录失败，输出错误信息，重复登录函数
			else
			{
				System.out.println("ID/密码 错误");
				System.out.println("请重新登录:");
				adminLogin();
			}
			rs.close();
			ptmt.close();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException:"+e.getMessage());
		}*/
	}
	//图书入库
	public static void bookInput() throws SQLException{

	}
	//图书查询
	public static void bookInquiry() throws SQLException{
		try
		{
			String sqlstr = null;
			System.out.println("请选择查询关键字:\n1:类别\n2:书名\n3:出版社\n4:年份\n5:作者\n6:价格");
			int i = input.nextInt();
			//根据用户选择决定PreparedStatement内容，默认书名排序
			switch(i)
			{
				//类别
				case 1:
					sqlstr="select * from book where category = ? order by title limit 50";
					break;
				//书名
				case 2:
					sqlstr="select * from book where title = ? order by title limit 50";
					break;
				//出版社
				case 3:
					sqlstr="select * from book where press = ? order by title limit 50";
					break;
				//年份
				case 4:
					sqlstr="select * from book where year between ? and ? order by title limit 50";
					break;
				//作者
				case 5:
					sqlstr="select * from book where author = ? order by title limit 50";
					break;
				//价格
				case 6:
					sqlstr="select * from book where price between ? and ? order by title limit 50";
					break;
			}
			PreparedStatement ptmt=con.prepareStatement(sqlstr);
			System.out.println("输入查询条件:");
			switch(i)
			{
				//年份，输入int型年份区间
				case 4:
					int min_year,max_year;
					min_year=input.nextInt();
					max_year=input.nextInt();
					ptmt.setInt(1,min_year);
					ptmt.setInt(2,max_year);
					break;
				//价格，输入BigDecimal型价格区间
				case 6:
					BigDecimal min_price,max_price;
					min_price=input.nextBigDecimal();
					max_price=input.nextBigDecimal();
					ptmt.setBigDecimal(1,min_price);
					ptmt.setBigDecimal(2,max_price);
					break;
				//其余输入字符串
				default:
					//输入流格式输入字符串，可以规避字符串中含有空格导致输入提前结束
					BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
					String inquire = null;
					try 
					{
						inquire=br.readLine();
					} 
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					ptmt.setString(1,inquire);
			}
			ResultSet rs = ptmt.executeQuery();
			//无匹配内容，输出无匹配信息
			if(!rs.next())
			{
				System.out.println("没有匹配");
			}
			//有匹配内容
			else
			{
			//输出列名
				System.out.println(
				"bno"+"\t"+"category"+"\t"+"title"+"\t"+
				"press"+"\t"+"year"+"\t"+"author"+"\t"+
				"price"+"\t"+"total"+"\t"+"stock");
			//输出第一行
				System.out.println(
				rs.getString("bno")+"\t"+
				rs.getString("category")+"\t"+
				rs.getString("title")+"\t"+
				rs.getString("press")+"\t"+
				rs.getInt("year")+"\t"+
				rs.getString("author")+"\t"+
				rs.getBigDecimal("price")+"\t"+
				rs.getInt("total")+"\t"+
				rs.getInt("stock"));
			//输出剩下的行
			while(rs.next())
			{
				System.out.println(
				rs.getString("bno")+"\t"+
				rs.getString("category")+"\t"+
				rs.getString("title")+"\t"+
				rs.getString("press")+"\t"+
				rs.getInt("year")+"\t"+
				rs.getString("author")+"\t"+
				rs.getBigDecimal("price")+"\t"+
				rs.getInt("total")+"\t"+
				rs.getInt("stock"));
			}
			}
			//关闭Statement和ResultSet对象，退出函数
			rs.close();
			ptmt.close();
		}
		catch(SQLException e)
		{
			System.out.println("SQLException:"+e.getMessage());
		}
	}
	//借书
	public static void bookBorrow() throws SQLException{

	}
	//还书
	public static void bookReturn() throws SQLException{

	}
}
