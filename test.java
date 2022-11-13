import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;  

public class test{
    public static void main(String[] args) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        SimpleDateFormat dojSDF = new SimpleDateFormat("yyyy-MM-dd");
        // Date date = null;

        java.util.Date arr_day = dojSDF.parse("2022-01-11");
        java.util.Date dept_day = dojSDF.parse("2022-01-11");

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(arr_day);
        cal2.setTime(dept_day);
        cal1.add(Calendar.DAY_OF_MONTH,0);
        cal2.add(Calendar.DAY_OF_MONTH,2);

        arr_day = dojSDF.parse(dojSDF.format(cal1.getTime()));
        dept_day = dojSDF.parse(dojSDF.format(cal2.getTime()));

        java.util.Date arr_time_d =  sdf.parse("00:00:00");
        java.util.Date dept_time_d = sdf.parse("00:00:00");

        long arr_time_l = arr_day.getTime() + arr_time_d.getTime();
        long dept_time_l = dept_day.getTime()+dept_time_d.getTime();

        System.out.println(dept_time_l-arr_time_l);
    }
}