package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.helloword.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

public class Test2 {
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
    public void testSave() {
        /*
        为对象分配ID
        在flush()缓存是会发送一台哦insert语句
        在save方法之前设置id是无效的
        持久化对象的id是不能被修改的
         */
        News news = new News("BB", "bb", new Date(new java.util.Date().getTime())) ;
        System.out.println(news);
        news.setId(11);//无效
        session.save(news) ;
        System.out.println(news);
//        news.setId(23);//抛出异常
    }


    /*
       persist:也会执行insert操作
       和save()的区别:
           在调用persist方法之前，若对象已经有了id，则不会执行insert此操作，而抛出异常
    */
    @Test
    public void testPersist(){
        News news = new News("CC","cc",new Date(new java.util.Date().getTime())) ;
//        news.setId(100);
        session.persist(news);
    }

    /*
  get VS load
   1.
    get:立即加载对象
    load:若不使用该对象,则不会立即执行查询操作，而返回一个代理对象
    get立即加载，load延迟检索
   2.若数据表中没有对应的记录，Session没有被关闭
    get返回null
    load：若不使用该对象，没问题；若需要初始化，抛出异常
   3.load方法可能抛出懒加载异常 LazyInitializationException
    在需要初始化代理对像之情，关闭了Session
     */

    @Test
    public void testGet(){
        News news = session.get(News.class,4) ;
        System.out.println(news.getClass().getName());
    }

    @Test
    public void testLoad(){
        session.clear();
        News news = session.load(News.class,4); ;
        System.out.println(news.getClass().getName());
    }
}
