package com.example.demo;

import com.example.demo.domain.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream流不会对数据源造成改变
 */
@SpringBootTest
public class StreamTest {

    /**
     * 创建Stream
     */
    @Test
    public void test1() {
        //1.可以通过Collection系列集合的stream()或parallelStream()获取流
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();
        //2.通过Arrays中的静态方法stream()获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employees);
        //3.通过Stream的类的静态方法of()获取流
        Stream<String> stream3 = Stream.of("aa", "ss", "dd");
        Stream<Integer> stream4 = Stream.of(12, 34, 56);
        //4.创建无限流
        //迭代
        Stream<Integer> iterate5 = Stream.iterate(0, x -> x + 2);
        iterate5.limit(10).forEach(System.out::println);
        //生成
        Stream.generate(() -> Math.random()).limit(5).forEach(System.out::println);
    }

    List<Employee> list = Arrays.asList(
            new Employee("qwe", 19, 9999.99),
            new Employee("asd", 29, 8888.88),
            new Employee("asd", 29, 8888.88),
            new Employee("zxc", 39, 7777.77),
            new Employee("qaz", 49, 6666.66),
            new Employee("qaz", 49, 6666.66),
            new Employee("wsx", 59, 5555.55),
            new Employee("edc", 69, 4444.44),
            new Employee("edc", 69, 4444.44),
            new Employee("edc", 69, 4444.44)
    );

    /**
     * 中间操作
     */

    /*
     * 筛选与切片
     * filter——接收Lambda ，从流中排除某些元素。
     * limit(n)——截断流,使其元素不超过给定数量。
     * skip(n)——跳过元素，返回一个扔掉了前n 个元素的流。若流中元素不足n 个，则返回一个空流。与limit(n）互补
     * distinct——筛选，通过流所生成元素的hashCode()和equals(）去除重复元素
     */
    @Test
    public void test2() {
        //中间操作：不会执行任何操作++按顺序执行
        Stream<Employee> stream = list.stream()
                .filter(e -> {
                    System.out.println("短路");
                    return e.getAge() > 20;//满足条件之后不再执行
                })
                .skip(2)
                .distinct()
                .limit(3);

        //终止操作：一次性执行全部内容，即"惰性求值"
        stream.forEach(System.out::println);//内部迭代:迭代操作由Stream API 完成

        Iterator<Employee> iterator = list.iterator(); //外部迭代
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /*
     * 映射
     * map—--接收Lambda ,将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成-个新的元素
     * flatMap—--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test3() {
        List<String> li = Arrays.asList("qwe", "asd", "zxc", "qaz", "wsx", "edc");
        li.stream().map((str) -> str.toUpperCase()).forEach(System.out::println);
        li.forEach(System.out::println);
        li.stream().map(str -> str + "--").forEach(System.out::println);
        final Stream<String> stream1 = list.stream().map(Employee::getName);
        stream1.forEach(System.out::println);
        System.out.println("------------------------");
        Stream<Stream<Character>> stream = li.stream().map(StreamTest::filterCharacter);
        stream.forEach(sm -> sm.forEach(System.out::print));
        System.out.println("-------------------------");
        Stream<Character> characterStream = li.stream().flatMap(StreamTest::filterCharacter);
        characterStream.forEach(System.out::print);
    }

    public static Stream<Character> filterCharacter(String str) {
        List<Character> lc = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            lc.add(ch);
        }
        return lc.stream();
    }

    /*
     * 排序
     * sorted()——自然排序
     * sorted(Comparator com)——定制排序
     */
    @Test
    public void test4() {
        List<String> li = Arrays.asList("qwe", "asd", "zxc", "qaz", "wsx", "edc");
        li.stream().sorted().forEach(System.out::println);
        list.stream().sorted((e1, e2) -> e1.getName().compareTo(e2.getName())).forEach(System.out::println);
    }

    List<Employee> emp = Arrays.asList(
            new Employee("qwe", 19, 9999.99, Employee.Status.BUSY),
            new Employee("asd", 29, 8888.88, Employee.Status.FREE),
            new Employee("zxc", 39, 7777.77, Employee.Status.VOCATION),
            new Employee("qaz", 49, 6666.66, Employee.Status.BUSY),
            new Employee("wsx", 59, 5555.55, Employee.Status.VOCATION),
            new Employee("edc", 69, 4444.44, Employee.Status.FREE)
    );

    /*
     * 终止操作
     * al1Match—检查是否匹配所有元素
     * anyMatch—检查是否至少匹配一个元素
     * noneMatch—检查是否没有匹配所有元素
     * findFirst—返回第一个元素
     * findAny—返回当前流中的任意元素
     * count——返回流中元素的总个数
     * max-返回流中最大值
     * min——返回流中最小值
     */
    @Test
    public void test5() {
        //al1Match—检查是否匹配所有元素
        boolean b = emp.stream().allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);
        //anyMatch—检查是否至少匹配一个元素
        boolean b1 = emp.stream().anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);
        //noneMatch—检查是否没有匹配所有元素
        boolean b2 = emp.stream().noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);
        //findFirst—返回第一个元素
        Optional<Employee> first = emp.stream().sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary())).findFirst();
        System.out.println(first.get());
        //findAny—返回当前流中的任意元素
        Optional<Employee> any = emp.stream().filter(e -> e.getStatus().equals(Employee.Status.FREE)).findAny(); //串行流
        System.out.println(any.get());
        Optional<Employee> any1 = emp.parallelStream().filter(e -> e.getStatus().equals(Employee.Status.FREE)).findAny(); //并行流
        System.out.println(any1.get());
        //count——返回流中元素的总个数
        long count = emp.stream().count();
        System.out.println(count);
        //max-返回流中最大值
        Optional<Employee> max = emp.stream().max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());
        //min——返回流中最小值
        Optional<Integer> min = emp.stream().map(Employee::getAge).min(Integer::compare);
        System.out.println(min.get());
    }

    /**
     * 归约
     * reduce(T identity,BinaryOperator) / reduce(BinaryOperator)—可以将流中元素反复结合起来，得到一个值
     */
    @Test
    public void test6(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Integer reduce = integers.stream().reduce(1, (x, y) -> x + y);
        System.out.println(reduce);
        Optional<Integer> reduce2 = integers.stream().reduce((x, y) -> x + y);
        System.out.println(reduce2.get());
        Optional<Double> reduce1 = list.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(reduce1.get());
    }

    /**
     * 收集
     * collect—--将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test7(){
        List<String> collect = list.stream().map(Employee::getName).sorted(String::compareTo).collect(Collectors.toList());
        collect.forEach(System.out::println);
        System.out.println("---------toSet----------");
        Set<String> collect1 = list.stream().map(Employee::getName).collect(Collectors.toSet());
        collect1.forEach(System.out::println);
        System.out.println("----------toCollection---------");
        HashSet<String> collect2 = list.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        collect2.forEach(System.out::println);
        System.out.println("---------counting----------");
        Long collect3 = list.stream().collect(Collectors.counting());
        System.out.println(collect3);
        System.out.println("---------averagingDouble----------");
        Double collect4 = list.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(collect4);
        System.out.println("----------summingDouble---------");
        Double collect8 = list.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(collect8);
        System.out.println("----------summarizingDouble---------");
        DoubleSummaryStatistics collect5 = list.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(collect5);
        System.out.println("----------maxBy---------");
        Optional<Employee> collect6 = list.stream().collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(collect6.get());
        System.out.println("----------minBy---------");
        Optional<Double> collect7 = list.stream().map(Employee::getSalary).collect(Collectors.minBy(Double::compare));
        System.out.println(collect7);
        System.out.println("----------分组---------");
        Map<String,List<Employee>> map=list.stream().collect(Collectors.groupingBy(Employee::getName));
        System.out.println(map);
        System.out.println("----------分区----------");
        Map<Boolean, List<Employee>> collect9 = list.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 6000));
        System.out.println(collect9 );
        System.out.println("-----------连接-----------");
        String collect10 = list.stream().map(Employee::getName).collect(Collectors.joining("--","??","!!"));
        System.out.println(collect10);

    }



}
