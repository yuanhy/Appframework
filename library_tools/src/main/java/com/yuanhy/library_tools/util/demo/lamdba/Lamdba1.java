package com.yuanhy.library_tools.util.demo.lamdba;


public class Lamdba1 {
    public static void main(String[] strings){
        Runnable runnable= () ->{
            System.out.println("lamdba111111111111");

        };
        runnable.run();
    }
}
