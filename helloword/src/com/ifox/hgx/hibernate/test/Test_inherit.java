package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.subclass.Person;
import com.ifox.hgx.hibernate.subclass.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class Test_inherit {
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
    public void TestSave() {
        /*
            插入操作：
                对于子类对象只需要将记录插入进表中就ok了。
                辨别者列由hibernate自动维护。
         */
        Person person = new Person() ;
        person.setAge(12);
        person.setName("AAA");
        session.save(person) ;

        Student student = new Student() ;
        student.setAge(22);
        student.setName("BB");
        student.setSchool("四川师范大学");
        session.save(student) ;

    }

    /*
    缺点:
        1.使用了辨别者列
        2.子类独有的子不能添加非空约束
        3.若继承层次较深，数据表的字段也会较多。
     */

    @Test
    public void testGetQuery() {
//        查询:
//          父类:只需要查询一张表
//          子类：只需要查询一张表    student0_.TYPE='STUDENT'

        List<Person> people = session.createQuery("FROM  Person ").list() ;
        System.out.println(people.size());

        List<Student> students = session.createQuery("from Student ").list() ;

        System.out.println(students.size());

    }

}
