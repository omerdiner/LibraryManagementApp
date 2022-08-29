package GUI;

import Business.BookManager;
import Entities.Book;
import Helper.Config;
import Helper.Helper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.TreeSet;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel label_welcome;
    private JPanel panel_top;
    private JButton button_logout;
    private JPanel panel_bookList;
    private JScrollPane scroll_bookList;
    private JTable table_bookList;
    private JPanel panel_bookForm;
    private JTextField field_bookName;
    private JTextField field_author;
    private JTextField field_page;
    private JComboBox combo_types;
    private JButton button_addBook;
    private JTextField field_bookId;
    private JButton button_deleteBook;
    private JTextField field_search_bookName;
    private JTextField field_search_author;
    private JTextField field_search_publisher;
    private JComboBox combo_search_bookType;
    private JButton button_bookSearch;
    private JTextField field_publisher;
    private JButton button_ordeyBy;
    private JComboBox combo_orderBy;
    private JPanel panel_bookSearch;
    private JLabel field_totalBookCount;
    private JLabel field_totalAuthorCount;
    private JPanel panel_about;
    private JLabel field_totalPageCount;
    private JLabel field_authorWithMostBooks;
    private JLabel field_totalPublisherCount;
    TreeSet<String> publisherNamesList;


    private DefaultTableModel model_bookList;
    private Object[] rowBookList;
    private DefaultTableModel mdl_patikaList;
    private Object[] rowPatikaList;
    private JPopupMenu patika_menu;
    private DefaultTableModel mdl_courseList;
    private Object[] rowCourseList;

    public OperatorGUI() {


        add(wrapper);
        setSize(1000, 700);
        int x = Helper.screenCenterPosition("x", getSize());
        int y = Helper.screenCenterPosition("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);


        model_bookList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] column_bookList = {"ID", "Kitap Adı", "Yazar Adı", "Sayfa Sayısı", "Yayınevi", "Tür"};
        rowBookList = new Object[column_bookList.length];
        model_bookList.setColumnIdentifiers(column_bookList);
        table_bookList.setModel(model_bookList);
        table_bookList.getTableHeader().setReorderingAllowed(false);

        //I'm shrinking the columns so that there is more space for the text areas
        table_bookList.getColumnModel().getColumn(0).setMaxWidth(30);
        table_bookList.getColumnModel().getColumn(3).setMaxWidth(60);
        table_bookList.getColumnModel().getColumn(5).setMaxWidth(70);
        //

        loadBookModel();

        table_bookList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedBookId = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 0).toString() : null;
                field_bookId.setText(selectedBookId);
            }
        });
        table_bookList.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    String selectedBookId = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 0).toString() : null;
                    int id = Integer.parseInt(selectedBookId);
                    String name = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 1).toString() : null;
                    String author = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 2).toString() : null;
                    String selectedPage = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 3).toString() : null;
                    int page = Integer.parseInt(selectedPage);
                    String publisher = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 4).toString() : null;
                    String type = table_bookList.getSelectedRow() != -1 ? table_bookList.getValueAt(table_bookList.getSelectedRow(), 5).toString() : null;
                    if (BookManager.update(id, name, author, page, publisher, type)) {
                        Helper.showMessage("Kitap güncellendi.");

                    }
                    loadBookModel();

                }
            }
        });
//
        field_publisher.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {

                } else {
                    String to_check = field_publisher.getText();
                    int to_check_len = to_check.length();
                    for (String data : publisherNamesList) {
                        String check_from_data = "";
                        for (int i = 0; i < to_check_len; i++) {
                            if (to_check_len <= data.length()) {
                                check_from_data = check_from_data + data.charAt(i);
                            }
                        }

                        if (check_from_data.equals(to_check)) {
                            field_publisher.setText(data);
                            field_publisher.setSelectionStart(to_check_len);
                            field_publisher.setSelectionEnd(data.length());
                            break;
                        }
                    }
                }
            }
        });
//

//
        field_search_publisher.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {

                } else {
                    String to_check = field_search_publisher.getText();
                    int to_check_len = to_check.length();
                    for (String data : publisherNamesList) {
                        String check_from_data = "";
                        for (int i = 0; i < to_check_len; i++) {
                            if (to_check_len <= data.length()) {
                                check_from_data = check_from_data + data.charAt(i);
                            }
                        }

                        if (check_from_data.equals(to_check)) {
                            field_search_publisher.setText(data);
                            field_search_publisher.setSelectionStart(to_check_len);
                            field_search_publisher.setSelectionEnd(data.length());
                            break;
                        }
                    }
                }
            }
        });

        //

        button_addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(field_bookName) || Helper.isFieldEmpty(field_page) || Helper.isFieldEmpty(field_author)) {
                    Helper.showMessage("Eklenecek kitabın tüm bilgilerini giriniz.");
                } else {
                    String name = field_bookName.getText();
                    String author = field_author.getText();
                    int page = Integer.parseInt(field_page.getText());
                    String publisher = field_publisher.getText();
                    String type = combo_types.getSelectedItem().toString();

                    if (BookManager.add(name, author, page, publisher, type)) {
                        Helper.showMessage("Kitap Eklendi.");
                        loadBookModel();
                        field_author.setText(null);
                        field_page.setText(null);
                        field_bookName.setText(null);
                        field_publisher.setText(null);
                        combo_types.setSelectedIndex(0);
                    }
                }
            }
        });
        button_deleteBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(field_bookId)) {
                    Helper.showMessage("Id alanı boş.");
                } else {
                    if (Helper.confirmTransaction("Kitabı silmek istediğinize emin misiniz?")) {
                        int id = Integer.parseInt(field_bookId.getText());
                        if (BookManager.delete(id)) {
                            Helper.showMessage("Kitap silindi.");
                            loadBookModel();
                            field_bookId.setText(null);
                        } else {
                            Helper.showMessage("Hata.");
                        }
                    }

                }
            }
        });
        button_bookSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = field_search_bookName.getText();
                String author = field_search_author.getText();
                String publisher = field_search_publisher.getText();
                String type = combo_search_bookType.getSelectedItem().toString();
                String query = BookManager.makeSearchQuery(name, author, type, publisher);
                ArrayList<Book> searchResultUserList = BookManager.searchBookList(query);
                loadBookModel(searchResultUserList);
            }
        });
        button_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginGUI loginGUI = new LoginGUI();
            }
        });


        button_ordeyBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderBy = combo_orderBy.getSelectedItem().toString();
                loadBookModel(orderBy);

            }
        });
    }

    public void loadBookModel() {
        DefaultTableModel clearModel = (DefaultTableModel) table_bookList.getModel();
        clearModel.setRowCount(0);
        for (Book obj : BookManager.getList()) {

            rowBookList[0] = obj.getId();
            rowBookList[1] = obj.getName();
            rowBookList[2] = obj.getAuthor();
            rowBookList[3] = obj.getPage();
            rowBookList[4] = obj.getPublisher();
            rowBookList[5] = obj.getGenre();
            model_bookList.addRow(rowBookList);
        }
        setInfosAboutLibrary();
        setPublisherAutoCompletionList();
    }

    public void loadBookModel(String sqlOrder) {
        DefaultTableModel clearModel = (DefaultTableModel) table_bookList.getModel();
        clearModel.setRowCount(0);
        for (Book obj : BookManager.getOrderedList(sqlOrder)) {

            rowBookList[0] = obj.getId();
            rowBookList[1] = obj.getName();
            rowBookList[2] = obj.getAuthor();
            rowBookList[3] = obj.getPage();
            rowBookList[4] = obj.getPublisher();
            rowBookList[5] = obj.getGenre();
            model_bookList.addRow(rowBookList);
        }
        setInfosAboutLibrary();
        setPublisherAutoCompletionList();
    }

    public void loadBookModel(ArrayList<Book> list) {
        DefaultTableModel clearModel = (DefaultTableModel) table_bookList.getModel();
        clearModel.setRowCount(0);
        for (Book obj : list) {

            rowBookList[0] = obj.getId();
            rowBookList[1] = obj.getName();
            rowBookList[2] = obj.getAuthor();
            rowBookList[3] = obj.getPage();
            rowBookList[4] = obj.getPublisher();
            rowBookList[5] = obj.getGenre();
            model_bookList.addRow(rowBookList);
        }
        setInfosAboutLibrary();
        setPublisherAutoCompletionList();
    }

    public void setInfosAboutLibrary() {
        field_authorWithMostBooks.setText(BookManager.getAuthorWithMostBooks());
        field_totalAuthorCount.setText(BookManager.getTotalAuthorCount());
        field_totalBookCount.setText(BookManager.getTotalBookCount());
        field_totalPageCount.setText(BookManager.getTotalPageCount());
        field_totalPublisherCount.setText(BookManager.getTotalPublisherCount());
    }

    public void setPublisherAutoCompletionList() {
        publisherNamesList = BookManager.getPublisherNamesList();
    }


    public static void main(String[] args) {

        OperatorGUI operatorGUI = new OperatorGUI();

    }
}
