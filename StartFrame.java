
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class StartFrame extends JFrame
{
   private Hotel hotel;
   private  JRadioButton guestRadio = new JRadioButton("Guest");
   private  JRadioButton managerRadio = new JRadioButton("Manager");
   private  JTextField userIDField = new JTextField("1234");
   private  JPasswordField pinField = new JPasswordField("1234");
   final JFrame frame = new JFrame();

   StartFrame(Hotel h)
   {
      
      hotel = h;
      load();
      frame.setTitle("Hotel Reservation System");
      frame.setSize(600,250);

      JPanel northPanel = new JPanel();
      JLabel label = new JLabel("Welcome to Hotel Reservation System");

      northPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
      label.setFont(new Font("Serif", Font.PLAIN, 24));
      northPanel.add(label);

      JPanel centerPanel = new JPanel();    
      centerPanel.setLayout(new BorderLayout());

      Border lineBorder1 = BorderFactory.createLineBorder(Color.GRAY);
      centerPanel.setBorder(lineBorder1);

      final ButtonGroup group = new ButtonGroup();
      group.add(guestRadio);
      group.add(managerRadio);
      group.setSelected(guestRadio.getModel(), true);
      JPanel centerUpperPanel = new JPanel();
      centerUpperPanel.add(guestRadio);
      centerUpperPanel.add(managerRadio);
      Border lineBorder2 = BorderFactory.createLineBorder(Color.GRAY);
      centerUpperPanel.setBorder(lineBorder2);

      JPanel centerMiddlePanel = new JPanel();
      JPanel centerMiddlePanel1 = new JPanel();
      JPanel centerMiddlePanel2 = new JPanel();

      JLabel idLabel = new JLabel("User ID");
      JLabel pinLabel = new JLabel("Password");
      pinField.setColumns(8);
      pinField.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e)
         {
            login();
         }
      });
      centerMiddlePanel1.add(idLabel);
      centerMiddlePanel1.add(userIDField);

      centerMiddlePanel2.add(pinLabel);
      centerMiddlePanel2.add(pinField);

      final JPanel centerMiddlePanel3 = new JPanel();    
      JButton loginButton = new JButton("Login");
      loginButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            login();
         }
      });

      loginButton.setPreferredSize(new Dimension(80,40));

      centerMiddlePanel3.add(loginButton);
      centerMiddlePanel.add(centerMiddlePanel1);
      centerMiddlePanel.add(centerMiddlePanel2);
      centerMiddlePanel.add(centerMiddlePanel3);

      centerPanel.add(centerUpperPanel, BorderLayout.NORTH);
      centerPanel.add(centerMiddlePanel, BorderLayout.CENTER);

      JPanel bottomPanel = new JPanel();    
      JLabel msgLabel = new JLabel("New User? Click to sign up");
      JButton signupButton = new JButton("Sign Up");

      signupButton.addActionListener(new ActionListener()
      {

         public void actionPerformed(ActionEvent e)
         {
            new SignUpFrame(hotel);
         }
      });
      bottomPanel.add(msgLabel);
      bottomPanel.add(signupButton);


      frame.add(northPanel,BorderLayout.NORTH);
      frame.add(centerPanel,BorderLayout.CENTER);

      frame.add(bottomPanel,BorderLayout.SOUTH);

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   private void login()
   {
      int id = Integer.parseInt(userIDField.getText());
      String pin = new String(pinField.getPassword());
      if(hotel.authentification(id,pin )) 
      {
         User user = hotel.findUserByID(id);
         assert(user.getClass() == Guest.class);
         if(guestRadio.isSelected() && user.getClass() == Guest.class) {
            new UserFrame(hotel, (Guest) user);
         }

         else if(managerRadio.isSelected() && user.getClass() == Manager.class)
         {
            new ManagerFrame(hotel);
         }
         else 
            JOptionPane.showMessageDialog(frame, "invalid username or password", "Hotel Message",
                     JOptionPane.WARNING_MESSAGE);
      }
      else 
         JOptionPane.showMessageDialog(frame, "invalid username or password", "Hotel Message",
                  JOptionPane.WARNING_MESSAGE);
   }

   // load if file exists
   private void load()
   {
      // load reservation.txt if it exists
      File file = new File("Reservation.txt");

      ObjectInputStream in = null;
      try
      {
         in = new ObjectInputStream(new FileInputStream(file));
         hotel = (Hotel) in.readObject();
         in.close();
      } catch (IOException exception)
      {
         //JOptionPane.showMessageDialog(null, exception);
      }
      catch (ClassNotFoundException exception)
      {
         //JOptionPane.showMessageDialog(null, exception);
      }
   }
}