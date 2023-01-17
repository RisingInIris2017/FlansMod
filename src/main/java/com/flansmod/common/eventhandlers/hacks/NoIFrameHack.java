package com.flansmod.common.eventhandlers.hacks;

import com.flansmod.common.FlansMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.Class;
import java.lang.reflect.Field;

public class NoIFrameHack
{
	public NoIFrameHack()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	@SubscribeEvent
	public void removeIFrame(LivingAttackEvent event) {
		if (!(event.getEntityLiving().world.isRemote)) {
			EntityLivingBase entity = event.getEntityLiving();
			try {
				Class<?> entityClass = entity.getClass();
				Field lastDamage = entityClass.getDeclaredField("lastDamage");
				lastDamage.setAccessible(true);
				lastDamage.set(entity, 0.0f);
			} catch (Exception e) {
				FlansMod.log.error("Failed to set lastDamage to 0.\n" + e.getMessage(), e);
			}
		}
	}
}
