package server.server.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.server.dtos.response.TopProductsMonth;
import server.server.dtos.response.TopSellersMonth;
import server.server.repository.OrderRepository;
import server.server.service.TopPerformersService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopPerformersServiceImpl implements TopPerformersService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<TopSellersMonth> getTop3SellersOfTheMonth() {
        List<Object[]> list = orderRepository.getTop3SellersOfTheMonth();
        List<TopSellersMonth> topSellersMonthList = new ArrayList<>();
        for (Object o[]: list) {
            TopSellersMonth seller = TopSellersMonth.builder()
                    .id((Long) o[0])
                    .name((String) o[1])
                    .surname((String) o[2])
                    .username((String) o[3])
                    .longitude((Double) o[4])
                    .latitude((Double) o[5])
                    .numberOfOrders((Long) o[6])
                    .build();
            topSellersMonthList.add(seller);
        }

        return topSellersMonthList;
    }

    @Override
    public List<TopProductsMonth> getTop3ProductsOfTheMonth() {
        List<Object[]> list = orderRepository.getTop3ProductsOfTheMonth();
        List<TopProductsMonth> topProductsMonthList = new ArrayList<>();
        for (Object o[]: list) {
            TopProductsMonth product = TopProductsMonth.builder()
                    .productId((Long) o[0])
                    .productName((String) o[1])
                    .productPicture((String) o[2])
                    .sellerUsername((String) o[3])
                    .longitude((Double) o[4])
                    .latitude((Double) o[5])
                    .soldItems((Long) o[6])
                    .build();
            topProductsMonthList.add(product);
        }

        return topProductsMonthList;
    }
}
