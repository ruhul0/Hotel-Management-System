import java.util.Date;




public class Manager extends User
{
    public Manager(int id, String name, String password)
    {
        super(id, name, password);
    }
    
    public void checkReservation(Date startDate, Reservations r)
    {
        r.getReservationByDate(startDate);
    }
}
