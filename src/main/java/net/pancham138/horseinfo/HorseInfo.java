package net.pancham138.horseinfo;

import java.text.DecimalFormat;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class HorseInfo implements ModInitializer, IWailaPlugin, IEntityComponentProvider {

    public static final Logger LOGGER = ModLog.getLogger();

    public static final String MOD_ID = "horseinfo";
    public static final String MOD_NAME = "Hwyla Addon Horse Info";
    
    private static final Identifier ENABLED = new Identifier(MOD_ID, "enabled");
    private static final Identifier IGNORE_MOUNT = new Identifier(MOD_ID, "ignoremount");
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    
    @Override
    public void onInitialize() {
        LOGGER.log(Level.INFO, "Initializing {}", MOD_NAME);
    }
    
    @Override
    public void register(IRegistrar registrar) {
        LOGGER.log(Level.INFO, "Registering ", MOD_NAME);

        registrar.addConfig(ENABLED, true);
        registrar.addConfig(IGNORE_MOUNT, false);

        registrar.addComponent(this, TooltipPosition.BODY, HorseBaseEntity.class);
        registrar.addOverride(this, HorseBaseEntity.class);
    }

    @Override
    public Entity getOverride(IEntityAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(IGNORE_MOUNT) && accessor.getEntity() instanceof HorseBaseEntity horse) {
            final PlayerEntity player = accessor.getPlayer();
            if (player != null && player.hasVehicle() && player.getVehicle() == horse) {
                return IEntityComponentProvider.EMPTY_ENTITY;
            }
        }
        return null;
    }

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (config.getBoolean(ENABLED)) {
            final Entity entity = accessor.getEntity();
            
            if (entity instanceof HorseBaseEntity) {
                final HorseBaseEntity horse = (HorseBaseEntity)entity;

                final double jumpStrength = horse.getJumpStrength();
                final double jumpHeight = -0.1817584952f * Math.pow(jumpStrength, 3) + 3.689713992f * Math.pow(jumpStrength, 2) + 2.128599134f * jumpStrength - 0.343930367f;
                
                Formatting colorJump;
                if (jumpHeight < 2.0f)
                    colorJump = Formatting.DARK_GRAY;
                else if (jumpHeight > 4.0f)
                    colorJump = Formatting.GOLD;
                else
                    colorJump = Formatting.WHITE;
                
                addText(tooltip, "tooltip.horseinfo.jump", FORMAT.format(jumpHeight), colorJump);

                final double speed = horse.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue() * 42.157787584f;
                
                Formatting colorSpeed;
                if (speed < 7.0f)
                    colorSpeed = Formatting.DARK_GRAY;
                else if (speed > 11.0f)
                    colorSpeed = Formatting.GOLD;
                else
                    colorSpeed = Formatting.WHITE;
                    
                addText(tooltip, "tooltip.horseinfo.speed", FORMAT.format(speed), colorSpeed);
            }
        }
    }

    public static void addText(ITooltip tooltip, String key, String value, Formatting color) {
        tooltip.add(getText(key, value, color));
    }

    public static Text getText(String key, String value, Formatting color) {
        Text text = new TranslatableText(key, value);
        text.getStyle().withColor(color);
        return text;
    }

}