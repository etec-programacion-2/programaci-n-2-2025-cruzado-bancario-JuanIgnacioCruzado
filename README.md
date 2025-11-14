Requisitos Previos

Java JDK 11 o superior
Gradle 7.0 o superior
Git (opcional)







Instalación
Opción 1:
git clone git@github.com:etec-programacion-2/programaci-n-2-2025-cruzado-bancario-JuanIgnacioCruzado.git
cd programaci-n-2-2025-cruzado-bancario-JuanIgnacioCruzado


Compilar y ejecutar  
chmod +x gradlew
./gradlew build
./gradlew run

Uso de la Aplicación

Primera vez (Registro)

Clic en "CREAR CUENTA"
Completar: Nombre, Apellido, DNI, Saldo Inicial
Clic en "REGISTRAR"

Iniciar Sesión:

Ingresar DNI
Clic en "INGRESAR"

Operaciones:

Depositar: Agregar dinero a tu cuenta
Extraer: Retirar dinero (valida fondos)
Transferir: Enviar dinero a otra cuenta
Historial: Ver todas tus transacciones
Mi Cuenta ▼: Ver información o cerrar sesión


Persistencia de Datos
Los datos se guardan automáticamente en usuarios_banco.dat en el directorio del proyecto.

