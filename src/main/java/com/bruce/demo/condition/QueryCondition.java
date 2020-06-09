package com.bruce.demo.condition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class QueryCondition {

    private Long id;

    private String name;

    private String depName;

    private Integer age;

    private int pageIndex;

    private int pageSize = 10;

    public PageRequest buildPageRequest() {
        return this.buildPageRequest(Sort.Direction.ASC, "id");
    }

    public PageRequest buildPageRequest(Sort.Direction direction, String sortBy) {
        return PageRequest.of(pageIndex, pageSize, direction, sortBy);
    }

}
