# policies-app-prueba

## Resumen de la solución

Se ha diseñado un servicio backend robusto utilizando una arquitectura en capas. Esta estructura permite una separación
de las responsabilidades, facilitando el mantenimiento y la escalabilidad. Ante la premisa de manejar 40 millones de
clientes, se priorizó un diseño de base de datos normalizados que minimiza la redundancia y optimiza los tiempos de
respuesta.

### Modelo de datos

1. Entidades Principales:

- **Clients (Clientes):** Almacena la información básica del cliente. Se incluyen campos de auditoría como created_at y
  updated_at.
- **Policies (Pólizas):** Define los productos ofrecidos por la aseguradora. Se crea una tabla de tipos (policy_type)
  para
  clasificar fácilmente entre Vida, Vehículo o Salud.
- **Subscriptions (Suscripciones):** Esta es la tabla principal. Representa el contrato vigente entre un cliente y una
  póliza, manejando estados y el costo total.

2. Manejo de reglas de negocio:

- **Gestión de vehículos:** A través de subscription_vehicles y vehicles, permite asociar uno o más vehículos a una
  suscripción de tipo "Vehículo".
- **Gestión de Vida/Salud:** Por medio de las tablas subscription_beneficiary y beneficiaries, se gestionan los
  beneficiarios asociados a pólizas de Vida o Salud, permitiendo definir el parentesco (relationship_type)

3. Relaciones claves:

- **1:N (Clients - Subscriptions):** Un cliente puede tener múltiples suscripciones.
- **N:M (Subscriptions - Vehicles/Beneficiaries):** Se implementan tablas intermedias para dar flexibilidad al negocio.

### Cómo correr el proyecto en local

Para facilitar la revisión de la prueba, se implementó una base de datos en memoria (H2) que se autopuebla al iniciar
la aplicación

#### **1. Requisitos previos**

- Java JDK 21 instalado
- Maven 3.9+

#### **2. Pasos para la ejecución**

1. Clonar el repositorio

`git clone https://github.com/dev-leonardomn/policies-app-prueba.git`

2. Compilar el proyecto:

`./mvnw clean install`

3. Compilar el proyecto:

`./mvnw spring-boot:run`

#### **3. Acceso a la documentación y datos**

**- Swagger UI:** http://localhost:8080/swagger-ui/index.html

**- Consola H2:** http://localhost:8080/h2-console

- User: sa | Password: vacío

#### **4. Carga de datos inicial**

- La aplicación incluye un archivo **import.sql**. Al iniciar el backend, Spring Boot ejecuta automáticamente este
  script para:
    - Crear tipos de póliza (Vida, Vehículo, Salud).
    - Crear pólizas de prueba.
    - Insertar clientes de prueba.
    - Insertar beneficiarios de prueba.
    - Insertar vehículos de prueba.

### Propuesta AWS

Según mi experiencia y conocimiento básico con servicios de AWS y después de una corta investigación acerca de sus
servicios, podría sugerir una arquitectura como la siguiente:

**AWS Elastic Beanstalk:** Beanstalk es muy amigable a la hora de desplegar el servicio, ya que únicamente se sube el
archivo .jar y este servicio se encarga de crear el servidor, configurar el balanceador de carga y escalar la aplicación
automáticamente si hay mucho tráfico.

**Amazon RDS:** Es un servicio de base de datos que AWS administra por uno. Para gestionar el volumen de 40 millones de
registros, activaría las réplicas de lectura. Esto nos ayudará a que una base de datos guarda pólizas nuevas, otras se
encargan de responder las consultas de los clientes, evitando saturar el sistema.

**Amazon S3:** Este servicio nos puede ayudar a futuro si queremos almacenar archivos. Es un servicio de bajo costo y es
seguro, ya que está diseñado para guardar archivos pesados.

**API Gateway:** Utilizaría este servicio controlar quién puede a la API y evitar ataques que puedan saturar mis
servicios.

**Secrets Managers:** Para evitar exponer datos sensibles directamente en application.properties, utilizaría este
servicio para almacenarlos de forma segura.