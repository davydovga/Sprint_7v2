package responses.order;

import lombok.Data;

import java.util.List;

@Data
public class GetOrderResponse {
    private List<Orders> orders;
    private PageInfo pageInfo;
    private List<Station> availableStations;
}
