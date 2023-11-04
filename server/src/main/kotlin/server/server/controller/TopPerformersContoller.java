package server.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.server.dtos.response.MonthTopSellers;
import server.server.service.TopPerformersService;

import java.util.List;

@RestController
@RequestMapping("top3")
public class TopPerformersContoller {
    @Autowired
    TopPerformersService topPerformersService;
    @GetMapping("/sellers")
    public List<MonthTopSellers> getTop3Sellers(){
        return topPerformersService.getTop3SellersOfTheMonth();
    }


}
