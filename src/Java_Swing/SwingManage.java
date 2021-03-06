package Java_Swing;

import Product.*;
import Read_Write_file.IO_Read_Write_File;
import Regex.AccountPasswordExample;
import Regex.PhoneNumberExample;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SwingManage extends JFrame {
    private JList<Homestay> listHomestay;
    private JTextField textNameHs;
    private JTextField textPriceHs;
    private JTextField textPhoneHs;
    private JTextField textQAddressHs;
    private JTextField textHighlightHs;
    private JButton addHs;
    private JButton deleteHs;
    private JList<Customer> listCustomer;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textDayBirth;
    private JTextField textNationlity;
    private JButton saveCustomer;
    private JTextField textAccount;
    private JTextField textPassWord;
    private JComboBox<String> comboBoxGender;
    private JPanel swingManage;
    private JButton deleteCustomer;
    private JTextField textAddressHs;
    private JButton saveHs;
    private JButton logOut;
    private JButton addCustomerButton;
    private JTextField textAccHs;
    private JTextField textPassHs;
    private JLabel LabelHs;
    private JLabel LabelCus;
    private JList<HomestayOfCus_Date> HomeOfCusList;
    private JList<CustomerOfHs_Date> CusOfHomesList;
    private JLabel labelCusOfHs;
    private JLabel labelHomesOfCus;
    private static final SwingAccount swingAccount = new SwingAccount();
    private static final SwingManage manage = new SwingManage();
    private static final String PATH_CUSTOMER = "file_Data/customer";
    private static final String PATH_HOMESTAY = "file_Data/homestay";
    private static final IO_Read_Write_File<Customer> Read_Write_file = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<Homestay> Read_Write_file1 = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<HomestayOfCus_Date> fileCusDate = new IO_Read_Write_File<>();
    private static final IO_Read_Write_File<CustomerOfHs_Date> fileHomeDate = new IO_Read_Write_File<>();
    private static ArrayList<Customer> customers;
    private static ArrayList<Homestay> homestays;
//    private static ArrayList<HomestayOfCus_Date> homeOfCus_Dates;
//    private static ArrayList<CustomerOfHs_Date> cusOfHome_Dates;
    private static final AccountAdmin accountAdmins = new AccountAdmin();
    private static final AccountPasswordExample accountPasswordExample = new AccountPasswordExample();
    private static final PhoneNumberExample phoneNumberExample = new PhoneNumberExample();
    private static DefaultListModel<Homestay> listHomestayModel;
    private static DefaultListModel<Customer> listCustomerModel;
    private static DefaultListModel<HomestayOfCus_Date> listHomestayOfCusModel;
    private static DefaultListModel<CustomerOfHs_Date> listCustomerOfHsModel;

    SwingManage() {
        super("Homestay");
        this.setContentPane(this.swingManage);
        this.setPreferredSize(new Dimension(1820, 680));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        saveHs.setEnabled(false);
        deleteHs.setEnabled(false);
        saveCustomer.setEnabled(false);
        deleteCustomer.setEnabled(false);

        listHomestayModel = new DefaultListModel<>();
        listCustomerModel = new DefaultListModel<>();
        listHomestay.setModel(listHomestayModel);
        listCustomer.setModel(listCustomerModel);

        listCustomerOfHsModel = new DefaultListModel<>();
        CusOfHomesList.setModel(listCustomerOfHsModel);

        listHomestayOfCusModel = new DefaultListModel<>();
        HomeOfCusList.setModel(listHomestayOfCusModel);

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manage.setVisible(false);
                swingAccount.setVisible(true);
            }
        });
        addHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddHsClicked(e);
            }
        });
        saveHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveHsClicked(e);
            }
        });
        deleteHs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    homestays.remove(homestay);
                    LabelHs.setText("X??a homestay " + homestay.getNameHs() + " th??nh c??ng!");
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    refreshHomestayList();
                }
            }
        });
        listHomestay.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int homeNumer = listHomestay.getSelectedIndex();
                if (homeNumer >= 0) {
                    Homestay homestay = homestays.get(homeNumer);
                    textNameHs.setText(homestay.getNameHs());
                    textPriceHs.setText(String.valueOf(homestay.getPriceHs()));
                    textPhoneHs.setText(homestay.getPhoneNumberHs());
                    textAddressHs.setText(homestay.getAddress());
                    textQAddressHs.setText(homestay.getCountyAddress());
                    textHighlightHs.setText(homestay.getHighlight());
                    textAccHs.setText(homestay.getAccHomestay());
                    textPassHs.setText(homestay.getPassHomestay());
                    cusOfHomesList(homestay.getAccHomestay(), homestay.getNameHs());
                    saveHs.setEnabled(true);
                    deleteHs.setEnabled(true);
                } else {
                    saveHs.setEnabled(false);
                    deleteHs.setEnabled(false);
                }
            }
        });
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddCusClicked(e);
            }
        });
        saveCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonSaveCusClicked(e);
            }
        });
        deleteCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int CusNumer = listCustomer.getSelectedIndex();
                if (CusNumer >= 0) {
                    Customer customer = customers.get(CusNumer);
                    customers.remove(customer);
                    LabelCus.setText("X??a kh??ch h??ng " + customer.getName() + " th??nh c??ng!");
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    refreshCustomerList();
                }
            }
        });
        listCustomer.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int CusNumer = listCustomer.getSelectedIndex();
                if (CusNumer >= 0) {
                    Customer customer = customers.get(CusNumer);
                    textName.setText(customer.getName());
                    comboBoxGender.setSelectedItem(customer.getGender());
                    textPhone.setText(customer.getPhoneNumber());
                    textDayBirth.setText(customer.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    textNationlity.setText(customer.getNationality());
                    textAccount.setText(customer.getAccount());
                    textPassWord.setText(customer.getPassword());
                    homeOfCusList(customer.getAccount(), customer.getName());
                    saveCustomer.setEnabled(true);
                    deleteCustomer.setEnabled(true);
                } else {
                    saveCustomer.setEnabled(false);
                    deleteCustomer.setEnabled(false);
                }
            }
        });
        CusOfHomesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        HomeOfCusList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }

    public void refreshHomestayList() {
        listHomestayModel.removeAllElements();
        for (Homestay homestay : homestays) {
            listHomestayModel.addElement(homestay);
        }
    }

    public void refreshCustomerList() {
        listCustomerModel.removeAllElements();
        for (Customer customer : customers) {
            listCustomerModel.addElement(customer);
        }
    }

    public void cusOfHomesList(String accHomes, String nameHomes) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHomes)) != null) {
            labelCusOfHs.setText(String.format("Danh s??ch kh??ch h??ng c???a %s", nameHomes));
            listCustomerOfHsModel.removeAllElements();
            for (CustomerOfHs_Date cusOfHs : checkFileCusOfHs_Date(accHomes)) {
                listCustomerOfHsModel.addElement(cusOfHs);
            }
        }
    }

    public void homeOfCusList(String accCus, String nameCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) != null) {
            labelHomesOfCus.setText(String.format("Danh s??ch homestay c???a %s", nameCus));
            listHomestayOfCusModel.removeAllElements();
            for (HomestayOfCus_Date homeOfCus : checkFileHomeOfCus_Date(accCus)) {
                listHomestayOfCusModel.addElement(homeOfCus);
            }
        }
    }

    public void checkFileHs() {
        if (Read_Write_file1.readFile(PATH_HOMESTAY) == null) {
            homestays = new ArrayList<>();
        } else {
            homestays = Read_Write_file1.readFile(PATH_HOMESTAY);
        }
    }

    public void checkFileCustomer() {
        if (Read_Write_file.readFile(PATH_CUSTOMER) == null) {
            customers = new ArrayList<>();
        } else {
            customers = Read_Write_file.readFile(PATH_CUSTOMER);
        }
    }

    public boolean checkAccount(String account) {
        for (Customer customer : customers) {
            if (customer.getAccount().equals(account)) {
                return true;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getAccHomestay().equals(account)) {
                return true;
            }
        }
        for (AccountAdmin acc : accountAdmins.getListAccountAdmin()) {
            if (acc.getAdminAccount().equals(account)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        for (Homestay homestay : homestays) {
            if (homestay.getPhoneNumberHs().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CustomerOfHs_Date> checkFileCusOfHs_Date(String accHs) {
        if (fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs)) == null) {
            return new ArrayList<>();
        } else {
            return fileHomeDate.readFile(String.format("file_Data/FileHomes%sData", accHs));
        }
    }

    public void buttonAddHsClicked(ActionEvent e) {
        Homestay homestay = new Homestay(
                textNameHs.getText(),
                Integer.parseInt(textPriceHs.getText()),
                textPhoneHs.getText(),
                textAddressHs.getText(),
                textQAddressHs.getText(),
                textHighlightHs.getText(),
                textAccHs.getText(),
                textPassHs.getText()
        );

        boolean checkAccountRegex = accountPasswordExample.validate(textAccHs.getText());
        boolean checkPasswordRegex = accountPasswordExample.validate(textPassHs.getText());
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhoneHs.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelHs.setText("Kh??ng c?? k?? t??? ?????c bi???t hay d???u c??ch!");
        } else {
            if (checkAccount(textAccHs.getText())) {
                LabelHs.setText("B??? tr??ng t??i kho???n, xin nh???p t??i kho???n kh??c!");
            } else if (checkPhoneNumber(textPhoneHs.getText())) {
                LabelHs.setText("B??? tr??ng s??? ??i???n tho???i, xin nh???p s??? ??i???n tho???i kh??c!");
            } else {
                boolean check = homestays.add(homestay);
                if (check) {
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    fileHomeDate.writerFile(checkFileCusOfHs_Date(textAccHs.getText()), String.format("file_Data/FileHomes%sData", textAccHs.getText()));
                    refreshHomestayList();
                    LabelHs.setText("Homestay " + homestay.getNameHs() + " t???o th??nh c??ng!");
                } else {
                    LabelHs.setText("T???o Homestay kh??ng th??nh c??ng!");
                }
            }
        }
    }

    public ArrayList<HomestayOfCus_Date> checkFileHomeOfCus_Date(String accCus) {
        if (fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus)) == null) {
            return new ArrayList<>();
        } else {
            return fileCusDate.readFile(String.format("file_Data/FileCus%sData", accCus));
        }
    }

    public void buttonAddCusClicked(ActionEvent e) {
        Customer customer = new Customer(
                textName.getText(),
                (String) comboBoxGender.getSelectedItem(),
                textPhone.getText(),
                textDayBirth.getText(),
                textNationlity.getText(),
                textAccount.getText(),
                textPassWord.getText()
        );

        boolean checkAccountRegex = accountPasswordExample.validate(textAccount.getText());
        boolean checkPasswordRegex = accountPasswordExample.validate(textPassWord.getText());
        boolean checkPhoneRegex = phoneNumberExample.validate(textPhone.getText());

        if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
            LabelCus.setText("Kh??ng c?? k?? t??? ?????c bi???t hay d???u c??ch!");
        } else {
            if (checkAccount(textAccount.getText())) {
                LabelCus.setText("B??? tr??ng t??i kho???n, xin nh???p t??i kho???n kh??c!");
            } else if (checkPhoneNumber(textPhone.getText())) {
                LabelCus.setText("B??? tr??ng s??? ??i???n tho???i, xin nh???p s??? ??i???n tho???i kh??c!");
            } else {
                boolean check = customers.add(customer);
                if (check) {
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    fileCusDate.writerFile(checkFileHomeOfCus_Date(textAccount.getText()), String.format("file_Data/FileCus%sData", textAccount.getText()));
                    refreshCustomerList();
                    LabelCus.setText("T??i kho???n " + customer.getAccount() + " t???o th??nh c??ng!");
                } else {
                    LabelCus.setText("T???o t??i kho???n kh??ng th??nh c??ng!");
                }
            }
        }
    }

    public void buttonSaveHsClicked(ActionEvent e) {
        int homeNumer = listHomestay.getSelectedIndex();
        if (homeNumer >= 0) {
            Homestay homestay = homestays.get(homeNumer);
            Homestay homestayNew = new Homestay(
                    textNameHs.getText(),
                    Integer.parseInt(textPriceHs.getText()),
                    textPhoneHs.getText(),
                    textAddressHs.getText(),
                    textQAddressHs.getText(),
                    textHighlightHs.getText(),
                    textAccHs.getText(),
                    textPassHs.getText()
            );

            boolean checkAccountRegex = accountPasswordExample.validate(textAccHs.getText());
            boolean checkPasswordRegex = accountPasswordExample.validate(textPassHs.getText());
            boolean checkPhoneRegex = phoneNumberExample.validate(textPhoneHs.getText());

            if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
                LabelHs.setText("Kh??ng c?? k?? t??? ?????c bi???t hay d???u c??ch!");
            } else {
                if (textAccHs.getText().equals(homestay.getAccHomestay())){
                    homestays.removeIf((homes) -> (homes.getAccHomestay().equals(homestay.getAccHomestay())));
                    Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                    if (checkPhoneNumber(textPhoneHs.getText())) {
                        LabelHs.setText("B??? tr??ng s??? ??i???n tho???i, xin nh???p s??? ??i???n tho???i kh??c!");
                    } else {
                        boolean check = homestays.add(homestayNew);
                        if (check ) {
                            Read_Write_file1.writerFile(homestays, PATH_HOMESTAY);
                            refreshHomestayList();
                            LabelHs.setText("Homestay " + homestayNew.getNameHs() + " l??u th??nh c??ng!");
                        } else {
                            LabelHs.setText("T???o Homestay l??u kh??ng th??nh c??ng!");
                        }
                    }
                } else {
                    textAccHs.setText(homestay.getAccHomestay());
                    LabelHs.setText("T??n t??i kho???n kh??ng ???????c thay ?????i!");
                }
            }
        }
    }

    public void buttonSaveCusClicked(ActionEvent e) {
        int CusNumer = listCustomer.getSelectedIndex();
        if (CusNumer >= 0) {
            Customer customer = customers.get(CusNumer);
            Customer customerNew = new Customer(
                    textName.getText(),
                    (String) comboBoxGender.getSelectedItem(),
                    textPhone.getText(),
                    textDayBirth.getText(),
                    textNationlity.getText(),
                    textAccount.getText(),
                    textPassWord.getText()
            );

            boolean checkAccountRegex = accountPasswordExample.validate(textAccount.getText());
            boolean checkPasswordRegex = accountPasswordExample.validate(textPassWord.getText());
            boolean checkPhoneRegex = phoneNumberExample.validate(textPhone.getText());

            if (!checkAccountRegex | !checkPasswordRegex | !checkPhoneRegex) {
                LabelCus.setText("Kh??ng c?? k?? t??? ?????c bi???t hay d???u c??ch!");
            } else {
                if (textAccount.getText().equals(customer.getAccount())){
                    customers.removeIf((cus) -> (cus.getAccount().equals(customer.getAccount())));
                    Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                    if (checkPhoneNumber(textPhone.getText())) {
                        LabelCus.setText("B??? tr??ng s??? ??i???n tho???i, xin nh???p s??? ??i???n tho???i kh??c!");
                    } else {
                        boolean check = customers.add(customerNew);
                        if (check) {
                            Read_Write_file.writerFile(customers, PATH_CUSTOMER);
                            refreshCustomerList();
                            LabelCus.setText("T??i kho???n " + customerNew.getAccount() + "  l??u th??nh c??ng!");
                        } else {
                            LabelCus.setText("T??i kho???n l??u kh??ng th??nh c??ng!");
                        }
                    }
                } else {
                    textAccount.setText(customer.getAccount());
                    LabelCus.setText("T??n t??i kho???n kh??ng ???????c thay ?????i!");
                }
            }
        }
    }
}