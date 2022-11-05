import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ResourceBundle;

// import java.util.Date;
import java.util.*;
import java.sql.*;

class QueryRunner implements Runnable
{
    //  Declare socket for client access
    protected Socket socketConnection;

    public QueryRunner(Socket clientSocket)
    {
        this.socketConnection =  clientSocket;
    }

    public void admin(Connection con, int train_no, int ac_coach_count, int sl_coach_count, String DOJ) throws SQLException{

        Statement st = con.createStatement();
        String dDOJ = DOJ.substring(0,4) + "_" + DOJ.substring(5,7) + "_" + DOJ.substring(8,10);
        
        String query_insert = "INSERT INTO train(uid,ac_count,sl_count,DOJ) values("    ;
        query_insert = query_insert + Integer.toString(train_no) + ", ";
        query_insert = query_insert + Integer.toString(ac_coach_count) + ", ";
        query_insert = query_insert + Integer.toString(sl_coach_count) + ", ";
        query_insert = query_insert + "'" + DOJ + "'" ;      
        query_insert = query_insert + ");";
        
        String ac_table_name = "t" + Integer.toString(train_no) + "_" + dDOJ + "_ac" ;
        String sl_table_name = "t" + Integer.toString(train_no) + "_" + dDOJ + "_sl" ;
        
        String AC_table = "CREATE TABLE " + ac_table_name + " (";
        AC_table = AC_table + "available INT NOT NULL";
        AC_table = AC_table + " );";

        String SL_table = "CREATE TABLE " + sl_table_name + " (";
        SL_table = SL_table + "available INT NOT NULL";
        SL_table = SL_table + " );";

        String AC_insert = "INSERT INTO "+ ac_table_name + "(available) values(" + Integer.toString(ac_coach_count*18)+");";
        String SL_insert = "INSERT INTO "+ sl_table_name + "(available) values(" + Integer.toString(sl_coach_count*24)+");";

        // System.out.println(query_insert);
        // System.out.println(AC_table);
        // System.out.println(SL_table);
        // System.out.println(AC_insert);
        // System.out.println(SL_insert);
        
        
        st.executeUpdate(query_insert);
        st.executeUpdate(AC_table);
        st.executeUpdate(SL_table);
        st.executeUpdate(AC_insert);
        st.executeUpdate(SL_insert);
        
    }
    
    // void bookingFunction(Connection con, int no_of_passengers, String name_of_all_passengers, int train_no, String DOJ, String Class) throws SQLException {
    public String bookingFunction(Connection con, ArrayList <String> tokens) throws SQLException {    
      
        // int index = 0;


        int no_of_passengers = Integer.parseInt(tokens.get(0));
        String train_no = tokens.get(no_of_passengers+1);
        String DOJ = tokens.get(no_of_passengers+2);
        String Class = tokens.get(no_of_passengers+3);
        
        Class = Class.toLowerCase();

        String dDOJ = DOJ.substring(0,4) + "_" + DOJ.substring(5,7) + "_" + DOJ.substring(8,10);
        
        Statement st = con.createStatement();
        
        String table_name = "t" + train_no + "_" + dDOJ + "_" + Class;
        String fetch = "select * from " + table_name + ";";
        
        //System.out.println(fetch);
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet ch = dbm.getTables(null, null, table_name, new String[] {"TABLE"});
        // if (!ch.next()) {    
        //     System.out.println("No data"); 
        // } 

        Boolean checker = ch.next();
        //System.out.println(checker); 
        if(!checker) {
            return "Train is not available";
        }
        ResultSet rs = st.executeQuery(fetch);
        rs.next();
        int checkAvailable = rs.getInt("available");
        // System.out.println(checkAvailable);
        if(checkAvailable < no_of_passengers){
            return "Train has insufficient number of seats";
        }

        int coach_count =0;
        //System.out.println(Class);
        if(Class.compareTo("ac") == 0){
            String ac_coach_count_query = "select ac_count from train where uid = " + train_no + " and doj = '"+ DOJ + "';";
            //System.out.println(ac_coach_count_query);
            rs = st.executeQuery(ac_coach_count_query);
            rs.next();
            coach_count = rs.getInt("ac_count") * 18 ;
        }
        else if (Class.compareTo("sl") == 0) {
            String sl_coach_count_query = "select sl_count from train where uid = " + train_no + " and doj = '" + DOJ + "';";
            //System.out.println(sl_coach_count_query);
            ResultSet rs1 = st.executeQuery(sl_coach_count_query);
            rs1.next();
            coach_count = rs1.getInt("sl_count") * 24;
        }       
        
        //System.out.println(coach_count);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String pnr = "";
        String pnr_doj = DOJ.substring(0,4) + DOJ.substring(5,7) + DOJ.substring(8,10);
        
        for(int i=1; i<=no_of_passengers; i++){
            int serial_no;
            int coach_no;
            int berth_no;
            // Todo : Seat type alternative implementation
            String type = "";
            
            if(Class.compareTo("ac") == 0){
                serial_no = coach_count - checkAvailable + i;
                coach_no = (serial_no-1)/18 + 1;
                berth_no = (serial_no-1)%18 + 1;

                String seat_type_query = "select type from ac_cc where berth_no = "+ berth_no + ";";
                //System.out.println(seat_type_query);
                rs = st.executeQuery(seat_type_query);
                rs.next();
                type = rs.getString("type");
            }
            else{
                serial_no = coach_count - checkAvailable + i;
                coach_no = (serial_no-1)/24 + 1;
                berth_no = (serial_no-1)%24 + 1;

                String seat_type_query = "select type from sl_cc where berth_no = "+berth_no+";";
                rs = st.executeQuery(seat_type_query);
                rs.next();
                type = rs.getString("type");
            }

            if(i==1) {
                pnr = train_no+ pnr_doj;
                if(coach_no<10) pnr = pnr + '0';
                pnr = pnr + Integer.toString(coach_no);
                if(berth_no<10) pnr = pnr + '0';
                pnr = pnr + Integer.toString(berth_no);
            }
            
            String query = "insert into ticket_records(uid,booking_timestamp,berth_no,doj,coach_no,coach_type,seat_type,pnr,passenger_name) values(";
            query = query + train_no + ", '"; // uid
            query = query + timestamp.toString().substring(0, 19) + "', "; //timestamp 0 - 19
            query = query + Integer.toString(berth_no) + ", '"; // berth_no
            query = query + DOJ + "', "; // doj
            query = query + Integer.toString(coach_no) + ", '";// coach_no
            query = query + Class + "', '"; // coach type
            query = query + type + "', '";// seat_type
            query = query + pnr + "', "; // pnr
            query = query + "'" + tokens.get(i) + "'"; // pname
            query = query + ");";
            
            //System.out.println(query);
            st.executeUpdate(query);
        }

        int update_count = checkAvailable - no_of_passengers;
        String update_query = "update " + table_name + " set available = " + Integer.toString(update_count) + ";";

        //System.out.println(update_query);
        st.executeUpdate(update_query);
        
        
        return "Tickets has been booked for the following request";
        
    }

    public void run()
    {
      try
        {
            //  Reading data from client
            InputStreamReader inputStream = new InputStreamReader(socketConnection
                                                                  .getInputStream()) ;
            BufferedReader bufferedInput = new BufferedReader(inputStream) ;
            OutputStreamWriter outputStream = new OutputStreamWriter(socketConnection
                                                                     .getOutputStream()) ;
            BufferedWriter bufferedOutput = new BufferedWriter(outputStream) ;
            PrintWriter printWriter = new PrintWriter(bufferedOutput, true) ;
            
            String clientCommand = "" ;
            String responseQuery = "" ;
            String queryInput = "" ;

            ResourceBundle rd = ResourceBundle.getBundle("config");
            
            String url = rd.getString("url");
            String username = rd.getString("username");
            String password = rd.getString("password");

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            // admin(con,12891,5,6,"2022-12-10");

            while(true)
            {
                // Read client query
                clientCommand = bufferedInput.readLine();
                //System.out.println(clientCommand);
                // System.out.println("Recieved data <" + clientCommand + "> from client : " 
                //                     + socketConnection.getRemoteSocketAddress().toString());

                //  Tokenize here
                StringTokenizer tokenizer = new StringTokenizer(clientCommand);
                queryInput = tokenizer.nextToken();
                
                if(queryInput.equals("Finish"))
                {
                    String returnMsg = "Connection Terminated - client : " 
                                        + socketConnection.getRemoteSocketAddress().toString();
                    System.out.println(returnMsg);
                    inputStream.close();
                    bufferedInput.close();
                    outputStream.close();
                    bufferedOutput.close();
                    printWriter.close();
                    socketConnection.close();
                    return;
                }

                ArrayList<String> tokens = new ArrayList<String>();
                tokens.add(queryInput);
                while(tokenizer.hasMoreElements()){
                    queryInput = tokenizer.nextToken();
                    tokens.add(queryInput);
                }

                String result = bookingFunction(con, tokens);
                //System.out.println(result);
                printWriter.println(result);
                

                //-------------- your DB code goes here----------------------------
                // try
                // {
                //    // Thread.sleep(6000);
                // } 
                // catch (InterruptedException e)
                // {
                //     e.printStackTrace();
                // }
                 //

                // String query = "select * from ac_cc";
                
                // DatabaseMetaData dbm = con.getMetaData();
                // ResultSet ch = dbm.getTables(null, null, "ac_cc", new String[] {"TABLE"});
                // System.out.println(ch.next());

                // Statement st = con.createStatement();
                // ResultSet rs = st.executeQuery(query);
                // while(rs.next()){
                //         int id = rs.getInt("berth_no");
                //         String name = rs.getString("type");
                //         System.out.println(id + " - " + name);
                // }

                // responseQuery = "******* Dummy result ******";

                //----------------------------------------------------------------
                
                //  Sending data back to the client
                printWriter.println(responseQuery); 
                // System.out.println("\nSent results to client - " 
                //                     + socketConnection.getRemoteSocketAddress().toString() );
                
            }
        }
        catch(IOException | ClassNotFoundException | SQLException e)
        {
            return;
        }
    }
}

/**
 * Main Class to controll the program flow
 */
public class ServiceModule 
{
    static int serverPort = 7005;
    static int numServerCores = 2 ;
    //------------ Main----------------------
    public static void main(String[] args) throws IOException 
    {    
        // Creating a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(numServerCores);
        
        //Creating a server socket to listen for clients
        ServerSocket serverSocket = new ServerSocket(serverPort); //need to close the port
        Socket socketConnection = null;
        
        // Always-ON server
        while(true)
        {
            System.out.println("Listening port : " + serverPort 
                                + "\nWaiting for clients...");
            socketConnection = serverSocket.accept();   // Accept a connection from a client
            System.out.println("Accepted client :" 
                                + socketConnection.getRemoteSocketAddress().toString() 
                                + "\n");
            //  Create a runnable task
            Runnable runnableTask = new QueryRunner(socketConnection);
            //  Submit task for execution   
            executorService.submit(runnableTask);   
        }
    }
}

 