package Database;

import Entities.Book;
import Helper.DBConnector;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

public class DBOperations {
    public static ArrayList<Book> getList() {
        ArrayList<Book> bookList = new ArrayList<>();
        Statement statement = null;
        String query = "SELECT * FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("book_name");
                String author = resultSet.getString("book_author");
                int page = resultSet.getInt("book_page");
                String publisher = resultSet.getString("book_publisher");
                String type = resultSet.getString("book_genre");

                bookList.add(new Book(id, name, author, page, publisher, type));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookList;
    }

    public static boolean add(String name, String author, int page, String publisher, String type) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO book (book_name,book_author,book_page,book_publisher,book_genre) VALUES(?,?,?,?,?)";
        try {
            preparedStatement = DBConnector.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, page);
            preparedStatement.setString(4, publisher);
            preparedStatement.setString(5, type);
            int response = preparedStatement.executeUpdate();
            if (response == -1) {
                Helper.showMessage("Hata! Kitap eklenemedi.");
            }
            return response != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean delete(int id) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE  FROM book WHERE id=?";
        try {
            preparedStatement = DBConnector.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;

    }

    public static boolean update(int id, String name, String author, int page, String publisher, String type) {

        PreparedStatement preparedStatement = null;
        String query = "UPDATE book SET book_name=?,book_author=?,book_page=?,book_publisher=?,book_genre=? WHERE ID=?";
        try {
            preparedStatement = DBConnector.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, author);
            preparedStatement.setInt(3, page);
            preparedStatement.setString(4, publisher);
            preparedStatement.setString(5, type);
            preparedStatement.setInt(6, id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static ArrayList<Book> searchBookList(String searchQuery) {
        ArrayList<Book> bookList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int page = resultSet.getInt("book_page");
                String name = resultSet.getString("book_name");
                String author = resultSet.getString("book_author");
                String type = resultSet.getString("book_genre");
                String publisher = resultSet.getString("book_publisher");
                bookList.add(new Book(id, name, author, page, publisher, type));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookList;
    }

    public static String makeSearchQuery(String name, String author, String type, String publisher) {
        String query = "SELECT * FROM book WHERE book_name LIKE  '%{{name}}%' AND book_author LIKE '%{{author}}%'AND book_publisher LIKE '%{{publisher}}%' ";

        query = query.replace("{{name}}", name);
        query = query.replace("{{author}}", author);
        query = query.replace("{{publisher}}", publisher);
        if (!type.isEmpty()) {
            query += " AND book_genre='{{type}}'";
            query = query.replace("{{type}}", type);
        }


        return query;

    }

    public static ArrayList<Book> getOrderedList(String queryOrder) {
        String query = "SELECT * FROM book";
        ArrayList<Book> bookList = new ArrayList<>();
        switch (queryOrder) {
            case "Yazar Adı":
                query = "SELECT * FROM book ORDER BY book_author";
                break;
            case "Kitap Adı":
                query = "SELECT * FROM book ORDER BY book_name";
                break;
            case "Sayfa Sayısı(Azalan)":
                query = "SELECT * FROM book ORDER BY book_page DESC";
                break;
            case "Sayfa Sayısı(Artan)":
                query = "SELECT * FROM book ORDER BY book_page ASC";
                break;
            case "Tür":
                query = "SELECT * FROM book ORDER BY book_genre";
                break;
            case "Yayınevi":
                query = "SELECT * FROM book ORDER BY book_publisher";
                break;

        }
        Statement statement = null;
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("book_name");
                String author = resultSet.getString("book_author");
                int page = resultSet.getInt("book_page");
                String publisher = resultSet.getString("book_publisher");
                String type = resultSet.getString("book_genre");

                bookList.add(new Book(id, name, author, page, publisher, type));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookList;
    }

    public static String getAuthorWithMostBooks() {
        Statement statement = null;
        String result = "";
        String query = "SELECT book_author,COUNT(*) as total FROM book GROUP BY book_author ORDER BY COUNT(*) DESC LIMIT 1";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int total = resultSet.getInt("total");
                String author = resultSet.getString("book_author");
                result = author + " - " + total + " kitap";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

    public static String getTotalAuthorCount() {
        Statement statement = null;
        String result = "";
        String query = "SELECT COUNT(DISTINCT book_author) as total FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int total = resultSet.getInt("total");
                result = total + "";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getTotalBookCount() {
        Statement statement = null;
        String result = "";
        String query = "SELECT COUNT(*) as total FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int total = resultSet.getInt("total");

                result = total + "";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getTotalPageCount() {
        Statement statement = null;
        String result = "";
        String query = "SELECT SUM(book_page) as total FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int total = resultSet.getInt("total");
                result = total + "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String getTotalPublisherCount() {
        Statement statement = null;
        String result = "";
        String query = "SELECT COUNT(DISTINCT book_publisher) as total FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int total = resultSet.getInt("total");
                result = total + " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static TreeSet<String> getPublisherNamesList() {
        TreeSet<String> publishers = new TreeSet<>();
        String result = "";
        Statement statement = null;
        String query = "SELECT DISTINCT book_publisher as publisher FROM book";
        try {
            statement = DBConnector.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String publisher = resultSet.getString("publisher");
                publishers.add(publisher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return publishers;

    }
}
