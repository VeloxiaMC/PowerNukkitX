package cn.nukkit.recipe.type;

import cn.nukkit.recipe.BaseRecipe;
import cn.nukkit.recipe.Input;
import cn.nukkit.recipe.descriptor.ItemDescriptor;

/**
 * The type Smithing recipe for trim equipment.
 *
 * @author CoolLoong
 */
public class SmithingTrimRecipe extends BaseRecipe {
    private final String tag;

    public SmithingTrimRecipe(String id, ItemDescriptor base, ItemDescriptor addition, ItemDescriptor template, String tag) {
        super(id);
        results.clear();
        ingredients.add(template);
        ingredients.add(base);
        ingredients.add(addition);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean match(Input input) {
        return false;
    }

    @Override
    public RecipeType getType() {
        return RecipeType.SMITHING_TRIM;
    }
}
