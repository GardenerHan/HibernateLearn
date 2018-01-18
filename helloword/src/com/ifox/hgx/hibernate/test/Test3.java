package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.entities.Pay;
import com.ifox.hgx.hibernate.entities.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test3 {
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
    public void TestComponent(){
        Worker worker = new Worker() ;
        Pay pay = new Pay() ;
        pay.setMonthlyPay(1000);
        pay.setYearPay(80000);
        pay.setVocationWithPay(6);

        worker.setName("asdas");
        worker.setPay(pay);

        session.save(worker) ;

    }

    @Test
    public  void testMany2One(){

    }
}
