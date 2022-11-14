import java.util.Scanner;
// import java.io.BufferedReader;
// import java.io.DataInputStream;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.OutputStream;

public class Master{
    public static void main(String[] args){
        System.out.println("WELCOME TO THE RAILWAY RESERVATION SYSTEM: \n");
        System.out.println("1. Admin Procedure\n");
        System.out.println("2. Booking Procedure\n");
        System.out.println("3. Search Procedure\n");

         

        Scanner sc = new Scanner(System.in);
        int select = sc.nextInt();

        if(select == 1){
            try {
                
                // Process p1 = Runtime.getRuntime().exec("javac admin.java");
                // p1.waitFor();
                // Process p2 = Runtime.getRuntime().exec("java admin");
                // OutputStream out = p2.getOutputStream();
                // p2.waitFor();
                // out.flush();
                // DataInputStream in = new DataInputStream(
                //     p2.getInputStream());

                // BufferedReader br = new BufferedReader(new InputStreamReader(in));
                //         String line, result="";
                //         while ((line = br.readLine()) != null) {
                //                 result +=line;
                // }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(select == 2){
            try {
                // Process p5 = Runtime.getRuntime().exec("javac ServiceModule.java");
                // Process p6 = Runtime.getRuntime().exec("java ServiceModule");
                // Process p7 = Runtime.getRuntime().exec("javac client.java");
                // Process p8 = Runtime.getRuntime().exec("java client");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(select == 3){
            try {
                // Process p3 = Runtime.getRuntime().exec("javac SearchProcedure.java");
                // Process p4 = Runtime.getRuntime().exec("java SearchProcedure");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        sc.close();
    }
}