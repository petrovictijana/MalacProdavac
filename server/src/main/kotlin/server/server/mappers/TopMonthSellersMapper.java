package server.server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import server.server.dtos.response.MonthTopSellers;

@Mapper
public interface TopMonthSellersMapper {

    @Mappings(
            {
                    @Mapping(target = "id", source = "id"),
                    @Mapping(target = "name", source = "name"),
                    @Mapping(target = "surname", source = "surname"),
                    @Mapping(target = "username", source = "username"),
                    @Mapping(target = "longitude", source = "longitude"),
                    @Mapping(target = "latitude", source = "latitude"),
                    @Mapping(target = "numberOfOrders", source = "numberOfOrders")
            }
    )
    MonthTopSellers ObjectToMonthTopSellers(Object o);
}
