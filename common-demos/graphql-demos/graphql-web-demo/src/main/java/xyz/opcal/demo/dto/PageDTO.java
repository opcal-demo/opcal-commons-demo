package xyz.opcal.demo.dto;

import java.util.List;

public record PageDTO<T> (List<T> content, Long total, Integer pageNum, Integer pageSize) {

}
