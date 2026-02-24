package cn.nukkit.config.category;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(fluent = true)
public class GameplaySettings extends OkaeriConfig {
    @Comment("pnx.settings.gameplay.mobAi")
    boolean mobAi = false;
    @Comment("pnx.settings.gameplay.enablecommandblocks")
    boolean enableCommandBlocks = false;
    @Comment("pnx.settings.gameplay.allowbeta")
    boolean allowBeta = false;
    @Comment("pnx.settings.gameplay.enableredstone")
    boolean enableRedstone = true;
    @Comment("pnx.settings.gameplay.tickRedstone")
    boolean tickRedstone = true;
    @Comment("pnx.settings.gameplay.viewDistance")
    int viewDistance = 8;
    @Comment("pnx.settings.gameplay.spawnProtection")
    int spawnProtection = 16;
    @Comment("pnx.settings.gameplay.gamemode")
    int gamemode = 0;
    @Comment("pnx.settings.gameplay.forceGamemode")
    boolean forceGamemode = false;
    @Comment("pnx.settings.gameplay.hardcore")
    boolean hardcore = false;
    @Comment("pnx.settings.gameplay.pvp")
    boolean pvp = true;
    @Comment("pnx.settings.gameplay.difficulty")
    int difficulty = 1;
    @Comment("pnx.settings.gameplay.allowNether")
    boolean allowNether = true;
    @Comment("pnx.settings.gameplay.allowEnd")
    boolean allowTheEnd = true;
    @Comment("pnx.settings.gameplay.forceResources")
    boolean forceResources = false;
    @Comment("pnx.settings.gameplay.allowClientPacks")
    boolean allowClientPacks = true;
    @Comment("pnx.settings.gameplay.packCdn")
    HashMap<String, String> packCdn = new HashMap<>(Map.of(
            "PMC_HubUI.zip", "https://cdn.pylemc.com/packs/hub/PMC_HubUI.zip",
            "PMC_HubEntity", "https://cdn.pylemc.com/packs/hub/PMC_HubEntity.zip"
    ));
    @Comment("pnx.settings.gameplay.serverAuthoritativeMovement")
    String serverAuthoritativeMovement = "server-auth";
    @Comment("pnx.settings.gameplay.allowVibrantVisuals")
    boolean allowVibrantVisuals = true;
    @Comment("pnx.settings.gameplay.cacheStructures")
    boolean cacheStructures = false;
}