package mrthomas20121.tinkers_reforged.Tools;

import mrthomas20121.tinkers_reforged.Module.ModuleTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.SwordCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.List;

public class SwordKhopesh extends SwordCore {

    public SwordKhopesh() {
        super(PartMaterialType.handle(TinkerTools.toolRod),
                PartMaterialType.head(ModuleTools.CurvedBlade),
                PartMaterialType.extra(TinkerTools.crossGuard));
        this.addCategory(Category.WEAPON);
    }
    @Override
    public ToolNBT buildTagData(List<Material> materials) {
        ToolNBT data = buildDefaultTag(materials);

        data.attack += 1f;
        data.durability *= 1f;
        data.attackSpeedMultiplier = 1f;

        return data;
    }
    @Override
    public float damagePotential() { return 1f; }

    @Override
    public double attackSpeed() {
        return 1.4d;
    }

    @Override
    public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
        // deal damage first
        boolean hit = super.dealDamage(stack, player, entity, damage);
        // and then sweep
        if(hit && !ToolHelper.isBroken(stack)) {
            // sweep code from EntityPlayer#attackTargetEntityWithCurrentItem()
            // basically: no crit, no sprinting and has to stand on the ground for sweep. Also has to move regularly slowly
            double d0 = (double) (player.distanceWalkedModified - player.prevDistanceWalkedModified);
            boolean flag = true;
            if(player instanceof EntityPlayer) {
                flag = ((EntityPlayer) player).getCooledAttackStrength(0.5F) > 0.9f;
            }
            boolean flag2 = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding();
            if(flag && !player.isSprinting() && !flag2 && player.onGround && d0 < (double) player.getAIMoveSpeed()) {
                for(EntityLivingBase entitylivingbase : player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox().expand(1.0D, 0.25D, 1.0D))) {
                    if(entitylivingbase != player && entitylivingbase != entity && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < 9.0D) {
                        entitylivingbase.knockBack(player, 0.4F, (double) MathHelper.sin(player.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
                        super.dealDamage(stack, player, entitylivingbase, 1f);
                    }
                }

                player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                if(player instanceof EntityPlayer) {
                    ((EntityPlayer) player).spawnSweepParticles();
                }
            }
        }

        return hit;
    }
}
