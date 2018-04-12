package cms.sre.gitlab_to_hygieia_transformer;

import cms.sre.mongo_connection_helper.MongoClientFactory;
import cms.sre.mongo_connection_helper.MongoClientParameters;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@PropertySource(value = "file:/data/gitlab-to-hygieia-transformer/configuration/application.properties", ignoreResourceNotFound = true)
@SpringBootApplication()
public class App extends AbstractMongoConfiguration {
    @Value("${mongodb.databaseName:#{null}}")
    private String mongoDatabaseName;

    @Value("${mongodb.keyStoreKeyPassword:#{null}}")
    private String mongoKeyStoreKeyPassword;

    @Value("${mongodb.keyStoreLocation:#{null}}")
    private String mongoKeyStoreLocation;

    @Value("${mongodb.keyStorePassword:#{null}}")
    private String mongoKeyStorePassword;

    @Value("${mongodb.trustStoreLocation:#{null}}")
    private String mongoTrustStoreLocation;

    @Value("${mongodb.trustStorePassword:#{null}}")
    private String mongoTrustStorePassword;

    @Value("${mongodb.username:#{null}}")
    private String mongoUsername;

    @Value("${mongodb.password:#{null}}")
    private String mongoPassword;

    @Value("${mongodb.replicaSetLocation:#{null}}")
    private String[] mongoReplicaSetLocation;

    @Value("${mongodb.mongoReplicaSetName:#{null}}")
    private String mongoReplicaSetName;

    private static String nullWrapper(String value){
        return value == null ? "(null)" : value;
    }

    private static String nullWrapper(String[] values){
        String ret = null;
        if(values.length > 0){
            ret = "";
            for (String value : values) {
                ret += nullWrapper(value);
            }
        }
        return ret;
    }

    @Override
    public MongoClient mongoClient() {
        System.out.println();
        System.out.println();
        System.out.println("########################");

        System.out.println("Configuration Values");
        System.out.println("MongoKeyStoreKeyPassword : " + nullWrapper(this.mongoKeyStoreKeyPassword));
        System.out.println("MongoKeyStorePassword : " + nullWrapper(this.mongoKeyStorePassword));
        System.out.println("MongoKeyStoreLocation : " + nullWrapper(this.mongoKeyStoreLocation));
        System.out.println("MongoTrustStoreLocation : " + nullWrapper(this.mongoTrustStoreLocation));
        System.out.println("MongoTrustStorePassword : " + nullWrapper(this.mongoTrustStorePassword));
        System.out.println("MongoDatabaseName : " + nullWrapper(this.mongoDatabaseName));
        System.out.println("MongoUsername : " + nullWrapper(this.mongoUsername));
        System.out.println("MongoPassword : " + nullWrapper(this.mongoPassword));
        System.out.println("MongoReplicaSetLocation : " + nullWrapper(this.mongoReplicaSetLocation));
        System.out.println("MongoReplicaSetName : " + nullWrapper(this.mongoReplicaSetName));

        System.out.println("########################");
        System.out.println();
        System.out.println();

        MongoClientParameters params = new MongoClientParameters()
                .setKeyStoreKeyPassword(this.mongoKeyStoreKeyPassword)
                .setKeyStoreLocation(this.mongoKeyStoreLocation)
                .setKeyStorePassword(this.mongoKeyStorePassword)
                .setTrustStoreLocation(this.mongoTrustStoreLocation)
                .setTrustStorePassword(this.mongoTrustStorePassword)
                .setDatabaseName(this.mongoDatabaseName)
                .setMongoUsername(this.mongoUsername)
                .setMongoPassword(this.mongoPassword)
                .setReplicaSetLocations(this.mongoReplicaSetLocation)
                .setReplicaSetName(this.mongoReplicaSetName);

        MongoClient client = null;
        if(this.mongoReplicaSetLocation.length == 1 && this.mongoReplicaSetLocation[0].equalsIgnoreCase("localhost")){
            System.out.println("Connecting to Local Mongo");
            if(this.mongoUsername != null && this.mongoUsername.length() > 0 && this.mongoPassword != null && this.mongoPassword.length() > 0){
                client = MongoClientFactory.getLocalhostMongoClient(this.mongoDatabaseName, this.mongoUsername, this.mongoPassword);
            }else{
                client = MongoClientFactory.getLocalhostMongoClient();
            }
        }else{
            System.out.println("Connecting to Remote Mongo");

            client = MongoClientFactory.getMongoClient(params);
        }
        return client;
    }

    @Override
    protected String getDatabaseName() {
        return this.mongoDatabaseName;
    }

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }
}
