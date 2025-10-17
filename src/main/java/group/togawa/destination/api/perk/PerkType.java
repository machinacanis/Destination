package group.togawa.destination.api.perk;

import com.google.gson.annotations.SerializedName;

public enum PerkType {
    NONE,

    /** 能力类型Perk，这一类Perk主要提供一些独特的功能性 */
    @SerializedName("ability")
    ABILITY,

    /** 伤害类型Perk，这一类Perk主要负责提升伤害 */
    @SerializedName("damage")
    DAMAGE,

    /** 配件类型的Perk可以调整枪械的基础数值 */

    /** 枪口Perk */
    @SerializedName("muzzle")
    MUZZLE,

    /** 枪管Perk */
    @SerializedName("barrel")
    BARREL,

    /** 瞄具Perk */
    @SerializedName("scope")
    SCOPE,

    /** 枪托Perk */
    @SerializedName("stock")
    STOCK,

    /** 握把Perk */
    @SerializedName("grip")
    GRIP,

    /** 弹匣Perk */
    @SerializedName("magazine")
    MAGAZINE,

    /** 子弹Perk */
    @SerializedName("bullet")
    BULLET,
}
