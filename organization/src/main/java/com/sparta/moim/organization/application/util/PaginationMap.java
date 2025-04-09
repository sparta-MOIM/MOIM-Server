package com.sparta.moim.organization.application.util;

import com.sparta.moim.common.page.Pagination;
import java.util.List;
import java.util.function.Function;

public class PaginationMap {
    public static <T, R> Pagination<R> map(Pagination<T> pagination, Function<T, R> mapper) {
        List<R> content = pagination.getContent().stream()
                .map(mapper)
                .toList();

        return Pagination.of(
                pagination.getPage(),
                pagination.getSize(),
                pagination.getTotal(),
                content
        );
    }

}
