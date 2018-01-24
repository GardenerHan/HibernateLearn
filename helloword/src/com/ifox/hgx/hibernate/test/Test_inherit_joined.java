package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.joined.subclass.Person;
import com.ifox.hgx.hibernate.joined.subclass.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class Test_inherit_joined {
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

/*
    优点：
    1.不需要辨别者列
    2.子类独有的字段可以添加非空约束
    3.没有冗余字段
 */
    @Test
    public void TestSave() {
            Person person = new Person() ;
            person.setAge(12);
            person.setName("AAA");
            session.save(person);

            Student student = new Student();
            student.setAge(22);
            student.setName("BB");
            student.setSchool("四川师范大学");
            session.save(student);
    }

    /*
        查询父类，做一个左外连接查询
        查询子类:做一个内连接查询
     */

    @Test
    public void testGetQuery() {

        List<Person> people = session.createQuery("FROM Person ").list() ;
        System.out.println(people.size());

        List<Student> students = session.createQuery("from Student ").list() ;

        System.out.println(students.size());

    }

}
