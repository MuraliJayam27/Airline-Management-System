//STEP 1. Import required packages

import java.sql.*;
import java.util.Scanner;

public class JdbcDemo 
{

   //Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   //static final String DB_URL = "jdbc:mysql://localhost/companydb";
   static final String DB_URL = "jdbc:mysql://localhost/Aviation?useSSL=false";
   //Database credentials
   static final String USER = "root";// add your user 
   static final String PASS = "Murali-Jayam27";// add password
   
   public static void main(String[] args) 
   {
      Connection conn = null;
      Statement stmt = null;


   // STEP 2. Connecting to the Database
      try
      {
         //STEP 2a: Register JDBC driver
         Class.forName(JDBC_DRIVER);
         //STEP 2b: Open a connection
         System.out.println("Connecting to Database...");
         conn = DriverManager.getConnection(DB_URL,USER,PASS);
         //STEP 2c: Execute a query
         System.out.println("Creating statement...");
         stmt = conn.createStatement();

         Scanner Scan = new Scanner(System.in);
         Scan.useDelimiter("\n");

         System.out.println("\n*********************************");
         System.out.println("Hello, Welcome Back");

         String Airline_id = Admin_login(stmt,Scan);
         Main_Menu(stmt,Scan);
      }
      catch(SQLException se)
      {    	 //Handle errors for JDBC
         se.printStackTrace();
      }
      catch(Exception e)
      {        	//Handle errors for Class.forName
         e.printStackTrace();
      }
      finally
      {				//finally block used to close resources
         try
         {
            if(stmt!=null)
               stmt.close();
         }
         catch(SQLException se2)
         {
         }
         try
         {
            if(conn!=null)
               conn.close();
         }
         catch(SQLException se)
         {
            se.printStackTrace();
         }					//end finally try
      }					//end try
      System.out.println("*********************************");
}					//end main
   public static String Admin_login(Statement stmt, Scanner Scan)
   {
      try
      {
         String sql="select * from Airlines";  
         while(true)
         {
            ResultSet rs=stmt.executeQuery(sql);
            System.out.println("\nEnter your Airline ID to Enter the Database:");
            System.out.print("=> ");
            String id=Scan.next();
            while(rs.next())
            {
               if(rs.getString("Airline_ID").equals(id))
                  return rs.getString("Airline_ID");
            }
            System.out.println("Enter a valid Airline ID to Enter the Database....");
         }
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      return "";
   }
   public static void Main_Menu(Statement stmt,Scanner Scan)
   {
      
         while(true)
         {
            System.out.println("Select one of the option.");
            System.out.println("PRESS '0' TO EXIT.");
            System.out.println("1. To View the Flight Details");
            System.out.println("2. To Add New Flight Details");
            System.out.println("3. To Update Flight Details");
            System.out.println("4. To Delete a Flight Route");
            System.out.print("=> ");

            int c = Scan.nextInt();
            switch(c)
            {
               case 0:
                  return ;
               case 1:
                  view(stmt,Scan);
                  break;
               case 2:
                  add(stmt,Scan);
                  break;
               case 3:
                  update(stmt,Scan);
                  break;
               case 4:
                  remove(stmt,Scan);
                  break;
            }
         }
   }
   public static void view(Statement stmt,Scanner Scan)
   {
      try
      {
         String sql, Rot;     //Rot = Route
         System.out.println("\nEnter the Route: ");
         Rot = Scan.next();

         sql = "SELECT * FROM Flight_Description WHERE Route = '"+ Rot +"'";
         ResultSet rs1 = stmt.executeQuery(sql);

         if (rs1.next())
         {
            sql = "select Flight_Number, Route, Registration, Distance, Departure_Time from Flight_Description where Route = '"+ Rot +"'";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while(rs.next())
            {
               //Retrieve by column name
               String FNo  = rs.getString("Flight_Number");    //Flight_Number
               String Rt = rs.getString("Route");              //Route
               String Reg = rs.getString("Registration");      //Registration
               String Dis = rs.getString("Distance");          //Distance
               String DT = rs.getString("Departure_Time");      //Departure_Time 

               //Display values
               System.out.println("~> Flight_Number: " + FNo);
               System.out.println("~> Route: " + Rt);
               System.out.println("~> Registration: " + Reg);
               System.out.println("~> Distance: " + Dis);
               System.out.println("~> Departure_Time: " + DT);
               System.out.println("\n*********************************");
               System.out.print("\n");
            }

               //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            
         }
         else
         {
            System.out.println("Route Does Not Exist.");
         } 
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      return ;
   }
   public static void add(Statement stmt,Scanner Scan)
   {
      try
      {
         String sql, FNo, Reg, Rt, Dis, DT, AT;     

               System.out.println("\nEnter the Details of New Route.\n");
               System.out.println("Enter the Flight Number: ");
               System.out.print("=> ");
               FNo = Scan.next();
               System.out.println("Enter the Flight Registration: ");
               System.out.print("=> ");
               Reg = Scan.next();
               System.out.println("Enter the Flight Route: ");
               System.out.print("=> ");
               Rt = Scan.next();
               System.out.println("Enter the Route Distance: ");
               System.out.print("=> ");
               Dis = Scan.next();
               System.out.println("Enter the Flight Departure Time: ");
               System.out.print("=> ");
               DT = Scan.next();
               System.out.println("Enter the Flight Arrival_Time: ");
               System.out.print("=> ");
               AT = Scan.next();

               sql = "INSERT INTO Flight_Description(Flight_Number, Registration, Route, Distance, Departure_Time, Arrival_Time) VALUES('"+ FNo +"', '"+ Reg +"', '"+ Rt +"', '"+ Dis +"', '"+ DT +"', '"+ AT +"')";
               int rs = stmt.executeUpdate(sql);

               if (rs == 1)
               {
                  System.out.println("Details Added Successfully.");
                  System.out.println("\n*********************************");
                  System.out.print("\n");
               }
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      return ;
   }
   public static void update(Statement stmt,Scanner Scan)
   {
      try{
         String sql, FN, Dis;     
         System.out.println("\nEnter the Flight Number to Update: ");
         System.out.print("=> ");
         FN = Scan.next();

         sql = "SELECT * FROM Flight_Description WHERE Flight_NUmber = '"+ FN +"'";
         ResultSet rs = stmt.executeQuery(sql);

         if(rs.next())
         {
            System.out.println("Enter the Route Distance to be Updated.");
            System.out.print("=> ");
            Dis = Scan.next();
            sql = "UPDATE Flight_Description set Distance = '"+ Dis +"' WHERE Flight_Number = '"+ FN +"'";
            int rs1 = stmt.executeUpdate(sql);

            if(rs1 == 1)
            {
               System.out.println("Details Updated Successfully.");
               System.out.println("\n*********************************");
               System.out.print("\n");
            }
         }
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      return ;
   }
   public static void remove(Statement stmt,Scanner Scan)
   {
      try{
         String sql, FN, Dis;     
               System.out.println("\nEnter the Flight_Number to Delete Route: ");
               System.out.print("=> ");
               FN = Scan.next();

               sql = "SELECT * FROM Flight_Description WHERE Flight_NUmber = '"+ FN +"'";
               ResultSet rs = stmt.executeQuery(sql);

               if(rs.next())
               {
                  sql = "DELETE FROM Flight_Description WHERE Flight_Number = '"+ FN +"'";
                  int rs1 = stmt.executeUpdate(sql);

                  if(rs1 == 1)
                  {
                     System.out.println("Details Deleted Successfully.");
                     System.out.println("\n*********************************");
                     System.out.print("\n");
                  }
               }
      }
      catch(SQLException se)
      {
         se.printStackTrace();
      }
      return ;
   }

}					//end class
//Note : By default autocommit is on. you can set to false using con.setAutoCommit(false)