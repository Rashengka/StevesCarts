package vswe.stevescarts.Helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.Items.ModItems;
import vswe.stevescarts.ModuleData.ModuleData;

import java.util.ArrayList;
import java.util.Random;

public class GiftItem {
	private int chanceWeight;
	private int costPerItem;
	private ItemStack item;
	private boolean fixedSize;
	public static ArrayList<GiftItem> ChristmasList;
	public static ArrayList<GiftItem> EasterList;

	public GiftItem(final ItemStack item, final int costPerItem, final int chanceWeight) {
		this.item = item;
		this.chanceWeight = chanceWeight;
		this.costPerItem = costPerItem;
	}

	public GiftItem(final Block block, final int costPerItem, final int chanceWeight) {
		this(new ItemStack(block, 1), costPerItem, chanceWeight);
	}

	public GiftItem(final Item item, final int costPerItem, final int chanceWeight) {
		this(new ItemStack(item, 1), costPerItem, chanceWeight);
	}

	public static void init() {
		(GiftItem.ChristmasList = new ArrayList<GiftItem>()).add(new GiftItem(new ItemStack(Blocks.DIRT, 32), 25, 200000));
		GiftItem.ChristmasList.add(new GiftItem(new ItemStack(Blocks.STONE, 16), 50, 100000));
		GiftItem.ChristmasList.add(new GiftItem(new ItemStack(Items.COAL, 8), 50, 50000));
		GiftItem.ChristmasList.add(new GiftItem(new ItemStack(Items.REDSTONE, 2), 75, 22000));
		GiftItem.ChristmasList.add(new GiftItem(new ItemStack(Items.IRON_INGOT, 4), 75, 25000));
		GiftItem.ChristmasList.add(new GiftItem(Items.GOLD_INGOT, 80, 17000));
		GiftItem.ChristmasList.add(new GiftItem(Items.DIAMOND, 250, 5000));
		addModuleGifts(GiftItem.ChristmasList);
		addModuleGifts(GiftItem.EasterList = new ArrayList<GiftItem>());
	}

	public static void addModuleGifts(final ArrayList<GiftItem> gifts) {
		for (final ModuleData module : ModuleData.getList().values()) {
			if (module.getIsValid() && !module.getIsLocked() && module.getHasRecipe() && module.getCost() > 0) {
				final GiftItem item = new GiftItem(new ItemStack(ModItems.modules, 1, (int) module.getID()), module.getCost() * 20, (int) Math.pow(151 - module.getCost(), 2.0));
				item.fixedSize = true;
				gifts.add(item);
			}
		}
	}

	public static ArrayList<ItemStack> generateItems(final Random rand, final ArrayList<GiftItem> gifts, int value, final int maxTries) {
		int totalChanceWeight = 0;
		for (final GiftItem gift : gifts) {
			totalChanceWeight += gift.chanceWeight;
		}
		final ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if (totalChanceWeight == 0) {
			return items;
		}
		for (int tries = 0; value > 0 && tries < maxTries; ++tries) {
			int chance = rand.nextInt(totalChanceWeight);
			for (final GiftItem gift2 : gifts) {
				if (chance < gift2.chanceWeight) {
					int maxSetSize = value / gift2.costPerItem;
					if (maxSetSize * gift2.item.stackSize > gift2.item.getItem().getItemStackLimit(gift2.item)) {
						maxSetSize = gift2.item.getItem().getItemStackLimit(gift2.item) / gift2.item.stackSize;
					}
					if (maxSetSize > 0) {
						int setSize = 1;
						if (!gift2.fixedSize) {
							for (int i = 1; i < maxSetSize; ++i) {
								if (rand.nextBoolean()) {
									++setSize;
								}
							}
						}
						final ItemStack copy;
						final ItemStack item = copy = gift2.item.copy();
						copy.stackSize *= setSize;
						items.add(item);
						value -= setSize * gift2.costPerItem;
						break;
					}
					break;
				} else {
					chance -= gift2.chanceWeight;
				}
			}
		}
		return items;
	}
}