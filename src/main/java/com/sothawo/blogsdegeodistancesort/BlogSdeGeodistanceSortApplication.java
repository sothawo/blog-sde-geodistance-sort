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

import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class BlogSdeGeodistanceSortApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogSdeGeodistanceSortApplication.class);

    private final FoodPOIRepository repository;

    public BlogSdeGeodistanceSortApplication(FoodPOIRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogSdeGeodistanceSortApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (repository.count() == 0) {
            LOGGER.info("Loading POI data...");
            AtomicLong count = new AtomicLong();
            Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource("germany-latest-food.csv").toURI()));
            Observable.fromIterable(lines::iterator)
                .filter(StringUtils::hasLength)
                .map(this::getFoodPOI)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .window(1000)
                .map(foodPOIObservable -> foodPOIObservable.toList().subscribe(foodPOIS -> {
                    count.addAndGet(foodPOIS.size());
                    LOGGER.info("saving {} POIs, total {}", foodPOIS.size(), count.get());
                    repository.saveAll(foodPOIS);
                }))
                .blockingSubscribe();
            LOGGER.info("Finished loading POI data.");
        }
    }

    private Optional<FoodPOI> getFoodPOI(String line) {
        try {
            final String[] fields = line.split("\\|");
            if (fields.length != 5) {
                throw new IllegalArgumentException("no 5 fields in line");
            }
            Integer category = Integer.valueOf(fields[0]);
            String id = fields[1];
            double lat = Double.parseDouble(fields[2]);
            double lon = Double.parseDouble(fields[3]);
            String name = fields[4];
            GeoPoint location = new GeoPoint(lat, lon);
            return Optional.of(new FoodPOI(id, category, name, location));
        } catch (Exception e) {
            LOGGER.error("error in line: \"{}\", {}", line, e.getMessage());
            return Optional.empty();
        }
    }

}
