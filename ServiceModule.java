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

import org.apache.commons.dbcp2.BasicDataSource;

import java.util.ResourceBundle;

// import java.time.Duration;
// import java.time.Instant;

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
    
    // void bookingFunction(Connection con, int no_of_passengers, String name_of_all_passengers, int train_no, String DOJ, String Class) throws SQLException {
    public String bookingFunction(Connection con, ArrayList <String> tokens) throws SQLException {    

        int no_of_passengers = Integer.parseInt(tokens.get(0));
        String train_no = tokens.get(no_of_passengers+1);
        train_no = Integer.toString(Integer.parseInt(train_no));
        String DOJ = tokens.get(no_of_passengers+2);
        String Class = tokens.get(no_of_passengers+3);
        
        Class = Class.toLowerCase();

        String dDOJ = DOJ.substring(0,4) + "_" + DOJ.substring(5,7) + "_" + DOJ.substring(8,10);
        
        Statement st = con.createStatement();
        
        String table_name = "t" + train_no + "_" + dDOJ + "_" + Class;
        
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

        try{
            con.setAutoCommit(false);
            String fetch = "select * from " + table_name + ";";
            ResultSet rs = st.executeQuery(fetch);
            rs.next();
            int checkAvailable = rs.getInt("available");
            // System.out.println(checkAvailable);
            if(checkAvailable < no_of_passengers){
                con.commit();
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
            String masterQuery = "";

            String response = "TICKET RESULTS:- \n";
            
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
                
                String pname = tokens.get(i);
                if(i != no_of_passengers){
                    pname = pname.substring(0, pname.length() - 1);
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
                query = query + "'" + pname + "'"; // pname
                query = query + ");";
                
                //System.out.println(query);
                
                response = response + "PNR : " + pnr + " NAME : " + pname + " TRAIN NO : " + train_no + " COACH NO : " + coach_no + " CLASS : " + type + " BERTH NO : " + berth_no + " TYPE : " + type + "\n";
                masterQuery += query;
            }

            int update_count = checkAvailable - no_of_passengers;
            String update_query = "update " + table_name + " set available = " + Integer.toString(update_count) + ";";
            update_query+=masterQuery;

            //System.out.println(update_query);
            st.executeUpdate(update_query);
            con.commit();

            return response;
        }
        catch(SQLException e){
            //JDBCTutorialUtilities.printSQLException(e);
            if (con != null) {
                try {
                    con.rollback();
                    return bookingFunction(con, tokens);
                } catch (SQLException excep) {
                    //JDBCTutorialUtilities.printSQLException(excep);
                }
                
            }
            return "This transaction has been rolled back";
        }

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

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxTotal(25);

            Class.forName("org.postgresql.Driver");
            Connection con = dataSource.getConnection();
            
            // Setting all transaction isolation to serializable
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

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
                
                if(queryInput.equals("#")) // EOF: # 
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

                // long start = System.nanoTime();

                String result = bookingFunction(con, tokens);

                // long end = System.nanoTime();
                // //System.out.println(result);
                // System.out.println("Response Time : " + (end-start));
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
    static int numServerCores = 100;
    //------------ Main----------------------
    public static void main(String[] args) throws IOException 
    {    
        // Creating a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(numServerCores);
        
        //Creating a server socket to listen for clients
        ServerSocket serverSocket = new ServerSocket(serverPort); //need to close the port
        Socket socketConnection = null;
        
        // Always-ON server
        // long start = System.nanoTime();
        // System.out.println("Server start time : " + start); 
        while(true)
        {
            System.out.println("Listening port : " + serverPort 
                                + "\nWaiting for clients...");
            socketConnection = serverSocket.accept();   // Accept a connection from a client
            long c_s = System.nanoTime(); 
            System.out.println("Accepted client :" 
                                + socketConnection.getRemoteSocketAddress().toString() 
                                );
            
            //  Create a runnable task
            Runnable runnableTask = new QueryRunner(socketConnection);
            //  Submit task for execution   
            executorService.submit(runnableTask);   
            long c_e = System.nanoTime();

            // System.out.println("Client Start time : " + (c_s-start));
            // System.out.println("Client End time : " + (c_e-start));
            System.out.println("Client Exec time : " + (c_e-c_s) + "\n");
        }
    }
}

 