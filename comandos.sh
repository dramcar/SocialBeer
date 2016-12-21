#! /bin/bash


#HOSTNAME=${HOSTNAME:davidr}


if [ $# -eq 0 ]; then
    echo "$0 compila|ejecuta|restaura"
else
    if [ "$1" = "compila" ]; then
        
        # Compilar el servicio
        javac socialwebservice/SocialBeer.java

        # Empaquetar clases compiladas del servicio
        jar cvf socialwebservice.jar socialwebservice/*.class

        # Copiar el .jar en el directorio webapps
        cp socialwebservice.jar ./axis-1_4/webapps/


        # Parar el servidor SimpleAxisServer en el directorio axis-1_4/webapps
        cd ./axis-1_4/webapps
        java -cp ../lib/axis-ant.jar:../lib/commons-logging-1.0.4.jar:../lib/axis.jar:../lib/jaxrpc.jar:../lib/saaj.jar:../lib/commons-discovery-0.2.jar:../lib/log4j-1.2.8.jar:../lib/wsdl4j-1.5.1.jar:../../mail.jar:../../activation.jar org.apache.axis.client.AdminClient -p 8888 quit

        # Ejecutar el SimpleAxisServer en el directorio axis-1_4/webapps
        java -cp ../lib/axis-ant.jar:../lib/commons-logging-1.0.4.jar:../lib/axis.jar:../lib/jaxrpc.jar:../lib/saaj.jar:../lib/commons-discovery-0.2.jar:../lib/log4j-1.2.8.jar:../lib/wsdl4j-1.5.1.jar:../../mail.jar:../../activation.jar:socialwebservice.jar:./ org.apache.axis.transport.http.SimpleAxisServer -p 8888 1> ../../logServer.txt 2>&1 &
        cd ../../


        # Desplegar el servidor en el directorio axis-1_4/webapps
        cd ./axis-1_4/webapps
        cp ../../deploySocial.wsdd ./axis/
        java -cp ../lib/axis-ant.jar:../lib/commons-logging-1.0.4.jar:../lib/axis.jar:../lib/jaxrpc.jar:../lib/saaj.jar:../lib/commons-discovery-0.2.jar:../lib/log4j-1.2.8.jar:../lib/wsdl4j-1.5.1.jar:../../mail.jar:../../activation.jar org.apache.axis.client.AdminClient -p 8888 axis/deploySocial.wsdd

        # Generar el wsdl de Banco desde el directorio axis-1_4/webapps
        java -cp ../lib/axis-ant.jar:../lib/commons-logging-1.0.4.jar:../lib/axis.jar:../lib/jaxrpc.jar:../lib/saaj.jar:../lib/commons-discovery-0.2.jar:../lib/log4j-1.2.8.jar:../lib/wsdl4j-1.5.1.jar:../../mail.jar:../../activation.jar org.apache.axis.wsdl.WSDL2Java "http://localhost:8888/axis/services/SocialBeer?wsdl"

        # Copiar directorios desde el directorio axis-1_4/webapps
        cp -r localhost/ ../../
        cp -r $HOSTNAME/ ../../
        cd ../../

        # Compila ClienteSocial con clases generadas a partir del wsdl
        javac -cp axis-1_4/lib/axis-ant.jar:axis-1_4/lib/commons-logging-1.0.4.jar:axis-1_4/lib/axis.jar:axis-1_4/lib/jaxrpc.jar:axis-1_4/lib/saaj.jar:axis-1_4/lib/commons-discovery-0.2.jar:axis-1_4/lib/log4j-1.2.8.jar:axis-1_4/lib/wsdl4j-1.5.1.jar:mail.jar:activation.jar:./ SocialBeerCliente.java


    elif [ "$1" = "ejecuta" ]; then # A SocialBeerCliente se le pasan los argumentos que van tras "ejecuta"
        shift
        java -cp axis-1_4/lib/axis-ant.jar:axis-1_4/lib/commons-logging-1.0.4.jar:axis-1_4/lib/axis.jar:axis-1_4/lib/jaxrpc.jar:axis-1_4/lib/saaj.jar:axis-1_4/lib/commons-discovery-0.2.jar:axis-1_4/lib/log4j-1.2.8.jar:axis-1_4/lib/wsdl4j-1.5.1.jar:mail.jar:activation.jar:./ SocialBeerCliente $*

    elif [ "$1" = "restaura" ]; then

        # Parar el servidor SimpleAxisServer en el directorio axis-1_4/webapps
        cd ./axis-1_4/webapps
        java -cp ../lib/axis-ant.jar:../lib/commons-logging-1.0.4.jar:../lib/axis.jar:../lib/jaxrpc.jar:../lib/saaj.jar:../lib/commons-discovery-0.2.jar:../lib/log4j-1.2.8.jar:../lib/wsdl4j-1.5.1.jar:../../mail.jar:../../activation.jar org.apache.axis.client.AdminClient -p 8888 quit
        rm -R ./localhost/
        rm -R ./$HOSTNAME/
        rm -R ../../localhost/
        rm -R ../../$HOSTNAME/
        rm ./axis/deploySocial.wsdd
        cd ../../
        rm *.class
        rm socialwebservice.jar
        rm ./socialwebservice/*.class
        rm ./axis-1_4/webapps/socialwebservice.jar
    else
        echo "$0 compila|ejecuta|restaura"
    fi

fi