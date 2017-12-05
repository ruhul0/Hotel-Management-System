import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Reservation implements Serializable
{
 private Date startDate;
 private Date endDate;
 private int id;
 private Room room;
 //add transaction id
 private int transactionID;

 private final SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
 
 // add constructor
 public Reservation( int transID, Date startDate, Date endDate, int id, Room r)
 {
    this.startDate = startDate;
    this.endDate = endDate;
    this.id = id;
    transactionID = transID;
    room = r;   
 }
 
 public Reservation(Date startDate, Date endDate, int id)
 {
	 this.startDate = startDate;
	 this.endDate = endDate;
	 this.id = id;
 }
 
 public int getTransactionID()
 {
    return transactionID;
 }
 
 public int getID()
 {
	 return id;
 }
 
 public int getRoomNumber()
 {
	 return room.getRoomNumber();
 }
 
 public Date getStartDate()
 {
	 return startDate;
 }
 
 public Date getendDate()
 {
	 return endDate;
 }
 
 public String toString()
 {

    return "Room #" + getRoomNumber() + " checkin: " + dt.format(startDate) + " checkout: " + dt.format(endDate);
 }

 public int getPrice()
 {
    return room.getCost();
 }
}

