# aplicaciones-moviles

En el repositorio se podra encontrar el codigo fuente de la aplicación Android que se conecta con el backend Vinilos.

Para poder ejecutar el proyecto se necesitan realizar los siguiente pasos:

1. Clonar el repositorio ``git@github.com:ChristianBorrasTorres/aplicaciones-moviles.git``

2. Tener instalado Android Studio en la maquina

3. Importar el proyecto con la opción Open y seleccionar la carpeta recien clonanda

4. Desde la vista Android (seleccionada en la parte superior izquiera), en la seccion Gradle Script, abrir el archivo build.gradle y realizar un Sync.

5. Desde el menu superior en la opción Build, seleccionar la opción Clean Project y despues Build Project

6. Si se genera un error en el proceso de Build, en el menu superior en la opción File seleccionar Invalidate Caches, esto con el objetivo de limpiar el proceso de compilación y arrancar desde 0. Si se requiere esta opción volver a realizar el paso 5.

7. Una vez finalizados los pasos anteriores Android Studio habra descargado todas las dependencias necesarias para el proyecto y compilado el proyecto, con esto se debe seleccionar el dispositivo donde se quiere correr la aplicación y dar click en el boton play como se ve en la siguiente imagen.
   ![image](https://user-images.githubusercontent.com/70450979/200200409-2721f2e1-adab-47ad-a340-940d992e3bc9.png)

6. Se debe esperar mientras finaliza el proceso de Build e Instalación de la aplicación.

7. La aplicación ya debe estar funcional en el dispostivo o emulador seleccionado.