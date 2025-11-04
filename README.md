# 🩺 Sistema de Gestión de Turnos - Consultorio Médico

## 📘 Descripción
Este proyecto implementa un sistema de gestión de turnos para un consultorio médico, diseñado con el objetivo de reemplazar la gestión manual en papel por una solución informática modular, escalable y mantenible.

El sistema permite asignar, modificar, cancelar y listar turnos de manera eficiente, evitando superposiciones y errores en la agenda.

Está desarrollado en Java puro, con una interfaz por consola y una estructura de datos en memoria, pero su arquitectura está preparada para incorporar persistencia en bases de datos o nuevas interfaces (por ejemplo, una web o escritorio) sin alterar la lógica central.

## 🎯 Objetivo del Sistema

- El sistema busca resolver los problemas detectados en la gestión actual del consultorio:
- Evitar la superposición de turnos mediante la verificación de disponibilidad de fecha y hora.
- Mantener un registro consistente de los turnos vigentes.
- Permitir la cancelación y modificación de turnos de forma controlada y rápida.
- Brindar feedback claro a la secretaria usuaria mediante mensajes de texto intuitivos.
- Aplicar principios de Clean Architecture, bajo acoplamiento y alta cohesión.

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

## 🧱 Arquitectura del Sistema

El proyecto sigue una arquitectura por capas, inspirada en principios de Clean Architecture y en patrones de diseño como Repository, Strategy y Result Pattern.

### 1. Capa Core

Contiene las entidades del dominio (`Turno`, `Paciente`) y las interfaces base (`ITurnoRepository`).
Define la lógica esencial del negocio, independiente de los detalles técnicos.

### 2. Capa Modules/Handlers

Cada caso de uso está encapsulado en un handler:
- `ReservaHandler`
- `ModificacionHandler`
- `CancelacionHandler`
- `ListadoHandler`

Esto permite aplicar el principio de responsabilidad única (SRP) y facilita pruebas unitarias o cambios individuales sin afectar el resto del sistema.

### 3. Capa Gestión

`GestionTurnos` actúa como fachada o coordinador, sirviendo de intermediario entre la interfaz de usuario y los módulos de negocio.
Valida reglas generales, coordina los handlers y devuelve resultados estandarizados.

### 4. Capa Interfaz/Presentación

Incluye el menú principal por consola (`MenuPrincipal`), encargado de interactuar con el usuario y mostrar resultados.
Está completamente desacoplada de la lógica de negocio, lo que permite reemplazarla fácilmente por otra interfaz.

### 5. Capa Utils

Contiene clases auxiliares reutilizables:
- `InputUtils`: lectura y normalización de datos de entrada.
- `ValidadorTurno` y `ValidadorPaciente`: validaciones por entidad.
- `ResultadoOperacion`: estructura estándar para resultados y mensajes entre capas.

## 🧠 Patrones y Principios Aplicados

| Patrón / Principio                        | Implementación                                | Propósito                                                       |
| ----------------------------------------- | --------------------------------------------- | --------------------------------------------------------------- |
| **Repository Pattern**                    | `ITurnoRepository` + `TurnoRepositoryMemoria` | Aislar la fuente de datos y permitir intercambiarla fácilmente. |
| **Strategy / Command Pattern**            | Handlers (`ReservaHandler`, etc.)             | Encapsular la lógica de cada caso de uso.                       |
| **Facade Pattern**                        | `GestionTurnos`                               | Coordinar los módulos y unificar el flujo del sistema.          |
| **Result Pattern**                        | `ResultadoOperacion`                          | Representar mensajes y estados de éxito/error entre capas.      |
| **Validator Pattern**                     | `ValidadorTurno`, `ValidadorPaciente`         | Separar las reglas de validación por entidad.                   |
| **Single Responsibility Principle (SRP)** | Todas las clases                              | Cada clase tiene una responsabilidad clara y única.             |

## 🔄 Beneficios del Diseño

- **Modularidad**: cada módulo puede evolucionar de forma independiente.
- **Reutilización**: validadores y utilidades se aplican en distintos contextos.
- **Escalabilidad**: es posible agregar nuevos casos de uso sin romper los existentes.
- **Mantenibilidad**: estructura limpia y fácilmente testeable.
- **Extensibilidad**: admite futuras implementaciones de persistencia o interfaces gráficas.
