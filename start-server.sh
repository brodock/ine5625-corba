#!/bin/bash
orbd -ORBInitialPort 2500 &
java build/classes/servico/Servidor
