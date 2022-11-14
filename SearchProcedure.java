import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Calendar;  
// import java.util.Scanner;
import java.io.IOException;
// import java.util.Scanner;
// import java.lang.Thread.State;

public class SearchProcedure {

    public static String journeySearcher(Connection con,String startStation, String endStation) throws SQLException, ParseException
    {
        String journeyPlanner = "";
        int option = 0;
        String query1 = "Select * from station where name = '" + startStation + "';";
        //System.out.println(query1);
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery(query1);

        while(rs1.next()){
            int uid = rs1.getInt("uid");
            String doj= rs1.getString("doj");
            String arr_time = rs1.getString("Arrival_time");
            String dept_time = rs1.getString("Departure_time");
            int start_stn_order = rs1.getInt("order_of_station"); 
            
            String query2 = "Select * from station where name = '"+ endStation + "' and uid = " + uid + " and doj = '" + doj + "';";
        //     System.out.println(query2);
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(query2); 
            
            if(rs2.next()){
                //System.out.println("================== DIRECT TRAINS =================");
                int end_stn_order = rs2.getInt("order_of_station");

                if(start_stn_order<end_stn_order){
                    option++;
                    journeyPlanner+= "Option : "+ option+"\n\tTrain-uid : "+uid + " Date-of-journey : " + doj + " Arrival Time : " + arr_time + " Deparature Time : " + dept_time+"\n"; 
                }
            }
            else{
                //System.out.println("================== NON DIRECT TRAINS =================");
                int order_to_check = start_stn_order+1;

                while(true){
                
                    String query3 = "Select * from station where order_of_station = "+ order_to_check + " and uid = " + uid + " and doj = '" + doj + "';";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(query3);
                //     System.out.println(query3);

                    if(rs3.next()){
                        
                        String imm_stn = rs3.getString("name");
                        String arr_time_imm = rs3.getString("Arrival_time");
                        String doj_rs3 = rs3.getString("doj");
                        int imm_arr_offset = rs3.getInt("off_set");
                    
                        Statement st4 = con.createStatement();
                        String query4 = "Select * from station where name = '" + imm_stn + "';";
                        // System.out.println(query4);
                        ResultSet rs4 = st4.executeQuery(query4);
                        
                        while(rs4.next()){
                            
                            int uid_rs4 = rs4.getInt("uid");
                            String doj_rs4= rs4.getString("doj");
                            String arr_time_imm_rs4 = rs4.getString("Arrival_time");
                            String dept_time_imm_rs4 = rs4.getString("Departure_time");
                            int start_stn_order_rs4 = rs4.getInt("order_of_station"); 
                            int imm_dept_offset = rs4.getInt("off_set");
                            String imm_station = rs4.getString("name") ; 

                            String query5 = "Select * from station where name = '"+ endStation + "' and uid = " + uid_rs4 + " and doj = '" + doj_rs4 + "' ;";
                        //     System.out.println(query5);
                            Statement st5 = con.createStatement();
                            ResultSet rs5 = st5.executeQuery(query5);

                            if(rs5.next()){
                                
                                int end_stn_order_rs5 = rs5.getInt("order_of_station"); 
                                
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                SimpleDateFormat dojSDF = new SimpleDateFormat("yyyy-MM-dd");
                                // Date date = null;

                                java.util.Date arr_day = dojSDF.parse(doj_rs3);
                                java.util.Date dept_day = dojSDF.parse(doj_rs4);

                                Calendar cal1 = Calendar.getInstance();
                                Calendar cal2 = Calendar.getInstance();
                                cal1.setTime(arr_day);
                                cal2.setTime(dept_day);
                                cal1.add(Calendar.DAY_OF_MONTH,imm_arr_offset);
                                cal2.add(Calendar.DAY_OF_MONTH,imm_dept_offset);

                                arr_day = dojSDF.parse(dojSDF.format(cal1.getTime()));
                                dept_day = dojSDF.parse(dojSDF.format(cal2.getTime()));

                                java.util.Date arr_time_d =  sdf.parse(arr_time_imm);
                                java.util.Date dept_time_d = sdf.parse(dept_time_imm_rs4);

                                long arr_time_l = arr_day.getTime() + arr_time_d.getTime();
                                long dept_time_l = dept_day.getTime() + dept_time_d.getTime();
                                
                                if( dept_time_l-arr_time_l < 10800000 && (dept_time_l-arr_time_l >= (long)0) && (start_stn_order_rs4<end_stn_order_rs5)){
                                    option+=1;
                                    journeyPlanner+= "Option : "+ option+"\n\tTrain-1-uid : "+uid + " Date-of-journey : " + doj + " Arrival Time : " + arr_time + " Deparature Time : " + dept_time;
                                    journeyPlanner+= "\n\t" + imm_station+ "\n\tTrain-2-uid : "+uid_rs4 + " Date-of-journey : " + doj_rs4 + " Arrival Time : " + arr_time_imm_rs4 + " Deparature Time : " + dept_time_imm_rs4+"\n"; 
                                }
                            }
                            
                        }
                    }
                    else{
                        break;
                    }
                    order_to_check++;
                }
                
            }
        }
        if(option == 0){
            journeyPlanner = "No trains are available between two stations";
        }
        // st.close();
        return journeyPlanner;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException 
    {    
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password"); // 

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        Scanner sc = new Scanner(System.in); // System.in is a standard input stream
        System.out.println("Enter Start station :  ");
        String startStation = sc.nextLine();
        // String endStation = sc.nextLine();

        
        System.out.println("Enter Destination Station : ");
        String endStation = sc.nextLine();


        String train_journey = journeySearcher(con, startStation, endStation);
        System.out.println(train_journey);
        
        sc.close();
        //con.close();
    }
}
