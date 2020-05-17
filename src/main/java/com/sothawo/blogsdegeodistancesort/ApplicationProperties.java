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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Configuration
@ConfigurationProperties(prefix = "com.sothawo.blog-sde-geodistancesort")
public class ApplicationProperties {
    /** host:port of the Elasticsearch cluster */
    private String elasticSearchHost;

    /** host:port of a proxy to use */
    private String elasticSearchProxy;

    public String getElasticSearchHost() {
        return elasticSearchHost;
    }

    public void setElasticSearchHost(String elasticSearchHost) {
        this.elasticSearchHost = elasticSearchHost;
    }

    public String getElasticSearchProxy() {
        return elasticSearchProxy;
    }

    public void setElasticSearchProxy(String elasticSearchProxy) {
        this.elasticSearchProxy = elasticSearchProxy;
    }
}
