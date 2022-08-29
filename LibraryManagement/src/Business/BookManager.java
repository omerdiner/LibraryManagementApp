package Business;


import Database.DBOperations;
import Entities.Book;

import java.util.ArrayList;
import java.util.TreeSet;

public class BookManager {

    public static ArrayList<Book> getList() {
        return DBOperations.getList();
    }

    public static boolean add(String name, String author, int page, String publisher, String type) {

        return DBOperations.add(name, author, page, publisher, type);
    }

    public static boolean delete(int id) {
        return DBOperations.delete(id);
    }

    public static boolean update(int id, String name, String author, int page, String publisher, String type) {


        return DBOperations.update(id, name, author, page, publisher, type);
    }

    public static ArrayList<Book> searchBookList(String searchQuery) {
        return DBOperations.searchBookList(searchQuery);
    }

    public static String makeSearchQuery(String name, String author, String type, String publisher) {
        return DBOperations.makeSearchQuery(name, author, type, publisher);

    }

    public static ArrayList<Book> getOrderedList(String orderBy) {
        return DBOperations.getOrderedList(orderBy);
    }

    public static String getAuthorWithMostBooks() {
        return DBOperations.getAuthorWithMostBooks();
    }

    public static String getTotalAuthorCount() {
        return DBOperations.getTotalAuthorCount();
    }

    public static String getTotalBookCount() {
        return DBOperations.getTotalBookCount();
    }

    public static String getTotalPageCount() {
        return DBOperations.getTotalPageCount();
    }

    public static String getTotalPublisherCount() {
        return DBOperations.getTotalPublisherCount();
    }

    public static TreeSet<String> getPublisherNamesList() {
        return DBOperations.getPublisherNamesList();
    }
}
