# [🔥 UPDATE V2] SpringBoot

Aplicación base de Spring Boot para nuevos proyectos

## 🏷️ Características de la aplicación

```
1. Utiliza base de datos PostgreSQL
2. Incorpora MultiTenant con diferentes conexiones a bases de datos
3. Creación automática de la base de datos a partir del modelo
4. Creación automática del modelo a partir de un script
5. Implementación sencilla de la autenticación de la aplicación
6. Crud genérico para todas las entidades
7. Endpoint para búsquedas avanzadas
```

## 💾 Base de datos PostgreSQL

```
La aplicación utiliza una base de datos PostgreSQL
Por defecto genera las siguientes tablas en el primer arranque

c_multitenant_datasource (
    id bigInt # id del tenant
    driverclassname varchar(255) # driver para conectarse, ejemplo: org.postgresql.Driver
    active boolean # cuando arranca la aplicación solo carga los tenant activos
    name varchar(255) # nombre del tenant que se utilizará en los headers de las llamadas
    password varchar(255) # contraseña del usuario con el que se conectará a la base de datos
    url varchar(255) # url de conexión, ejemplo: jdbc:postgresql://localhost:5432/postgres?ApplicationName=MultiTenant
    username varchar(255) # usuario con el que se conectará a la base de datos
)

flyway_schema_history {
    # tabla que utiliza la librería flyway para las migraciones de base de datos
}

revinfo {
    # tabla para guardar los datos de auditoría de las entidades
}

o_mstr_example{
    # tabla de la entidad que se utiliza como base para generar otras entiades

    id # id de la fila
    created_by # id del usuario que ha insertado esta fila
    created_on # fecha en la que el usuario ha insertado esta fila
    modified_by # id del último usuario que ha modificado esta fila
    modified_on # fecha de la última modificación de esta fila
}

o_mstr_example_aud{
    # tabla de auditoría de la entidad que se utiliza como base para generar otras entiades
    
    id # id de la entidad modificada
    rev # id de la revisión
    revtype # versión de la fila, aumenta en cada modificación
    created_by # id del usuario que ha insertado esta fila
    created_on # fecha en la que el usuario ha insertado esta fila
    modified_by # id del último usuario que ha modificado esta fila
    modified_on # fecha de la última modificación de esta fila
}
```

## 🏹 MultiTenant

```
La aplicación permite la separación por Tenant mediante diferentes bases de datos
Por defecto se utiliza como tenant la base de datos postgres y el schema public
Cada nuevo tenant será una base de datos diferente pero siempre se utilizará el schema public

Para generar un nuevo tenant seguir los siguientes pasos:
1. Crear una nueva base de datos
2. Lanzar una instancia de la aplicación contra la base de datos para que genere las tablas iniciales (** NO HACER ESTAS MIGRACIONES EN PRODUCCIÓN)
3. Añadir a la tabla "c_multitenant_datasource" del cluster principal una fila con los datos de la conexión
4. Reiniciar la aplicación del cluster principal para que cargue los nuevos tenant

Para poder seleccionar al tenant al que quieres acceder en cada petición se necesita la siguiente cabecera: X-TenantID
El valor de la cabecera tiene que coincidir con el nombre de uno existente en la tabla "c_multitenant_datasource"
En el caso de que el usuario realice una petición contra un tenant que no exista o no tenga permisos recibirá un código 403
La lista de tenants a los que un usuario tiene acceso se cargan en el proceso de autenticación, en la clase "UserDetailsServiceImpl"

** En producción modificar la propiedad "spring.jpa.hibernate.ddl-auto=update" y realizar las migraciones de las bases de datos de otra forma
``````

## 🔐 Autenticación

``````
El código permite modificar la autenticación de forma sencilla
Simplemente modificar la clase "UserDetailsServiceImpl" para recuperar los datos del usuario a partir del token
El token se espera mediante la cabecera "Authentication"
En el caso de que el token no sea válido dicho método deberá responder con null, esto provocará que el usuario reciba un código 401
En el caso de que el token sea válido pero no tenga permisos siguiendo las exploresiones de Spring Security recibirá un código 403

Ejemplos de expresiones de seguridad de Spring Security -> https://www.baeldung.com/spring-security-check-user-role
``````

## 🔍 Métodos avanzados de búsqueda

``````
Cada entidad implementa una ruta "/search" que permite realizar queries avanzadas
Para realizar las queries se esperan los siguientes parámetros:

page # Página actual de la búsqueda, se espera un valor 0...N
size # Tamaño de las páginas de la búsqueda, se espera un valor 1...N
sort # Criterio de ordenación para los resultados, se esperan valores siguiendo el ejemplo: nombre,ASC  descripcion,DESC
style # Permite cambiar el objeto que queremos como respuesta, el objeto BASE tendrá menos campos que el COMPLETE
search # Query para el filtrado, ejemplos de queries:

Tendrá como nombre pedro Y el apellido comenzará por T
- nombre:Pedro,apellido:T*

Tendrá como nombre pedro Y el apellido terminará por T
- nombre:Pedro,apellido:*T

Tendrá como nombre pedro O el apellido será fernandez
- nombre:Pedro'apellido:Ferenandez

Tendrá como nombre pedro O el apellido NO será fernandez
- nombre:Pedro'apellido!Ferenandez

Tendrá como nombre pedro Y el apellido estará vacío
- nombre:Pedro,apellido#_

Tendrá como nombre pedro Y el apellido no estará vacío
- nombre:Pedro,apellido¿_

Guia de los modificadores

1. Simple – can be represented by one character:

    Equality: represented by colon (:)
    Negation: represented by Exclamation mark (!)
    Greater than: represented by (>)
    Less than: represented by (<)
    Like: represented by tilde (~)
    Empty: represented by hashtag (#_)
    Null: represented by slash (/_)
    NotEmpty: represented by question mark (¿_)
    NotNull: represented by dollar ($_)
    
2. Complex – need more than one character to be represented:

    Starts with: represented by (=prefix*)
    Ends with: represented by (=*suffix)
    Contains: represented by (=*substring*)
``````

## 🔍 Añadir una relación entre entidades 1:N o N:1

``````
Para añadir una entidad 1:N se utilizarán como ejemplo las entidades Company y Department

1. Lanzar el script de ejemplo:

node scripts/clone_example.js Company String name String description
node scripts/clone_example.js Department String name String description
node scripts/clone_example.js Employee String name String surname String office String businessEmail Integer age Boolean active Double salary Double hourlyRate
node scripts/clone_example.js Material String name String description String location
node scripts/clone_example_usecase.js AddDepartmentToCompanyUseCase
node scripts/clone_example_usecase.js RemoveDepartmentFromCompanyUseCase

2. Añadir a la clase "Company" el siguiente código:

  @JsonManagedReference
  @OneToMany(mappedBy = "company")
  private List<Department> departments = new ArrayList<>();
  
3. Añadir a la clase "Department" el siguiente código:

  protected Long idCompany;

  @ToString.Exclude
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "idCompany", insertable = false, updatable = false)
  private Company company;
  
4. Añadir a la clase "CompanyOutputDTO" el siguiente código:

  List<BaseDepartmentOutputDTO> departments = new ArrayList<>();
  
5. Modificar en la clase "CompanyMapper" el siguiente método:

  @Mappings({@Mapping(source = "company", target = "departments", qualifiedByName = "departments")})
  CompanyOutputDTO toCompanyOutputDTO(Company company);
  
6. Añadir a la clase "CompanyMapper" el siguiente código:

  @Named("departments")
  default List<BaseDepartmentOutputDTO> departmentsToBaseDepartmentsOutputDTOS(Company company) {
    return company.getDepartments().stream()
        .map(DepartmentMapper.INSTANCE::toDepartmentOutputDTO)
        .collect(Collectors.toList());
  } 

7. Añadir a la clase "DepartmentInputDTO" el siguiente código: 

  protected Long idCompany;
  
8. Añadir a la clase "BaseDepartmentOutputDTO" el siguiente código:

  protected Long idCompany;
 
9. Añadir a la clase "DepartmentOutputDTO" el siguiente código:

  BaseCompanyOutputDTO company;
  
10. Modificar en la clase "DepartmentMapper" el siguiente método:

  @Mappings({@Mapping(source = "department", target = "company", qualifiedByName = "company")})
  DepartmentOutputDTO toDepartmentOutputDTO(Department department); 

11. Añadir a la clase "DepartmentMapper" el siguiente código:

  @Named("company")
  default BaseCompanyOutputDTO companyToBaseCompanyOutputDTO(Department department) {
    return CompanyMapper.INSTANCE.toBaseCompanyOutputDTO(department.getCompany());
  }
``````

## 🔍 Añadir búsquedas con relaciones en los search

``````
Para este ejemplo se necesita haber completado previamente el tutorial "Añadir una relación entre entidades 1:N o N:1"

1. Modificar el método de la clase "DepartmentSpecification"

  @Override
  public Predicate toPredicate(
      final Root<Department> root,
      @NotNull final CriteriaQuery<?> query,
      @NotNull final CriteriaBuilder builder) {
    String key = criteria.getKey();
    Object value = criteria.getValue();
    return switch (key){
      case "companyName" -> companyNamePredicate(root,builder,key,value);
      default -> getPredicate(root.get(key), builder, key, value);
    };
  }

  private Predicate companyNamePredicate(Root<Department> root, CriteriaBuilder builder, String key, Object value){
    Join<Department, Company> companyJoin = root.join("company");
    return getPredicate(companyJoin.get("name"),builder,key,value);
  }
  
2. Pasar como query en el método search una query válida:
 
companyName:Amazon

``````

## 🔍 Nuevo caso de uso

Para crear un caso de uso tenemos 2 opciones, crearlo a mano siguiendo el tutorial o utilizar el script para utilizar
una plantilla

``````
Ajemplos para lanzar el script y generar nuevos casos de uso

node clone_example_usecase.js AddDepartmentToCompanyUseCase
node clone_example_usecase.js RemoveDepartmentFromCompanyUseCase
``````

## 🔍 Personalizar las plantillas

Esta aplicación se base en plantillas para generar nuevo código mediante los scripts "clone_example.js"

Se pueden personalizar antes de lanzarse

Cada vez que se lanzan reemplazan el contenido de los ficheros que existan en origen, pero no modifica los que no
coincidan

¿Qué puedo modificar?

``````
- Para modificar la plantilla para los crud y las nuevas entidades modificar el contenido del paquete 

com.diegotobalina.framework.customizable.entities.example

- Para modificarla plantilla par los nuevos casos de uso modificar la clase

com.diegotobalina.framework.customizable.usecases.ExampleUseCase.java
``````

## 🚀 Desplegar en docker

``````
1. Compilar el proyecto con: mvn clean package
2. Generar la imagen de docker: docker compose build
3. Levantar la aplicación: docker compose up -d
``````

### ⚠️ Beware of forks. I do not give any guarantee that the fork may turn out to be a scam.


