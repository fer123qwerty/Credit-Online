# Proyecto de prueba para entrevistas

Repositorio:

https://github.com/fer123qwerty/Credit-Online

El controllers a evaluar es: PaymentController

Notas: al ejecutar el proyecto se carga una base de datos H2 con la informacion de las tablas proporcionadas,  
cuando se ejecuta el servicio de modificara la base de datos, 
para las siguientes ejecusiones seria volver a ejecutar de nuevo el proyecto.

Este es el cUrl del servicio:

curl --location 'http://localhost:8080/api/payments/process?currentDate=2021-02-15&interestRate=0.075&vatRate=0.16&businessYearDays=360'

