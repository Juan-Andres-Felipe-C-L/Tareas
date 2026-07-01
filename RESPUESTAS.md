- Instructor a quién se presenta: Michael Giraldo Bañol
- Quién presenta: Juan Andrés Felipe Castro Londoño

NIVEL 3° de la actividad:

11.	¿Cuál es la diferencia entre @RestController y @Controller en Spring Boot? R/: Que el @RestController le da al proyecto las capacidades, estructuras y/o funcionalidades de una API - REST; a diferencia, de que @Controller se puede usar como Controlador para una aplicación o programa que se instala localmente en computadora.

12.	¿Por qué se usan DTOs en lugar de exponer directamente la entidad JPA? R/: Porque, si no hay estos DTOs, se podría llegar a exponer información sensible a personas que se podrían aprovechar de ella.

13.	¿Qué ventaja tiene @PrePersist sobre asignar la fecha en el constructor de la entidad? R/: Que automatiza varios de los procesos que hay que hacer manualmente, incluyendo, poner la hora de creación de un objeto.

14.	¿Qué diferencia hay entre spring.jpa.hibernate.ddl-auto=update y ddl-auto=create? ¿Cuál usarías en producción y por qué? R/: La diferencia es que la primera, como su nombre lo dice en inglés, hará que mientras se va modificando el proyecto en Sprint también se vaya actualizando la estructura de la base de datos. Y, la segunda configuración, hace que se cree las tablas de la base de datos mientras se digita el código de una aplicación.

En un futuro, yo utilizaría spring.jpa.hibernate.ddl-auto=update por el hecho de que ayuda a actualizar la base de datos, más no se borraria si hubiera algún error de ejecución.

15.	Si esta API fuera a producción con usuarios reales, menciona al menos 3 cambios que harías en la configuración o arquitectura.
R/: 1. Cambiaria el DTO global de la página por otros. 2.Cambiara la ruta de los Endpoints por unas más faciles de comprender. 3. A todos los endpoints, si tienen entrada de datos, yo los usaría mandandolos por la URL y con la anotación @PathVariable.
