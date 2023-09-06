package org.huajuan.rtmp_auth_server.controller;

import org.huajuan.rtmp_auth_server.service.RtmpAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RtmpAuthController {

    @Autowired
    private RtmpAuthService rtmpAuthService;

    @PostMapping("/live_control/play")
    public ResponseEntity<String> play(@RequestParam(value = "username") String username,
                               @RequestParam(value = "password") String password) {
        var authResult = rtmpAuthService.auth(username, password);
        if (authResult.getSuccess()){
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResult.getReason());
    }

    @PostMapping("/live_control/publish")
    public ResponseEntity<String> publish(@RequestParam(value = "username") String username,
                                          @RequestParam(value = "password") String password) {
        var authResult = rtmpAuthService.auth(username, password);
        if (authResult.getSuccess()){
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResult.getReason());
    }

}
