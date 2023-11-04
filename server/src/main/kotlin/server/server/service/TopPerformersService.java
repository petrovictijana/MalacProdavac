package server.server.service;

import server.server.dtos.response.MonthTopSellers;

import java.util.List;

public interface TopPerformersService {
    List<MonthTopSellers> getTop3SellersOfTheMonth();
}
