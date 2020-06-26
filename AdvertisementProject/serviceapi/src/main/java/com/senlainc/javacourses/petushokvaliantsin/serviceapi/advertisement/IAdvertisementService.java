package com.senlainc.javacourses.petushokvaliantsin.serviceapi.advertisement;

import com.senlainc.javacourses.petushokvaliantsin.dto.advertisement.AdvertisementDto;
import com.senlainc.javacourses.petushokvaliantsin.model.advertisement.Advertisement;

import java.util.List;

public interface IAdvertisementService {

    boolean create(AdvertisementDto object);

    boolean delete(Long index);

    boolean update(AdvertisementDto object);

    Advertisement read(Long index);

    AdvertisementDto getAdvertisement(Long index);

    List<AdvertisementDto> getUserAdvertisements(Long index, int page, int numberElements, String state);

    List<AdvertisementDto> getAdvertisements(int page, int numberElements, String direction, String sortField, String search,
                                             String category, double minPrice, double maxPrice);
}
