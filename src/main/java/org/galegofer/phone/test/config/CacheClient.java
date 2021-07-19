package org.galegofer.phone.test.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import java.util.Optional;
import org.galegofer.phone.test.domain.model.payload.Root;
import org.springframework.stereotype.Component;

@Component
public class CacheClient {

    public static final String DEVICE_INFO = "deviceInfo";

    private final HazelcastInstance client
        = Hazelcast.newHazelcastInstance(createConfig());

    private Config createConfig() {
        Config config = new Config();
        config.addMapConfig(mapConfig());
        return config;
    }

    private MapConfig mapConfig() {
        MapConfig mapConfig = new MapConfig(DEVICE_INFO);
        mapConfig.setTimeToLiveSeconds(3600);
        mapConfig.setMaxIdleSeconds(3600);
        return mapConfig;
    }

    public Root put(String brandModel, Root deviceInfo) {
        IMap<String, Root> map = client.getMap(DEVICE_INFO);
        return map.putIfAbsent(brandModel, deviceInfo);
    }

    public Optional<Root> get(String brandModel) {
        IMap<String, Root> map = client.getMap(DEVICE_INFO);
        return Optional.ofNullable(map.get(brandModel));
    }

    public Root remove(String brandModel) {
        IMap<String, Root> map = client.getMap(DEVICE_INFO);
        return map.remove(brandModel);
    }
}
