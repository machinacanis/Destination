package group.togawa.destination.api;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
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

public final class AttachmentPerkDataAccessor {

    private static final String ATTACHMENT_PERKS_LIST = "Perks"; // 添加：用于存储配件的Perk数据的列表标签

    private AttachmentPerkDataAccessor() {}

    /** 获取一个配件的PerkTag列表 */
    @Nullable
    public static ListTag getPerkTags(ItemStack attachment) {
        CompoundTag nbt = attachment.getOrCreateTag();
        if (nbt.contains(ATTACHMENT_PERKS_LIST, Tag.TAG_LIST)) {
            return nbt.getList(ATTACHMENT_PERKS_LIST, Tag.TAG_COMPOUND);
        }
        return null;
    }

    /** 获取一个配件的PerkItem列表 */
    @Nullable
    public static List<PerkItem> getPerks(ItemStack attachment) {
        ListTag PerkTags = getPerkTags(attachment);
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

    /** 获取一个配件的指定PerkItem */
    @Nullable
    public static PerkItem getPerk(
        ItemStack attachment,
        ResourceLocation perkId
    ) {
        List<PerkItem> perks = getPerks(attachment);
        for (PerkItem perk : perks) {
            if (perk.PerkId != null && perk.PerkId.equals(perkId)) {
                return perk;
            }
        }
        return null; // 如果没有找到对应ID的Perk，返回null
    }

    /** 获取一个配件的指定PerkItem，perkId为字符串 */
    @Nullable
    public static PerkItem getPerk(ItemStack attachment, String perkId) {
        return getPerk(
            attachment,
            ResourceLocation.fromNamespaceAndPath(Destination.ID, perkId)
        );
    }

    /** 向配件的NBT中添加指定Perk */
    public static void addPerk(ItemStack attachment, PerkItem perk) {
        List<PerkItem> perks = getPerks(attachment);
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
        CompoundTag nbt = attachment.getOrCreateTag();
        nbt.put(ATTACHMENT_PERKS_LIST, perkTags);
    }

    /** 从配件的NBT中移除指定ID的Perk */
    public static void removePerk(
        ItemStack attachment,
        ResourceLocation perkId
    ) {
        List<PerkItem> perks = getPerks(attachment);
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
        CompoundTag nbt = attachment.getOrCreateTag();
        nbt.put(ATTACHMENT_PERKS_LIST, perkTags);
    }

    /** 获取安装在枪械上的指定类型配件的Perk列表 */
    @Nullable
    public static List<PerkItem> getInstalledAttachmentPerks(
        ItemStack gun,
        AttachmentType type
    ) {
        if (gun.getItem() instanceof IGun iGun) {
            ItemStack attachment = iGun.getAttachment(gun, type); // 从枪械获取配件
            if (attachment != null) {
                // 配件存在
                List<PerkItem> perks = getPerks(attachment);
                if (perks != null && !perks.isEmpty()) {
                    return perks; // 假设每个配件只有一个Perk，返回第一个
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /** 获取安装在枪械上的指定类型配件的指定PerkItem */
    @Nullable
    public static PerkItem getInstalledAttachmentPerk(
        ItemStack gun,
        AttachmentType type,
        ResourceLocation perkId
    ) {
        if (gun.getItem() instanceof IGun iGun) {
            ItemStack attachment = iGun.getAttachment(gun, type); // 从枪械获取配件
            if (attachment != null) {
                // 配件存在
                return getPerk(attachment, perkId);
            }
            return null;
        } else {
            return null;
        }
    }
}
