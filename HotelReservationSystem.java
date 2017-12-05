import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;




public class HotelReservationSystem
{
   public static void main(String[] args)
   {   
      Hotel hotel = new Hotel();
     
      // create manager
      hotel.addUser(new Manager(1, "Manager", "admin"));
      
      // start gui
      new StartFrame(hotel);
      
   }
   
}
