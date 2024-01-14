package ru.yadzuka;

import java.util.List;

public class TestClass {

    public static void main(String[] args) {
        List<String> names = List.of("Adam Angels", "John Whilson", "Adam Aanger", "Kaney West");
        printNamesInOrder(names, true);
        System.out.println("");
        printNamesInOrder(names, false);
    }

    private static void printNamesInOrder(List<String> names, boolean nameFirst) {
        names.stream().sorted((o1, o2) -> {
            String[] split1 = o1.split("\\s+"), split2 = o2.split("\\s+");
            return nameFirst ? split1[0].compareTo(split2[0]) : split1[1].compareTo(split2[1]);
        }).forEach(System.out::println);
    }
}
