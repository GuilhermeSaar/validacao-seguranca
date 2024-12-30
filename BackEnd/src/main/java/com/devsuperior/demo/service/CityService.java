package com.devsuperior.demo.service;

import com.devsuperior.demo.dto.CityDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;


    @Transactional(readOnly = true)
    public List<CityDTO> findAllCities() {

        List<City> result = cityRepository.findAll();
        result.sort(Comparator.comparing(City::getName));

        return result.stream().map(CityDTO::new).collect(Collectors.toList());
    }


    @Transactional
    public CityDTO insertCity(CityDTO cityDTO) {

        City city = new City();
        city.setName(cityDTO.getName());

        city = cityRepository.save(city);
        return new CityDTO(city);
    }


}
