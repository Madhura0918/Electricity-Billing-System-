package electrcity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class generate_bill extends JFrame implements ActionListener {
    Choice searchMonthCho;
    String meter;
    JTextArea area;
    JButton bill;
    generate_bill(String meter){
        this.meter=meter;
        setSize(500,700);
        setLocation(500,30);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);

        JPanel panel = new JPanel();
        panel.setBackground(Color.black);

        JLabel heading = new JLabel("Generate Bill");
        heading.setForeground(Color.white);

        JLabel meter_no = new JLabel(meter);

        searchMonthCho = new Choice();
        searchMonthCho.add("January");
        searchMonthCho.add("February");
        searchMonthCho.add("March");
        searchMonthCho.add("April");
        searchMonthCho.add("May");
        searchMonthCho.add("June");
        searchMonthCho.add("July");
        searchMonthCho.add("August");
        searchMonthCho.add("September");
        searchMonthCho.add("October");
        searchMonthCho.add("November");
        searchMonthCho.add("December");

        area=new JTextArea(50,15);
        area.setText("\n\n\t--------------------Click on the ----------------------\n \t ----------------Generate Bill");
        area.setFont(new Font("Sanserif",Font.ITALIC,15));
        area.setBackground(new Color(135, 206, 250));
        JScrollPane pane = new JScrollPane(area);
        bill=new JButton("Generate Bill");
        bill.addActionListener(this);
        add(pane);
        panel.add(heading);
        panel.add(meter_no);
        panel.add(searchMonthCho);
        add(panel,"North");
        bill.setBackground(Color.black);
        bill.setForeground(Color.white);
        add(bill,"South");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            Database c = new Database();
            String smonth = searchMonthCho.getSelectedItem();
            area.setText("\n Power Limited \nElectricity Bill for month of "+smonth+",2025\n\n\n");
            ResultSet rs = c.statement.executeQuery("Select * from newcustomer where meter_no = '"+meter+"'");
            if (rs.next()){
                area.append("\n    Customer Name        : "+rs.getString("name"));
                area.append("\n    Customer Meter Number: "+rs.getString("meter_no"));
                area.append("\n    Customer Address     : "+rs.getString("address"));
                area.append("\n    Customer City        : "+rs.getString("city"));
                area.append("\n    Customer State       : "+rs.getString("state"));
                area.append("\n    Customer Email       : "+rs.getString("email"));
                area.append("\n    Customer Phone Number       : "+rs.getString("phone_no"));

            }

            rs = c.statement.executeQuery("select * from meter_info where meter_number ='"+meter+"'");
            if (rs.next()){
                area.append("\n    Customer Meter Location        : "+rs.getString("meter_location"));
                area.append("\n    Customer Meter Type: "+rs.getString("meter_type"));
                area.append("\n    Customer Phase Code   : "+rs.getString("phase_code"));
                area.append("\n    Customer Bill Type        : "+rs.getString("bill_type"));
                area.append("\n    Customer Days      : "+rs.getString("Days"));


            }
            rs = c.statement.executeQuery("select * from tax");
            if (rs.next()){
                area.append("\n    Cost Per Unit        : "+rs.getString("cost_per_unit"));
                area.append("\n   Meter Rent: "+rs.getString("meter_rent"));
                area.append("\n   Service Charge   : "+rs.getString("service_charge"));
                area.append("\n   Service Tax        : "+rs.getString("service_tax"));
                area.append("\n   Swacch Bharat      : "+rs.getString("swachh_bharat"));
                area.append("\n   Fixed Tax     : "+rs.getString("fixed_tax"));

            }
            rs = c.statement.executeQuery("select * from bill where meter_no = '"+meter+"' and month = '"+searchMonthCho.getSelectedItem()+"'");
            if (rs.next()) {
                area.append("\n    Current Month       : " + rs.getString("month"));
                area.append("\n   Units Consumed: " + rs.getString("unit"));
                area.append("\n   Total Charges   : " + rs.getString("total_bill"));
                area.append("\n Total Payable: "+rs.getString("total_bill"));
            }

        }catch(Exception E){
            E.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new generate_bill("");
    }
}
