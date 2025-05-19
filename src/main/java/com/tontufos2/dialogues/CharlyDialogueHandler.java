package com.tontufos2.dialogues; // Paquete donde se define el manejador de diálogos de Charly

import com.tontufos2.Tontufos2;
import com.tontufos2.entity.custom.CharlyGarciaEntity; // Importa la clase de entidad personalizada CharlyGarciaEntity
import com.mojang.brigadier.exceptions.CommandSyntaxException; // Importa la excepción para errores de comandos (diálogos)
import net.minecraft.server.network.ServerPlayerEntity; // Clase del jugador del lado servidor
import net.minecraft.util.Identifier; // Clase para manejar identificadores de recursos únicos
import org.ladysnake.blabber.impl.common.PlayerDialogueTracker; // API interna de Blabber para manejar diálogos de jugadores

/**
 * Handler centralizado para lanzar diálogos de Charly García con Blabber.
 */
public class CharlyDialogueHandler {

    /**
     * Inicia un diálogo de Charly con el jugador.
     * @param player Jugador que inicia el diálogo.
     * @param charly Instancia de la entidad CharlyGarciaEntity.
     * @param dialogueId Identificador único del diálogo a iniciar.
     */
    public static void startCharlyDialogue(ServerPlayerEntity player, CharlyGarciaEntity charly, Identifier dialogueId) {
        try {
            // Inicia el diálogo usando el identificador pasado como argumento.
            PlayerDialogueTracker.get(player).startDialogue(dialogueId, charly);

            Tontufos2.LOGGER.info("Piano  dialogo.");
            System.out.println("Iniciando diálogo '" + dialogueId + "' con Charly para " + player.getName().getString());
        } catch (CommandSyntaxException e) {
            // Manejo y logging de posibles errores al iniciar diálogo.
            System.err.println("Error al iniciar diálogo con Charly: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
