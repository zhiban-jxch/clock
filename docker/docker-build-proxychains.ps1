proxychains docker buildx build --platform=linux/arm64,linux/amd64 -t jxch/zhiban-clock:$(Get-Date -Format 'yyyyMMddHH') . --push
