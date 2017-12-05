

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;


public class ViewCancelReservationFrame
{
   private Guest guest;
   private Hotel hotel;

   private final JFrame frame = new JFrame();
   private final JPanel northPanel = new JPanel();
   private final JPanel centerPanel = new JPanel();
   private final JPanel southPanel = new JPanel();
   private final JScrollPane scrollPane ;
   private final JButton cancelButton = new JButton("Cancel Reservation");
   private final JList<Reservation> jlist = new JList<Reservation>();
   private DefaultListModel<Reservation> model = new DefaultListModel<Reservation>();
   

   ViewCancelReservationFrame(Hotel h, Guest g)
   {
      hotel = h;
      guest = g;
      frame.setTitle("Hotel Reservation");
      frame.setSize(400,400);

      northPanel.setLayout(new BorderLayout());
      JLabel label = new JLabel("Select a reservation to cancel it:\n");
      
      northPanel.add(label,BorderLayout.NORTH);
      Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY);
      northPanel.setBorder(lineBorder1);

      setView();
      scrollPane= new JScrollPane(jlist);
      centerPanel.setLayout(new BorderLayout());
      centerPanel.add(scrollPane, BorderLayout.CENTER);


      cancelButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            cancelReservation();
         }
      });


      southPanel.add(cancelButton, BorderLayout.SOUTH);
      Border lineBorder2 = BorderFactory.createLineBorder(Color.GRAY);
      southPanel.setBorder(lineBorder2);

      frame.add(northPanel, BorderLayout.NORTH);
      frame.add(centerPanel, BorderLayout.CENTER);
      
      frame.add(southPanel, BorderLayout.SOUTH);
      
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
      frame.setVisible(true);
   }


   private void cancelReservation()
   {
      Reservation r = jlist.getSelectedValue();
      if(r != null){
         int index = jlist.getSelectedIndex();

         model.remove(index);
         hotel.cancelReservation(r);
      }
   }

   private void setView()
   {
      ArrayList<Reservation> rs = hotel.getReservations(guest);
      for(Reservation r : rs)
      {
         model.addElement(r);
      }
      jlist.setModel(model);

   }

}


