
import java.io.*;
import java.util.*;

import javax.swing.event.*;

public class Hotel implements Serializable
{
   public Hotel()
   {
      rooms = new ArrayList<Room>();
      //10 economic rooms
      for(int i = 0; i < 10; i++){
         rooms.add(new Room(i, 2000, new Reservations() ));
      }
      //10 luxurious rooms
      for(int i = 10; i < 20; i++){
         rooms.add(new Room(i, 800, new Reservations() ));
      }
      users = new ArrayList<User>();
   }

   public Iterator<Room> roomIterator()
   {
      return rooms.iterator();
   }

   public Iterator<User> userIterator()
   {
      return users.iterator();
   }

   public void addUser(User user)
   {
      users.add(user);
   }


   public void addRoom(Room r)
   {
      rooms.add(r);
   }


   public User findUserByID(int id)
   {
      Iterator<User> it = userIterator();

      while(it.hasNext())
      {
         User user = it.next();
         if(user.getID() == id){
            return user;
         }
      }
      return null;
   }

   public boolean authentification(int id, String pin)
   {
      User user = findUserByID(id);
      if (user != null) return user.getPassword().equals(pin);
      return false;
   }


   public void changeAvailableRooms(Calendar startDate, Calendar endDate, int roomType)
   {
      Iterator<Room> it = roomIterator();
      availableRooms = new ArrayList<Room>();
      while(it.hasNext())
      {
         Room room = it.next();
         if(room.isType(roomType) && room.isAvailable(startDate, endDate)) {
            // availableRooms += (room.toString() + '\n');
            availableRooms.add(room);
         }
      }
      ChangeEvent e = new ChangeEvent(this);
      for(ChangeListener c : listeners)
      {
         c.stateChanged(e);
      }
   }


   public ArrayList<Room> getAvailableRooms()
   {
      return availableRooms;
   }

   public void attach(ChangeListener changeListener)
   {
      listeners.add(changeListener);
   }

   public void cancelReservation(Reservation r)
   {
      Iterator<Room> it = roomIterator();
      while(it.hasNext())
      {
         Room room = it.next();
         if(room.getRoomNumber() == r.getRoomNumber()) {
            room.cancelReservation(r); 
            return;
         }
      }
   }
   

   public ArrayList<Reservation> getReservations(Guest guest)
   {
      Iterator<Room> it = roomIterator();
      ArrayList<Reservation> rlist = new ArrayList<Reservation>();
      while(it.hasNext())
      {
         Room room = it.next();
         
         Reservations rs = room.getReservations();
         Iterator<Reservation> it1 = rs.getReservationByUser(guest.getID());
         while(it1.hasNext())rlist.add(it1.next());
         
      }
      return rlist;
   }


   public ArrayList<Reservation> getReservations(Date d)
   {
      Iterator<Room> it = roomIterator();
      ArrayList<Reservation> rlist = new ArrayList<Reservation>();
      while(it.hasNext())
      {
         Room room = it.next();        
         Reservations rs = room.getReservations();
         Iterator<Reservation> it1 = rs.getReservationByDate(d);
         while(it1.hasNext())rlist.add(it1.next());       
      }
      return rlist;
   }
   
   public ArrayList<Room> getRooms()
   {
      return rooms;
   }
   
   private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException
   {
      in.defaultReadObject();
      listeners = new ArrayList<ChangeListener>();
   }

   private ArrayList<Room> rooms;
   private ArrayList<User> users;
   private transient ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
   private ArrayList<Room> availableRooms;
   
}
