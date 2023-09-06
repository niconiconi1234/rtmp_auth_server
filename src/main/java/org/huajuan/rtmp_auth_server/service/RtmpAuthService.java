package org.huajuan.rtmp_auth_server.service;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class RtmpAuthService {

    @Value("${rtmp.auth.username}")
    private String correctUsername;

    @Value("${rtmp.auth.password}")
    private String correctPassword;

    @Data
    @NoArgsConstructor
    @RequiredArgsConstructor
    public class RtmpAuthResult {
        @NonNull
        private Boolean success;
        @NonNull
        private Integer code;

        public static Map<Integer, String> codeReasons = new HashMap<>();

        // 静态初始化块，类加载时执行
        static {
            codeReasons.put(0, "Success");
            codeReasons.put(1, "Username or password mismatch");
        }

        public String getReason() {
            return codeReasons.get(this.code);
        }
    }


    public RtmpAuthResult auth(String username, String password) {
        if (Objects.equals(username, correctUsername) && Objects.equals(password, correctPassword)) {
            return new RtmpAuthResult(true, 0);
        }
        return new RtmpAuthResult(false, 1);
    }


    @PostConstruct
    public void logUsernamePassword() {
        log.info("Username:{}, Password:{}", correctUsername, correctPassword);
    }
}
