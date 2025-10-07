# 🩺 Sistema de Gestión de Turnos - Consultorio Médico

## 📘 Descripción

Este proyecto corresponde al desarrollo de un sistema de gestión de turnos para un consultorio médico.
El objetivo es reemplazar la gestión manual en papel por un sistema informático que permita asignar, modificar, cancelar y listar turnos de manera eficiente, evitando la pérdida o superposición de los mismos.

El sistema está implementado en Java, con una interfaz por consola, y utiliza una estructura de datos en memoria (sin persistencia en archivos o base de datos).

## 🎯 Objetivo del Sistema

- El sistema busca resolver los problemas detectados en la gestión actual del consultorio:
- Evitar la superposición de turnos mediante la verificación de disponibilidad de fecha y hora.
- Mantener un registro consistente de los turnos vigentes.
- Permitir la cancelación y modificación de turnos de forma controlada y rápida.
- Brindar feedback claro a la secretaria usuaria mediante mensajes de texto intuitivos.

## 🧩 Funcionalidades principales

#### Reservar Turno
- La secretaria selecciona una fecha y hora disponible.
- Se ingresan los datos del paciente.
- Se confirma el turno.

#### Modificar Turno:
- Se selecciona el turno existente a modificar.
- Se confirma la selección.
- Se ingresan nueva fecha y hora válidas.

#### Cancelar Turno:
- Se selecciona el turno a cancelar.
- Se confirma la cancelación.

#### Listar Turnos por Fecha y Hora:
- Permite visualizar los turnos asignados en un rango o fecha determinada.