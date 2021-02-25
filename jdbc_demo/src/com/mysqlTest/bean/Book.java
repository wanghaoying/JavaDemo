package com.mysqlTest.bean;

public class Book {
    private int id;
    private String name;
    private String author;
    private int sale;
    private int price;
    private int stack;

    public Book() {
    }

    public Book(int id, String name, String author, int sale, int price, int stack) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.sale = sale;
        this.price = price;
        this.stack = stack;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", sale=" + sale +
                ", price=" + price +
                ", stack=" + stack +
                '}';
    }
}
