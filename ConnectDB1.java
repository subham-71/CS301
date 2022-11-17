import java.sql.*;
import java.util.ResourceBundle;
import org.apache.commons.dbcp2.BasicDataSource;

// For testing connection pool to the postgres database
public class ConnectDB1 {

        public static void main(String[] args) throws Exception {

                ResourceBundle rd = ResourceBundle.getBundle("config");
                String url = rd.getString("url"); // localhost:5432
                String username = rd.getString("username");
                String password = rd.getString("password");

                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setUrl(url);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                dataSource.setMinIdle(5);
                dataSource.setMaxIdle(10);
                dataSource.setMaxTotal(25);

                // 1
                String query = "select * from ac_cc";

                // 2
                // int userid = 1;
                // String uname = "Aman";
                // String query = "insert into student values (" + userid + ", '" + uname +
                // "')";

                // 3
                // String query = "insert into student values (?,?)";

                Class.forName("org.postgresql.Driver");
                Connection con = dataSource.getConnection();

                DatabaseMetaData dbm = con.getMetaData();
                ResultSet ch = dbm.getTables(null, null, "ac_cc", new String[] { "TABLE" });
                System.out.println(ch.next());
                // 1
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                        int id = rs.getInt("berth_no");
                        String name = rs.getString("type");
                        System.out.println(id + " - " + name);
                }

                st.close();
                con.close();
        }
}
