package group.togawa.destination.api;

import group.togawa.destination.Destination;
import group.togawa.destination.api.perk.PerkItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class GunPerkDataAccessor {

    private static final String GUN_PERKS_LIST = "Perks"; // 添加：用于存储枪械的Perk数据的列表标签

    private GunPerkDataAccessor() {
        // 私有构造函数防止实例化
    }

    /** 获取一把枪械的PerkTag列表 */
    @Nullable
    public static ListTag getPerkTags(ItemStack gun) {
        CompoundTag nbt = gun.getOrCreateTag();
        if (nbt.contains(GUN_PERKS_LIST, Tag.TAG_LIST)) {
            return nbt.getList(GUN_PERKS_LIST, Tag.TAG_COMPOUND);
        }
        return null;
    }

    /** 获取一把枪械的PerkItem列表 */
    @Nullable
    public static List<PerkItem> getPerks(ItemStack gun) {
        ListTag PerkTags = getPerkTags(gun);
        if (PerkTags == null) {
            return null; // 如果没有Perk标签，返回null
        }
        List<PerkItem> perks = new ArrayList<>();
        for (int i = 0; i < PerkTags.size(); i++) {
            CompoundTag PerkTag = PerkTags.getCompound(i);
            perks.add(new PerkItem(PerkTag)); // 通过构造方法将NBT形式的Perk数据转换成PerkItem对象
        }
        return perks;
    }

    /** 获取一把枪械的指定PerkItem */
    @Nullable
    public static PerkItem getPerk(ItemStack gun, ResourceLocation perkId) {
        List<PerkItem> perks = getPerks(gun);
        for (PerkItem perk : perks) {
            if (perk.PerkId != null && perk.PerkId.equals(perkId)) {
                return perk;
            }
        }
        return null; // 如果没有找到对应ID的Perk，返回null
    }

    /** 获取一把枪械的指定PerkItem，perkId为字符串 */
    @Nullable
    public static PerkItem getPerk(ItemStack gun, String perkId) {
        return getPerk(
            gun,
            ResourceLocation.fromNamespaceAndPath(Destination.ID, perkId)
        );
    }

    /** 向枪械的NBT中添加指定Perk */
    public static void addPerk(ItemStack gun, PerkItem perk) {
        List<PerkItem> perks = getPerks(gun);
        if (perks == null) {
            perks = new ArrayList<>();
        }
        // 检查是否已经存在相同ID的Perk
        for (PerkItem existingPerk : perks) {
            if (
                existingPerk.PerkId != null &&
                existingPerk.PerkId.equals(perk.PerkId)
            ) {
                return; // 如果已经存在相同ID的Perk，直接返回
            }
        }
        perks.add(perk); // 添加新的Perk
        // 将更新后的Perk列表保存回NBT
        ListTag perkTags = new ListTag();
        for (PerkItem p : perks) {
            CompoundTag tag = new CompoundTag();
            if (p.PerkId != null) {
                tag.putString("PerkId", p.PerkId.toString());
            }
            tag.putBoolean("Upgraded", p.Upgraded);
            perkTags.add(tag);
        }
        CompoundTag nbt = gun.getOrCreateTag();
        nbt.put(GUN_PERKS_LIST, perkTags);
    }

    /** 从枪械的NBT中移除指定ID的Perk */
    public static void removePerk(ItemStack gun, ResourceLocation perkId) {
        List<PerkItem> perks = getPerks(gun);
        if (perks == null) {
            return; // 如果没有Perk标签，直接返回
        }
        perks.removeIf(
            perk -> perk.PerkId != null && perk.PerkId.equals(perkId) // 移除指定ID的Perk
        );
        // 将更新后的Perk列表保存回NBT
        ListTag perkTags = new ListTag();
        for (PerkItem p : perks) {
            CompoundTag tag = new CompoundTag();
            if (p.PerkId != null) {
                tag.putString("PerkId", p.PerkId.toString());
            }
            tag.putBoolean("Upgraded", p.Upgraded);
            perkTags.add(tag);
        }
        CompoundTag nbt = gun.getOrCreateTag();
        nbt.put(GUN_PERKS_LIST, perkTags);
    }
}
