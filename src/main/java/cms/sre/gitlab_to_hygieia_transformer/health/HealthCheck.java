package cms.sre.gitlab_to_hygieia_transformer.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.mongo.MongoHealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HealthCheck implements HealthIndicator {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Health health() {
        Health ret = null;
        if(this.mongoTemplate != null){
            ret = new MongoHealthIndicator(this.mongoTemplate)
                    .health();
        } else {
            ret = Health.down()
                    .withDetail("MongoTemplate-Autowired", false)
                    .build();
        }
        return ret;
    }
}
