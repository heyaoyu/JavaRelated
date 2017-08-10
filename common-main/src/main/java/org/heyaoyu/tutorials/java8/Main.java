//package org.heyaoyu.tutorials.java8;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.stream.Stream;
//
///**
// * Created by heyaoyu on 2017/6/2.
// */
//public class Main {
//
//  public static void main(String[] args) {
//    List<String> stringList = new ArrayList<String>() {{
//      this.add("1");
//      this.add("2");
//      this.add("3");
//    }};
//
//    stringList.forEach(new Consumer<String>() {
//      @Override
//      public void accept(String s) {
//        System.out.println("Consumer handle:" + s);
//      }
//    });
//
//    Stream<String> stringStream = stringList.stream();
//    stringStream.filter(s -> s.length() > 5);
//
//    Stream<String> stringParallelStream = stringList.parallelStream();
//    stringParallelStream.filter(s -> s.length() > 5);
//  }
//
//}
