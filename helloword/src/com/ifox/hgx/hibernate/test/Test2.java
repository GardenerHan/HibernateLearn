package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.helloword.News;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

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

    /*

     */
    @Test
    public void testLoad(){
        session.clear();
        News news = session.load(News.class,4);
        System.out.println(news.getClass().getName());
    }
    /*
        update:
        1.若更新一个持久化对象，不需要调用update方法，应为调用Transaction的commit()方法是，会先执行session的flush方法
        2.跟新一个游离的对象，需要显示的调用session的update方法，可以将一个有利的对象转变成一个持久化对象
        注意:
            1.无论更新的游离对象是否和数据表的记录一样，都会有update的sql 的语句
                如何能让update方法不在盲目的发update的sql语句?
                    在News.hbm.xml class节点 设置: select-before-update="true",但通常不需要设置该属性（需要多发一条select语句）
            2.若数据表没有对应的记录，但还是调用了update方法，会抛出异常
            3，当update方法关联了一个游离对象，弱国Session的缓存中已经有相同的ID的持久化对象，会抛出异常
                NonUniqueObjectException:因为session缓存中，不能有两个OID相同的对象。
     */
    @Test
    public  void testUpdate(){
        News news = session.get(News.class,4);//在第一个session缓存里面

        transaction.commit();
        session.close();

//        news.setId(1000);

        //一个新的session
        session = sessionFactory.openSession() ;
        transaction = session.beginTransaction() ;

//        news.setAuthor("Oracle");

     //   News news1 = session.get(News.class,4);
        session.update(news);
    }
    @Test
    public  void testSaveOrUpdate(){
        /*
        有OID:update
        临时对象:insert
        1.若OID 不是null，但数据表还有对应的记录，会抛出一个异常
        2.了解:OID值等于ID的unsaved-value属性值的对象，也被认为是一个游离对象
         */
        News news = new News("DD","dd",new Date(new java.util.Date().getTime())) ;
        news.setId(10);
//        news.setId(10000);//抛出异常
        session.saveOrUpdate(news);
    }
    /*
    delete:执行删除操作:OID与数据表一条记录对应，就会准备执行delete操作,若OID在数据库中没有对应的记录，抛出异常OptimisticLockException
    可以通过 <property name="use_identifier_rollback">true</property> 将删除OID的对象OID为nuyll
    变成这样:News{id=null, title='AA', author='aa', date=2017-12-13} id = null
     */
    @Test
    public void testDelete(){
//        News news = new News() ;
//        news.setId(11);
        News news = session.get(News.class ,5 ) ;
        session.delete(news);//预备删除

        System.out.println(news);
    }

    /*
    evict():从Session缓存中吧指定的持久化对象移除
     */
    @Test
    public void testEvict(){
        News news = session.get(News.class,1) ;
        News news1 = session.get(News.class,2) ;
        news.setTitle("AA");
        news1.setTitle("BB");

        session.evict(news);

    }
    /*
    储存过程
     */
    @Test
    public  void testDoWork(){
        session.doWork(
                new Work(){
                    @Override
                    public void execute(Connection connection) throws SQLException {
                        System.out.println(connection);
//                        com.mysql.jdbc.JDBC4Connection@65f87a2c
                    }
                }
        ) ;
    }
}
