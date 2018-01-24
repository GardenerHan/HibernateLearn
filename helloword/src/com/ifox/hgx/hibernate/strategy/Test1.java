package com.ifox.hgx.hibernate.strategy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Test ;
import  org.junit.Before ;


public class Test1 {
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

    //类级别的检索
    @Test
    public void test1(){
//        lazy = "false" 立即加载
//        访问属性:立即检索
//        获取引用:延迟加载 仅仅对load()有用

        Customer customer = session.load(Customer.class,1);
        System.out.println(customer.getClass());
        System.out.println(customer.getCustomerId());
    }
}
