package electrcity.billing.system;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class update_information extends JFrame implements ActionListener {
    JLabel nametext;
    JTextField addressText,cityText,stateText,emailText,phoneText;
    String meter;
    JButton update,cancel;
    update_information(String meter){
        this.meter=meter;
        setBounds(400,150,777,450);
        getContentPane().setBackground(new Color(135,206,250));
        setLayout(null);

        JLabel heading = new JLabel("Update Customer Information");
        heading.setBounds(50,10,400,40);
        heading.setFont(new Font("serif",Font.BOLD,20));
        add(heading);

        JLabel name = new JLabel("Name");
        name.setBounds(30,70,100,20);
        add(name);

        nametext = new JLabel("");
        nametext.setBounds(150,70,200,20);
        add(nametext);

        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30,110,100,20);
        add(meterNo);

        JLabel meterText = new JLabel("");
        meterText.setBounds(150,110,100,20);
        add(meterText);

        JLabel address = new JLabel("Address");
        address.setBounds(30,150,100,20);
        add(address);

        addressText= new JTextField();
        addressText.setBounds(150,150,200,20);
        add(addressText);

        JLabel city = new JLabel("City");
        city.setBounds(30,190,100,20);
        add(city);

        cityText=new JTextField();
        cityText.setBounds(150,190,200,20);
        add(cityText);

        JLabel state = new JLabel("State");
        state.setBounds(30,230,100,20);
        add(state);

        stateText=new JTextField();
        stateText.setBounds(150,230,200,20);
        add(stateText);

        JLabel email = new JLabel("Email");
        email.setBounds(30,270,100,20);
        add(email);

        emailText = new JTextField();
        emailText.setBounds(150,270,200,20);
        add(emailText);

        JLabel phone = new JLabel("Phone");
        phone.setBounds(30,310,100,20);
        add(phone);

        phoneText= new JTextField();
        phoneText.setBounds(150,310,200,20);
        add(phoneText);

        try{
            Database c = new Database();
            ResultSet rs = c.statement.executeQuery("select * from newcustomer where meter_no='"+meter+"'");
            if(rs.next()){
                nametext.setText(rs.getString("name"));
                meterText.setText(rs.getString("meter_no"));
                addressText.setText(rs.getString("address"));
                cityText.setText(rs.getString("city"));
                stateText.setText(rs.getString("state"));
                emailText.setText(rs.getString("email"));
                phoneText.setText(rs.getString("phone_no"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        update=new JButton("Update");
        update.setBackground(Color.black);
        update.setForeground(Color.white);
        update.setBounds(50,360,120,20);
        update.addActionListener(this);
        add(update);

        cancel=new JButton("Cancel");
        cancel.setBackground(Color.black);
        cancel.setForeground(Color.white);
        cancel.setBounds(200,360,120,20);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/img_1.png"));
        Image image= imageIcon.getImage().getScaledInstance(400,410,Image.SCALE_DEFAULT);
        ImageIcon imageIcon1 = new ImageIcon(image);
        JLabel imgLabel = new JLabel(imageIcon1);
        imgLabel.setBounds(360,0,400,410);
        add(imgLabel);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==update)
        {
            String saddress = addressText.getText();
            String scity=cityText.getText();
            String sstate = stateText.getText();
            String semail = emailText.getText();
            String sphone = phoneText.getText();

            try{
                Database c = new Database();
                c.statement.executeUpdate("update newcustomer set address ='"+saddress+"',city='"+scity+"',state='"+sstate+"',email='"+semail+"',phone='"+sphone+"' where meter_no='"+meter+"'");

                JOptionPane.showMessageDialog(null,"User information updated successfully ");
                setVisible(false);
            } catch (Exception E) {
                    E.printStackTrace();
            }
        }else{
            setVisible(false);
        }
    }

    public static void main(String[] args) {

        new update_information("");
    }
}
