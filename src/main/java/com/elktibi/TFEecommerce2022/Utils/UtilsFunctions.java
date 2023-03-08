package com.elktibi.TFEecommerce2022.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UtilsFunctions {

    public static String uploadImagesDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images/products";
    public static String thymeleafFriendlyPath = "/images/products/";

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String calendarToDateString(Calendar date) {
        return dateFormat.format(date.getTime());
    }

    public static String[] clothingSize = {"XS", "S", "M", "L", "XL", "XXL"};

    public static String[] shoeSizing = {"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46"};

    public static String[] noSizing = {"unique"};

}
