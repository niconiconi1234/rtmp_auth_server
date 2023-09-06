# rtmp auth server

## usage

配合nginx-rtmp-module使用的鉴权服务器。

### 运行rtmp auth server
以下命令中环境变量`USERNAME`和`PASSWORD`为鉴权的用户名和密码，可以自行修改。
```bash
docker run \
-d \
--name rtmp-auth-server \
-p 8080:8080 \
-e USERNAME=admin \
-e PASSWORD=admin \
huajuan6848/rtmp_auth_server:0.0.1-SNAPSHOT
```

### nginx配置

```nginx
rtmp {
    server {
        listen 1935;
        chunk_size 4096;

        application live {
            live on;
            # 鉴权
            on_publish http://host-rtmp-auth-server:8080/live_control/publish;
            on_play http://host-rtmp-auth-server:8080/live_control/play;
        }
    }
}
```

### 推流
启动带有rtmp模块的nginx，推流时需要带上鉴权参数，例如：
`rtmp://host-rtmp-server:1935/live/stream?username=admin&password=admin`，其中admin和admin为鉴权的用户名和密码，应该与docker运行rtmp auth server时的环境变量`USERNAME`和`PASSWORD`保持一致。`stream`为流名，可以自行修改。

### 拉流
和推流一样，拉流时同样需要带上鉴权参数，例如：
`rtmp://host-rtmp-server:1935/live/stream?username=admin&password=admin`，其中admin和admin为鉴权的用户名和密码，应该与docker运行rtmp auth server时的环境变量`USERNAME`和`PASSWORD`保持一致。`stream`为流名，可以自行修改。

