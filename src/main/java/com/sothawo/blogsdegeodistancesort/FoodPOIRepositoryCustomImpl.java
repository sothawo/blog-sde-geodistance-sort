/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.blogsdegeodistancesort;

import org.elasticsearch.common.unit.DistanceUnit;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SuppressWarnings("unused")
public class FoodPOIRepositoryCustomImpl implements FoodPOIRepositoryCustom {

    private final ElasticsearchOperations operations;

    public FoodPOIRepositoryCustomImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public List<SearchHit<FoodPOI>> searchWithin(GeoPoint geoPoint, Double distance, String unit) {

        Query query = new NativeSearchQueryBuilder()
            .withQuery(
                geoDistanceQuery("location")
                    .point(geoPoint.getLat(), geoPoint.getLon())
                    .distance(distance, DistanceUnit.fromString(unit)))
            .build();

        // add a sort to get the actual distance back in the sort value
        Sort sort = Sort.by(new GeoDistanceOrder("location", geoPoint).withUnit(unit));
        query.addSort(sort);

        return operations.search(query, FoodPOI.class).getSearchHits();
    }
}
