package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.helloword.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;


public class Test1 {

    @Test
    public void test() {
        //1创建一个SessionFactory
        SessionFactory sessionFactory = null;
        //Configuration 对象:对应hibernate映射文件
        Configuration configuration = new Configuration().configure();
        //4.0之前
//        sessionFactory = configuration.buildSessionFactory() ;
        //4.x 新添加的对象 hibernate 的配置都需要 在该对象注册后才能生效
        //5.x ServiceRegistry的实现需用StandardServiceRegistryBuilder之前版本用的是 ServiceRegistryBuilder

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        // new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build() ;
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        // sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory() ;
        //2.创建Session对象
        Session session = sessionFactory.openSession();
        //3.开启事务
        Transaction transaction = session.beginTransaction();
        //4.执行保存操作
        News news = new News("Java", "HGX", new Date(new java.util.Date().getTime()));
        session.save(news);
        //5.提交事务
        transaction.commit();
        //6.关闭session
        session.close();
        //7.关闭SessionFactory
        sessionFactory.close();
    }

    @Test
    public void test2() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        News news = new News("Java", "HGX", new Date(new java.util.Date().getTime()));
        session.save(news);
        transaction.commit();
        session.close();
        //7.关闭SessionFactory
        sessionFactory.close();
    }


    @Test
    public void test3() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        News news = session.get(News.class, 1);
        System.out.println(news);

        News news1 = session.get(News.class, 1);

        System.out.println(news1);
        transaction.commit();
        session.close();
        //7.关闭SessionFactory
        sessionFactory.close();

        /*
                select
                news0_.ID as ID1_0_0_,
                news0_.TITLE as TITLE2_0_0_,
                news0_.AUTHOR as AUTHOR3_0_0_,
                news0_.DATE as DATE4_0_0_
                from
                NEWS news0_
                where
                news0_.ID=?
                News{id=9, title='Java', author='HGX', date=2017-12-06}

         */


    }

    @Test
    public void testSessionFlush() {

        /*
        flush：使数据表中的session缓存中的对象状态保持一致。为了保持一致，则会可能发送相应的sql语句
        flush方法可能会发送sql语句，但不会提交事务
        注意:
            1.在为提交事务或显示的调用session.flush()之前，也可能会进行flush()操作.
            -- 执行HQL或QBC查询，会进行flush()操作，以得到数据库记录。
            --
         */

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        News news = session.get(News.class, 1);
        System.out.println(news);

        news.setAuthor("hgxokok");
        System.out.println(news);

//        session.flush();
//        System.out.println("flush");
//        System.out.println(news);

        System.out.println("-------------------");

        News news1 = (News) session.createCriteria(News.class).uniqueResult();


        transaction.commit();
        session.close();
        //7.关闭SessionFactory
        sessionFactory.close();
    }

    @Test
    public void testSessionFlush2(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


//        若记录的ID是由底层数据库使用自增的方式生成的，则在调用save方式立即发送insert语句
//        因为save方法后，必须保证ID存在
        News news = new News("Java","sun",new Date(new java.util.Date().getTime())) ;
        session.save(news) ;

        transaction.commit();
        session.close();
        //7.关闭SessionFactory
        sessionFactory.close();
    }

    @Test
    public void testRefresh(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();


//       refresh():会强制发送select语句，使得Session缓存的对象和数据最新的保持一致
        News news = session.get(News.class,4) ;
        System.out.println(news);

        session.refresh(news);

        System.out.println(news);
        transaction.commit();
        session.close();
        sessionFactory.close() ;
    }

    @Test
    public  void testClear(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        News news = session.get(News.class,4) ;

        session.clear();

        News news1 = session.get(News.class,4) ;

    }
}
