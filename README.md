# Conversor de monedas

![STATUS](https://img.shields.io/badge/STATUS-FINALIZADO-blue)


### Descripción
* Conversor de monedas es una aplicación de línea de comandos (CLI) diseñada para permitir a los usuarios
  convertir entre diferentes monedas utilizando tipos de cambio actuales.
  La aplicación utilizará la API de ExchangeRate-API para obtener tipos de cambio en
  tiempo real y realizará conversiones basadas en las entradas del usuario.


### Funciones
- _**Conversión de Moneda:**_ Los usuarios podrán ingresar la cantidad de dinero y las monedas
  de origen y destino para realizar la conversión. La aplicación utilizará la API
  para obtener el tipo de cambio actual entre las dos monedas y calculará el monto convertido.


- _**Selección de Monedas:**_ La aplicación permitirá a los usuarios seleccionar las monedas
  de una lista predefinida o ingresar sus propios códigos de moneda según el estándar ISO 4217.


- _**Actualización de Tipos de Cambio:**_ La aplicación actualizará automáticamente los tipos de
  cambio en intervalos regulares para garantizar la precisión de las conversiones.


- _**Historial de Conversiones:**_ La aplicación mantendrá un registro de las conversiones
  realizadas por el usuario, incluyendo la fecha, la cantidad convertida, las monedas de
  origen, destino, y el tipo de cambio utilizado. El historial se mostrara al finalizar
  el programa.


- _**Generar un Json del historial de conversiones:**_  La aplicación permite a los usuarios almacenar de manera
  estructurada y legible el registro de todas las conversiones realizadas. El archivo se ubica
  dentro de la estructura de carpetas del proyecto.


### Herramientas utiilizadas
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)


### Acceso al proyecto
- <p>Puedes <a href="https://github.com/DL-VI/conversor-monedas">acceder al código fuente del proyecto</a> o <a href="https://github.com/DL-VI/conversor-monedas/archive/refs/heads/main.zip">descargarlo</a>.</p>


### Abrir y ejecutar
- Este proyecto utiliza **Maven** para gestionar sus dependencias.
  Al clonar o descargar este repositorio, asegúrate de tener **Maven** instalado en tu sistema o
  ábrelo en Intellij Idea para cargar las dependencias automaticamente.


- Busca la carpeta **app** y ejecuta el **_Main_**
