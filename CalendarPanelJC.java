import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;


public class CalendarPanelJC extends JPanel {
   protected int yy;
   protected int mm, dd;
   Calendar calendar = new GregorianCalendar();
   protected final int thisYear = calendar.get(Calendar.YEAR);
   protected final int thisMonth = calendar.get(Calendar.MONTH);
   private JComboBox<String> monthChoice;
   private JComboBox<Integer> yearChoice;


   private JTable jtable = new JTable();
   private DefaultTableModel dtm = new DefaultTableModel();
   private JPanel tablePanel = new JPanel();
   private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
   private Point point = null;
   String[] months = { "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" };

   CalendarPanelJC() {
      super();
      setYYMMDD(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
               calendar.get(Calendar.DAY_OF_MONTH));
      buildGUI();
      recompute();
   }

   CalendarPanelJC(int year, int month, int today) {
      super();
      setYYMMDD(year, month, today);
      buildGUI();
      recompute();
   }

   private void setYYMMDD(int year, int month, int today) {
      yy = year;
      mm = month;
      dd = today;
   }

   private void buildGUI() {
      setBorder(BorderFactory.createEtchedBorder());
      setLayout(new BorderLayout());

      //month choice
      monthChoice = new JComboBox<String>();
      for (int i = 0; i < months.length; i++)
         monthChoice.addItem(months[i]);
      monthChoice.setSelectedItem(months[mm]);
      monthChoice.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            int i = monthChoice.getSelectedIndex();
            if (i >= 0) {
               mm = i;
               recompute();
               changeSelectedDay();
            }
         }
      });
      monthChoice.getAccessibleContext().setAccessibleName("Months");

      //year choice
      yearChoice = new JComboBox<Integer>();
      yearChoice.setEditable(true);
      for (int i = yy - 5; i < yy + 5; i++)
         yearChoice.addItem(i);
      yearChoice.setSelectedItem(yy);
      yearChoice.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            int i = yearChoice.getSelectedIndex();
            if (i >= 0) {
               yy = (int) yearChoice.getSelectedItem();
               recompute();
               changeSelectedDay();
            }
         }
      });

      JPanel monthYearPanel = new JPanel();
      monthYearPanel.add(monthChoice);
      monthYearPanel.add(yearChoice);

      //calendar panel
      tablePanel.setLayout(new BorderLayout());
      tablePanel.add(monthYearPanel, BorderLayout.NORTH);

      String[] str = {"S","M","T","W","R","F","S"};
      for(int i = 0; i < str.length; i++)
      {
         dtm.addColumn(str[i]);
      }

      //No resize or reorder
      jtable.getTableHeader().setResizingAllowed(false);
      jtable.getTableHeader().setReorderingAllowed(false);

      //Single cell selection
      jtable.setColumnSelectionAllowed(true);
      jtable.setRowSelectionAllowed(true);
      jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      jtable.setRowHeight(30);
      dtm.setColumnCount(7);
      dtm.setRowCount(6);
      recompute();

      //model mutator
      jtable.addMouseListener(new MouseAdapter()
      {
         public void mouseClicked(MouseEvent e)
         {
            point  = e.getPoint();
            changeSelectedDay();
         }
      });


      jtable.setModel(dtm);
      tablePanel.add(new JScrollPane(jtable));
      add(BorderLayout.CENTER, tablePanel);
   }


   protected void recompute() {

      int numberOfDays, startOfMonth; 

      //Clear table
      for (int i=0; i<6; i++){
         for (int j=0; j<7; j++){
            dtm.setValueAt(null, i, j);
         }
      }

      //Get first day of month and number of days
      GregorianCalendar cal = new GregorianCalendar(yy, mm, 1);
      numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
      startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

      //Draw calendar
      for (int i=1; i<=numberOfDays; i++){
         int row = new Integer((i+startOfMonth-2)/7);
         int column  =  (i+startOfMonth-2)%7;
         dtm.setValueAt(i, row, column);
      }
   }


   public void setDate(int yy, int mm, int dd) {
      this.yy = yy;
      this.mm = mm;
      this.dd = dd;
      recompute();
   }


   public int getYY(){
      return yy;  
   }
   public int getMM()
   {
      return mm;
   }
   public int getDD()
   {
      return dd;
   }

   public void addListener(ChangeListener c)
   {
      listeners.add(c);
   }

   //notify method
   private void changeSelectedDay()
   {
      if(point != null){
         int col = jtable.columnAtPoint(point);
         int row = jtable.rowAtPoint(point);
         Object o =  dtm.getValueAt(row, col);  
         if(o instanceof Integer) 
         {
            dd = (int) o;
            ChangeEvent changeEvent = new ChangeEvent(this);
            for(ChangeListener c : listeners)
            {
               //notify
               c.stateChanged(changeEvent);
            }
         }
      }
   }

}
