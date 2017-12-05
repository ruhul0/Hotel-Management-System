import java.util.Iterator;

public class ComprehensiveReceipt implements ReceiptFormatter
{
   private Hotel hotel;
   private Guest guest;
   private int total;
   public ComprehensiveReceipt(Hotel h, Guest g)
   {
      hotel = h;
      guest = g;
      total = 0;
   }
   
   public String format()
   {
      String receipt = guest.toString() + '\n';
      Iterator<Room> it = hotel.roomIterator();
      while(it.hasNext())
      {
         Room r = it.next();
         Reservations rs = r.getReservations();
         Iterator<Reservation> it1 = rs.getReservationByUser(guest.getID());
         while(it1.hasNext())
         {
            Reservation re = it1.next();
            receipt+=re.toString() + '\n';
            total+=r.getCost() *( re.getendDate().getTime()- re.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
         }
         
      }
      return receipt += "Total amount due: " + total;
  
   } 

}
