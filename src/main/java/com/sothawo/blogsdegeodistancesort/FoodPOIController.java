/*
 Copyright 2020 the original author(s)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package com.sothawo.blogsdegeodistancesort;

import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@RestController
@RequestMapping("/foodpois")
public class FoodPOIController {

    private final FoodPOIRepository repository;

    public FoodPOIController(FoodPOIRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/nearest3")
    List<ResultData> nearest3(@RequestBody RequestData requestData) {

        GeoPoint location = new GeoPoint(requestData.getLat(), requestData.getLon());
        Sort sort = Sort.by(new GeoDistanceOrder("location", location).withUnit("km"));

        List<SearchHit<FoodPOI>> searchHits;

        if (StringUtils.hasText(requestData.getName())) {
            searchHits = repository.searchTop3ByName(requestData.getName(), sort);
        } else {
            searchHits = repository.searchTop3By(sort);
        }

        return searchHits.stream()
            .map(searchHit -> {
                Double distance = (Double) searchHit.getSortValues().get(0);
                FoodPOI foodPOI = searchHit.getContent();
                return new ResultData(foodPOI.getName(), foodPOI.getLocation(), distance);
            }).collect(Collectors.toList());
    }
}
