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
import java.util.Date;
import java.util.*;
import java.sql.*;

class book{
    
    void admin(Connection con, int train_no, int ac_coach_count, int sl_coach_count, String DOJ) throws SQLException{

        Statement st = con.createStatement();
        
        String query_insert = "Insert into ticket_records(uid,ac_coach_count,sl_coach_count,DOJ) values("    ;
        query_insert = query_insert + Integer.toString(train_no) ;
        query_insert = query_insert + Integer.toString(ac_coach_count) ;
        query_insert = query_insert + Integer.toString(sl_coach_count) ;
        query_insert = query_insert + DOJ ;      
        query_insert = query_insert + ");";
        
        String ac_table_name = Integer.toString(train_no) + "_" + DOJ + "_AC" ;
        String sl_table_name = Integer.toString(train_no) + "_" + DOJ + "_SL" ;
        
        String AC_table = "CREATE TABLE " + ac_table_name + " (";
        AC_table = AC_table + "available INT NOT NULL";
        AC_table = AC_table + " );";

        String SL_table = "CREATE TABLE " + sl_table_name + " (";
        SL_table = SL_table + "available INT NOT NULL";
        SL_table = SL_table + " );";

        String AC_insert = "INSERT INTO "+ ac_table_name + "(available) values(" + Integer.toString(ac_coach_count*18)+");";
        String SL_insert = "INSERT INTO "+ sl_table_name + "(available) values(" + Integer.toString(sl_coach_count*18)+");";

        st.executeQuery(query_insert);
        st.executeQuery(AC_table);
        st.executeQuery(SL_table);
        st.executeQuery(AC_insert);
        st.executeQuery(SL_insert);
        
    }
    
    // void bookingFunction(Connection con, int no_of_passengers, String name_of_all_passengers, int train_no, String DOJ, String Class) throws SQLException {
    void bookingFunction(Connection con, ArrayList <String> tokens) throws SQLException {    
      
        // int index = 0;
        int no_of_passengers = Integer.parseInt(tokens.get(0));
        String train_no = tokens.get(no_of_passengers+1);
        String DOJ = tokens.get(no_of_passengers+2);
        String Class = tokens.get(no_of_passengers+3);
        
        
        Statement st = con.createStatement();
        
        String table_name = train_no + "_" + DOJ + "_" + Class;
        String fetch = "SELECT * FROM " + table_name + ";";
            
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet ch = dbm.getTables(null, null, table_name, new String[] {"TABLE"});
        Boolean checker = ch.next();
        if(!checker) {
            System.out.println("Train is not available");
            return;
        }
        ResultSet rs = st.executeQuery(fetch);
        rs.next();
        int checkAvailable = rs.getInt("available");
        if(checkAvailable < no_of_passengers){
            System.out.println("Train has insufficient number of seats");
            return;
        }
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        
        
        for(int i=1; i<=no_of_passengers; i++){

            String query = "insert into ticket_records(uid,booking_timestamp,berth_no,doj,coach_no,coach_type,seat_type,pnr,passenger_name) values(";
            query = query + train_no + ", "; // uid
            query = query + timestamp.toString() + ", "; //timestamp
            // query = query + Integer.toString(berth_no) + "" // berth_no
            query = query + DOJ + ", "; // doj
            // coach_no
            // seat_type
            // pnr
            query = query + '"' + tokens.get(i) + '"'; // pname
            query = query + ");";

        }
        
    }
}

class QueryRunner implements Runnable
{
    //  Declare socket for client access
    protected Socket socketConnection;

    public QueryRunner(Socket clientSocket)
    {
        this.socketConnection =  clientSocket;
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

            String url = "jdbc:postgresql://localhost:5432/railway"; // localhost:5432
            String username = "postgres";
            String password = ""; // pw

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            while(true)
            {
                // Read client query
                clientCommand = bufferedInput.readLine();
                System.out.println(clientCommand);
                // System.out.println("Recieved data <" + clientCommand + "> from client : " 
                //                     + socketConnection.getRemoteSocketAddress().toString());

                //  Tokenize here
                StringTokenizer tokenizer = new StringTokenizer(clientCommand);

                ArrayList<String> tokens = new ArrayList<String>();
                while(tokenizer.hasMoreElements()){
                    queryInput = tokenizer.nextToken();
                    tokens.add(queryInput);
                }


                // for(int i=0; i<tokens.size(); i++){
                //     System.out.println(tokens.get(i));   
                // }
                
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

                String query = "select * from ac_cc";
                
                DatabaseMetaData dbm = con.getMetaData();
                ResultSet ch = dbm.getTables(null, null, "ac_cc", new String[] {"TABLE"});
                System.out.println(ch.next());

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                        int id = rs.getInt("berth_no");
                        String name = rs.getString("type");
                        System.out.println(id + " - " + name);
                }

                responseQuery = "******* Dummy result ******";

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

