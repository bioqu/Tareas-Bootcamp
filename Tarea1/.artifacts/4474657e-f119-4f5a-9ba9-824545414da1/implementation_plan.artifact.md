# Plan de Modularización de IconoButton

El objetivo es permitir que el componente `IconoButton` sea reutilizable aceptando el icono (`ImageVector`) y la acción (`onClick`) como parámetros, en lugar de tenerlos prefijados.

## Cambios Propuestos

### UI Components

#### [MODIFY] [IconoButton.kt](file:///Users/luispereira/Desarrollo/Bootcamp/Tareas/Tarea1/app/src/main/java/cl/uchile/dcc/mobile/gastospersonales/ui/component/IconoButton.kt)
- Modificar la firma de la función para recibir:
    - `icon: ImageVector`: El icono a mostrar.
    - `onClick: () -> Unit`: La acción al pulsar.
    - `contentDescription: String?`: Descripción para accesibilidad.
    - `modifier: Modifier`: Para permitir ajustes de padding/layout desde fuera.
- Importar `androidx.compose.ui.graphics.vector.ImageVector`.

#### [MODIFY] [MainActivity.kt](file:///Users/luispereira/Desarrollo/Bootcamp/Tareas/Tarea1/app/src/main/java/cl/uchile/dcc/mobile/gastospersonales/ui/MainActivity.kt)
- Reemplazar las implementaciones directas de `IconButton` por el nuevo componente `IconoButton`.
- Configurar las acciones del ViewModel (`changetoFormulario`, `changetoGastos`) en los botones correspondientes.

## Plan de Verificación

### Verificación Manual
- Desplegar la aplicación y verificar que los botones en la `topBar` y `bottomBar` funcionan correctamente.
- Comprobar que al cambiar el icono en la llamada al componente, este se actualiza visualmente.
