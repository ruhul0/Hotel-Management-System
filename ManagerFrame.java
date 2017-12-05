import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.DefaultCaret;


public class ManagerFrame extends JFrame
{

   private Hotel hotel;   
   private final CalendarPanelJC westPanel;
   private JTextArea textArea = new JTextArea();

   private JScrollPane scrollPane = new JScrollPane(textArea); 

   ManagerFrame(Hotel h)
   {
      hotel = h;
      final JFrame frame = new JFrame();
      frame.setTitle("Hotel Manager Interface");
      frame.setSize(800,350);

      //title
      JPanel northPanel = new JPanel();
      JLabel label = new JLabel("Manager Interface");
      northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
      label.setFont(new Font("Serif", Font.PLAIN, 24));
      northPanel.add(label);

      //panel to display reservations
      JPanel eastPanel = new JPanel();
      eastPanel.setLayout(new BorderLayout());
      textArea.setEditable(false);

      //always scroll to first line
      DefaultCaret caret = (DefaultCaret) textArea.getCaret();
      caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

      eastPanel.add(scrollPane, BorderLayout.CENTER);

      westPanel = new CalendarPanelJC();
      westPanel.addListener(new ChangeListener(){
         public void stateChanged(ChangeEvent e)
         {
            //view ... model in CalendarPanelJC
            setView();
            repaint();
         }
      });

      //load button and action
      JPanel bottomPanel = new JPanel();
      JButton loadButton = new JButton("Load");
      loadButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(ManagerFrame.this);
            if (result == JFileChooser.APPROVE_OPTION) {
               File selectedFile = fileChooser.getSelectedFile();
               ObjectInputStream in = null;
               try
               {
                  in = new ObjectInputStream(new FileInputStream(selectedFile));
                  hotel = (Hotel) in.readObject();
                  in.close();
               } catch (IOException exception)
               {
                  JOptionPane.showMessageDialog(null, exception + "io");
               }
               catch (ClassNotFoundException exception)
               {
                  JOptionPane.showMessageDialog(null, exception + "class");
               }
            }
         }
      });

      //save/quit button and action
      JButton saveAndQuitButton = new JButton("Save and Quit");
      saveAndQuitButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {

            try{
               File file = new File("Reservation.txt");
               ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(hotel);
               out.close();
               System.exit(0);
            }
            catch (IOException exception)
            {
               JOptionPane.showMessageDialog(null, exception);
            }

         }
      }
               );

      bottomPanel.add(loadButton);
      bottomPanel.add(saveAndQuitButton);

      //finished frame
      frame.add(northPanel, BorderLayout.NORTH);
      frame.add(westPanel, BorderLayout.WEST);
      frame.add(eastPanel, BorderLayout.CENTER);
      frame.add(bottomPanel, BorderLayout.SOUTH);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //debate on exit
      frame.setVisible(true);
   }

   private void setView()
   {
      Calendar date = Calendar.getInstance();
      date.set(westPanel.getYY(),  westPanel.getMM(), westPanel.getDD());

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
      String t = "Room Information: for ";
      t += sdf.format(date.getTime()) + "\n\n";
      ArrayList<Reservation> rlist = hotel.getReservations(date.getTime());
      t += "Booked Rooms:\n";
      for(Reservation r : rlist)
      {
         t+="Room #" + r.getRoomNumber() 
                  + " Guest ID: " + r.getID() 
                  + " Price: " + r.getPrice() + "TK"
                  + '\n';
      }

      t+="\nAvailable Rooms: \n";

      for(Room r : hotel.getRooms())
      {
         if(! r.getReservations().getReservationByDate(date.getTime()).hasNext())
            t+="Room #" + r.getRoomNumber() + '\n';
      }
      textArea.setText(t);


   }
}
