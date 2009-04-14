#!/bin/bash
killall orbd
orbd -ORBInitialPort 2500 &
cd build/classes
java servico/Servidor -ORBInitialPort 2500 -ORBInitialHost
