package com.app.springboot.utilities;

import com.app.springboot.datatransfer.PaginationValues;

import java.util.Optional;

public class PaginationUtility {
    public PaginationValues CompletePaginationValues(Optional<Integer> offset, Optional<Integer> limit) {
        Integer offsetValue = 0;
        Integer limitValue = 3;

        if (offset.isPresent()) {
            offsetValue = offset.get();
        }

        if (limit.isPresent()) {
            limitValue = limit.get();
        }

        PaginationValues paginationValues = new PaginationValues();
        paginationValues.setLimit(limitValue);
        paginationValues.setOffset(offsetValue);

        return paginationValues;
    }
}
