package com.auri.ironam;

import com.auri.ironam.SpiritCapability.ISpirit;
import com.auri.ironam.SpiritCapability.Spirit;
import com.auri.ironam.SpiritCapability.SpiritProvider;
import com.auri.ironam.SpiritCapability.SpiritStorage;
import com.google.common.base.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameType;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Random;
import java.util.Set;

/**
 * Created by 1800855 on 10/17/16.
 */
public class EventHandlerCommon {
    public String getHeldItemName() {
        try {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            String heldItemName;
            ItemStack heldItemMH;
            ItemStack heldItemOH;
            heldItemMH = player.getHeldItemMainhand() != null ? player.getHeldItemMainhand() : null;
            heldItemOH = player.getHeldItemOffhand() != null ? player.getHeldItemOffhand() : null;
            if (heldItemMH != null) {
                heldItemName = heldItemMH.getUnlocalizedName();
            }
            else if (heldItemOH != null) {
                heldItemName = heldItemOH.getUnlocalizedName();
            } else heldItemName = "NOTHING";

            return heldItemName;
        } catch (java.lang.NullPointerException excep) {
            return "NOTHING";
        }
    }

    public Boolean getTag(String input) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        Set<String> tags = player.getTags();
        boolean haveTag = tags.contains(input);
        return haveTag;
    }

    public int getButton(MouseEvent e) {
        int button = e.getButton();
        return button;
    }

    public double getNearbyEntityDistance(LivingEvent.LivingUpdateEvent e) {
        Entity ent = e.getEntityLiving();
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        double y = player.chunkCoordY;
        double x = player.chunkCoordX;
        double z = player.chunkCoordZ;
        double dist = ent.getDistance(x, y, z);
        return dist;
    }


    @SubscribeEvent
    public void onRightClick(MouseEvent e) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        String heldItemName;
        heldItemName = getHeldItemName();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        if (getButton(e) == 1) {
            if (Objects.equal(heldItemName, ModItems.itemBinder.getUnlocalizedName())) {
                spirit.setSpiritPoints(1);
                System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
            }
            if (Objects.equal(heldItemName, ModItems.swordSpiritDiamond.getUnlocalizedName())) {
                    if (spirit.getSpiritPoints() == 1) {
                        System.out.println("ISSPIRIT");
                    }
            }
            if (Objects.equal(heldItemName, ModItems.itemBinder.getUnlocalizedName())) {
                    spirit.setSpiritPoints(0);
            }

        }

    }

    @SubscribeEvent
    public void onLivingUpdate (LivingEvent.LivingUpdateEvent e){
    EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
    EntityLivingBase toGlow = e.getEntityLiving();
    float dist;
    String heldItemName;
    heldItemName = getHeldItemName();
    if (toGlow != null) {
        try {
            dist = player.getDistanceToEntity(toGlow);
        } catch (java.lang.NullPointerException excep) {
            dist = 0;
        }
    } else dist = 11;
    if (Objects.equal(heldItemName, ModItems.itemGlowTorch.getUnlocalizedName())) {
        if (dist <= 10) {
            toGlow.setGlowing(true);
        } else toGlow.setGlowing(false);
    } else toGlow.setGlowing(false);

    }


    @SubscribeEvent
    public void onDamageOther(AttackEntityEvent e) {
        Entity entity = e.getTarget();
        String heldItemName = getHeldItemName();

        if (Objects.equal(heldItemName, ModItems.itemGlowTorch.getUnlocalizedName())) {
            entity.setGlowing(true);
        }

        if (Objects.equal(heldItemName, ModItems.weaponGravitySword.getUnlocalizedName())) {
            entity.fall(12, 1);
        }

    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent e) {
        Entity ent = e.getEntity();
        if (ent instanceof EntityPlayer) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player != null) {
                if (Objects.equal(player.getUniqueID().toString(), "97a523c9-c9b8-4759-9835-381a15ac4087")) {
                    System.out.print("UUID = " + player.getUniqueID().toString() + "\n");
                    player.setGameType(GameType.CREATIVE);
                }
            }
        }
    }

    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent e) {
            if (e.getEntity() instanceof EntityZombie) {
                Random rand = new Random(System.currentTimeMillis());
                int y = rand.nextInt(4);
                ItemStack stack = new ItemStack(ModItems.materialEctoball, y);
                EntityItem drop = new EntityItem(e.getEntity().worldObj, e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ, stack);
                int x = rand.nextInt(11);
                if (x <= 4) {
                    e.getDrops().add(drop);
                }
            }
    }

    @SubscribeEvent
    public void onPlayerUnload(PlayerEvent.PlayerLoggedOutEvent e) {
        EntityPlayer player = e.player;
        Spirit spirit = new Spirit();
        ISpirit iSpirit = spirit.getSpirit();
        SpiritProvider provider = new SpiritProvider();
        NBTTagCompound tag =  spirit.serializeNBT();
        provider.serializeNBT();
        System.out.println("NBT SAVED, IS " + spirit.getSpiritPoints());
    }

    @SubscribeEvent
    public void onPlayerLoad(PlayerEvent.PlayerLoggedInEvent e) {
        EntityPlayer player = e.player;
        Spirit spirit = new Spirit();
        ISpirit iSpirit = spirit.getSpirit();
        SpiritProvider provider = new SpiritProvider();
        NBTTagCompound tag = player.getEntityData();
        provider.deserializeNBT(tag);
        spirit.deserializeNBT(tag);
        System.out.println("NBT LOADED, IS " + spirit.getSpiritPoints());
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
        EntityPlayer player = e.getEntityPlayer();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        ISpirit oldSpirit = e.getOriginal().getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);

        spirit.setSpiritPoints(oldSpirit.getSpiritPoints());
    }

}
