package com.app.springboot.utils;

import com.app.springboot.datatransfer.CityData;

public class CityUtils {
    public static CityData getCityData() {
        CityData cityData = new CityData();

        cityData.setId(1);
        cityData.setName("Ciudad 1");

        return cityData;
    }
}
