package cn.nukkit.form;

import cn.nukkit.form.image.FormImage;
import cn.nukkit.player.Player;
import cn.nukkit.form.response.Response;
import cn.nukkit.form.window.Form;
import cn.nukkit.form.window.SimpleForm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xRookieFight
 * @since 24/02/2026
 */
public abstract class BaseForm<R extends Response> {

    private final ConcurrentHashMap<String, Map<String, Object>> playerState = new ConcurrentHashMap<>();

    protected abstract Form<R> getForm(Player player);

    protected abstract void onResponse(Player player, R response);

    protected void onClose(Player player) {
        // NOOP
    }

    public void send(Player player) {
        getForm(player)
                .onSubmit((p, response) -> {
                    if (response != null) {
                        onResponse(p, response);
                    }
                    clearState(p);
                })
                .onClose(p -> {
                    onClose(player);
                    clearState(player);
                })
                .send(player);
    }

    protected void setState(Player player, String key, Object value) {
        playerState
                .computeIfAbsent(player.getName(), k -> new ConcurrentHashMap<>())
                .put(key, value);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getState(Player player, String key) {
        Map<String, Object> state = playerState.get(player.getName());
        if (state == null) return null;
        return (T) state.get(key);
    }

    private void clearState(Player player) {
        playerState.remove(player.getName());
    }

    public static SimpleForm addButton(SimpleForm form, String text, FormImage image) {
        if (image != null) {
            form.addButton(text, image.convert());
        } else {
            form.addButton(text);
        }
        return form;
    }
}