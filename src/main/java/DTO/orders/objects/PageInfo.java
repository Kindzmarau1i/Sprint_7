package DTO.orders.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageInfo {
    private Integer page;
    private Integer total;
    private Integer limit;
}