# 📚 DOCUMENTACIÓN COMPLETA - TONTUFOS2 MOD

## 🎯 INFORMACIÓN GENERAL

**Nombre del Proyecto:** Tontufos2  
**Versión:** 1.0.0  
**Minecraft:** 1.20.1  
**Mod Loader:** Fabric  
**Java:** 17+  
**Creadores:** Brianchatran, Maatt_MC  
**Propósito:** Mod para la serie de Twitch TontufosSMP 2

---

## 🏗️ ESTRUCTURA DEL PROYECTO

```
tontufos2-template-1.20.1/
├── build.gradle                    # Configuración de Gradle
├── gradle.properties              # Versiones y propiedades
├── fabric.mod.json               # Metadata del mod
├── src/
│   ├── main/java/com/tontufos2/
│   │   ├── Tontufos2.java        # Clase principal
│   │   ├── items/                # Ítems personalizados
│   │   ├── blocks/               # Bloques personalizados
│   │   ├── entity/               # Entidades personalizadas
│   │   ├── commands/             # Comandos
│   │   ├── blabber/              # Sistema de diálogos
│   │   ├── dialogues/            # Manejo de diálogos
│   │   ├── loot/                 # Modificadores de loot
│   │   ├── sound/                # Sonidos
│   │   ├── util/                 # Utilidades
│   │   └── mixin/                # Modificaciones del código base
│   └── main/resources/
│       ├── assets/tontufos2/     # Texturas, modelos, sonidos
│       └── data/tontufos2/       # Datos del mod (recetas, loot, etc.)
└── libs/                         # Dependencias externas
```

---

## 🔧 CONFIGURACIÓN TÉCNICA

### Dependencias Principales:
- **Fabric API:** Framework principal
- **Blabber:** Sistema de diálogos para NPCs
- **Even More Instruments:** Instrumentos musicales (incluye teclado)
- **Cardinal Components:** Sistema de componentes para entidades
- **Genshinstrument:** Más instrumentos musicales

### Versiones:
- Minecraft: 1.20.1
- Fabric Loader: 0.16.13
- Fabric API: 0.92.1+1.20.1
- Blabber: 1.6.1-mc1.20.1
- Cardinal Components: 5.2.3

---

## 📦 CONTENIDO DEL MOD

### 1. ÍTEMS PERSONALIZADOS

#### Fafa (Ítem Principal)
- **Tipo:** Comida especial
- **Efectos:** 
  - Aumenta toxicidad del jugador
  - Da velocidad temporal (10 segundos)
  - Hace daño si toxicidad ≥ 2
- **Características:**
  - Se puede comer con hambre llena (`alwaysEdible`)
  - Sistema de toxicidad progresivo
  - Daño personalizado "fafa_overdose"

#### Charly García Spawn Egg
- **Propósito:** Invocar a Charly García
- **Uso:** Clic derecho en el suelo

#### Charly Music Disc
- **Contenido:** "Nos Siguen Pegando Abajo" - Charly García
- **Duración:** 2400 ticks (2 minutos)
- **Rareza:** RARE

### 2. BLOQUES PERSONALIZADOS

#### Fafa Block
- **Receta:** 9 fafas en forma de bloque
- **Textura:** Basada en terracotta
- **Uso:** Decorativo y para invocar a Charly

### 3. ENTIDADES PERSONALIZADAS

#### Charly García Entity
- **Tipo:** NPC interactivo
- **Vida:** 20 puntos
- **Velocidad:** 0.2
- **Interacción:** Clic derecho para diálogos
- **Misión:** Necesita un teclado (de Even More Instruments)
- **Estados:**
  - Sin piano: Diálogo introductorio
  - Con piano: Diálogo secundario
- **Persistencia:** Recuerda si ya recibió el piano

---

## ⚙️ SISTEMAS PRINCIPALES

### 1. SISTEMA DE TOXICIDAD

**Archivo:** `ToxicityHandler.java`

**Funcionalidad:**
- Controla la toxicidad de cada jugador
- Se ejecuta cada segundo (20 ticks)
- Efectos progresivos según nivel

**Niveles de Toxicidad:**
- **1:** Solo velocidad boost
- **2:** Velocidad + veneno + mensaje
- **4:** Más velocidad + más veneno + mensaje
- **6:** Máxima velocidad + máximo veneno + mensaje crítico

**Efectos:**
- **Velocidad:** `0.2 * √(toxicidad)`
- **Veneno:** Duración 100 ticks, potencia = toxicidad - 2
- **Decay:** Baja 1 punto cada 5 segundos

### 2. SISTEMA DE INVOCACIÓN DE CHARLY

**Archivo:** `CharlySummonerHandler.java`

**Estructura Requerida:**
```
   T   (Antorcha)
  FFF  (3x3 fafa blocks)
 FFFFF (5x5 fafa blocks)
FFFFFFF (7x7 fafa blocks)
```

**Proceso:**
1. Detecta estructura cerca del jugador
2. Verifica que no se haya usado antes
3. Spawnea Charly con efectos visuales
4. Destruye la pirámide
5. Marca como usada

### 3. SISTEMA DE DIÁLOGOS

**Archivos:**
- `CharlyDialogueHandler.java`
- `data/tontufos2/blabber/dialogues/`

**Diálogos Disponibles:**
- `charly_intro`: Diálogo inicial
- `charly_second`: Diálogo después del piano

**Características:**
- Sistema ramificado con múltiples opciones
- Condiciones para mostrar opciones
- Acciones (comandos) integradas
- Ilustraciones con entidades

### 4. SISTEMA DE LOOT

**Archivos:**
- `AddItemLootModifier.java`
- `AddChestLootModifier.java`
- `AddMobLootModifier.java`

**Cofres Modificados:**
- Mazmorras simples
- Portales arruinados
- Armero de aldeanos
- Esqueletos (mobs)

**Loot Añadido:**
- Fafa (con probabilidad configurable)

---

## 🎮 COMANDOS

### Piramide Test Command
- **Comando:** `/piramide_test`
- **Función:** Genera una pirámide de prueba para testing
- **Uso:** Útil para probar la invocación de Charly

---

## 📁 ARCHIVOS DE DATOS

### Recetas
- `fafa_block.json`: 9 fafas → 1 bloque de fafa
- `unblock_of_fafa.json`: 1 bloque → 9 fafas
- `charly_music_disc.json`: Receta del disco

### Avances (Advancements)
- `root.json`: Avance raíz del mod
- `give_piano_to_charly.json`: Avance por dar piano a Charly

### Loot Tables
- Modificadores para añadir fafa a cofres
- Condiciones personalizadas para loot

### Diálogos (Blabber)
- `charly_intro.json`: Diálogo inicial completo
- `charly_second.json`: Diálogo secundario

---

## 🎨 RECURSOS VISUALES

### Texturas
- `fafa.png`: Ítem fafa
- `fafa_block.png`: Bloque de fafa
- `charly_garcia.png`: Textura de Charly
- `charly_garcia_spawn_egg.png`: Spawn egg
- `charly_music_disc.png`: Disco de música

### Modelos
- Modelos 3D en formato JSON
- Modelos de bloques e ítems
- Modelos de entidades

### Sonidos
- `charly_song.ogg`: Música de Charly
- `sounds.json`: Configuración de sonidos

---

## 🔄 FLUJO DE EJECUCIÓN

### Al Iniciar el Mod:
1. `Tontufos2.onInitialize()` se ejecuta
2. Se registran todos los sistemas:
   - Ítems y bloques
   - Entidades
   - Comandos
   - Handlers de eventos
   - Modificadores de loot
   - Condiciones personalizadas

### Durante el Juego:
1. **Sistema de Toxicidad:** Se ejecuta cada segundo
2. **Detección de Pirámides:** Se ejecuta cada tick
3. **Interacciones con Charly:** Por clic derecho
4. **Loot Modificado:** Al abrir cofres

### Eventos Principales:
- `ServerTickEvents.END_WORLD_TICK`: Para toxicidad y pirámides
- `ServerPlayerEvents.AFTER_RESPAWN`: Limpiar toxicidad al respawnear
- `LootTableEvents.MODIFY`: Modificar loot de cofres

---

## 🛠️ DESARROLLO Y MANTENIMIENTO

### Para Añadir Nuevos Ítems:
1. Crear clase en `items/`
2. Registrar en `ModItems.java`
3. Añadir a `ModItemGroups.java`
4. Crear texturas y modelos
5. Añadir traducciones

### Para Añadir Nuevas Entidades:
1. Crear clase en `entity/custom/`
2. Registrar en `ModEntities.java`
3. Crear texturas y modelos
4. Añadir spawn egg si es necesario

### Para Añadir Nuevos Diálogos:
1. Crear archivo JSON en `data/tontufos2/blabber/dialogues/`
2. Usar `CharlyDialogueHandler` para iniciarlos
3. Añadir condiciones si es necesario

### Para Modificar Loot:
1. Usar `LootTableEvents.MODIFY`
2. Crear `LootPool` con `ItemEntry`
3. Añadir a `tableBuilder.pool()`

---

## 🐛 DEBUGGING Y LOGGING

### Logs Importantes:
- `Tontufos2.LOGGER.info()`: Para información general
- `System.out.println()`: Para debugging rápido
- `System.err.println()`: Para errores

### Puntos de Debug:
- Toxicidad: Se imprime en consola
- Invocación de Charly: Se registra en logs
- Diálogos: Se imprime información de inicio

---

## 📋 CHECKLIST PARA NUEVOS DESARROLLADORES

### Antes de Empezar:
- [ ] Leer toda esta documentación
- [ ] Entender la estructura del proyecto
- [ ] Familiarizarse con Fabric API
- [ ] Probar el mod en desarrollo

### Para Modificar Código:
- [ ] Hacer backup del archivo
- [ ] Probar cambios en desarrollo
- [ ] Verificar que no rompe funcionalidad existente
- [ ] Documentar cambios

### Para Añadir Contenido:
- [ ] Seguir las convenciones de nombres
- [ ] Añadir traducciones
- [ ] Crear recursos visuales
- [ ] Probar en juego

---

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

### Técnico:
- Código limpio y documentado
- Sistema modular y extensible
- Compatibilidad con otros mods
- Performance optimizado

---

## 📞 CONTACTO Y SOPORTE

**Creadores:** Brianchatran, Maatt_MC  
**Licencia:** CC0-1.0

---

*Esta documentación debe mantenerse actualizada con cada cambio significativo en el código.*
