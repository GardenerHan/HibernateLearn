package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.n2n.Category;
import com.ifox.hgx.hibernate.n2n.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class Test_n2n {
    private Configuration cfg;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void before() {
        cfg = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void after() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public  void TestSave(){
        Category category1 = new Category() ;
        category1.setName("C_AA");

        Category category2 = new Category() ;
        category2.setName("C_BB");

        Item item1 = new Item() ;
        item1.setName("I_AA");

        Item item2 = new Item() ;
        item2.setName("I_BB");

        //设定关联关系
        category1.getItems().add(item1) ;
        category1.getItems().add(item2) ;

        category2.getItems().add(item1) ;
        category2.getItems().add(item2) ;

        session.save(category1) ;
        session.save(category2) ;

        session.save(item1) ;
        session.save(item2) ;

    }
    @Test
    public void  testGet(){

        Category category = session.get(Category.class,1) ;
        System.out.println(category.getName());
        //需要连接中间表  inner join ITEMS item1_
        Set<Item> items = category.getItems() ;
        System.out.println(items.size());
    }

}
