package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dtos.response.MonthTopSellers;
import server.server.mappers.TopMonthSellersMapper;
import server.server.repository.OrderRepository;
import server.server.service.TopPerformersService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopPerformersServiceImpl implements TopPerformersService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<MonthTopSellers> getTop3SellersOfTheMonth() {
        List<Object[]> list = orderRepository.getTop3SellersOfTheMonth();
        List<MonthTopSellers> monthTopSellers = new ArrayList<>();
        for (Object o[]: list) {
            MonthTopSellers seller = MonthTopSellers.builder()
                    .id((Long) o[0])
                    .name((String) o[1])
                    .surname((String) o[2])
                    .username((String) o[3])
                    .longitude((Double) o[4])
                    .latitude((Double) o[5])
                    .numberOfOrders((Long) o[6])
                    .build();
            monthTopSellers.add(seller);
        }

        return monthTopSellers;
    }
}
