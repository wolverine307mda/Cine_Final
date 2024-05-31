# Cineverso

¡Bienvenido a Cineverso! Esta aplicación ha sido diseñada por tres estudiantes de Desarrollo de Aplicaciones Web (DAW) del IES Luis Vives. Utilizamos Kotlin y JavaFX para desarrollar interfaces de usuario y crear un cine funcional con una base de datos para nuestros clientes, butacas y productos.

### Descripción

Cineverso es más que una simple aplicación de gestión de cine. Es una solución integral diseñada para simplificar la administración y operación de un cine moderno. Desde la venta de entradas hasta la gestión de butacas y productos, Cineverso ofrece una amplia gama de funcionalidades diseñadas para satisfacer las necesidades tanto de los clientes como de los administradores de cine.

### Funcionalidades Principales

- **Venta de Entradas Online:** Los usuarios pueden comprar entradas de cine en línea de forma rápida y sencilla, seleccionando sus asientos preferidos y añadiendo complementos si lo desean.
  
- **Gestión de Butacas:** Cineverso permite a los administradores gestionar la disponibilidad de las butacas en la sala, así como realizar un seguimiento de las reservas y ocupaciones de los asientos.
  
- **Control de Productos:** Además de la gestión de entradas, Cineverso también proporciona herramientas para administrar productos como bebidas, comidas y otros artículos que los clientes pueden adquirir durante su visita al cine.

### Tecnologías Utilizadas

- **Kotlin y JavaFX:** Utilizamos Kotlin y JavaFX para desarrollar una interfaz de usuario intuitiva y atractiva que garantiza una experiencia de usuario fluida.
  
- **Base de Datos:** Integramos una base de datos para almacenar y gestionar datos importantes relacionados con clientes, butacas, productos y ventas, asegurando así una gestión eficiente de la información.

## Diagramas

### CRUD de Producto

### Create
![Diagrama de Secuencia - Create de Producto](Documentacion/Diagramas%20de%20Secuencia/Create.png)

### Read
![Diagrama de Secuencia - Read de Producto](Documentacion/Diagramas%20de%20Secuencia/findById.png)

### Update
![Diagrama de Secuencia - Update de Producto](Documentacion/Diagramas%20de%20Secuencia/Update.png)

### Delete
![Diagrama de Secuencia - Delete de Producto](Documentacion/Diagramas%20de%20Secuencia/Delete.png)

## Proyecto de GESTIÓN DE CINE y Venta de Entradas.

### Descripción

Aplicación diseñada para facilitar la gestión integral de la representación de un cine, incluyendo la venta de entradas y selección de butaca entre otras opciones.

Trata de una solución para la administración de un cine. Esta aplicación permite a los usuarios comprar entradas de cine en línea, seleccionar asientos. Para los administradores del cine, proporciona herramientas para gestionar la disponibilidad de la sala, y realizar un seguimiento de las ventas de entradas, así como de la gestión de los productos.

### Objetivo del Proyecto

Queremos realizar un programa para gestionar nuestro cine usando bases de datos con **SQLite** y ficheros.

#### Funcionalidades

Nuestra aplicación hace uso de un menú interface para:

1. **Comprar entrada:** si hay butacas libres reservará una butaca con el número de socio. Se podrá añadir un máximo de 3 complementos. El número de socio es LLLNNN (L es letra y N es número). Se obtiene un fichero (entrada) llamado entrada_Butaca_NSocio_Fecha.html (entrada, más el identificador de la butaca, más el identificador del socio, más la fecha de la compra).
2. **Devolver entrada:** devuelve una entrada liberando los recursos asociados.
3. **Estado del cine:** Muestra el estado del cine.
4. **Obtener recaudación:** se obtiene la recaudación dada una fecha válida dada en formato AAAA/MM/DD.
5. **Importar complementos:** Importa complementos en base a un fichero CSV dado.
6. **Exportar estado del cine:** exporta el estado del cine dada una fecha válida AAAA/MM/DD en un fichero json dado.
7. **Configurar butacas:** Configura las butacas en base a un fichero CSV dado.
8. **Actualizar butaca:** Cambia la información de una butaca dado su identificador: LN.
