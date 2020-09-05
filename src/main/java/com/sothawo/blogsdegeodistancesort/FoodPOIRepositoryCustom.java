/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.blogsdegeodistancesort;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface FoodPOIRepositoryCustom {

    /**
     * search all {@link FoodPOI} that are within a given distance of a point
     *
     * @param geoPoint
     *     the center point
     * @param distance
     *     the distance
     * @param unit
     *     the distance unit
     * @return the found entities
     */
    List<SearchHit<FoodPOI>> searchWithin(GeoPoint geoPoint, Double distance, String unit);
}
