import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;


public class guiCaller {
    
    public static void main(String arr[])
    {        
        GUI gui=new GUI();
    }    
    
}


class GUI extends JFrame implements ActionListener
{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     int screenWidth, screenHeight;
     JPanel mainPanel,topPanel,bottomPanel;
     JTextField productTextField,priceTextField,salesTaxTextField,totalTextField;
     JLabel productLabel,productListLabel, categoryLabel,priceLabel,importedLabel,salesTaxLabel,totalTaxLabel;
     
     
     JButton addButton,addToBillButton,clearButton;
     JComboBox categoryCombobox,importedCombobox;
     String[] categoryCombobox_Items={"Books","Food","Medical Products","Luxury","Electronics","Sports","Other"},importedComboboxItems={"No","Yes"};
     
     
     JComboBox productListComboBox;
     
     String[] productList_Items;
     
     
     float total=0.0f;
     float total_tax=0.0f;
     
     
     JLabel invoice;
     JTable jTable=null;
     
     
     String[] colName = { "PRODUCT", "CATEGORY", "IMPORTED", "PRICE/UNIT"};
    

//	DecimalFormat df = new DecimalFormat("xxxxxx.xx");

     
     
    GUI() 
    {
      super("Bill Counter");
 
      if (jTable == null) 
  	  {
        jTable = new JTable() 
        {
            public boolean isCellEditable(int nRow, int nCol)
            {
                return false;
            }
         };
       }
    
 	   DefaultTableModel productTableModel = (DefaultTableModel) jTable.getModel();
       productTableModel.setColumnIdentifiers(colName);
       jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     
     
		
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        	
       	
        	tableModel.addRow(colName);
        	jTable.setModel(tableModel);
    
		
		
		 		    
    	
  		try
  	    {
 	 	   jdbcProduct jp = new jdbcProduct();
  		   productList_Items= jp.retrieve();
    	}
    	catch(Exception e3)
  	    {
  	    	System.out.println("Here:"+e3);
  	    }
    
    
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);//for closing on clicking close button
        setLayout(null);
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
        setSize(screenWidth, screenHeight);
        setResizable(false);
    
    
        mainPanel = new JPanel();
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel=new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        
        topPanel.setBounds(0, 0, (int) (0.8 * Toolkit.getDefaultToolkit().getScreenSize().width), (int) (0.1 * Toolkit.getDefaultToolkit().getScreenSize().height));
        
        bottomPanel.setBounds(50, 200, (int) (0.8 * Toolkit.getDefaultToolkit().getScreenSize().width), (int) (0.5 * Toolkit.getDefaultToolkit().getScreenSize().height));
        
      //  topPanel.setBackground(Color.red);
     //  bottomPanel.setBackground(Color.blue); 
        
       productLabel=new JLabel("Product");
       productTextField=new JTextField(15);
       
       
       productListLabel=new JLabel("Product List");
       productListComboBox=new JComboBox(productList_Items);
       
       
       categoryLabel=new JLabel("Category");
       categoryCombobox=new JComboBox(categoryCombobox_Items);
       
       priceLabel=new JLabel("Price");
       priceTextField=new JTextField(10);
       
       importedLabel=new JLabel("Imported");
       importedCombobox=new JComboBox(importedComboboxItems);
            
      
      
       addButton=new JButton("ADD");
       
       invoice=new JLabel("Purchase List");
       
       
       
       clearButton=new JButton("Clear Bill");
       
       
       // BILLING COMPONENTS
       
       
       addToBillButton=new JButton("Add To Bill");
      
       
       salesTaxLabel=new JLabel("Sales Tax");
       salesTaxTextField=new JTextField(10);
       
       totalTaxLabel=new JLabel("Total");
       totalTextField=new JTextField(10);
       
       
       
       salesTaxTextField.setText(""+total_tax);
       totalTextField.setText(""+total);
         
       
        topPanel.add(productLabel);
        topPanel.add(productTextField);
        topPanel.add(categoryLabel);
        topPanel.add(categoryCombobox);
        topPanel.add(priceLabel);
        topPanel.add(priceTextField);
        topPanel.add(importedLabel);
        topPanel.add(importedCombobox);
        topPanel.add(addButton);
        
        
        bottomPanel.add(productListLabel);
        bottomPanel.add(productListComboBox);
        bottomPanel.add(addToBillButton);
        bottomPanel.add(invoice);
        bottomPanel.add(jTable);
        bottomPanel.add(salesTaxLabel);
        bottomPanel.add(salesTaxTextField);
        bottomPanel.add(totalTaxLabel);
        bottomPanel.add(totalTextField);
        bottomPanel.add(clearButton);
        
        
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, screenWidth, screenHeight);
        
        
        addButton.addActionListener(this);
        addToBillButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
        add(mainPanel);

        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==addButton)
        { 
        	
        	//here conditions for null are not checked due to time contraint
        	//also, we can add a textfield that confirms successful addition of product, which again i have skipped. 
        	//but can be done by returning a true or false to this calling pgm
        	
        	
        	String product="",category="",imported="";
			Float price=0.0f;
        	
        	product=productTextField.getText();
        	category=(String)categoryCombobox.getSelectedItem();
        	imported=(String)importedCombobox.getSelectedItem();
        	
        	
        	
        	price=Float.parseFloat(priceTextField.getText());
        	
        	
        	try
        	{
        	jdbcProduct ap= new jdbcProduct();
        	ap.insert(product,category,imported,price);
 			}
 			catch(Exception e5){}
 			
        	productListComboBox.addItem(""+product);
        
        	
        	productTextField.setText("");
        	priceTextField.setText("");
        	
        	
        }
        
        
        else if(e.getSource()==addToBillButton)
        {
        	
        	
        	try
        	{
        	Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bill_db","bill_db");
			String str_bill="select * from product_details where product='";
	
        	
        	str_bill+=(String)productListComboBox.getSelectedItem() +"'";
        	
			PreparedStatement ps=con.prepareStatement(str_bill);

			ResultSet rs=ps.executeQuery();
        	
        	rs.next();
        	
        	
        //	System.out.println(""+pro+cat+imp+pri);
        	
        	
        	DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        	
        	
        	String[] data = new String[4];
        	
        	
        	data[0]=rs.getString(1);
        	data[1]=rs.getString(2);
        	data[2]=rs.getString(3);
        	data[3]=""+rs.getFloat(4);
        	
        	
        	float price = Float.parseFloat(data[3]);
        	
        	float temp_total=price;
        	float temp_sales_tax=0.0f;
        	
        	
        	//10% tax
        	if(data[1].equalsIgnoreCase("books") || data[1].equalsIgnoreCase("food") || data[1].equalsIgnoreCase("medical products"))
			{
				
			}        	
			else
			{
				temp_sales_tax=(0.1f*temp_total);
			}
        	
        	
        	//5% tax for all imported goods
        	if(data[2].equalsIgnoreCase("yes"))
			{
				temp_sales_tax+=(0.05f*temp_total);
			}
        	
        	
        	//to apprx to nearest 0.05
        	
        	float tmp = Math.round((temp_sales_tax*100));
        	
        	
        	
        	int quo=((int)tmp)%5;
        	if(quo>0)
        	{
        		temp_sales_tax=(tmp+(5-quo))/100;
        	}
        	else
        	{
        		temp_sales_tax=(tmp)/100;
        	}
        	
        	
        	
        	data[3]=""+(temp_total+temp_sales_tax);
        	
        	
        	tableModel.addRow(data);
        	jTable.setModel(tableModel);
    
    		
    
    		total+=temp_total + temp_sales_tax;
        	totalTextField.setText(""+total);
        	
        	
        	total_tax+=temp_sales_tax;
        	salesTaxTextField.setText(""+total_tax);
        	
    
    	
        	}
        	catch(Exception e4)
        	{}    	
        	
        }
        else if(e.getSource()==clearButton)
        {
        
        	DefaultTableModel model = (DefaultTableModel) jTable.getModel();
    		model.setRowCount(0);
        
        	total=0.0f;
        	totalTextField.setText(""+total);
        
        
        	total_tax=0.0f;
        	salesTaxTextField.setText(""+total_tax);
        	
        
    	}
        
    }
}
    