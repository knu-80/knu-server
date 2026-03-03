package kr.co.knuserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisLuaConfig {

    /**
     * KEYS[1] = queueKey
     * KEYS[2] = memberKey
     * --
     * ARGV[1] = pubBoothId
     * ARGV[2] = pubWaitingId
     * ARGV[3] = timestamp
     */
    @Bean
    public DefaultRedisScript<Long> registerScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                """
                if redis.call('EXISTS', KEYS[2]) == 1 then
                    return 0
                end

                redis.call('SET', KEYS[2], ARGV[1])
                redis.call('ZADD', KEYS[1], ARGV[3], ARGV[2])

                return 1
                """
        );
        script.setResultType(Long.class);
        return script;
    }

    /**
     * KEYS[1] = queueKey
     * KEYS[2] = memberKey
     * --
     * ARGV[1] = pubWaitingId
     */
    @Bean
    public DefaultRedisScript<Long> cancelScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                """
                local removed = redis.call('ZREM', KEYS[1], ARGV[1])
                if removed == 1 then
                    redis.call('DEL', KEYS[2])
                    return 1
                end
                return 0
                """
        );
        script.setResultType(Long.class);
        return script;
    }
}