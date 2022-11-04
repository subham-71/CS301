import java.sql.*;

public class ConnectDB {
        
        public static void main(String[] args) throws Exception {
                String url = "jdbc:postgresql://localhost:5432/"; // localhost:5432
                String username = "postgres";
                String password = ""; // 
                // 1
                String query = "select * from ac_cc";
                
                // 2
                // int userid = 1;
                // String uname = "Aman";
                // String query = "insert into student values (" + userid + ", '" + uname + "')";

                // 3
                // String query = "insert into student values (?,?)";

                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection(url, username, password);


                DatabaseMetaData dbm = con.getMetaData();
                ResultSet ch = dbm.getTables(null, null, "ac_cc", new String[] {"TABLE"});
                System.out.println(ch.next());
                // 1 
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                        int id = rs.getInt("berth_no");
                        String name = rs.getString("type");
                        System.out.println(id + " - " + name);
                }

                // 2
                // int cnt = st.executeUpdate(query);
                // System.out.println(cnt + " rows affected");

                // 3
                // PreparedStatement st = con.prepareStatement(query);
                // st.setInt(1, userid);
                // st.setString(2, uname);
                // int cnt = st.executeUpdate();

                st.close();
                con.close();
        }
}
