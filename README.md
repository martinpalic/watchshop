## WatchShop 

Demo application. It represents rest service for uploading watches to eshop warehouse.

### Build Info
- Standard Spring Boot project with Maven based build. Consult framework documentation.
- Maven wrapper can be used
- Flyway is used for persistence migration. H2 embedded DB is used by default.
- Mapstruct and Lombok are used, for best experience use available IDE plugins.
- Rest API uses default JSON content, XML is also supported when specified by request header. Other formats can also be used if proper jackson databinding is added to pom.xml

### Future Improvements
- [ ] Work with generic product so eshop can expand in the future
- [ ] Prepare endpoints and services for advanced operations over products
- [ ] Secure Endpoints