package ui;

import business.ContactBusiness;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactForm extends JFrame {

    private JPanel rootPanel;
    private JTextField textName;
    private JTextField textPhone;
    private JButton buttonCancel;
    private JButton buttonSave;

    private ContactBusiness mContactBusiness;

    public ContactForm() {

        setContentPane(rootPanel); //mostra qual painel deve chamar
        setSize(500,250); //define o tamanho do painel
        setVisible(true); //deixa o painel visível

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //pegando o tamanho de todo o monitor.
        setLocation(dim.width/2 - getSize().width/2, dim.height/2 - getSize().height/2);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //encerra o programa ao ser fechado

        mContactBusiness = new ContactBusiness();
        setListeners();
    }

    private void setListeners(){
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm();
                dispose();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //colocado dentro de um try catch para tratar o erro
                try{
                    String name= textName.getText();
                    String phone= textPhone.getText();

                    mContactBusiness.save(name, phone);


                    new MainForm();
                    dispose();

                } catch (Exception excp){
                    JOptionPane.showMessageDialog(new JFrame(), excp.getMessage());

                }
            }
        });
    }
}
