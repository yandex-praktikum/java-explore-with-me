# java-explore-with-me

Pull request:

https://github.com/Kazannik/java-explore-with-me-2/pull/1

| Сервисы:                  | ports (внешний: внутренний): | path:                                
|---------------------------|:----------------------------:|---------------------------------------------|                    
| ewm-gateway               |  8080:8080<br/> 9090:9090;   | http://gateway:8080<br/>http://gateway:9090 |
| ewm-main-service          |          6540:8080;          | http://ewm-main-service:8080                |
| postgresql-ewm-main-db    |          6541:5432;          | http://main-db:5432                         |
| ewm-stats-service         |          6542:8080;          | http://ewm-stats-service:8080               |                  
| postgresql-ewm-stats-db   |          6543:5432;          | http://stat-db:5432                         |
