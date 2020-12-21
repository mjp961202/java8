package com.example.demo;

import com.example.demo.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 * Java8内置的四大核心函数式接口
 * Consumer<T> :消费型接口
 *         void accept(T t);
 * Supplier<T> ∶供给型接口
 *         T ();
 * Function<T，R> ︰函数型接口
 *         R apply(T t);
 * Predicate<T> ︰断言型接口
 *         boolean test(T t);
 */
@SpringBootTest
public class HanShuTest {

    //Consumer<T> :消费型接口   void accept(T t);
    @Test
    public void test1(){
        happy(10000,m-> System.out.println("消费："+m+"元"));
    }

    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

    //Supplier<T> ∶供给型接口   T get();
    @Test
    public void test2(){
        getNumList(10,()->(int) Math.random()*100).forEach(System.out::println);
    }

    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=num;i++){
            Integer n=sup.get();
            list.add(n);
        }
        return  list;
    }

    //Function<T，R> ︰函数型接口   R apply(T t);
    @Test
    public void test3(){
        String newStr=strHandler("   mjp  961202  ",(str)-> str.trim());
        System.out.println(newStr);
    }

    public String strHandler(String str, Function<String,String> fun){
        return fun.apply(str);
    }

    //Predicate<T> ︰断言型接口   boolean test(T t);
    @Test
    public void test4(){
        filterStr(Arrays.asList("str","asd","qwe","zxc"),str->str.indexOf("s")!=-1).forEach(System.out::println);
    }

    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> lis=new ArrayList<>();
        for (String str:list){
            if(pre.test(str)){
                lis.add(str);
            }
        }
        return lis;
    }

    /**
     * 方法引用:若 Lambda 体中的内容有方法已经实现了，我们可以使用"方法引用"
     * (可以理解为方法引用是Lambda表达式的另外一种表现形式)
     */
    //对象::实例方法名
    @Test
    public void test(){
        PrintStream ps= System.out;

        Consumer<String> tConsumer = x -> ps.println(x);
        tConsumer.accept("qwe");

        Consumer<String> consumer = ps::println;
        consumer.accept("asd");

        Consumer<String> con = System.out::println;
        con.accept("zxc");
    }

    //类::静态方法名
    @Test
    public void tes(){
        Comparator<Integer> com=(x,y)->Integer.compare(x,y);
        System.out.println(com.compare(6,5));
        Comparator<Integer> cop= Integer::compare;
        System.out.println(cop.compare(5,6));
    }

    //类::实例方法名
    //****************属性一必须是方法的调用者****************
    @Test
    public void te(){
        BiPredicate<String,String> bp=(x,y)->x.equals(y);
        System.out.println(bp.test("str","s t r"));
        BiPredicate<String,String> bip=String::equals;
        System.out.println(bip.test("str","str"));
    }

    /**
     * 构造器引用
     * 需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致!
     */
    @Test
    public void t(){
        Supplier<Employee> sup=()->new Employee();
        Supplier<Employee> sp=Employee::new;
    }


}
