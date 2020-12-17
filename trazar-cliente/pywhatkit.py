#Pywhatkit
import pywhatkit
import sys
#Ejecucion : en cmd

#python3 pywhatkit.py phone bodyText timeToSend Delay
#python pywhatkit.py phone bodyText timeToSend Delay

#Ejemplo:
#python pywhatkit.py +542662547896 "Hola que tal probando" 13 20

pywhatkit.sendwhatmsg(sys.argv[1],sys.argv[2],sys.argv[3],sys.argv[4]);

#ACLARACION: timeToSend es la hora del dia a la quie lo quere enviar,
#delay es la cantidad de segundos a esperar antes de enviarlo.
