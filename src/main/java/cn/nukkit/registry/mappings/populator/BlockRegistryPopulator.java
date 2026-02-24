package cn.nukkit.registry.mappings.populator;

import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockFlowingLava;
import cn.nukkit.block.property.CommonBlockProperties;
import cn.nukkit.registry.mappings.BlockMappings;
import cn.nukkit.registry.mappings.JeBlockState;
import cn.nukkit.level.updater.Updater;
import cn.nukkit.level.updater.block.*;
import cn.nukkit.level.updater.util.tagupdater.CompoundTagUpdaterContext;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.TreeMapCompoundTag;
import cn.nukkit.registry.Registries;
import cn.nukkit.utils.HashUtils;
import cn.nukkit.utils.JSONUtils;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Populates the block registries.
 */
public final class BlockRegistryPopulator {

    @FunctionalInterface
    private interface Remapper {

        CompoundTag remap(CompoundTag tag);

        static Remapper of(Updater... updaters) {
            CompoundTagUpdaterContext context = new CompoundTagUpdaterContext();
            for (Updater updater : updaters) {
                updater.registerUpdaters(context);
            }

            return tag -> {
                CompoundTag updated = context.update(tag, 0);
                updated.remove("version"); // we already removed this, but the context adds it. remove it again.
                return new TreeMapCompoundTag(updated.getTags());
            };
        }

    }

    final static Remapper mapper = Remapper.of(
            BlockStateUpdater_1_20_10.INSTANCE,
            BlockStateUpdater_1_20_30.INSTANCE,
            BlockStateUpdater_1_20_40.INSTANCE,
            BlockStateUpdater_1_20_50.INSTANCE,
            BlockStateUpdater_1_20_60.INSTANCE,
            BlockStateUpdater_1_20_70.INSTANCE,
            BlockStateUpdater_1_20_80.INSTANCE,
            BlockStateUpdater_1_21_0.INSTANCE,
            BlockStateUpdater_1_21_10.INSTANCE,
            BlockStateUpdater_1_21_20.INSTANCE,
            BlockStateUpdater_1_21_30.INSTANCE,
            BlockStateUpdater_1_21_40.INSTANCE,
            BlockStateUpdater_1_21_60.INSTANCE
    );

    public static BlockMappings initMapping() {
        try (InputStream stream = BlockRegistryPopulator.class.getClassLoader().getResourceAsStream("mappings/blocks.json")) {
            Map<String, Map<String, Object>> blocks = JSONUtils.from(
                    stream,
                    new TypeToken<>() {
                    }
            );
            Object2ObjectOpenHashMap<JeBlockState, cn.nukkit.block.BlockState> MAP1 = new Object2ObjectOpenHashMap<>();
            Object2ObjectOpenHashMap<cn.nukkit.block.BlockState, JeBlockState> MAP2 = new Object2ObjectOpenHashMap<>();
            blocks.forEach((k, v) -> {
                final TreeMapCompoundTag treeMapCompoundTag = new TreeMapCompoundTag();
                var name = v.get("bedrock_identifier").toString();
                treeMapCompoundTag.putString("name", name);
                final TreeMapCompoundTag stateTag = new TreeMapCompoundTag();
                if (v.containsKey("bedrock_states")) {
                    Map<String, Object> states = asStringObjectMap(v.get("bedrock_states"));
                    states.forEach((key, value) -> {
                        final String valueString = value.toString();
                        if (valueString.equals("true") || valueString.equals("false")) {
                            stateTag.putBoolean(key, Boolean.parseBoolean(valueString));
                        } else if (value instanceof Number number) {
                            stateTag.putInt(key, number.intValue());
                        } else {
                            stateTag.putString(key, value.toString());
                        }
                    });
                }
                treeMapCompoundTag.putCompound("states", stateTag);
                treeMapCompoundTag.putString("version", "18087936");

                final CompoundTag remappedTag = mapper.remap(treeMapCompoundTag);
                final int i = HashUtils.fnv1a_32_nbt(remappedTag);
                cn.nukkit.block.BlockState pnxBlockState = Registries.BLOCKSTATE.get(i);
                JeBlockState jeBlockState = new JeBlockState(k);
                MAP1.put(jeBlockState, pnxBlockState);
                MAP2.put(pnxBlockState, jeBlockState);
            });
            final cn.nukkit.block.BlockState i = BlockFlowingLava.PROPERTIES.getBlockState(CommonBlockProperties.LIQUID_DEPTH.createValue(0));
            JeBlockState jeBlockState = new JeBlockState("minecraft:lava[level=0]");
            MAP1.put(jeBlockState, i);
            MAP2.put(i, jeBlockState);
            MAP1.trim();
            MAP2.trim();
            return BlockMappings.builder().mapping1(MAP1).mapping2(MAP2).build();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Map<String, Object> asStringObjectMap(Object value) {
        if (!(value instanceof Map<?, ?> rawMap)) {
            return Map.of();
        }
        Map<String, Object> result = new HashMap<>(rawMap.size());
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            Object key = entry.getKey();
            if (key instanceof String name) {
                result.put(name, entry.getValue());
            }
        }
        return result;
    }
}
