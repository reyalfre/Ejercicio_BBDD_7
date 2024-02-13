Sea la base de datos llamada Personal, compuesta de dos tablas Empleado y Departamento las cuales se generarán ejecutando el script personal.sql


Hacer un programa en java que cree la tabla OficinaEmpleados, cuya estructura es la siguiente:


Nombre Empleado, Nombre Departamento, Salario y Comisión.


Los datos a insertar, se sacarán de las tablas anteriores, excepto el atributo Comisión que se calculará de acuerdo al departamento al que pertenezca:


    -Si pertenece a 'Contabilidad', será el 10% del salario.

    -Si pertenece a 'Investigación', será el 20% del salario.

    -Si pertenece a 'Ventas', será el 5% del salario.

    -Si pertenece a 'Producción', será el 15% del salario


Posteriormente, se actualizará la tabla según la comisión. Las condiciones son:


    -Si la comisión es menor de 300, este se incrementará en un 10%

    -Si esta entre 400 y 600, se incrementara en un 5%

    -Si es mayor de 600 se quedará igual.


Finalmente, se escribirá en pantalla la tabla actualizada