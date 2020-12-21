package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.service.MyPredicate;
import com.example.demo.service.MyPredicateImpl;
import com.example.demo.service.MyPredicateImpl2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.StyledEditorKit;
import java.sql.SQLOutput;
import java.util.*;

@SpringBootTest
public class TestApplicationTests {

    @Test
    public void contextLoads() {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(com);
    }

    @Test
    public void test() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> treeSet = new TreeSet<>(com);
    }

    List<Employee> list = Arrays.asList(
            new Employee("qwe", 19, 9999.99),
            new Employee("asd", 29, 8888.88),
            new Employee("zxc", 39, 7777.77),
            new Employee("qaz", 49, 6666.66));

    public List<Employee> getList(List<Employee> list){
        List<Employee> list1=new ArrayList<>();
        for (Employee e:list){
            if(e.getAge()>30){
                list1.add(e);
            }
        }
        return list1;
    }

    public List<Employee> getList1(List<Employee> list, MyPredicate<Employee> mp){
        List<Employee> list1=new ArrayList<>();
        for (Employee e:list){
            if(mp.test(e)){
                list1.add(e);
            }
        }
        return list1;
    }

    @Test
    public void test1(){
        List<Employee> l=getList(list);
        l.forEach(System.out::println);
    }

    //策略设计模式
    @Test
    public void test2(){
        List<Employee> l=getList1(list,new MyPredicateImpl());
        l.forEach(System.out::println);
        System.out.println("------------------");
        List<Employee> l2=getList1(list,new MyPredicateImpl2());
        l2.forEach(System.out::println);
    }

    //匿名内部类
    @Test
    public void test3(){
        List<Employee> l=getList1(list, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge()>30;
            }
        });
        l.forEach(System.out::println);

        List<Employee> l2=getList1(list, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary()>8000;
            }
        });
        l2.forEach(System.out::println);
    }

    //lambda
    @Test
    public void test4(){
        List<Employee> list1 = getList1(list, e -> e.getSalary() > 8000);
        list1.forEach(System.out::println);
        getList1(list,e->e.getAge()>30).forEach(System.out::println);
    }

    //stream
    @Test
    public void test5(){
        //list.stream().filter(e->e.getAge()>30).forEach(System.out::println);
        list.stream().map(Employee::getName).limit(3).forEach(System.out::println);
    }



}
