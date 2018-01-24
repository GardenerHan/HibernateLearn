package com.ifox.hgx.hibernate.n21.both;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    private Integer customerId ;
    private String customerName ;
//    声明集合类型时，需使用接口类型，因为hibernate在获取
//    集合类型时，返回的时hibernate内置的集合类型，而不是javase一个标准的集合实现。
//    需要把集合初始化，防止空指针异常。
    private Set<Order> orders = new HashSet<>() ;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
