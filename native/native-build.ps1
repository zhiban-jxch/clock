mvn clean -Pnative -Prelease spring-boot:build-image -f pom.xml

docker tag zhiban.clock:1.0.0 jxch/zhiban-clock:$(Get-Date -Format 'yyyyMMddHH')
docker tag zhiban.clock:1.0.0 jxch/zhiban-clock:1.0.0
docker tag zhiban.clock:1.0.0 jxch/zhiban-clock:latest
docker push jxch/zhiban-clock:$(Get-Date -Format 'yyyyMMddHH')
docker push jxch/zhiban-clock:1.0.0
docker push jxch/zhiban-clock:latest
