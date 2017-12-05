import java.util.*;

public class SimpleReceipt implements ReceiptFormatter
{
   private Hotel hotel;
   private Guest guest;
   private int transactionID;
   private int total;
   
   public SimpleReceipt(Hotel h, Guest g, int id)
   {
      hotel = h;
      guest = g;
      transactionID = id;
      total = 0;
   }

   public String format()
   {
      String receipt = guest.toString()+ '\n';
      Iterator<Room> it = hotel.roomIterator();
      while(it.hasNext())
      {
         Room r = it.next();
         Reservations rs = r.getReservations();
         Iterator<Reservation> it1 = rs.getReservationByUser(guest.getID()) ;
         while(it1.hasNext())
         {
            Reservation re = it1.next();
            if(re.getTransactionID() == transactionID){
               receipt+=("Room #" + r.getRoomNumber() + '\n');
               total+=r.getCost() *( re.getendDate().getTime()- re.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
            }
         }
         
      }
      return receipt += "Total amount due: " + total;
   } 

}
