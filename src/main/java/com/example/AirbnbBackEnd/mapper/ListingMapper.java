package com.example.AirbnbBackEnd.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {ListingPictureMapper.class})
public interface ListingMapper {
}
