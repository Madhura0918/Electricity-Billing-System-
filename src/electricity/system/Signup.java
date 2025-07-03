package electrcity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {

    Choice loginASChO;
    TextField meterText,EmployerText,UserNameText,NameText,PasswordText;
    JButton create,back;
    Signup(){
        super("Signup Page");
        getContentPane().setBackground(new Color(135,206,250));

        /* Login as customer or admin */
        JLabel createAs = new JLabel("Create Account As");
        createAs.setBounds(30,50,125,20);
        add(createAs);

        loginASChO =new Choice();
        loginASChO.add("Admin");
        loginASChO.add("Customer");
        loginASChO.setBounds(170,50,120,20);
        add(loginASChO);

        /* Meter */
        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30,100,125,20);
        meterNo.setVisible(false);
        add(meterNo);

        meterText=new TextField();
        meterText.setBounds(170,100,125,20);
        meterText.setVisible(false);
        add(meterText);

        /* Employee */
        JLabel Employer = new JLabel("Employer ID");
        Employer.setBounds(30,100,125,20);
        Employer.setVisible(true);
        add(Employer);

        EmployerText=new TextField();
        EmployerText.setBounds(170,100,125,20);
        Employer.setVisible(true);
        add(EmployerText);

        /* Username */
        JLabel userName=new JLabel("UserName");
        userName.setBounds(30,140,125,20);
        add(userName);

        UserNameText = new TextField();
        UserNameText.setBounds(170,140,125,20);
        add(UserNameText);

        /* Name */
        JLabel name = new JLabel("Name");
        name.setBounds(30,180,125,20);
        add(name);

        NameText = new TextField("");
        NameText.setBounds(170,180,125,20);
        add(NameText);

        meterText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    Database c = new Database();
                    ResultSet rs = c.statement.executeQuery("select * from signup where meter_no='"+meterText.getText()+"'");
                    if(rs.next()){
                        NameText.setText(rs.getString("name"));
                    }
                }catch(Exception E){
                    E.printStackTrace();
                }
            }
        });

        /* Password */
        JLabel Password = new JLabel("Password");
        Password.setBounds(30,220,125,20);
        add(Password);

        PasswordText = new TextField();
        PasswordText.setBounds(170,220,125,20);
        add(PasswordText);


        loginASChO.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginASChO.getSelectedItem();
                if(user.equals("Customer"))
                {
                    Employer.setVisible(false);
                    NameText.setEditable(false);
                    EmployerText.setVisible(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                }else {
                    Employer.setVisible(true);
                    EmployerText.setVisible(true);
                    meterNo.setVisible(false);
                    meterText.setVisible(false);
                }
            }
        });

        create = new JButton("Create");
        create.setBackground(new Color(66,127,219));
        create.setForeground(Color.black);
        create.setBounds(50,285,100,25);
        create.addActionListener(this);
        add(create);

        back = new JButton("Back");
        back.setBackground(new Color(66,127,219));
        back.setForeground(Color.black);
        back.setBounds(180,285,100,25);
        back.addActionListener(this);
        add(back);

        /* Image */
        ImageIcon boyIcon = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image boyImg = boyIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
        ImageIcon boyIcon2 = new ImageIcon(boyImg);

        JLabel boyLabel = new JLabel(boyIcon2);
        boyLabel.setBounds(320,30,250,250);
        add(boyLabel);

        setSize(600,380);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==create){
            String sloginAs = loginASChO .getSelectedItem();
            String susername=UserNameText.getText();
            String sname = NameText.getText();
            String spassword =PasswordText.getText();
            String smeter =meterText.getText();
            try{
                Database c = new Database();
                String query=null;
                if(sloginAs.equals("Admin")) {
                    query = "insert into signup value('" + smeter + "','" + susername + "','" + sname + "','" + spassword + "','" + sloginAs + "')";
                }else{
                    query="update  signup set username = '"+susername+"',password ='"+spassword+"',usertype = '"+sloginAs+"' where meter_no = '"+smeter+"' ";
                }
                System.out.println("Executing SQL: " + query);

                c.statement.executeUpdate(query);
                
                JOptionPane.showMessageDialog(null,"Account Created successfully");
                setVisible(false);
                new Login();

            }catch(Exception E){
                E.printStackTrace();
            }
        } else if (e.getSource()==back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String [] args){
        new Signup();
    }
}
