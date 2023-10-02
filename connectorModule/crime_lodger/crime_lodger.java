package crime_lodger;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class crime_lodger extends Frame implements ActionListener, ItemListener
{
  Panel p1,p2,p3,p4,p5,p6,p7,p10,p11,p12;
  Panel dp1,dp2,dp3,dp4,dp5,dp6,dp7,dp8;
  Button b1,b2,b3,b4,b5,b8,b9,b10,eb1,eb2,fb1,fb2;
  Button sb1,sb2,sb3,sb4,sb5,sb6;
  Button crb1,crb2;
  Button vcb1;
  Label l1,l,l2,el1,el2,el3,el4,el5,el6,el7,el8;
  Label fl1,fl2,fl3,fl4,fl5,fl6,fl7,fl8,fl9,fl10,fl11; 
  Label dpl1,dpl2,dpl3,dpl4,dpl5,dpl6,dpl7,dpl8;
  Label dpl11,dpl12,dpl13;
  Label dpl21,dpl22,dpl23;
  Label dpl31,dpl32,dpl33;
  Label dpl41,dpl42,dpl43;
  Label dpl51,dpl52,dpl53;
  Label dpl61,dpl62,dpl63;
  Label dpl71,dpl72,dpl73;
  Label dpl81,dpl82,dpl83;
  Label al1;
  Label vfl1;
  Label dl1;
  Label crl1,crl2,crl3,crl4,crl5;
  Label vcl1;
  List crli1;
  TextField ftf1,ftf2,ftf3,ftf4,ftf5,ftf6,ftf7,ftf8,ftf9,ftf10,fta1;
  TextField etf1,etf2,etf3;
  TextField atf1;
  TextField vftf1;
  TextField dpt11,dpt12,dpt13;
  TextField dpt21,dpt22,dpt23; 
  TextField dpt31,dpt32,dpt33;  
  TextField dpt41,dpt42,dpt43;  
  TextField dpt51,dpt52,dpt53;  
  TextField dpt61,dpt62,dpt63;
  TextField dpt71,dpt72,dpt73;
  TextField dpt81,dpt82,dpt83;
  TextField vctf1;
  TextArea crarea; 
  Checkbox ecbg1,ecbg2;
  Checkbox acbg1,acbg2,acbg3,acbg4,acbg5;
  Checkbox vfcbg1,vfcbg2,vfcbg3,vfcbg4,vfcbg5;
  Checkbox crcbg1,crcbg2;
  Checkbox vccbg1,vccbg2,vccbg3,vccbg4,vccbg5,vccbg6;
  Choice ec1,ec2,fc1,fc2,crc1;
  TextArea ta1;
  Button ab1;
  Button vfb1;
  JTable jt,jt1,jt2;
  JScrollPane sp,sp1,sp2;
  DefaultTableModel tableModel,tableModel1,tableModel2;
  String alcbg,vfcbg,vccbg,vctf;
  int empid []=new int[1000];
  int index=0;
  
//DATABASE CREATION AND OPERATIONS.......................................................................
  
    Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	Scanner sc;
	
	Connection getConnected()
	{
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
			System.out.println("Welcome ! Java Connetion Successfull ");
		}catch(Exception e){ System.out.println(e);} 

		return con;
	}
	
	void insert_into_employee_table_from_employee_form()
	{
	    String eid=etf1.getText().trim();
		int empid=Integer.valueOf(eid);
		String empname=etf2.getText().trim();
		String gender=(ecbg1.getState())?ecbg1.getLabel().trim():ecbg2.getLabel().trim();
		String job=ec1.getSelectedItem().trim();
		String mobile=etf3.getText().trim();
		String emp_add=ta1.getText().trim();
		String city=ec2.getSelectedItem().trim();
		
		con=getConnected();
		try	
		{
			st=con.createStatement();
			st.executeUpdate("insert into employee values("+empid+",'"+empname+"','"+gender+"','"+job+"','"+mobile+"','"+emp_add+"','"+city+"')");
			System.out.println("Insertion Successfull ");
			con.close();
		}catch(Exception e) {}
	}
	
	void insert_into_fir_table_from_fir_form()
	{
		// fir table
		String fir=ftf1.getText().trim();
		int firno=Integer.valueOf(fir);
		String crimetype=fc1.getSelectedItem().trim();
		String crimedesc=fta1.getText().trim();
		String crimedate=ftf6.getText().trim();
		String crimetime=ftf9.getText().trim();
		String crimeplace= ftf10.getText().trim();
		
		//victim table
		String vname = ftf2.getText().trim();
		String vmob = ftf7.getText().trim();
		String vadd = ftf8.getText().trim();
		
		//suspect table
		String sname=ftf3.getText().trim();
		String sadd=ftf4.getText().trim();
		String smob=ftf5.getText().trim();
		
		//investigation table
		int eid=empid[index];
		
		con=getConnected();
		try
		{
			//victim
			st=con.createStatement();
			st.executeUpdate("insert into victim(victimname,victimmobno,victimadd) values('"+vname+"','"+vmob+"','"+vadd+"')");
			System.out.println("Insertion Successfull ");
			
			//suspect
			st=con.createStatement();
			st.executeUpdate("insert into suspect(sname,sadd,smob) values('"+sname+"','"+sadd+"','"+smob+"')");
			System.out.println("Insertion Successfull ");
			
			//extract suspect id
			st=con.createStatement();
			rs=st.executeQuery("select suspectid from suspect");
			int suspectid=0;
			while(rs.next())
				suspectid=rs.getInt("suspectid");
			
			//extract victim id
			st=con.createStatement();
			rs=st.executeQuery("select victimid from victim");
			int victimid=0;
			while(rs.next())
				victimid=rs.getInt("victimid");
			
			
			st=con.createStatement();
			st.executeUpdate("insert into fir values("+firno+",'"+crimetype+"','"+crimedesc+"','"+crimedate+"','"+crimetime+"','"+crimeplace+"',"+victimid+","+suspectid+")");
			System.out.println("Insertion Succesfull ");
			
			
			//investigation
			st=con.createStatement();
			st.executeUpdate("insert into investigation(firno,empid) values("+firno+","+eid+")");
			System.out.println("Insertion Successfull ");
			con.close();
		}catch(Exception e) {}
	}
	
	void insert_into_casereview_table()
	{
		    String firno=crli1.getSelectedItem().trim();
		    String s="";
		    for(int i=0;i<firno.length();i++)
		    {
		    	if(firno.charAt(i)!='/')
		    	{
		    	 s=s+firno.charAt(i);
		    	}
		    	else
		    	{
		    		if(firno.charAt(i)=='/')
		    			break;
		    	}
		    }
			int firid=Integer.valueOf(s);
			String status=(crcbg1.getState())?crcbg1.getLabel().trim():crcbg2.getLabel().trim();
			String conclusion=crarea.getText().trim();
			
			con=getConnected();
			try	
			{
				st=con.createStatement();
				st.executeUpdate("insert into casereview(firid,conclusion,status) values("+firid+",'"+conclusion+"','"+status+"')");
				System.out.println("Insertion Successfull ");
				con.close();
			}catch(Exception e) {}
	}
 
	
	
//DATABASE END.......................................................................................
	
  crime_lodger()
  {
	  //Windows_Adapter_File_TO_ADD_ACTION_ON_X_Button_START.........................................
	
	  addWindowListener (new WindowAdapter() {    
          public void windowClosing (WindowEvent e) {    
              dispose();    
          }    
      });
	  //Windows_Adapter_File_TO_ADD_ACTION_ON_X_Button_END.........................................
//Dashboard_Panel_Start.........................................................................	  
   p3=new Panel();
   p3.setLayout(null);
   p3.setBounds(160,160,1180,768);
   p3.setBackground(new Color(188,189,34));
   p3.setVisible(true);
   
   dl1=new Label(" STATS/FIGURES ",Label.CENTER);
   dl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,60))	;
   dl1.setBounds(0,20,1200,60);
   
   
   dpl1=new Label(" MURDER CASES ",Label.CENTER);
   dpl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl1.setBounds(5,5,200,30);
   
   dp1=new Panel();
   dp1.setLayout(null);
   dp1.setBounds(60,100,200,170);
   dp1.setBackground(new Color(204,204,255));
   dp1.setVisible(true);
  
   dpl2=new Label(" MOLESTATION CASES ",Label.CENTER);
   dpl2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl2.setBounds(5,5,200,30);
   
   dp2=new Panel();
   dp2.setLayout(null);
   dp2.setBounds(350,100,200,170);
   dp2.setBackground(new Color(255,255,128));
   dp2.setVisible(true);
   
   dpl3=new Label(" THEFT CASES ",Label.CENTER);
   dpl3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl3.setBounds(5,5,200,30);
   
   dp3=new Panel();
   dp3.setLayout(null);
   dp3.setBounds(660,100,200,170);
   dp3.setBackground(new Color(102,102,153));
   dp3.setVisible(true);
   
   dpl4=new Label(" FRAUD CASES ",Label.CENTER);
   dpl4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl4.setBounds(5,5,200,30);
   
   dp4=new Panel();
   dp4.setLayout(null);
   dp4.setBounds(60,350,200,170);
   dp4.setBackground(new Color(255,255,153));
   dp4.setVisible(true);
   
   dpl5=new Label(" RAPE CASES ",Label.CENTER);
   dpl5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl5.setBounds(5,5,200,30);
   
   dp5=new Panel();
   dp5.setLayout(null);
   dp5.setBounds(350,350,200,170);
   dp5.setBackground(new Color(255,204,153));
   dp5.setVisible(true);
   
   dpl6=new Label(" TERRORIST CASES ",Label.CENTER);
   dpl6.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl6.setBounds(5,5,200,30);
   
   dp6=new Panel();
   dp6.setLayout(null);
   dp6.setBounds(660,350,200,170);
   dp6.setBackground(new Color(204,255,255));
   dp6.setVisible(true);
   
   dpl7=new Label(" NARCOTICS CASES ",Label.CENTER);
   dpl7.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl7.setBounds(5,5,200,30);
   
   dp7=new Panel();
   dp7.setLayout(null);
   dp7.setBounds(970,100,200,170);
   dp7.setBackground(new Color(255,255,0));
   dp7.setVisible(true);
   
   dpl8=new Label(" ANTI-NATIONAL CASES ",Label.CENTER);
   dpl8.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15))	;
   dpl8.setBounds(5,5,200,30);
   
   dp8=new Panel();
   dp8.setLayout(null);
   dp8.setBounds(970,350,200,170);
   dp8.setBackground(new Color(0,255,255));
   dp8.setVisible(true);
   
   dpl11=new Label(" TOTAL ");
   dpl11.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl11.setBounds(10,50,80,20);
   
   dpt11=new TextField();
   dpt11.setBounds(100,50,50,20);
   dpt11.setEnabled(false);
   
   dpl12=new Label(" SOLVED ");
   dpl12.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl12.setBounds(10,80,80,20);
   
   dpt12=new TextField();
   dpt12.setBounds(100,80,50,20);
   dpt12.setEnabled(false);
   
   dpl13=new Label(" PENDING ");
   dpl13.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl13.setBounds(10,110,80,20);
   
   dpt13=new TextField();
   dpt13.setBounds(100,110,50,20);
   dpt13.setEnabled(false);
   
   dpl21=new Label(" TOTAL ");
   dpl21.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl21.setBounds(10,50,80,20);
   
   dpt21=new TextField();
   dpt21.setBounds(100,50,50,20);
   dpt21.setEnabled(false);
   
   dpl22=new Label(" SOLVED ");
   dpl22.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl22.setBounds(10,80,80,20);
   
   dpt22=new TextField();
   dpt22.setBounds(100,80,50,20);
   dpt22.setEnabled(false);
   
   dpl23=new Label(" PENDING ");
   dpl23.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl23.setBounds(10,110,80,20);
   
   dpt23=new TextField();
   dpt23.setBounds(100,110,50,20);
   dpt23.setEnabled(false);
   
   dpl31=new Label(" TOTAL ");
   dpl31.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl31.setBounds(10,50,80,20);
   
   dpt31=new TextField();
   dpt31.setBounds(100,50,50,20);
   dpt31.setEnabled(false);
   
   dpl32=new Label(" SOLVED ");
   dpl32.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl32.setBounds(10,80,80,20);
   
   dpt32=new TextField();
   dpt32.setBounds(100,80,50,20);
   dpt32.setEnabled(false);
   
   dpl33=new Label(" PENDING ");
   dpl33.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl33.setBounds(10,110,80,20);
   
   dpt33=new TextField();
   dpt33.setBounds(100,110,50,20);
   dpt33.setEnabled(false);
   
   dpl41=new Label(" TOTAL ");
   dpl41.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl41.setBounds(10,50,80,20);
   
   dpt41=new TextField();
   dpt41.setBounds(100,50,50,20);
   dpt41.setEnabled(false);
   
   dpl42=new Label(" SOLVED ");
   dpl42.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl42.setBounds(10,80,80,20);
   
   dpt42=new TextField();
   dpt42.setBounds(100,80,50,20);
   dpt42.setEnabled(false);
   
   dpl43=new Label(" PENDING ");
   dpl43.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl43.setBounds(10,110,80,20);
   
   dpt43=new TextField();
   dpt43.setBounds(100,110,50,20);
   dpt43.setEnabled(false);
   
   dpl51=new Label(" TOTAL ");
   dpl51.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl51.setBounds(10,50,80,20);
   
   dpt51=new TextField();
   dpt51.setBounds(100,50,50,20);
   dpt51.setEnabled(false);
   
   dpl52=new Label(" SOLVED ");
   dpl52.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl52.setBounds(10,80,80,20);
   
   dpt52=new TextField();
   dpt52.setBounds(100,80,50,20);
   dpt52.setEnabled(false);
   
   dpl53=new Label(" PENDING ");
   dpl53.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl53.setBounds(10,110,80,20);
   
   dpt53=new TextField();
   dpt53.setBounds(100,110,50,20);
   dpt53.setEnabled(false);
   
   dpl61=new Label(" TOTAL ");
   dpl61.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl61.setBounds(10,50,80,20);
   
   dpt61=new TextField();
   dpt61.setBounds(100,50,50,20);
   dpt61.setEnabled(false);
   
   dpl62=new Label(" SOLVED ");
   dpl62.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl62.setBounds(10,80,80,20);
   
   dpt62=new TextField();
   dpt62.setBounds(100,80,50,20);
   dpt62.setEnabled(false);
   
   dpl63=new Label(" PENDING ");
   dpl63.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl63.setBounds(10,110,80,20);
   
   dpt63=new TextField();
   dpt63.setBounds(100,110,50,20);
   dpt63.setEnabled(false);
   
   dpl71=new Label(" TOTAL ");
   dpl71.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl71.setBounds(10,50,80,20);
   
   dpt71=new TextField();
   dpt71.setBounds(100,50,50,20);
   dpt71.setEnabled(false);
   
   dpl72=new Label(" SOLVED ");
   dpl72.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl72.setBounds(10,80,80,20);
   
   dpt72=new TextField();
   dpt72.setBounds(100,80,50,20);
   dpt72.setEnabled(false);
   
   dpl73=new Label(" PENDING ");
   dpl73.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl73.setBounds(10,110,80,20);
   
   dpt73=new TextField();
   dpt73.setBounds(100,110,50,20);
   dpt73.setEnabled(false);
   
   dpl81=new Label(" TOTAL ");
   dpl81.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl81.setBounds(10,50,80,20);
   
   dpt81=new TextField();
   dpt81.setBounds(100,50,50,20);
   dpt81.setEnabled(false);
   
   dpl82=new Label(" SOLVED ");
   dpl82.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl82.setBounds(10,80,80,20);
   
   dpt82=new TextField();
   dpt82.setBounds(100,80,50,20);
   dpt82.setEnabled(false);
  
   dpl83=new Label(" PENDING ");
   dpl83.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
   dpl83.setBounds(10,110,80,20);
   
   dpt83=new TextField();
   dpt83.setBounds(100,110,50,20);
   dpt83.setEnabled(false);


   
   add(p3);
   p3.add(dl1);
   p3.add(dp1);
   p3.add(dp2);
   p3.add(dp3);
   p3.add(dp4);
   p3.add(dp5);
   p3.add(dp6);
   p3.add(dp7);
   p3.add(dp8);
   
   dp1.add(dpl1);
   dp1.add(dpl11);
   dp1.add(dpl12);
   dp1.add(dpl13);
   dp1.add(dpt11);
   dp1.add(dpt12);
   dp1.add(dpt13);
   
   dp2.add(dpl2);
   dp2.add(dpl21);
   dp2.add(dpl22);
   dp2.add(dpl23);
   dp2.add(dpt21);
   dp2.add(dpt22);
   dp2.add(dpt23);
   
   dp3.add(dpl3);
   dp3.add(dpl31);
   dp3.add(dpl32);
   dp3.add(dpl33);
   dp3.add(dpt31);
   dp3.add(dpt32);
   dp3.add(dpt33);
   
   dp4.add(dpl4);
   dp4.add(dpl41);
   dp4.add(dpl42);
   dp4.add(dpl43);
   dp4.add(dpt41);
   dp4.add(dpt42);
   dp4.add(dpt43);
   
   dp5.add(dpl5);
   dp5.add(dpl51);
   dp5.add(dpl52);
   dp5.add(dpl53);
   dp5.add(dpt51);
   dp5.add(dpt52);
   dp5.add(dpt53);

   
   dp6.add(dpl6);
   dp6.add(dpl61);
   dp6.add(dpl62);
   dp6.add(dpl63);
   dp6.add(dpt61);
   dp6.add(dpt62);
   dp6.add(dpt63);
   
   dp7.add(dpl7);
   dp7.add(dpl71);
   dp7.add(dpl72);
   dp7.add(dpl73);
   dp7.add(dpt71);
   dp7.add(dpt72);
   dp7.add(dpt73);
   
   dp8.add(dpl8);
   dp8.add(dpl81);
   dp8.add(dpl82);
   dp8.add(dpl83);
   dp8.add(dpt81);
   dp8.add(dpt82);
   dp8.add(dpt83);
   
   Count_Total_Cases();
   Count_Solved_Cases();
   Count_Pending_Cases();
      
//DashBoard_Panel_End...........................................................................
   
//TITLE PANEL POLICE LINE DO NOT CROSS START..............................................
   p2=new Panel();
   p2.setLayout(null);
   p2.setBounds(160,50,1180,100);
   p2.setBackground(new Color(254,184,0));
   
   l1=new Label();
   l1.setBounds(0,0,1200,20);
   l1.setBackground(new Color(0,0,0));
   l=new Label("POLICE LINE DO NOT CROSS",Label.CENTER);
   l.setBounds(0,20,1200,60);
   l.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,60))	;
   l2=new Label();
   l2.setBounds(0,80,1200,20);
   l2.setBackground(new Color(0,0,0));
   
   add(p2);
   p2.add(l1);
   p2.add(l);
   p2.add(l2);	
//TITLE PANEL POLICE LINE DO NOT CROSS END..............................................


//SIDE/MENU_PANEL START ..............................................................

   p1=new Panel();
   p1.setLayout(null);
   p1.setBounds(0,0,150,768);
   p1.setBackground(new Color(52,52,52));

   b1=new Button("DASHBOARD");
   b1.setBounds(15,100,120,40);
   b1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b1.addActionListener(this);
   
   b2=new Button("ADD EMPLOYEES");
   b2.setBounds(15,180,120,40);
   b2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b2.addActionListener(this);
   
   b3=new Button("ALL EMPLOYEES");
   b3.setBounds(15,260,120,40);
   b3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b3.addActionListener(this);
   
   b4=new Button("ADD FIR");
   b4.setBounds(15,340,120,40);
   b4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b4.addActionListener(this);

   b5=new Button("VIEW FIR");
   b5.setBounds(15,420,120,40);
   b5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b5.addActionListener(this);
   
   b8=new Button("SETTINGS");
   b8.setBounds(15,500,120,40);
   b8.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b8.addActionListener(this);
   
   b9=new Button("CASE REVIEW");
   b9.setBounds(15,580,120,40);
   b9.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b9.addActionListener(this);
   
   b10=new Button("VIEW CASES");
   b10.setBounds(15,660,120,40);
   b10.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   b10.addActionListener(this);
   
   add(p1);   
   p1.add(b1);
   p1.add(b2);
   p1.add(b3);
   p1.add(b4);
   p1.add(b5);
   p1.add(b8);	
   p1.add(b9);
   p1.add(b10);

//SIDE/MENU_PANEL END ..............................................................
   
 //EMPLOYEE FORM START..............................................................
 
   p4=new Panel();
   p4.setLayout(null);
   p4.setBounds(160,160,1180,768);
   p4.setBackground(new Color(255,217,74));
   p4.setVisible(false);
 
   el1=new Label(" EMPLOYEE FORM ",Label.CENTER);
   el1.setBounds(0,20,1200,50);
   el1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,40));
   
   el2=new Label(" EMPLOYEE CODE ");
   el2.setBounds(300,100,200,40);
   el2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   etf1=new TextField();
   etf1.setBounds(520,100,250,30);
   
   el3=new Label(" EMPLOYEE NAME ");
   el3.setBounds(300,150,200,40);
   el3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   etf2=new TextField();
   etf2.setBounds(520,150,250,30);

   el4=new Label(" GENDER ");
   el4.setBounds(300,200,200,40);
   el4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   CheckboxGroup ecbg=new CheckboxGroup();
   ecbg1=new Checkbox("M",ecbg,false);
   ecbg1.setBounds(520,200,50,40); 
   ecbg1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ecbg2=new Checkbox("F",ecbg,false);
   ecbg2.setBounds(580,200,50,40); 
   ecbg2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));

   el5=new Label(" JOB PROFILE ");
   el5.setBounds(300,250,200,40);
   el5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));

   ec1=new Choice();
   ec1.setBounds(520,250,250,40);
   ec1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ec1.add("-Pick an Option-");
   ec1.add(" ACP ");
   ec1.add(" DCP ");
   ec1.add(" Constable ");
   ec1.add(" Head Constable ");
   ec1.add(" Sub Inspector ");
   ec1.add(" Inspector ");
   ec1.add(" Circuit Officer (CO) ");
   ec1.add(" SP ");
   ec1.add(" SSP ");
   ec1.add(" IG ");
   ec1.add(" DIG ");

   el6=new Label(" MOBILE NUMBER ");
   el6.setBounds(300,300,200,40);
   el6.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   etf3=new TextField();
   etf3.setBounds(520,300,250,30);
   
   el7=new Label("EMPLOYEE ADDRESS");
   el7.setBounds(300,350,210,40);
   el7.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ta1=new TextArea();
   ta1.setBounds(520,350,250,80);
   
   el8=new Label(" CITY ");
   el8.setBounds(300,450,200,40);
   el8.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ec2=new Choice();
   ec2.setBounds(520,450,250,40);
   ec2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   
   ec2.add("-Pick an Option-");
   ec2.add(" Almora ");
   ec2.add(" Bageshwar ");
   ec2.add(" Chamoli ");
   ec2.add(" Dehradun ");
   ec2.add(" Haridwar ");
   ec2.add(" Haldwani ");
   ec2.add(" Jaspur ");
   ec2.add(" Kashipur ");
   ec2.add(" Rishikesh ");
   ec2.add(" Rudrapur ");
   ec2.add(" Tanakpur ");
   
   
   eb1=new Button("ADD");
   eb1.setBounds(370,520,120,40);
   eb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   eb1.addActionListener(this);
   
   eb2=new Button("RESET");
   eb2.setBounds(560,520,120,40);
   eb2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   eb2.addActionListener(this);
   
   add(p4);
   p4.add(el1);
   p4.add(el2);
   p4.add(etf1);
   p4.add(el3);
   p4.add(etf2);
   p4.add(el4);
   p4.add(ecbg1);
   p4.add(ecbg2);
   p4.add(el5);
   p4.add(ec1);
   p4.add(el6);
   p4.add(etf3);
   p4.add(el7);
   p4.add(ta1);
   p4.add(el8);
   p4.add(ec2);
   p4.add(eb1);
   p4.add(eb2);

//EMPLOYEE FORM END...............................................


//FIR FORM START..................................................

   p6=new Panel();
   p6.setLayout(null);
   p6.setBounds(160,160,1180,768);
   p6.setBackground(new Color(238, 130, 238));
   p6.setVisible(false);
   
   String columns1[]={"firno","crimetype","crimedesc","crimedate","crimetime","crimeplace","victimname","victimmobno","victimadd","sname","sadd","smob","inv_Officer"};
   
   fl1=new Label(" LODGE FIR ",Label.CENTER);
   fl1.setBounds(0,10,1200,30);
   fl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,30));
   
   fl2=new Label(" FIR NUMBER ");
   fl2.setBounds(300,50,270,30);
   fl2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf1=new TextField();
   ftf1.setBounds(720,50,410,30);

   fl3=new Label(" VICTIM NAME ");
   fl3.setBounds(300,100,270,30);
   fl3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf2=new TextField();
   ftf2.setBounds(720,100,410,30);   
   
   fl4=new Label(" CRIME TYPE / INVESTIGATING OFFICER ");
   fl4.setBounds(300,150,390,30);
   fl4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
 
   fc1=new Choice();
   fc1.setBounds(720,150,200,30);
   fc1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   fc1.add("-Select-");
   fc1.add(" Murder ");
   fc1.add(" Molestation ");
   fc1.add(" Theft ");
   fc1.add(" Fraud ");
   fc1.add(" Rape ");
   fc1.add(" Terriorst Act ");
   fc1.add(" Narcotics Act ");
   fc1.add(" Anti National Act ");
   
   fc2=new Choice();
   fc2.setBounds(930,150,200,30);
   fc2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   fc2.addItemListener(this);
   
   /*Choice_Control_Investigaing_officer_Method_START*/
   String v1="";
	  try{ 	
		  	int i=0;
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("Select empid,empname from employee");
			//fc2.add("-Select-");
				while(rs.next())
				{
				v1=rs.getString("empname");
				empid[i++]=rs.getInt("empid");
				fc2.add(v1);	
				}
				rs.close();
	            st.close();
	            con.close();
			}catch(Exception e) {}
	  /*Choice_Control_Investigaing_officer_Method_END*/

   
   fl5=new Label(" CRIME DESCRIPTION ");
   fl5.setBounds(300,200,270,30);
   fl5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   fta1=new TextField();
   fta1.setBounds(720,200,410,30);
   
   fl6=new Label(" SUSPECT NAME ");
   fl6.setBounds(300,250,270,30);
   fl6.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf3=new TextField();
   ftf3.setBounds(720,250,410,30); 
   
   fl7=new Label(" SUSPECT ADDRESS ");
   fl7.setBounds(300,300,270,30);
   fl7.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf4=new TextField();
   ftf4.setBounds(720,300,410,30); 
   
   fl8=new Label(" SUSPECT MOBILE NUMBER ");
   fl8.setBounds(300,350,270,30);
   fl8.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf5=new TextField();
   ftf5.setBounds(720,350,410,30); 
   
   fl9=new Label(" CRIME DATE/TIME/PLACE ");
   fl9.setBounds(300,400,270,30);
   fl9.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf6=new TextField();
   ftf6.setBounds(720,400,120,30); 

   ftf9=new TextField();
   ftf9.setBounds(870,400,120,30); 

   ftf10=new TextField();
   ftf10.setBounds(1020,400,110,30); 

   fl10=new Label(" VICTIM MOBILE NUMBER ");
   fl10.setBounds(300,450,270,30);
   fl10.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf7=new TextField();
   ftf7.setBounds(720,450,410,30); 
   
   fl11=new Label(" VICTIM ADDRESS ");
   fl11.setBounds(300,500,270,30);
   fl11.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
   
   ftf8=new TextField();
   ftf8.setBounds(720,500,410,30); 
   
   fb1=new Button("ADD");
   fb1.setBounds(470,540,120,30);
   fb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   fb1.addActionListener(this);
   
   fb2=new Button("RESET");
   fb2.setBounds(670,540,120,30);
   fb2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
   fb2.addActionListener(this);
   
   tableModel1 = new DefaultTableModel(columns1,0);
 	jt1=new JTable(tableModel1);
 	jt1.setModel(tableModel1);
 	
 
   add(p6);
   p6.add(fl1);
   p6.add(fl2);
   p6.add(ftf1);
   p6.add(fl3);
   p6.add(ftf2);
   p6.add(fl4);
   p6.add(fc1);
   p6.add(fc2);
   p6.add(fl5);
   p6.add(fta1);
   p6.add(fl6);
   p6.add(ftf3);
   p6.add(fl7);
   p6.add(ftf4);
   p6.add(fl8);
   p6.add(ftf5);
   p6.add(fl9);
   p6.add(ftf6);
   p6.add(ftf9);
   p6.add(ftf10);
   p6.add(fl10);
   p6.add(ftf7);
   p6.add(fl11);
   p6.add(ftf8);
   p6.add(fb1);
   p6.add(fb2);


//FIR FORM END.................................................
   
   
   
//All Employee Form Start......................................
   p5=new Panel();
   p5.setLayout(null);
   p5.setBounds(160,160,1180,768);
   p5.setVisible(false);
   
   	String columns[]={"EmployeeId","Employee Name","Gender","Job","Mobile","Address","City"};   
   	
   	CheckboxGroup acbg=new CheckboxGroup();
    acbg1=new Checkbox("Name",acbg,false);
    acbg1.setBounds(20,20,120,20); 
    acbg1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    acbg1.addItemListener(this);
   	
    acbg2=new Checkbox("City",acbg,false);
    acbg2.setBounds(140,20,80,20); 
    acbg2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    acbg2.addItemListener(this);
    
   	
    acbg3=new Checkbox("Mobile No",acbg,false);
    acbg3.setBounds(250,20,120,20); 
    acbg3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    acbg3.addItemListener(this);
    
   	
    acbg4=new Checkbox("Employee Id",acbg,false);
    acbg4.setBounds(400,20,120,20); 
    acbg4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    acbg4.addItemListener(this);
    
   	
    acbg5=new Checkbox("Job Profile",acbg,false);
    acbg5.setBounds(550,20,120,20); 
    acbg5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    acbg5.addItemListener(this);
    
    al1=new Label("-Select-",Label.CENTER);
    al1.setBounds(690,10,160,30);
    al1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,30));
    
    atf1=new TextField();
    atf1.setBounds(870,10,150,30);
    
    ab1=new Button("Search");
    ab1.setBounds(1040,10,120,30);
    ab1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
    ab1.addActionListener(this);
   	
    tableModel = new DefaultTableModel(columns,0);
	jt=new JTable(tableModel);
	jt.setModel(tableModel);
	fillEmployeeTable();
	
	//sp=new JScrollPane(jt); 
	
	//jt.setBounds(0,80,1179,450);
	//p5.add(jt); 
	
	add(p5);
	p5.add(acbg1);
	p5.add(acbg2);
	p5.add(acbg3);
	p5.add(acbg4);
	p5.add(acbg5);
	p5.add(al1);
	p5.add(atf1);
	p5.add(ab1);
	
//All Employee Form End......................................
	
	
//View FIR Form Start........................................
	
	   p7=new Panel();
	   p7.setLayout(null);
	   p7.setBounds(160,160,1180,768);
	   p7.setBackground(new Color(255,255,255));
	   p7.setVisible(false);
	
	CheckboxGroup vfcbg=new CheckboxGroup();
    vfcbg1=new Checkbox("Fir No",vfcbg,false);
    vfcbg1.setBounds(20,20,120,20); 
    vfcbg1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vfcbg1.addItemListener(this);
   	
    vfcbg2=new Checkbox("Crime Type",vfcbg,false);
    vfcbg2.setBounds(140,20,120,20); 
    vfcbg2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vfcbg2.addItemListener(this);
    
   	
    vfcbg3=new Checkbox("Crime Place",vfcbg,false);
    vfcbg3.setBounds(260,20,120,20); 
    vfcbg3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vfcbg3.addItemListener(this);
    
   	
    vfcbg4=new Checkbox("Victim Name",vfcbg,false);
    vfcbg4.setBounds(400,20,120,20); 
    vfcbg4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vfcbg4.addItemListener(this);
    
   	
    vfcbg5=new Checkbox("Inv Officer",vfcbg,false);
    vfcbg5.setBounds(550,20,120,20); 
    vfcbg5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vfcbg5.addItemListener(this);
    
    vfl1=new Label("-Select-",Label.CENTER);
    vfl1.setBounds(690,10,160,30);
    vfl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,30));
    
    vftf1=new TextField();
    vftf1.setBounds(870,10,150,30);
    
    vfb1=new Button("Search");
    vfb1.setBounds(1040,10,120,30);
    vfb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
    vfb1.addActionListener(this);
	
    add(p7);
    p7.add(vfcbg1);
	p7.add(vfcbg2);
	p7.add(vfcbg3);
	p7.add(vfcbg4);
	p7.add(vfcbg5);
	p7.add(vfl1);
	p7.add(vftf1);
	p7.add(vfb1);	
	
	fillfirtable();
	
//View FIR Form End..........................................   
	
	
//Settings_Panel_Start...........................................
	   p10=new Panel();
	   p10.setLayout(null);
	   p10.setBounds(160,160,1180,768);
	   p10.setBackground(new Color(140, 140, 140));
	   p10.setVisible(false);
	   
	   sb1=new Button("Background");
	   sb1.setBounds(15,100,120,40);
	   sb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb1.addActionListener(this);
	   
	   sb2=new Button("Title");
	   sb2.setBounds(15,250,120,40);
	   sb2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb2.addActionListener(this);
	   
	   sb3=new Button("Buttons");
	   sb3.setBounds(15,400,120,40);
	   sb3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb3.addActionListener(this);
	   
	   sb4=new Button("Labels");
	   sb4.setBounds(900,100,120,40);
	   sb4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb4.addActionListener(this);

	   sb5=new Button("TextFields");
	   sb5.setBounds(900,250,120,40);
	   sb5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb5.addActionListener(this);
	   
	   sb6=new Button("Panels");
	   sb6.setBounds(900,400,120,40);
	   sb6.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
	   sb6.addActionListener(this);


	    add(p10);
	    p10.add(sb1);
		p10.add(sb2);
		p10.add(sb3);
		p10.add(sb4);
		p10.add(sb5);
		p10.add(sb6);
//Settings_Panel_End.............................................	
		
//Case_Review_Panel_Start........................................
	p11=new Panel();
    p11.setLayout(null);
    p11.setBounds(160,160,1180,768);
    p11.setBackground(new Color(255,217,21));
    p11.setVisible(false);
    
    crl1=new Label(" INVESTIGATING OFFICER ");
    crl1.setBounds(200,50,300,30);
    crl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crc1=new Choice();
    crc1.setBounds(550,50,400,30);
    crc1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    crc1.addItemListener(this);
    
    crl2=new Label(" CASES ");
    crl2.setBounds(200,100,300,30);
    crl2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crl5=new Label("FirNo/Crimetype/Employee Id/Employee Name");
    crl5.setBounds(550,100,400,20);
    crl5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crli1=new List();
    crli1.setBounds(550,125,400,100);
    crli1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    crli1.addItemListener(this);
    
    
    crl3=new Label(" STATUS ");
    crl3.setBounds(200,240,300,30);
    crl3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    CheckboxGroup crcbg=new CheckboxGroup();
    crcbg1=new Checkbox(" SOLVED ",crcbg,false);
    crcbg1.setBounds(550,240,100,40); 
    crcbg1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crcbg2=new Checkbox(" PENDING ",crcbg,false);
    crcbg2.setBounds(680,240,150,40); 
    crcbg2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crl4=new Label(" CONCLUSION ");
    crl4.setBounds(200,300,300,30);
    crl4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crarea = new TextArea(" "); 
    crarea.setBounds(550,300,400,150);    
    crarea.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,20));
    
    crb1=new Button(" SUBMIT ");
    crb1.setBounds(350,490,100,30);
    crb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
    crb1.addActionListener(this);

    crb2=new Button(" RESET ");
    crb2.setBounds(550,490,100,30);
    crb2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,15));
    crb2.addActionListener(this);
    
    
    /*Choice_Control_Investigaing_officer_Method_START*/
    String inv="";
 	  try{ 	
 		  	int i=0;
 			Class.forName("com.mysql.cj.jdbc.Driver");  
 			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
 			Statement st=con.createStatement();
 			ResultSet rs=st.executeQuery("Select empid,empname from employee");
 			//fc2.add("-Select-");
 				while(rs.next())
 				{
 				inv=rs.getString("empname");
 				empid[i++]=rs.getInt("empid");
 				crc1.add(inv);	
 				}
 				rs.close();
 	            st.close();
 	            con.close();
 			}catch(Exception e) {}
 	  /*Choice_Control_Investigaing_officer_Method_END*/
 	  
    
    add(p11);
    p11.add(crl1);
    p11.add(crc1);
    p11.add(crl2);
    p11.add(crli1);
    p11.add(crl3);
    p11.add(crcbg1);
    p11.add(crcbg2);
    p11.add(crl4);
    p11.add(crarea);
    p11.add(crb1);
    p11.add(crb2);
    p11.add(crl5);
    
   
//Case_Review_Panel_End..........................................
    
//View_Cases_Panel_Start.........................................
    p12=new Panel();
    p12.setLayout(null);
    p12.setBounds(160,160,1180,768);
    p12.setBackground(new Color(255,217,74));
    p12.setVisible(false);
    
	CheckboxGroup vccbg=new CheckboxGroup();
    vccbg1=new Checkbox("Fir Id",vccbg,false);
    vccbg1.setBounds(30,20,70,20); 
    vccbg1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg1.addItemListener(this);
   	
    vccbg2=new Checkbox("Crime Type",vccbg,false);
    vccbg2.setBounds(130,20,100,20); 
    vccbg2.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg2.addItemListener(this);
    
   	
    vccbg3=new Checkbox("Status",vccbg,false);
    vccbg3.setBounds(240,20,62,20); 
    vccbg3.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg3.addItemListener(this);
    
   	
    vccbg4=new Checkbox("Victim Name",vccbg,false);
    vccbg4.setBounds(300,20,110,20); 
    vccbg4.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg4.addItemListener(this);
    
   	
    vccbg5=new Checkbox("Suspect Name",vccbg,false);
    vccbg5.setBounds(420,20,120,20); 
    vccbg5.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg5.addItemListener(this);
    
    vccbg6=new Checkbox("Inv Officer",vccbg,false);
    vccbg6.setBounds(550,20,100,20); 
    vccbg6.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,16));
    vccbg6.addItemListener(this);
    
    vcl1=new Label("-Select-",Label.CENTER);
    vcl1.setBounds(690,10,200,40);
    vcl1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,30));
    
    vctf1=new TextField();
    vctf1.setBounds(900,10,120,30);
    
    vcb1=new Button("Search");
    vcb1.setBounds(1040,10,120,30);
    vcb1.setFont(new Font("Serif",Font.BOLD | Font.ITALIC,10));
    vcb1.addActionListener(this);
	
    add(p12);
    p12.add(vccbg1);
    p12.add(vccbg2);
    p12.add(vccbg3);
    p12.add(vccbg4);
    p12.add(vccbg5);   
    p12.add(vccbg6);
    p12.add(vcl1);
    p12.add(vcb1);
    p12.add(vctf1);
    
    fillcasereviewtable();
//View_Cases_Panel_End.........................................    
	   
   setSize(1366,768);
   setLayout(null);
   setVisible(true);
  }

  
  void fillEmployeeTable()
  {
	  
	  String v1="",n1="",d1="",st1="",vt1="",s1="",m1="";
	  try{ 	
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
			Statement st=con.createStatement();
			System.out.println("Database Connection Established ");
			ResultSet rs=st.executeQuery("Select * from employee");
			
				while(rs.next())
				{
				v1=rs.getString("empid");
				n1=rs.getString("empname");
				d1=rs.getString("gender");
				st1=rs.getString("job");
				vt1=rs.getString("mobile");
				s1=rs.getString("emp_add");
				m1=rs.getString("city");
					
				Object[] row = {v1,n1,d1,st1,vt1,s1,m1};
					tableModel.addRow(row);
					
				}
				rs.close();
	            st.close();
	            con.close();
	            
				System.out.println("Jtable1.Data get Succesfully ");
			}catch(Exception e) {}
	  sp=new JScrollPane(jt);
	  sp.setBounds(0,80,1179,450);
	  p5.add(sp); 
	  
  }
  
  void fillfirtable()
  {
			String fir=" ",crimetype="",crimedesc="",crimedate="",crimetime="",crimeplace="",vname="",vmob="",vadd="",sname="",sadd="",smob="",io="";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery("Select\r\n"
						+ "    fir.*,\r\n"
						+ "    fir.firno As firno1,\r\n"
						+ "    fir.crimetype As crimetype1,\r\n"
						+ "    fir.crimedesc As crimedesc1,\r\n"
						+ "    fir.crimedate As crimedate1,\r\n"
						+ "    fir.crimetime As crimetime1,\r\n"
						+ "    fir.crimeplace As crimeplace1,\r\n"
						+ "    investigation.firno As firno2,\r\n"
						+ "    suspect.sname,\r\n"
						+ "    suspect.sadd,\r\n"
						+ "    suspect.smob,\r\n"
						+ "    victim.victimname,\r\n"
						+ "    victim.victimadd,\r\n"
						+ "    victim.victimmobno,\r\n"
						+ "    employee.empname\r\n"
						+ "From\r\n"
						+ "    investigation Inner Join\r\n"
						+ "    fir On investigation.firno = fir.firno Inner Join\r\n"
						+ "    suspect On fir.suspectid = suspect.suspectid Inner Join\r\n"
						+ "    victim On fir.victimid = victim.victimid Inner Join\r\n"
						+ "    employee On investigation.empid = employee.empid\r\n"
						+ "Group By\r\n"
						+ "    fir.firno");
			
				while(rs.next())
				{
					fir=rs.getString("firno");
					crimetype=rs.getString("crimetype");
					crimedesc=rs.getString("crimedesc");
					crimedate=rs.getString("crimedate");
					crimetime=rs.getString("crimetime");
					crimeplace=rs.getString("crimeplace");
					vname=rs.getString("victimname");
					vmob=rs.getString("victimmobno");
					vadd=rs.getString("victimadd");
					sname=rs.getString("sname");
					sadd=rs.getString("sadd");
					smob=rs.getString("smob");
					io=rs.getString("empname");
					
				Object[] row = {fir,crimetype,crimedesc,crimedate,crimetime,crimeplace,vname,vmob,vadd,sname,sadd,smob,io};
					tableModel1.addRow(row);
					
				}
				rs.close();
	            st.close();
	            con.close();
				System.out.println("Jtable1.Data get Succesfully ");		
			}catch(Exception e) {}
			 sp1=new JScrollPane(jt1);
			  sp1.setBounds(0,80,1179,450);
			  p7.add(sp1); 
  }
  
  void fillcasereviewtable()
  {
	  String columns2[]={"Fir Id","Crime Type","Crime Date","Victim Name","Suspect Name","Employee Name","Status"};  
	    tableModel2 = new DefaultTableModel(columns2,0);
		jt2=new JTable(tableModel2);
		jt2.setModel(tableModel2);
			String firid=" ",crimetype="",crimedate="",status="",vname="",sname="",io="";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery("Select\r\n"
						+ "    casereview.firid,\r\n"
						+ "    victim.victimname,\r\n"
						+ "    suspect.sname,\r\n"
						+ "    fir.crimetype,\r\n"
						+ "    fir.crimedate,\r\n"
						+ "    employee.empname,\r\n"
						+ "    casereview.status\r\n"
						+ "From\r\n"
						+ "    casereview Inner Join\r\n"
						+ "    fir On casereview.firid = fir.firno Inner Join\r\n"
						+ "    suspect On fir.suspectid = suspect.suspectid Inner Join\r\n"
						+ "    victim On fir.victimid = victim.victimid Inner Join\r\n"
						+ "    investigation On investigation.firno = fir.firno Inner Join\r\n"
						+ "    employee On investigation.empid = employee.empid");

				while(rs.next())
				{
					firid=rs.getString("firid");
					crimetype=rs.getString("crimetype");
					crimedate=rs.getString("crimedate");
					vname=rs.getString("victimname");
					sname=rs.getString("sname");
					io=rs.getString("empname");
					status=rs.getString("status");
					
				Object[] row2 = {firid,crimetype,crimedate,vname,sname,io,status};
					tableModel2.addRow(row2);
					
				}
				rs.close();
	            st.close();
	            con.close();
				System.out.println("Jtable1.Data get Succesfully ");		
			}catch(Exception e) {}
			 sp2=new JScrollPane(jt2);
			  sp2.setBounds(0,80,1179,450);
			  p12.add(sp2); 
  }
  
  void SearchEmployeeTable(String fname, String value)
  {
	  tableModel.setRowCount(0);
	  p5.remove(sp);
	  String v1="",n1="",d1="",st1="",vt1="",s1="",m1="";
	  
	  String query="Select * from employee where "+fname+" = '"+value+"'";
	  try{ 	
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
			Statement st=con.createStatement();
			System.out.println("Database Connection Established ");
			ResultSet rs=st.executeQuery(query);
			
				while(rs.next())
				{
				v1=rs.getString("empid");
				n1=rs.getString("empname");
				d1=rs.getString("gender");
				st1=rs.getString("job");
				vt1=rs.getString("mobile");
				s1=rs.getString("emp_add");
				m1=rs.getString("city");
					
				Object[] row = {v1,n1,d1,st1,vt1,s1,m1};
					tableModel.addRow(row);
					
				}
				rs.close();
	            st.close();
	            con.close();
	            
				System.out.println("Jtable1.Data get Succesfully ");
			}catch(Exception e) {}
	  sp=new JScrollPane(jt);
	  sp.setBounds(0,80,1179,450);
	  p5.add(sp); 
	  
  }
  
 
  void Searchfirtable(String fname, String value)
  {
	  tableModel1.setRowCount(0);
	  p7.remove(sp1);
			String fir=" ",crimetype="",crimedesc="",crimedate="",crimetime="",crimeplace="",vname="",vmob="",vadd="",sname="",sadd="",smob="",io="";
			 String query="Select\r\n"
			 		+ "    fir.firno,\r\n"
			 		+ "    fir.crimetype,\r\n"
			 		+ "    fir.crimedesc,\r\n"
			 		+ "    fir.crimedate,\r\n"
			 		+ "    fir.crimetime,\r\n"
			 		+ "    fir.crimeplace,\r\n"
			 		+ "    victim.victimname,\r\n"
			 		+ "    victim.victimadd,\r\n"
			 		+ "    victim.victimmobno,\r\n"
			 		+ "    suspect.sname,\r\n"
			 		+ "    suspect.sadd,\r\n"
			 		+ "    suspect.smob,\r\n"
			 		+ "    employee.empname\r\n"
			 		+ "From\r\n"
			 		+ "    employee Inner Join\r\n"
			 		+ "    investigation On investigation.empid = employee.empid Inner Join\r\n"
			 		+ "    fir On investigation.firno = fir.firno Inner Join\r\n"
			 		+ "    suspect On fir.suspectid = suspect.suspectid Inner Join\r\n"
			 		+ "    victim On fir.victimid = victim.victimid\r\n"
			 		+ "Where"+" "+fname+" = '"+value+"'";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery(query);
				
				
				while(rs.next())
				{
					fir=rs.getString("fir.firno");
					crimetype=rs.getString("fir.crimetype");
					crimedesc=rs.getString("fir.crimedesc");
					crimedate=rs.getString("fir.crimedate");
					crimetime=rs.getString("fir.crimetime");
					crimeplace=rs.getString("fir.crimeplace");
					vname=rs.getString("victim.victimname");
					vmob=rs.getString("victim.victimmobno");
					vadd=rs.getString("victim.victimadd");
					sname=rs.getString("suspect.sname");
					sadd=rs.getString("suspect.sadd");
					smob=rs.getString("suspect.smob");
					io=rs.getString("employee.empname");
					
				Object[] row = {fir,crimetype,crimedesc,crimedate,crimetime,crimeplace,vname,vmob,vadd,sname,sadd,smob,io};
					tableModel1.addRow(row);
					
				}
				rs.close();
	            st.close();
	            con.close();
				System.out.println("Jtable1.Data get Succesfully ");		
			}catch(Exception e) {}
			 sp1=new JScrollPane(jt1);
			  sp1.setBounds(0,80,1179,450);
			  p7.add(sp1); 
  }
  
  void searchcasereviewtable(String fname, String value)
  {
		  tableModel2.setRowCount(0);
		  p12.remove(sp2);
			String firid=" ",crimetype="",crimedate="",status="",vname="",sname="",io="";
			String query1="Select\r\n"
					+ "    casereview.firid,\r\n"
					+ "    fir.crimetype,\r\n"
					+ "    fir.crimedate,\r\n"
					+ "    victim.victimname,\r\n"
					+ "    suspect.sname,\r\n"
					+ "    employee.empname,\r\n"
					+ "    casereview.status\r\n"
					+ "From\r\n"
					+ "    employee Inner Join\r\n"
					+ "    investigation On investigation.empid = employee.empid Inner Join\r\n"
					+ "    fir On investigation.firno = fir.firno Inner Join\r\n"
					+ "    suspect On fir.suspectid = suspect.suspectid Inner Join\r\n"
					+ "    victim On fir.victimid = victim.victimid Inner Join\r\n"
					+ "    casereview On casereview.firid = fir.firno\r\n"
					+ "Where"+" "+fname+" = '"+value+"'";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery(query1);
						

				while(rs.next())
				{
					firid=rs.getString("casereview.firid");
					crimetype=rs.getString("fir.crimetype");
					crimedate=rs.getString("fir.crimedate");
					vname=rs.getString("victim.victimname");
					sname=rs.getString("suspect.sname");
					io=rs.getString("employee.empname");
					status=rs.getString("casereview.status");
					
				Object[] row2 = {firid,crimetype,crimedate,vname,sname,io,status};
					tableModel2.addRow(row2);
					
				}
				rs.close();
	            st.close();
	            con.close();
				System.out.println("Jtable2.Data get Succesfully ");		
			}catch(Exception e) {}
			 sp2=new JScrollPane(jt2);
			  sp2.setBounds(0,80,1179,450);
			  p12.add(sp2); 
  }
 
  
 
 
  /*List_Control__Method_Case_Review_Table_START*/
  void add_items_in_list()
  {
   crli1.removeAll();	
   String str="";
	  try{ 	
		  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select concat(fir.firno,'/',fir.crimetype,'/',employee.empid,'/',employee.empname) from employee Inner Join investigation On investigation.empid=employee.empid Inner Join fir On investigation.firno=fir.firno where employee.empid="+empid[index]);
			
				while(rs.next())
				{
				str=rs.getString("concat(fir.firno,'/',fir.crimetype,'/',employee.empid,'/',employee.empname)");
				crli1.add(str);	
				}
				
				rs.close();
	            st.close();
	            con.close();
			}catch(Exception e) {}
  }
  /*List_Control_Method_Case_Review_Table_END*/
  
  void Count_Total_Cases()
  {
	   String s="",s1="",s2="",s3="",s4="",s5="",s6="",s7="";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				Statement st1=con.createStatement();
				Statement st2=con.createStatement();
				Statement st3=con.createStatement();
				Statement st4=con.createStatement();
				Statement st5=con.createStatement();
				Statement st6=con.createStatement();
				Statement st7=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Murder'");
				ResultSet rs1=st1.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Molestation'");
				ResultSet rs2=st2.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Theft'");
				ResultSet rs3=st3.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Fraud'");
				ResultSet rs4=st4.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Rape'");
				ResultSet rs5=st5.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Terriorst Act'");
				ResultSet rs6=st6.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Narcotics Act'");
				ResultSet rs7=st7.executeQuery("Select\r\n"
						+ "    Count(fir.crimetype)\r\n"
						+ "From\r\n"
						+ "    fir\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Anti National Act'");
				while(rs.next())
				s=rs.getString("Count(fir.crimetype)");
				
				while(rs1.next())
					s1=rs1.getString("Count(fir.crimetype)");
				
				while(rs2.next())
					s2=rs2.getString("Count(fir.crimetype)");
				
				while(rs3.next())
					s3=rs3.getString("Count(fir.crimetype)");
				
				while(rs4.next())
					s4=rs4.getString("Count(fir.crimetype)");
				
				while(rs5.next())
					s5=rs5.getString("Count(fir.crimetype)");
				
				while(rs6.next())
					s6=rs6.getString("Count(fir.crimetype)");
				
				while(rs7.next())
					s7=rs7.getString("Count(fir.crimetype)");
				
				  dpt11.setText(s);
				  dpt21.setText(s1);
				  dpt31.setText(s2);
				  dpt41.setText(s3);
				  dpt51.setText(s4);
				  dpt61.setText(s5);
				  dpt71.setText(s6);
				  dpt81.setText(s7);
				
				rs.close();
				rs1.close();
				rs2.close();
				rs3.close();
				rs4.close();
				rs5.close();
				rs6.close();
				rs7.close();
	            st.close();
	            st1.close();
	            st2.close();
	            st3.close();
	            st4.close();
	            st5.close();
	            st6.close();
	            st7.close();
	            con.close();	
			}catch(Exception e) {}
	 
  }
  
  void Count_Solved_Cases()
  {
	   String s="",s1="",s2="",s3="",s4="",s5="",s6="",s7="";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				Statement st1=con.createStatement();
				Statement st2=con.createStatement();
				Statement st3=con.createStatement();
				Statement st4=con.createStatement();
				Statement st5=con.createStatement();
				Statement st6=con.createStatement();
				Statement st7=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Murder' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs1=st1.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Molestation' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs2=st2.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Theft' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs3=st3.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Fraud' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs4=st4.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Rape' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs5=st5.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Terriorst Act' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs6=st6.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Narcotics Act' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				ResultSet rs7=st7.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Anti National Act' And\r\n"
						+ "    casereview.status = 'SOLVED'");
				while(rs.next())
				s=rs.getString("Count(casereview.status)");
				
				while(rs1.next())
					s1=rs1.getString("Count(casereview.status)");
				
				while(rs2.next())
					s2=rs2.getString("Count(casereview.status)");
				
				while(rs3.next())
					s3=rs3.getString("Count(casereview.status)");
				
				while(rs4.next())
					s4=rs4.getString("Count(casereview.status)");
				
				while(rs5.next())
					s5=rs5.getString("Count(casereview.status)");
				
				while(rs6.next())
					s6=rs6.getString("Count(casereview.status)");
				
				while(rs7.next())
					s7=rs7.getString("Count(casereview.status)");
				
				  dpt12.setText(s);
				  dpt22.setText(s1);
				  dpt32.setText(s2);
				  dpt42.setText(s3);
				  dpt52.setText(s4);
				  dpt62.setText(s5);
				  dpt72.setText(s6);
				  dpt82.setText(s7);
				
				rs.close();
				rs1.close();
				rs2.close();
				rs3.close();
				rs4.close();
				rs5.close();
				rs6.close();
				rs7.close();
	            st.close();
	            st1.close();
	            st2.close();
	            st3.close();
	            st4.close();
	            st5.close();
	            st6.close();
	            st7.close();
	            con.close();	
			}catch(Exception e) {}
	 
  }
  
 void Count_Pending_Cases() 
 {
	   String s="",s1="",s2="",s3="",s4="",s5="",s6="",s7="";
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/crimelodger","root","1234");  
				Statement st=con.createStatement();
				Statement st1=con.createStatement();
				Statement st2=con.createStatement();
				Statement st3=con.createStatement();
				Statement st4=con.createStatement();
				Statement st5=con.createStatement();
				Statement st6=con.createStatement();
				Statement st7=con.createStatement();
				System.out.println("Database Connection Established ");
				ResultSet rs=st.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Murder' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs1=st1.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Molestation' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs2=st2.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Theft' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs3=st3.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Fraud' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs4=st4.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Rape' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs5=st5.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Terriorst Act' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs6=st6.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Narcotics Act' And\r\n"
						+ "    casereview.status = 'PENDING'");
				ResultSet rs7=st7.executeQuery("Select\r\n"
						+ "    Count(casereview.status)\r\n"
						+ "From\r\n"
						+ "    fir Inner Join\r\n"
						+ "    casereview On casereview.firid = fir.firno\r\n"
						+ "Where\r\n"
						+ "    fir.crimetype = 'Anti National Act' And\r\n"
						+ "    casereview.status = 'PENDING'");
				while(rs.next())
				s=rs.getString("Count(casereview.status)");
				
				while(rs1.next())
					s1=rs1.getString("Count(casereview.status)");
				
				while(rs2.next())
					s2=rs2.getString("Count(casereview.status)");
				
				while(rs3.next())
					s3=rs3.getString("Count(casereview.status)");
				
				while(rs4.next())
					s4=rs4.getString("Count(casereview.status)");
				
				while(rs5.next())
					s5=rs5.getString("Count(casereview.status)");
				
				while(rs6.next())
					s6=rs6.getString("Count(casereview.status)");
				
				while(rs7.next())
					s7=rs7.getString("Count(casereview.status)");
				
				  dpt13.setText(s);
				  dpt23.setText(s1);
				  dpt33.setText(s2);
				  dpt43.setText(s3);
				  dpt53.setText(s4);
				  dpt63.setText(s5);
				  dpt73.setText(s6);
				  dpt83.setText(s7);
				
				rs.close();
				rs1.close();
				rs2.close();
				rs3.close();
				rs4.close();
				rs5.close();
				rs6.close();
				rs7.close();
	            st.close();
	            st1.close();
	            st2.close();
	            st3.close();
	            st4.close();
	            st5.close();
	            st6.close();
	            st7.close();
	            con.close();	
			}catch(Exception e) {}
 }
public void actionPerformed(ActionEvent ae)
{
	
//........EMPLOYEE_FORM_START...............................
 	if(ae.getSource()==eb1)
 	{
 		insert_into_employee_table_from_employee_form();	
 		//RefillEmployeeTable();
 	}
 	
 	if(ae.getSource()==eb2)
 	{
 		etf1.setText("");
		etf2.setText("");
	    ec1.select(0);
		etf3.setText("");
        ta1.setText("");
		ec2.select(0);
 	}
	
//........EMPLOYEE_FORM_END.................................
 	
//........FIR_FORM_START................................................................
 	if(ae.getSource()==fb1)
 	{
 		insert_into_fir_table_from_fir_form();
 	}
 	if(ae.getSource()==fb2)
 	{
 		ftf1.setText("");
		fc1.select(0);
		fc2.select(0);
		fta1.setText("");
		ftf6.setText("");
		ftf9.setText("");
		ftf10.setText("");
		ftf2.setText("");
	    ftf7.setText("");
	    ftf8.setText("");
		ftf3.setText("");
		ftf4.setText("");
		ftf5.setText("");
 		
 	}
//........FIR_FORM_END..................................................................
	
//SIDE/MENU__PANEL__BUTTONS__START.............................................
	if(ae.getSource()==b1)
	{
		p3.setVisible(true);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(false);
		p11.setVisible(false);
		p12.setVisible(false);
	}
	if(ae.getSource()==b2)
	{
		p3.setVisible(false);
		p4.setVisible(true);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(false);
		p11.setVisible(false);
		p12.setVisible(false);
	}
	if(ae.getSource()==b3)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(true);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(false);
		p11.setVisible(false);
		p12.setVisible(false);
	}
	if(ae.getSource()==b4)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(true);
		p7.setVisible(false);
		p10.setVisible(false);
		p11.setVisible(false);
		p12.setVisible(false);
	}
    if(ae.getSource()==b5)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(true);
		p10.setVisible(false);
		p11.setVisible(false);
		p12.setVisible(false);
	}
	if(ae.getSource()==b8)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(true);
		p11.setVisible(false);
		p12.setVisible(false);
	}
	if(ae.getSource()==b9)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(false);
	    p11.setVisible(true);
	    p12.setVisible(false);
	}
	if(ae.getSource()==b10)
	{
		p3.setVisible(false);
		p4.setVisible(false);
		p5.setVisible(false);
		p6.setVisible(false);
		p7.setVisible(false);
		p10.setVisible(false);
	    p11.setVisible(false);
	    p12.setVisible(true);
	}
//SIDE/MENU__PANEL__BUTTONS__END.............................................
	
//view_All_Employee_Form_start....................................................
	
	if(ae.getSource()==ab1)
	{
		if(acbg1.getState()==true)
		{
			alcbg=atf1.getText();
			SearchEmployeeTable("empname",alcbg);
		}
		if(acbg2.getState()==true)
		{
			alcbg=atf1.getText();
			SearchEmployeeTable("city",alcbg);
		}
		if(acbg3.getState()==true)
		{
			alcbg=atf1.getText();
			SearchEmployeeTable("mobile",alcbg);
		}
		if(acbg4.getState()==true)
		{
			alcbg=atf1.getText();
			SearchEmployeeTable("empid",alcbg);
		}
		if(acbg5.getState()==true)
		{
			alcbg=atf1.getText();
			SearchEmployeeTable("job",alcbg);
		}
	}	
	
	
//view_All_Employee_Form_End......................................................
	
//View FIR Form Start.............................................................
	if(ae.getSource()==vfb1)
	{
		if(vfcbg1.getState()==true)
		{
			vfcbg=vftf1.getText();
			Searchfirtable("fir.firno",vfcbg);
		}
		if(vfcbg2.getState()==true)
		{
			vfcbg=vftf1.getText();
			Searchfirtable("fir.crimetype",vfcbg);
		}
		if(vfcbg3.getState()==true)
		{
			vfcbg=vftf1.getText();
			Searchfirtable("fir.crimeplace",vfcbg);
		}
		if(vfcbg4.getState()==true)
		{
			vfcbg=vftf1.getText();
			Searchfirtable("victim.victimname",vfcbg);
		}
		if(vfcbg5.getState()==true)
		{
			vfcbg=vftf1.getText();
			Searchfirtable("employee.empname",vfcbg);
		}
	}	
	
//View FIR Form End...............................................................	
	
	
//Settings_Panel_Start............................................................
	if(ae.getSource()==sb1)
	{
		Color initialcolor=Color.WHITE;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);
		setBackground(color); 
	}
	if(ae.getSource()==sb2)
	{
		Color initialcolor=Color.WHITE;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);
		l.setBackground(color); 
	}
	if(ae.getSource()==sb3)
	{
		Color initialcolor=Color.WHITE;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);    
		b1.setBackground(color); 
		b2.setBackground(color); 
		b3.setBackground(color); 
		b4.setBackground(color); 
		b5.setBackground(color); 
		b8.setBackground(color); 
		b9.setBackground(color);   
		b10.setBackground(color);  
		eb1.setBackground(color);  
		eb2.setBackground(color);  
		fb1.setBackground(color); 
		fb2.setBackground(color); 
		sb1.setBackground(color); 
		sb2.setBackground(color); 
		sb3.setBackground(color); 
		sb4.setBackground(color); 
		sb5.setBackground(color);  
		sb6.setBackground(color); 
		crb1.setBackground(color);
		crb2.setBackground(color);
		vcb1.setBackground(color);
		ab1.setBackground(color);
		vfb1.setBackground(color);
	}
	if(ae.getSource()==sb4)
	{
		Color initialcolor=Color.BLACK;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);    
		l1.setBackground(color); 
		l2.setBackground(color); 
		el1.setBackground(color); 
		el2.setBackground(color); 		
		el3.setBackground(color); 
		el4.setBackground(color); 
		el5.setBackground(color); 
		el6.setBackground(color); 
		el7.setBackground(color);   
		el8.setBackground(color);   
		fl1.setBackground(color); 
		fl2.setBackground(color); 
		fl3.setBackground(color); 
		fl4.setBackground(color); 
		fl5.setBackground(color); 
		fl6.setBackground(color); 
		fl7.setBackground(color);  
		fl8.setBackground(color); 
		fl9.setBackground(color);
		fl10.setBackground(color);
		fl11.setBackground(color);
		dpl1.setBackground(color);
		dpl2.setBackground(color);
		dpl3.setBackground(color);
		dpl4.setBackground(color);
		dpl5.setBackground(color);
		dpl6.setBackground(color);
		dpl7.setBackground(color);
		dpl8.setBackground(color);
		dpl11.setBackground(color);
		dpl12.setBackground(color);
		dpl13.setBackground(color);
		dpl21.setBackground(color);
		dpl22.setBackground(color);
		dpl23.setBackground(color);
		dpl31.setBackground(color);
		dpl32.setBackground(color);
		dpl33.setBackground(color);
		dpl41.setBackground(color);
		dpl42.setBackground(color);
		dpl43.setBackground(color);
		dpl51.setBackground(color);
		dpl52.setBackground(color);
		dpl53.setBackground(color);
		dpl61.setBackground(color);
		dpl62.setBackground(color);
		dpl63.setBackground(color);
		dpl71.setBackground(color);
		dpl72.setBackground(color);
		dpl73.setBackground(color);
		dpl81.setBackground(color);
		dpl82.setBackground(color);
		dpl83.setBackground(color);
		al1.setBackground(color);
		vfl1.setBackground(color);
		dl1.setBackground(color);
		crl1.setBackground(color);
		crl2.setBackground(color);
		crl3.setBackground(color);
		crl4.setBackground(color);
		crl5.setBackground(color);
		vcl1.setBackground(color);
	}
	if(ae.getSource()==sb5)
	{
		Color initialcolor=Color.WHITE;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);    
		 crli1.setBackground(color); 
		 ftf1.setBackground(color); 
		 ftf2.setBackground(color); 
		 ftf3.setBackground(color); 
		 ftf4.setBackground(color); 
		 ftf5.setBackground(color); 
		 ftf6.setBackground(color);   
		 ftf7.setBackground(color);  
		 ftf8.setBackground(color);  
		 ftf9.setBackground(color);  
		 ftf10.setBackground(color); 
		 fta1.setBackground(color); 
		 etf1.setBackground(color); 
		 etf2.setBackground(color); 
		 etf3.setBackground(color); 
		 atf1.setBackground(color); 
		 vftf1.setBackground(color); 
		 dpt11.setBackground(color); 
		 dpt12.setBackground(color); 
		 dpt13.setBackground(color); 
		 dpt21.setBackground(color); 
		 dpt22.setBackground(color); 
		 dpt23.setBackground(color); 
		 dpt31.setBackground(color); 
		 dpt32.setBackground(color); 
		 dpt33.setBackground(color); 
		 dpt41.setBackground(color); 
		 dpt42.setBackground(color); 
		 dpt43.setBackground(color); 
		 dpt51.setBackground(color); 
		 dpt52.setBackground(color); 
		 dpt53.setBackground(color); 
		 dpt61.setBackground(color); 
		 dpt62.setBackground(color); 
		 dpt63.setBackground(color); 
		 dpt71.setBackground(color); 
		 dpt72.setBackground(color); 
		 dpt73 .setBackground(color); 
		 dpt81.setBackground(color); 
		 dpt82.setBackground(color); 
		 dpt83.setBackground(color); 
		 vctf1.setBackground(color); 
		 crarea.setBackground(color); 
		 ecbg1.setBackground(color); 
		 ecbg2.setBackground(color); 
		 acbg1.setBackground(color); 
		 acbg2.setBackground(color); 
		 acbg3.setBackground(color);
		 acbg4.setBackground(color); 
		 acbg5.setBackground(color); 
		 vfcbg1.setBackground(color); 
		 vfcbg2.setBackground(color); 
		 vfcbg3.setBackground(color); 
		 vfcbg4.setBackground(color); 
		 vfcbg5.setBackground(color); 
		 crcbg1.setBackground(color); 
		 crcbg2.setBackground(color); 
		 vccbg1.setBackground(color); 
		 vccbg2.setBackground(color); 
		 vccbg3.setBackground(color); 
		 vccbg4.setBackground(color); 
		 vccbg5.setBackground(color); 
		 vccbg6.setBackground(color); 
		 ec1.setBackground(color); 
		 ec2.setBackground(color); 
		 fc1.setBackground(color); 
		 fc2.setBackground(color); 
		 crc1.setBackground(color); 
		 ta1.setBackground(color); 
	}
	if(ae.getSource()==sb6)
	{
		Color initialcolor=Color.WHITE;    
		Color color=JColorChooser.showDialog(this,"color",initialcolor);    
		p1.setBackground(color); 
		p2.setBackground(color); 
		p3.setBackground(color); 
		p4.setBackground(color); 
		p5.setBackground(color); 
		p6.setBackground(color); 
		p7.setBackground(color);   
		p10.setBackground(color);  
		p11.setBackground(color);  
		p12.setBackground(color);  
		dp1.setBackground(color); 
		dp2.setBackground(color); 
		dp3.setBackground(color); 
		dp4.setBackground(color); 
		dp5.setBackground(color); 
		dp6.setBackground(color); 
		dp7.setBackground(color);  
		dp8.setBackground(color); 
	}
//Settings_Panel_End..............................................................
	
//caseReview_Panel_Start.........................................................
	if(ae.getSource()==crb1)
	insert_into_casereview_table();
	if(ae.getSource()==crb2)
	{
		crarea.setText("");
	    crc1.select(0);
	    crli1.removeAll();
	}
//CaseReview_Panel_End...........................................................
	
	//View_Cases_Panel_Start.........................................................
	if(ae.getSource()==vcb1)
	{
		if(vccbg1.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("casereview.firid",vctf);
		}
		if(vccbg2.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("fir.crimetype",vctf);
		}
		if(vccbg3.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("casereview.status",vctf);
		}
		if(vccbg4.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("victim.victimname",vctf);
		}
		if(vccbg5.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("suspect.sname",vctf);
		}
		if(vccbg6.getState()==true)
		{
			vctf=vctf1.getText();
			searchcasereviewtable("employee.empname",vctf);
		}
	}	
	//View_Cases_Panel_End...........................................................
}

public void itemStateChanged(ItemEvent ie)
{
	//fir_Form_Start........................
	 if(ie.getSource()==fc2)
	 index=fc2.getSelectedIndex();
	//fir_Form_End..........................
	 
	 //case_review_Start....................
	 if(ie.getSource()==crc1)
	 {
		 index=crc1.getSelectedIndex();
		 add_items_in_list(); 
	 }
	 //case_review_End.....................
	
	//view_All_Employee_start....................................................
		if(acbg1.getState()==true)
		{
			alcbg=acbg1.getLabel();
			al1.setText(alcbg);
		}
		if(acbg2.getState()==true)
		{
			alcbg=acbg2.getLabel();
			al1.setText(alcbg);
		}
		if(acbg3.getState()==true)
		{
			alcbg=acbg3.getLabel();
			al1.setText(alcbg);
		}
		if(acbg4.getState()==true)
		{
			alcbg=acbg4.getLabel();
			al1.setText(alcbg);
		}
		if(acbg5.getState()==true)
		{
			alcbg=acbg5.getLabel();
			al1.setText(alcbg);
		}

	//view_All_Employee_End.............................................

	//view_Fir_start....................................................
				if(vfcbg1.getState()==true)
				{
					vfcbg=vfcbg1.getLabel();
					vfl1.setText(vfcbg);
				}
				if(vfcbg2.getState()==true)
				{
					vfcbg=vfcbg2.getLabel();
					vfl1.setText(vfcbg);
				}
				if(vfcbg3.getState()==true)
				{
					vfcbg=vfcbg3.getLabel();
					vfl1.setText(vfcbg);
				}
				if(vfcbg4.getState()==true)
				{
					vfcbg=vfcbg4.getLabel();
					vfl1.setText(vfcbg);
				}
				if(vfcbg5.getState()==true)
				{
					vfcbg=vfcbg5.getLabel();
					vfl1.setText(vfcbg);
				}

	//view_Fir_End......................................................

  //view_cases_Panel_start..............................................
				if(vccbg1.getState()==true)
				{
					vccbg=vccbg1.getLabel();
					vcl1.setText(vccbg);
				}
				if(vccbg2.getState()==true)
				{
					vccbg=vccbg2.getLabel();
					vcl1.setText(vccbg);
				}
				if(vccbg3.getState()==true)
				{
					vccbg=vccbg3.getLabel();
					vcl1.setText(vccbg);
				}
				if(vccbg4.getState()==true)
				{
					vccbg=vccbg4.getLabel();
					vcl1.setText(vccbg);
				}
				if(vccbg5.getState()==true)
				{
					vccbg=vccbg5.getLabel();
					vcl1.setText(vccbg);
				}
				if(vccbg6.getState()==true)
				{
					vccbg=vccbg6.getLabel();
					vcl1.setText(vccbg);
				}
  //view_Cases_Panel_End................................................
}
public static void main(String [] args)
{
  new  crime_lodger();
 
}

}