package group.togawa.destination.api.perk;

import group.togawa.destination.Destination;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PerkItem {

    @Nullable
    public ResourceLocation PerkId = null; // Perk的ID

    public boolean Upgraded = false; // Perk的强化状态

    public PerkItem(ResourceLocation id, boolean upgraded) {
        this.PerkId = id;
        this.Upgraded = upgraded;
    }

    public PerkItem(ResourceLocation id) {
        this.PerkId = id;
    }

    public PerkItem(String id, boolean upgraded) {
        this.PerkId = ResourceLocation.fromNamespaceAndPath(Destination.ID, id);
        this.Upgraded = upgraded;
    }

    public PerkItem(String id) {
        this.PerkId = ResourceLocation.fromNamespaceAndPath(Destination.ID, id);
    }

    public PerkItem(CompoundTag tag) {
        if (tag.contains("PerkId")) {
            this.PerkId = ResourceLocation.tryParse(tag.getString("PerkId"));
        }
        if (tag.contains("Upgraded")) {
            this.Upgraded = tag.getBoolean("Upgraded");
        }
    }
}
