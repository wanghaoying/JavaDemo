package org.example.dao;

import org.springframework.stereotype.Repository;

@Repository
public class CarDao {

    private int lable = 1;

    public int getLable() {
        return lable;
    }

    public void setLable(int lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "CarDao{" +
                "lable=" + lable +
                '}';
    }
}
