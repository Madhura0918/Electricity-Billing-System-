package electrcity.billing.system;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class customer_details extends JFrame implements ActionListener {
    Choice searchMeterCho,searchNameCho;
    JTable table;
    JButton search ,print,close;
    customer_details(){
        super("Customer Detail");
        getContentPane().setBackground(new Color(135,206,250));
        setSize(700,500);
        setLocation(400,200);
        setLayout(null);

        JLabel searchMeter= new JLabel("Search By Meter Number");
        searchMeter.setBounds(20,30,150,20);
        add(searchMeter);

        searchMeterCho = new Choice();
        searchMeterCho.setBounds(180,20,150,20);
        add(searchMeterCho);
        try{
            Database c = new Database();
            ResultSet rs = c.statement .executeQuery("select * from newcustomer");
            while(rs.next()){
                searchMeterCho.add(rs.getString("meter_no"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        JLabel searchName= new JLabel("Search By Name");
        searchName.setBounds(400,20,100,20);
        add(searchName);

        searchNameCho = new Choice();
        searchNameCho.setBounds(520,20,150,20);
        add(searchNameCho);
        try{
            Database c = new Database();
            ResultSet rs = c.statement .executeQuery("select * from newcustomer");
            while(rs.next()){
                searchNameCho.add(rs.getString("name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        table = new JTable();
        try{
            Database c = new Database();
            ResultSet rs = c.statement.executeQuery("select * from newcustomer");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,100,700,500);
        scrollPane.setBackground(Color.white);
        add(scrollPane);

         search = new JButton("Search");
         search.setBackground(Color.white);
         search.setBounds(20,70,80,20);
         search.addActionListener(this);
         add(search);

        print = new JButton("Print");
        print.setBackground(Color.white);
        print.setBounds(120,70,80,20);
        print.addActionListener(this);
        add(print);

        close = new JButton(" Close");
        close.setBackground(Color.white);
        close.setBounds(600,70,80,20);
        close.addActionListener(this);
        add( close);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==search){
            String query_search="select * from newcustomer where meter_no='"+searchMeterCho.getSelectedItem()+"'and name='"+searchNameCho.getSelectedItem()+"'";
            try{
                Database c = new Database();
                ResultSet rs = c.statement.executeQuery(query_search);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            }catch(Exception E){
                E.printStackTrace();
            }
        }else if(e.getSource()==print){
            try{
                table.print();
            }catch(Exception E){
                E.printStackTrace();
            }
        } else  {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
         new  customer_details();
    }
}
