package electrcity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
/**/
public class pay_bill extends JFrame implements ActionListener {
    Choice  searchMonthCho;
    String meter;
    JButton pay,back;
    pay_bill(String meter){
        super("Pay Bill");
        this.meter=meter;

        setSize(900,600);
//      setBackground(new Color(135, 206, 250));
        setLocation(300,150);

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(135, 206, 250)); // Sky Blue
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);


        JLabel heading = new JLabel("Pay Bill");
        heading.setFont(new Font("serif",Font.BOLD,20));
        heading.setBounds(120,5,400,30);
        backgroundPanel.add(heading);

        JLabel meterNumber = new JLabel("Meter Number");
        meterNumber.setBounds(35,80,200,20);
        backgroundPanel.add(meterNumber);

        JLabel meterNumberText = new JLabel("");
        meterNumberText.setBounds(300,80,200,20);
        backgroundPanel.add(meterNumberText);

        JLabel name = new JLabel("Name");
        name.setBounds(35,140,200,20);
        backgroundPanel.add(name);

        JLabel nameText = new JLabel("");
        nameText.setBounds(300,140,200,20);
        backgroundPanel.add(nameText);

        JLabel month= new JLabel("Month");
        month.setBounds(35,200,200,20);
        backgroundPanel. add(month);

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
        searchMonthCho.setBounds(300,200,150,20);
        backgroundPanel.add( searchMonthCho);


        JLabel unit= new JLabel("Unit");
        unit.setBounds(35,260,200,20);
        backgroundPanel.add(unit);

        JLabel unitText = new JLabel("");
        unitText.setBounds(300,260,200,20);
        backgroundPanel.add(unitText);

        JLabel totalBill= new JLabel("Total Bill");
        totalBill.setBounds(35,320,200,20);
        backgroundPanel.add(totalBill);

        JLabel totalBillText = new JLabel("");
        totalBillText.setBounds(300,320,200,20);
        backgroundPanel.add(totalBillText);

        JLabel status= new JLabel("Status");
        status.setBounds(35,380,200,20);
        backgroundPanel.add(status);

        JLabel statusText = new JLabel("");
        statusText.setBounds(300,380,200,20);
        statusText.setForeground(Color.RED);
        backgroundPanel.add(statusText);

        try{
            Database c = new Database();
            ResultSet rs = c.statement.executeQuery("Select * from newcustomer where meter_no = '"+meter+"'");
            while(rs.next()){
                meterNumberText.setText(meter);
                nameText.setText(rs.getString("name"));
            }
        }catch(Exception E){
            E.printStackTrace();
        }

        searchMonthCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Database c = new Database();
                try{
                    ResultSet rs = c.statement.executeQuery("select * from bill where meter_no='"+meter+"'and month='"+searchMonthCho.getSelectedItem()+"'");
                    while(rs.next()){
                        unitText.setText(rs.getString("unit"));
                        totalBillText.setText(rs.getString("total_bill"));
                        statusText.setText(rs.getString("status"));
                    }
                }catch(Exception E){
                    E.printStackTrace();
                }
            }
        });

        pay = new JButton("Pay");
        pay.setBackground(Color.black);
        pay.setForeground(Color.white);
        pay.setBounds(100,460,100,25);
        pay.addActionListener(this);
        backgroundPanel.add(pay);

        back = new JButton("Back");
        back.setBackground(Color.black);
        back.setForeground(Color.white);
        back.setBounds(230,460,100,25);
        back.addActionListener(this);
        backgroundPanel.add(back);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pay){
            try{
                Database c = new Database();
                c.statement.executeUpdate("update bill  set status='Paid' where meter_no='"+meter+"'and month='"+searchMonthCho.getSelectedItem()+"' ");
            }catch(Exception E){
                E.printStackTrace();
            }
            setVisible(false);
            new payment_bill(meter);
        }else{
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new pay_bill("");
    }
}
