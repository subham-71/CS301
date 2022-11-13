import java.sql.*;
import java.util.ResourceBundle;

public class Admin {

    public static void admin(Connection con, int train_no, int ac_coach_count, int sl_coach_count, String DOJ) throws SQLException{

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
        
        st.executeUpdate(query_insert);
        st.executeUpdate(AC_table);
        st.executeUpdate(SL_table);
        st.executeUpdate(AC_insert);
        st.executeUpdate(SL_insert);
        
        st.close();
    }
    
    public static void main(String[] args) throws Exception {
            ResourceBundle rd = ResourceBundle.getBundle("config");
            String url = rd.getString("url");
            String username = rd.getString("username");
            String password = rd.getString("password"); 

            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, username, password);

            admin(con,12893,2,6,"2022-02-10");
            
            con.close();
    }
}
