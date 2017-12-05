
import java.io.Serializable;
import java.util.*;


public class Room implements Serializable
{
   public Room(int n, int c, Reservations r)
   {
      roomNumber = n;
      cost = c;
      reservations = r;
   }
   
   public void setReservation(Reservations list)
   {
      reservations = list;
   }
   
   public Reservations getReservations()
   {
      return reservations;
   }
   
   public int getRoomNumber()
   {
      return roomNumber;
   }
   public void addReservation(Reservation r)
   {
      reservations.add(r);
   }
   
   public void cancelReservation(Reservation r)
   {
      reservations.cancel(r);
   }
   
   public boolean isType(int type)
   {
      return cost == type;
   }

   public boolean isAvailable(Calendar startDate, Calendar endDate)
   {
      Iterator<Reservation> it = reservations.getReservations();
      while(it.hasNext())
      {
         Reservation r = it.next();
         Date sDate = r.getStartDate();
         Date eDate = r.getendDate();
         if( ((sDate.after(startDate.getTime()) || sDate.equals(startDate.getTime())) 
                  && (sDate.before(endDate.getTime())) || sDate.equals(endDate.getTime()))
                  ||  ((eDate.after(startDate.getTime()) || eDate.equals(startDate.getTime())) 
                           && (eDate.before(endDate.getTime()) || eDate.equals(endDate.getTime()))))
                  {
                     return false;
                  }
      }
      
      return true;
   }
   
   public int getCost()
   {return cost;}
   
   public String toString()
   {
      return "Room #" + getRoomNumber() + " Price = " + getCost();
   }
   private int cost;
   private int roomNumber;
   private Reservations reservations;
   
}
