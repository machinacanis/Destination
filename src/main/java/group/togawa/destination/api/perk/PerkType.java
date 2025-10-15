package group.togawa.destination.api.perk;

import com.google.gson.annotations.SerializedName;

public enum PerkType {
    /**
     * 瞄具类型配件的可用词缀
     */
    @SerializedName("attachment_scope_perk")
    ATTACHEMENT_SCOPE_PERK,

    /**
     * 枪口类型配件的可用词缀
     */
    @SerializedName("attachment_muzzle_perk")
    ATTACHEMENT_MUZZLE_PERK,

    /**
     * 枪管类型配件的可用词缀
     */
    @SerializedName("attachment_stock_perk")
    ATTACHEMENT_STOCK_PERK,

    /**
     * 握把类型配件的可用词缀
     */
    @SerializedName("attachment_grip_perk")
    ATTACHEMENT_GRIP_PERK,

    /**
     * 枪托类型配件的可用词缀
     */
    @SerializedName("attachment_lazer_perk")
    ATTACHEMENT_LAZER_PERK,

    /**
     * 扩容弹匣类型配件的可用词缀
     */
    @SerializedName("attachment_extended_mag_perk")
    ATTACHEMENT_EXTENDED_MAG_PERK,

    /**
     * 枪械的枪口类型词缀
     */
    @SerializedName("gun_muzzle_perk")
    GUN_MUZZLE_PERK,

    /**
     * 枪械的枪管类型词缀
     */
    @SerializedName("gun_barrel_perk")
    GUN_BARREL_PERK,

    /**
     * 枪械的弹匣类型词缀
     */
    @SerializedName("gun_mag_perk")
    GUN_MAG_PERK,

    /**
     * 枪械的握把类型词缀
     */
    @SerializedName("gun_grip_perk")
    GUN_GRIP_PERK,

    /**
     * 枪械的瞄具类型词缀
     */
    @SerializedName("gun_scope_perk")
    GUN_SCOPE_PERK,

    /**
     * 枪械的枪托类型词缀
     */
    @SerializedName("gun_stock_perk")
    GUN_STOCK_PERK,

    /**
     * 枪械的能力类型词缀
     */
    @SerializedName("gun_ability_perk")
    GUN_ABILITY_PERK,

    /**
     * 不是词缀
     */
    NONE,
}
