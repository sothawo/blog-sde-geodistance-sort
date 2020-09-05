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

import org.springframework.lang.Nullable;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class RequestData {
    @Nullable
    private String name;
    private double lat;
    private double lon;
    @Nullable
    Double distance;
    @Nullable
    String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Nullable
    public Double getDistance() {
        return distance;
    }

    public void setDistance(@Nullable Double distance) {
        this.distance = distance;
    }

    @Nullable
    public String getUnit() {
        return unit;
    }

    public void setUnit(@Nullable String unit) {
        this.unit = unit;
    }
}
