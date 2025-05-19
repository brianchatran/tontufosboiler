package com.tontufos2.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent CHARLY_SONG = registerSoundEvent("charly_song");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier("tontufos2", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        System.out.println("Registrando sonidos de tontufos2");
    }
}
