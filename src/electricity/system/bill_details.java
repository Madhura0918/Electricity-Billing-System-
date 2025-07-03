package electrcity.billing.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class bill_details extends JFrame {
    String meter;
    bill_details(String meter){
        super("Bill details");
        this.meter=meter;
        setSize(700,650);
        setLocation(400,150);
        setLayout(null);
        getContentPane().setBackground(new Color(135, 206, 250));

        JTable table = new JTable();

        try{
            Database c = new Database();
            String query_bill= "Select * from bill where meter_no ='"+meter+"'";
            ResultSet rs = c.statement.executeQuery(query_bill);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0,0,700,650);
        add(sp);

        setVisible(true);

    }

    public static void main(String[] args) {
        new bill_details("");
    }
}
