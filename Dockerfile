FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.3 as builder
COPY . /app
WORKDIR /app
RUN rm -rf build \
    && ./gradlew nativeCompile

FROM alpine:3 as runner
ENV USERNAME=admin
ENV PASSWORD=admin
COPY --from=builder /app/build/native/nativeCompile/rtmp_auth_server /app/rtmp_auth_server
WORKDIR /app
RUN apk add gcompat
CMD ["sh","-c","/app/rtmp_auth_server --rtmp.auth.username=$USERNAME --rtmp.auth.password=$PASSWORD"]
