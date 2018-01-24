package com.ifox.hgx.hibernate.test;

import com.ifox.hgx.hibernate.one2one.foreign.Department;
import com.ifox.hgx.hibernate.one2one.foreign.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test5 {
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
        Department department = new Department() ;
        department.setDeptName("DEPT_BB");

        Manager manager = new Manager() ;
        manager.setMgrName("MGR_BB");

        //设定关联关系
        department.setManager(manager);
        manager.setDepartment(department);
        //保存操作
        //建议先保存没有外键列的那个对象，这样会减少sql语句
        session.save(manager) ;
        session.save(department) ;


    }
    @Test
    public void  testGet(){
//        默认情况下使用懒加载
//        所以会出现懒加载异常
        Department dept = session.get(Department.class,1) ;

        System.out.println(dept.getDeptName());

        System.out.println(dept.getManager().getClass());
//查询Manager对象的连接条件应该是department.manager_id = manager.manager_id
//但是却是 manager0_.MANAGER_ID=department1_.DEPARTMENT_ID 不对。
//加上property-ref="manager" 正确 manager0_.MANAGER_ID=department1_.MANAGER_ID
        System.out.println(dept.getManager().getMgrName());
    }

    @Test
    public void testGet2(){
//        在查询没有外键实体对象时，使用左外连接查询，一并查询出关联的对象，并初始化
        Manager manager = session.get(Manager.class,1) ;
        System.out.println(manager.getMgrName());
        System.out.println(manager.getDepartment().getDeptName());
    }
}
