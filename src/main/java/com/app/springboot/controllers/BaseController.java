package com.app.springboot.controllers;

import com.app.springboot.utilities.PaginationUtility;

public class BaseController {
    PaginationUtility paginationUtility;

    public BaseController() {
        paginationUtility = new PaginationUtility();
    }
}
