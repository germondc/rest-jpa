# rest-jpa

Simple CRUD Rest API with hibernate JPA persistence

## items covered:
- Junit 5 tests, mocked mvc contoller tests
- H2 database with console
- Persistence attribute converter (enum to and from persisted string)
- Pagination - http://localhost:8080/lots?page=2&size=25
- [data.sql](https://github.com/germondc/rest-jpa/blob/master/src/main/resources/data.sql) for queries run at startup
- [application.properties](https://github.com/germondc/rest-jpa/blob/master/src/main/resources/application.properties) includes settings for logging system SQL
- Swagger UI with some customisation: http://localhost:8080/swagger-ui.html
- Configuration properties, with annontated metadata
