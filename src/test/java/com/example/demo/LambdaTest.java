package com.example.demo;

import com.example.demo.domain.Employee;
import com.example.demo.lambdas.MyInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.interceptor.KeyGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;


@SpringBootTest
public class LambdaTest {

    /**
     * Lambda表达式
     * 左侧:Lambda表达式的参数列表
     * 右侧:Lambda表达式中所需执行的功能,即 Lambda体
     */

    //语法格式一:无参数,无返回值
    @Test
    public void test1(){
        int x=5;
        Runnable r=()-> System.out.println("======================="+x);
        r.run();
    }

    //语法格式二:有一个参数,并且无返回值
    @Test
    public void test2(){
        Consumer<String> tConsumer = (x) -> System.out.println(x);
        tConsumer.accept("=========================");
    }

    //语法格式三:若只有一个参数,小括号可以省略不写
    @Test
    public void test3(){
        Consumer<String> tConsumer = x -> System.out.println(x);
        tConsumer.accept("=========================");
    }

    //语法格式四:有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
    @Test
    public void test4(){
        Comparator<Integer> com = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x,y);
        };
        System.out.println(com.compare(15,15));
    }

    //语法格式五:若Lambda 体中只有一条语句，return和大括号都可以省略不写
    @Test
    public void test5(){
        Comparator<Integer> com=(x,y)->Integer.compare(x,y);
        System.out.println(com.compare(15,11));
    }

    //语法格式六:Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
    @Test
    public void test6(){
        Comparator<Integer> com=(Integer x,Integer y)->Integer.compare(x,y);
        System.out.println(com.compare(1,11));
    }

    public Integer operation(Integer num, MyInterface my){
        return my.getValur(num);
    }

    /**
     * Lambda表达式需要“函数式接口”的支持
     * 函数式接口:接口中只有一个抽象方法的接口，称为函数式接口。
     * 可以使用注解@FunctionalInterface修饰
     */

    @Test
    public void tes1(){
        Integer operation = operation(100, x -> x * x);
        System.out.println(operation);
    }

    List<Employee> list = Arrays.asList(
            new Employee("qwe", 19, 9999.99),
            new Employee("asd", 29, 8888.88),
            new Employee("zxc", 39, 7777.77),
            new Employee("qaz", 49, 6666.66));

    //排序
    @Test
    public void tes2(){
        Collections.sort(list,(x, y)->{
            if(x.getAge()==y.getAge()){
                return x.getName().compareTo(y.getName());
            }else{
                return -Integer.compare(x.getAge(),y.getAge());
            }
        });
        list.forEach(System.out::println);
    }

}
