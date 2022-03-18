package com.bezkoder.springjwt.utils;

import java.util.ArrayList;

import java.util.Arrays;

public class Constant {

    public static final int ORDER_STATUS = 1;
    public static final int DELIVERY_STATUS = 2;
    public static final int COMPLETE_STATUS = 3;
    public static final int RETURNED_STATUS = 4;
    public static final int CANCELED_STATUS = 5;

    public static ArrayList<Integer> SIZE_VN = new ArrayList<>(Arrays.asList(35, 36, 37, 38, 39, 40, 41, 42));
    public static ArrayList<Integer> LIST_ORDER_STATUS = new ArrayList<>(Arrays.asList(ORDER_STATUS, DELIVERY_STATUS, COMPLETE_STATUS, RETURNED_STATUS, CANCELED_STATUS));


    
}
