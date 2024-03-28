package yesman.epicfight.world.capabilities.provider;

import com.google.common.collect.Maps;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.HuskEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.registries.ForgeRegistries;
import noppes.npcs.CustomEntities;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.boss.WitherGhostPatch;
import yesman.epicfight.world.capabilities.entitypatch.boss.WitherPatch;
import yesman.epicfight.world.capabilities.entitypatch.boss.enderdragon.EnderDragonPatch;
import tkk.epic.patch.TkkCustomNpcPatch;
import yesman.epicfight.world.capabilities.entitypatch.mob.*;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.EpicFightEntities;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ProviderEntity implements ICapabilityProvider, NonNullSupplier<EntityPatch<?>> {
	private static final Map<EntityType<?>, Function<Entity, Supplier<EntityPatch<?>>>> CAPABILITIES = Maps.newHashMap();
	private static final Map<EntityType<?>, Function<Entity, Supplier<EntityPatch<?>>>> CUSTOM_CAPABILITIES = Maps.newHashMap();
	
	public static void registerEntityPatches() {
		Map<EntityType<?>, Function<Entity, Supplier<EntityPatch<?>>>> registry = Maps.newHashMap();
		registry.put(EntityType.PLAYER, (entityIn) -> ServerPlayerPatch::new);;
		registry.put(CustomEntities.entityCustomNpc, (entityIn) -> TkkCustomNpcPatch::new);
		registry.put(EntityType.ZOMBIE, (entityIn) -> ZombiePatch<ZombieEntity>::new);
		registry.put(EntityType.CREEPER, (entityIn) -> CreeperPatch::new);
		registry.put(EntityType.ENDERMAN, (entityIn) -> EndermanPatch::new);
		registry.put(EntityType.SKELETON, (entityIn) -> SkeletonPatch<SkeletonEntity>::new);
		registry.put(EntityType.WITHER_SKELETON, (entityIn) -> WitherSkeletonPatch::new);
		registry.put(EntityType.STRAY, (entityIn) -> StrayPatch::new);
		registry.put(EntityType.ZOMBIFIED_PIGLIN, (entityIn) -> ZombifiedPiglinPatch::new);
		registry.put(EntityType.ZOMBIE_VILLAGER, (entityIn) -> ZombieVillagerPatch::new);
		registry.put(EntityType.HUSK, (entityIn) -> ZombiePatch<HuskEntity>::new);
		registry.put(EntityType.SPIDER, (entityIn) -> SpiderPatch::new);
		registry.put(EntityType.CAVE_SPIDER, (entityIn) -> CaveSpiderPatch::new);
		registry.put(EntityType.IRON_GOLEM, (entityIn) -> IronGolemPatch::new);
		registry.put(EntityType.VINDICATOR, (entityIn) -> VindicatorPatch::new);
		registry.put(EntityType.EVOKER, (entityIn) -> EvokerPatch::new);
		registry.put(EntityType.WITCH, (entityIn) -> WitchPatch::new);
		registry.put(EntityType.DROWNED, (entityIn) -> DrownedPatch::new);
		registry.put(EntityType.PILLAGER, (entityIn) -> PillagerPatch::new);
		registry.put(EntityType.RAVAGER, (entityIn) -> RavagerPatch::new);
		registry.put(EntityType.VEX, (entityIn) -> VexPatch::new);
		registry.put(EntityType.PIGLIN, (entityIn) -> PiglinPatch::new);
		registry.put(EntityType.PIGLIN_BRUTE, (entityIn) -> PiglinBrutePatch::new);
		registry.put(EntityType.HOGLIN, (entityIn) -> HoglinPatch::new);
		registry.put(EntityType.ZOGLIN, (entityIn) -> ZoglinPatch::new);
		registry.put(EntityType.ENDER_DRAGON, (entityIn) -> {
			if (entityIn instanceof EnderDragonEntity) {
				return EnderDragonPatch::new;
			}
			return () -> null;
		});
		registry.put(EntityType.WITHER, (entityIn) -> WitherPatch::new);
		registry.put(EpicFightEntities.WITHER_SKELETON_MINION.get(), (entityIn) -> WitherSkeletonPatch::new);
		registry.put(EpicFightEntities.WITHER_GHOST_CLONE.get(), (entityIn) -> WitherGhostPatch::new);
		
		EntityPatchRegistryEvent entitypatchRegistryEvent = new EntityPatchRegistryEvent(registry);
		ModLoader.get().postEvent(entitypatchRegistryEvent);
		
		registry.forEach(CAPABILITIES::put);
	}
	
	public static void registerEntityPatchesClient() {
		CAPABILITIES.put(EntityType.PLAYER, (entityIn) -> {
			if (entityIn instanceof ClientPlayerEntity) {
				return LocalPlayerPatch::new;
			} else if (entityIn instanceof RemoteClientPlayerEntity) {
				return AbstractClientPlayerPatch<RemoteClientPlayerEntity>::new;
			} else if (entityIn instanceof ServerPlayerEntity) {
				return ServerPlayerPatch::new;
			} else {
				return () -> null;
			}
		});
	}
	
	public static void clear() {
		CUSTOM_CAPABILITIES.clear();
	}
	
	public static void putCustomEntityPatch(EntityType<?> entityType, Function<Entity, Supplier<EntityPatch<?>>> entitypatchProvider) {
		CUSTOM_CAPABILITIES.put(entityType, entitypatchProvider);
	}
	
	public static Function<Entity, Supplier<EntityPatch<?>>> get(String registryName) {
		ResourceLocation rl = new ResourceLocation(registryName);
		EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(rl);
		return CAPABILITIES.get(entityType);
	}
	
	private EntityPatch<?> capability;
	private LazyOptional<EntityPatch<?>> optional = LazyOptional.of(this);
	
	public ProviderEntity(Entity entity) {
		//Entity temp=!(entity instanceof EntityCustomNpc)?entity:(((EntityCustomNpc)entity).modelData.getEntity((EntityCustomNpc) entity)==null)?entity:((EntityCustomNpc)entity).modelData.getEntity((EntityCustomNpc) entity);
		/*
		Entity temp;
		if(entity instanceof EntityCustomNpc) {
			EntityCustomNpc customNpc = (EntityCustomNpc)entity;
			if(customNpc.modelData != null && customNpc.modelData.getEntity(customNpc) != null) {
				temp = customNpc.modelData.getEntity(customNpc);
			} else {
				temp = entity;
			}
		} else {
			temp = entity;
		}
		*/
		Function<Entity, Supplier<EntityPatch<?>>> provider = CUSTOM_CAPABILITIES.getOrDefault(entity.getType(), CAPABILITIES.get(entity.getType()));
		
		if (provider != null) {
			this.capability = provider.apply(entity).get();
		}
	}
	
	public boolean hasCapability() {
		return capability != null;
	}
	
	@Override
	public EntityPatch<?> get() {
		return this.capability;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return cap == EpicFightCapabilities.CAPABILITY_ENTITY ? this.optional.cast() :  LazyOptional.empty();
	}
}