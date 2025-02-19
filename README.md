add application.properties and add a db config, for example this is the config i use for my mysql database (password is redacted)

spring.application.name=calculator
spring.datasource.url=jdbc:mysql://namox.idi.ntnu.no:3306/scottld
spring.datasource.username=scottld
spring.datasource.password=[redacted]
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
