package stevesvehicles.common.modules.datas.registries;

import static stevesvehicles.common.items.ComponentTypes.EMPTY_DISK;
import static stevesvehicles.common.items.ComponentTypes.ENTITY_ANALYZER;
import static stevesvehicles.common.items.ComponentTypes.ENTITY_SCANNER;
import static stevesvehicles.common.items.ComponentTypes.PIPE;
import static stevesvehicles.common.items.ComponentTypes.SHOOTING_STATION;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevesvehicles.client.localization.entry.info.LocalizationGroup;
import stevesvehicles.client.rendering.models.common.ModelGun;
import stevesvehicles.client.rendering.models.common.ModelMobDetector;
import stevesvehicles.client.rendering.models.common.ModelShootingRig;
import stevesvehicles.client.rendering.models.common.ModelSniperRifle;
import stevesvehicles.common.core.StevesVehicles;
import stevesvehicles.common.holiday.HolidayType;
import stevesvehicles.common.modules.common.addon.mobdetector.ModuleAnimal;
import stevesvehicles.common.modules.common.addon.mobdetector.ModuleBat;
import stevesvehicles.common.modules.common.addon.mobdetector.ModuleMonster;
import stevesvehicles.common.modules.common.addon.mobdetector.ModulePlayer;
import stevesvehicles.common.modules.common.addon.mobdetector.ModuleVillager;
import stevesvehicles.common.modules.common.addon.projectile.ModuleCake;
import stevesvehicles.common.modules.common.addon.projectile.ModuleEgg;
import stevesvehicles.common.modules.common.addon.projectile.ModuleFireball;
import stevesvehicles.common.modules.common.addon.projectile.ModulePotion;
import stevesvehicles.common.modules.common.addon.projectile.ModuleSnowball;
import stevesvehicles.common.modules.common.attachment.ModuleShooter;
import stevesvehicles.common.modules.common.attachment.ModuleShooterAdvanced;
import stevesvehicles.common.modules.datas.ModuleData;
import stevesvehicles.common.modules.datas.ModuleDataGroup;
import stevesvehicles.common.modules.datas.ModuleSide;
import stevesvehicles.common.vehicles.VehicleRegistry;

public class ModuleRegistryShooters extends ModuleRegistry {
	public static final String SHOOTER_KEY = "Shooters";
	public static final String DETECTOR_KEY = "EntityDetectors";
	private ModuleData advanced;

	public ModuleRegistryShooters() {
		super("common.shooters");
		loadShooters();
		loadDetectors();
		loadProjectiles();
	}

	private void loadShooters() {
		ModuleDataGroup shooters = ModuleDataGroup.createGroup(SHOOTER_KEY, LocalizationGroup.SHOOTER);
		ModuleDataGroup detectors = ModuleDataGroup.getGroup(DETECTOR_KEY);
		ModuleData shooter = new ModuleData("shooter", ModuleShooter.class, 15) {
			@Override
			@SideOnly(Side.CLIENT)
			public void loadModels() {
				ArrayList<Integer> pipes = new ArrayList<>();
				for (int i = 0; i < 9; i++) {
					if (i == 4) {
						continue;
					}
					pipes.add(i);
				}
				addModel("Rig", new ModelShootingRig());
				addModel("Pipes", new ModelGun(pipes));
			}
		};
		shooter.addShapedRecipe(PIPE, PIPE, PIPE, PIPE, SHOOTING_STATION, PIPE, PIPE, PIPE, PIPE);
		shooter.addSides(ModuleSide.TOP);
		shooter.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		shooters.add(shooter);
		register(shooter);
		advanced = new ModuleData("advanced_shooter", ModuleShooterAdvanced.class, 50) {
			@Override
			@SideOnly(Side.CLIENT)
			public void loadModels() {
				addModel("Rig", new ModelShootingRig());
				addModel("MobDetector", new ModelMobDetector());
				addModel("Pipes", new ModelSniperRifle());
			}
		};
		advanced.addShapedRecipe(null, ENTITY_SCANNER, null, null, SHOOTING_STATION, PIPE, Items.IRON_INGOT, ENTITY_ANALYZER, Items.IRON_INGOT);
		advanced.addSides(ModuleSide.TOP);
		advanced.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		advanced.addRequirement(detectors);
		shooters.add(advanced);
		register(advanced);
	}

	private void loadDetectors() {
		ModuleDataGroup detectors = ModuleDataGroup.createGroup(DETECTOR_KEY, LocalizationGroup.ENTITY);
		ModuleData animal = new ModuleData("entity_detector_animal", ModuleAnimal.class, 1);
		animal.addShapedRecipeWithSize(1, 2, Items.PORKCHOP, EMPTY_DISK);
		animal.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		animal.addParent(advanced);
		detectors.add(animal);
		register(animal);
		ModuleData player = new ModuleData("entity_detector_player", ModulePlayer.class, 7);
		player.addShapedRecipeWithSize(1, 2, Items.DIAMOND, EMPTY_DISK);
		player.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		player.addParent(advanced);
		detectors.add(player);
		register(player);
		ModuleData villager = new ModuleData("entity_detector_villager", ModuleVillager.class, 1);
		villager.addShapedRecipeWithSize(1, 2, Items.EMERALD, EMPTY_DISK);
		villager.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		villager.addParent(advanced);
		detectors.add(villager);
		register(villager);
		ModuleData monster = new ModuleData("entity_detector_monster", ModuleMonster.class, 1);
		monster.addShapedRecipeWithSize(1, 2, Items.ROTTEN_FLESH, EMPTY_DISK);
		monster.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		monster.addParent(advanced);
		detectors.add(monster);
		register(monster);
		ModuleData bat = new ModuleData("entity_detector_bat", ModuleBat.class, 1);
		bat.addShapedRecipeWithSize(1, 2, Blocks.PUMPKIN, EMPTY_DISK);
		bat.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		bat.addParent(advanced);
		detectors.add(bat);
		register(bat);
		if (!StevesVehicles.holidays.contains(HolidayType.HALLOWEEN)) {
			bat.lock();
		}
	}

	private void loadProjectiles() {
		ModuleDataGroup shooters = ModuleDataGroup.getGroup(SHOOTER_KEY);
		ModuleData potion = new ModuleData("projectile_potion", ModulePotion.class, 10);
		potion.addShapedRecipeWithSize(1, 2, Items.GLASS_BOTTLE, EMPTY_DISK);
		potion.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		potion.addRequirement(shooters);
		register(potion);
		ModuleData fire = new ModuleData("projectile_fire_charge", ModuleFireball.class, 10);
		fire.addShapedRecipeWithSize(1, 2, Items.FIRE_CHARGE, EMPTY_DISK);
		fire.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		fire.addRequirement(shooters);
		fire.lockByDefault();
		register(fire);
		ModuleData egg = new ModuleData("projectile_egg", ModuleEgg.class, 10);
		egg.addShapedRecipeWithSize(1, 2, Items.EGG, EMPTY_DISK);
		egg.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		egg.addRequirement(shooters);
		register(egg);
		ModuleData snowball = new ModuleData("projectile_snowball", ModuleSnowball.class, 10);
		snowball.addShapedRecipeWithSize(1, 2, Items.SNOWBALL, EMPTY_DISK);
		snowball.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		snowball.addRequirement(shooters);
		register(snowball);
		if (!StevesVehicles.holidays.contains(HolidayType.CHRISTMAS)) {
			snowball.lock();
		}
		ModuleData cake = new ModuleData("projectile_cake", ModuleCake.class, 10);
		cake.addShapedRecipeWithSize(1, 2, Items.CAKE, EMPTY_DISK);
		cake.addVehicles(VehicleRegistry.CART, VehicleRegistry.BOAT);
		cake.addRequirement(shooters);
		cake.lock();
		register(cake);
	}
}