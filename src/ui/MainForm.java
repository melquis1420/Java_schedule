package ui;

import business.ContactBusiness;
import entity.ContactEntity;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JButton buttonNewContact;
    private JButton buttonRemove;
    private JTable tableContacts;
    private JLabel labelContactCount;

    private ContactBusiness mContactBusiness;
    private String mName= "";
    private String mPhone= "";


    public MainForm() {

        setContentPane(rootPanel); //mostra qual painel deve chamar
        setSize(500, 250); //define o tamanho do painel
        setVisible(true); //deixa o painel visível


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //pegando o tamanho de
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //encerra o programa ao ser fechado


        mContactBusiness = new ContactBusiness();


        setListeners();
        loadContacts();

    }

    private void loadContacts() {
        List<ContactEntity> contactList = mContactBusiness.getList();

        String[] columnNames = {"Nome","Telefone"};
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        for (ContactEntity i : contactList) {
            Object[] o = new Object[2];


            o[0]= i.getName();
            o[1]= i.getPhone();

            model.addRow(o);

        }

        tableContacts.clearSelection();
        tableContacts.setModel(model);

        labelContactCount.setText(mContactBusiness.getContactCountDescription());
    }

    private void setListeners() {
        buttonNewContact.addActionListener(new ActionListener() { //criou uma classe para responder ao evento de clicar.
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContactForm();
                dispose(); //dispose serve para encerrar a janela aberta
            }
        });

        tableContacts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (e.getValueIsAdjusting()){

                   if (tableContacts.getSelectedRow() != -1){
                       mName= tableContacts.getValueAt(tableContacts.getSelectedRow(), 0).toString();
                       mPhone= tableContacts.getValueAt(tableContacts.getSelectedRow(), 1).toString();
                   }

                }

            }
        });


        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  try{
                    mContactBusiness.delete(mName, mPhone);
                    loadContacts();

                    mName= "";
                    mPhone="";


                } catch (Exception excp){
                    JOptionPane.showMessageDialog(new JFrame(), excp.getMessage());

                }

            }
        });
    }
}
