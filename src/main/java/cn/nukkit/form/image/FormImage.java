package cn.nukkit.form.image;

import cn.nukkit.form.element.simple.ButtonImage;
import lombok.Getter;

@Getter
public class FormImage {

    private final String image;
    private final ImageType type;

    public FormImage(String image, ImageType type) {
        this.image = image;
        this.type = type;
    }

    public ButtonImage convert() {
        ButtonImage.Type buttonType = switch (type) {
            case TEXTURE -> ButtonImage.Type.PATH;
            case URL -> ButtonImage.Type.URL;
        };

        return buttonType.of(image);
    }
}