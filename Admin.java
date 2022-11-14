import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.File;
import java.util.StringTokenizer;

public class Admin {

    public static void admin(Connection con, ArrayList <String> tokens) throws SQLException{
    // public static void admin(Connection con, int train_no, int ac_coach_count, int sl_coach_count, String DOJ) throws SQLException{
        
        String train_no = tokens.get(0);
        String DOJ = tokens.get(1);
        String ac_coach_count = tokens.get(2);
        String sl_coach_count = tokens.get(3); 
        
        Statement st = con.createStatement();
        String dDOJ = DOJ.substring(0,4) + "_" + DOJ.substring(5,7) + "_" + DOJ.substring(8,10);
        
        String query_insert = "INSERT INTO train(uid,ac_count,sl_count,DOJ) values("    ;
        query_insert = query_insert + (train_no) + ", ";
        query_insert = query_insert + (ac_coach_count) + ", ";
        query_insert = query_insert + (sl_coach_count) + ", ";
        query_insert = query_insert + "'" + DOJ + "'" ;      
        query_insert = query_insert + ");";
        
        String ac_table_name = "t" + (train_no) + "_" + dDOJ + "_ac" ;
        String sl_table_name = "t" + (train_no) + "_" + dDOJ + "_sl" ;
        
        String AC_table = "CREATE TABLE " + ac_table_name + " (";
        AC_table = AC_table + "available INT NOT NULL";
        AC_table = AC_table + " );";

        String SL_table = "CREATE TABLE " + sl_table_name + " (";
        SL_table = SL_table + "available INT NOT NULL";
        SL_table = SL_table + " );";

        String AC_insert = "INSERT INTO "+ ac_table_name + "(available) values(" + Integer.toString(Integer.parseInt(ac_coach_count)*18)+");";
        String SL_insert = "INSERT INTO "+ sl_table_name + "(available) values(" + Integer.toString(Integer.parseInt(sl_coach_count)*24)+");";
        
        try{
            st.executeUpdate(query_insert);

        } catch(SQLException e){
            System.out.println("Record already exists.") ;
        }

        try{
            st.executeUpdate(AC_table);

        } catch(SQLException e){
            System.out.println("Record already exists.") ;
        }

        try{
            st.executeUpdate(SL_table);

        } catch(SQLException e){
            System.out.println("Record already exists.") ;
        }

        try{
            st.executeUpdate(AC_insert);

        } catch(SQLException e){
            System.out.println("Record already exists."); 
        }

        try{
            st.executeUpdate(SL_insert);

        } catch(SQLException e){
            System.out.println("Record already exists.");
        }
        
        st.close();
    }
    
    public static void main(String[] args) throws Exception {

            System.out.println("Admin Called\n");
            String driver_class = "org.postgresql.Driver";
            String inputfile = "Trainschedule.txt" ;
            File queries = new File(inputfile);
            Scanner sc = new Scanner(queries);

            ResourceBundle rd = ResourceBundle.getBundle("config");
            String url = rd.getString("url");
            String username = rd.getString("username");
            String password = rd.getString("password"); 

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            
            String query = "";
            String queryInput = "" ;
            
            while(sc.hasNextLine())
            {
                query = sc.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(query);
                queryInput = tokenizer.nextToken();
                if(queryInput.equals("#")) // EOF: # 
                {
                    return;
                }

                ArrayList<String> tokens = new ArrayList<String>();
                tokens.add(queryInput);
                while(tokenizer.hasMoreElements()){
                    queryInput = tokenizer.nextToken();
                    tokens.add(queryInput);
                }

                admin(con,tokens);
            }

            sc.close();
            con.close();
    }
}
