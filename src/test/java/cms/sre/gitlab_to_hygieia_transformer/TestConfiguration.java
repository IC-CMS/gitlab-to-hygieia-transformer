package cms.sre.gitlab_to_hygieia_transformer;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@org.springframework.boot.test.context.TestConfiguration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class TestConfiguration extends App{
    private MongodExecutable mongodExecutable;

    public TestConfiguration(){
        super.mongoDatabaseName = "test";
    }

    @Override
    public MongoClient mongoClient() {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        int port = 27017;
        try {
            IMongodConfig mongodConfig = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(port, Network.localhostIsIPv6()))
                    .build();

            this.mongodExecutable = starter.prepare(mongodConfig);
            MongodProcess process = mongodExecutable.start();

        } catch(Exception e){
            //Wrapping Exceptions
            throw new RuntimeException(e);
        }
        return new MongoClient("localhost", port);
    }

    @Override
    public void finalize(){
        if(this.mongodExecutable != null){
            this.mongodExecutable.stop();
        }
    }
}
