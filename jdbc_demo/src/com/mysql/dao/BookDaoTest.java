package com.mysql.dao;

import com.mysql.bean.Book;
import org.junit.Test;

public class BookDaoTest {

    @Test
    public void getBookTest(){
        BookDao bookDao = new BookDao();
        Book book = bookDao.getBook(1);
        System.out.println(book);
    }

    @Test
    public void deleteBookByIDTest(){
        BookDao bookDao = new BookDao();
        int result = bookDao.deleteBookByID(102);
        System.out.println(result);
    }

    @Test
    public void getCountTest(){
        BookDao bookDao = new BookDao();
        long count = bookDao.getCount();
        System.out.println(count);
    }


}
