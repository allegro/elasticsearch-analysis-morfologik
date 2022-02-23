package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.Base58;

import java.io.File;
import java.time.Duration;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class ElasticsearchWithPluginContainer extends GenericContainer<ElasticsearchWithPluginContainer> {
    /**
     * Elasticsearch Default HTTP port
     */
    private static final int ELASTICSEARCH_DEFAULT_PORT = 9200;

    /**
     * Elasticsearch Default Transport port
     */
    private static final int ELASTICSEARCH_DEFAULT_TCP_PORT = 9300;

    private final String dockerImage;
    private File plugin;

    /**
     * Create an Elasticsearch Container by passing the full docker image name
     *
     * @param dockerImageName Full docker image name, like: docker.elastic.co/elasticsearch/elasticsearch:6.4.1
     */
    public ElasticsearchWithPluginContainer(String dockerImageName) {
        super(dockerImageName);
        this.dockerImage = dockerImageName;
    }

    public ElasticsearchWithPluginContainer withPlugin(File pluginZipFile) {
        plugin = pluginZipFile;
        return this.withFileSystemBind(plugin.getPath(), "/tmp/plugins/" + plugin.getName());
    }

    private ImageFromDockerfile prepareImage(String imageName) {
        String pluginContainerPath = plugin == null ? null : ("/tmp/plugins/" + plugin.getName());
        ImageFromDockerfile image = new ImageFromDockerfile()
                .withDockerfileFromBuilder(builder -> {
                    builder.from(imageName);
                    if (pluginContainerPath != null) {
                        builder.copy(pluginContainerPath, pluginContainerPath);
                        builder.run("bin/elasticsearch-plugin", "install", "file://" + pluginContainerPath);
                    }
                });
        if (pluginContainerPath != null) {
            image.withFileFromFile(pluginContainerPath, plugin);
        }
        return image;
    }

    @Override
    protected void configure() {
        logger().info("Starting an elasticsearch container using [{}]", dockerImage);
        withNetworkAliases("elasticsearch-" + Base58.randomString(6));
        withEnv("discovery.type", "single-node");
        withEnv("xpack.security.enabled", "false");
        addExposedPorts(ELASTICSEARCH_DEFAULT_PORT, ELASTICSEARCH_DEFAULT_TCP_PORT);
        setWaitStrategy(new HttpWaitStrategy()
                .forPort(ELASTICSEARCH_DEFAULT_PORT)
                .forStatusCodeMatching(response -> response == HTTP_OK || response == HTTP_UNAUTHORIZED)
                .withStartupTimeout(Duration.ofMinutes(2)));
        setImage(prepareImage(dockerImage));
    }

    public String getHttpHostAddress() {
        return getContainerIpAddress() + ":" + getMappedPort(ELASTICSEARCH_DEFAULT_PORT);
    }

    public ElasticsearchWithPluginContainer withCustomConfigFile(File configFile) {
        return this.withFileSystemBind(configFile.getPath(), "/usr/share/elasticsearch/config/" + configFile.getName());
    }
}
