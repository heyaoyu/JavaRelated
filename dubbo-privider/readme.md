start zk
consumer invoke http://localhost:8088/hello?name=1
access http://localhost:7979/hystrix-dashboard and add http://localhost:8080/hystrix.stream to monitor