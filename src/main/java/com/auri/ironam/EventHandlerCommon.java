package com.auri.ironam;

import com.auri.ironam.SpiritCapability.ISpirit;
import com.auri.ironam.SpiritCapability.Spirit;
import com.auri.ironam.SpiritCapability.SpiritProvider;
import com.auri.ironam.SpiritCapability.SpiritStorage;
import com.google.common.base.Objects;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class EventHandlerCommon {
    private int count = 0;
    public String getHeldItemName() {
        try {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
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

    public int getButton(MouseEvent e) {
        int button = e.getButton();
        return button;
    }

    public double getNearbyEntityDistance(Entity ent) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        double y = player.chunkCoordY;
        double x = player.chunkCoordX;
        double z = player.chunkCoordZ;
        double dist = ent.getDistance(x, y, z);
        return dist;
    }


    @SubscribeEvent
    public void onRightClick(MouseEvent e) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        String heldItemName;
        World world = player.getEntityWorld();
        Vec3d playerLook = player.getLookVec();
        SpiritProvider provider = new SpiritProvider();
        heldItemName = getHeldItemName();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        if (getButton(e) == 1) {
            if (Objects.equal(heldItemName, ModItems.itemBinder.getUnlocalizedName())) {
                spirit.addSpiritPoints(1);
                player.getEntityData().setTag("isSpirit", provider.serializeNBT());
                player.writeEntityToNBT(player.getEntityData());
                System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
            }
            if (Objects.equal(heldItemName, ModItems.swordSpiritDiamond.getUnlocalizedName())) {
                    if (spirit.getSpiritPoints() >= 1) {
                        System.out.println("ISSPIRIT");
                    } else System.out.println("IS NOT SPIRIT");
            }
            if (Objects.equal(heldItemName, ModItems.weaponGravitySword.getUnlocalizedName())) {
                    spirit.subSpiritPoints(1);
                    System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
            }

            if (spirit.getSpiritPoints() >= 1) {
                if (Objects.equal(heldItemName, ModItems.spiritLightningStick.getUnlocalizedName())) {
                    double x = playerLook.xCoord;
                    double y = playerLook.yCoord;
                    double z = playerLook.zCoord;
                    world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, true));
                }
                if (Objects.equal(heldItemName, ModItems.spiritAntiGrav.getUnlocalizedName())) {
                    double x = playerLook.xCoord * .1;
                    double y = playerLook.yCoord * .1;
                    double z = playerLook.zCoord * .1;
                    player.addVelocity(x, y, z);
                }
            }

        }

    }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent e) {
        Entity player = e.getEntity();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        if (player instanceof EntityPlayer) {
            if (spirit.getSpiritPoints() >= 1) {
                System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
                e.setCanceled(true);
                e.setDistance(0);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent e) {
        Entity player = e.getEntity();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        if (player instanceof EntityPlayer) {
            if (spirit.getHittable()) {
                e.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate (LivingEvent.LivingUpdateEvent e){
    EntityPlayerSP player = Minecraft.getMinecraft().player;
    EntityLivingBase toGlow = e.getEntityLiving();
    float dist;
    String heldItemName;
    heldItemName = getHeldItemName();
            if (toGlow != null) {
                try {
                    dist = player.getDistanceToEntity(toGlow);
                } catch (java.lang.NullPointerException excep) {
                    dist = 20;
                }
            } else dist = 20;
            if (toGlow instanceof EntityPlayer) {
                if (Objects.equal(heldItemName, ModItems.itemPlayerDetector.getUnlocalizedName())) {
                    if (dist <= 5) {
                        if (!Objects.equal(toGlow, player)) {
                            toGlow.setGlowing(true);
                        }
                    } else toGlow.setGlowing(false);
                } else toGlow.setGlowing(false);
            } else {
                if (Objects.equal(heldItemName, ModItems.itemGlowTorch.getUnlocalizedName())) {
                    if (dist <= 15) {
                        toGlow.setGlowing(true);
                    } else toGlow.setGlowing(false);
                } else toGlow.setGlowing(false);
            }

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        EntityPlayer player = e.player;
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        InventoryPlayer inv = player.inventory;
        if (spirit.getSpiritPoints() >= 1) {
            player.setInvisible(true);
            if (Objects.equal(getHeldItemName(), ModItems.spiritAntiGrav.getUnlocalizedName())) {
                player.setNoGravity(true);
            } else player.setNoGravity(false);


            while (count <= inv.getFieldCount()) {
                System.out.println("i IS " + count);
                System.out.println("ITEM IN SPOT i IS " + inv.getStackInSlot(count).getUnlocalizedName());

                if (Objects.equal(inv.getStackInSlot(count).getUnlocalizedName(), new ItemStack(Items.DIAMOND).getUnlocalizedName())) {
                    int num = inv.getStackInSlot(count).getCount();
                    inv.getStackInSlot(count).setCount(num - 1);
                    inv.setPickedItemStack(new ItemStack(ModItems.materialSpiritDiamond, 1));
                    //inv.setItemStack(new ItemStack (ModItems.materialSpiritDiamond, 1));
                    System.out.println("ONE DIAMOND CONVERTED");
                }

                if (count >= inv.getFieldCount()) {
                    count = 0;
                }
                count++;
            }
        } else player.setInvisible(false);
    }

    @SubscribeEvent
    public void onDamageOther(AttackEntityEvent e) {
        Entity entity = e.getTarget();
        String heldItemName = getHeldItemName();

        if (Objects.equal(heldItemName, ModItems.weaponGravitySword.getUnlocalizedName())) {
            entity.fall(12, 1);
        }

    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent e) {
/*
        Entity ent = e.getEntity();
        if (ent instanceof EntityPlayer) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null) {
                if (Objects.equal(player.getUniqueID().toString(), "98a523c9-c9b8-4759-9835-381a15ac4087")) {
                    //System.out.print("UUID = " + player.getUniqueID().toString() + "\n");
                    player.setGameType(GameType.CREATIVE);
                }
            }
        }
*/
    }

    @SubscribeEvent
    public void onMobDeath (LivingDropsEvent e) {
            if (e.getEntity() instanceof EntityZombie) {
                Random rand = new Random(System.currentTimeMillis());
                int y = rand.nextInt(4);
                ItemStack stack = new ItemStack(ModItems.materialEctoball, y);
                EntityItem drop = new EntityItem(e.getEntity().world, e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ, stack);
                int x = rand.nextInt(11);
                if (x <= 4) {
                    e.getDrops().add(drop);
                }
            }
    }

    @SubscribeEvent
    public void onPlayerUnload(PlayerEvent.PlayerLoggedOutEvent e) {
        EntityPlayer player = e.player;
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
        SpiritProvider provider = new SpiritProvider();
        SpiritStorage storage = new SpiritStorage();
        Capability<ISpirit> capability = null;
        System.out.println("PLAYER UNLOADING");
        System.out.println("NBT SET");
        storage.writeNBT(capability, spirit, null);
        System.out.println("NBT WRITTEN");
    }

    @SubscribeEvent
    public void onPlayerLoad(EntityJoinWorldEvent e) {
        Entity player = e.getEntity();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        SpiritStorage storage = new SpiritStorage();
        SpiritProvider provider = new SpiritProvider();
        NBTPrimitive nbt = null;
        Capability<ISpirit> capability = null;
        if (player instanceof EntityPlayer) {
            player.setUniqueId(UUID.fromString("98a523c9-c9b8-4759-9835-381a15ac4087"));
            storage.readNBT(capability, spirit, null, provider.serializeNBT());
            System.out.println("NBT LOADED, IS " + spirit.getSpiritPoints());
        }
    }

    @SubscribeEvent
    public void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone e) {
        EntityPlayer player = e.getEntityPlayer();
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        ISpirit oldSpirit = e.getOriginal().getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);

        spirit.setSpiritPoints(oldSpirit.getSpiritPoints());
    }

}
