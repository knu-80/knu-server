package kr.co.knuserver.application.booth;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UuidDeviceIdGenerator implements DeviceIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
