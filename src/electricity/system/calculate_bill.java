package electrcity.billing.system;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class calculate_bill extends JFrame implements ActionListener {
        JLabel nameText,addressText;
        TextField unitText;
        Choice meternumCho,monthCho;
        JButton submit,cancel;
    calculate_bill(){
        super("Calculate Bill");
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(135,206,250));
        add(panel);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(70,10,300,20);
        heading.setFont(new Font("serif",Font.BOLD,20));
        panel.add(heading);

        JLabel meternum = new JLabel("Meter Number");
        meternum.setBounds(50,80,100,20);
        panel.add(meternum);

        meternumCho=new Choice();
        try{
            Database c = new Database();
            ResultSet rs = c.statement.executeQuery("select * from newcustomer");

            while(rs.next()){
                meternumCho.add(rs.getString("meter_no"));
            }
        }catch(Exception E){
            E.printStackTrace();
        }
        meternumCho.setBounds(180,80,100,20);
        panel.add(meternumCho);

        JLabel name = new JLabel("Name");
        name.setBounds(50,120,100,20);
        panel.add(name);

        nameText = new JLabel(" ");
        nameText.setBounds(180,120,150,20);
        panel.add(nameText);

        JLabel address = new JLabel("Address");
        address.setBounds(50,160,100,20);
        panel.add(address);


        addressText= new JLabel("");
        addressText.setBounds(180,160,150,20);
        panel.add(addressText);

        try{
            Database c = new Database();
            ResultSet rs= c.statement.executeQuery("Select * from newcustomer where meter_no='"+meternumCho.getSelectedItem()+"' ");
            while(rs.next()){
                nameText.setText(rs.getString("name"));
                addressText.setText(rs.getString("address"));
            }
        }catch(Exception E){
            E.printStackTrace();
        }
        meternumCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try{
                    Database c = new Database();
                    ResultSet rs= c.statement.executeQuery("Select * from newcustomer where meter_no='"+meternumCho.getSelectedItem()+"' ");
                    while(rs.next()){
                        nameText.setText(rs.getString("name"));
                        addressText.setText(rs.getString("address"));
                    }
                }catch(Exception E){
                    E.printStackTrace();
                }
            }
        });

        JLabel unitconsumed = new JLabel("Unit Consumed");
        unitconsumed.setBounds(50,200,100,20);
        panel.add(unitconsumed);

        unitText=new TextField();
        unitText.setBounds(180,200,150,20);
        panel.add(unitText);

        JLabel month = new JLabel("Month");
        month.setBounds(50,240,100,20);
        panel.add(month);

        monthCho = new Choice();
        monthCho.add("January");
        monthCho.add("February");
        monthCho.add("March");
        monthCho.add("April");
        monthCho.add("May");
        monthCho.add("June");
        monthCho.add("July");
        monthCho.add("August");
        monthCho.add("September");
        monthCho.add("October");
        monthCho.add("November");
        monthCho.add("December");
        monthCho.setBounds(180,240,150,20);
        panel.add(monthCho);

        submit = new JButton("Submit");
        submit.setBounds(80,300,100,25);
        submit.setBackground(Color.black);
        submit.setForeground(Color.white);
        submit.addActionListener(this);
        panel.add(submit);

        cancel = new JButton("Cancel");
        cancel.setBounds(220,300,100,25);
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.addActionListener(this);
        panel.add(cancel);


        setLayout(new BorderLayout());
        add(panel,"Center");
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/budget.png"));
        Image image = imageIcon.getImage().getScaledInstance(250,200,Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel imageLabel = new JLabel(imageIcon1);
        add(imageLabel,"East");

        setSize(675,400);
        setLocation(400,200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            String smeterNo= meternumCho.getSelectedItem();
            String sunit=unitText.getText();
            String smonth=monthCho.getSelectedItem();

            int totalBill =0;
            int units=Integer.parseInt(sunit);
            String query_tax="select * from tax";
            try{
                Database c= new Database();
                ResultSet rs =c.statement.executeQuery(query_tax);
                while(rs.next()){
                    totalBill +=units * Integer.parseInt(rs.getString("cost_per_unit"));
                    totalBill += Integer.parseInt(rs.getString("meter_rent"));
                    totalBill += Integer.parseInt(rs.getString("service_charge"));
                    totalBill += Integer.parseInt(rs.getString("swachh_bharat"));
                    totalBill += Integer.parseInt(rs.getString("fixed_tax"));
                }
            }catch(Exception E){
                E.printStackTrace();
            }
            String query_total_bill="insert into bill values('"+smeterNo+"','"+smonth+"','"+sunit+"','"+totalBill+"','Not Paid')";
            try{
                Database c = new Database();
                c.statement.executeUpdate(query_total_bill);
                JOptionPane.showMessageDialog(null,"Customer bill updated successfully");
                setVisible(false);
            }catch(Exception E){
                E.printStackTrace();
            }
        }else{
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new calculate_bill();
    }
}
