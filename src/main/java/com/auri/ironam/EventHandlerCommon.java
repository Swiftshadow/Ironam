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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
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
import net.minecraftforge.items.*;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class EventHandlerCommon {
    private int count = 0;
    private InventoryPlayer inv;
    PlayerInvWrapper invWrapper;
    CombinedInvWrapper comWrapper;
    ItemHandlerHelper itemHelper;

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

    public void convertItem(Item toTake, Item toGive) {
        if (Objects.equal(inv.getStackInSlot(count).getUnlocalizedName(), new ItemStack(toTake).getUnlocalizedName())) {
            try {
                if (Objects.equal(comWrapper.getStackInSlot(count).getUnlocalizedName(), new ItemStack(toTake).getUnlocalizedName())) {
                    int num = inv.getStackInSlot(count).getCount();
                    comWrapper.setStackInSlot(count, new ItemStack(toTake, 0));
                    comWrapper.setStackInSlot(count, new ItemStack(toGive, num));
                }
            } catch (java.lang.NullPointerException excep) {
                System.out.println("INV IS NULL");
            }
        }
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
                if (spirit.getBound() == false) {
                    spirit.setBoundX(player.posX);
                    spirit.setBoundZ(player.posZ);
                    spirit.setBoundY(player.posY);
                    spirit.setBound(true);
                }
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
                    if (spirit.getSpiritPoints() == 0) {
                        spirit.setBound(false);
                    }
                    System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
            }

            if (spirit.getSpiritPoints() >= 1) {

                if (Objects.equal(heldItemName, ModItems.spiritLightningStick.getUnlocalizedName())) {
                    double x = playerLook.xCoord;
                    double y = playerLook.yCoord;
                    double z = playerLook.zCoord;
                    world.addWeatherEffect(new EntityLightningBolt(world, x, y, z, false));
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
        Entity ent = e.getEntity();
        EntityPlayer player = Minecraft.getMinecraft().player;
        ISpirit spirit = null;
        if (player != null) {
            spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        }
        if (spirit != null) {
            if (ent instanceof EntityPlayer) {
                if (spirit.getSpiritPoints() >= 1) {
                    if (spirit.getHittable()) {
                        System.out.println("SPIRIT POINTS ARE " + spirit.getSpiritPoints());
                        e.setCanceled(true);
                        e.setDistance(0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent e) {
        Entity ent = e.getEntity();
        EntityPlayer player = Minecraft.getMinecraft().player;
        ISpirit spirit = null;
        if (player != null) {
            spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        }
        if (spirit != null) {
            if (ent instanceof EntityPlayer) {
                if (spirit.getSpiritPoints() >= 1) {
                    if (spirit.getHittable()) {
                        e.setCanceled(true);
                    }
                }
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
        World world = player.world;
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        this.inv = player.inventory;
        if (invWrapper == null) {
            invWrapper = new PlayerInvWrapper(inv);
            comWrapper = new CombinedInvWrapper(invWrapper);
        }

        if (itemHelper == null) {
            itemHelper = new ItemHandlerHelper();
        }

        if (spirit.getSpiritPoints() >= 1) {
            player.setInvisible(true);

            if (player.posX > (spirit.getBoundX() + 30)) {
                player.setPosition(player.posX - 1, player.posY, player.posZ);
            }

            if (player.posZ > (spirit.getBoundZ() + 30)) {
                player.setPosition(player.posX , player.posY, player.posZ - 1);
            }

            if (player.posX < (spirit.getBoundX() - 30)) {
                player.setPosition(player.posX + 1, player.posY, player.posZ);
            }

            if (player.posZ < (spirit.getBoundZ() - 30)) {
                player.setPosition(player.posX , player.posY, player.posZ + 1);
            }
            if (Objects.equal(getHeldItemName(), ModItems.spiritAntiGrav.getUnlocalizedName())) {
                player.setNoGravity(true);
            } else player.setNoGravity(false);

            convertItem(Items.DIAMOND, ModItems.materialSpiritDiamond);
            convertItem(Items.IRON_INGOT, ModItems.materialSpiritIron);
            convertItem(Items.GOLD_INGOT, ModItems.materialSpiritGold);
            convertItem(ModItems.materialEctoplasm, ModItems.materialPureEctoplasm);

            if (inv.inventoryChanged) {
                inv.markDirty();
            }

            count++;

            if (count >= inv.getSizeInventory()) {
                count = 0;
            }
        } else player.setInvisible(false);

        if (Objects.equal(getHeldItemName(), ModItems.itemRedstoneStick.getUnlocalizedName())) {
           // itemHelper.calcRedstoneFromInventory();
        }

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
        EntityPlayer eplayer = e.getEntityPlayer();
        EntityPlayer player = Minecraft.getMinecraft().player;
        ISpirit spirit = player.getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);
        ISpirit oldSpirit = e.getOriginal().getCapability(SpiritProvider.SPIRIT_CAPABILITY, null);

        spirit.setSpiritPoints(oldSpirit.getSpiritPoints());
    }

}
