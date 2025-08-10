# 🎯 GUÍA PARA NUEVO DESARROLLADOR - TONTUFOS2 MOD

## 👋 ¡Bienvenido al Proyecto!

Hola! Te voy a pasar el proyecto **Tontufos2** para que trabajes en él. Este es un mod de Minecraft para la versión 1.20.1 que fue creado para la serie de Twitch **TontufosSMP 2**.

## 📚 DOCUMENTACIÓN DISPONIBLE

He creado **3 archivos principales** para que entiendas todo el proyecto:

### 1. 📖 `DOCUMENTACION_TONTUFOS2.md` / `DOCUMENTACION_TONTUFOS2.pdf`
- **Documentación completa** del proyecto
- Explicación de todos los sistemas
- Estructura del código
- Guías de desarrollo
- Checklist para nuevos desarrolladores

### 2. 📝 `CODIGO_COMENTADO.md` / `CODIGO_COMENTADO.pdf`
- **Código fuente comentado** línea por línea
- Explicación de cada método y clase
- Comentarios detallados de la lógica
- Ejemplos de uso

### 3. 🔧 `convert_to_pdf.py`
- Script para convertir los archivos Markdown a PDF
- Útil si prefieres leer en formato PDF

## 🚀 PRIMEROS PASOS

### 1. Leer la Documentación
**IMPORTANTE:** Antes de tocar cualquier código, lee completamente:
- `DOCUMENTACION_TONTUFOS2.md` (o el PDF)
- `CODIGO_COMENTADO.md` (o el PDF)

### 2. Entender el Proyecto
El mod tiene estos componentes principales:
- **Sistema de Toxicidad:** Maneja efectos progresivos por consumo de "fafa"
- **Charly García:** NPC interactivo con diálogos y misiones
- **Sistema de Invocación:** Pirámides que invocan a Charly
- **Loot Modificado:** Fafa aparece en cofres específicos

### 3. Configurar el Entorno
```bash
# Asegúrate de tener:
- Java 17+
- Gradle
- Un IDE (IntelliJ IDEA recomendado)
- Minecraft 1.20.1
- Fabric Loader
```

## 🛠️ ESTRUCTURA DEL PROYECTO

```
src/main/java/com/tontufos2/
├── Tontufos2.java              # 🎯 CLASE PRINCIPAL
├── items/                      # 📦 Ítems personalizados
│   ├── ModItems.java          # Registro de ítems
│   ├── FafaItem.java          # Lógica del ítem fafa
│   └── ModItemGroups.java     # Grupos de inventario
├── entity/                     # 👤 Entidades
│   ├── ModEntities.java       # Registro de entidades
│   └── custom/
│       └── CharlyGarciaEntity.java  # NPC Charly
├── util/                       # ⚙️ Utilidades
│   ├── ToxicityHandler.java   # Sistema de toxicidad
│   └── CharlySummonerHandler.java  # Invocación
├── dialogues/                  # 💬 Diálogos
│   └── CharlyDialogueHandler.java
├── loot/                       # 💰 Modificadores de loot
├── commands/                   # ⚡ Comandos
└── sound/                      # 🎵 Sonidos
```

## 🎯 SISTEMAS PRINCIPALES

### 1. Sistema de Toxicidad (`ToxicityHandler.java`)
- **Función:** Controla efectos por consumo de fafa
- **Efectos:** Velocidad, veneno, mensajes
- **Decay:** Baja automáticamente cada 5 segundos

### 2. Entidad Charly García (`CharlyGarciaEntity.java`)
- **Función:** NPC interactivo
- **Misión:** Necesita un teclado (de Even More Instruments)
- **Estados:** Sin piano / Con piano
- **Persistencia:** Recuerda si ya recibió el piano

### 3. Invocación de Charly (`CharlySummonerHandler.java`)
- **Función:** Detecta pirámides y invoca a Charly
- **Estructura:** Antorcha + 3 capas de bloques fafa
- **Efectos:** Partículas, sonidos, destruye pirámide

## 🔍 ARCHIVOS IMPORTANTES

### Configuración:
- `build.gradle` - Dependencias y configuración
- `gradle.properties` - Versiones
- `fabric.mod.json` - Metadata del mod

### Recursos:
- `src/main/resources/assets/` - Texturas, modelos, sonidos
- `src/main/resources/data/` - Recetas, loot, diálogos

## 🐛 DEBUGGING Y LOGGING

### Logs Importantes:
```java
Tontufos2.LOGGER.info("Mensaje de información");
System.out.println("Debug rápido");
System.err.println("Errores");
```

### Comando de Prueba:
```bash
/piramide_test  # Genera una pirámide de prueba
```

## 📋 CHECKLIST ANTES DE EMPEZAR

- [ ] Leer toda la documentación
- [ ] Entender la estructura del proyecto
- [ ] Familiarizarse con Fabric API
- [ ] Probar el mod en desarrollo
- [ ] Entender el sistema de toxicidad
- [ ] Comprender la lógica de Charly
- [ ] Ver cómo funciona la invocación

## 🎮 PROBAR EL MOD

### Para Probar:
1. **Fafa:** Come fafa y observa los efectos de toxicidad
2. **Charly:** Construye una pirámide y invócalo
3. **Diálogos:** Interactúa con Charly por clic derecho
4. **Misión:** Consigue un teclado y dáselo a Charly
5. **Loot:** Busca fafa en cofres de mazmorras

## 🚨 CONSEJOS IMPORTANTES

### Para Modificar Código:
1. **Hacer backup** antes de cambios grandes
2. **Probar** cada cambio en desarrollo
3. **Verificar** que no rompe funcionalidad existente
4. **Documentar** cambios importantes

### Para Añadir Contenido:
1. **Seguir convenciones** de nombres existentes
2. **Añadir traducciones** en `en_us.json`
3. **Crear recursos visuales** necesarios
4. **Probar** en juego

## 📞 CONTACTO

Si tienes dudas sobre el código:
- **Creadores:** Brianchatran, Maatt_MC
- **Twitch:** https://twitch.tv/brianchatran
- **Propósito:** Mod para TontufosSMP 2

## 🎯 OBJETIVOS DEL MOD

### Funcional:
- Sistema de toxicidad progresivo
- NPC interactivo con misiones
- Loot modificado en cofres
- Invocación mediante estructuras

### Temático:
- Referencias a Charly García
- Humor y referencias culturales
- Experiencia narrativa
- Contenido para streaming

---

## 🚀 ¡A TRABAJAR!

Ahora que tienes toda la información, ¡puedes empezar a trabajar en el proyecto! 

**Recuerda:** Si tienes dudas, revisa primero la documentación. Todo está explicado en detalle.

¡Buena suerte! 🎮✨
