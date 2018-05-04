package com.massivecraft.factions.util;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class Heads {
	public static ItemStack AMARELO;
	public static ItemStack ROXO;
	public static ItemStack LARANJA;
	public static ItemStack AZURE;
	public static ItemStack BRANCO;
	public static ItemStack VERMELHO;
	
	static {
		VERMELHO = getSkull("http://textures.minecraft.net/texture/5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b");
		BRANCO = getSkull("http://textures.minecraft.net/texture/366a5c98928fa5d4b5d5b8efb490155b4dda3956bcaa9371177814532cfc");
		LARANJA = getSkull("http://textures.minecraft.net/texture/e79add3e5936a382a8f7fdc37fd6fa96653d5104ebcadb0d4f7e9d4a6efc454");
		AMARELO = getSkull("http://textures.minecraft.net/texture/14c4141c1edf3f7e41236bd658c5bc7b5aa7abf7e2a852b647258818acd70d8");
		ROXO = getSkull("http://textures.minecraft.net/texture/593f67f9f730d42fda8de69565ea55892c5f85d9cae6dd6fcba5d26f1e7238d1");
		AZURE = getSkull("http://textures.minecraft.net/texture/bfaf7aab1e177ad38e51bfc19ab662149c31953a569a40caa81f7a4932069");
	}
	
	public static ItemStack getSkull(String url) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if (url == null || url.isEmpty())
            return skull;
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
