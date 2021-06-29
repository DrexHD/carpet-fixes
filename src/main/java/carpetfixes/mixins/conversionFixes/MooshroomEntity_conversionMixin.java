package carpetfixes.mixins.conversionFixes;

import carpetfixes.CarpetFixesServer;
import carpetfixes.CarpetFixesSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntity_conversionMixin extends CowEntity {

    protected MooshroomEntity_conversionMixin(EntityType<? extends CowEntity> entityType, World world) { super(entityType, world); }

    //PortalCooldown, DeathLootTable, effects, & tags still need to be implemented!

    @Redirect(method = "sheared", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
            ordinal = 0
    ))
    public boolean ConversionFix(World world, Entity cowEntity) {
        if (CarpetFixesSettings.conversionFix && cowEntity instanceof CowEntity) {
            cowEntity.setFireTicks(this.getFireTicks()); //Fire
            cowEntity.velocityDirty = true;
            cowEntity.setVelocity(this.getVelocity()); //Motion
            cowEntity.setNoGravity(this.hasNoGravity()); //noGravity
            cowEntity.copyPositionAndRotation(this); //Rotation + Position
            cowEntity.setSilent(this.isSilent()); //Silent
        }
        return world.spawnEntity(cowEntity);
    }
}

