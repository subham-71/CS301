import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Calendar;  
import java.io.IOException;

// Search Procedure to find the train between two stations
public class SearchProcedure {
    
    // Function takes the connection object, start station and end station
    public static String journeySearcher(Connection con,String startStation, String endStation) throws SQLException, ParseException
    {   
        // Journey Planner contains the various options by which you can travel from start to end
        String journeyPlanner = "";
        int option = 0;

        // Searching all trains from start station
        String query1 = "Select * from station where name = '" + startStation + "';";
        Statement st1 = con.createStatement();
        ResultSet rs1 = st1.executeQuery(query1);

        while(rs1.next()){
            int uid = rs1.getInt("uid");
            String doj= rs1.getString("doj");
            String arr_time = rs1.getString("Arrival_time");
            String dept_time = rs1.getString("Departure_time");
            int start_stn_order = rs1.getInt("order_of_station"); 
            
            // Checking if the current train directly goes to the end station
            String query2 = "Select * from station where name = '"+ endStation + "' and uid = " + uid + " and doj = '" + doj + "';";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(query2); 
            Boolean result_check = rs2.next();
            int end_stn_order = -1;
            if(result_check){
                end_stn_order = rs2.getInt("order_of_station");
            }
            
            // If the end station appears after start station on order of journey, then only journey is possible
            if(result_check && start_stn_order<end_stn_order){
                // Direct trains
                option++;
                journeyPlanner+= "Option : "+ option+"\n\tTrain-uid : "+uid + " Date-of-journey : " + doj + " Arrival Time : " + arr_time + " Deparature Time : " + dept_time+"\n"; 
            }
            else{
                //Non-Direct trains
                int order_to_check = start_stn_order+1;
                while(true){

                    // Checking all the station on the journey of train
                    String query3 = "Select * from station where order_of_station = "+ order_to_check + " and uid = " + uid + " and doj = '" + doj + "';";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(query3);
                    if(rs3.next()){
                        
                        String imm_stn = rs3.getString("name");
                        String arr_time_imm = rs3.getString("Arrival_time");
                        String doj_rs3 = rs3.getString("doj");
                        int imm_arr_offset = rs3.getInt("off_set");
                        
                        // Fetching all the trains from the respective station
                        Statement st4 = con.createStatement();
                        String query4 = "Select * from station where name = '" + imm_stn + "';";
                        ResultSet rs4 = st4.executeQuery(query4);
                        
                        while(rs4.next()){
                            
                            int uid_rs4 = rs4.getInt("uid");
                            String doj_rs4= rs4.getString("doj");
                            String arr_time_imm_rs4 = rs4.getString("Arrival_time");
                            String dept_time_imm_rs4 = rs4.getString("Departure_time");
                            int start_stn_order_rs4 = rs4.getInt("order_of_station"); 
                            int imm_dept_offset = rs4.getInt("off_set");
                            String imm_station = rs4.getString("name") ; 
                            
                            // Checking if the train comes to the end station (atmost one break)
                            String query5 = "Select * from station where name = '"+ endStation + "' and uid = " + uid_rs4 + " and doj = '" + doj_rs4 + "' ;";
                            Statement st5 = con.createStatement();
                            ResultSet rs5 = st5.executeQuery(query5);

                            if(rs5.next()){
                                
                                int end_stn_order_rs5 = rs5.getInt("order_of_station"); 
                                
                                // Checking arrival time at intermediate station (train 1) < departure time at intermediate station (train 2)
                                // Combining doj with offsets with the day time
                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                                SimpleDateFormat dojSDF = new SimpleDateFormat("yyyy-MM-dd");

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
                                
                                // Keeping a bound on the wait time (Kept it at 3 hours) and checking order of station on train 2
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
        // If there are no options, no trains to be returned
        if(option == 0){
            journeyPlanner = "No trains are available between two stations";
        }
        return journeyPlanner;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException 
    {    
        // Establishing the connnection
        ResourceBundle rd = ResourceBundle.getBundle("config");
        String url = rd.getString("url"); // localhost:5432
        String username = rd.getString("username");
        String password = rd.getString("password"); // 

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, username, password);

        Scanner sc = new Scanner(System.in); // System.in is a standard input stream

        // Taking start station as input
        System.out.println("Enter Start station :  ");
        String startStation = sc.nextLine();
        
        // Taking end station as output
        System.out.println("Enter Destination Station : ");
        String endStation = sc.nextLine();
        
        // Calling the journeySearcher function
        String train_journey = journeySearcher(con, startStation, endStation);

        // Printing train journey
        System.out.println(train_journey);
        
        sc.close();
    }
}
