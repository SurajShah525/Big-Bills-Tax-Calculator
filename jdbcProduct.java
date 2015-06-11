import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class jdbcProduct
{
	
	
	static String product="",category="",imported="";
	static float price=0.0f;
	
	
	public void insert(String product1,String category1,String imported1, Float price1) throws Exception
	{
	
	
	
		product=product1;
		category=category1;
		imported=imported1;
		price=price1;
	
	
	
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bill_db","bill_db");
		String str="insert into product_details values(?,?,?,?)";
		PreparedStatement ps=con.prepareStatement(str);
		
	
		String price2=""+price;
		
		ps.setString(1,product);
		ps.setString(2,category);
		ps.setString(3,imported);
		ps.setFloat(4,price);
			

		ps.executeUpdate();
		
	
		con.close();
		ps.close();	
	
	}
	
	
	
	public String[] retrieve() throws Exception
	{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bill_db","bill_db");
		String str="select product from product_details";
		String str1="select count(product) from product_details";
		
		
		PreparedStatement ps=con.prepareStatement(str1);

		ResultSet rs=ps.executeQuery();



		int cnt=0;

			rs.next();
			String s=""+rs.getInt(1);
	//		System.out.println(""+s);
			cnt=Integer.parseInt(s);	
		
		PreparedStatement ps1=con.prepareStatement(str);

		ResultSet rs1=ps1.executeQuery();

		String to_ret[] = new String[cnt];


		int i=0;
		while(rs1.next())
		{
			to_ret[i++]=""+rs1.getString(1);
			
		}	
		
		con.close();
		ps1.close();
		ps.close();
		rs.close();
		rs1.close();
		
		return to_ret;
		
	}
	
	
	public static void main(String arr[])throws Exception
	{
		
	}	
}