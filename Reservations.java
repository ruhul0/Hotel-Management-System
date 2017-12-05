import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;


public class Reservations implements Serializable
{

   private ArrayList<Reservation> reservationList;

   public Reservations()
   {
      reservationList = new ArrayList<Reservation>();
   }

   public void add(Reservation r)
   {
      reservationList.add(r);

      // sort the list according to the start dates
      if(reservationList.size()>1){
         Collections.sort(reservationList,new Comparator<Reservation>(){
            public int compare(Reservation r1, Reservation r2)
            {
               if(r1.getStartDate().after(r2.getStartDate()))
                  return  -1;
               else
                  return 1;
            }});
      }
   }


   public void cancel(Reservation r)
   {

      reservationList.remove(r);

   }

   public Iterator<Reservation> getReservationByUser(int id)
   {
      ArrayList<Reservation> reservesUnderSameID = new ArrayList<Reservation>();
      for(Reservation re : reservationList)
      {
         if(re.getID()==id)
         {
            reservesUnderSameID.add(re);
         }
      }

      return reservesUnderSameID.iterator();
   }

   public Iterator<Reservation> getReservationByDate(Date date)
   {
      ArrayList<Reservation> reservesUnderSameDate = new ArrayList<Reservation>();

      for(Reservation re : reservationList)
      {
         if(date.before(re.getStartDate()) || date.after(re.getendDate())) continue;
         reservesUnderSameDate.add(re);
      }
      return reservesUnderSameDate.iterator();
   }

   public Iterator<Reservation> getIteratorByRoom(int roomNum)
   {
      ArrayList<Reservation> reservesUnderSameRoom = new ArrayList<Reservation>();
      for(Reservation re : reservationList)
      {
         if(re.getRoomNumber()==roomNum)
         {
            reservesUnderSameRoom.add(re);
         }
      }

      return reservesUnderSameRoom.iterator();
   }


   public Iterator<Reservation> getReservations()
   {
      return reservationList.iterator();
   }

   /*
    deprecated
    */
   public void save()
   {
      try {


         File file = new File("reservations.txt");

         // if file doesn't exists, then create it
         if (!file.exists()) {
            file.createNewFile();
         }

         FileWriter fw = new FileWriter(file.getAbsoluteFile());
         BufferedWriter bw = new BufferedWriter(fw);
         DateFormat df = new SimpleDateFormat("yyyyMMdd");
         for (Reservation r: reservationList)
         {
            bw.write(df.format(r.getStartDate())+";");
            bw.write(df.format(r.getendDate())+";");
            bw.write(r.getID()+";");
            bw.write(r.getRoomNumber()+"/r/n");

         }
         bw.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
