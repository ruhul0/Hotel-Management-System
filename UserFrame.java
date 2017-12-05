import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;


public class UserFrame
{
   private Guest guest;
   private Hotel hotel;
   private int transactionID;
    UserFrame(Hotel h, Guest g)
    {
        hotel = h;
        guest = g;
        final Random rand = new Random();
        final JFrame frame = new JFrame();
        frame.setTitle("Hotel User Interface");
        frame.setSize(600,200);
        
        //title
        JPanel northPanel = new JPanel();
        JLabel label = new JLabel("Welcome to hotel: " + guest.getName());
        label.setFont(new Font("Serif", Font.PLAIN, 18));
        northPanel.add(label);   
        
        //buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1,2));
        JButton makeReservationButton = new JButton("Make a Reservation");
        makeReservationButton.setFont(new Font("Serif", Font.PLAIN, 20));
        makeReservationButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

               transactionID = rand.nextInt();
               new ReservationFrame(hotel, guest,transactionID);
                
            }
        });
        JButton viewOrCancelButton = new JButton("View/Cancel a Reservation");

        viewOrCancelButton.setFont(new Font("Serif", Font.PLAIN, 20));
        viewOrCancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               new ViewCancelReservationFrame(hotel, guest);

            }
        });
        bottomPanel.add(makeReservationButton);
        bottomPanel.add(viewOrCancelButton);
        
        //finished frame
        frame.add(northPanel, BorderLayout.NORTH);
        //frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //debate on exit
        frame.setVisible(true);
    }
}
