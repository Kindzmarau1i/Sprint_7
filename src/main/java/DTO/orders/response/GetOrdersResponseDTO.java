package DTO.orders.response;

import lombok.Getter;
import lombok.Setter;
import DTO.orders.objects.AvailableStations;
import DTO.orders.objects.Orders;
import DTO.orders.objects.PageInfo;

import java.util.List;

@Getter
@Setter
public class GetOrdersResponseDTO {
    public List<Orders> orders;
    public PageInfo pageInfo;
    public List<AvailableStations> availableStations;
}